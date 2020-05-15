package com.zxc.shiro.config;

import com.zxc.shiro.matcher.UserMatcher;
import com.zxc.shiro.realm.AdminRealm;
import com.zxc.shiro.realm.UserRealm;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ShiroConfig {


    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier(value = "getDefaultWebSecurityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean bean=new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);
        //添加过滤器（shiro内置的）
        /**
         * @target 添加过滤器（shiro内置的）
         *  anon:无需认证
         *  authc：必须认证
         *  user:必须拥有记住我功能
         *  perms:拥有对某个资源的权限
         *  roles：拥有某个角色权限
         */
        Map<String,String> filterMap=new LinkedHashMap<>();
        //给某个路径设置需要的权限，真正给subject授权的是在Realm里面做的
        filterMap.put("/login/in","anon");
        filterMap.put("/noauth","anon");
        filterMap.put("/user/add","anon");
        filterMap.put("/user/delete","perms[user:delete]");
        filterMap.put("/user/update","perms[user:update]");
        bean.setFilterChainDefinitionMap(filterMap);
        //设置登录请求,如果设置了需要认证，并且没有认证，则会自动跳转到"/login"下
        bean.setLoginUrl("/login");
        //设置未授权请求
        bean.setUnauthorizedUrl("/noauth");
        return bean;
    }

    //创建默认的安全管理器
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager( @Qualifier(value = "userRealm")UserRealm userRealm){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        //将挑选Realm域的类注入SecurityManager
        securityManager.setAuthenticator(modularRealmAuthenticator());
//      //设置授权注入的类
        securityManager.setAuthorizer(modularRealmAuthorizer());
        //安全管理器关联--->UserRealm
        //securityManager.setRealm(taskRealm);
        //securityManager.setRealm(userRealm);
        List<Realm> realms = new ArrayList<>();
        //添加多个Realm
        realms.add(adminRealm());
        realms.add(userRealm());
        securityManager.setRealms(realms);

        //设置rememberMeManager
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        SimpleCookie remeberMeCookie = new SimpleCookie("rememberMe");
        remeberMeCookie.setMaxAge(86400);
        remeberMeCookie.setHttpOnly(true);
        rememberMeManager.setCookie(remeberMeCookie);
        securityManager.setRememberMeManager(rememberMeManager);
        //设置缓存，默认缓存用户授权信息，认证信息不缓存
        securityManager.setCacheManager(new MemoryConstrainedCacheManager());

        return securityManager;
    }
    @Bean
    public ModularRealmAuthorizer modularRealmAuthorizer() {
        //自己重写的ModularRealmAuthorizer
        return new UserModularRealmAuthorizer();
    }
    @Bean
    public UserModularRealmAuthenticator modularRealmAuthenticator() {
        //自己重写的ModularRealmAuthorizer
        return new UserModularRealmAuthenticator();
    }

    @Bean
    public AdminRealm adminRealm(){
        AdminRealm adminRealm=new AdminRealm();
        adminRealm.setCredentialsMatcher(new UserMatcher());
        return  adminRealm;
    }
    @Bean
    public UserRealm userRealm(){
        UserRealm userRealm=new UserRealm();
        userRealm.setCredentialsMatcher(new UserMatcher());
        return  userRealm;
    }
}
