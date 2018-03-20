package com.exhibition.dao;

import com.exhibition.po.Exhibits;
import com.exhibition.po.Exhibitstore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by final on 17-7-30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@SuppressWarnings("SpringJavaAutowiringInspection")

public class ExhibitsDaoTest {

    @Autowired
    private ExhibitsDao exhibitsDao;

    @Autowired
    private ExhibitstoreDao exhibitstoreDao;

    @Test
    public void insertExhibits() throws Exception {
        List<Exhibitstore> exhibitstoreList = exhibitstoreDao.getAllExhibitstore("1", 0, 20);
        Exhibitstore exhibitstore = exhibitstoreList.get(0);
        Exhibits exhibits = new Exhibits(exhibitstore);
        exhibits.setPrice(200);
        exhibits.setNumber(20);
        try {
            exhibitsDao.insertExhibits(exhibits);
            System.out.println("增加成功:");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteExhibits() throws Exception {
        exhibitsDao.deleteExhibits(1);
    }

    @Test
    public void updateExhibits() throws Exception {
        Exhibits exhibits = exhibitsDao.getExhibitsById(1);
        System.out.println(exhibits);
        exhibits.setNumber(1);
        exhibits.setPrice(10);
        exhibits.setCategory("食品");
        exhibits.setIntro("nothing");
        exhibits.setPhotoPath("/static/path.jpg");
        exhibitsDao.updateExhibits(exhibits);
    }

    @Test
    public void queryExhibitsByName() throws Exception {
        String name = "辣";
        List<Exhibits> exhibitsList = exhibitsDao.queryExhibitsByName(name,0,10);
        for (Exhibits exhibits : exhibitsList) {
            System.out.println(exhibits);
        }
    }

    @Test
    public void getExhibitsByName() throws Exception {
        Exhibits exhibits = exhibitsDao.getExhibitsByName("辣条");
        System.out.println(exhibits);

    }

    @Test
    public void getExhibits() throws Exception {
        List<Exhibits> exhibitsList = exhibitsDao.getExhibits("1", 0, 20);
        for (Exhibits exhibits : exhibitsList) {
            System.out.println(exhibits);
        }
    }

    @Test
    public void getExhibitsByExhibitorId() throws Exception {
        Integer id = 3;
        List<Exhibits> exhibitsList = exhibitsDao.getExhibitsByExhibitorId(id,0,20);
        for (Exhibits exhibits : exhibitsList) {
            System.out.println(exhibits);
        }
    }

}