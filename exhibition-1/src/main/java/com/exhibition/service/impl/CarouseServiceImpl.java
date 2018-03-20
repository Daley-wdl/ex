package com.exhibition.service.impl;

import com.exhibition.dao.CarouseDao;
import com.exhibition.enums.ExceptionEnums;
import com.exhibition.exceptions.ServiceException;
import com.exhibition.po.Carouse;
import com.exhibition.service.CarouseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class CarouseServiceImpl implements CarouseService {

    private static final Logger logger = Logger.getLogger(CarouseServiceImpl.class);

    @Autowired
    private CarouseDao carouseDao;

    public static final int SIZE = 6;   //首页轮播图最大数量

    private static List<Carouse> CAROUSE_CACHE = Collections.synchronizedList(new LinkedList<>());

    private static volatile long lastUpdateTime = System.currentTimeMillis();

    private static final int UPDATE_TIME_GAP = 30;//每30min更新一次轮播图缓存

    @Override
    public List<Carouse> getCarouseListForUser() {
        /*
        如果距离上次访问数据库的时间小于UPDATE_TIME_GAP分钟，则读取缓存中的轮播图
        否则从数据库中读取，并且刷新数据库
         */

        /*
        第一次读取时，缓存为空，需要从数据库读取
         */
        if (CAROUSE_CACHE.size() == 0) {
            readCarouseFromDB();
        }

        /*
        检查缓存是否需要更新
         */
        long currentTimeMillis = System.currentTimeMillis();
        if ((currentTimeMillis - lastUpdateTime) > (1000 * 60 * UPDATE_TIME_GAP)) {
            readCarouseFromDB();
        }

        /*
        返回数据
         */
        List<Carouse> carouseList = new ArrayList<>(CAROUSE_CACHE.size());
        synchronized (this.getClass()) {
            CAROUSE_CACHE.forEach(carouse -> {
                carouseList.add(carouse.ownClone());
            });
        }
        return carouseList;

    }

    /**
     * 从数据库中读取轮播图，并更新缓存和上次修改时间
     */
    private void readCarouseFromDB() {
        if ((System.currentTimeMillis() - lastUpdateTime) < (1000 * 60 * UPDATE_TIME_GAP)) {
            List<Carouse> carouseList = carouseDao.getCarouseListForUser(SIZE);
            synchronized (this.getClass()) {
                if ((System.currentTimeMillis() - lastUpdateTime) < (1000 * 60 * UPDATE_TIME_GAP)) {
                    CAROUSE_CACHE = Collections.synchronizedList(carouseList);
                    lastUpdateTime = System.currentTimeMillis();
                }
            }
            if (logger.isInfoEnabled()) {
                logger.info("更新轮播图缓存,更新时间: " + new Date(lastUpdateTime));
            }
        }
    }

    @Override
    public void add(Carouse carouse) {
        carouseDao.insert(carouse);
    }

    @Override
    public void delete(int id) {
        if (id <= 0) {
            return;
        }
        carouseDao.delete(id);
    }

    @Override
    public void update(Carouse carouse) {
        if (carouse == null || carouse.getId() == null) {
            throw new ServiceException(ExceptionEnums.LackInfo);
        }
        carouseDao.update(carouse);
    }
}
