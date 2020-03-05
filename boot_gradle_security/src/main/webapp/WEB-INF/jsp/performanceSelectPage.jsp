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

	<form class="container" style="margin-top:15px;">
		<div class="row">
			<div class="col">
				<div class="form-group">
					<div class='input-group date' id='datetimepicker6'>
						<input type='text' class="form-control" /> <span class="input-group-addon"> <span class="glyphicon glyphicon-calendar"></span>
						</span>
					</div>
				</div>
			</div>
			<div class="col">
				<div class="form-group">
					<div class='input-group date' id='datetimepicker7'>
						<input type='text' class="form-control" /> <span class="input-group-addon"> <span class="glyphicon glyphicon-calendar"></span>
						</span>
					</div>
				</div>
			</div>

			<div class="col">
				<select class="custom-select custom-select-lg mb-3" id="inputGroupSelect01" style="margin-top: 2px;">
					<option selected>One</option>
					<option value="1">Two</option>
					<option value="2">Three</option>
				</select>
			</div>

			<div class="col">
				<select class="custom-select custom-select-lg mb-3" id="inputGroupSelect02" style="margin-top: 2px;">
					<option selected>One</option>
					<option value="1">One</option>
					<option value="2">Two</option>
					<option value="3">Three</option>
				</select>

			</div>

			<div class="col col-lg-2"><button type="button" class="btn btn-secondary">검색</button></div>
		</div>
	</form>
</div>


<script type="text/javascript">
	$(document).ready(function() {
		$(function() {
			$('#datetimepicker6').datetimepicker({
				format : 'YYYY/MM/DD'
			});

			$('#datetimepicker7').datetimepicker({
				useCurrent : false, //Important! See issue #1075
				format : 'YYYY/MM/DD'
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