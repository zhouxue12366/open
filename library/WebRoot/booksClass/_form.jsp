<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<fieldset class="solid">
	<legend>添加图书分类</legend>
	<input type="hidden" name="booksClass.id" value="${booksClass.id}" />
	<div>
		<label>类别名称</label>
		<input type="text" name="booksClass.className" value="${booksClass.className}" />${classNameMsg}
	</div>
	<div>
		<label>类别分类号</label>
		<input type="text" name="booksClass.classNum" value="${booksClass.classNum}" />${classNumMsg}
	</div>
	<div>
		<label>借书期限</label>
		<select name="booksClass.borrowDays">
		
		<c:choose>
			<c:when test="${booksClass.borrowDays=='无期限'}">
				 <option value="无期限"  selected = "selected">无期限</option>
			</c:when>
			<c:otherwise>
			 <option value="无期限">无期限</option>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${booksClass.borrowDays=='3天'}">
				 <option value="3天"  selected = "selected">3天</option>
			</c:when>
			<c:otherwise>
			 <option value="3天">3天</option>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${booksClass.borrowDays=='7天'}">
				 <option value="7天"  selected = "selected">7天</option>
			</c:when>
			<c:otherwise>
			 <option value="7天">7天</option>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${booksClass.borrowDays=='半个月'}">
				 <option value="半个月"  selected = "selected">半个月</option>
			</c:when>
			<c:otherwise>
			 <option value="半个月">半个月</option>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${booksClass.borrowDays=='一个月'}">
				 <option value="一个月"  selected = "selected">一个月</option>
			</c:when>
			<c:otherwise>
			 <option value="一个月">一个月</option>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${booksClass.borrowDays=='三个月'}">
				 <option value="三个月"  selected = "selected">三个月</option>
			</c:when>
			<c:otherwise>
			 <option value="三个月">三个月</option>
			</c:otherwise>
		</c:choose>
				<c:choose>
			<c:when test="${booksClass.borrowDays=='半年'}">
				 <option value="半年"  selected = "selected">半年</option>
			</c:when>
			<c:otherwise>
			 <option value="半年">半年</option>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${booksClass.borrowDays=='一年'}">
				 <option value="一年"  selected = "selected">一年</option>
			</c:when>
			<c:otherwise>
			 <option value="一年">一年</option>
			</c:otherwise>
		</c:choose>
		</select>
	</div>
	<div>
		<label>所属分类</label>
		<select name="booksClass.parent_id">
			<!-- 一级类目 -->
			<c:forEach items="${booksClassMap}" var="map">
				<option value="${map.key.id}">${map.key.className}</option>			
			<!-- 二级类目 -->
				<c:forEach items="${map.value}" var="value">
					<option value="${value.id}"> - ${value.className}</option>
				</c:forEach>
			</c:forEach>
		</select>
	</div>
	<div>
		
		<label>&nbsp;</label>
		<input value="提交" type="submit">	<label>&nbsp;</label>
		<input value="返回" type="button" onclick="javascript:history.go(-1);">
	</div>
</fieldset>