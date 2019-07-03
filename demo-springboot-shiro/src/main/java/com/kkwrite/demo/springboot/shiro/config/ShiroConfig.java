package com.kkwrite.demo.springboot.shiro.config;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

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

import com.kkwrite.demo.springboot.shiro.filter.ShiroAuthFilter;
import com.kkwrite.demo.springboot.shiro.manager.MySessionManager;
import com.kkwrite.demo.springboot.shiro.realms.MyShiroRealm;

@Configuration
public class ShiroConfig {

	@Value("${spring.redis.host}")
	private String host;
	@Value("${spring.redis.port}")
	private int port;
	@Value("${spring.redis.timeout}")
	private int timeout;
	
	/**
	 * @param securityManager
	 * @return
	 */
	@Bean
	public ShiroFilterFactoryBean getShiroFilter(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        //注意过滤器配置顺序不能颠倒
        //配置退出过滤器，修改了默认logou过滤器，清除相应的缓存信息
        filterChainDefinitionMap.put("/easeApi/authc/user/change/Exit", "logout");
        // 配置需要拦截的链接
        filterChainDefinitionMap.put("/easeApi/authc/**", "ShiroAuthFilter");
        
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        // 自定义的登录过滤器
        filterMap.put("ShiroAuthFilter", new ShiroAuthFilter());
        
        shiroFilterFactoryBean.setFilters(filterMap);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
	}
	
	/**
     * 凭证匹配器
     * 由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5"); // 散列算法:这里使用MD5算法;
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
     * SecurityManager
     * @return
     */
	@Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager() {
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
     * 自定义sessionManager，使用redisSessionDAO生成并保存session
     */
    @Bean(name = "sessionManager")
    public SessionManager sessionManager() {
        MySessionManager mySessionManager = new MySessionManager();
        mySessionManager.setSessionDAO(redisSessionDAO());
        return mySessionManager;
    }
    
    /**
     * 配置shiro redisManager
     * @return
     */
    @ConfigurationProperties(prefix = "spring.redis")
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setDatabase(0);
        redisManager.setHost(host);
		redisManager.setPort(port);
		redisManager.setTimeout(timeout);
        return redisManager;
    }
    
    /**
     * cacheManager 缓存 redis实现
     * @return
     */
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }
    
    /**
     * 通过redis RedisSessionDAO shiro sessionDao层的实现 
     * 使用shiro-redis插件
     */
    @Bean("redisSessionDAO")
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }
    
    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
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
