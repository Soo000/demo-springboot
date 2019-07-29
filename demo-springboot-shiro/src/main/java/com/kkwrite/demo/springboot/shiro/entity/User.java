package com.kkwrite.demo.springboot.shiro.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.crazycake.shiro.AuthCachePrincipal;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 用户实体
 * 实现 AuthCachePrincipal接口，可以把数据存放到 redis 中
 *
 */
@Table(name = "pe_user")
@Entity
public class User implements Serializable, AuthCachePrincipal {

	private static final long serialVersionUID = 6886411976094942276L;

	@Id
	private String id;
	@NotEmpty(message = "账号不能为空")
	@Size(min = 3, max = 20)
	@Column(nullable = false, length = 20, unique = true)
	private String username;

	@NotEmpty(message = "密码不能为空")
	@Size(max = 100)
	@Column(length = 100)
	private String password;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "pe_user_role", 
		joinColumns={@JoinColumn(name = "user_id", referencedColumnName="id")}, 
		inverseJoinColumns= {@JoinColumn(name = "role_id", referencedColumnName = "id")})
	private Set<Role> roles = new HashSet<Role>();

	public User() {
	}

	public User(String username) {
		this.username = username;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	/**
	 * 存放到 redis 中的 key(不指定有默认值)
	 */
	@Override
	public String getAuthCacheKey() {
		return null;
	}

}
