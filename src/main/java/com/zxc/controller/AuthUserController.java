package com.zxc.controller;

import com.zxc.entity.AuthUser;
import com.zxc.service.AuthUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (AuthUser)表控制层
 *
 * @author makejava
 * @since 2020-05-14 15:51:08
 */
@Controller
@RequestMapping("authUser")
public class AuthUserController {
    /**
     * 服务对象
     */
    @Resource
    private AuthUserService authUserService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/selectOne")
    public String selectOne(Long id) {
        System.out.println(this.authUserService.queryById(1L));
        return "login";
    }

}