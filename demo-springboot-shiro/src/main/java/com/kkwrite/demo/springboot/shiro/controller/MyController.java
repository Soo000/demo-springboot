package com.kkwrite.demo.springboot.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/easeApi")
public class MyController {

	/**
	 * 用户登录
	 * 
	 * @param request
	 * @param username
	 * @return
	 */
	@RequestMapping("/auth/login")
	public Object login(String username, String password) {
		JSONObject jsonObject = new JSONObject();
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
			subject.login(token);
			jsonObject.put("token", subject.getSession().getId());
			jsonObject.put("msg", "登录成功");
		} catch (IncorrectCredentialsException e) {
			jsonObject.put("msg", "密码错误");
		} catch (LockedAccountException e) {
			jsonObject.put("msg", "登录失败，该用户已被冻结");
		} catch (AuthenticationException e) {
			jsonObject.put("msg", "该用户不存在");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return jsonObject;
	}

	@RequestMapping(value = "/authc/getUser")
	// 此处为了方便用1,2,3来代表用户身份
	@RequiresRoles(value = { "2" }, logical = Logical.OR)
	@RequiresPermissions("listen:high")
	public Object getUser() {
		return "Success";
	}

	/*@RequestMapping(value = "/authc/changeData")
	@RequiresRoles(value = "1")
	public Object changeData() {
		UserPrivacy userPrivacy = new UserPrivacy();
		userPrivacy.setRoleCode("2"); // 修改用户身份
		String username = "123456";
		boolean bol = userDao.UpdateToPrivacy(userPrivacy, "username", username);
		return bol;
	}*/
}
