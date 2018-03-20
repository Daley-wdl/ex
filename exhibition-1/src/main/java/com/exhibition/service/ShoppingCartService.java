package com.exhibition.service;

import com.exhibition.po.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    /**
     * 添加展品到购物车
     * @param userId    用户id
     * @param exhibitsId    展品id
     * @param buyNum    购买数量
     */
    void add(Integer userId, Integer exhibitsId, Integer buyNum);

    /**
     * 删除购物车item
     * @param shoppingCartIds
     */
    void delete(Integer[] shoppingCartIds);

    /**
     * 返回用户的全部购物车列表
     * @param userId
     * @return
     */
    List<ShoppingCart> getShoppingCartList(Integer userId);

    /**
     * 获取用户购物车内的商品数量
     * @param userId
     * @return
     */
    int getCount(Integer userId);

    /**
     * 根据id数组获取购物车列表
     * @param shoppingCartIds
     * @return
     */
    List<ShoppingCart> getShoppingCartByIds(Integer[] shoppingCartIds);

    /**
     * 更新购物车，使id为cartId的购物车项数目+num（num可以为-）
     * @param cartId
     * @param num
     */
    void updateNum(int cartId, int num);


}
