<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xml:lang="zh-CN" xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />

<style type="text/css">
	.table_box{width:100%;border:1px solid #89D5EF;background:#FFF;margin-bottom:25px;float:left;padding:1px}
    .table_box table{border:1px solid #CCC;border-collapse:collapse;width:100%}
    .table_box table td,.table_box table th{font-size:13px;border-right:0px solid #ccc;border-bottom:1px solid #ccc;overflow:hidden;padding:3px 3px}
    table.list{border:1px solid #e4e4e4;border-collapse:collapse;width:100%;margin-bottom:4px;background:#fff}
table.list th{background-color:#EEE;white-space:nowrap;text-align:left}
table.list td{vertical-align:top}
table.list td.id{width:2%;text-align:center}
table.list td.checkbox{width:15px;padding:0}
table.list td.buttons{width:15%;white-space:nowrap;text-align:right}
table.list td.buttons a{padding-right:.6em}
table.list caption{text-align:left;padding:.5em .5em .5em 0}
table tbody tr:hover{background-color:#FFD}
table tr.highlight{background-color:#c90}
body.manage_container{font-size:12px;}
	</style>
</head>
<body class="manage_container">
	<div>
		<div>
			<div  class="table_box"><br></br>
				<font color="red" ><b>请单击类别进行选择</b></font><br></br>
				<table class="list">
					<tbody>
						<tr>
							<th width="50%">类别</th>
							<th width="50%">已有图书本数</th>							
						</tr>
						<c:forEach items="${booksClassMap}" var="map">
							<tr onclick="chooseBooksClass(${map.key.id})">
							<!-- 一级类目 -->
							<td style="text-align: left;">
							<input type="hidden" id="booksClass_id${map.key.id}" name="booksClass_id" value="${map.key.id}"/>
							<input type="hidden" id="booksClass_name${map.key.id}" name="booksClass_name" value="${map.key.className}"/>
							<b>${map.key.classNum}&nbsp;&nbsp;${map.key.className}</b></td>
							<td style="text-align: left;">${map.key.booksNum}</td>
							
							</tr>
							<!-- 二级类目 -->
							<c:forEach items="${map.value}" var="value">
							<tr onclick="chooseBooksClass(${value.id})">
								<td style="text-align: left;">
								<input type="hidden" id="booksClass_id${value.id}" name="booksClass_id" value="${value.id}"/>
								<input type="hidden" id="booksClass_name${value.id}" name="booksClass_name" value="${value.className}"/>
								&nbsp;${value.classNum}&nbsp;&nbsp;${value.className}</td>
								<td style="text-align: left;">${value.booksNum }</td>
							</tr>
							</c:forEach>
							
						</c:forEach>
					</tbody>
				</table>

			</div>
		
		</div>
	</div>
	<script src="${contextPath}/js/jquery-1.4.4.min.js" type="text/javascript"></script>
	
	<script>
		function chooseBooksClass(id){
			
			window.opener.document.getElementById('booksInfo.booksClass_id').value = document.getElementById('booksClass_id'+id).value ;
			window.opener.document.getElementById('booksInfo.booksClass_name').value = document.getElementById('booksClass_name'+id).value ;
			window.close();
		}
	</script>
</body>
</html>