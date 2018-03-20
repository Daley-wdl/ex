package com.exhibition.service.impl;

import com.exhibition.constants.ReplyConstants;
import com.exhibition.dao.CommentDao;
import com.exhibition.dao.ReplyDao;
import com.exhibition.enums.ExceptionEnums;
import com.exhibition.exceptions.ServiceException;
import com.exhibition.po.Comment;
import com.exhibition.po.Reply;
import com.exhibition.service.ReplyService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;


@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
@Transactional
public class ReplyServiceImpl implements ReplyService{

    Logger logger = Logger.getLogger(ReplyServiceImpl.class);

    @Autowired
    private ReplyDao replyDao;

    @Autowired
    private CommentDao commentDao;

    private final int DEFAULT_SIZE = 20;   //默认每页20条记录

    @Override
    public Reply queryReplyByReplyId(Integer id) {

        if(StringUtils.isNotEmpty(id.toString()) && id > 0) {
            return replyDao.queryReplyByReplyId(id);
        }
        else{
            return null;
        }
    }


    @Override
    public boolean insertReply(Reply reply) {

        if(reply == null) {
            if (logger.isInfoEnabled()) {
                logger.info("传入的信息为空");
            }
            throw new ServiceException(ExceptionEnums.LackInfo);
        }
        if(!checkReply(reply)) {
            if (logger.isInfoEnabled()) {
                logger.info(reply + "\n传入的信息不完整");
            }
            throw new ServiceException(ExceptionEnums.LackInfo);
        }
        //检查是否超过了回复数量限制
        int replyCount = replyDao.getCountByCommentId(reply.getCommentId());
        if (replyCount > ReplyConstants.REPLY_MAX_NUM) {
            throw new ServiceException(ExceptionEnums.SurpassMaxCount);
        }
        reply.setReplyDate(new Timestamp(System.currentTimeMillis()));

        return (replyDao.insertReply(reply) == 1);
    }

    /**
     * 用户回复
     *
     * @param reply
     */
    @Override
    public void insertReplyByUser(Reply reply,int orderItemId) {
        Comment comment = commentDao.getCommentByOrderItemId(orderItemId);
        Integer commentId = comment.getId();
        reply.setCommentId(commentId);
        insertReply(reply);
    }

    @Override
    public boolean deleteReply(Integer id) {

        if(id <= 0) {
            logger.debug("the id is :" + id + ", id有错误");
            throw new ServiceException(ExceptionEnums.WrongId);
        }
        return (replyDao.deleteReply(id) == 1);
    }

    @Override
    public boolean updateReply(Reply reply) {

        if(reply == null) {
            logger.debug("传入的更新信息为空");
            throw new ServiceException(ExceptionEnums.LackInfo);
        }
        if(!checkReply(reply)) {
            logger.debug(reply + "传入的更新信息不完整");
            throw new ServiceException(ExceptionEnums.LackInfo);
        }
        reply.setReplyDate(new Timestamp(System.currentTimeMillis()));
        return (replyDao.updateReply(reply) == 1);
    }

    @Override
    public List<Reply> queryReplyListByCommentId(Integer commentId) {

        if (commentId == null) {
            if (logger.isDebugEnabled()) {
                logger.debug(commentId + "id 信息有错误");
            }
            throw new ServiceException(ExceptionEnums.WrongId);
        }
        return replyDao.queryReplyListByCommentId(commentId);
    }

    @Override
    public List<Reply> queryReplyListByProductId(Integer productId, Integer page, Integer size) {

        if(StringUtils.isEmpty(productId.toString()) || productId <= 0) {
            logger.debug(productId + "id 信息有错误");
            throw new ServiceException(ExceptionEnums.WrongId);
        }
        if(page == null || page <= 0) {
            page = 1;
        }
        if(size == null || size <= 0) {
            size = DEFAULT_SIZE;
        }
        Integer start = (page - 1) * size;
        return replyDao.queryReplyListByProductId(productId,start,size);
    }

    @Override
    public List<Reply> queryReplyListByExhibitorId(Integer exhibitorId, Integer page, Integer size) {

        if(StringUtils.isEmpty(exhibitorId.toString()) || exhibitorId <= 0) {
            logger.debug(exhibitorId + "id 信息有错误");
            throw new ServiceException(ExceptionEnums.WrongId);
        }
        if(page == null || page <= 0) {
            page = 1;
        }
        if(size == null || size <= 0) {
            size = DEFAULT_SIZE;
        }
        Integer start = (page - 1) * size;
        return replyDao.queryReplyListByExhibitorId(exhibitorId,start,size);
    }

    @Override
    public List<Reply> queryReplyListByUserId(Integer userId, Integer page, Integer size) {

        if(StringUtils.isEmpty(userId.toString()) || userId <= 0) {
            logger.debug(userId + "id 信息有错误");
            throw new ServiceException(ExceptionEnums.WrongId);
        }
        if(page == null || page <= 0) {
            page = 1;
        }
        if(size == null || size <= 0) {
            size = DEFAULT_SIZE;
        }
        Integer start = (page - 1) * size;
        return replyDao.queryReplyListByUserId(userId,start,size);

    }

    @Override
    public int getCount() {

        return replyDao.getCount();
    }

    @Override
    public boolean deleteManyReply(List<String> idList) {

        if(idList == null) {
            return false;
        }
        for(String sId : idList) {
            Integer id = Integer.parseInt(sId);
            if(id <= 0) {
                return false;
            }
            replyDao.deleteReply(id);
        }
        return true;
    }

    /**
     * 获取对订单详情项的回复
     *
     * @param orderItemId
     * @return
     */
    @Override
    public List<Reply> getReplysForUser(int orderItemId) {
        LinkedList<Reply> replies = new LinkedList<>();
        Comment comment = commentDao.getCommentByOrderItemId(orderItemId);
        Integer commentId = comment.getId();
        List<Reply> replyList = replyDao.queryReplyListByCommentId(commentId);
        replies.addAll(replyList);
        //将评论信息加入到回复中
        Reply reply = new Reply();
        reply.setReplyDate(comment.getCommentDate());
        reply.setUserId(comment.getUserId());
        reply.setProductId(comment.getProductId());
        reply.setReplyContent(comment.getCommentContent());
        reply.setPhotoPath(comment.getPhotoPath());
        replies.addFirst(reply);
        return replies;
    }


    /**
     * 审查reply的必须字段是否为空
     * @param reply
     * @return
     */
    private boolean checkReply(Reply reply) {
        if(StringUtils.isEmpty(reply.getReplyContent())) {
            return false;
        }
        if(reply.getCommentId()==null||reply.getCommentId()<=0) {
            return false;
        }
        return true;
    }
}
