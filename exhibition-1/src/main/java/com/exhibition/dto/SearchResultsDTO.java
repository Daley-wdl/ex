package com.exhibition.dto;

import java.util.List;

/**
 * 作为SolrService层向Controller返回搜索结果的DTO
 * @param <T>
 */
public class SearchResultsDTO<T> {

    private long count;
    private List<T> data;
    private Float maxScore;
    private long start;

    public SearchResultsDTO(long count, List<T> data, Float maxScore, long start) {
        this.count = count;
        this.data = data;
        this.maxScore = maxScore;
        this.start = start;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Float getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Float maxScore) {
        this.maxScore = maxScore;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }
}
