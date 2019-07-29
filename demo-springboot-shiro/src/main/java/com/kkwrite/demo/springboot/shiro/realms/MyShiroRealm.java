package com.kkwrite.demo.springboot.shiro.realms;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.kkwrite.demo.springboot.shiro.entity.Permission;
import com.kkwrite.demo.springboot.shiro.entity.Role;
import com.kkwrite.demo.springboot.shiro.entity.User;
import com.kkwrite.demo.springboot.shiro.service.UserService;

/**
 * 自定义Realm
 */
public class MyShiroRealm extends AuthorizingRealm {

	private static final Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

	@Autowired
	private UserService userService;
	
	@Override
	public void setName(String name) {
		super.setName("MyShiroRealm");
	}

	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		logger.info("进入 ShiroRealm 授权器");
		//User user = (User) principals.getPrimaryPrincipal();
		String username = (String) principals.getPrimaryPrincipal();
		User user = userService.findByName(username);
		// 角色集合
		Set<String> roles = new HashSet<>();
		// 权限集合
		Set<String> permissions = new HashSet<>();
		for (Role role: user.getRoles()) {
			roles.add(role.getName());
			for (Permission permission: role.getPermissions()) {
				permissions.add(permission.getCode());
			}
		}
		
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		// 添加用户角色
		authorizationInfo.addRoles(roles);
		// 添加用户权限
		authorizationInfo.setStringPermissions(permissions);
		
		return authorizationInfo;
	}

	/**
	 * 认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		logger.info(" 进入ShiroRealm 认证器");
		// 从token中获取用户名.
		String username = (String) token.getPrincipal();
		
		// 获取用户信息
		User user = userService.findByName(username);
		if (user != null) {
			String password = user.getPassword();
			// 用户存在，对密码进行md5转码并与前端传来的password比对
			//password = EncryUtils.getMD5(user.getPassword());
			return new SimpleAuthenticationInfo(username, password, getName()); // getName() realm name	
		}
		
		return null;
	}

	/**
	 * 通过用户名清除缓存
	 */
	public void clearCache(String username) {
		System.out.println("调用cache清理操作");
		PrincipalCollection principals = new SimplePrincipalCollection(username, getName());
		clearCache(principals);
	}

	@Override
	public void clearCache(PrincipalCollection principals) {
		super.clearCache(principals);
	}

}
