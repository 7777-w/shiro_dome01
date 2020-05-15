package com.zxc.controller;

import com.zxc.entity.AuthUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/add")
    public String UserAdd(){
        return "/user/userAdd";
    }
    @RequestMapping("/update")
    public String UserUpdate(){
        return "/user/userUpdate";
    }
    @RequestMapping("/delete")
    public String UserDelete(){
        return "/user/userDelete";
    }
}
