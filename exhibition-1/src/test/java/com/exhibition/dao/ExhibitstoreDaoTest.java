package com.exhibition.dao;

import com.exhibition.po.Exhibitstore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by final on 17-7-29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ExhibitstoreDaoTest {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ExhibitstoreDao exhibitstoreDao;

    @Test
    public void addExhibits() throws Exception {
        Exhibitstore exhibitstore = new Exhibitstore();
        exhibitstore.setCategory("类别未知");
        exhibitstore.setExhibitorId(3);
        exhibitstore.setExhibitsName("辣条2代");
        exhibitstore.setIntro("意大利货");
        exhibitstore.setMainPhotoPath("/static/test2.jpg");
        exhibitstore.setStatus("0");
        exhibitstoreDao.addExhibits(exhibitstore);
    }

    @Test
    public void deleteExhibits() throws Exception {
        Integer exhibitsId = 3;
        exhibitstoreDao.deleteExhibits(exhibitsId);
    }

    @Test
    public void updateExhibits() throws Exception {
        Integer exhibistoreId = 1;
        Exhibitstore exhibitstore = new Exhibitstore();
        exhibitstore.setId(exhibistoreId);
        exhibitstore.setStatus("1");
        exhibitstore.setCategory("latiao");
        exhibitstoreDao.updateExhibits(exhibitstore);
    }

    @Test
    public void getAllExhibitstore() throws Exception {
        String status = "1";
        List<Exhibitstore> exhibitstoreList = exhibitstoreDao.getAllExhibitstore(status, 0, 10);
        for (Exhibitstore exhibitstore : exhibitstoreList) {
            System.out.println(exhibitstore);
        }
    }

    @Test
    public void getExhibitstoreByExhibitorId() throws Exception {
        Integer exhibitorId = 3;
        List<Exhibitstore> exhibitstoreList = exhibitstoreDao.getExhibitstoreByExhibitorId(exhibitorId, 0, 10);
        for (Exhibitstore exhibitstore : exhibitstoreList) {
            System.out.println(exhibitstore);
        }
    }

    @Test
    public void getExhibitstoresByName() throws Exception {
        String exhibitsName = "辣条";
        Exhibitstore exhibitstore = exhibitstoreDao.getExhibitstoresByName(exhibitsName);
        System.out.println(exhibitstore);
    }

    @Test
    public void searchExhibitstoresByName() throws Exception {
    }

    @Test
    public void getCount() {
        System.out.println(exhibitstoreDao.getCount());
        System.out.println(exhibitstoreDao.getCountByStatus("0"));
        System.out.println(exhibitstoreDao.getCountByStatus("1"));

    }

}