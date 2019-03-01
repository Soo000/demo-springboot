package com.kkwrite.jpa.ctrl;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.kkwrite.jpa.pojo.UsersDO;
import com.kkwrite.jpa.repository.UsersRepository;

/** 
 * 类说明 
 *
 * @author Soosky Wang
 * @date 2018年7月24日 下午5:29:50 
 * @version 1.0.0
 */
@RestController
public class HelloCtrl {

	@Resource(name = "usersRepository")
	private UsersRepository usersRepository;
	
	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}
	
	@GetMapping("/getuser/{username}")
	public UsersDO getUser(@PathVariable String username) {
		return usersRepository.findByUsername(username);
	}
	
}
