package com.kkwrite.demo.springboot.shiro.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieTool {

	public static void addCookie(HttpServletResponse response, Cookie sIDCookie, boolean b) {

	}

	public static Cookie setCookie(String sessionid, String sidVal, int maxage) {
		return null;
	}

	/**
	 * description：获得或删除指定Cookie的值
	 * 
	 * @param request  请求对象
	 * @param response 响应对象
	 * @param name     名字
	 * @param isRemove 是否移除
	 * @return 值
	 */
	public static String getCookie(HttpServletRequest request, HttpServletResponse response, 
			String name, boolean isRemove) {
		String value = null;
		Cookie[] cookies = request.getCookies();// 获取Cookie数组
		if (cookies != null) {
			for (Cookie cookie: cookies) { // 循环
				if (cookie.getName().equals(name)) { // 根据name获取
					try {
						value = URLDecoder.decode(cookie.getValue(), "utf-8"); // 解码
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					if (isRemove) { // 是否删除
						cookie.setMaxAge(0);
						response.addCookie(cookie);
					}
				}
			}
		}
		return value;
	}

	/**
	 * description：设置 Cookie
	 * 
	 * @param name   名称
	 * @param value  值
	 * @param maxAge 生存时间（单位秒）
	 * @param uri    路径
	 */
	public static void setCookie(HttpServletResponse response, 
			String name, String value, String path, int maxAge) {
		Cookie cookie = new Cookie(name, null);
		cookie.setPath(path); // 详情参考
		cookie.setMaxAge(maxAge);
		try {
			cookie.setValue(URLEncoder.encode(value, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response.addCookie(cookie);
	}

	public static Cookie getCookieByName(HttpServletRequest request, String string) {
		return null;
	}

}
