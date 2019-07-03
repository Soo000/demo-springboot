package com.kkwrite.demo.springboot.redis.oper;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class Operator {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@PostConstruct
	public void postOperator() {
		redisTemplate.opsForValue().set("name", "wangke");
	}
	
}
