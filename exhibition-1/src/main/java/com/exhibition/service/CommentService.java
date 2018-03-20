package com.exhibition.service;

import com.exhibition.dto.CommentPreviewDto;
import com.exhibition.po.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Since: JDK 1.8
 * @Author: ZhaoKunsong
 * @Description:
 * @Date: 2017/8/14 21:27
 **/
public interface CommentService {

    /**
     * 根据commentId查询
     * @param id
     * @return
     */
    public Comment queryCommentByCommentId(Integer id);

    /**
     * 条件查询
     * @param comment
     * @param page  当前页数
     * @param size  每页的记录数
     * @return
     */
    public List<Comment> queryCommentList(Comment comment,Integer page, Integer size);

    /**
     * 新增评论
     * @param comment
     * @return
     */
    public boolean insertComment(Comment comment);

    /**
     * 删除评论
     * @param id
     * @return
     */
    public boolean deleteComment(Integer id);

    /**
     * 修改评论
     * @param comment
     * @return
     */
    public boolean updateComment(Comment comment);

    /**
     * 根据展品id查询
     * @param productId
     * @param page
     * @param size
     * @return
     */
    public List<Comment> queryCommentListByProductId(Integer productId, Integer page, Integer size);

    /**
     * 根据用户id查询
     * @param userId
     * @param page
     * @param size
     * @return
     */
    public List<Comment> queryCommentListByUserId(Integer userId, Integer page, Integer size);

    /**
     * 获取总数
     * @return
     */
    public int getCount();

    /**
     * 批量删除
     * @param idList
     * @return
     */
    public boolean deleteManyComment(List<String> idList);

    /**
     * 获取展商评论预览信息：根据展品分组
     * @param exhibitorId
     * @param page
     * @param size
     * @return
     */
    CommentPreviewDto getCommontPreviews(int exhibitorId,int page,int size);

    int getCount(int exhibitsId);

    /**
     * 获取展品评论中评分最高的一条
     * @param exhibitsId
     * @return
     */
    Comment getBestComment(int exhibitsId);
}
