package com.kkwrite.demo.springboot.shiro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kkwrite.demo.springboot.shiro.dao.JpaUserDao;
import com.kkwrite.demo.springboot.shiro.entity.User;

@Service("userService")
public class UserService {

	//@Resource(name = "defaultUserDao")
	//private UserDao userDao;
	
	@Autowired
	private JpaUserDao userDao; 
	
	public User findById(String id) {
		return userDao.getOne(id);
	}
	
	public User findByName(String username) {
		return userDao.findByUsername(username);
	}
	
}