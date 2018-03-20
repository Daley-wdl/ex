package com.exhibition.shiro;


import javax.annotation.Resource;


import com.exhibition.po.Role;
import com.exhibition.po.User;
import com.exhibition.service.UserService;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class UserRealm extends AuthorizingRealm {

    private static Logger logger = Logger.getLogger(UserRealm.class);

    @Resource
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principalCollection) {
        //获取登录时输入的用户名
        String loginName = (String) principalCollection.getPrimaryPrincipal();
        //到数据库查是否有此对象
        User user=userService.findUserByName(loginName);
        if(user!=null){
            //权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
            SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
            //用户的角色集合
            Set<String> roleNames = new HashSet<>();
            for (Role role : user.getRoles()) {
                roleNames.add(role.getRoleName() );
            }
            info.setRoles(roleNames );
            //用户的角色对应的所有权限，如果只使用角色定义访问权限，下面的四行可以不要

            for (Role role : user.getRoles() ) {
                List<String> permisson = userService.getPermissionsName(role.getRoleId() );
                info.addStringPermissions( permisson );
            }

            return info;
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        // 调用userService查询是否有此用户
        User user = userService.findUserByName(username);

        if (user == null) {
            // 抛出 帐号找不到异常
            throw new UnknownAccountException();
        }


        // 判断帐号是否锁定
//        if (Boolean.TRUE.equals(user.getLocked())) {
//            // 抛出 帐号锁定异常
//            throw new LockedAccountException();
//        }

        // 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUsername(), // 用户名
                user.getPassword(), // 密码
//                ByteSource.Util.bytes(user.getCredentialsSalt()),// salt=username+salt
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                getName() // realm name
        );
        return authenticationInfo;
    }



}
