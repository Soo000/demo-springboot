package com.kkwrite.demo.springboot.rabbitmq.consumer.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.stereotype.Component;

/** 
 * HelloReceiver1
 *
 * @author Soosky Wang
 * @date 2018年10月27日 下午6:25:19 
 * @version 1.0.0
 */
@Component
@RabbitListener(queues = "sb.hello")
public class HelloReceiver1 {

	@RabbitHandler
	public void process(String msg) {
		System.out.println("Receive messge: " + msg);
	}
	
}
