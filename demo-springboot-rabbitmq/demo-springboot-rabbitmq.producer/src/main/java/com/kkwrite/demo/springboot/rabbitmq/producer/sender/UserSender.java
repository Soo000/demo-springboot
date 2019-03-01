package com.kkwrite.demo.springboot.rabbitmq.producer.sender;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kkwrite.demo.springboot.rabbitmq.common.constant.Const;
import com.kkwrite.demo.springboot.rabbitmq.common.pojo.User;

/** 
 * User Sender
 *
 * @author Soosky Wang
 * @date 2018年10月27日 下午11:12:55 
 * @version 1.0.0
 */
@Component
public class UserSender {

	@Autowired
	private AmqpTemplate amqpTemplate;
	
	public void send() {
		User user = new User();
		user.setUserId(1L);
		user.setUsername("wangke");
		amqpTemplate.convertAndSend(Const.QUEUE_USER, user);
	}
	
}
