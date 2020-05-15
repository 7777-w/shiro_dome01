package com.zxc.shiro.config;

import com.zxc.entity.AuthUser;
import com.zxc.shiro.component.LoginType;
import com.zxc.shiro.realm.AdminRealm;
import com.zxc.shiro.realm.UserRealm;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserModularRealmAuthorizer extends ModularRealmAuthorizer {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserModularRealmAuthenticator.class);

    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        System.out.println(principals);
        System.out.println("-------------"+((AuthUser)principals.getPrimaryPrincipal()).getIsAdmin());
        int flag=((AuthUser)principals.getPrimaryPrincipal()).getIsAdmin();
        System.out.println("-------------"+((AuthUser)principals.getPrimaryPrincipal()).getIsAdmin());
        String realmName=null;
        if (flag==1){
            realmName=LoginType.ADMIN.toString();
        }else{
            realmName=LoginType.USER.toString();
        }
        assertRealmsConfigured();
        for (Realm realm : getRealms()) {
            if (!(realm instanceof Authorizer)){
                continue;
            }
            LOGGER.info("======>域名："+realm.getName()+":"+LoginType.ADMIN.toString());
            if (realmName.contains(LoginType.ADMIN.toString())) {
                    LOGGER.info("======>选择执行admin域");
                    return ((AdminRealm) realm).isPermitted(principals, permission);
            }
            if (realmName.contains(LoginType.USER.toString())) {
                    LOGGER.info("======>选择执行user域");
                    return ((UserRealm) realm).isPermitted(principals, permission);
            }
        }
        LOGGER.info("======>哪个域都不执行");
        return false;
    }
}
