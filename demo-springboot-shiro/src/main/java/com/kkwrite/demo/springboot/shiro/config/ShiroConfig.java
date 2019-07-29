package com.kkwrite.demo.springboot.shiro.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kkwrite.demo.springboot.shiro.manager.MySessionManager;
import com.kkwrite.demo.springboot.shiro.realms.MyShiroRealm;

@Configuration
public class ShiroConfig {

	@Value("${spring.redis.host}")
	private String host;
	@Value("${spring.redis.port}")
	private int port;
	@Value("${spring.redis.database}")
	private int database;
	@Value("${spring.redis.timeout}")
	private int timeout;
	
	/**
     * 凭证匹配器
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5"); // 散列算法:这里使用md5(32位大)算法;
        hashedCredentialsMatcher.setHashIterations(1); // 散列的次数，比如散列两次，相当于 md5(md5(md5("")));
        return hashedCredentialsMatcher;
    }
    
    /**
     * 注入自定义的Realm类 
     **/
    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }
	
    /**
     * 安全管理器
     * @return
     */
	@Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager() {
		// 定义 SecurityManager
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 设置自定义 realm
        securityManager.setRealm(myShiroRealm());
        // 自定义session管理 使用redis
        securityManager.setSessionManager(sessionManager());
        // 自定义缓存实现 使用redis
        securityManager.setCacheManager(cacheManager());

        return securityManager;
    }
	
	/**
	 * shiro 过滤器工厂
	 * 在 web 程序中，shiro进行权限控制是通过一组过滤器集合来控制
	 * @param securityManager
	 * @return
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
		// 1.创建过滤器工厂
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		// 2.设置安全管理器
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		// 3.通用配置（跳转页面，未授权页面）
		shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized"); // 未授权跳转的页面		
		shiroFilterFactoryBean.setLoginUrl("/login"); // 未登录跳转的地址
		/*
		 * 4.配置过滤器集合
		 * key = 过滤过拦截的 url
		 * value = 过滤器类型
         * 注意过滤器配置顺序不能颠倒
		 */
        Map<String, String> filterMap = new LinkedHashMap<>();
		// 配置退出过滤器，修改了默认logou过滤器，清除相应的缓存信息 
        filterMap.put("/logout", "logout");
        // 可以匿名访问
        //filterMap.put("/user/home", "anon");
        /*
         * 使用过滤器的形式配置请求地址的权限
         * 具有某种权限才可以访问
         */
        //filterMap.put("/user/home", "perms[user-home]");
        /*
         * 使用过滤器的形式配置请求地址的权限
         * 具有某种角色才可以访问
         */
        //filterMap.put("/user/home", "roles[系统管理员]");
        // 认证之后才可以访问
        filterMap.put("/user/**", "authc");
        // 自定义的登录过滤器
        //filterMap.put("ShiroAuthFilter", new ShiroAuthFilter());
        // 5.过滤器集合放回到过滤器工厂
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
	}	
    
    /**
     * 1.redisManager
     * @return
     */
    @ConfigurationProperties(prefix = "spring.redis")
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
		redisManager.setPort(port);
		redisManager.setDatabase(database);
		redisManager.setTimeout(timeout);
        return redisManager;
    }
    
    /**
     * 2.通过 redisSessionDAO 操作 redis
     * 使用shiro-redis插件
     */
    @Bean("redisSessionDAO")
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }
    
    /**
     * 3.自定义sessionManager，使用 redisSessionDAO 生成并保存 session
     */
    @Bean(name = "sessionManager")
    public SessionManager sessionManager() {
        MySessionManager mySessionManager = new MySessionManager();
        mySessionManager.setSessionDAO(redisSessionDAO());
        return mySessionManager;
    }
    
    /**
     * 4.缓存管理（redis实现）
     * @return
     */
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }
    
    /**
     * 配置对 shiro 注解的支持.
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
    		@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = 
        		new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
	
}
