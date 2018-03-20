package com.exhibition.controller;

import com.exhibition.constants.SessionConstants;
import com.exhibition.dto.ListDataDto;
import com.exhibition.exceptions.ServiceException;
import com.exhibition.po.OrderCanceledItem;
import com.exhibition.po.OrderItem;
import com.exhibition.service.OrderItemService;
import com.exhibition.vo.DataResult;
import com.exhibition.vo.LayuiReplay;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/orderItem")
public class OrderItemController {

    private static final Logger logger = Logger.getLogger(OrderItemController.class);

    @Autowired
    private OrderItemService orderItemService;


    /**
     * 展商获取自己被预定的展品列表
     * @param completed 订单是否完成
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/orderItems/exhibitor",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getOrderItemForExhibitor(@RequestParam("completed") boolean completed
            , @RequestParam("page") int page, @RequestParam("size") int size) {
        Gson gson = new Gson();
        Subject subject = SecurityUtils.getSubject();
        Integer userId = (Integer) subject.getSession().getAttribute(SessionConstants.USER_ID);
        try {
            ListDataDto<OrderItem> listDataDto = orderItemService.getOrderItemListForExhibitor(userId, completed, page, size);
            return gson.toJson(new DataResult<>(0, "OK", listDataDto));
        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.info(e);
            }
            if (e instanceof ServiceException) {
                return gson.toJson(new DataResult<>(1, e.getMessage(), null));
            } else {
                return gson.toJson(new DataResult<>(1, "获取列表失败，请重试", null));
            }
        }
    }

    /**
     * 根据订单id获取订单包含的商品列表
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/getOrderItemByOrderId",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getOrderItemByOrderId(@RequestParam("orderId") int orderId) {
        Gson gson = new Gson();
        Subject subject = SecurityUtils.getSubject();
        Integer userId = (Integer) subject.getSession().getAttribute(SessionConstants.USER_ID);
        try {
            List<OrderItem> orderItemList = orderItemService.getOrderItemListForUser(orderId);
            return gson.toJson(new LayuiReplay<OrderItem>(0, "OK", orderItemList.size(), orderItemList));
        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.info(e);
            }
            if (e instanceof ServiceException) {
                return gson.toJson(new DataResult<>(1, e.getMessage(), null));
            } else {
                return gson.toJson(new DataResult<>(1, "获取列表失败，请重试", null));
            }
        }
    }

    /**
     * 获取用户所取消的订单详情列表
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/orderCanceledItems/user",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getOrderCanceledItemForUser(@RequestParam("page") int page, @RequestParam("size") int size) {
        Gson gson = new Gson();
        Subject subject = SecurityUtils.getSubject();
        Integer userId = (Integer) subject.getSession().getAttribute(SessionConstants.USER_ID);
        try {
            ListDataDto<OrderCanceledItem> listDataDto = orderItemService.getOrderCanceledItemForUser(userId, page, size);
            return gson.toJson(new LayuiReplay<OrderCanceledItem>(0, "OK", listDataDto.getCount(), listDataDto.getList()));
        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.info(e);
            }
            if (e instanceof ServiceException) {
                return gson.toJson(new DataResult<>(1, e.getMessage(), null));
            } else {
                return gson.toJson(new DataResult<>(1, "获取列表失败，请重试", null));
            }
        }
    }


}
