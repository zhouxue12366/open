<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xml:lang="zh-CN" xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link href="${contextPath}/css/manage.css" media="screen" rel="stylesheet"
	type="text/css" />
<script src="${contextPath}/js/jquery-1.4.4.min.js" type="text/javascript"></script>
</head>
<body>
	<div class="manage_container">
		<%@ include file="/common/_menu.jsp"%>
		<div class="main">
			<h1>
				User管理&nbsp;&nbsp; <a href="${contextPath}/user/add">创建User</a>
			</h1>
			<div class="table_box">
				<table class="list">
					<tbody>
						<tr>
							<th>id</th>
							<th>姓名</th>
							<th>地址</th>
							<th>电话</th>
							<th>备注</th>
							<th>操作</th>
						</tr>
						<c:forEach items="${userPage.list}" var="user">
							<tr>
								<td>${user.id}</td>
								<td>${user.username}</td>
								<td>${user.address}</td>
								<td>${user.phonenum}</td>
								<td>${user.comment}</td>
								<td style="text-align: left;">&nbsp;&nbsp;<a
									href="${contextPath}/user/delete/${user.id}">删除</a> &nbsp;&nbsp;<a
									href="${contextPath}/user/edit/${user.id}">修改</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

				<c:set var="currentPage" value="${userPage.pageNumber}" />
				<c:set var="totalPage" value="${userPage.totalPage}" />
				<c:set var="actionUrl" value="${contextPath}/user/" />
				<c:set var="urlParas" value="" />
				<%@ include file="/common/_paginate.jsp"%>

			</div>
		</div>
	</div>
</body>
</html>