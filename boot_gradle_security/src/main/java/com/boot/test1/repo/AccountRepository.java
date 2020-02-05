package com.boot.test1.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.boot.test1.mapper.AccountMapper;
import com.boot.test1.vo.Account;
import com.boot.test1.vo.Authority;

@Repository
public class AccountRepository {

	@Autowired
	AccountMapper accountMapper;

	public Account save(Account account, Authority authority ) {
		accountMapper.insertUser(account);
		accountMapper.insertUserAuthority(authority);
		return account;
	}

	public Account findById(String username) {
		System.out.println("############### findById ###############");
		return accountMapper.readAccount(username);
	}

	public List<String>findauthoritiesbyid(String username){
		System.out.println("############### findauthoritiesbyid ###############");
		return (List<String>)accountMapper.readAuthorites(username);
	}


}
