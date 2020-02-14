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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
	
	private String loginIdName ;  			// 로그인 id값이 들어오는 input태그 name
	private String loginPasswordName ;		// 로그인 pw값이 들어오는 input태그 name
	private String loginRedirectUrl ;		// 로그인 성공시 redirect 할 URL이 지정되어 있는 input태그 name
	private String exceptionMsgName ;		// 예외 메시지를 REQUEST의 ATTRIBUTE에 저장할 때 사용
	private String defaultFailureUrl ;		// 화면에 보여줄 url(로그인 화면)
	
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
			errormsg = "ID 또는 PW가 일치하지 않습니다.";//messageSource.getMessage("msg.first", null , Locale.KOREA);
		} else if(exception instanceof InternalAuthenticationServiceException) {
			errormsg = "ID 또는 PW가 일치하지 않습니다.";//messageSource.getMessage("msg.first", null , Locale.KOREA);
		} else if(exception instanceof DisabledException) {
			errormsg = "계정이 비활성화되었습니다.";
		} else if(exception instanceof CredentialsExpiredException) {
			errormsg = "계정이 만료되었습니다.";
		} else if(exception instanceof UsernameNotFoundException) {
			errormsg = "계정 정보 혹은 계정의 권한정보가 존재하지 않습니다.";
		}
		
		request.setAttribute(loginIdName, loginId);
		request.setAttribute(loginPasswordName, loginPw);
		request.setAttribute(loginRedirectUrl, loginRedirect);
		request.setAttribute(exceptionMsgName, errormsg);
		
		log.info(" exception.getMessage() : " + exception.getMessage() );
		
		request.getRequestDispatcher(defaultFailureUrl).forward(request, response);
	}
}
