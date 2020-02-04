package com.boot.test1.controller;

import javax.servlet.http.HttpServletRequest;

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
	
	// ADMIN 계정 부여
	@RequestMapping("/create")
	public Account create() {
		
		String adminId = "admin";
		
		Account account = new Account();
		account.setId(adminId);
		account.setPassword("1234");
		
		Authority authority = new Authority();
		
		authority.setUserName(adminId);
		authority.setAuthorityName("ROLE_ADMIN");
		
		accountService.save(account, authority);
		
		return account;
	}
	
	// LOGIN
	@RequestMapping(value = "/login", method=RequestMethod.GET )
	public String login(Model model, HttpServletRequest req) {
		
		System.out.println("### /login 입니다 ");
		
		model.addAttribute( "message", req.getServletContext() );
		
		return "loginPage";
	}
	
	// LOGIN SUCCESS	
	@GetMapping("/loginSuccess")
	@ResponseBody
	public String loginSuccess(HttpServletRequest req) {
		return "SUCCESS다 !";
	}

}
