package com.exhibition.service.impl;

import com.exhibition.constants.OrderConstants;
import com.exhibition.dao.OrderAddressDao;
import com.exhibition.dao.OrderDao;
import com.exhibition.enums.ExceptionEnums;
import com.exhibition.exceptions.ServiceException;
import com.exhibition.po.OrderAddress;
import com.exhibition.service.OrderAddressService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
@Transactional
public class OrderAddressServiceImpl implements OrderAddressService {

    private static final Logger logger = Logger.getLogger(OrderAddressServiceImpl.class);

    @Autowired
    private OrderAddressDao orderAddressDao;

    @Autowired
    private OrderDao orderDao;

    /**
     * 获取收货地址
     *
     * @param orderAddressId
     * @return
     */
    @Override
    public OrderAddress getOrderAddress(int orderAddressId) {
        return orderAddressDao.getOrderAddress(orderAddressId);
    }

    /**
     * 获取用户的所有使用过的收货地址
     *
     * @param userId
     * @return
     */
    @Override
    public List<OrderAddress> getOrderAddressList(int userId) {
        List<OrderAddress> orderAddressList = orderAddressDao.getOrderAddressList(userId);
        return orderAddressList;
    }

    /**
     * 添加收货地址
     *
     * @param orderAddress
     */
    @Override
    public OrderAddress insert(OrderAddress orderAddress) {
        if (StringUtils.isEmpty(orderAddress.getUserZipcode())) {
            orderAddress.setUserZipcode(OrderConstants.DEFAULT_ZIPCODE);
        }
        orderAddress.setUpdateTime(orderAddress.getCreateTime());//设置更新时间为创建时间
        orderAddressDao.insert(orderAddress);
        int orderAddressId=orderAddress.getAddressId();//获取自增主键
        return orderAddressDao.getOrderAddress(orderAddressId);
    }

    @Override
    public void delete(int orderAddressId) {
        int countByOrderAddressId = orderDao.getCountByOrderAddressId(orderAddressId);
        if (countByOrderAddressId != 0) {
            throw new ServiceException(ExceptionEnums.OrderAddressIsInUse);
        }
        orderAddressDao.delete(orderAddressId);
    }

    @Override
    public void update(OrderAddress orderAddress) {
        if (orderAddress.getAddressId() == null) {
            throw new ServiceException(ExceptionEnums.LackId);
        }
        orderAddressDao.update(orderAddress);
    }
}
