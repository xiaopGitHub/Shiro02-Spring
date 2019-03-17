package com.xp.factory;

import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

/**
 * @Author xp
 * @CreateTime 2019/03/04  15:41
 * @Function 通过数据库查询角色权限,放入map中，Shiro底层就是用LinkedHashMap封装的角色权限
 *           角色权限为key,过滤器为value值
 */
@Component(value = "filterChainDefinitionMapBuilder")
public class ShiroFilterFactoryBean {

    public LinkedHashMap<String, String> buildFilterChainDefinitionMap() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        //模拟访问数据库查询用户角色权限,初始化map.注意初始化顺序
        map.put("/login.jsp","anon" );
        map.put("/shiro/login","anon" );
        map.put("/shiro/logout","logout" );
        map.put("/user.jsp","authc,roles[user]" );
        map.put("/admin.jsp","authc,roles[admin]" );
        //user表示,如果是通过remember me记住我登陆的,也可以对success.jsp进行访问
        map.put("/success.jsp","user");
        map.put("/**","authc" );
        return map;
    }
}
