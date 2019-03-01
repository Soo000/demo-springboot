package com.kkwrite.demo.springboot.rabbitmq.producer.sender;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kkwrite.demo.springboot.rabbitmq.common.constant.Const;

/** 
 * HelloSender1
 *
 * @author Soosky Wang
 * @date 2018年10月27日 下午6:29:13 
 * @version 1.0.0
 */
@Component
public class HelloSender1 {

	@Autowired
	private AmqpTemplate ampqTemplate;
	
	public void send(String msg) {
		System.out.println("在 " + System.currentTimeMillis() + " 向  " + Const.QUEUE_HELLO + " 发送消息 " + msg);
		ampqTemplate.convertAndSend(Const.QUEUE_HELLO, msg);
	}
	
}
