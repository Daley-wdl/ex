package com.exhibition.dao;



import com.exhibition.po.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * Created by final on 17-5-20.
 */
public interface UserDao {

    /**
     * *根据角色查询所有用户
     * @param roleId
     * @param start
     * @param count
     * @return
     */
    List<User> selectUserByRole(@Param("roleId") int roleId, @Param("start") int start, @Param("count") int count);

    List<User> selectUserByName(@Param("start") int start,@Param("size") int size,@Param("name") String name,@Param("status")String status);

    /**
     * 通过名字查询某角色的用户
     * @param start
     * @param size
     * @param name
     * @param status
     * @param roleId
     * @return
     */
    List<User> selectUserByNameAndRole(@Param("start") int start,@Param("size") int size,@Param("name") String name,
                                       @Param("status")String status,@Param("roleId") Integer roleId);

    /**
     * 根据用户名获取id
     * @param username
     * @return
     */
    Integer getId(String username);

    String getUsername(int id);

    /**
     * 彻底删除用户信息
     * @param userId
     */
    void deleteUser(Integer userId);

    /**
     * 删除用户对应的角色信息
     * @param userId
     */
    void deleteUserToRole(Integer userId);

    /**
     * 根据用户名查找用户信息以及角色身份
     * @param username
     * @return
     */
    User findUserByName(String username);

    /**
     * 根据用户id查找用户信息
     * @param userId    用户id
     * @return
     */
    User findUserById(Integer userId);

    /**
     * 更新用户信息(用户名、密码)
     * <strong>需指定id</strong>
     * @param user
     */
    void updateUser(User user);

    /**
     * 设置用户的角色
     * @param userId
     * @param roleId
     */
    void setRole(@Param("userId") Integer userId,@Param("roleId") Integer roleId);

    /**
     * @Auther: ZhangYuanYou
     * @Description: 新增用户
     * @Date: 下午4:05 17-5-21
     */
    int insertUser(User user);


    /**
     * @Auther: ZhangYuanYou
     * @Description: 根据role_id获取相应的权限名
     * @Date: 上午11:04 17-5-20
     */
    List<String> getPermissionsName(Integer roleId);

    /**
     * 修改展商的lock字段,表示该用户账号是否被锁
     * @param username    用户名
     * @param lock  是否锁定
     */
    void lockUser(@Param("username") String username, @Param("lock") Boolean lock);

    /**
     * 获取被锁定的账号信息
     * @param start 第一条记录在数据库中的索引
     * @param count 查询数量
     * @return
     */
    List<User> getLockedUser(@Param("page") Integer start, @Param("count") Integer count);
}
