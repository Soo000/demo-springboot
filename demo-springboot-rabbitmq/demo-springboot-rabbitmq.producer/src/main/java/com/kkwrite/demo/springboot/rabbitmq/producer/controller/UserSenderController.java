package com.kkwrite.demo.springboot.rabbitmq.producer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kkwrite.demo.springboot.rabbitmq.producer.sender.UserSender;

/** 
 * Hello Controller
 *
 * @author Soosky Wang
 * @date 2018年10月27日 下午11:29:22 
 * @version 1.0.0
 */
@RestController
public class UserSenderController {

	@Autowired
	private UserSender userSender;
	
	@GetMapping("/sendUser")
	public String sendUser() {
		userSender.send();
		return "ok";
	}
	
}
