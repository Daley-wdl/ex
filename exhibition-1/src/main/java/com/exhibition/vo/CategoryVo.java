package com.exhibition.vo;

/**
 * 为用户展示分类查询的结果
 */
public class CategoryVo {

    /*
    分类名
     */
    private String category;
    /*
    该分类下商品的数量
    */
    private long count;

    public CategoryVo() {
    }

    public CategoryVo(String category, long count) {
        this.category = category;
        this.count = count;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
