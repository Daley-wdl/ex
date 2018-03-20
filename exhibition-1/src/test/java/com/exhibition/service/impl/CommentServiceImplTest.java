package com.exhibition.service.impl;

import com.exhibition.dto.CommentPreviewDto;
import com.exhibition.po.Comment;
import com.exhibition.po.CommentPreview;
import com.exhibition.service.CommentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @Since: JDK 1.8
 * @Author: ZhaoKunsong
 * @Description: CommentServiceImpl的测试类
 * @Date: 2017/8/28 9:11
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class CommentServiceImplTest {

    @Autowired
    private CommentService commentService;

    @Test
    public void queryCommentByCommentIdTest() {

        Comment comment = commentService.queryCommentByCommentId(3);
        System.out.println("**************" + comment);
    }

    @Test
    public void queryCommentListTest(){

        Comment comment = new Comment();
        List<Comment> list = commentService.queryCommentList(null,1,2);
        System.out.println(list);
    }

    @Test
    public void insertCommentTest() {

        Comment comment = new Comment();
        comment.setCommentDate(new Timestamp(System.currentTimeMillis()));
        comment.setCommentContent("第二次测试");
        comment.setUserId(1);
        comment.setProductId(1);
        comment.setStatus("0");
        boolean result = commentService.insertComment(comment);
        System.out.println("******************" + result);
    }

    @Test
    public void deleteCommentTest() {

        boolean result = commentService.deleteComment(3);
        System.out.println("******************" + result);

    }

    @Test
    public void updateCommentTest() {

        Comment comment = commentService.queryCommentByCommentId(4);
        System.out.println("--------------------" + comment);
        comment.setCommentContent("修改后的样子222");
        boolean result = commentService.updateComment(comment);
        System.out.println("测试结果------------------------" + result);
    }

    @Test
    public void queryCommentListByProductIdTest() {

        List<Comment> comment = commentService.queryCommentListByProductId(1,0,5);
        System.out.println(comment);
    }

    @Test
    public void queryCommentListByUserIdTest() {

        List<Comment> comment = commentService.queryCommentListByUserId(1,0,5);
        System.out.println(comment);
    }

    @Test
    public void getCountTest() {

        int result = commentService.getCount();
        System.out.println("测试结果------------------------" + result);
    }

    @Test
    public void deleteManyCommentTest() {

        List<String> list = new ArrayList<>();
        list.add("5");
        list.add("4");
        boolean result = commentService.deleteManyComment(list);
        System.out.println("************************" + result);
    }

    @Test
    public void getCommentPreview() {
        int exhibitorId = 22;
        CommentPreviewDto commontPreviews = commentService.getCommontPreviews(exhibitorId, 1, 10);
        List<CommentPreview> commentPreviewList = commontPreviews.getCommentPreviewList();
        commentPreviewList.forEach(commentPreview -> System.out.println(commentPreview));
        System.out.println(commontPreviews.getTotalCount());
    }
}
