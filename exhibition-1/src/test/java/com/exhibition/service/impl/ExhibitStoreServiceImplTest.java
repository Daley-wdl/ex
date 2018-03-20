package com.exhibition.service.impl;

import com.exhibition.po.Exhibitstore;
import com.exhibition.service.ExhibitStoreService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by final on 17-8-1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ExhibitStoreServiceImplTest {

    @Autowired
    private ExhibitStoreService exhibitStoreService;

    @Test
    public void save() throws Exception {
        Exhibitstore exhibitstore = new Exhibitstore();
        exhibitstore.setCategory("类别未知");
        exhibitstore.setExhibitorId(3);
        exhibitstore.setExhibitsName("辣条2代");
        exhibitstore.setIntro("意大利货");
        exhibitstore.setMainPhotoPath("/static/test2.jpg");
        exhibitstore.setStatus("0");

        exhibitStoreService.save(exhibitstore);
    }

    @Test
    public void update() throws Exception {
        List<Exhibitstore> exhibitstoreList = exhibitStoreService.getALLExhibitstores("0", 0, 10);
        Exhibitstore exhibitstore = exhibitstoreList.get(0);
        exhibitstore.setExhibitsName("意大利炮");
        exhibitstore.setCategory("ss");

        exhibitStoreService.update(exhibitstore);
    }

    @Test
    public void getALLExhibitstores() throws Exception {
    }

    @Test
    public void getExhibitstoresByExhibitorName() throws Exception {
        String exhibitorName = "zhang";
        List<Exhibitstore> exhibitstoreList = exhibitStoreService.getExhibitstoresByExhibitorName(exhibitorName, 1, 10);
        for (Exhibitstore exhibitstore : exhibitstoreList) {
            System.out.println(exhibitstore);
        }
    }

    @Test
    public void searchExhibitstoresByName() throws Exception {
        String name = "辣";
        List<Exhibitstore> exhibitstoreList = exhibitStoreService.searchExhibitstoresByName(name,0,10);
        for (Exhibitstore exhibitstore : exhibitstoreList) {
            System.out.println(exhibitstore);
        }
    }

    @Test
    public void setExhibitstoreStatus() throws Exception {
        int id = 2;
        exhibitStoreService.setExhibitstoreStatus(id,"5");//不会生效，也不会抛出异常
    }

    @Test
    public void getCount() throws Exception {
        System.out.println(exhibitStoreService.getCount());
    }

    @Test
    public void getCountByStatus() throws Exception {
        String status = "01";
        System.out.println(exhibitStoreService.getCountByStatus(status));
    }

}