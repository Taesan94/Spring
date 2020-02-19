package com.boot.test1.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.boot.test1.service.AccountService;
import com.boot.test1.vo.Account;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		log.info("### authenticate ### ");

		String username = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();
		
		Account account = (Account) accountService.loadUserByUsername(username);
		
		// pw같은지 검증.
		if ( !passwordEncoder.matches(password,account.getPassword())) {
			throw new BadCredentialsException(username);
		}else if(!account.isEnabled()) {
			throw new DisabledException(username);
		}else if(!account.isAccountNonExpired()) {
			throw new AccountExpiredException(username);
		}else if(!account.isAccountNonLocked()) {
			throw new LockedException(username);
		}else if(!account.isCredentialsNonExpired()) {
			throw new CredentialsExpiredException(username);
		}

		return new UsernamePasswordAuthenticationToken(account, account, account.getAuthorities());
	}


	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
