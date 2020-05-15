package com.zxc.controller;

import com.zxc.service.AuthUserService;
import com.zxc.shiro.component.UserToken;
import com.zxc.utils.Md5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    AuthUserService authUserService;
    @RequestMapping("/index")
    public String index(){
        return "index";
    }
    @RequestMapping("/login")
    public String login(){
        return "login";
    }
    @RequestMapping("/noauth")
    public String noauth(){
        return "noauth";
    }
    @RequestMapping("/login/in/{flag}")
    public String loginIn(String username, String password, @PathVariable("flag")Integer flag){
        Subject subject= SecurityUtils.getSubject();

        String pwd= Md5Util.md5(password);
        //UsernamePasswordToken token=new UsernamePasswordToken(username,pwd);
        UserToken token=null;
        if (flag.equals(1)){
            token=new UserToken(username,password,"Admin");
        }else {
            token=new UserToken(username,password,"User");
        }
        LOGGER.info(username+":"+password);

        subject.login(token);
        return "login";
    }
}
