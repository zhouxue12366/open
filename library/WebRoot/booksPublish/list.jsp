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
			<font color="red" ><b>请单击名称进行选择</b></font><br></br>
				<table class="list">
					<tbody>
						<tr>
							<th width="50%">名称</th>
							<th width="50%">ISBN</th>							
						</tr>
						<c:forEach items="${booksPublishList}" var="booksPublish">
							<tr onclick="chooseBooksPublish(${booksPublish.id})">
							<!-- 一级类目 -->
							<td style="text-align: left;">
							<input type="hidden" id="booksPublish_id${booksPublish.id}" name="booksPublish_id" value="${booksPublish.id}"/>
							<input type="hidden" id="booksPublish_name${booksPublish.id}" name="booksPublish_name" value="${booksPublish.publishname}"/>
							<b>${booksPublish.publishname}</b></td>
							<td style="text-align: left;">${booksPublish.ISBN}</td>
							
							</tr>
												
						</c:forEach>
					</tbody>
				</table>

			</div>
		
		</div>
	</div>
	<script src="${contextPath}/js/jquery-1.4.4.min.js" type="text/javascript"></script>
	
	<script>
		function chooseBooksPublish(id){
			
			window.opener.document.getElementById('booksInfo.booksPublish_id').value = document.getElementById('booksPublish_id'+id).value ;
			window.opener.document.getElementById('booksInfo.booksPublish_name').value = document.getElementById('booksPublish_name'+id).value ;
			window.close();
		}
	</script>
</body>
</html>