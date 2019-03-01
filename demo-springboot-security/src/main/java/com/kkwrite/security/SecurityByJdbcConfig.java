package com.kkwrite.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/** 
 * Spring Security Jdbc 权限认证
 *
 * @author Soosky Wang
 * @date 2018年7月24日 上午10:15:15 
 * @version 1.0.0
 */
@Configuration
public class SecurityByJdbcConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 设置 jdbc 认证的数据源，使用默认 users 和 authorities 表
		auth.jdbcAuthentication().dataSource(dataSource);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 禁用 csrf
		http.csrf().disable();
		
		// 配置匹配静态资源允许访问
		//http.authorizeRequests().antMatchers("/**/*.html").permitAll();
		http.authorizeRequests().antMatchers("/**/*.js").permitAll();
		http.authorizeRequests().antMatchers("/**/*.css").permitAll();
		http.authorizeRequests().antMatchers("/**/*.jpg").permitAll();
		http.authorizeRequests().antMatchers("/**/*.png").access("permitAll");
		
		// 配置需要认证的资源
		http.authorizeRequests().antMatchers("/index").hasRole("GUEST");
		http.authorizeRequests().antMatchers("/home").hasRole("USER");
		http.authorizeRequests().antMatchers("/admin").hasRole("ADMIN");
		
		// 登录页面不需要认证
		http.authorizeRequests().mvcMatchers("/toLoginPage").access("permitAll");
		// 登录提交处理不需要认证
		http.authorizeRequests().mvcMatchers("/doLogin").access("permitAll");
		
		// 其它都需要认证
		http.authorizeRequests().antMatchers("/**").authenticated();

		// 使用自定义登录
		http
			.formLogin()
			// 定义登录页面的 url
			.loginPage("/toLoginPage")
			// 登录提交的 url
			.loginProcessingUrl("/doLogin")
			.permitAll();

	}

}
