package com.exhibition.utils;

import com.exhibition.po.User;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @Auther: ZhangYuanYou
 * @Description: 加密User中的密码
 * @Date: 下午7:41 17-6-4
 */
public class PasswordUtils {

    private RandomNumberGenerator randomNumberGenerator =
            new SecureRandomNumberGenerator();
    private String algorithmName = "md5";
    private final int hashIterations;

    public PasswordUtils(int hashIterations) {
        this.hashIterations = hashIterations;
    }

    /**
     * @Auther: ZhangYuanYou
     * @Description: 设置user中的盐，并且根据用户名和盐来加密用户的密码
     * @Date: 下午7:49 17-6-4
     */
    public void encryptPassword(User user) {
        user.setSalt(randomNumberGenerator.nextBytes().toHex());
        String newPassword = new SimpleHash(
                algorithmName,
                user.getPassword(),
                //user.getCredentialsSalt()辅助方法返回username+salt
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                hashIterations).toHex();
        user.setPassword(newPassword);
    }
}
