<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
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
				Blog管理&nbsp;&nbsp; <a href="${contextPath}/blog/add">创建Blog</a>
			</h1>
			<div class="table_box">
				<table class="list">
					<tbody>
						<tr>
							<th width="4%">id</th>
							<th width="35%">标题</th>
							<th width="12%">操作</th>
						</tr>
						<c:forEach items="${blogPage.list}" var="blog">
							<tr>
								<td style="text-align: left;">${blog.id}</td>
								<td style="text-align: left;">${blog.title}</td>
								<td style="text-align: left;">&nbsp;&nbsp;<a
									class="btnDelBlog" href="${contextPath}/blog/delete/${blog.id}">删除</a>
									&nbsp;&nbsp;<a 
									href="${contextPath}/blog/edit/${blog.id}">修改</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

				<c:set var="currentPage" value="${blogPage.pageNumber}" />
				<c:set var="totalPage" value="${blogPage.totalPage}" />
				<c:set var="actionUrl" value="${contextPath}/blog/" />
				<c:set var="urlParas" value="" />
				<%@ include file="/common/_paginate.jsp"%>

			</div>
		</div>
	</div>
	<script src="${contextPath}/js/jquery-1.4.4.min.js" type="text/javascript"></script>
	<input type="hidden"/>
	<script>
	 $(document).ready(function(){
		 $(".btnDelBlog").click(function() {
				var bool = confirm("是否删除");
				if(!bool) return;
			});
	 });
	</script>
</body>
</html>