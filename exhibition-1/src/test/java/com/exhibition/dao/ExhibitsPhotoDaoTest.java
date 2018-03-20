package com.exhibition.dao;

import com.exhibition.po.ExhibitsPhoto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

@SuppressWarnings("SpringJavaAutowiringInspection")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ExhibitsPhotoDaoTest {

    @Autowired
    private ExhibitsPhotoDao exhibitsPhotoDao;

    @Test
    public void add() throws Exception {
        ExhibitsPhoto photo = new ExhibitsPhoto(2, "/test", 5);
        exhibitsPhotoDao.add(photo);
    }

    @Test
    public void addFench() {
        List<ExhibitsPhoto> list = new LinkedList<>();
        list.add(new ExhibitsPhoto(2, "/test2", 1));
        list.add(new ExhibitsPhoto(3, "/test3", 1));
        list.add(new ExhibitsPhoto(4, "/test4", 1));
        list.add(new ExhibitsPhoto(5, "/test5", 1));
        exhibitsPhotoDao.addBatch(list);
    }

    @Test
    public void delete() throws Exception {
        Integer id = 1;
        exhibitsPhotoDao.delete(id);
    }

    @Test
    public void deleteByExhibitorId() throws Exception {
        Integer exhibitorId = 1;
        exhibitsPhotoDao.deleteByExhibitorId(exhibitorId);
    }

    @Test
    public void getPhotos() throws Exception {
        List<ExhibitsPhoto> photoList = exhibitsPhotoDao.getPhotos(2);
        for (ExhibitsPhoto s : photoList) {
            System.out.println(s);
        }
    }

}