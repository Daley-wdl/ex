package com.exhibition.service.impl;

import com.exhibition.po.Reply;
import com.exhibition.service.ReplyService;
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
 * @Description: ReplyServiceImpl的测试类
 * @Date: 2017/8/28 9:11
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ReplyServiceImplTest {

    @Autowired
    private ReplyService replyService;

    @Test
    public void queryReplyByReplyId() {

        Reply reply = replyService.queryReplyByReplyId(2);
        System.out.println("****************" + reply);
    }

    @Test
    public void insertReply() {

        Reply reply = new Reply();
        reply.setCommentId(1);
        reply.setExhibitorId(1);
        reply.setProductId(1);
        reply.setUserId(1);
        reply.setReplyDate(new Timestamp(System.currentTimeMillis()));
        reply.setStatus("0");
        reply.setReplyContent("第一次测试");
        boolean result = replyService.insertReply(reply);
        System.out.println("测试结果------------------------" + result);
    }

    @Test
    public void deleteReply() {

        boolean result = replyService.deleteReply(2);
        System.out.println("测试结果------------------------" + result);
    }

    @Test
    public void updateReply() {

        Reply reply = replyService.queryReplyByReplyId(3);
        System.out.println("--------------------" + reply);
        reply.setReplyContent("我修改了11111");
        boolean result = replyService.updateReply(reply);
        System.out.println("测试结果------------------------" + result);
    }

    @Test
    public void queryReplyListByCommentId() {

        List<Reply> r = replyService.queryReplyListByCommentId(1);
        System.out.println(r);
    }

    @Test
    public void queryReplyListByProductId() {

        List<Reply> r = replyService.queryReplyListByProductId(1,0,5);
        System.out.println(r);
    }

    @Test
    public void queryReplyListByExhibitorId() {

        List<Reply> r = replyService.queryReplyListByExhibitorId(1,0,5);
        System.out.println(r);
    }

    @Test
    public void queryReplyListByUserId() {

        List<Reply> r = replyService.queryReplyListByUserId(1,0,5);
        System.out.println(r);
    }

    @Test
    public void getCount() {

        int result = replyService.getCount();
        System.out.println("测试结果------------------------" + result);
    }

    @Test
    public void deleteManyReply() {

        List<String> list = new ArrayList<>();
        list.add("3");
        list.add("4");
        list.add("5");
        boolean result = replyService.deleteManyReply(list);
        System.out.println("*********************" + result);
    }

    @Test
    public void getReplysForUser() {
        List<Reply> replyList = replyService.getReplysForUser(21);
        replyList.forEach(System.out::println);
    }
}
