package com.exhibition.controller;

import com.exhibition.constants.SessionConstants;
import com.exhibition.dto.CommentPreviewDto;
import com.exhibition.exceptions.ServiceException;
import com.exhibition.po.Comment;
import com.exhibition.po.CommentPreview;
import com.exhibition.po.Exhibits;
import com.exhibition.service.CommentService;
import com.exhibition.service.ExhibitsService;
import com.exhibition.vo.FeedbackJson;
import com.exhibition.vo.LayuiReplay;
import com.exhibition.vo.ReplyResult;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Since: JDK 1.8
 * @Author: ZhaoKunsong
 * @Description: 评论管理的控制层
 * @Date: 2017/9/2 9:29
 **/
@Controller
@RequestMapping("/comment")
public class CommentController {

    private static final Logger logger = Logger.getLogger(CommentController.class);

    @Autowired
    private CommentService commentService;

    @Autowired
    private ExhibitsService exhibitsService;

    /**
     * <p>管理员获取评价列表</p>
     * <p>需要传入表单</p>
     * <p>若传入的请求参数为空，则根据分页返回所有的列表</p>
     * <p>否则返回条件查询结果</p>
     * <p>在shiro中配置只有admin和manager才能访问</p>
     * @param comment 查询条件
     * @param page 页数
     * @param size 每页的记录数
     * @return 评论信息列表的Json字符串,包括：详细信息、状态码、原因
     */
    @RequestMapping(value = "/commentListJson")
    @ResponseBody
    public String getCommentListJson(Comment comment, Integer page, Integer size) {

        Gson gson = new Gson();
        FeedbackJson feedbackJson = new FeedbackJson();
        List<Comment> commentList = commentService.queryCommentList(comment, page, size);
        //若结果为空则返回错误信息
        if(commentList.isEmpty()) {
            feedbackJson.setDetailList(null);
            feedbackJson.setReason("There are not have any commentList information");
            feedbackJson.setError("1");
        }
        else {
            feedbackJson.setDetailList(commentList);
            feedbackJson.setReason("success");
            feedbackJson.setError("0");
        }

        return gson.toJson(feedbackJson);
    }

    /**
     * <p>增加评论</p>
     * @param comment 封装的新增评论对象
     * @return Json字符串，包括：详细信息、状态码、原因
     */
    @RequestMapping(value = "/addComment",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String addComment(@Validated Comment comment, BindingResult bindingResult) {
        Gson gson = new Gson();
        if (logger.isInfoEnabled()) {
            logger.info(comment);
        }
        if (bindingResult.hasErrors()) {
            return gson.toJson(new ReplyResult(1, "输入信息不全"));
        }

        Subject subject = SecurityUtils.getSubject();
        Integer userId = (Integer) subject.getSession().getAttribute(SessionConstants.USER_ID);
        if (userId == null) {
            return gson.toJson(new ReplyResult(1, "用户id为空"));
        }
        try {
            comment.setUserId(userId);
            commentService.insertComment(comment);
            return gson.toJson(new ReplyResult(0, "添加评论成功"));
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.debug(e);
            }
            return gson.toJson(new ReplyResult(1, "添加评论失败"));
        }
    }

    /**
     * <p>根据Id获取评论信息</p>
     * @param commentId 评论的id
     * @return Json字符串，包括：详细信息、状态码、原因
     */
    @RequestMapping(value = "/commentByIdJson")
    @ResponseBody
    public String getCommentByIdJson(Integer commentId) {

        Gson gson = new Gson();
        FeedbackJson feedbackJson = new FeedbackJson();

        if(commentId == null) {
            feedbackJson.setDetailObject(null);
            feedbackJson.setReason("The parameter is null");
            feedbackJson.setError("1");
        }
        else {
            Comment comment = commentService.queryCommentByCommentId(commentId);
            if(comment == null) {
                feedbackJson.setDetailObject(null);
                feedbackJson.setReason("There are not have any comment information");
                feedbackJson.setError("1");
            }
            else {
                feedbackJson.setDetailObject(comment);
                feedbackJson.setReason("success");
                feedbackJson.setError("0");
            }
        }

        return gson.toJson(feedbackJson);
    }

    /**
     * <p>编辑评论</p>
     * @param comment 封装的修改后的评论对象
     * @return Json字符串，包括：详细信息、状态码、原因
     */
    @RequestMapping(value = "/editComment")
    @ResponseBody
    public String editComment(Comment comment) {

        Gson gson = new Gson();
        FeedbackJson feedbackJson = new FeedbackJson();

        if(comment == null) {
            feedbackJson.setReason("The parameter is null");
            feedbackJson.setError("1");
            return gson.toJson(feedbackJson);
        }
        try {
            boolean result = commentService.updateComment(comment);
            if(!result) {
                feedbackJson.setReason("edit comment defeated");
                feedbackJson.setError("1");
            }
            else {
                feedbackJson.setReason("success");
                feedbackJson.setError("0");
            }
            return gson.toJson(feedbackJson);
        }
        catch (ServiceException se) {
            feedbackJson.setReason("please insure the information completely");
            feedbackJson.setError("1");
            return gson.toJson(feedbackJson);
        }

    }

    /**
     * <p>删除单条评论</p>
     * @param commentId
     * @return Json字符串，包括：详细信息、状态码、原因
     */
    @RequestMapping(value = "/deleteComment")
    @ResponseBody
    public String deleteComment(Integer commentId) {

        Gson gson = new Gson();
        FeedbackJson feedbackJson = new FeedbackJson();

        if(commentId == null) {
            feedbackJson.setReason("The parameter is null");
            feedbackJson.setError("1");
            return gson.toJson(feedbackJson);
        }

        try {
            boolean result = commentService.deleteComment(commentId);
            if(!result) {
                feedbackJson.setReason("delete comment defeated");
                feedbackJson.setError("1");
            }
            else {
                feedbackJson.setReason("success");
                feedbackJson.setError("0");
            }
            return gson.toJson(feedbackJson);

        }
        catch (ServiceException se) {
            feedbackJson.setReason("please insure the id is true");
            feedbackJson.setError("1");
            return gson.toJson(feedbackJson);
        }
    }

    /**
     * <p>删除多条评论</p>
     * @param idList
     * @return Json字符串，包括：详细信息、状态码、原因
     */
    @RequestMapping(value = "/deleteManyComments")
    @ResponseBody
    public String deleteManyComments(List idList) {

        Gson gson = new Gson();
        FeedbackJson feedbackJson = new FeedbackJson();

        if(idList.isEmpty()) {
            feedbackJson.setReason("The parameter is null");
            feedbackJson.setError("1");
        }
        else {
            boolean result = commentService.deleteManyComment(idList);
            if (!result) {
                feedbackJson.setReason("delete many comment defeated");
                feedbackJson.setError("1");
            }
            else {
                feedbackJson.setReason("success");
                feedbackJson.setError("0");
            }
        }
        return gson.toJson(feedbackJson);
    }

    @RequestMapping(value = "/getCommentsPreview",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getCommentsPreview(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        Gson gson = new Gson();
        Subject subject = SecurityUtils.getSubject();
        Integer userId = (Integer) subject.getSession().getAttribute("userId");
        CommentPreviewDto commentPreviewDto = commentService.getCommontPreviews(userId, page, size);
        String json = gson.toJson(new LayuiReplay<CommentPreview>(0, "OK",
                commentPreviewDto.getTotalCount(), commentPreviewDto.getCommentPreviewList()));
        return json;
    }

    @RequestMapping(value = "/getCommentsByExhibitsId", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getCommentsByExhibitsId(@RequestParam("exhibitsId") Integer exhibitsId
            , @RequestParam("page") int page, @RequestParam("size") int size) {
        Gson gson = new Gson();
        Subject subject = SecurityUtils.getSubject();
        Integer userId = (Integer) subject.getSession().getAttribute(SessionConstants.USER_ID);
        if (userId == null) {
            return gson.toJson(new LayuiReplay<Comment>(-1, "未登录", 0, null));
        }
        try {
            List<Comment> comments = commentService.queryCommentListByProductId(exhibitsId, page, size);
            int count = commentService.getCount(exhibitsId);
            return gson.toJson(new LayuiReplay<Comment>(0, "OK", count, comments));
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.debug(e);
            }
            return gson.toJson(new LayuiReplay<Comment>(1, "发生错误", 0, null));
        }
    }

}
