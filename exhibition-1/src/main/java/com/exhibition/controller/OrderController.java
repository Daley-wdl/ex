package com.exhibition.controller;

import com.exhibition.constants.SessionConstants;
import com.exhibition.dto.ListDataDto;
import com.exhibition.exceptions.ServiceException;
import com.exhibition.po.Order;
import com.exhibition.po.OrderCanceledItem;
import com.exhibition.po.OrderItem;
import com.exhibition.po.ShoppingCart;
import com.exhibition.service.OrderItemService;
import com.exhibition.service.OrderService;
import com.exhibition.service.ShoppingCartService;
import com.exhibition.utils.ShipmentAmountCounter;
import com.exhibition.vo.*;
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

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    private static final Logger logger = Logger.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ShipmentAmountCounter shipmentAmountCounter;

    @Autowired
    private OrderItemService orderItemService;

    /**
     * 获取基础邮费
     * @return
     */
    @RequestMapping(value = "/getBaseShipmentPrice" ,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getBaseShipmentPrice(@RequestParam("shippingCartIds[]")Integer[] shippingCartIds){
        Gson gson = new Gson();
        List<ShoppingCart> shoppingCartList = shoppingCartService.getShoppingCartByIds(shippingCartIds);
        int shipmentAmount = 0;
        try {
            shipmentAmount = shipmentAmountCounter.getShipmentAmount(shoppingCartList);
        } catch (Exception e) {
            logger.debug("获取快递费失败",e);
        }
        return gson.toJson(new DataResult<Integer>(0,"OK" , shipmentAmount));
    }

    @RequestMapping(value = "/addOrder",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String addOrder(@Validated Order order, BindingResult bindingResult, @RequestParam("shoppingCartIds[]")Integer[] shoppingCartIds) {
        if (logger.isInfoEnabled()) {
            logger.info(Arrays.toString(shoppingCartIds)+"\n"+order);
        }
        Gson gson = new Gson();
        if (shoppingCartIds == null || shoppingCartIds.length == 0 || bindingResult.hasErrors()) {
            return gson.toJson(new ReplyResult(1, "订单信息不完整"));
        }
        Subject subject = SecurityUtils.getSubject();
        Integer userId = (Integer) subject.getSession().getAttribute(SessionConstants.USER_ID);

        try {
            Order orderDB = orderService.addOrder(userId, shoppingCartIds, order.getOrderAddressId(), order.getPayType(), order.getShipmentType());
            return gson.toJson(new DataResult<Order>(0,"OK",order));
        } catch (ServiceException e) {
            if (logger.isInfoEnabled()) {
                logger.info(e);
            }
            return gson.toJson(new ReplyResult(1, e.getMessage()));
        } catch (DataAccessException e) {
            logger.debug("生成订单失败",e);
            return gson.toJson(new ReplyResult(1, "生成订单失败，请重试"));
        } catch (Exception e) {
            logger.debug("生成订单失败",e);
            return gson.toJson(new ReplyResult(1, "生成订单失败，请重试"));
        }
    }

    @RequestMapping(value = "/cancelOrder",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String cancelOrder(@RequestParam("orderId") int orderId) {
        Gson gson = new Gson();
        Subject subject = SecurityUtils.getSubject();
        Integer userId = (Integer) subject.getSession().getAttribute(SessionConstants.USER_ID);

        try {
            orderService.cancelOrder(orderId);
            logger.debug(String.format("用户取消订单:\t用户id为%d\t订单id:%d", userId, orderId));
            return gson.toJson(new ReplyResult(0, "取消订单成功"));
        } catch (ServiceException e) {
            if (logger.isInfoEnabled()) {
                logger.info(e);
            }
            return gson.toJson(new ReplyResult(1, e.getMessage()));
        } catch (DataAccessException e) {
            logger.debug("取消订单失败",e);
            return gson.toJson(new ReplyResult(1, "取消订单失败，请重试"));
        } catch (Exception e) {
            logger.debug("取消订单失败",e);
            return gson.toJson(new ReplyResult(1, "取消订单失败，请重试"));
        }
    }

    /**
     * 获取用户的订单信息
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/getOrdersForUser",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getOrders(@RequestParam("page") int page,@RequestParam("size") int size) {
        Gson gson = new Gson();
        Subject subject = SecurityUtils.getSubject();
        Integer userId = (Integer) subject.getSession().getAttribute(SessionConstants.USER_ID);

        try {
            List<OrderInfoForUser> orderInfo = orderService.getOrderInfoForUser(userId, page, size);
            return gson.toJson(new DataResult<List<OrderInfoForUser>>(0,"OK",orderInfo));
        } catch (ServiceException e) {
            if (logger.isInfoEnabled()) {
                logger.info(e);
            }
            return gson.toJson(new LayuiReplay<Order>(1, e.getMessage(),0,null));
        } catch (DataAccessException e) {
            logger.debug("获取订单列表失败",e);
            return gson.toJson(new LayuiReplay<Order>(1, "获取订单列表失败，请重试",0,null));
        } catch (Exception e) {
            logger.debug("获取订单列表失败",e);
            return gson.toJson(new LayuiReplay<Order>(1, "获取订单列表失败，请重试",0,null));
        }
    }

    @RequestMapping(value = "/getOrdersForExhibitor" ,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getOrdersForExhibitor(@RequestParam("canceled")boolean canceled,
                                        @RequestParam("page")int page,@RequestParam("size")int size){
        Gson gson = new Gson();
        Subject subject = SecurityUtils.getSubject();
        Integer userId = (Integer) subject.getSession().getAttribute(SessionConstants.USER_ID);

        try {
            ListDataDto<OrderForExhibitorVo> orderListDataDto = orderService.getOrdersForExhibitor(userId, canceled, page, size);
            return gson.toJson(new LayuiReplay<OrderForExhibitorVo>(0, "OK", orderListDataDto.getCount(), orderListDataDto.getList()));
        } catch (Exception e) {
            logger.debug(e);
            return gson.toJson(new LayuiReplay<OrderForExhibitorVo>(1, "获取订单列表失败", 0, null));
        }
    }

    /**
     * 查询订单中指定展商的订单详情
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/getOrderItemsForExhibitor" ,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getOrderItemsForExhibitor(@RequestParam("orderId")int orderId){
        Gson gson = new Gson();
        Subject subject = SecurityUtils.getSubject();
        Integer userId = (Integer) subject.getSession().getAttribute(SessionConstants.USER_ID);
        try {
            List<OrderItem> orderItemList = orderService.getOrderItemsForExhibitor(orderId, userId);
            int totalAmount = 0;
            for (OrderItem orderItem : orderItemList) {
                totalAmount += orderItem.getProductAmount();
            }
            return gson.toJson(new DataResult<List<OrderItem>>(0, String.valueOf(totalAmount), orderItemList));
        } catch (Exception e) {
            logger.error(e);
            return gson.toJson(new DataResult<List<OrderItem>>(1, "获取订单详情信息失败,请重试", null));
        }

    }

    @RequestMapping(value = "/getCanceledOrderItemForExhibitor" ,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getCanceledOrderItemForExhibitor(@RequestParam("orderId")int orderId){
        Gson gson = new Gson();
        Subject subject = SecurityUtils.getSubject();
        Integer userId = (Integer) subject.getSession().getAttribute(SessionConstants.USER_ID);
        try {
            List<OrderCanceledItem> canceledItemList = orderService.getCanceledOrderItemsForExhibitor(orderId, userId);
            return gson.toJson(new DataResult<List<OrderCanceledItem>>(0, "OK", canceledItemList));
        } catch (Exception e) {
            logger.debug(e);
            return gson.toJson(new DataResult<List<OrderItem>>(1, "获取订单详情信息失败,请重试", null));
        }
    }


    @RequestMapping(value = "/setPayOffByExhibitor" ,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String setPayOffByExhibitor(int orderId){
        Gson gson = new Gson();
        Subject subject = SecurityUtils.getSubject();
        Integer userId = (Integer) subject.getSession().getAttribute(SessionConstants.USER_ID);
        try {
            orderService.updateIsPayOffByExhibitor(userId, orderId);
            return gson.toJson(new ReplyResult(0, "修改成功"));
        } catch (Exception e) {
            logger.debug(e);
            return gson.toJson(new ReplyResult(1, "修改失败，请重试"));
        }
    }

    @RequestMapping(value = "/setShipmentId" ,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String setShipmentId(@RequestParam("shipmentId")String shipmentId,@RequestParam("orderId")int orderId){
        Gson gson = new Gson();
        Subject subject = SecurityUtils.getSubject();
        Integer userId = (Integer) subject.getSession().getAttribute(SessionConstants.USER_ID);
        try {
            orderItemService.setShipmentId(shipmentId,userId,orderId);
            return gson.toJson(new ReplyResult(0, "设置成功"));
        } catch (Exception e) {
            logger.debug(e);
            return gson.toJson(new ReplyResult(1, "设置失败，请重试"));
        }
    }
}
