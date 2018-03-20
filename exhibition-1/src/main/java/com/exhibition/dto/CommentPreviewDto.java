package com.exhibition.dto;

import com.exhibition.po.CommentPreview;

import java.util.List;

/**
 * 用于传送评论的预览信息
 */
public class CommentPreviewDto {

    private List<CommentPreview> commentPreviewList;//预览信息列表
    private int totalCount;//数据库中展商带有评论的展商数量

    public CommentPreviewDto(List<CommentPreview> commentPreviewList, int totalCount) {
        this.commentPreviewList = commentPreviewList;
        this.totalCount = totalCount;
    }

    public CommentPreviewDto() {
    }

    public List<CommentPreview> getCommentPreviewList() {
        return commentPreviewList;
    }

    public void setCommentPreviewList(List<CommentPreview> commentPreviewList) {
        this.commentPreviewList = commentPreviewList;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
