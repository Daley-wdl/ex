package com.exhibition.service;

import com.exhibition.po.Reply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Since: JDK 1.8
 * @Author: ZhaoKunsong
 * @Description: Reply的Service接口
 * @Date: 2017/8/27 10:15
 **/
public interface ReplyService {

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
    public boolean insertReply(Reply reply);

    /**
     * 用户回复
     * @param reply
     */
    void insertReplyByUser(Reply reply,int orderItemId);

    /**
     * 删除反馈
     * @param id
     * @return int
     */
    public boolean deleteReply(Integer id);

    /**
     * 更新反馈
     * @param reply
     * @return int
     */
    public boolean updateReply(Reply reply);

    /**
     * 根据评论Id查询
     * @param commentId
     * @return
     */
    public List<Reply> queryReplyListByCommentId(Integer commentId);

    /**
     * 根据展品Id查询
     * @param productId
     * @param page
     * @param size
     * @return
     */
    public List<Reply> queryReplyListByProductId(Integer productId, Integer page, Integer size);

    /**
     * 根据展商Id查询
     * @param exhibitorId
     * @param page
     * @param size
     * @return
     */
    public List<Reply> queryReplyListByExhibitorId(Integer exhibitorId, Integer page, Integer size);

    /**
     * 根据用户id查询
     * @param userId
     * @param page
     * @param size
     * @return
     */
    public List<Reply> queryReplyListByUserId(Integer userId, Integer page, Integer size);

    /**
     * 获取总数
     * @return int
     */
    public int getCount();

    /**
     * 批量删除
     * @param idList
     * @return
     */
    public boolean deleteManyReply(List<String> idList);

    /**
     * 获取对订单详情项的回复
     * @param orderItemId
     * @return
     */
    List<Reply> getReplysForUser(int orderItemId);

}
