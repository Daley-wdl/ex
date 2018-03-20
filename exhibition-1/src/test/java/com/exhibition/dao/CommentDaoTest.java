package com.exhibition.dao;

import com.exhibition.po.Comment;
import com.exhibition.po.CommentPreview;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.List;


/**
 * @Since: JDK 1.8
 * @Author: ZhaoKunsong
 * @Description: Comment的持久层测试
 * @Date: 2017/8/26 9:39
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class CommentDaoTest {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private CommentDao commentDao;

    static int userId = 22;
    static int productId = 10;

    @Test
    public void insertComment() {
        productId = 11;
        System.out.println("开始测试------");
        Comment comment = new Comment();
        comment.setCommentDate(new Timestamp(System.currentTimeMillis()));
        comment.setCommentContent("冯宝宝3！！！");
        comment.setUserId(userId);
        comment.setProductId(productId);
        comment.setStatus("0");
        comment.setProductGrade(2);
        System.out.println(comment);
        int result = commentDao.insertComment(comment);
        System.out.println("测试结果------------------------" + result);
    }

    @Test
    public void deleteComment() {
        int result = commentDao.deleteComment(2);
        System.out.println("测试结果------------------------" + result);
    }

    @Test
    public void update() {
        Comment comment = commentDao.queryCommentByCommentId(3);
        System.out.println("--------------------" + comment);
        comment.setCommentContent("修改后的样子");
        int result = commentDao.updateComment(comment);
        System.out.println("测试结果------------------------" + result);
    }

    @Test
    public void getAll() {
        List<Comment> comment1 = commentDao.queryCommentListByProductId(1,0,5);
        System.out.println(comment1);
        List<Comment> comment2 = commentDao.queryCommentListByUserId(1,0,5);
        System.out.println(comment2);
        int result = commentDao.getCount();
        System.out.println("测试结果------------------------" + result);
    }

    @Test
    public void select() {
        Comment comment = new Comment();
        comment.setCommentContent("第一次");
        List<Comment> list = commentDao.queryCommentList(null,0,5);
        System.out.println(list);
    }

    @Test
    public void getCommonsGroup() {
        Integer exhibitsId = 1;
        List<Comment> comments = commentDao.getCommentsByExhibitsId(exhibitsId, 0, 10);
        comments.forEach((item)->{
            System.out.println(item);
        });
    }

    @Test
    public void getCommontsPreview() {

        List<CommentPreview> commentPreviewList = commentDao.getCommentPreview(userId, 0, 10);
        commentPreviewList.forEach(commentPreview -> System.out.println(commentPreview));
    }

    @Test
    public void getCommentPreviewCount() {
        userId = 1;
        int commentPreviewCount = commentDao.getCommentPreviewCount(userId);
        System.out.println(commentPreviewCount);
    }

    @Test
    public void getCommentsByProductId() {
        List<Comment> comments = commentDao.queryCommentListByProductId(productId, 0, 10);
        comments.forEach(comment -> System.out.println(comment));
    }

    @Test
    public void getCommentsCount() {
        int commentsCount = commentDao.getCommentsCount(1);
        System.out.println("展品评论数:" + commentsCount);
    }

    @Test
    public void getAvgGrade(){
        float avgGrade = commentDao.getAvgGrade(11);
        System.out.println(avgGrade);
    }
}
