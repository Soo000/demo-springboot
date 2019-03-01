package com.kkwrite.demo.elasticsearch.client.rest.high;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** 
 * RestHighApp
 *
 * @author Soosky Wang
 * @date 2018年11月8日 下午6:45:13 
 * @since 1.0
 */
@SpringBootApplication
public class RestHighApp {

	public static void main(String[] args) {
		new SpringApplication(RestHighApp.class).run(args);
	}
	
}
