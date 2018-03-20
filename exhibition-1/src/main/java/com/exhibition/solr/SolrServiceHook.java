package com.exhibition.solr;

import org.apache.solr.client.solrj.SolrQuery;

/**
 * 用于查询中通过钩子方法增加查询功能，如分面等
 */
public interface SolrServiceHook {

    /**
     * 修改SolrQuery行为
     * @param query
     */
    void modifySolrQuery(SolrQuery query);
}
