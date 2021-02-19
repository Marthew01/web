package com.web.component.shiro.util;

import com.web.dao.model.entity.User;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @author zhang-rongyao
 * @version V1.0
 * @Package com.example.boot_shiro.utils
 * @date 2020/12/22/022
 */
public class PasswordHelper {
    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    public static final String ALGORITHM_NAME = "md5"; // 基础散列算法
    public static final int HASH_ITERATIONS = 2; // 自定义散列次数

    public void encryptPassword(User user) {
        // 随机字符串作为salt因子
        user.setSalt(randomNumberGenerator.nextBytes().toHex());

        //干扰因子，防止相同密码生成字符串一样
        // user.getCredentialsSalt() == username + salt + salt
        String newPassword = new SimpleHash(ALGORITHM_NAME, user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()), HASH_ITERATIONS).toHex();
        user.setPassword(newPassword);
    }

    public static void main(String[] args) {
        RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

        String newPassword = new SimpleHash(ALGORITHM_NAME, "123456",
                ByteSource.Util.bytes("admin"+"6a032b48cab1c9417b44463677afa520"+"6a032b48cab1c9417b44463677afa520"), HASH_ITERATIONS).toHex();
        ByteSource bytes = ByteSource.Util.bytes("6a032b48cab1c9417b44463677afa520");
        System.out.println(newPassword);
    }

}
