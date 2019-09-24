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
			<div class="table_box">
				<table class="list">
					<tbody>
						<tr>
						<td colspan="8"><font color="red" >查询结果</font></td>
						</tr>
						<tr>
							<th width="20%">图书编号</th>
							<th width="20%">中文名称</th>
							<th width="10%">借书人</th>
							<th width="10%">借出时间</th>
							<th width="10%">应还时间</th>
							<th width="10%">逾期天数</th>
							<th width="10%">状态</th>
							<th width="10%">操作</th>
						</tr>
						<c:forEach items="${booksBorrowInfoPage.list}" var="booksBorrowInfo">
							<tr>							
								<td style="text-align: left;">${booksBorrowInfo.booksSerialNo}</td>
								<td style="text-align: left;">${booksBorrowInfo.booksNameCN}</td>
								<td style="text-align: left;">${booksBorrowInfo.employeeId}</td>
								<td style="text-align: left;">${booksBorrowInfo.lendDate}</td>
								<td style="text-align: left;">${booksBorrowInfo.agreeReturnDate}</td>
								<td style="text-align: left;">${booksBorrowInfo.overdueDays}</td>								
								<td style="text-align: left;">
									<c:choose>
									<c:when test="${booksBorrowInfo.status=='0'}">
										<font color="red" >未还</font>
									</c:when>
									<c:otherwise>
			 							<font color="red" >已还</font>
									</c:otherwise>
									</c:choose>
								</td>
								<td style="text-align: left;">
									<c:if test="${booksBorrowInfo.status=='0'}">
										<a href="javascript:returnBook(${booksBorrowInfo.id});">归还</a>
									</c:if>
								</td>
							</tr>
							</c:forEach>
					</tbody>
				</table>
				<c:set var="currentPage" value="${booksBorrowInfoPage.pageNumber}" />
				<c:set var="totalPage" value="${booksBorrowInfoPage.totalPage}" />
				<c:set var="actionUrl" value="${contextPath}/booksBorrowInfo/" />
				<c:set var="urlParas" value="" />
				<%@ include file="/common/_paginate.jsp"%>
								
			</div>
		
		</div>
	</div>
	<form name="returnForm" action="${contextPath}/booksBorrowInfo/returnBook" method="post">
		<input type="hidden" id="id" name="id"></input>
	</form>
	<script src="${contextPath}/js/jquery-1.4.4.min.js" type="text/javascript"></script>
	<script>
		function returnBook(id){
		document.returnForm.id.value = id;
		document.returnForm.submit();
		}
	</script>
</body>
</html>