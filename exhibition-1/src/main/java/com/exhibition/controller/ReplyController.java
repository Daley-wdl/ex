package com.exhibition.controller;

import com.exhibition.enums.RoleList;
import com.exhibition.exceptions.ServiceException;
import com.exhibition.po.Comment;
import com.exhibition.po.Reply;
import com.exhibition.service.CommentService;
import com.exhibition.service.ReplyService;
import com.exhibition.vo.DataResult;
import com.exhibition.vo.FeedbackJson;
import com.exhibition.vo.ReplyList;
import com.exhibition.vo.ReplyResult;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/reply")
public class ReplyController {

    private static final Logger logger = Logger.getLogger(ReplyController.class);

    @Autowired
    private ReplyService replyService;

    @Autowired
    private CommentService commentService;

    /**
     * <p>展商增加反馈</p>
     * @param reply 封装的新增反馈信息对象
     * @return Json字符串，包括：详细信息、状态码、原因
     */
    @RequestMapping(value = "/addReply/{type}",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String addReplyByExhibitor(Reply reply, @PathVariable("type")String type
            ,@RequestParam(value = "orderItemId",required = false) int orderItemId) {
        Gson gson = new Gson();
        Subject subject = SecurityUtils.getSubject();
        Integer userId = (Integer) subject.getSession().getAttribute("userId");
        try {
            if ("exhibitor".equals(type)) {
                //展商回复，userId为null
                reply.setExhibitorId(userId);
                reply.setUserId(null);
                replyService.insertReply(reply);
            } else if ("user".equals(type)) {
                //用户回复，exhibitorId为null
                reply.setUserId(userId);
                reply.setExhibitorId(null);
                replyService.insertReplyByUser(reply,orderItemId);
            } else {
                return gson.toJson(new ReplyResult(0, "请求路径错误!"));
            }

            return gson.toJson(new ReplyResult(1, "回复成功!"));
        } catch (ServiceException e) {
            if (logger.isInfoEnabled()) {
                logger.info(e);
            }
            return gson.toJson(new ReplyResult(0, e.getMessage()));
        } catch (Exception e) {
            if (logger.isInfoEnabled() || logger.isDebugEnabled()) {
                logger.debug(e.getMessage());
            }
            return gson.toJson(new ReplyResult(0, "Error"));
        }
    }

    /**
     * 获取评论的回复列表
     * @param commentId
     * @return
     */
    @RequestMapping(value = "/getReplysByCommentId",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getReplysByCommentId(@RequestParam("commentId") Integer commentId) {
        Gson gson = new Gson();
        try {
            List<Reply> replyList = replyService.queryReplyListByCommentId(commentId);
            Comment comment = commentService.queryCommentByCommentId(commentId);
            Reply reply = new Reply();
            reply.setCommentId(commentId);
            reply.setUserId(comment.getUserId());
            reply.setReplyDate(comment.getCommentDate());
            reply.setReplyContent(comment.getCommentContent());
            replyList.add(0,reply);
            String json = gson.toJson(new ReplyList<Reply>(replyList, replyList.size()));
            return json;
        } catch (ServiceException e) {
            return gson.toJson(new ReplyList<Reply>(null,0));
        }
    }

    @RequestMapping(value = "/getReplysByOrderItemId" ,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getReplysByOrderItemId(@RequestParam("orderItemId")int orderItemId){
        Gson gson = new Gson();
        try {
            List<Reply> replyList = replyService.getReplysForUser(orderItemId);
            return gson.toJson(replyList);
        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.info(e);
            }
            logger.debug(e);
            return gson.toJson(Collections.emptyList());
        }
    }


    /**
     * <p>编辑反馈信息</p>
     * @param reply 封装的修改后的反馈信息对象
     * @return Json字符串，包括：详细信息、状态码、原因
     */
    @RequestMapping(value = "/editReply")
    @ResponseBody
    public String editReply(Reply reply) {

        Gson gson = new Gson();
        FeedbackJson feedbackJson = new FeedbackJson();

        if(reply == null) {
            feedbackJson.setReason("The parameter is null");
            feedbackJson.setError("1");
            return gson.toJson(feedbackJson);
        }
        try {
            boolean result = replyService.updateReply(reply);
            if(!result) {
                feedbackJson.setReason("edit reply defeated");
                feedbackJson.setError("1");
            }
            else {
                feedbackJson.setReason("success");
                feedbackJson.setError("0");
            }
            return gson.toJson(feedbackJson);
        }
        catch (ServiceException se) {
            feedbackJson.setReason("please insure the information completely");
            feedbackJson.setError("1");
            return gson.toJson(feedbackJson);
        }

    }

    /**
     * <p>删除单条反馈信息</p>
     * @param replyId
     * @return Json字符串，包括：详细信息、状态码、原因
     */
    @RequestMapping(value = "/deleteReply")
    @ResponseBody
    public String deleteReply(Integer replyId) {

        Gson gson = new Gson();
        FeedbackJson feedbackJson = new FeedbackJson();

        if(replyId == null) {
            feedbackJson.setReason("The parameter is null");
            feedbackJson.setError("1");
            return gson.toJson(feedbackJson);
        }

        try {
            boolean result = replyService.deleteReply(replyId);
            if(!result) {
                feedbackJson.setReason("delete reply defeated");
                feedbackJson.setError("1");
            }
            else {
                feedbackJson.setReason("success");
                feedbackJson.setError("0");
            }
            return gson.toJson(feedbackJson);

        }
        catch (ServiceException se) {
            feedbackJson.setReason("please insure the id is true");
            feedbackJson.setError("1");
            return gson.toJson(feedbackJson);
        }
    }

    /**
     * <p>删除多条反馈信息</p>
     * @param idList
     * @return Json字符串，包括：详细信息、状态码、原因
     */
    @RequestMapping(value = "/deleteManyReplies")
    @ResponseBody
    public String deleteManyReplies(List idList) {

        Gson gson = new Gson();
        FeedbackJson feedbackJson = new FeedbackJson();

        if(idList.isEmpty()) {
            feedbackJson.setReason("The parameter is null");
            feedbackJson.setError("1");
        }
        else {
            boolean result = replyService.deleteManyReply(idList);
            if (!result) {
                feedbackJson.setReason("delete many replies defeated");
                feedbackJson.setError("1");
            }
            else {
                feedbackJson.setReason("success");
                feedbackJson.setError("0");
            }
        }
        return gson.toJson(feedbackJson);
    }

}
