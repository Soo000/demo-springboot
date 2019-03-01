package com.kkwrite.jsp.ctrl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** 
 * 类说明 
 *
 * @author Soosky Wang
 * @date 2018年7月24日 下午5:51:32 
 * @version 1.0.0
 */
@RestController
public class HelloCtrl {

	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}
	
}
