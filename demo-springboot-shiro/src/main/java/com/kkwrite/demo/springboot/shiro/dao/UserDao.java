package com.kkwrite.demo.springboot.shiro.dao;

import com.kkwrite.demo.springboot.shiro.entity.User;

public interface UserDao {
	
	User findById(String id);
	
	User findByUsername(String username);
	
}