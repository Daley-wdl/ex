package com.exhibition.controller;

import com.exhibition.dto.SearchResultsDTO;
import com.exhibition.exceptions.ServiceException;
import com.exhibition.po.Comment;
import com.exhibition.po.Exhibits;
import com.exhibition.po.ExhibitsPhoto;
import com.exhibition.service.CommentService;
import com.exhibition.service.ExhibitsService;
import com.exhibition.solr.SolrService;
import com.exhibition.vo.*;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.SpellCheckResponse;
import org.apache.solr.common.params.FacetParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 使用solr进行搜索查询的Controller
 */
@RestController
@RequestMapping("/search")
public class SearchController {

    private static final Logger logger = Logger.getLogger(SearchController.class);

    @Autowired
    private SolrService solrService;

    @Autowired
    private ExhibitsService exhibitsService;

    @Autowired
    private CommentService commentService;

    /**
     * 搜索，如果搜索结果为空，则自动建议
     * @param field
     * @param value
     * @param page
     * @param count
     * @return
     */
    @RequestMapping(value = "/simpleSearch",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String search(@RequestParam("field") String field, @RequestParam("value") String value,
                         @RequestParam(value = "page") Integer page, @RequestParam(value = "count") Integer count) {
        Gson gson = new Gson();
        try {
            SearchResultsDTO<ExhibitsForSearch> searchResultsDTO = solrService.search(ExhibitsForSearch.class, field, value, page, count, true);
            //如果查询结果为空，则进行拼写检查，如果检查结果词的文档数不为0，则返回搜索结果
            if (searchResultsDTO.getCount() == 0) {
                SpellCheckResponse.Suggestion spellCheck = solrService.getSpellCheck(field, value, (query) -> {
                });
                int numFound = spellCheck.getNumFound();
                if (numFound != 0) {
                    List<String> alternatives = spellCheck.getAlternatives();
                    //得分最高的查询建议词
                    String mainAlternate = alternatives.get(0);
                    //查询结果
                    SearchResultsDTO<ExhibitsForSearch> spellCheckResult = solrService.search(ExhibitsForSearch.class, field, mainAlternate, page, count, true);
                    return gson.toJson(spellCheckResult);
                }
            }
            return gson.toJson(searchResultsDTO);
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.debug(e);
            }
            return gson.toJson(new SearchResultsDTO<ExhibitsForSearch>(0L, null, 0F, 0));
        }
    }

    /**
     * 拼写建议
     * @param queryStr
     * @return
     */
    @RequestMapping(value = "/suggest",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String suggest(@RequestParam("queryStr") String queryStr) {
        Gson gson = new Gson();
        try {
            Map<String, SpellCheckResponse.Suggestion> suggest = solrService.getSuggest(queryStr);
            List<String> results = new LinkedList<>();
            suggest.forEach((k,v)->{
                List<String> alternatives = v.getAlternatives();
                results.addAll(alternatives);
            });
            return gson.toJson(new LayuiReplay<String>(0, "OK", results.size(), results));
        } catch (IOException|SolrServerException e) {
            if (logger.isDebugEnabled()) {
                logger.debug(e);
            }
            return gson.toJson(new LayuiReplay<String>(1, "Error", 0, null));
        }
    }

    /**
     * 获取推荐的展品（新上架的展品）
     * @param rows
     * @return
     */
    @RequestMapping(value = "/getPopExhibits",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getPopExhibits(@RequestParam(value = "rows",required = false)Integer rows) {
        Gson gson = new Gson();
        if (rows <= 0) {
            rows = null;
        }
        try {
            SearchResultsDTO<ExhibitsForSearch> resultsDTO = solrService.query(ExhibitsForSearch.class, "*:*", 1,
                    rows, query -> {
                query.addSort("creatTime", SolrQuery.ORDER.desc);
            });
            String json = gson.toJson(resultsDTO);
            return json;
        } catch (IOException|SolrServerException e) {
            if (logger.isInfoEnabled()) {
                logger.info(e);
            }
        }
        return gson.toJson(new SearchResultsDTO(0, null, 0F, 0));
    }

    /**
     * 获取推荐的热门分类
     * @return
     */
    @RequestMapping(value = "/getCategory", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getCategory(@RequestParam("size")int size,@RequestParam(value = "offset",required = false)Integer offset) {
        Gson gson = new Gson();
        List<CategoryVo> categoryList = new LinkedList<>();

        String[] fields = {"category"};
        try {
            List<FacetField> fieldFacetList = solrService.getFieldFacet(fields, size, 1, query -> {
                if (offset != null && offset > 0) {
                    query.set(FacetParams.FACET_OFFSET, offset);
                }
            });
            for (FacetField facetField : fieldFacetList) {
                List<FacetField.Count> values = facetField.getValues();
                for (FacetField.Count count : values) {
                    String name = count.getName();
                    long countCount = count.getCount();
                    CategoryVo vo = new CategoryVo(name, countCount);
                    categoryList.add(vo);
                }
            }
        } catch (IOException | SolrServerException e) {
            if (logger.isInfoEnabled()) {
                logger.info(e);
            }
            return gson.toJson(new LayuiReplay<CategoryVo>(1,"查询出现错误，请重试",categoryList.size(),categoryList));
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.debug(e);
            }
            return gson.toJson(new LayuiReplay<CategoryVo>(1,"查询出现错误，请重试",categoryList.size(),categoryList));
        }

        return gson.toJson(new LayuiReplay<CategoryVo>(0,"OK",categoryList.size(),categoryList));
    }

    /**
     * 根据类别搜索展品
     * @param category
     * @return
     */
    @RequestMapping(value = "/getExhibitsByCategory", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getExhibitsByCategory(@RequestParam("category") String category,
                                        @RequestParam("page") int page, @RequestParam("size") int size) {
        Gson gson = new Gson();
        String queryStr = "category:" + category;
        try {
            SearchResultsDTO<ExhibitsForSearch> resultsDTO = solrService.query(ExhibitsForSearch.class, queryStr, page, size, query -> {
            });
            return gson.toJson(new LayuiReplay<ExhibitsForSearch>(0,"OK",(int)resultsDTO.getCount(),resultsDTO.getData()));
        } catch (Exception e) {
            logger.debug(e);
        }
        return gson.toJson(new LayuiReplay<ExhibitsForSearch>(0,"OK",0,null));
    }

    /**
     * 获取展商发布的展品
     * @param exhibitorId
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/getExhibitsByExhibitor", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getExhibitsByExhibitor(@RequestParam("exhibitorId") Integer exhibitorId,
                                         @RequestParam("page") int page, @RequestParam("size") int size) {
        Gson gson = new Gson();
        List<Exhibits> exhibitsList = exhibitsService.getExhibitsByExhibitorId(page, size, exhibitorId);
        return gson.toJson(new LayuiReplay<Exhibits>(0,"OK",exhibitsList.size(),exhibitsList));
    }

    /**
     * 获取展品的详细信息（详情图片、exhibits信息、主要评论）
     * @param id
     * @return
     */
    @RequestMapping(value = "/getExhibitInfo", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getExhibitInfo(@RequestParam("id") int id) {
        Gson gson = new Gson();
        ExhibitsForSearch exhibitsForSearch = new ExhibitsForSearch();
        try {
            Exhibits exhibits = exhibitsService.getExhibitsById(id);
            Comment bestComment = commentService.getBestComment(id);
            List<ExhibitsPhoto> exhibitsPhotos = exhibitsService.getExhibitsPhotos(id);
            List<String> photoPaths = new LinkedList<>();
            photoPaths.add(exhibits.getMainPhotoPath());
            exhibitsPhotos.forEach(exhibitsPhoto -> {
                photoPaths.add(exhibitsPhoto.getPath());
            });
            ExhibitsDetail exhibitsDetail = new ExhibitsDetail(exhibits, bestComment, photoPaths);
            String json = gson.toJson(new DataResult<ExhibitsDetail>(0,"OK",exhibitsDetail));

            return json;
        } catch (ServiceException e) {
            if (logger.isInfoEnabled()) {
                logger.info(e);
            }
        } catch (Exception e) {
            logger.debug(e);
        }
        return gson.toJson(new DataResult<ExhibitsDetail>(0,"查询出错，请重试",null));
    }

    /**
     * 获取展品的评论列表
     * @param exhibitsId
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/getComments",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getCommentsByExhibitsId(@RequestParam("exhibitsId") Integer exhibitsId
            , @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        Gson gson = new Gson();
        List<Comment> comments = commentService.queryCommentListByProductId(exhibitsId, page, size);
        comments.forEach(comment -> {
            String userName = comment.getUserName();
            if (userName.length() > 2) {
                char[] chars = userName.toCharArray();
                String newName = chars[0] + "****" + chars[chars.length - 1];
                comment.setUserName(newName);
            }
        });
        int count = commentService.getCount(exhibitsId);
        LayuiReplay<Comment> replay = new LayuiReplay<>(0, "OK", count, comments);
        return gson.toJson(replay);
    }


    @RequestMapping(value = "/getExhibitsByExhibitorId", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getExhibitsByExhibitorId(@RequestParam("exhibitorId") int exhibitorId
            , @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        Gson gson = new Gson();
        try {
            List<Exhibits> exhibitsList = exhibitsService.getExhibitsByExhibitorId(page, size, exhibitorId);
            return gson.toJson(new DataResult<List<Exhibits>>(0,"OK", exhibitsList));
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.debug(e);
            }
            return gson.toJson(new DataResult<List<Exhibits>>(1,"请求发生错误,请重试", null));
        }
    }

}
