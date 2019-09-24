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
<body >
	<div class="manage_container">
		<div class="main">
			<div class="table_box"><br></br>
			<font color="red" ><b>请单击图书编号或中文名称来选择图书！</b></font>
				<br></br>
				<label>图书查找:</label>
				<select id="queryCondition" name="queryCondition">
					<option selected = "selected" value="0">--请选择查询条件--</option>
					<option value="1">按图书编号查找</option>
					<option value="2">按中文名称查找</option>
					<option value="3">按类别查找</option>
					<option value="4">按出版社查找</option>
				</select>&nbsp;&nbsp;
				<input type="text" size="50" id="conditionValue" name="conditionValue"/>
				&nbsp;&nbsp;
				<input type="button" onclick="query()" value="查找"></input><br></br>
				<table class="list">
					<tbody>
						<tr>
							<th width="20%">图书编号</th>
							<th width="20%">中文名称</th>
							<th width="15%">类别</th>
							<th width="20%">出版社</th>
							<th width="15%">作者</th>				
							<th width="10%">状态</th>
						</tr>
					<c:forEach items="${booksInfoPage.list}" var="booksInfo">
							<tr onclick="chooseBooksInfo(${booksInfo.id})">							
								<td style="text-align: left;">
								<input type="hidden" id="booksSerialNo${booksInfo.id}" value="${booksInfo.booksSerialNo}"></input>
								<input type="hidden" id="booksNameCN${booksInfo.id}" value="${booksInfo.booksNameCN}"></input>
								
								${booksInfo.booksSerialNo}
								</td>
								<td style="text-align: left;">${booksInfo.booksNameCN}</td>
								<td style="text-align: left;">${booksInfo.booksClass_name}</td>
								<td style="text-align: left;">${booksInfo.booksPublish_name}</td>
								<td style="text-align: left;">${booksInfo.author}</td>
								<td style="text-align: left;">
									<c:choose>
									<c:when test="${booksInfo.status=='0'}">
										<font color="red" >未借</font>
									</c:when>
									<c:otherwise>
			 							<font color="red" >借出</font>
									</c:otherwise>
									</c:choose>
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
  
	<form name="queryForm" action="${contextPath}/booksInfo/list" method="post">
		<input type="hidden" name="queryCondition" id="queryCondition"></input>
		<input type="hidden" name="conditionValue" id="conditionValue"></input>
	</form>
	<script src="${contextPath}/js/jquery-1.4.4.min.js" type="text/javascript"></script>
	<input type="hidden"/>
	<script type="text/javascript">
	 
		function query(){
			
			if(document.getElementById("queryCondition").value =='0'){
				alert("请选择查询条件"); 
				return false;
			}
			if(document.getElementById("conditionValue").value == null || document.getElementById("conditionValue").value ==''){
				alert("请输入查询条件");
				return false;
			}
			document.queryForm.queryCondition.value=document.getElementById("queryCondition").value;
			document.queryForm.conditionValue.value=document.getElementById("conditionValue").value;
			document.queryForm.submit();
		}
		
		function chooseBooksInfo(id){
			window.opener.document.getElementById('booksBorrowInfo.booksInfo_id').value = id ;
			window.opener.document.getElementById('booksBorrowInfo.booksSerialNo').value = document.getElementById('booksSerialNo'+id).value ;
			window.opener.document.getElementById('booksBorrowInfo.booksNameCN').value = document.getElementById('booksNameCN'+id).value ;
			window.close();
		}
	</script>
</body>
</html>