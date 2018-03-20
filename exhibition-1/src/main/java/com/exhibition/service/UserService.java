package com.exhibition.service;

import com.exhibition.enums.RoleList;
import com.exhibition.exceptions.ServiceException;
import com.exhibition.po.User;

import java.util.List;

/**
 * Created by final on 17-7-22.
 */
public interface UserService {
    /**
     * 返回符合角色的所有用户
     * @param roleId
     * @return
     */
    List<User> selectUserByRole(int roleId, int page, int count);

    /**
     * 根据名字和状态模糊查询
     * @param page
     * @param size
     * @param name
     * @param status
     * @return
     */
    List<User> searchUserByName(int page,int size,String name,String status);

    /**
     * 通过名字查询某角色的用户
     * @param page
     * @param size
     * @param name
     * @param status
     * @param roleId
     * @return
     */
    List<User> searchUserByNameAndRole(int page,int size,String name,String status,Integer roleId);

    /**
     * 删除用户信息
     * @param userId
     */
    void deleteUser(int userId);

    /**
     * @Auther: ZhangYuanYou
     * @Description: 新增用户
     * @Date: 下午4:05 17-5-21
     */
    public boolean register(User user) throws ServiceException;

    /**
     * 根据用户名查找用户信息以及角色身份
     * @param username
     * @return
     */
    public User findUserByName(String username);

    /**
     * 根据用户id查看用户信息
     * @param userId
     * @return
     */
    User findUserById(Integer userId) throws ServiceException;

    /**
     * @Auther: ZhangYuanYou
     * @Description: 根据role_id获取相应的权限名
     * @Date: 上午11:04 17-5-20
     */
    public List<String> getPermissionsName(Integer roleId);

    /**
     * 根据用户名获取id
     * @param username  用户名
     * @return
     */
    Integer getIdByName(String username) throws ServiceException;

    /**
     * 更新用户信息(用户名、密码)
     * <strong>需指定id</strong>
     * @param user
     */
    void updateUser(User user);

    /**
     * 修改用户密码
     * @param user
     */
    void updatePassword(User user) throws ServiceException;

    /**
     * 设置用户的角色
     * @param userId
     * @param roleId
     */
    void setUserRole(Integer userId,Integer roleId);

    /**
     * 锁定/解锁 用户
     */
    void lockUser(String username, Boolean locked);

    /**
     * 获取锁定账号的信息
     * @param page  当前页数
     * @param count 查询的总数
     * @return
     */
    List<User> getLockedUser(Integer page, Integer count);
}
