package com.kkwrite.jsp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/** 
 * 类说明 
 *
 * @author Soosky Wang
 * @date 2018年7月24日 下午5:50:00 
 * @version 1.0.0
 */
@SpringBootApplication
@EnableWebMvc
public class DemoSpringBootJspApp {

	public static void main(String[] args) {
		SpringApplication.run(DemoSpringBootJspApp.class, args);
	}
	
}
