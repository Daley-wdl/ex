package com.exhibition.dao;

import com.exhibition.po.Reply;
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
 * @Description: Reply的持久层测试
 * @Date: 2017/8/26 9:39
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ReplyDaoTest {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ReplyDao replyDao;

    @Test
    public void insertComment() {
        Integer commentId = 9;
        System.out.println("开始测试------");
        Reply reply = new Reply();
        reply.setCommentId(commentId);
//        reply.setUserId(20);
        reply.setExhibitorId(22);
        reply.setProductId(10);
        reply.setReplyDate(new Timestamp(System.currentTimeMillis()));
        reply.setStatus("1");
        reply.setReplyContent("今天很冷，多穿衣服");
        int result = replyDao.insertReply(reply);
        System.out.println("测试结果------------------------" + result);
    }

    @Test
    public void deleteComment() {
        int result = replyDao.deleteReply(1);
        System.out.println("测试结果------------------------" + result);
    }

    @Test
    public void update() {
        Reply reply = replyDao.queryReplyByReplyId(3);
        System.out.println("--------------------" + reply);
        reply.setReplyContent("我修改了");
        int result = replyDao.updateReply(reply);
        System.out.println("测试结果------------------------" + result);
    }

    @Test
    public void getAll() {

    }


    @Test
    public void getCountByCommentId() {
        int count = replyDao.getCountByCommentId(9);
        System.out.println("count=" + count);
    }
}
