package com.boot.test1.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.boot.test1.vo.Account;
import com.boot.test1.vo.Authority;

@Mapper
public interface AccountMapper {
	
	Account readAccount(String id);
	
	List<String> readAuthorites(String id);
	
	void insertUser(Account account);
	
	void insertUserAuthority(Authority authority);
	
	List<Account> readAllUsers();
	
	
}
