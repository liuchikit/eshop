package com.zhumian.config.shiro;

import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //设置登录页面
        shiroFilterFactoryBean.setLoginUrl("/index");
        //登录成功后跳转页面
        shiroFilterFactoryBean.setSuccessUrl("/home");
        //未授权页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/index");

        //拦截器
        Map<String,Filter> filterMap = new HashMap<>();
        shiroFilterFactoryBean.setFilters(filterMap);

        //权限控制
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/login","anon");
        filterChainDefinitionMap.put("/static/**","anon");
        //filterChainDefinitionMap.put("/templates/**","anon");
        filterChainDefinitionMap.put("/**","authc");
        // TODO: 2018/3/21 查询权限
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager(@Qualifier("shiroRealm")ShiroRealm shiroRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm);

        return securityManager;
    }

    @Bean(name = "shiroRealm")
    public ShiroRealm shiroRealm(){
        ShiroRealm shiroRealm = new ShiroRealm();
        return shiroRealm;
    }

    public SimpleCookie simpleCookie(){
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //设置cookie有限时间30天
        simpleCookie.setMaxAge(2592000);
        return simpleCookie;

    }

    public RememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(simpleCookie());
        return cookieRememberMeManager;
    }


}
