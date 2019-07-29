package com.kkwrite.demo.springboot.shiro.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kkwrite.demo.springboot.shiro.entity.User;
import com.kkwrite.demo.springboot.shiro.filter.ShiroAutoAuthFilter;
import com.kkwrite.demo.springboot.shiro.http.MyHttpServletRequestWrapper;

/**
 * 包括判断是否认证的方法和获取缓存用户信息，和login+设置cookie的操作
 * 
 * @author Soosky
 */
public class ShiroTool {

	private static final Logger logger = LoggerFactory.getLogger(ShiroTool.class);

	/**
	 * 验证是否登陆
	 */
	public static boolean isAuthenticated(String sessionID, HttpServletRequest request, HttpServletResponse response) {
		boolean status = false;

		SessionKey key = new WebSessionKey(sessionID, request, response);
		try {
			Session se = SecurityUtils.getSecurityManager().getSession(key);
			Object obj = se.getAttribute(DefaultSubjectContext.AUTHENTICATED_SESSION_KEY);
			if (obj != null) {
				status = (Boolean) obj;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Session se = null;
			Object obj = null;
		}

		return status;
	}

	/**
	 * 获取用户登录信息
	 */
	public static User getUserPrivacy(String sessionID, HttpServletRequest request,
			HttpServletResponse response) {
		/*boolean status = false;
		SessionKey key = new WebSessionKey(sessionID, request, response);
		try {
			Session se = SecurityUtils.getSecurityManager().getSession(key);
			Object obj = se.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
			SimplePrincipalCollection coll = (SimplePrincipalCollection) obj;
			return (UserPrivacy) coll.getPrimaryPrincipal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}*/
		return null;
	}

	public static void authLogin(MyHttpServletRequestWrapper request, HttpServletResponse response, String username,
			String password) {
		// JSESSIONID为 :true 执行登录操作
		logger.info("Shiro执行登录操作");
		Subject subject = SecurityUtils.getSubject();
		String sidVal = (String) subject.getSession().getId();
		Cookie SIDCookie = CookieTool.setCookie(ShiroAutoAuthFilter.SESSIONID, sidVal, ShiroAutoAuthFilter.MAXAGE);
		CookieTool.addCookie(response, SIDCookie, true);
		request.putHeader(ShiroAutoAuthFilter.AUTHORIZATION, sidVal);		
		logger.info("subject生成的sessionID   ：" + sidVal);
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		subject.login(token);
	}

}
