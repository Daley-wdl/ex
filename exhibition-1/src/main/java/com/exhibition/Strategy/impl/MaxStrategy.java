package com.exhibition.Strategy.impl;

import com.exhibition.Strategy.ShipmentAmountStrategy;
import com.exhibition.dao.ExhibitsDao;
import com.exhibition.enums.ExceptionEnums;
import com.exhibition.exceptions.ServiceException;
import com.exhibition.po.Exhibits;
import com.exhibition.po.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("SpringJavaAutowiringInspection")
@Primary
@Component
public class MaxStrategy implements ShipmentAmountStrategy {

    @Autowired
    private ExhibitsDao exhibitsDao;


    /**
     * 根据用户选择的商品计算快递费
     * 计算每家店铺中快递费最贵的一项，然后将每家店铺的快递费求和
     *
     * @param shoppingCartList
     * @return
     */
    @Override
    public int getShipmentAmount(List<ShoppingCart> shoppingCartList) {
        int shipmentAmount = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (ShoppingCart shoppingCart : shoppingCartList) {
            Integer exhibitsId = shoppingCart.getExhibitsId();
            Exhibits exhibits = exhibitsDao.getExhibitsById(exhibitsId);
            if (exhibits == null) {
                throw new ServiceException(ExceptionEnums.ExhibitsNotExists);
            }
            Integer exhibitsShipmentAmount = exhibits.getShipmentAmount();
            Integer exhibitorId = exhibits.getExhibitorId();
            if (map.containsKey(exhibitorId)) {
                Integer max = map.get(exhibitorId);
                if (max < exhibitsShipmentAmount) {
                    map.put(exhibitorId, exhibitsShipmentAmount);
                }
            } else {
                map.put(exhibitorId, exhibitsShipmentAmount);
            }
        }
        for (Integer value : map.values()) {
            shipmentAmount += value;
        }
        return shipmentAmount;
    }
}
