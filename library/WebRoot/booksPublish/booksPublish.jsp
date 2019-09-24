<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xml:lang="zh-CN" xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link href="${contextPath}/css/manage.css" media="screen"
	rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="manage_container">
		<%@ include file="/common/_menu.jsp"%>
		<div class="main">
			<h1>
				出版社管理&nbsp;&nbsp; <a href="${contextPath}/booksPublish/add">添加</a>
			</h1>
			<div class="table_box">
				<table class="list">
					<tbody>
						<tr>
							<th width="50%">出版社名称</th>
							<th width="30%">ISBN</th>
							<th width="20%">操作</th>
						</tr>
						
						<c:forEach items="${booksPublishList}" var="booksPublish">
							<tr>
								<td style="text-align: left;">${booksPublish.publishname}</td>
								<td style="text-align: left;">${booksPublish.ISBN}</td>
								<td style="text-align: left;">&nbsp;&nbsp;								
									<a	href="${contextPath}/booksPublish/edit/${booksPublish.id}">修改</a>&nbsp;&nbsp;
									<a	class="btnDelBlog" href="${contextPath}/booksPublish/delete/${booksPublish.id}">删除</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

			</div>
		
		</div>
	</div>
	<script src="${contextPath}/js/jquery-1.4.4.min.js" type="text/javascript"></script>
	<input type="hidden"/>
	<script>
	 $(document).ready(function(){
		 $(".btnDelBlog").click(function() {
				var bool = confirm("是否删除该出版社");
				if(!bool) return false;
			});
	 });
	</script>
</body>
</html>