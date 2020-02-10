package com.boot.test1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.boot.test1.repo.AccountRepository;
import com.boot.test1.vo.Account;

@Service
public class AccountService implements UserDetailsService{
	
	@Autowired
	AccountRepository accounts;
	
	Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		log.info("## loadUserByUsername ##");
		
		Account account = accounts.findById(username);
		
		if( account == null ) {
			log.debug("## 계정정보가 존재하지 않습니다. ##");
			throw new UsernameNotFoundException(username);
		}
		return account;
	}
}
