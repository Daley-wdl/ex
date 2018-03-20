package com.exhibition.dao;

import com.exhibition.po.CommonCategory;
import com.exhibition.po.Exhibitor;
import com.exhibition.po.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by final on 17-7-25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ExhibitiorDaoTest {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ExhibitiorDao exhibitiorDao;

    @Test
    public void insertExhibitor() throws Exception {
        Exhibitor exhibitor = new Exhibitor(3,"张","0","845447141@qq.com",
                "12345678910","photo_path","无");
        int result = exhibitiorDao.insertExhibitor(exhibitor);
        System.out.println("插入记录成功:" + (result==1));
    }

    @Test
    public void selectByUserId() throws Exception {
        Integer userId = 3;
        Exhibitor exhibitor = exhibitiorDao.selectByUserId(userId);
        System.out.println(exhibitor);
    }

    @Test
    public void searchByName() {
        String username = "zh";
        List<Exhibitor> exhibitorList = exhibitiorDao.searchByName(username);
        for (Exhibitor exhibitor : exhibitorList) {
            System.out.println(exhibitor);
        }
    }

    @Test
    public void updateExhibitor() {
        Integer id = 1;
        Exhibitor exhibitor = new Exhibitor();
        exhibitor.setId(1);
        exhibitor.setPhone("10123456879");
        exhibitor.setUserId(4);//无效
        exhibitor.setRealName("元");
        exhibitor.setLicensePhoto("/static/photo.jpg");
        exhibitor.setIntro("暂时没有更多介绍");
        exhibitor.setStatus("1");
        exhibitiorDao.updateExhibitor(exhibitor);
    }

    @Test
    public void getCommonCategory() {
        Integer userId = 3;
        CommonCategory commonCategory = exhibitiorDao.selectCommonCategory(userId);
        System.out.println(commonCategory);
    }

    @Test
    public void updateCommonCategory() {
        CommonCategory commonCategory = new CommonCategory();
        commonCategory.setTag("古风;");
        commonCategory.setUserId(3);
        exhibitiorDao.updateCommonCategory(commonCategory);
    }

    @Test
    public void insertCommonCategory() {
        CommonCategory commonCategory = new CommonCategory();
        commonCategory.setTag("食品，音乐");
        commonCategory.setUserId(3);
        exhibitiorDao.insertCommonCategory(commonCategory);
    }
}