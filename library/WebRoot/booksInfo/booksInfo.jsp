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
				图书管理&nbsp;&nbsp; <a href="${contextPath}/booksInfo/add">添加</a>
			</h1>
			<div class="table_box">
				<table class="list">
					<tbody>
						<tr>
							<th width="10%">图书编号</th>
							<th width="20%">中文名称</th>
							<th width="8%">类别</th>
							<th width="17%">出版社</th>
							<th width="10%">出版日期</th>
							<th width="10%">登记日期</th>
							<th width="5%">借出次数</th>
							<th width="10%">状态</th>
							<th width="10%">操作</th>
						</tr>
					<c:forEach items="${booksInfoPage.list}" var="booksInfo">
							<tr>
								<td style="text-align: left;">${booksInfo.booksSerialNo}</td>
								<td style="text-align: left;">${booksInfo.booksNameCN}</td>
								<td style="text-align: left;">${booksInfo.booksClass_name}</td>
								<td style="text-align: left;">${booksInfo.booksPublish_name}</td>
								<td style="text-align: left;">${booksInfo.publishDate}</td>
								<td style="text-align: left;">${booksInfo.recordDate}</td>
								<td style="text-align: left;">${booksInfo.borrowTimes}</td>
								<td style="text-align: left;">
									<c:choose>
									<c:when test="${booksInfo.status=='0'}">
										未借
									</c:when>
									<c:otherwise>
			 							借出
									</c:otherwise>
									</c:choose>
								</td>
								<td style="text-align: left;">
									&nbsp;&nbsp;								
									<a	href="${contextPath}/booksInfo/edit/${booksInfo.id}">修改</a>&nbsp;&nbsp;
									<a	class="btnDelBlog" href="${contextPath}/booksInfo/delete/${booksInfo.id}">删除</a>
								</td>
							</tr>
							</c:forEach>
					</tbody>
				</table>
				<c:set var="currentPage" value="${booksInfoPage.pageNumber}" />
				<c:set var="totalPage" value="${booksInfoPage.totalPage}" />
				<c:set var="actionUrl" value="${contextPath}/booksInfo/" />
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
				var bool = confirm("是否删除该图书");
				if(!bool) return false;
			});
	 });
	</script>
</body>
</html>