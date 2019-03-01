package com.kkwrite.jpa.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;

/** 
 * GlyUserDO
 *
 * @author Soosky Wang
 * @date 2018年7月24日 下午5:36:46 
 * @version 1.0.0
 */
@Entity(name = "users")
public class UsersDO {

	@Id
	private String username;
	private String password;
	private Integer enabled;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getEnabled() {
		return enabled;
	}
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	
	@Override
	public String toString() {
		return "GlyUserDO [username=" + username + ", password=" + password + ", enabled=" + enabled + "]";
	}
}
