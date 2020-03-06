<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Main Page</title>

<!-- jquery -->
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>

<!-- Ajax -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/css/bootstrap-datetimepicker.min.css" />

<!-- bootstrap -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

<!-- Calendar -->

<!-- moment / 이게 먼저 load 되어야 하네..-->
<script src="/js/moment.min.js"></script>
<script src="/js/bootstrap-datepicker.ko.min.js"></script>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<!-- datetimepicker-->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/css/bootstrap-datetimepicker.min.css" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min.js"></script>


</head>
<br>
<br>
<div class="container border" style="width: 1070px; padding-top: 15px;">
<div class="container" align="center">
		<div class="row">
			<div class="col">시작일시</div>
			<div class="col">종료일시</div>
			<div class="col">지역</div>
			<div class="col">분류</div>
			<div class="col"></div>
			</div>
	</div>

	<form class="container" style="margin-top:15px;" action="/performanceAPI">
		<div class="row">
			<div class="col">
				<div class="form-group">
					<div class='input-group date' id='datetimepicker6'>
						<input type='text' class="form-control" name="from"/> <span class="input-group-addon"> <span class="glyphicon glyphicon-calendar"></span>
						</span>
					</div>
				</div>
			</div>
			<div class="col">
				<div class="form-group">
					<div class='input-group date' id='datetimepicker7'>
						<input type='text' class="form-control" name="to" /> <span class="input-group-addon"> <span class="glyphicon glyphicon-calendar"></span>
						</span>
					</div>
				</div>
			</div>

			<div class="col">
				<select class="custom-select custom-select-lg mb-3" id="inputGroupSelect01" name="sido" style="margin-top: 2px;">
					<option value="">전체</option>
					<option value="9">서울</option>
					<option value="2">경기</option>
					<option value="10">세종</option>
					<option value="7">대전</option>
					<option value="6">대구</option>
					<option value="8">부산</option>
					<option value="5">광주</option>
					<option value="15">제주</option>
					<option value="1">강원</option>
					<option value="3">경남</option>
					<option value="4">경북</option>
					<option value="11">울산</option>
					<option value="12">인천</option>
					<option value="13">전남</option>
					<option value="14">전북</option>
					<option value="16">충남</option>
					<option value="17">충북</option>
				</select>
			</div>

			<div class="col">
				<select class="custom-select custom-select-lg mb-3" id="inputGroupSelect02" name="realmCode" style="margin-top: 2px;">
					<option value="">전체</option>
					<option value="A000">연극,뮤지컬</option>
					<option value="B000">음악,콘서트,국악</option>
					<option value="C000">무용</option>
					<option value="D000">미술</option>
					<option value="E000">건축</option>
					<option value="G000">영상</option>
					<option value="H000">문학</option>
					<option value="I000">문화정책</option>
					<option value="J000">축제문화공간</option>
					<option value="L000">기타</option>
				</select>

			</div>

			<div class="col col-lg-2"><input type="submit" class="btn btn-secondary" value="검색"></div>
		</div>
	</form>
</div>


<script type="text/javascript">
// 달력보여주기위함
$(document).ready(function() {
		$(function() {
			$('#datetimepicker6').datetimepicker({
				format : 'YYYYMMDD'
			});

			$('#datetimepicker7').datetimepicker({
				useCurrent : false, //Important! See issue #1075
				format : 'YYYYMMDD'
			});

			$("#datetimepicker6").on("dp.change", function(e) {
				$('#datetimepicker7').data("DateTimePicker").minDate(e.date);
			});
			$("#datetimepicker7").on("dp.change", function(e) {
				$('#datetimepicker6').data("DateTimePicker").maxDate(e.date);
			});
		});
	});
</script>

</body>
</html>