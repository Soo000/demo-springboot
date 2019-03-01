package com.kkwrite.demo.springboot.rabbitmq.consumer.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/10/30
 * 创建时间: 11:10
 */
@Component
@RabbitListener(queues = "sb.info.user")
public class TopicUserMessageReceiver {

    @RabbitHandler
    public void process(String msg) {
        System.out.println("TopicUserMessageReceiver: " + msg);
    }

}