package com.zxc.shiro.realm;

import com.zxc.entity.AuthUser;
import com.zxc.service.AuthUserService;
import com.zxc.utils.Md5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

//用户域
public class UserRealm extends AuthorizingRealm {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRealm.class);

    @Autowired
    AuthUserService userService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("======>用户授权域");
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        //添加资源授权字符串,格式是这个   表名:操作
//        info.addStringPermission("user:update");
        //拿到这个登录的这个对象
        Subject subject=SecurityUtils.getSubject();
        //获得寄存的东西
        AuthUser currentUser=(AuthUser)subject.getPrincipal();
        //设置当前的用户的权限
        info.addStringPermission(currentUser.getPerms());
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        LOGGER.info("======>用户认证域");
        UsernamePasswordToken token=(UsernamePasswordToken)authenticationToken;
        AuthUser user=userService.selectByUsername(token.getUsername());
        if (null==user){
            return null;
        }
        return new SimpleAuthenticationInfo(user,user.getPassword(),"UserRealm");
    }
}
