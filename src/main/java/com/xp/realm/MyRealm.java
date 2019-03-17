package com.xp.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author xp
 * @CreateTime 2019/03/02  10:10
 * @Function 自定义认证和授权的Realm,继承AuthorizingRealm,同时实现doGetAuthenticationInfo()方法
 *           和doGetAuthorizationInfo()方法
 */
public class MyRealm extends AuthorizingRealm {

    /**
     *      认证
     * */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("--------------------------------- >执行认证");
        //1.把AuthenticationToken 转换为 UsernamePasswordToken
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
        // 2) credentials: 盐值加密数据库中获取的密码,用户名不同,加密后的密码也不同
        String credentials = null;
        if ("admin".equals(username)) {
            credentials = "038bdaf98f2037b31f1e75b5b4c9b26e";
        } else if ("user".equals(username)) {
            credentials = "098d2c478e9c11555ce2823231e02ec1";
        }
        // 3) realmName: 当前realm对象的name,调用该父类的getName()即可
        String realmName = super.getName();
        // 4) 盐值credentialsSalt: 这里用username作为盐值,也可以用随机字符串
        ByteSource credentialsSalt = ByteSource.Util.bytes(username);
        //simpleAuthenticationInfo中封装了从数据库中查询的用户信息,最后shiro完成输入的密码和数据库查询出的密码的比对
/*        SimpleAuthenticationInfo simpleAuthenticationInfo=
                new SimpleAuthenticationInfo(principal, credentials, realmName);*/

        //MD5盐值加密,区别不同用户密码相同的情况,返回带盐的SimpleAuthenticationInfo
        SimpleAuthenticationInfo simpleAuthenticationInfo =
                new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);
        return simpleAuthenticationInfo;
    }

/*    public static void main(String[] args) {
        String algorithmName = "MD5";
        Object credentials1 = "123456";
        //盐值
        Object salt = ByteSource.Util.bytes("admin");
        //加密次数
        int hashIterations = 1024;
        //Shiro的MD5盐值加密,Shiro底层使用SimpleHash加密
        Object result = new SimpleHash(algorithmName, credentials1, salt, hashIterations);
        System.out.println(result);
    }*/


    /**
     *      授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("-----------------------------------> 执行授权");
        // 1. 从PrincipalCollection中获取登陆用户的信息
        Object principal = principalCollection.getPrimaryPrincipal();

        // 2. 利用登陆用户的信息,来获取当前用户的角色或权限(可能需要查询数据库)
        // 将用户权限放入Set中
        Set<String> roles = new HashSet<>();
        roles.add("user");
        //模拟admin用户有user和admin两个权限
        if ("admin".equals(principal)) {
            roles.add("admin");
        }

        // 3. 设置SimpleAuthorizationInfo的roles属性并返回
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo(roles);
        return simpleAuthorizationInfo;
    }
}
