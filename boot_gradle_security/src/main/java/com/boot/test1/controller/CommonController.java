package com.boot.test1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.boot.test1.mapper.AccountMapper;

@Controller
public class CommonController {
	
	@Autowired
	AccountMapper accountMapper;
	
	@RequestMapping("/user")
	public String user() {
		return "userPage";
	}
	
	@RequestMapping("/member")
	public String member() {
		return "memberPage";
	}	
	
	@RequestMapping("/accessDenied_page")
	public String accessDenied() {
		return "accessDenied_page";
	}
	
	@RequestMapping("/admin")
	public String admin() {
		return "adminPage";
	}	
	
	@RequestMapping("/logout")
	public String logout() {
		return "logout";
	}	
	
}
