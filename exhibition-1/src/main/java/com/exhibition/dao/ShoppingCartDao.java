package com.exhibition.dao;

import com.exhibition.po.ShoppingCart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShoppingCartDao {

    void insert(ShoppingCart shoppingCart);

    void delete(int id);

    void deletByBatch(List<Integer> list);

    void update(ShoppingCart shoppingCart);

    /**
     * 返回用户的全部购物车列表
     *
     * @param userId
     * @return
     */
    List<ShoppingCart> getShoppingCartList(Integer userId);

    ShoppingCart getShoppingCartById(int id);

    /**
     * 获取用户购物车内的商品数量
     *
     * @param userId
     * @return
     */
    int getCount(Integer userId);

    /**
     * 根据id数组获取购物车列表
     *
     * @param shoppingCartIds
     * @return
     */
    List<ShoppingCart> getShoppingCartByIds(Integer[] shoppingCartIds);

    /**
     * 获取指定用户、指定展品的购物车项
     * @param userId
     * @param exhibitsId
     * @return
     */
    List<ShoppingCart> getShoppingCart(@Param("userId") int userId, @Param("exhibitsId") int exhibitsId);
}
