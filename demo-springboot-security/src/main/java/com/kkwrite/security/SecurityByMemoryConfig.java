package com.kkwrite.security;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/** 
 * Spring Security 配置类
 *
 * @author Soosky Wang
 * @date 2018年7月24日 下午4:27:28 
 * @version 1.0.0
 */
//@Configuration
public class SecurityByMemoryConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(WebSecurity web) throws Exception {
		// 是否启用调试
		web.debug(true);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 在内存中配置用户和角色
		auth.inMemoryAuthentication().withUser("guest").password("guest").roles("GUEST");
		auth.inMemoryAuthentication().withUser("user").password("user").roles("USER");
		auth.inMemoryAuthentication().withUser("admin").password("admin").roles("GUEST", "USER", "ADMIN");

		// 过期账号
		auth.inMemoryAuthentication().withUser("wangke").password("wangke").accountExpired(true).roles("USER");
		// 锁定账号
		auth.inMemoryAuthentication().withUser("soosky").password("soosky").accountLocked(true).roles("USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/index").hasRole("GUEST");
		http.authorizeRequests().antMatchers("/home").hasRole("USER");
		http.authorizeRequests().antMatchers("/admin").hasRole("ADMIN");
		
		// 其它地址需要认证
		http.authorizeRequests().anyRequest().authenticated();
		
		http.formLogin();
	}
	
}
