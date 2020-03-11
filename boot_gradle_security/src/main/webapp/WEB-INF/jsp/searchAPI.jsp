<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.json.*"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>코로나 뉴스정보</title>


<link rel="stylesheet" type="text/css" href="/jqgrid/css/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="/jqgrid/css/ui.jqgrid.css" />

<script type="text/javascript" src="/jqgrid/js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/jqgrid/js/i18n/grid.locale-kr.js"></script>
<script type="text/javascript" src="/jqgrid/js/jquery.jqGrid.min.js"></script>

<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
</head>
<body>
	<div align="center">
		<br> <br>


		<h3>코로나 뉴스정보</h3>

		<c:forEach items="${newsInfos}" var="row" varStatus="status">

			<table border="1">
				<tr>
					<th>title</th>
					<th>originallink</th>
					<th>link</th>
					<th>description</th>
					<th>pubDate</th>
				</tr>
				<tr>
					<td>${row.title}</td>
					<td>${row.originallink}</td>
					<td>${row.link}</td>
					<td>${row.description}</td>
					<td>${row.pubDate}</td>
				</tr>
				<br>
			</table>

		</c:forEach>


		<br> <br> <a class="btn btn-outline-success" href="/goHome">홈 화면으로가기</a><br> <br> <br> <br>
	</div>
</body>
</html>