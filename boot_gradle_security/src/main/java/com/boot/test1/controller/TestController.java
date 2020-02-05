package com.boot.test1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.boot.test1.mapper.AccountMapper;
import com.boot.test1.mapper.TestMapper;

@Controller
public class TestController {
	
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
	
	@RequestMapping("/admin")
	public String admin() {
		return "adminPage";
	}	
	
}
