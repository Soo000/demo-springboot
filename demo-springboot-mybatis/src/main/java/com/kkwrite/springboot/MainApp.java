package com.kkwrite.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**
 * 启动类
 * 配置 fastjson 
 * 在启动类中注入Bean: HttpMessageConverters
 */
@SpringBootApplication
//@MapperScan("com.kkwrite.springboot.*.mapper") // 扫描 mybatis mapper 类
@MapperScan("com.kkwrite.springboot.mapper") // 扫描 mybatis mapper 类
public class MainApp {

	/**
	 * 使用 @Bean 注入 fastJsonHttpMessageConverter
	 * @return
	 */
	@Bean
	public HttpMessageConverters fastJsonHttpMessageConverters() {
		// 定义一个 convert 转换对象
		FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
		// 添加 fastjson 配置信息，是否需要格式化返回 json 数据
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
		// 在 convert 中添加配置信息
		converter.setFastJsonConfig(fastJsonConfig);
		
		HttpMessageConverter<?> httpMessageConverter = converter;
		return new HttpMessageConverters(httpMessageConverter);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(MainApp.class, args);
	}

}
