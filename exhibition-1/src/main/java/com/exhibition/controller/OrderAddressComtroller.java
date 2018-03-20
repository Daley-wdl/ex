package com.exhibition.controller;

import com.exhibition.constants.OrderConstants;
import com.exhibition.constants.SessionConstants;
import com.exhibition.exceptions.ServiceException;
import com.exhibition.po.OrderAddress;
import com.exhibition.service.OrderAddressService;
import com.exhibition.vo.DataResult;
import com.exhibition.vo.LayuiReplay;
import com.exhibition.vo.ReplyResult;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/orderAddress")
public class OrderAddressComtroller {

    private static final Logger logger = Logger.getLogger(OrderAddressComtroller.class);

    @Autowired
    private OrderAddressService orderAddressService;

    @RequestMapping(value = "/addOrderAddress",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String addOrderAddress(@Validated OrderAddress orderAddress, BindingResult bindingResult) {
        Gson gson = new Gson();

        /*
        提交的表单信息不全
         */
        if (bindingResult.hasErrors()) {
            return gson.toJson(new ReplyResult(1, "填写信息有误"));
        }

        Subject subject = SecurityUtils.getSubject();
        Integer userId = (Integer) subject.getSession().getAttribute(SessionConstants.USER_ID);
        orderAddress.setUserId(userId);
        try {
            orderAddressService.insert(orderAddress);
        } catch (ServiceException e) {
            if (logger.isInfoEnabled()) {
                logger.info(e);
            }
            return gson.toJson(new ReplyResult(1, e.getMessage()));
        } catch (DataAccessException e) {
            logger.debug("添加订单收货地址失败",e);
            return gson.toJson(new ReplyResult(1, "添加订单收货地址失败，请重试"));
        } catch (Exception e) {
            logger.debug("添加订单收货地址失败",e);
            return gson.toJson(new ReplyResult(1, "添加订单收货地址失败，请重试"));
        }
        return gson.toJson(new DataResult<OrderAddress>(0, "添加订单收货地址成功", orderAddress));
//        return gson.toJson(new ReplyResult(0, "添加订单收货地址成功"));
    }

    @RequestMapping(value = "/deleteOrderAddress",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String deleteOrderAddress(@RequestParam("orderAddressId") int orderAddressId){
        Gson gson = new Gson();
        Subject subject = SecurityUtils.getSubject();
        Integer userId = (Integer) subject.getSession().getAttribute(SessionConstants.USER_ID);
        OrderAddress orderAddress = orderAddressService.getOrderAddress(orderAddressId);
        if (orderAddress == null || !orderAddress.getUserId().equals(userId)) {
            return gson.toJson(new ReplyResult(1, "该地址不存在"));
        }
        try {
            orderAddressService.delete(orderAddressId);
            return gson.toJson(new ReplyResult(0, "OK"));
        } catch (ServiceException e) {
            if (logger.isInfoEnabled()) {
                logger.info(e);
            }
            return gson.toJson(new ReplyResult(1, e.getMessage()));
        } catch (DataAccessException e) {
            logger.debug("删除订单收货地址失败",e);
            return gson.toJson(new ReplyResult(1, "删除订单收货地址失败，请重试"));
        } catch (Exception e) {
            logger.debug("删除订单收货地址失败",e);
            return gson.toJson(new ReplyResult(1, "删除订单收货地址失败，请重试"));
        }
    }

    @RequestMapping(value = "/getOrderAddressList",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getOrderAddressList() {
        Gson gson = new Gson();
        Subject subject = SecurityUtils.getSubject();
        Integer userId = (Integer) subject.getSession().getAttribute(SessionConstants.USER_ID);
        try {
            List<OrderAddress> orderAddressList = orderAddressService.getOrderAddressList(userId);
            return gson.toJson(new LayuiReplay<OrderAddress>(0, "OK", orderAddressList.size(), orderAddressList));
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.debug(e);
            }
            return gson.toJson(new LayuiReplay<OrderAddress>(1, "获取地址失败，请重试", 0, null));
        }

    }

}
