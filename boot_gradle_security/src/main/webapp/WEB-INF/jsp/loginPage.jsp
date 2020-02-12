<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@ page import="org.springframework.security.core.Authentication"%>

<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="com.boot.test1.vo.Account" %>

<%
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();

	Object principal = auth.getPrincipal();
	String name = "";
	
	if ( principal instanceof Account ) System.out.println(" Accout 객체 맞음 !! ");
	else System.out.println(" Accout 객체 아님.. " + principal.getClass().toString());
	
	if ( principal != null && principal instanceof Account ){
		name = ((Account)principal).getUsername();
		System.out.println(" Account에서 가지고온 이름 ! : " + name);
	}else {
		name = (String)principal ;
		System.out.println(" 그냥 String.. ㅜㅜ : " + name);
	}
%>

<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

</head>
<body>
	<div class="container">
		<div class="row" style="padding-top: 20%">
			<div class="col-md-4 "></div>
			<div class="col-md-4" style="flex-align: center">
				<div class="card">
					<h5 class="card-header">로그인</h5>
					<div class="card-body">
						<form action='/loginProcess' method='POST'>
							<div class="form-group">
								<label for="InputId"> 아이디</label><input type="text" class="form-control" id="username" name="username" placeholder="ID">
							</div>
							<div class="form-group">
								<label for="InputPassword1">패스워드</label> <input type="password" class="form-control" id="password" name="password" placeholder="Password">
							</div>
							<div class="checkbox">
								<label> <input type="checkbox"> 아이디 기억하기
								</label>
							</div>
							<button name="submit" type="submit" class="btn btn-block btn-primary text-light">로그인</button>

							<c:if test="${not empty securityExceptionMsg}">
							<%--<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">  --%>
								<font color="red">
									<p>Your login attempt was not successful, try again</p>
									<p>${securityExceptionMsg}</p> 
									<%--<c:remove var="SPRING_SECURITY_LAST_EXCEPTION" scope="session" />  --%>
								</font>
							</c:if>
							<input type="hidden" name="loginRedirect" value="${loginRedirect}" />
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> <br>

							<sec:authorize access="isAuthenticated()">
							
							
								<div class="form-group" align="center">
									<h5>
 	 										<%=name%> 님, 반갑습니다. 
									<%--<sec:authentication property="principal.username" />님, 겁나 반갑습니다. --%>
									</h5>
									<br>
									<sec:authorize access="isAuthenticated()">
										<form action="/logout" method="POST">
											<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
											<button type="submit" class="btn btn-dark btn-sm">LOGOUT</button>
										</form>
									</sec:authorize>
								</div>
							</sec:authorize>
							

						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>