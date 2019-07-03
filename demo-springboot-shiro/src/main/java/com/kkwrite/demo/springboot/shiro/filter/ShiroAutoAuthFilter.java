package com.kkwrite.demo.springboot.shiro.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.alibaba.fastjson.JSON;
import com.kkwrite.demo.springboot.shiro.dao.UserDao;
import com.kkwrite.demo.springboot.shiro.http.MyHttpServletRequestWrapper;
import com.kkwrite.demo.springboot.shiro.utils.CookieTool;
import com.kkwrite.demo.springboot.shiro.utils.ShiroTool;

/**
 * 自定义的WebFilter过滤器，该项目中用于基于cookie的自动登录场景，在session过期或者关闭浏览器时，session失效或不存在时，
 * 在用户cookie尚存的情况下，再次请求被保护的资源时，重新获取认证，
 * 在Vue单页面的情况下无需刷新页面重新获取JSESSIONID，该类在JSESSIONID不存在时会自动生成cookie分配到前端页面中
 * 
 * @author Soosky
 */
@WebFilter(filterName = "shiroAutoAuthFilter", urlPatterns = {"/easeApi/authc/*"})
public class ShiroAutoAuthFilter implements Filter, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(ShiroAutoAuthFilter.class);
    
    ApplicationContext applicationContext;

    @Autowired
    private RedisUtil redisUtil;
    
    @Autowired
    private UserDao userDao;

    public static final String SESSIONID = "JSESSIONID";

    public static final int MAXAGE = 1800;

    public static final String AUTHORIZATION = "Authorization";

    @SuppressWarnings("deprecation")
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) 
    		throws IOException, ServletException {
        logger.info("shiroAutoAuthFilter被调用");
        
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        // 判断用于自动登录的cookie是否存在
        // Cookie UIDcookie = com.music.Tools.CookieTool.getCookieByName(request, "UID");
        Cookie UIDcookie = CookieTool.getCookieByName(request, "UID");
        // 用户给request添加header信息 添加Authorization头，保证此次请求不被拦截，实现不刷新页面自动认证的关键
        MyHttpServletRequestWrapper httpReq = new MyHttpServletRequestWrapper(request);
        if (UIDcookie != null) {
            //获取securityManager管理器
        	DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) applicationContext.getBean("securityManager");
            SecurityUtils.setSecurityManager(securityManager);
            String enUser = URLDecoder.decode(UIDcookie.getValue());            
            // 获得到username和password
            String[] userArray = com.music.Tools.PBEUtils.decrypt(enUser).split("_");
            String username = userArray[0];
            String password = userArray[1];
            Map<String, Object> map = acquire.getHashMap("username,password",username+","+password);
            //检查帐号密码是否有效
            boolean empty = userDao.QueryforPrivacy(map);
            if (!empty) {
                //拦截返回提示
                authcReq(response);
                return;
            }
            //获取当前JSESSIONID，判断是否存在
            Cookie SUID = CookieTool.getCookieByName(request, SESSIONID);
            if(SUID == null) {
                logger.info("JSESSIONID为空直接执行登录操作");
                //JSESSIONID为空直接执行登录操作,并设置JSESSIONID至前端，实现不刷新自动认证
                ShiroTool.authLogin(httpReq, response, username, password);
                arg2.doFilter(httpReq, response);
                return;
            }
            //判断 JSESSIONID 是否存在redis中 
            boolean bol = redisUtil.hasKey(3, "shiro:session:"+SUID.getValue());
            //redis检测JSESSIONID结果为若为false则调用登录操作
            logger.info("redis检测JSESSIONID结果为  :"+bol);
            if(!bol){
                //不存在执行登录操作
                ShiroTool.authLogin(httpReq, response, username, password);
            } else {
                // 存在，判断是否获得认证
                logger.info("JSESSIONID存在，验证是否已认证");
                boolean auth = ShiroTool.isAuthenticated(SUID.getValue(), request, response);
                if (!auth){
                    // 否，获取认证
                    logger.info("JSESSIONID未认证，执行登录操作");
                  
                    ShiroTool.authLogin(httpReq, response, username, password);
                }else {
                    // 是，打印消息
                    logger.info("JSESSIONID已认证");
                }
            }
        }
        
        chain.doFilter(httpReq, response);
    }

    private void authcReq(HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        Map<String, Object> map= new HashMap<>();
        map.put("status", 4);
        map.put("msg", "未找到用户信息");
        writer.write(JSON.toJSONString(map));
        writer.close();
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
    @Override
    public void destroy() {
    }

}
