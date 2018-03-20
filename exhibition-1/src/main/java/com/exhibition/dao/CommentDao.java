package com.exhibition.dao;

import com.exhibition.po.Comment;
import com.exhibition.po.CommentPreview;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Since: JDK 1.8
 * @Author: ZhaoKunsong
 * @Description: 评论Dao接口
 * @Date: 2017/8/14 21:21
 **/
public interface CommentDao {

    /**
     * 根据commentId查询
     * @param id
     * @return
     */
    public Comment queryCommentByCommentId(Integer id);

    /**
     * 条件查询
     * @param comment
     * @param start
     * @param size
     * @return
     */
    public List<Comment> queryCommentList(@Param("comment") Comment comment, @Param("start") Integer start, @Param("size") Integer size);

    /**
     * 新增评论
     * @param comment
     * @return
     */
    public int insertComment(Comment comment);

    /**
     * 删除评论
     * @param id
     * @return
     */
    public int deleteComment(Integer id);

    /**
     * 修改评论
     * @param comment
     * @return
     */
    public int updateComment(Comment comment);

    /**
     * 根据展品id查询
     * @param productId
     * @param start
     * @param size
     * @return
     */
    public List<Comment> queryCommentListByProductId(@Param("productId") Integer productId, @Param("start") Integer start, @Param("size") Integer size);

    /**
     * 根据用户id查询
     * @param userId
     * @param start
     * @param size
     * @return
     */
    public List<Comment> queryCommentListByUserId(@Param("userId") Integer userId, @Param("start") Integer start, @Param("size") Integer size);

    /**
     * 获取总数
     * @return
     */
    public int getCount();

    /**
     * 获取展示的展品评论列表
     * @param exhibitsId    展品id
     * @param start  起始index
     * @param size  数量
     * @return
     */
    List<Comment> getCommentsByExhibitsId(@Param("exhibitsId") Integer exhibitsId,@Param("start") Integer start,@Param("size") Integer size);

    /**
     * 用于展商预览评论信息
     * 返回展商的展品列表，列表每一项都包含：展品id，展品名，展品平均评分，展品评论总数，最近评论的时间戳
     * @param exhibitorId
     * @param start
     * @param size
     * @return
     */
    List<CommentPreview> getCommentPreview(@Param("exhibitorId") Integer exhibitorId, @Param("start") Integer start, @Param("size") Integer size);

    /**
     * 获取预览展品的数量
     * @param exhibitorId
     * @return
     */
    int getCommentPreviewCount(Integer exhibitorId);

    /**
     * 获取展品的评论数量
     * @param exhibitsId
     * @return
     */
    int getCommentsCount(Integer exhibitsId);

    /**
     * 获取展品评论中评分最高的一条
     *
     * @param exhibitsId
     * @return
     */
    Comment getBestComment(int exhibitsId);

    /**
     * 获取展品的平均好评度
     * @param productId
     * @return
     */
    float getAvgGrade(int productId);

    /**
     * 获取用户对订单商品的评论
     * @param orderItemId
     * @return
     */
    Comment getCommentByOrderItemId(int orderItemId);
}

