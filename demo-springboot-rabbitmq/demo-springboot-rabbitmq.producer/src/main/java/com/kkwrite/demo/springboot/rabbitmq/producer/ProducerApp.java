package com.kkwrite.demo.springboot.rabbitmq.producer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.kkwrite.demo.springboot.rabbitmq.common.constant.Const;

/** 
 * 启动类
 *
 * @author Soosky Wang
 * @date 2018年10月12日 下午4:11:04 
 * @version 1.0.0
 */
@SpringBootApplication
public class ProducerApp {
	
	/**
	 * 向容器托管一个 sb.hello 队列
	 * @return
	 */
	@Bean
	public Queue helloQueue() {
		return new Queue(Const.QUEUE_HELLO);
	}
	
	/**
	 * 向容器托管一个 sb.user 队列
	 * @return
	 */
	@Bean
	public Queue userQueue() {
		return new Queue(Const.QUEUE_USER);
	}
	
	/**
	 * 向容器托管一个 sb.info.email 队列
	 * @return
	 */
	@Bean
	public Queue emailQueue() {
		return new Queue(Const.QUEUE_TOPIC_EMAIL);
	}
	
	/**
	 * 向容器托管一个 sb.info.user 
	 * @return
	 */
	@Bean
	public Queue userTopicQueue() {
		return new Queue(Const.QUEUE_TOPIC_USER);
	}
	
	/**
	 * 定义一个 sb.exchange 交换器
	 * @return
	 */
	@Bean
	TopicExchange exchange() {
		return new TopicExchange(Const.EXCHANGE_TOPIC);
	}
	
	/**
	 * 将队列与交换器绑定，即将队列 QUEUE_TOPIC_EMAIL 与  EXCHANGE_TOPIC 绑定
	 * 
	 * @return
	 */
	@Bean
	Binding bindingEmailExchange() {
		return BindingBuilder
				.bind(emailQueue())
				.to(exchange())
				.with("sb.*.email");
	}
	
	@Bean
	Binding bindingUserExchange() {
		return BindingBuilder
				.bind(userQueue())
				.to(exchange())
				.with("sb.*.user");
	}
	
	/*
	//===============以下是验证Fanout Exchange的队列==========
	@Bean
	public Queue AMessage() {
		return new Queue(FANOUT_QUEUE+".A");
	}

	@Bean
	public Queue BMessage() {
		return new Queue(FANOUT_QUEUE+".B");
	}

	@Bean
	public Queue CMessage() {
		return new Queue(FANOUT_QUEUE+".C");
	}
	//===============以上是验证Fanout Exchange的队列==========

	FanoutExchange fanoutExchange() {
		return new FanoutExchange(FANOUT_EXCHANGE);
	}

	@Bean
	Binding bindingExchangeA(Queue AMessage,FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(AMessage).to(fanoutExchange);
	}

	@Bean
	Binding bindingExchangeB(Queue BMessage, FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(BMessage).to(fanoutExchange);
	}

	@Bean
	Binding bindingExchangeC(Queue CMessage, FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(CMessage).to(fanoutExchange);
	}
	*/

	/**
	 * 程序入口
	 * @param args
	 */
	public static void main(String[] args) {
		new SpringApplication(ProducerApp.class).run(args);
	}
	
}
