package com.xp.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;

/**
 * @Author xp
 * @CreateTime 2019/03/04  12:52
 * @Function 自定义认证Realm,使用SHA1加密密码
 */
public class SecondRealm extends AuthenticatingRealm {
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("=============================>SecondRealm");
        //1.把AuthenticationToken 转换为 UsernamePasswordToken
        //token中封装了前台输入的用户信息
        UsernamePasswordToken up = (UsernamePasswordToken) token;

        //2.从UsernamePasswordToken中获取username
        String username = up.getUsername();

        //3.调用数据库中的方法,从数据库中查询username对应的用户记录
        System.out.println("从数据库中获取的用户名:+\t" + username + "所对应的用户信息");

        //4.若用户不存在,则可以抛出认证异常UnknownAccountException
        if ("unknown".equals(username)) {
            throw new UnknownAccountException("该用户不存在");
        }
        //5.根据用户信息判断是否抛出其他AuthenticationException异常,如用户被锁定等等
        if ("monster".equals(username)) {
            throw new LockedAccountException("该用户被锁定");
        }
        //6.根据用户信息情况构建AuthenticationInfo对象并返回
        //以下信息从数据库中获取
        // 1) principal: 认证的实体信息,可以是username,也可以是数据库对应的实体类对象
        Object principal = username;
        // 2) credentials: 数据库中获取的密码,用户名不同,加密后的密码也不同
        String credentials = null;
        if ("admin".equals(username)) {
            credentials = "ce2f6417c7e1d32c1d81a797ee0b499f87c5de06";
        } else if ("user".equals(username)) {
            credentials = "073d4c3ae812935f23cb3f2a71943f49e082a718";
        }
        // 3) realmName: 当前realm对象的name,调用该父类的getName()即可
        String realmName = super.getName();
        // 4) 盐值credentialsSalt: 这里用username作为盐值,也可以用随机字符串
        ByteSource credentialsSalt = ByteSource.Util.bytes(username);
        //SHA1盐值加密
        SimpleAuthenticationInfo simpleAuthenticationInfo =
                new SimpleAuthenticationInfo("SecondRealm", credentials, credentialsSalt, realmName);
        //返回给Shiro,进行比对
        return simpleAuthenticationInfo;
    }

    public static void main(String[] args) {
        String algorithmName = "SHA1";
        Object credentials1 = "123456";
        Object salt = ByteSource.Util.bytes("user");
        int hashIterations = 1024;
        Object result = new SimpleHash(algorithmName, credentials1, salt, hashIterations);
        System.out.println(result);
    }
}
