package com.kkwrite.demo.springboot.rabbitmq.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** 
 * 消息消费者
 *
 * @author Soosky Wang
 * @date 2018年10月27日 下午6:23:17 
 * @version 1.0.0
 */
@SpringBootApplication
public class ConsumerApp {

	public static void main(String[] args) {
		new SpringApplication(ConsumerApp.class).run(args);
	}
	
}
