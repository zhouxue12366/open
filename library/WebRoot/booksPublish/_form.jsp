<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<fieldset class="solid">
	<legend>添加出版社</legend>
	<input type="hidden" name="booksPublish.id" value="${booksPublish.id}" />
	<div>
		<label>出版社名称</label>
		<input type="text" name="booksPublish.publishname" value="${booksPublish.publishname}" />${publishnameMsg}
	</div>
	<div>
		<label>ISBN</label>
		<input type="text" name="booksPublish.ISBN" value="${booksPublish.ISBN}" />${ISBNMsg}
	</div>
	<div>
		<label>&nbsp;</label>
		<input value="提交" type="submit"><label>&nbsp;</label>
		<input value="返回" type="button" onclick="javascript:history.go(-1);">
	</div>
</fieldset>