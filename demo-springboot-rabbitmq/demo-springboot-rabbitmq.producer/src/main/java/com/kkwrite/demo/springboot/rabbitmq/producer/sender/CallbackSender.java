package com.kkwrite.demo.springboot.rabbitmq.producer.sender;

import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.kkwrite.demo.springboot.rabbitmq.common.constant.Const;

/** 
 * 发送消息回执确认
 *
 * @author Soosky Wang
 * @date 2018年10月28日 上午12:00:39 
 * @version 1.0.0
 */
@Component
public class CallbackSender implements RabbitTemplate.ConfirmCallback {

	@Autowired
    @Qualifier("callback")
	private RabbitTemplate rabbitTemplateNew;
	
	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		System.out.println("callback confirm: " + correlationData.getId());
	}
	
	public void send() {
		rabbitTemplateNew.setConfirmCallback(this);
        String msg = "callbackSender: I am callback sender";
        System.out.println(msg);
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        System.out.println("callbackSender UUID: " + correlationData.getId());
        this.rabbitTemplateNew.convertAndSend(Const.EXCHANGE_TOPIC, Const.RK_EMAIL, msg, correlationData);
    }

}
