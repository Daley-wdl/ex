package com.exhibition.service.impl;

import com.exhibition.constants.CommentConstants;
import com.exhibition.constants.OrderConstants;
import com.exhibition.dao.CommentDao;
import com.exhibition.dao.ExhibitsDao;
import com.exhibition.dao.OrderItemDao;
import com.exhibition.dto.CommentPreviewDto;
import com.exhibition.enums.ExceptionEnums;
import com.exhibition.exceptions.ServiceException;
import com.exhibition.po.Comment;
import com.exhibition.po.CommentPreview;
import com.exhibition.service.CommentService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Since: JDK 1.8
 * @Author: ZhaoKunsong
 * @Description:
 * @Date: 2017/8/14 21:27
 **/
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class CommentServiceImpl implements CommentService{

    Logger logger = Logger.getLogger(CommentServiceImpl.class);

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private ExhibitsDao exhibitsDao;

    private final int DEFAULT_SIZE = 20;   //默认每页20条记录

    @Override
    public Comment queryCommentByCommentId(Integer id) {

        if(StringUtils.isNotEmpty(id.toString()) && id > 0) {
            return commentDao.queryCommentByCommentId(id);
        }
        else{
            return null;
        }
    }

    @Override
    public List<Comment> queryCommentList(Comment comment, Integer page, Integer size) {

        if(page == null || page <= 0) {
            page = 1;
        }
        if(size == null || size <= 0) {
            size = DEFAULT_SIZE;
        }
        Integer start = (page - 1) * size;
        return commentDao.queryCommentList(comment, start, size);

    }

    @Override
    public boolean insertComment(Comment comment) {
        if(comment == null) {
            logger.debug("传入的信息为空");
            throw new ServiceException(ExceptionEnums.LackInfo);
        }
        /**
         * 插入评论信息
         */
        comment.setCommentDate(new Timestamp(System.currentTimeMillis()));
        comment.setStatus(CommentConstants.COMMENT_STATUS_OK);
        boolean result = (commentDao.insertComment(comment) == 1);
        /*
        设置订单item的评论状态
         */
        Integer orderItemId = comment.getOrderItemId();
        orderItemDao.updateCommentStatus(orderItemId, OrderConstants.COMMENT_ALREADY);
        /*
        更新商品的总评分
         */
        Integer productId = comment.getProductId();
        float avgGrade = commentDao.getAvgGrade(productId);
        exhibitsDao.updateAvgGrade(productId,Math.round(avgGrade));

        return result;
    }

    @Override
    public boolean deleteComment(Integer id) {

        if(id <= 0) {
            logger.debug("the id is :" + id + ", id有错误");
            throw new ServiceException(ExceptionEnums.WrongId);
        }
        return (commentDao.deleteComment(id) == 1);
    }

    @Override
    public boolean updateComment(Comment comment) {

        if(comment == null) {
            logger.debug("传入的更新信息为空");
            throw new ServiceException(ExceptionEnums.LackInfo);
        }

        comment.setCommentDate(new Timestamp(System.currentTimeMillis()));
        return (commentDao.updateComment(comment) == 1);
    }

    @Override
    public List<Comment> queryCommentListByProductId(Integer productId, Integer page, Integer size) {

        if(StringUtils.isEmpty(productId.toString()) || productId <= 0) {
            //logger.debug(productId + "id 信息有错误");
            //throw new ServiceException(ExceptionEnums.WrongId);
            return null;
        }
        if(page == null || page <= 0) {
            page = 1;
        }
        if(size == null || size <= 0) {
            size = DEFAULT_SIZE;
        }
        Integer start = (page - 1) * size;
        return commentDao.queryCommentListByProductId(productId,start,size);
    }

    @Override
    public List<Comment> queryCommentListByUserId(Integer userId, Integer page, Integer size) {

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
        return commentDao.queryCommentListByUserId(userId,start,size);
    }

    @Override
    public int getCount() {
        return commentDao.getCount();
    }

    @Override
    public boolean deleteManyComment(List<String> idList) {

        if(idList == null) {
            return false;
        }
        for(String sId : idList) {
            Integer id = Integer.parseInt(sId);
            if(id <= 0) {
                return false;
            }
            commentDao.deleteComment(id);
        }
        return true;
    }

    /**
     * 获取展商评论预览信息：根据展品分组
     *
     * @param exhibitorId
     * @return
     */
    @Override
    public CommentPreviewDto getCommontPreviews(int exhibitorId,int page,int size) {
        size = Math.max(size, 20);
        int start = (Math.max(page, 1) - 1) * size;
        List<CommentPreview> commentPreviewList = commentDao.getCommentPreview(exhibitorId, start, size);
        int total = commentDao.getCommentPreviewCount(exhibitorId);
        CommentPreviewDto commentPreviewDto = new CommentPreviewDto(commentPreviewList, total);
        return commentPreviewDto;
    }

    @Override
    public int getCount(int exhibitsId) {
        int commentsCount = commentDao.getCommentsCount(exhibitsId);
        return commentsCount;
    }

    /**
     * 获取展品评论中评分最高的一条
     *
     * @param exhibitsId
     * @return
     */
    @Override
    public Comment getBestComment(int exhibitsId) {
        return commentDao.getBestComment(exhibitsId);
    }

}
