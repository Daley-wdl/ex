package com.exhibition.dao;

import com.exhibition.po.Reply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Since: JDK 1.8
 * @Author: ZhaoKunsong
 * @Description: 反馈Dao接口
 * @Date: 2017/8/20 20:05
 **/
public interface ReplyDao {

    /**
     * 根据replyId查询
     * @param id
     * @return Reply
     */
    public Reply queryReplyByReplyId(Integer id);


    /**
     * 新增反馈
     * @param reply
     * @return int
     */
    public int insertReply(Reply reply);

    /**
     * 删除反馈
     * @param id
     * @return int
     */
    public int deleteReply(Integer id);

    /**
     * 更新反馈
     * @param reply
     * @return int
     */
    public int updateReply(Reply reply);

    /**
     * 根据评论Id查询
     * @param commentId
     * @return
     */
    public List<Reply> queryReplyListByCommentId(Integer commentId);

    /**
     * 根据展品Id查询
     * @param productId
     * @param start
     * @param size
     * @return
     */
    public List<Reply> queryReplyListByProductId(@Param("productId") Integer productId, @Param("start") Integer start, @Param("size") Integer size);

    /**
     *
     * @param exhibitorId
     * @param start
     * @param size
     * @return
     */
    public List<Reply> queryReplyListByExhibitorId(@Param("exhibitorId") Integer exhibitorId, @Param("start") Integer start, @Param("size") Integer size);

    /**
     * 根据用户id查询
     * @param userId
     * @param start
     * @param size
     * @return
     */
    public List<Reply> queryReplyListByUserId(@Param("userId") Integer userId, @Param("start") Integer start, @Param("size") Integer size);

    /**
     * 获取总数
     * @return int
     */
    public int getCount();

    /**
     * 获取评论的总回复数量
     * @param commentId
     * @return
     */
    int getCountByCommentId(Integer commentId);
}
