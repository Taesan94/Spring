package com.boot.test1.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import com.boot.test1.repo.AccountRepository;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
	private String username;
	private String defaultUrl;
	
	private RequestCache requestCache = new HttpSessionRequestCache();
	private RedirectStrategy redirectStragtegy = new DefaultRedirectStrategy();
	
	@Autowired
	AccountRepository accoutDao ;
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	// Getter, Setter
	public String getDefaultUrl() {
		return defaultUrl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
	}
	
	// Constructor
	public CustomAuthenticationSuccessHandler(String username , String defaultUrl){
		this.username = username;
		this.defaultUrl = defaultUrl;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		String requestUserName = request.getParameter(username);
		
		log.info( " requestUserName : " + requestUserName );
		
		// 실패회수 초기화
		accoutDao.resetFailCnt(requestUserName);
		
		// 에러세션 지우기
		clearAuthenticationAttributes(request);
		
		// Redirect URL 작업.
		resultRedirectStrategy(request, response, authentication);
		
	}
	
	// redirectUrl 지정 메서드
	protected void resultRedirectStrategy(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		
		if ( savedRequest != null ) {
			String targetUrl = savedRequest.getRedirectUrl();
			log.info( " GO !!! savedRequest.getRedirectUrl : " + targetUrl );
			redirectStragtegy.sendRedirect(request, response, targetUrl);
		}else {
			log.info( " GO !!! savedRequest.getRedirectUrl : " + defaultUrl );
			redirectStragtegy.sendRedirect(request, response, defaultUrl);
		}
	}
	
	// 남아있는 에러세션이 있다면 지워준다.
	protected void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if( session == null ) return ;
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}
	

}