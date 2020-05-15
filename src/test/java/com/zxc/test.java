package com.zxc;

import com.zxc.entity.AuthUser;
import com.zxc.service.AuthUserService;
import com.zxc.service.impl.AuthUserServiceImpl;
import com.zxc.utils.Md5Util;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class test {


    @Test
    public static void main(String[] args) {
        AuthUserService authUserService = new AuthUserServiceImpl();
        AuthUser user=authUserService.selectByUsername("xiaobai");
        System.out.println(user);
    }
}
