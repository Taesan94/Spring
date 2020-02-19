package com.boot.test1.handler;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.boot.test1.repo.AccountRepository;
import com.boot.test1.vo.Account;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
	
	private String loginIdName ;  			// 로그인 id값이 들어오는 input태그 name
	private String loginPasswordName ;		// 로그인 pw값이 들어오는 input태그 name
	private String loginRedirectUrl ;		// 로그인 성공시 redirect 할 URL이 지정되어 있는 input태그 name
	private String exceptionMsgName ;		// 예외 메시지를 REQUEST의 ATTRIBUTE에 저장할 때 사용
	private String defaultFailureUrl ;		// 화면에 보여줄 url(로그인 화면)
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	AccountRepository accoutDao;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	public CustomAuthenticationFailureHandler(String loginIdName, String loginPasswordName, String loginRedirectUrl,
			String exceptionMsgName, String defaultFailureUrl) {
		this.loginIdName = loginIdName;
		this.loginPasswordName = loginPasswordName;
		this.loginRedirectUrl = loginRedirectUrl;
		this.exceptionMsgName  = exceptionMsgName;
		this.defaultFailureUrl = defaultFailureUrl;
	}

	public String getLoginIdName() {
		return loginIdName;
	}
	public void setLoginIdName(String loginIdName) {
		this.loginIdName = loginIdName;
	}
	public String getLoginPasswordName() {
		return loginPasswordName;
	}
	public void setLoginPasswordName(String loginPasswordName) {
		this.loginPasswordName = loginPasswordName;
	}
	public String getLoginRedirectUrl() {
		return loginRedirectUrl;
	}
	public void setLoginRedirectUrl(String loginRedirectUrl) {
		this.loginRedirectUrl = loginRedirectUrl;
	}
	public String getExceptionMsgName() {
		return exceptionMsgName;
	}
	public void setExceptionMsgName(String exceptionMsgName) {
		this.exceptionMsgName = exceptionMsgName;
	}
	public String getDefaultFailureUrl() {
		return defaultFailureUrl;
	}
	public void setDefaultFailureUrl(String defaultFailureUrl) {
		this.defaultFailureUrl = defaultFailureUrl;
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		log.info("######### onAuthenticationFailure #########");
		
		String loginId = request.getParameter(loginIdName);
		String loginPw = request.getParameter(loginPasswordName);
		String loginRedirect = request.getParameter(loginRedirectUrl);
		String errormsg = exception.getMessage();
		
		if(exception instanceof BadCredentialsException) {
			// 잠긴계정인지 확인하여, errormsg변경해준다.
			boolean userUnLock = true;
			userUnLock = failCnt(loginId);
			if ( !userUnLock )
				errormsg = messageSource.getMessage("AccountStatusUserDetailsChecker.disabled", null , Locale.KOREA);
			else
				errormsg = messageSource.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", null , Locale.KOREA);
		} else if(exception instanceof InternalAuthenticationServiceException) {
			errormsg = messageSource.getMessage("AbstractUserDetailsAuthenticationProvider.InternalAuthentication", null , Locale.KOREA); 
		} else if(exception instanceof DisabledException) {
			errormsg = messageSource.getMessage("AccountStatusUserDetailsChecker.disabled", null , Locale.KOREA);
		} else if(exception instanceof CredentialsExpiredException) {
			errormsg = messageSource.getMessage("AccountStatusUserDetailsChecker.expired", null , Locale.KOREA);
		} else if(exception instanceof UsernameNotFoundException) {
			Object[] args = new String[] { loginId } ;
			errormsg = messageSource.getMessage("DigestAuthenticationFilter.usernameNotFound", args , Locale.KOREA);
		} else if(exception instanceof AccountExpiredException) {
			errormsg = messageSource.getMessage("AbstractUserDetailsAuthenticationProvider.expired", null , Locale.KOREA);
		} else if(exception instanceof LockedException) {
			errormsg = messageSource.getMessage("AbstractUserDetailsAuthenticationProvider.locked", null , Locale.KOREA);
		} 
		
		request.setAttribute(loginIdName, loginId);
		request.setAttribute(loginPasswordName, loginPw);
		request.setAttribute(loginRedirectUrl, loginRedirect);
		request.setAttribute(exceptionMsgName, errormsg);
		
		log.info(" exception.getMessage() : " + exception.getMessage() );
		
		request.getRequestDispatcher(defaultFailureUrl).forward(request, response);
	}
	
	private boolean failCnt(String loginId) {
		
		// 계정이 잠겼으면 추가로 실패횟수 증가시키지않고, true를 return한다.
		boolean userUnLock = true;

		// 실패횟수 select
		Account account = accoutDao.getUserInfo(loginId);
		userUnLock = account.isEnabled();

		// 계정이 활성화 되어있는 경우에만 실패횟수와, Enabled설정을 변경한다.
		// Enabled설정은 실패횟수가 5이상일 때 바뀐다.
		if ( userUnLock ) {
			if( account.getFailCnt() < 5 )
				accoutDao.loginFailCnt(loginId);
			else
				accoutDao.changeEnabled(loginId);
		}
		return userUnLock;
	}
}
