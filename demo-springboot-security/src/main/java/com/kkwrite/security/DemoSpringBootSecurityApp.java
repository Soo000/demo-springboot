package com.kkwrite.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;

/** 
 * 启动类
 * exclude = ErrorMvcAutoConfiguration.class 去掉默认的错误页面
 * @author Soosky Wang
 * @date 2018年7月24日 下午3:39:23 
 * @version 1.0.0
 */
@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
public class DemoSpringBootSecurityApp {

	public static void main(String[] args) {
		SpringApplication.run(DemoSpringBootSecurityApp.class, args);
	}
	
}
