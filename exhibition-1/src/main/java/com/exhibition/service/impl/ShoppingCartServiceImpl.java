package com.exhibition.service.impl;

import com.exhibition.constants.ShoppingCartContants;
import com.exhibition.dao.ExhibitsDao;
import com.exhibition.dao.ShoppingCartDao;
import com.exhibition.dao.UserDao;
import com.exhibition.enums.ExceptionEnums;
import com.exhibition.exceptions.ServiceException;
import com.exhibition.po.Exhibits;
import com.exhibition.po.ShoppingCart;
import com.exhibition.po.User;
import com.exhibition.service.ShoppingCartService;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private static final Logger logger = Logger.getLogger(ShoppingCartServiceImpl.class);

    @Autowired
    private ExhibitsDao exhibitsDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ShoppingCartDao shoppingCartDao;

    /**
     * 添加展品到购物车
     *
     * @param userId     用户id
     * @param exhibitsId 展品id
     * @param buyNum     购买数量
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public void add(Integer userId, Integer exhibitsId, Integer buyNum) {
        /*
        验证加入购物车数量
         */
        if (buyNum > ShoppingCartContants.MAX_BUYNUMBER) {
            throw new ServiceException(ExceptionEnums.SurpassMaxCount);
        }

        /*
        验证展品是否存在
         */
        Exhibits exhibits = exhibitsDao.getExhibitsById(exhibitsId);
        if (exhibits == null) {
            if (logger.isInfoEnabled()) {
                logger.info("用户添加展商到购物车时，展品不存在\n用户id" + userId + "\t展品id:" + exhibitsId);
            }
            throw new ServiceException(ExceptionEnums.ExhibitsNotExists);
        }

        /*
        验证是否已经添加过了
         */
        List<ShoppingCart> oriShoppingCart = shoppingCartDao.getShoppingCart(userId, exhibitsId);
        if (oriShoppingCart != null && oriShoppingCart.size() != 0) {
            throw new ServiceException(ExceptionEnums.ShoppingCartExist);
        }

        /*
        验证库存是否足够
         */
        String exhibitsName = exhibits.getExhibitsName();
        String picImg = exhibits.getMainPhotoPath();
        Integer price = exhibits.getPrice();

        //验证是否还有库存
        Integer number = exhibits.getNumber();
        if (number <= 0 || buyNum > number) {
            if (logger.isInfoEnabled()) {
                logger.info("添加购物车失败，展品库存库存不足:\t展品id" + exhibits.getId() + "\t展品名" + exhibitsName
                        + "\t添加数量:" + buyNum + "\t剩余数量:" + number);
            }
            throw new ServiceException(ExceptionEnums.SaleOut);
        }

        //验证总金额是否溢出
        long totalPrice = 0L;
        totalPrice = buyNum * price;
        if (totalPrice > Integer.MAX_VALUE || totalPrice <= 0) {
            logger.info("用户总金额发生溢出:" + exhibits.getId() + "\t" + exhibitsName);
            throw new ServiceException(ExceptionEnums.Overflow);
        }
        Integer productAmount = buyNum * price;//价格不会溢出

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setBuyNumber(buyNum);
        shoppingCart.setExhibitsId(exhibitsId);
        shoppingCart.setExhibitsName(exhibitsName);
        shoppingCart.setPicImg(picImg);
        shoppingCart.setProductAmount(productAmount);
        shoppingCart.setUserId(userId);
        shoppingCart.setUpdateTime(new Timestamp(System.currentTimeMillis()));

        //减库存
        Exhibits exhibitsForUpdate = new Exhibits();
        exhibitsForUpdate.setId(exhibitsId);
        exhibitsForUpdate.setNumber(exhibits.getNumber() - buyNum);
        exhibitsDao.updateExhibits(exhibitsForUpdate);

        //新增购物车
        shoppingCartDao.insert(shoppingCart);

    }

    /**
     * 删除购物车item
     *
     * @param shoppingCartIds
     */
    @Override
    public void delete(Integer[] shoppingCartIds) {
        /*
        1、还原展品库存
        2、删除购物车
         */
        List<ShoppingCart> shoppingCartList = shoppingCartDao.getShoppingCartByIds(shoppingCartIds);
        for (ShoppingCart shoppingCart : shoppingCartList) {
            Integer exhibitsId = shoppingCart.getExhibitsId();
            Integer buyNumber = shoppingCart.getBuyNumber();
            exhibitsDao.addExhibitsNumber(exhibitsId,buyNumber);
        }
        for (Integer shoppingCartId : shoppingCartIds) {
            shoppingCartDao.delete(shoppingCartId);
        }
    }

    /**
     * 返回用户的全部购物车列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<ShoppingCart> getShoppingCartList(Integer userId) {
        if (userId <= 0) {
            return Collections.EMPTY_LIST;
        }
        List<ShoppingCart> shoppingCartList = shoppingCartDao.getShoppingCartList(userId);
        if (shoppingCartList == null) {
            shoppingCartList = Collections.emptyList();
        }
        return shoppingCartList;
    }

    /**
     * 获取用户购物车内的商品数量
     * @param userId
     * @return
     */
    @Override
    public int getCount(Integer userId) {
        if (userId <= 0) {
            return 0;
        }
        return shoppingCartDao.getCount(userId);
    }

    /**
     * 根据id数组获取购物车列表
     *
     * @param shoppingCartIds
     * @return
     */
    @Override
    public List<ShoppingCart> getShoppingCartByIds(Integer[] shoppingCartIds) {
        if (shoppingCartIds == null || shoppingCartIds.length == 0) {
            return Collections.EMPTY_LIST;
        }
        return shoppingCartDao.getShoppingCartByIds(shoppingCartIds);
    }

    /**
     * 更新购物车，使id为cartId的购物车项数目+num（num可以为-）
     *
     * @param cartId
     * @param num
     */
    @Override
    public void updateNum(int cartId, int num) {
        if (num <= 0) {
            throw new ServiceException(ExceptionEnums.Error);
        }
        ShoppingCart cartDB = shoppingCartDao.getShoppingCartById(cartId);
        if (cartDB == null) {
            //查询结果为空
            throw new ServiceException(ExceptionEnums.WrongId);
        }
        Integer originNum = cartDB.getBuyNumber();
        if (num == originNum) {
            return;
        }
        int singlePrice = cartDB.getProductAmount() / originNum;

        //增加
        if (num > ShoppingCartContants.MAX_BUYNUMBER) {
            //超过添加的数量限制
            throw new ServiceException(ExceptionEnums.SurpassMaxCount);
        }
        ShoppingCart shoppingCartToUpdate = new ShoppingCart();
        shoppingCartToUpdate.setCartId(cartId);
        shoppingCartToUpdate.setBuyNumber(num);
        shoppingCartToUpdate.setProductAmount(num*singlePrice);
        shoppingCartDao.update(shoppingCartToUpdate);

    }


}
