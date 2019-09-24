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
				图书分类管理&nbsp;&nbsp; <a href="${contextPath}/booksClass/add">添加</a>
			</h1>
			<div class="table_box">
				<table class="list">
					<tbody>
						<tr>
							<th width="40%">类别</th>
							<th width="60%">操作</th>
						</tr>
						<c:forEach items="${booksClassMap}" var="map">
							<tr>
							<!-- 一级类目 -->
							<td style="text-align: left;"><b>${map.key.classNum}&nbsp;&nbsp;${map.key.className}</b></td>
							<td style="text-align: left;">&nbsp;&nbsp;								
								<a	href="${contextPath}/booksClass/edit/${map.key.id}">修改</a>&nbsp;&nbsp;
								<a	class="btnDelBlog" href="${contextPath}/booksClass/delete/${map.key.id}">删除</a>
							</td>
							</tr>
							<!-- 二级类目 -->
							<c:forEach items="${map.value}" var="value">
							<tr>
								<td style="text-align: left;">&nbsp;&nbsp;${value.classNum}&nbsp;&nbsp;${value.className}</td>
								<td style="text-align: left;">&nbsp;&nbsp;								
									<a	href="${contextPath}/booksClass/edit/${value.id}">修改</a>&nbsp;&nbsp;
									<a	class="btnDelBlog" href="${contextPath}/booksClass/delete/${value.id}">删除</a>
								</td>
							</tr>
							</c:forEach>
							
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
				var bool = confirm("是否删除该类目");
				if(!bool) return false;
			});
	 });
	</script>
</body>
</html>