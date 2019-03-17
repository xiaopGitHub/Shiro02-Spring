package com.xp.controller;

import com.xp.service.ShiroService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @Author xp
 * @CreateTime 2019/03/03  22:58
 * @Function ${VAR}
 */
@Controller
@RequestMapping("/shiro")
public class ShiroLoginController {

    @Autowired
    private ShiroService shiroService;

    @RequestMapping("/testShiroAnnotation")
    public String testShiroAnnotation(){
        shiroService.test();
        return "success";
    }

    @RequestMapping("/login")
    public String login(@RequestParam(value = "username") String username,
                        @RequestParam(value = "password") String password){
        // 获取当前的Subject
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            //把用户名和密码封装为UsernamePasswordToken
            UsernamePasswordToken token =
                    new UsernamePasswordToken(username, password);

            /**
             * 记住我,开发时会用checkbox来表示remember me,
             * 一般通过checkbox是否传值来判断是true还是false
             * */
            token.setRememberMe(true);

            try {
                //执行登陆,参数token被传到自定义Realm中.执行登陆了。
                subject.login(token);
            } catch (AuthenticationException ae) {
                System.out.println("登陆失败" +"---------------------->"+ ae.getMessage());
                return "login";
            }
        }
        return "success";
    }
}
