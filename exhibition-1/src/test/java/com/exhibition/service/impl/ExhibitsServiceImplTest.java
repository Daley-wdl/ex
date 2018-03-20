package com.exhibition.service.impl;

import com.exhibition.service.ExhibitStoreService;
import com.exhibition.service.ExhibitsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by final on 17-8-1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ExhibitsServiceImplTest {

    @Autowired
    private ExhibitStoreService exhibitStoreService;

    @Autowired
    private ExhibitsService exhibitsService;

    @Test
    public void delete() throws Exception {
    }

    @Test
    public void getExhibits() throws Exception {
    }

    @Test
    public void getExhibitsByExhibitorId() throws Exception {
    }

    @Test
    public void queryExhibitsByName() throws Exception {
    }

    @Test
    public void getExhibitsByName() throws Exception {
    }

    @Test
    public void addExhibits() throws Exception {
    }

    @Test
    public void setExhibitsStatus() throws Exception {
    }

    @Test
    public void getCount() throws Exception {
    }

    @Test
    public void getCountByStatus() throws Exception {
    }

    @Test
    public void updateAvgGrade() {
        int[] ids = {1, 10, 11};
        for (int id : ids) {
            exhibitsService.updateAvgGrade(id);
        }
    }

}