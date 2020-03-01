<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JQGRID를 적용해보자</title>


<link rel="stylesheet" type="text/css" href="/jqgrid/css/jquery-ui.css"/>
<link rel="stylesheet" type="text/css" href="/jqgrid/css/ui.jqgrid.css"/>

<script type="text/javascript" src="/jqgrid/js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/jqgrid/js/i18n/grid.locale-kr.js"></script>
<script type="text/javascript" src="/jqgrid/js/jquery.jqGrid.min.js"></script>

</head>
<body>
	<h1> JQGRID를 적용해보자  </h1>
	
	<table id="jqGrid"></table>
	<div id="jqGridPager"></div>
	
	<script type="text/javascript"> 
        $(document).ready(function () {
            $("#jqGrid").jqGrid({
                url: 'http://trirand.com/blog/phpjqgrid/examples/jsonp/getjsonp.php?callback=?&qwery=longorders',
                mtype: "GET",
                datatype: "jsonp",
                colModel: [
                    { label: 'OrderID', name: 'OrderID', key: true, width: 50 },
                    { label: 'Customer ID', name: 'CustomerID', width: 150 },
                    { label: 'Order Date', name: 'OrderDate', width: 150 },
                    { label: 'Freight', name: 'Freight', width: 150 },
                    { label:'Ship Name', name: 'ShipName', width: 150 }
                ],
                viewrecords: true,
                height: 500,
                rowNum: 100,
                pager: "#jqGridPager"
            });
        });
</script>

</body>
</html>