package com.kkwrite.demo.springboot.shiro.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.kkwrite.demo.springboot.shiro.dao.UserDao;
import com.kkwrite.demo.springboot.shiro.entity.User;

@Repository
public class DefaultUserDao implements UserDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public User findById(String id) {
		String sql = "select user_id as id, username, password from user where user_id = ?";
		return jdbcTemplate.queryForObject(sql, User.class, id);
	}

	@Override
	public User findByUsername(String username) {
		String sql = "select user_id as id, username, password from t_user where username = ?";
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
		return jdbcTemplate.queryForObject(sql, rowMapper, username);
	}

}
