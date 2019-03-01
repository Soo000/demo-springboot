package com.kkwrite.security.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginCtrl {
	
	@GetMapping("/toLoginPage")
	public String toLoginPage() {
		return "login";
	}

	@PostMapping("/dologin")
	public String doLogin(String username, String password) {
		System.out.println("username = " + username + " password = " + password);
		return "/html/home/home.html";
	}
	
	@GetMapping("/logout")
	public String logout() {
		return "redirect:/login";
	}
}
