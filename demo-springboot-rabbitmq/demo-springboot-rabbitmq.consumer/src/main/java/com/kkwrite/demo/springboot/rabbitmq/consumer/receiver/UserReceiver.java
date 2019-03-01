package com.kkwrite.demo.springboot.rabbitmq.consumer.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.stereotype.Component;

import com.kkwrite.demo.springboot.rabbitmq.common.pojo.User;

/** 
 * UserReceiver
 *
 * @author Soosky Wang
 * @date 2018年10月27日 下午11:22:52 
 * @version 1.0.0
 */
@Component
@RabbitListener(queues = "sb.user")
public class UserReceiver {

	@RabbitHandler
	public void process(User user) {
		System.out.println("User Receive: " + user.getUserId() + " " + user.getUsername());
	}
	
}
