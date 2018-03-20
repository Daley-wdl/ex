package com.exhibition.dao;

import com.exhibition.po.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by final on 17-6-4.
 */
public interface RoleDao {

    /**
     * @Auther: ZhangYuanYou
     * @Description: 根据角色名称为用户注册角色
     * @Date: 下午8:32 17-6-4
     */
    int registRole(@Param("user") User user, @Param("roleId") Integer roleId);

    /**
     * 根据角色id获取对应的权限名
     * @param roleId
     * @return
     */
    List<String> getPermissionsName(Integer roleId);
}
