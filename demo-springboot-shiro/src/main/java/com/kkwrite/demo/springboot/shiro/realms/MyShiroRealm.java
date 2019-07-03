package com.kkwrite.demo.springboot.shiro.realms;

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

import com.kkwrite.demo.springboot.shiro.dao.UserDao;
import com.kkwrite.demo.springboot.shiro.service.UserService;
import com.kkwrite.demo.springboot.shiro.utils.EncryUtils;

/**
 * 自定义Realm
 */
public class MyShiroRealm extends AuthorizingRealm {

	private static final Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

	@Autowired
	private UserService userService;

	@Autowired
	private UserDao userDao;

	// 通过用户名查找用户拥有权限
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 获取SimpleAuthenticationInfo中传来的username
		/*String username = (String) principals.getPrimaryPrincipal();
		Map<String, Object> map = acquire.getHashMap("username", username);
		// 得到用户实体
		UserPrivacy userPrivacy = (UserPrivacy) userService.GetMsgformTable(3, map);
		// 得到用户身份的代码
		String role = userPrivacy.getRoleCode();
		// 通过身份代码查找对应的权限代码的集合
		List<String> perCodeList = userDao.GetRoles(role);
		// 权限代码的集合查找对应的权限表达式 如 "listen:high"
		List<String> permission = userDao.GetPermission(perCodeList);
		// 添加用户权限
		authorizationInfo.addStringPermissions(permission);
		// 添加用户角色
		authorizationInfo.addRole(role);*/
		
		AuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		return authorizationInfo;
	}

	// 身份认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		logger.info("成功进入ShiroRealm认证器");
		// 从token中获取用户名.
		String username = (String) token.getPrincipal();
		
		/*SimpleAuthenticationInfo authenticationInfo;
		Map<String, Object> map = acquire.getHashMap("username", username);
		// 获取用户信息
		UserPrivacy userPrivacy = (UserPrivacy) userService.GetMsgformTable(3, map);
		if (userPrivacy != null) {
			// 用户存在，检查用户状态 code = 0 为保护状态
			Result result = userService.findNumcheck(username);
			int CODE = result.getStatus();
			if (CODE == 0) {
				throw new LockedAccountException();
			} else {
				// 用户存在，且不为保护状态，对密码进行md5转码并与前端传来的password比对
				String password = EncryUtils.getMD5(userPrivacy.getPassword().getBytes());
				// 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
				authenticationInfo = new SimpleAuthenticationInfo(username, password, getName());// getName() realm name
			}
		} else {
			return null;
		}*/
		
		// 用户存在，对密码进行md5转码并与前端传来的password比对
		String password = EncryUtils.getMD5("admin");
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, password, getName());
		return authenticationInfo;
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
