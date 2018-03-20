package com.exhibition.solr;

import com.exhibition.dto.SearchResultsDTO;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrResponse;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.*;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.StringUtils;
import org.apache.solr.common.params.CommonParams;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 对solrj的包装,方便查询
 * <strong>bean的字段需要带有solrj的注解</strong>
 * 增加：add
 * 删除：delete
 * 更新：update
 * 查询：
 *      1、search(*),q="field:keywords",查询默认字段，其高亮部分中用于获取bean主键的方法名为默认GET_ID_METHOD_NAME
 *      2、query(Class<T> klass, String queryStr, int page, int rows,SolrServiceHook hook) 较为简单的查询，查询默认字段，且没有高亮
 *      3、query(Class<T> klass,String q, String[] fq, String[] fl, String sort, int page, int rows
 *              ,boolean isHighLighting,String[] fieldsNameToHighlinght,String idName,SolrServiceHook hook)
 *              参数更加齐全
 *      4、query(Class<T> klass, String q, String[] fq, String[] fl, String sort, int page, int rows, SolrServiceHook hook)
 *              与上面的查询方法相同，只是不去掉了高亮功能
 *      5、通过getSolrQuery、getSolrResponse、setHighLightValue、getBeans组合起来完成更复杂的查询功能
 * 查询分面:getFieldFacet、getQueryFacet、getRangeFacet
 * 拼写检查：getSpellCheck
 * 自动建议查询词：getSuggest
 */
public class SolrServiceImpl implements SolrService {

    private static final Logger logger = Logger.getLogger(SolrServiceImpl.class);

    //使用前时，应先用Spring注入
    private SolrClient solrClient;

    public static final int DEFAULT_ROWS = 10;
    public static final String GET_ID_METHOD_NAME = "getId";
    public static final String ID_NAME = "id";
    public static final String GAP = "\n";
    public static final String HIGHLIGHT_SIMPLE_PRE = "<em class='highLight'>";
    public static final String HIGHLIGHT_SIMPLE_POST = "</em>";
    public static String DefaultSuggestHandle = "/suggest";

    public SolrClient getSolrClient() {
        return solrClient;
    }

    public void setSolrClient(SolrClient solrClient) {
        this.solrClient = solrClient;
    }

    /**
     * 通过唯一id获取对象
     * @param obj   用于承接查询出来的属性
     * @param id
     * @param <T>
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    @Override
    public <T> T getById(T obj, String id) throws IOException, SolrServerException {
        Class<?> klass = obj.getClass();
        SolrDocument document = solrClient.getById(id);
        Field[] fields = klass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            org.apache.solr.client.solrj.beans.Field annotation = field.getAnnotation(org.apache.solr.client.solrj.beans.Field.class);
            String fieldName = annotation.value();
            Object value = document.getFieldValue(fieldName);
            if (value != null) {
                try {
                    field.set(obj,value);
                } catch (IllegalAccessException e) {
                    if (logger.isInfoEnabled()) {
                        logger.info(e);
                    }
                }
            }

        }
        return obj;
    }

    /**
     * 自定义q进行搜索，且不具有高亮功能
     * @param klass bean的class
     * @param queryStr  查询字符串
     * @param page
     * @param rows
     * @param hook  用于修改SolrQuery的行为
     * @param <T>
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    public <T> SearchResultsDTO<T> query(Class<T> klass, String queryStr, int page, int rows, SolrServiceHook hook) throws IOException, SolrServerException {
        SolrQuery solrQuery = new SolrQuery(); //构造搜索条件
        solrQuery.setQuery(queryStr); //搜索关键词
        // 设置分页 start=0就是从0开始，，rows=5当前返回5条记录，第二页就是变化start这个值为5就可以了。
        solrQuery.setStart((Math.max(page, 1) - 1) * rows);
        solrQuery.setRows(rows);
        hook.modifySolrQuery(solrQuery);
        // 执行查询
        QueryResponse queryResponse = this.solrClient.query(solrQuery);
        SolrDocumentList results = queryResponse.getResults();
        long numFound = results.getNumFound();
        Float maxScore = results.getMaxScore();
        long start = results.getStart();
        List<T> lists = queryResponse.getBeans(klass);
        return new SearchResultsDTO<>(numFound,lists,maxScore,start);
    }

    /**
     * 查询,q="field:keywords"
     * @param klass  po的class
     * @param field 查询的字段
     * @param keyword  查询value
     * @param page  页数
     * @param rows  每页查询数量
     * @param isHighlighting    是否高亮,class=hlClass
     * @param <T>   泛型
     * @return
     * @throws Exception
     */
    public <T> SearchResultsDTO<T> search(Class<T> klass,String field, String keyword, Integer page, Integer rows,boolean isHighlighting) throws Exception {
        keyword.replaceAll("\\*", "");
        if (keyword.indexOf("~") == -1 && keyword.length()>=4) {
            keyword = keyword + "~";
        }
        SolrQuery solrQuery = new SolrQuery(field + ":" + keyword); //构造搜索条件
//        solrQuery.setQuery(field + ":" + keywords); //搜索关键词
        // 设置分页 start=0就是从0开始，，rows=5当前返回5条记录，第二页就是变化start这个值为5就可以了。
        solrQuery.setStart((Math.max(page, 1) - 1) * rows);
        solrQuery.setRows(rows);

        //是否需要高亮
        if (isHighlighting) {
            // 设置高亮
            solrQuery.setHighlight(true); // 开启高亮组件
            solrQuery.addHighlightField(field);// 高亮字段
            solrQuery.setHighlightSimplePre(HIGHLIGHT_SIMPLE_PRE);// 标记，高亮关键字前缀
            solrQuery.setHighlightSimplePost(HIGHLIGHT_SIMPLE_POST);// 后缀
        }

        // 执行查询
        QueryResponse queryResponse = this.solrClient.query(solrQuery);
        long start = queryResponse.getResults().getStart();
        long numFound = queryResponse.getResults().getNumFound();
        Float maxScore = queryResponse.getResults().getMaxScore();

        List<T> lists = queryResponse.getBeans(klass);
        if (isHighlighting && !"*".equals(field)) {
            //1、选取klass中表示主键的字段：使用第一个带有id的字段
            Field[] declaredFields = klass.getDeclaredFields();
            Field idField = null;
            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);
                String declaredFieldName = declaredField.getName();
                if (declaredFieldName.toLowerCase().indexOf("id") != -1) {
                    idField = declaredField;
                    break;
                }
            }

            if (idField == null) {
                return new SearchResultsDTO<>(numFound,lists,maxScore,start);
            }

            // 2、将高亮的标题数据写回到数据对象中
            Map<String, Map<String, List<String>>> map = queryResponse.getHighlighting();
            for (Map.Entry<String, Map<String, List<String>>> highlighting : map.entrySet()) {
                for (T item : lists) {
                    Object id = idField.get(item);
                    if (!highlighting.getKey().equals(id.toString())) {
                        continue;
                    }

                    try {
                        Field fieldToSet = klass.getDeclaredField(field);
                        List<String> tmpList = highlighting.getValue().get(field);
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 0; i < tmpList.size(); i++) {
                            stringBuilder.append(tmpList.get(i));
                            if (i != (tmpList.size()-1)) {
                                stringBuilder.append(GAP);
                            }
                        }
                        ServiceUtils.setValue(item,fieldToSet,stringBuilder.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }

        return new SearchResultsDTO<>(numFound,lists,maxScore,start);
    }


    /**
     * 增加bean
     * @param bean
     * @param isAutoUpdate  是否设置了自动提交
     * @param <T>
     * @throws Exception
     */
    public <T> void add(T bean,boolean isAutoUpdate) throws Exception {
        this.solrClient.addBean(bean); //添加数据到solr服务器
        //如果没有设置自动提交，则手动commit
        if (!isAutoUpdate) {
            this.solrClient.commit(); //提交
        }
    }

    /**
     * 更新（原子更新，不会新增文档）
     * @param bean  需要更新的bean实例，该bean对应solr的文档
     * @param isAutoSubmit  是否在solr配置文档中设置了自动提交
     * @param <T>
     * @throws IOException
     * @throws SolrServerException
     */
    public <T> void update(T bean,boolean isAutoSubmit) throws IOException, SolrServerException {
        Class<T> klass = (Class<T>) bean.getClass();
        SolrInputDocument document = new SolrInputDocument();

        Field[] fields = klass.getDeclaredFields();

        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(bean);//获取bean的字段值
                //如果值为空，则不是需要更新的字段
                if (value == null) {
                    continue;
                }
                //获取字段上Solr的Field注解
                org.apache.solr.client.solrj.beans.Field fieldAnnotation = field.getAnnotation(org.apache.solr.client.solrj.beans.Field.class);
                if (fieldAnnotation == null) {
                    continue;
                }
                //获取@Field注解的value值->solr文档的field字段名称
                String annoVal = fieldAnnotation.value();

                Map<String, String> map = new HashMap<>(1);//用于原子更新
                if (annoVal.toLowerCase().indexOf("id") == -1) {
                    //不是主键
                    map.put("set", value.toString());
                    document.addField(annoVal,map);
                } else {
                    //主键id
                    document.addField(annoVal,value);
                }

            } catch (IllegalAccessException e) {

            }
        }
        solrClient.add(document);
        if (!isAutoSubmit) {
            solrClient.commit();
        }
    }

    /**
     * 更新
     * @param beans
     * @param isAutoSubmit  是否在solr配置文档中设置了自动提交
     * @param <T>
     */
    public <T> void update(List<T> beans,boolean isAutoSubmit) {
        if (beans.isEmpty()) {
            return;
        }
        Class<T> klass = (Class<T>) beans.get(0).getClass();
        List<SolrDocument> documents = new ArrayList<>(beans.size());
        //--筛选出需要更新的字段，并加入map中
        Field[] fields = klass.getDeclaredFields();
        HashMap<Field, String> fieldStringHashMap = new HashMap<>();
        T tmpBean = beans.get(0);
        for (Field field : fields) {
            field.setAccessible(true);
            //获取字段上Solr的Field注解
            org.apache.solr.client.solrj.beans.Field fieldAnnotation = field.getAnnotation(org.apache.solr.client.solrj.beans.Field.class);
            if (fieldAnnotation == null) {
                continue;
            }
            try {
                Object value = field.get(beans);
                if (value == null) {
                    continue;
                }
            } catch (IllegalAccessException e) {
                continue;
            }
            //筛选完成
            String annoValue = fieldAnnotation.value();
            fieldStringHashMap.put(field, annoValue);
        }

        //--更新
        Map<String, String> map = new HashMap<>(1);//用于原子更新
        for (T bean : beans) {
            SolrInputDocument document = new SolrInputDocument();
            for (Map.Entry<Field, String> entry : fieldStringHashMap.entrySet()) {
                Field field = entry.getKey();
                String annoVal = entry.getValue();
                try {
                    Object value = field.get(bean);
                    if (annoVal.toLowerCase().indexOf("id") == -1) {
                        //不是主键
                        map.put("set", value.toString());
                        document.addField(annoVal,map);
                    } else {
                        //设置主键
                        document.addField(annoVal,value);
                    }
                } catch (IllegalAccessException e) {
                }
            }
            try {
                solrClient.add(document);
                if (!isAutoSubmit) {
                    solrClient.commit();
                }
            } catch (SolrServerException e) {
//                e.printStackTrace();
            } catch (IOException e) {
//                e.printStackTrace();
            }
        }
    }

    public void delete(List<String> ids) throws Exception {
        this.solrClient.deleteById(ids);
        this.solrClient.commit(); //提交
    }

    /**
     * 查询,可以指定是否高亮以及高亮的字段
     * @param klass bean'class
     * @param q
     * @param fq
     * @param fl
     * @param sort
     * @param page
     * @param rows
     * @param isHighLighting    是否高亮
     * @param fieldsNameToHighlinght    需要高亮的字段数组
     * @param idName    主键名
     * @param hook  钩子方法，调用时可以再次修改查询的功能
     * @param <T>
     * @return
     * @throws IOException
     * @throws SolrServerException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public <T> SearchResultsDTO<T> query(Class<T> klass,String q, String[] fq, String[] fl, String sort, int page, int rows
            ,boolean isHighLighting,String[] fieldsNameToHighlinght,String idName,SolrServiceHook hook) throws IOException, SolrServerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        SolrQuery query = getSolrQuery(q, fq, fl, sort, page, rows);
        hook.modifySolrQuery(query);
        SearchResultsDTO<T> resultsDTO = getBeans(klass, query, isHighLighting, fieldsNameToHighlinght, idName);
        return resultsDTO;
    }

    /**
     * 普通查询，不带有高亮效果
     * getSolrQuery()->getSolrResponse()->getBeans()
     * @param klass
     * @param q
     * @param fq
     * @param fl
     * @param sort
     * @param page
     * @param rows
     * @param hook
     * @param <T>
     * @return
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws SolrServerException
     * @throws IOException
     */
    public <T> SearchResultsDTO<T> query(Class<T> klass, String q, String[] fq, String[] fl, String sort, int page, int rows, SolrServiceHook hook) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, SolrServerException, IOException {
        SolrQuery query = getSolrQuery(q, fq, fl, sort, page, rows);
        hook.modifySolrQuery(query);
        SearchResultsDTO<T> resultsDTO = getBeans(klass, query, false, null, null);
        return resultsDTO;
    }

    /**
     * 根据条件创建query
     * @param q
     * @param fq
     * @param fl
     * @param sort
     * @param page
     * @param rows
     * @return
     */
    public SolrQuery getSolrQuery(String q, String[] fq, String[] fl,String sort, int page,int rows) {
        int start = (Math.max(page, 1) - 1) * rows;

        SolrQuery query = new SolrQuery();
        query.set(CommonParams.Q, q);
        query.set(CommonParams.FQ, fq);
        query.set(CommonParams.FL, fl);
        query.set(CommonParams.SORT, sort);
        query.set(CommonParams.START, start);
        query.set(CommonParams.ROWS, rows);

        return query;
    }

    /**
     * 根据query获取相应的bean列表
     * 1、如果需要高亮，设置高亮查询参数
     * 2、查询
     * 3、获取查询结果--bean列表
     * 4、<strong>如果未设置高亮则直接返回</strong>
     * 5、获取主键字段名(用于确定bean对应的高亮结果)；如果输入的id为空，则遍历bean字段的注解value值，使用遇到的第一个带有"id"的字段作为id
     * 6、获取高亮结果并设置到bean中
     * @param klass bean'Class,用于反射设置值
     * @param query
     * @param isHighLighting
     * @param fieldsNameToHighlinght
     * @param idName
     * @param <T>
     * @return
     * @throws IOException
     * @throws SolrServerException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public <T> SearchResultsDTO<T> getBeans(Class<T> klass, SolrQuery query,boolean isHighLighting,String[] fieldsNameToHighlinght,String idName)
            throws IOException, SolrServerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (isHighLighting) {
            query.setHighlight(true);
            for (String fieldNameToHighlight : fieldsNameToHighlinght) {
                query.addHighlightField(fieldNameToHighlight);
            }
            query.setHighlightSimplePre(HIGHLIGHT_SIMPLE_PRE);
            query.setHighlightSimplePost(HIGHLIGHT_SIMPLE_POST);
        }
        //查询
        QueryResponse response = getSolrResponse(query);
        //获取结果
        List<T> results = response.getBeans(klass);
        //将高亮字段注入到需要高亮的字段中
        if (isHighLighting) {
            if (ServiceUtils.isEmpty(idName)) {
                //--如果输入的idName为空，则通过反射遍历字段中solr@Field注解，如果注解value中带有“id”，则使用该字段，否则使用默认名"id"
                Field[] fields = klass.getDeclaredFields();
                for (Field field : fields) {
                    org.apache.solr.client.solrj.beans.Field annotation = field.getAnnotation(org.apache.solr.client.solrj.beans.Field.class);
                    if (annotation == null) {
                        continue;
                    }
                    String annoValue = annotation.value();
                    if (ServiceUtils.isEmpty(annoValue)) {
                        continue;
                    }
                    if (annoValue.toLowerCase().indexOf("id") != -1) {
                        idName = annoValue;
                        break;
                    }
                }
                //遍历后idName依然为空，则使用默认字段
                if (idName == null) {
                    idName = ID_NAME;
                }
            }
            setHighLightValue(results, response, idName, fieldsNameToHighlinght);
        }
        SolrDocumentList documentList = response.getResults();
        long numFound = documentList.getNumFound();
        Float maxScore = documentList.getMaxScore();
        long start = documentList.getStart();
        return new SearchResultsDTO<>(numFound,results,maxScore,start);
    }

    /**
     * 根据query获取响应
     * @param query
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    public QueryResponse getSolrResponse(SolrQuery query) throws IOException, SolrServerException {
        return solrClient.query(query);
    }

    /**
     * 将高亮段落注入到bean中
     * 1、获取主键字段值
     * 2、获取高亮结果(map类型，key为文档id，value为查询结果（map类型，key为字段名，value为该字段的高亮结果）)
     * 3、主键值和文档主键值进行比对
     * 4、使用反射，将高亮结果设置设置到相应的字段中
     * @param beans bean列表
     * @param response  solrResponse
     * @param idFieldName   bean中主键字段名
     * @param fields    高亮的字段
     * @param <T>
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public <T> List<T> setHighLightValue(List<T> beans, QueryResponse response,String idFieldName,String[] fields)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (beans.isEmpty() || fields.length == 0 || StringUtils.isEmpty(idFieldName)) {
            return beans;
        }
        Class<T> klass = (Class<T>) beans.get(0).getClass();
        Field idField = null;//
        Map<String, Field> fieldMap = new HashMap<>(fields.length);
        for (String field : fields) {
            try {
                Field fieldWithRef = klass.getDeclaredField(field);
                fieldWithRef.setAccessible(true);
                fieldMap.put(field, fieldWithRef);
            } catch (NoSuchFieldException e) {
//                e.printStackTrace();
            }
        }

        try {
            idField = klass.getDeclaredField(idFieldName);
            idField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            return beans;
        }

        Map<String, Map<String, List<String>>> highlightingMap = response.getHighlighting();
        for (T item : beans) {
            Object id = idField.get(item);
            for (Map.Entry<String, Map<String, List<String>>> highlighting : highlightingMap.entrySet()) {
                if (!highlighting.getKey().equals(id.toString())) {
                    continue;
                }
                //遍历每个需要设置的字段
                for (String field : fields) {
                    List<String> valueList = highlighting.getValue().get(field);
                    if (valueList == null || valueList.isEmpty()) {
                        continue;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    for (String s : valueList) {
                        stringBuilder.append(s).append(this.GAP);
                    }
                    String value = stringBuilder.toString();
                    if (ServiceUtils.isEmpty(value)) {
                        continue;
                    }
                    //使用反射设置高亮
                    try {
                        Field subField = fieldMap.get(field);
                        ServiceUtils.setValue(item,subField,value);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
        return beans;
    }

    /**
     * 使用分面功能，自动补全
     * @param field facet.field
     * @param prefix    前缀
     * @param min   min为最大返回结果数
     * @return
     */
    public String[] autoComplete(String field, String prefix, int min) {
        String words[] = null;
        StringBuffer sb = new StringBuffer("");
        SolrQuery query = new SolrQuery(field + ":" + prefix);
        QueryResponse rsp = new QueryResponse();
        // Facet为solr中的层次分类查询
        /*------------第二处标记：程序从第一处标记执行到这里需要300ms所以将上面的代码进行实例化最好------------------------*/
        try {
            query.setFacet(true);
            // query.setQuery("*:*");
            query = new SolrQuery(field + ":" + prefix);
            query.setFacetPrefix(prefix);
            query.addFacetField(field);
            rsp = solrClient.query(query);
            /*------------第三处标记：程序从第二处标记执行到这里需要200ms但此处很难再进行优化，由于查询的复杂性------------------------*/
        } catch (Exception e) {
            return null;
        }
        if (null != rsp) {
            FacetField ff = rsp.getFacetField(field);
            List<FacetField.Count> countList = ff.getValues();
            if (null == countList) {
                return null;
            }
            for (int i = 0; i < countList.size(); i++) {
                String tmp[] = countList.get(i).toString().split(" ");
                // 排除单个字
                if (tmp[0].length() < 2) {
                    continue;
                }
                sb.append(tmp[0] + " ");
                min--;
                if (min == 0) {
                    break;
                }
            }
            words = sb.toString().split(" ");
        } else {
            return null;
        }
        return words;
    }

    /**
     * 字段分面
     * @param fieldNames    用于分面的字段数组
     * @param limit 每个分面返回多少个唯一分面值
     * @param mincouut  必须出现的最小文档数,默认最小为1
     * @param hook
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    public List<FacetField> getFieldFacet(String[] fieldNames, int limit,int mincouut,SolrServiceHook hook) throws IOException, SolrServerException {
        SolrQuery query = new SolrQuery("*:*");
        query.setRows(0);
        query.setFacet(true);
        query.addFacetField(fieldNames);
        query.setFacetLimit(Math.max(limit,1));
        query.setFacetMissing(false);//返回值不包含缺失值的文档
        query.setFacetMinCount(Math.max(mincouut,1));
        hook.modifySolrQuery(query);

        QueryResponse response = solrClient.query(query);
        return response.getFacetFields();
    }

    /**
     * 查询分面
     * @param fields    字段数组
     * @param values    字段数组对应的value数组
     * @param hook
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    public Map<String,Integer> getQueryFacet(String[] fields, String[] values,SolrServiceHook hook) throws IOException, SolrServerException {
        SolrQuery query = new SolrQuery("*:*");
        query.setRows(0);
        query.setFacet(true);
        int len = Math.min(fields.length, values.length);
        for (int i = 0; i < len; i++) {
            String facetQueryStr = fields[i] + ":" + values[i];
            query.set("facet.query", facetQueryStr);
//            query.addFacetQuery(facetQueryStr);
        }
        hook.modifySolrQuery(query);

        QueryResponse response = solrClient.query(query);
        return response.getFacetQuery();
    }

    /**
     * 返回区间分面结果
     * @param rangeField    任何索引过的数值字段或者时间字段的名称
     * @param rangeStart    区间的下限，低于下限的值不计入分面
     * @param rangeEnd  区间的上限
     * @param gap   区间大小
     * @param hook  提供最后修改query行为的钩子方法
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    public List<RangeFacet> getRangeFacet(String rangeField, Number rangeStart, Number rangeEnd, Number gap, SolrServiceHook hook) throws IOException, SolrServerException {
        SolrQuery query = new SolrQuery("*:*");
        query.setRows(0);
        query.setFacet(true);
//        query.addNumericRangeFacet(rangeField, rangeStart, rangeEnd, gap);
        query.addNumericRangeFacet(rangeField, rangeStart, rangeEnd, gap);
        hook.modifySolrQuery(query);

        QueryResponse response = solrClient.query(query);
        return response.getFacetRanges();
    }

    /**
     * 拼写检查
     * 返回map，以入参queryStr的value部分为key，suggestion为value
     * suggestion属性：
     *      token:field名
     *      numFound:建议词找到的文档数
     *      alternative：建议词列表
     * @param queryStr  q=queryStr
     * @param hook
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    public Map<String, SpellCheckResponse.Suggestion> getSpellCheck(String queryStr, SolrServiceHook hook) throws IOException, SolrServerException {
        SolrQuery query = new SolrQuery();
        query.set(CommonParams.Q, queryStr);
        query.set("spellcheck", "on");
        hook.modifySolrQuery(query);
        QueryResponse response = getSolrResponse(query);
        if (response.getSpellCheckResponse() == null) {
            return Collections.emptyMap();
        }
        return response.getSpellCheckResponse().getSuggestionMap();
    }

    /**
     * 拼写检查
     * suggestion属性：
     *      token:field名
     *      numFound:建议词找到的文档数
     *      alternative：建议词列表
     * @param field
     * @param value
     * @param hook
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    public SpellCheckResponse.Suggestion getSpellCheck(String field, String value, SolrServiceHook hook) throws IOException, SolrServerException {
        SolrQuery query = new SolrQuery();
        query.set(CommonParams.Q, field + ":" + value);
        query.set("spellcheck", "on");
        hook.modifySolrQuery(query);
        QueryResponse response = getSolrResponse(query);
        Map<String, SpellCheckResponse.Suggestion> suggestionMap = response.getSpellCheckResponse().getSuggestionMap();
        if (suggestionMap == null) {
            return null;
        }
        SpellCheckResponse.Suggestion suggestion = suggestionMap.get(value);
        return suggestion;
    }

    /**
     * 自动建议查询词,如输入jav，提示Java，Java实战等
     * @param q   查询词语，支持多个查询词，例如；(java python)
     * @return  Map<String, SpellCheckResponse.Suggestion>:key为查询词，value为建议结果
     * @throws IOException
     * @throws SolrServerException
     */
    public Map<String, SpellCheckResponse.Suggestion> getSuggest(String q) throws IOException, SolrServerException {
        SolrQuery query = new SolrQuery();
        query.setRequestHandler(DefaultSuggestHandle);//设置查询url为默认url:/suggest,该部分需要在配置文件中配置
        query.set(CommonParams.Q, q);
        QueryResponse solrResponse = getSolrResponse(query);
        SpellCheckResponse spellCheckResponse = solrResponse.getSpellCheckResponse();
        if (spellCheckResponse == null) {
            return Collections.emptyMap();
        }
        Map<String, SpellCheckResponse.Suggestion> suggestionMap = spellCheckResponse.getSuggestionMap();
        if (suggestionMap == null) {
            return Collections.emptyMap();
        } else {
            return suggestionMap;
        }
    }

    static class ServiceUtils{

        /**
         * 使用反射将value(基本类型)设置到对应的obj中
         * @param objToSet  被设置值的对象实例
         * @param field 字段
         * @param value 值
         * @throws IllegalAccessException
         * @throws NumberFormatException
         */
        public static void setValue(Object objToSet,Field field, String value) throws IllegalAccessException,NumberFormatException {
            if (value == null || "".equals(value)) {
                return;
            }
            field.setAccessible(true);
            Object obj = field.get(objToSet);
            if (obj instanceof String) {
                field.set(objToSet,value);
            } else if (obj instanceof Integer) {
                field.set(objToSet, Integer.valueOf(value));
            } else if (obj instanceof Long) {
                field.set(objToSet, Long.valueOf(value));
            } else if (obj instanceof Float) {
                field.set(objToSet, Float.valueOf(value));
            } else if (obj instanceof Double) {
                field.set(objToSet, Double.valueOf(value));
            } else if (obj instanceof Short) {
                field.set(objToSet, Short.valueOf(value));
            } else if (obj instanceof Character) {
                field.set(objToSet, value.toCharArray()[0]);
            } else {
                //未知的引用类型
                return;
            }
        }

        /**
         * 使用反射将value(基本类型)设置到对应的obj中
         * @param objToSet  被设置值的对象实例
         * @param method    方法
         * @param value 值
         * @throws IllegalAccessException
         * @throws NumberFormatException
         * @throws InvocationTargetException
         */
        public static void setValue(Object objToSet,Method method, String value) throws IllegalAccessException, NumberFormatException, InvocationTargetException {
            if (value == null || "".equals(value)) {
                return;
            }
            method.setAccessible(true);
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length == 0) {
                return;
            }
            Class<?> paramClass = parameterTypes[0];
            if (String.class.equals(paramClass)) {
                method.invoke(objToSet, String.valueOf(value));
            } else if (Integer.class.equals(paramClass)) {
                method.invoke(objToSet, Integer.valueOf(value));
            } else if (Long.class.equals(paramClass)) {
                method.invoke(objToSet, Long.valueOf(value));
            } else if (Float.class.equals(paramClass)) {
                method.invoke(objToSet, Float.valueOf(value));
            } else if (Double.class.equals(paramClass)) {
                method.invoke(objToSet, Double.valueOf(value));
            } else if (Short.class.equals(paramClass)) {
                method.invoke(objToSet, Short.valueOf(value));
            } else if (Character.class.equals(paramClass)) {
                method.invoke(objToSet, value.toCharArray()[0]);
            } else {
                //未知的引用类型
                return;
            }
        }

        /**
         * 将字符串的首字母大写，例如：abc->Abc
         * @param s
         * @return
         */
        public static String wordAbc(String s) {
            if (isEmpty(s)) {
                return s;
            }
            if (s.length() == 1) {
                return s.substring(0, 1).toUpperCase();
            }
            return s.substring(0, 1).toUpperCase() + s.substring(1);
        }

        public static boolean isEmpty(String string) {
            if (string == null || "".equals(string)) {
                return true;
            }
            return false;
        }
    }
}
