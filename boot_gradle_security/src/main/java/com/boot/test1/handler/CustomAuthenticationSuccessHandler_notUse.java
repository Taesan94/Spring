package com.boot.test1.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

public class CustomAuthenticationSuccessHandler_notUse implements AuthenticationSuccessHandler {
	
	// RedirectStrategy은 화면을 이동하기위한 인터페이스이다.
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	private RequestCache requestCache = new HttpSessionRequestCache();
	
	private String targetUrlParameter ; 
	private String defaultUrl ;
	private boolean useReferer ;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	// constructor
	public CustomAuthenticationSuccessHandler_notUse(String targetUrlParameter, String defaultUrl ,boolean useReferer) {
		this.targetUrlParameter=targetUrlParameter;
		this.defaultUrl=defaultUrl;
		this.useReferer = useReferer;
		log.info(" targetUrlParameter : " + targetUrlParameter + ", defaultUrl : " + defaultUrl +", useReferer : " + useReferer );
	}
	
	// getter, settger
	public String getTargetUrlParameter() {
		return targetUrlParameter;
	}


	public void setTargetUrlParameter(String targetUrlParameter) {
		this.targetUrlParameter = targetUrlParameter;
	}


	public String getDefaultUrl() {
		return defaultUrl;
	}


	public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
	}


	public boolean isUseReferer() {
		return useReferer;
	}


	public void setUseReferer(boolean useReferer) {
		this.useReferer = useReferer;
	}


	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		log.info("######### onAuthenticationSuccess #########");
		
		clearAuthenticationAttributes(request);
		
		int intRedirectStrategy = decideRedirectStrategy(request,response);
		
		switch(intRedirectStrategy) {
		
		case 1: 
			useTargetUrl(request, response);
			break;
		case 2: 
			useSessionUrl(request, response);
			break;
		case 3: 
			useRefererUrl(request, response);
			break;
		default:
			useDefaultUrl(request, response);
		}
		
	}
	
	// protected void resultRedirectStrategy(HttpServletRequest request, HttpServletResponse response, Autentication authentication)
	
	/**
	 * 
	 * 인증 성공 후 어떤 URL로 redirect 할지를 결정한다.
	 * 
	 * 판단 기준은 targetUrlParameter 값을 읽은 URL이 존재할 경우 그것을 1순위
	 * 
	 * 1순위 URL이 없을 경우 Spring Security가 세션에 저장한 URL을 2순위
	 * 2순위 URL이 없을 경우 Request의 REFERER를 사용하고 그 REFERER URL이 존재할 경우 그 URL을 3순위
	 * 3순위 URL이 없을 경우 Default URL을 4순위로 한다.
	 * 
	 * @param request
	 * @param response
	 * @return 1 : targetUrlParameter 값을 읽은 URL
	 * 		   2 : Session에 저장되어 있는 URL
	 *  	   3 : referer 헤더에 있는 URL
	 *  	   0 : default URL
	 */
	
	private int decideRedirectStrategy(HttpServletRequest request, HttpServletResponse response) {
		
		int result = 0 ;
		
		SavedRequest savedRequest = requestCache.getRequest(request,  response);
		
		if ( !"".equals(targetUrlParameter) ) {
			
			String targetUrl = request.getParameter(targetUrlParameter);
			
			log.info(" request.getParameter(targetUrl) : [ "+ targetUrl +" ]");
			
			if(StringUtils.hasText(targetUrl)) {
				result = 1;
			}else {
				if (savedRequest != null ) result=2;
				else {
					String refererUrl = request.getHeader("REFERER");
					if ( useReferer && StringUtils.hasText(refererUrl)) result = 3;
					else result = 0 ;
				}
			}
			return result;
		}
		
		if( savedRequest != null ) {
			result = 2 ;
			return result ;
		}
		
		String refererUrl = request.getHeader("REFERER");
		if(useReferer && StringUtils.hasText(refererUrl)) result =3;
		else result = 0 ;
		
		return result;
	}
	
	/*
	 *  기본적으로 SpringSecurity는 로그인 실패하면 에러세션을 저장한다.
	 *  그러나, 예를들어 로그인을 5번시도하고 성공했다고해도 그 에러세션이 저장되어 있을것이다.
	 *  이 남아있는 에러세션을 지워주기위한 메서드이다.
	 */
	private void clearAuthenticationAttributes(HttpServletRequest request) {
		log.info("######### clearAuthenticationAttributes #########");
		
		HttpSession session = request.getSession(false);
		
		if ( session == null ) return;
		
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION); // SpringSecurity는 에러발생시 해당 key값을 사용한다.
	}
	
	private void useTargetUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("######### useTargetUrl #########");
		
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		
		if(savedRequest != null ) {
			requestCache.removeRequest(request, response);
		}
		
		String targetUrl = request.getParameter(targetUrlParameter);
		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	private void useSessionUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("######### useSessionUrl #########");
		
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		String targetUrl = savedRequest.getRedirectUrl();
		redirectStrategy.sendRedirect(request, response, targetUrl);
	}
	
	private void useRefererUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("######### useRefererUrl #########");
		
		String targetUrl = request.getHeader("REFERER");
		redirectStrategy.sendRedirect(request, response, targetUrl);
	}
	
	private void useDefaultUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("######### useDefaultUrl #########");
		
		redirectStrategy.sendRedirect(request, response, defaultUrl);
	}
	

}