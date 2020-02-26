<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script type="text/javascript">
function login() {
	if (!$("#mainForm").valid()) {
		CommonUtil.alertValidationMessage($("#mainForm"));
		return false; 
	}

	$("#mainForm").submit(function() {
		if (!$("#mainForm").valid()) {
			CommonUtil.alertValidationMessage($("#mainForm"));
			return false; 
		} else {
			return true;
		}
	});

}

</script>

</head>
<body>

	<h1> 로그인 </h1>
	<form method="POST" id="mainForm" action="/authenticate">
		<div class="loginForm">
			<ul>
			
				<li>id : <input data-val-required="아이디 필드가 필요합니다." data-name="아이디"
					id="ID" maxlength="15" name="ID" placeholder="아이디를 입력해주세요"
					type="text" value="" required />
				</li>
				
				<li>pw : <input data-val-required="비밀번호 필드가 필요합니다." data-name="비밀번호"
					id="Password" maxlength="16" name="Password"
					autocomplete="new-password" placeholder="패스워드를 입력해주세요"
					type="password" required />
				</li>
				
			</ul>
			
			<p>
				<label> <input class="id_save" id="remember-me"
					name="remember-me" title="Remember Me" type="checkbox" /> Remember Me
				</label>
			</p>

			<input type="button" src="/img/login/loginBtn.png" alt="로그인" class="btn" id="btnLogin" onclick="login();" value="전송" />
		</div>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

	</form>

	<br>
	name : ${name}
</body>
</html>