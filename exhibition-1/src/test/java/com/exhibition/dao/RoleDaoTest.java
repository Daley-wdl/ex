package com.exhibition.dao;

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
public class RoleDaoTest {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private RoleDao roleDao;

    @Test
    public void registRole() throws Exception {
    }

    @Test
    public void getPermissionsName() throws Exception {
        Integer roleId = 4;
        List<String> permissions = roleDao.getPermissionsName(roleId);
        for (String permission : permissions) {
            System.out.println(permission);
        }
    }

}