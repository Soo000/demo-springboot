package com.kkwrite.demo.springboot.rabbitmq.common.pojo;

import java.io.Serializable;

/** 
 * User
 *
 * @author Soosky Wang
 * @date 2018年10月27日 下午11:08:32 
 * @version 1.0.0
 */
public class User implements Serializable {

	private static final long serialVersionUID = -7324455598898661379L;
	
	private Long userId;
	private String username;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
