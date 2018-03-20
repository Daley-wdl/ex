package com.exhibition.solr;

import com.exhibition.dto.SearchResultsDTO;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrResponse;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.*;
import org.apache.solr.common.SolrDocument;
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
public interface SolrService {

    public SolrClient getSolrClient();

    public void setSolrClient(SolrClient solrClient);

    /**
     * 通过唯一id获取对象
     * @param obj   用于承接查询出来的属性
     * @param id
     * @param <T>
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    public <T> T getById(T obj, String id) throws IOException, SolrServerException;

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
    public <T> SearchResultsDTO<T> query(Class<T> klass, String queryStr, int page, int rows, SolrServiceHook hook) throws IOException, SolrServerException;

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
    public <T>SearchResultsDTO<T> search(Class<T> klass,String field, String keyword, Integer page, Integer rows,boolean isHighlighting) throws Exception ;


    /**
     * 增加bean
     * @param bean
     * @param isAutoUpdate  是否设置了自动提交
     * @param <T>
     * @throws Exception
     */
    public <T> void add(T bean,boolean isAutoUpdate) throws Exception ;

    /**
     * 更新（原子更新，不会新增文档）
     * @param bean  需要更新的bean实例，该bean对应solr的文档
     * @param isAutoSubmit  是否在solr配置文档中设置了自动提交
     * @param <T>
     * @throws IOException
     * @throws SolrServerException
     */
    public <T> void update(T bean,boolean isAutoSubmit) throws IOException, SolrServerException ;

    /**
     * 更新
     * @param beans
     * @param isAutoSubmit  是否在solr配置文档中设置了自动提交
     * @param <T>
     */
    public <T> void update(List<T> beans,boolean isAutoSubmit) ;

    public void delete(List<String> ids) throws Exception ;

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
            ,boolean isHighLighting,String[] fieldsNameToHighlinght,String idName,SolrServiceHook hook) throws IOException, SolrServerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException ;

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
    public <T> SearchResultsDTO<T> query(Class<T> klass, String q, String[] fq, String[] fl, String sort, int page, int rows, SolrServiceHook hook)
            throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, SolrServerException, IOException ;

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
    public SolrQuery getSolrQuery(String q, String[] fq, String[] fl,String sort, int page,int rows) ;

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
            throws IOException, SolrServerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException ;

    /**
     * 根据query获取响应
     * @param query
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    public QueryResponse getSolrResponse(SolrQuery query) throws IOException, SolrServerException ;

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
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException ;

    /**
     * 使用分面功能，自动补全
     * @param field facet.field
     * @param prefix    前缀
     * @param min   min为最大返回结果数
     * @return
     */
    public String[] autoComplete(String field, String prefix, int min) ;

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
    public List<FacetField> getFieldFacet(String[] fieldNames, int limit,int mincouut,SolrServiceHook hook) throws IOException, SolrServerException ;

    /**
     * 查询分面
     * @param fields    字段数组
     * @param values    字段数组对应的value数组
     * @param hook
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    public Map<String,Integer> getQueryFacet(String[] fields, String[] values,SolrServiceHook hook) throws IOException, SolrServerException ;

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
    public List<RangeFacet> getRangeFacet(String rangeField, Number rangeStart, Number rangeEnd, Number gap, SolrServiceHook hook) throws IOException, SolrServerException ;

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
    public Map<String, SpellCheckResponse.Suggestion> getSpellCheck(String queryStr, SolrServiceHook hook)
            throws IOException, SolrServerException ;

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
    public SpellCheckResponse.Suggestion getSpellCheck(String field, String value, SolrServiceHook hook) throws IOException, SolrServerException ;

    /**
     * 自动建议查询词,如输入jav，提示Java，Java实战等
     * @param q   查询词语，支持多个查询词，例如；(java python)
     * @return  Map<String, SpellCheckResponse.Suggestion>:key为查询词，value为建议结果
     * @throws IOException
     * @throws SolrServerException
     */
    public Map<String, SpellCheckResponse.Suggestion> getSuggest(String q) throws IOException, SolrServerException ;

}
