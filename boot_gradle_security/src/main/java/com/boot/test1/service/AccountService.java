package com.boot.test1.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.boot.test1.repo.AccountRepository;
import com.boot.test1.vo.Account;
import com.boot.test1.vo.Authority;

@Service
public class AccountService implements UserDetailsService{
	
	@Autowired
	AccountRepository accounts;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		System.out.println("############### loadUserByUsername ###############");
		
		Account account = accounts.findById(username);
		
		if( account == null ) {
			System.out.println("존재하지않는 ID 입니다.");
			throw new UsernameNotFoundException(" 존재하지않는 ID 입니다.");
		}
		  
		account.setAuthorities(getAuthorities(username));
		
		return account;
	}
	
	public Account save(Account account, Authority authority) {
		
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		account.setAccountNonExpired(true);
		account.setAccountNonLocked(true);
		account.setCredentialsNonExpired(true);
		account.setEnabled(true);

		return accounts.save(account, authority);
	}

	private Collection<? extends GrantedAuthority> getAuthorities(String username) {
		
		List<String> string_authorities = accounts.findauthoritiesbyid(username);
		
		if( string_authorities == null ) {
			System.out.println(" 해당 계정에지정된 권한이 존재하지 않습니다. ");
			throw new UsernameNotFoundException(" 해당 계정에지정된 권한이 존재하지 않습니다. ");
		}
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for ( String authority : string_authorities ) {
			authorities.add(new SimpleGrantedAuthority(authority));
		}
		return authorities;
	}
}
