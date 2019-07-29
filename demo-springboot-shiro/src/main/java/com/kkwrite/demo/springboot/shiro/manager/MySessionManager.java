package com.kkwrite.demo.springboot.shiro.manager;

import java.io.Serializable;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

/**
 * 自定义 Shiro 的会话管理器
 */
public class MySessionManager extends DefaultWebSessionManager {

	/**
	 * 重写获取 sessionId 的方法
	 * 请求头 Authorization 中具有 sessionid 信息
	 */
	protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
		// 获取请求头 Authorization 中的数据
		String id = WebUtils.toHttp(request).getHeader("Authorization");
		if (StringUtils.isEmpty(id)) {
			// 第一次登录，如果没有携带，需要生成新的 sessionId
			return super.getSessionId(request, response);
		} else {
			// 设置 sessionId 的来源（从请求头中获取）
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, "header");
			// 设置 sessionId 的值
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
			// 设置 sessionId 是否需要验证
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
			
			// 返回sessionId；
			return id;
		}
	}
}
