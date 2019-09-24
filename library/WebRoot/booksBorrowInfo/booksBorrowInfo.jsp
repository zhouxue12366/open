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
				图书借阅&nbsp;&nbsp; 
			</h1>
			<div align="center">
				<label>借书人(工号):</label>
				<input type="text" id="booksBorrowInfo.employeeId" name="booksBorrowInfo.employeeId" />
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" onclick="queryBorrowInfo()" value="查看借阅情况" />&nbsp;&nbsp;<font size="5" color="red" >${employeeIdMsg }${booksInfoMsg }</font>					
			</div><br></br>
			<div  align="center">
				<label>图书选择(编号):</label>
				<input type="hidden" id="booksBorrowInfo.booksInfo_id" name="booksBorrowInfo.booksInfo_id" />
				<input disabled type="text" id="booksBorrowInfo.booksSerialNo" name="booksBorrowInfo.booksSerialNo" />
				<a href="javascript:openBooksInfo();">点击</a>${booksClass_idMsg}
				&nbsp;&nbsp;&nbsp;&nbsp;
				<label>中文名称:</label>
				<input disabled type="text" id="booksBorrowInfo.booksNameCN" name="booksBorrowInfo.booksNameCN" />
				&nbsp;&nbsp;&nbsp;&nbsp;
				<label>约定归还日期:</label>
				<input type="text" id="booksBorrowInfo.agreeReturnDate" name="booksBorrowInfo.agreeReturnDate" />&nbsp;
				<label>(YYYY-MM-DD)</label>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" onclick="borrow()" value="确定借书" />
			</div><br></br>
			<div class="table_box">
				<table class="list">
					<tbody>
						<tr>
						<td colspan="9"><font color="red" >已借阅图书</font></td>
						</tr>
						<tr>
							<th width="10%">图书编号</th>
							<th width="20%">中文名称</th>
							<th width="17%">出版社</th>
							<th width="10%">借出时间</th>
							<th width="10%">应还时间</th>
							<th width="10%">实还时间</th>
							<th width="10%">押金</th>
							<th width="8%">状态</th>
							<th width="5%">操作</th>
						</tr>
						<c:forEach items="${booksBorrowInfoPage.list}" var="booksBorrowInfo">
							<tr>							
								<td style="text-align: left;">${booksBorrowInfo.booksSerialNo}</td>
								<td style="text-align: left;">${booksBorrowInfo.booksNameCN}</td>
								<td style="text-align: left;">${booksBorrowInfo.publishname}</td>
								<td style="text-align: left;">${booksBorrowInfo.lendDate}</td>
								<td style="text-align: left;">${booksBorrowInfo.agreeReturnDate}</td>
								<td style="text-align: left;">${booksBorrowInfo.realReturnDate}</td>
								<td style="text-align: left;">${booksBorrowInfo.deposit}</td>								
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
	<form name="queryForm" action="${contextPath}/booksBorrowInfo/queryBorrowInfo" method="post">
		<input type="hidden" id="employeeId" name="employeeId"></input>
	</form>
	<form name="borrowForm" action="${contextPath}/booksBorrowInfo/borrow" method="post">
		<input type="hidden" id="booksInfoId" name="booksInfoId"></input>
		<input type="hidden" id="employeeId" name="employeeId"></input>
		<input type="hidden" id="agreeReturnDate" name="agreeReturnDate"></input>
	</form>
	<script src="${contextPath}/js/jquery-1.4.4.min.js" type="text/javascript"></script>
	<script>
	function  openBooksInfo(){
		 window.open("${contextPath}/booksInfo/open",'newWin','modal=yes,width=1000,height=600,resizable=no,scrollbars=yes'); 
	 }
	
	function queryBorrowInfo(){
		document.queryForm.employeeId.value = document.getElementById("booksBorrowInfo.employeeId").value;
		document.queryForm.submit();
	}
	function borrow(){
		var booksInfo_id = document.getElementById("booksBorrowInfo.booksInfo_id").value;
		var employeeId = document.getElementById("booksBorrowInfo.employeeId").value;
		var agreeReturnDate = document.getElementById("booksBorrowInfo.agreeReturnDate").value;
		
		if(booksInfo_id== null || booksInfo_id==''){
			alert("请选择要借阅的图书");
			return false;
		}
		if(employeeId == null || employeeId==''){
			alert("请输入借书人工号");
			return false;
		}            
		if(agreeReturnDate == null || agreeReturnDate == ''){
			alert("请输入约定归还日期");
			return false;
		}
		var reg = /^\d{4}-\d{2}-\d{2}$/;     
	    var r = agreeReturnDate.match(reg);     
	    if(r==null){    
	     alert('对不起，您输入的日期格式不正确!'); 
	     return false;
	    }
		document.borrowForm.booksInfoId.value = booksInfo_id;
		document.borrowForm.employeeId.value = employeeId;
		document.borrowForm.agreeReturnDate.value = agreeReturnDate;
		document.borrowForm.submit();
	}
	function returnBook(id){
		document.returnForm.id.value = id;
		document.returnForm.submit();
	}
	</script>
</body>
</html>