package com.exhibition.service.impl;


import com.exhibition.dao.RoleDao;
import com.exhibition.dao.UserDao;
import com.exhibition.enums.ExceptionEnums;
import com.exhibition.enums.RoleList;
import com.exhibition.enums.UserStatus;
import com.exhibition.exceptions.ServiceException;
import com.exhibition.po.Exhibits;
import com.exhibition.po.User;
import com.exhibition.service.UserService;
import com.exhibition.utils.CheckFieldUtils;
import com.exhibition.utils.PasswordUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by final on 17-5-20.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class UserServiceImpl implements UserService {

    private static Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordUtils passwordUtils;

    private final int default_size = 20;//默认的请求条数size大小


    /**
     * 返回符合角色的所有用户
     * @param roleId
     * @return
     */
    @Override
    public List<User> selectUserByRole(int roleId, int page, int count) {
        int start=(page-1)*count;
        return userDao.selectUserByRole(roleId,start,count);
    }

    /**
     * 根据名字和状态查询
     * @param page
     * @param size
     * @param name
     * @param status
     * @return
     */
    @Override
    public List<User> searchUserByName(int page, int size, String name, String status) {
        page = (page <= 1) ? 1 : page;
        size = (size <= 1) ? 1 : default_size;
        int start = (page - 1) * size;
        if (!UserStatus.checkStatus(status)) {
            //如果输入的status不合法
            throw new ServiceException(ExceptionEnums.WrongStatus);
        }
        List<User> list=userDao.selectUserByName(start,size,name,status);
        return list;
    }

    /**
     * 通过名字查询某角色用户
     * @param page
     * @param size
     * @param name
     * @param status
     * @param roleId
     * @return
     */
    @Override
    public List<User> searchUserByNameAndRole(int page, int size, String name, String status, Integer roleId) {

        page = (page <= 1) ? 1 : page;
        size = (size <= 1) ? 1 : default_size;
        int start = (page - 1) * size;
        if (!UserStatus.checkStatus(status)) {
            //如果输入的status不合法
            throw new ServiceException(ExceptionEnums.WrongStatus);
        }
        List<User> list=userDao.selectUserByNameAndRole(start,size,name,status,roleId);
        return list;
    }

    public User findUserByName(String username){
        return userDao.findUserByName(username);
    }

    /**
     * 根据用户id查看用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public User findUserById(Integer userId) throws ServiceException {
        if (userId <= 0) {
            //Id<0
            throw new ServiceException(ExceptionEnums.WrongId);
        }
        return userDao.findUserById(userId);
    }

    public List<String> getPermissionsName(Integer roleId) {
        return roleDao.getPermissionsName(roleId);
    }

    /**
     * 根据用户名获取id
     *
     * @param username 用户名
     * @return
     */
    @Override
    public Integer getIdByName(String username) throws ServiceException {
        if (StringUtils.isEmpty(username)) {
            //如果用户名为空则抛出异常
            throw new ServiceException(ExceptionEnums.LackInfo);
        }
        Integer result = userDao.getId(username);
        return result;
    }

    /**
     * 更新用户信息(用户名、密码)
     * <strong>需指定id</strong>
     *
     * @param user
     */
    @Override
    public void updateUser(User user) {
        Integer userId = user.getUserId();
        if (userId == null) {
            //缺少用户Id
            logger.info("缺少用户Id");
            throw new ServiceException(ExceptionEnums.LackId);
        }
        logger.debug("修改用户信息----用户信息：\n" + user);
        userDao.updateUser(user);
    }


    /**
     * 锁定/解锁 用户
     *
     * @param username
     * @param locked
     */
    @Override
    public void lockUser(String username, Boolean locked) {
        if (StringUtils.isEmpty(username)) {
            //用户名为空，直接返回
            return;
        }
        String tmp = (Boolean.TRUE == locked) ? "锁定" : "解锁";
        logger.debug(tmp + "用户-----" + username);
        userDao.lockUser(username,locked);//给该账号加锁或者解锁
    }

    /**
     * 获取锁定账号的信息
     *
     * @param page  当前页数
     * @param count 查询的总数
     * @return
     */
    @Override
    public List<User> getLockedUser(Integer page, Integer count) {
        if (page == null || page <= 0) {
            //默认为首页
            page = 1;
        }
        if (count == null || count <= 0) {
            //默认查询的页数为20条
            count = 20;
        }
        Integer start = (page - 1) * count;//起始记录在数据库中的索引

        return userDao.getLockedUser(start,count);
    }

    /**
     * 删除用户信息
     * @param userId
     */
    @Override
    public void deleteUser(int userId) {
        if (userId <= 0) {
            return;
        }
        userDao.deleteUser(userId);//删除用户users表中的记录
        userDao.deleteUserToRole(userId);//删除用户-角色表user_role的信息
    }

    public boolean register(User user) throws ServiceException {
        boolean result = true;

        user.setCreatTime(new Timestamp(System.currentTimeMillis()));
        user.setLocked(false);
        User tmp = userDao.findUserByName(user.getUsername());
        if (tmp != null) {
            //该用户名已经被使用
            logger.info(user.getUsername() + "\t该用户名已经被使用，注册失败");
            throw new ServiceException(ExceptionEnums.UsernameExists);
        }

        //加密密码,设置盐值
        passwordUtils.encryptPassword(user);
        //保存到数据库
        result = (userDao.insertUser(user) == 1);

        //为用户   注册角色
        result = result && (roleDao.registRole(user, RoleList.User.getRoleId()) == 1);

        if (logger.isInfoEnabled()) {
            logger.info(user.getUsername()+"\t注册成功");
        }
        return result;
    }

    /**
     * 修改用户密码
     *
     * @param user
     */
    @Override
    public void updatePassword(User user) throws ServiceException {
        logger.info(user.getUsername() + "\t用户信息不完整，注册失败");
        String username = user.getUsername();
        String password = user.getPassword();
        int nameLen = username.length();
        int passLen = password.length();

        if (nameLen < 1 || nameLen > 30) {
            //数据长度不符合要求
            logger.info(user.getUsername() + "\t用户数据长度不符合要求，修改失败");
            throw new ServiceException(ExceptionEnums.WrongLength);
        }
        if (passLen < 1 || passLen > 30) {
            //数据长度不符合要求
            logger.info(user.getUsername() + "\t用户数据长度不符合要求，修改失败");
            throw new ServiceException(ExceptionEnums.WrongLength);
        }
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            //用户的账号和密码不能为空
            throw new ServiceException(ExceptionEnums.LackInfo);
        }

        //加密密码,设置盐值
        passwordUtils.encryptPassword(user);
        //更新用户信息
        updateUser(user);
    }

    /**
     * 设置用户的角色
     * @param userId
     * @param roleId
     */
    @Override
    public void setUserRole(Integer userId, Integer roleId) {
        if(userId<0){
            throw new ServiceException(ExceptionEnums.WrongId);
        }

        userDao.setRole(userId,roleId);
        User u= userDao.findUserById(userId);
        logger.info(u.getUsername()+"角色改为："+roleId);
    }
}
