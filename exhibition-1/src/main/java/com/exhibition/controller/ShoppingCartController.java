package com.exhibition.controller;

import com.exhibition.constants.SessionConstants;
import com.exhibition.exceptions.ServiceException;
import com.exhibition.po.ShoppingCart;
import com.exhibition.service.ShoppingCartService;
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

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    private static final Logger logger = Logger.getLogger(ShoppingCartController.class);

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加商品到购物车
     * @param shoppingCart  接收ExhibitsId和BuyNumber
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/addShoppingCart",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String addShoppingCart(@Validated ShoppingCart shoppingCart, BindingResult bindingResult) {
        Gson gson = new Gson();
        if (bindingResult.hasErrors()) {
            return gson.toJson(new ReplyResult(1, "表单信息不完整"));
        }
        Subject subject = SecurityUtils.getSubject();
        Integer userId = (Integer) subject.getSession().getAttribute(SessionConstants.USER_ID);
        //未登录
        if (userId == null) {
            return gson.toJson(new ReplyResult(1, "请先登录"));
        }

        try {
            shoppingCartService.add(userId,shoppingCart.getExhibitsId(),shoppingCart.getBuyNumber());
        } catch (ServiceException e) {
            if (logger.isInfoEnabled()) {
                logger.info(e);
            }
            return gson.toJson(new ReplyResult(1, e.getMessage()));
        } catch (DataAccessException e) {
            if (logger.isInfoEnabled()) {
                logger.info(e);
            }
            logger.debug("添加购物车失败",e);
            return gson.toJson(new ReplyResult(1, "添加操作出现错误，请重试"));
        } catch (Exception e) {
            logger.debug("添加购物车失败",e);
            if (logger.isInfoEnabled()) {
                logger.info(e);
            }
            return gson.toJson(new ReplyResult(1, "添加操作出现错误，请重试"));
        }
        return gson.toJson(new ReplyResult(0, "添加成功"));
    }

    /**
     * 删除购物车所选项
     * @param shoppingCartIds   购物车id列表
     * @return
     */
    @RequestMapping(value = "/deleteShoppingCart",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String deleteShoppingCart(@RequestParam("shoppingCartIds[]") Integer[] shoppingCartIds) {
        if (logger.isInfoEnabled()) {
            logger.info(Arrays.toString(shoppingCartIds));
        }
        Gson gson = new Gson();
        if (shoppingCartIds == null || shoppingCartIds.length == 0) {
            if (logger.isInfoEnabled()) {
                logger.info("shoppingCartIds为空");
            }
            return gson.toJson(new ReplyResult(1, "选择删除项为空"));
        }
        try {
            shoppingCartService.delete(shoppingCartIds);
        } catch (ServiceException e) {
            if (logger.isInfoEnabled()) {
                logger.info(e);
            }
            return gson.toJson(new ReplyResult(1, e.getMessage()));
        } catch (DataAccessException e) {
            logger.debug("删除购物车失败",e);
            return gson.toJson(new ReplyResult(1, "删除操作发生错误，请重试"));
        } catch (Exception e) {
            logger.debug("删除购物车失败",e);
            return gson.toJson(new ReplyResult(1, "删除操作发生错误，请重试"));
        }
        return gson.toJson(new ReplyResult(0, "删除成功"));
    }

    /**
     * 获取用户的购物车列表
     * @return
     */
    @RequestMapping(value = "/getShoppingCartList",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getShoppingCartList() {
        Gson gson = new Gson();
        Subject subject = SecurityUtils.getSubject();
        Integer userId = (Integer) subject.getSession().getAttribute(SessionConstants.USER_ID);
        //未登录
        if (userId == null) {
            return gson.toJson(new LayuiReplay<ShoppingCart>(-1, "您还未登录", 0, null));
        }

        List<ShoppingCart> shoppingCartList = shoppingCartService.getShoppingCartList(userId);
        return gson.toJson(new LayuiReplay<ShoppingCart>(0, "OK", shoppingCartList.size(), shoppingCartList));
    }

    /**
     * 根据购物车id返回购物车信息
     * @param ids
     * @return
     */
    @RequestMapping(value = "/getShoppingCartListById" ,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getShoppingCartListById(@RequestParam("cartIds[]")Integer[] ids){
        Gson gson = new Gson();
        try {
//            Integer[] idArray = new Integer[ids.length];
//            for (int i = 0; i < ids.length; i++) {
//                idArray[i] = Integer.valueOf(ids[i]);
//            }
            List<ShoppingCart> cartList = shoppingCartService.getShoppingCartByIds(ids);
            return gson.toJson(new LayuiReplay<ShoppingCart>(0, "OK", cartList.size(), cartList));
        } catch (ServiceException e) {
            if (logger.isInfoEnabled()) {
                logger.info(e);
            }
            return gson.toJson(new LayuiReplay<ShoppingCart>(1, "获取列表失败，请重试", 0, null));
        } catch (Exception e) {
            logger.debug(e);
            return gson.toJson(new LayuiReplay<ShoppingCart>(1, "获取列表失败，请重试", 0, null));
        }
    }

    /**
     * 添加购物车，数量+1
     * @param id    购物车id
     * @return
     */
    @RequestMapping(value = "/addNum" ,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String addNum(@RequestParam("id")int id,@RequestParam("num")int num){
        Gson gson = new Gson();
        try {
            shoppingCartService.updateNum(id, num);
            return gson.toJson(new ReplyResult(1, "增加成功"));
        } catch (ServiceException e) {
            return gson.toJson(new ReplyResult(0, e.getMessage()));
        }  catch (Exception e) {
            logger.debug(e);
            if (logger.isInfoEnabled()) {
                logger.info(e);
            }
            return gson.toJson(new ReplyResult(0, "添加失败，请重试"));
        }
    }

}
