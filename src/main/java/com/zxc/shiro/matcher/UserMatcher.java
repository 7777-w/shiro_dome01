package com.zxc.shiro.matcher;

import com.zxc.entity.AuthUser;
import com.zxc.service.AuthUserService;
import com.zxc.shiro.realm.UserRealm;
import com.zxc.utils.DataUtil;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * @author tomsun28
 * @date 18:01 2018/3/3
 */
@Component
public class UserMatcher extends SimpleCredentialsMatcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserMatcher.class);

    @Autowired
    AuthUserService authUserService;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        LOGGER.info("=========>开始匹配");
        Object tokenCredentials = getCredentials(authenticationToken);
        System.out.println("========="+tokenCredentials);
        Object accountCredentials = getCredentials(authenticationInfo);
        System.out.println("========="+authenticationToken);
        if (!authenticationToken.getPrincipal().toString().equals(authenticationInfo.getPrincipals().getPrimaryPrincipal().toString())
                && authenticationToken.getCredentials().toString().equals(authenticationInfo.getCredentials().toString())){
            LOGGER.info("用户名和密码不匹配!");
            return false;
        }
//        LOGGER.info("======>TokenName"+authenticationToken.getPrincipal());
//        LOGGER.info(authenticationToken.getPrincipal().toString());
//        String username=authenticationToken.getPrincipal().toString();
//        AuthUser user=authUserService.selectByUsername(username);
//        user.setStatus(1);
//        user.setCreateTime(DataUtil.getCurrentTime());
//        authUserService.update(user);
        return true;
    }
}
