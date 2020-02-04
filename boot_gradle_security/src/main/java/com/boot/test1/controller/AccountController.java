package com.boot.test1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boot.test1.mapper.AccountMapper;
import com.boot.test1.service.AccountService;
import com.boot.test1.vo.Account;
import com.boot.test1.vo.Authority;

@RestController
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
	

}
