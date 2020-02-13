package com.boot.test1.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
	
	private String loginIdName ;
	private String loginPasswordName ;
	private String loginRedirectName ;
	private String exceptionMsgName ;
	private String defaultFailureUrl ;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	public CustomAuthenticationFailureHandler(String loginIdName, String loginPasswordName, String loginRedirectName,
			String exceptionMsgName, String defaultFailureUrl) {
		this.loginIdName = loginIdName;
		this.loginPasswordName = loginPasswordName;
		this.loginRedirectName = loginRedirectName;
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
	public String getLoginRedirectName() {
		return loginRedirectName;
	}
	public void setLoginRedirectName(String loginRedirectName) {
		this.loginRedirectName = loginRedirectName;
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
		String loginRedirect = request.getParameter(loginRedirectName);
		
		log.info("loginId : " + loginId +", loginPw : " + loginPw +", loginRedirect : " + loginRedirect + ", exceptionMsgName : " + exceptionMsgName + ", defaultFailureUrl : " + defaultFailureUrl) ;
		
		request.setAttribute(loginIdName, loginId);
		request.setAttribute(loginPasswordName, loginPw);
		request.setAttribute(loginRedirectName, loginRedirect);
		
		request.setAttribute(exceptionMsgName, exception.getMessage());
		
		log.info(" exception.getMessage() : " + exception.getMessage() );
		
		request.getRequestDispatcher(defaultFailureUrl).forward(request, response);
	}
}
