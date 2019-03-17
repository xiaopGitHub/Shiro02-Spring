package com.xp.service;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.Date;

/**
 * @Author xp
 * @CreateTime 2019/03/04  15:11
 * @Function ${VAR}
 */

@Service
public class ShiroService {

    /**
     * 表示admin角色才能访问此方法,一般此直接写在controller层
     */
    @RequiresRoles({"admin"})
    public void test(){
        System.out.println("今天的日期是:   "+"==============   "+new Date());
    }
}
