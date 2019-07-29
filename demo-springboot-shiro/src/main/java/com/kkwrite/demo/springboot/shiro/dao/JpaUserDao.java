package com.kkwrite.demo.springboot.shiro.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.kkwrite.demo.springboot.shiro.entity.User;

@Repository
public interface JpaUserDao extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

	User findByUsername(String username);
	
}
