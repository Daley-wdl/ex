package com.exhibition.dao;

import com.exhibition.po.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by final on 17-7-25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class UserDaoTest {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private UserDao userDao;

    @Test
    public void findUserByName() throws Exception {
        String username = "zhang";
        User user = userDao.findUserByName(username);
        System.out.println(user);
    }

    @Test
    public void insertUser() throws Exception {
    }

    @Test
    public void getPermissionsName() throws Exception {
    }

    @Test
    public void lockUserTest() {
        Boolean locked = false;
        String username = "zhang";
        userDao.lockUser(username, locked);
        User user = userDao.findUserByName(username);
        System.out.println("locked:" + user.getLocked());
    }

    @Test
    public void updateUser() {
        User user = userDao.findUserByName("test");
        System.out.println("before update:\n" + user);
        user.setUsername("me");
//        user.setPassword("123");
        user.setSalt("can not use");
        userDao.updateUser(user);
        user = userDao.findUserByName("me");
        System.out.println("after update:\n" + user);
    }

    @Test
    public void deleteUser() {
        Integer userId = 17;
        userDao.deleteUser(userId);
        userDao.deleteUserToRole(userId);
    }

    @Test
    public void findUserById() {
        Integer userId = 3;
        User user = userDao.findUserById(userId);
        System.out.println(user);
    }

    @Test
    public void getId() {
        String username = "zhang";
        int id = userDao.getId(username);
        System.out.println(id);
    }
}