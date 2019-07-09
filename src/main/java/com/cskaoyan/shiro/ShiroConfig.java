package com.cskaoyan.shiro;

import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;


@Configuration
public class ShiroConfig {

	@Bean
	public DefaultWebSecurityManager securityManager(MallRealm mallRealm, MallShiroSessionManager mallShiroSessionManager){
		DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
		defaultWebSecurityManager.setRealm(mallRealm);
		defaultWebSecurityManager.setSessionManager(mallShiroSessionManager);
		return defaultWebSecurityManager;
	}
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
		shiroFilterFactoryBean.setLoginUrl("/admin/auth/xfor");
//		shiroFilterFactoryBean.setSuccessUrl("/dashboard"); //设置登陆成功页面

		HashMap<String, String> filterMap = new HashMap<>();

		filterMap.put("/admin/auth/xfor","anon");
//		filterMap.put("/admin/auth/401","anon");
		filterMap.put("/xfor","anon");
		filterMap.put("/admin/logout","logout");
		filterMap.put("/admin/**","authc");

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
		return shiroFilterFactoryBean;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager defaultWebSecurityManager)
	{
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(defaultWebSecurityManager);
		return authorizationAttributeSourceAdvisor;
	}

	@Bean
	public MallShiroSessionManager mallShiroSessionManager(){
		MallShiroSessionManager mallShiroSessionManager = new MallShiroSessionManager();
		return mallShiroSessionManager;
	}
}
