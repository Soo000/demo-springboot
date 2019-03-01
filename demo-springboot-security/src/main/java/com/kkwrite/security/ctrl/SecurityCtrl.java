package com.kkwrite.security.ctrl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** 
 * SecurityCtrl Controller 
 *
 * @author Soosky Wang
 * @date 2018年7月24日 下午3:40:55 
 * @version 1.0.0
 */
@RestController
public class SecurityCtrl {

	@GetMapping("/index")
	public String index() {
		return "index";
	}
	
	@GetMapping("/home")
	public String home() {
		return "home";
	}
	
	@GetMapping("/admin")
	public String admin() {
		return "admin";
	}
}
