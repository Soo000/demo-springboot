package com.kkwrite.demo.springboot.shiro.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class MyHttpServletRequestWrapper extends HttpServletRequestWrapper {

	public MyHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	public void putHeader(String authorization, String sidVal) {
		
	}

}
