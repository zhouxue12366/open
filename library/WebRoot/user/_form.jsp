<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<fieldset class="solid">
	<legend>创建User</legend>
	<input type="hidden" name="user.id" value="${user.id}" />
	<div>
		<label for="username">姓名</label> 
		<input id="username" name="user.username" value="${user.username}" /><span style="color:red;">${usernameMsg}</span>
	</div>
	<div>
		<label for="address">地址</label> 
		<input id="address" name="user.address" value="${user.address}"/><span style="color:red;">${addressMsg}</span>
	</div>
	<div>
		<label for="phonenum">电话</label>
		<input id="phonenum" name="user.phonenum" value="${user.phonenum}" /><span style="color:red;">${phonenumMsg}</span>
	</div>
	<div>
		<label for="comment">备注</label>
		<textarea id="comment" name="user.comment" cols="80" rows="10">${user.comment}</textarea>${commentMsg}
	</div>
	<div>
		<label>&nbsp;</label>
		<input value="提交" type="submit"/>
	</div>
	

</fieldset>



