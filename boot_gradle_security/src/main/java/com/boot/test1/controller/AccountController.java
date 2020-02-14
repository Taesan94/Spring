package com.boot.test1.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boot.test1.mapper.AccountMapper;
import com.boot.test1.service.AccountService;
import com.boot.test1.vo.Account;
import com.boot.test1.vo.Authority;

@Controller
public class AccountController {
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	AccountMapper accountMapper;
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	// ADMIN 계정 부여, 수동으로 계정등록.. 계정정보 하드코딩해서..
	@RequestMapping("/create")
	public Account create() {
		
		String adminId = "admin";
		
		Account account = new Account();
		account.setId(adminId);
		account.setPassword("1234");
		
		Authority authority = new Authority();
		
		authority.setUserName(adminId);
		authority.setAuthorityName("ROLE_ADMIN");
		
		// accountService.save(account, authority);
		
		return account;
	}
	
	// LOGIN
	@RequestMapping(value = "/login" )
	public String login(Model model, HttpServletRequest req) {
		
		log.info("### /login 입니다 ");
		return "loginPage";
	}
	
	// LOGIN SUCCESS	
	@GetMapping("/loginSuccess")
	@ResponseBody
	public String loginSuccess() {
		return "SUCCESS !";
	}
	
	// LOGIN SUCCESS	
	@GetMapping("/loginFail")
	@ResponseBody
	public String loginFail() {
		return "Fail !";
	}
	

}
