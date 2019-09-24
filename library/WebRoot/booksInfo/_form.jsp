<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<fieldset class="solid">
	<legend>添加图书</legend>
	<input type="hidden" name="booksInfo.id" value="${booksInfo.id}" />
	<div>
		<label>图书号（除分类号）</label>
		<input type="text" name="booksInfo.booksSerialNo" value="${booksInfo.booksSerialNo}" />${booksSerialNoMsg}
	</div>
	<div>
		<label>卷册号</label>
		<input type="text" name="booksInfo.volumeNumber" value="${booksInfo.volumeNumber}" />
	</div>
	<div>
		<label>分类</label>
		<input disabled type="text" id="booksInfo.booksClass_name" name="booksInfo.booksClass_name" value="${booksInfo.booksClass_name}" />
		<input type="hidden" id="booksInfo.booksClass_id" name="booksInfo.booksClass_id" value="${booksInfo.booksClass_id}" />
		<a href="javascript:openBooksClass();">点击</a>${booksClass_idMsg}
	</div>
	<div>
		<label>中文名称</label>
		<input type="text" name="booksInfo.booksNameCN" value="${booksInfo.booksNameCN}" />${booksNameCNMsg}
	</div>
	<div>
		<label>原文名称</label>
		<input type="text" name="booksInfo.booksNameReal" value="${booksInfo.booksNameReal}" />${booksNameRealMsg}
	</div>
	<div>
		<label>出版社</label>
		<input disabled type="text" id="booksInfo.booksPublish_name" name="booksInfo.booksPublish_name" value="${booksInfo.booksPublish_name}" />
		<input type="hidden" id="booksInfo.booksPublish_id" name="booksInfo.booksPublish_id" value="${booksInfo.booksPublish_id}" />
		<a href="javascript:openBooksPublish();">点击</a>${booksPublish_idMsg}
	</div>
	<div>
		<label>作者</label>
		<input type="text" name="booksInfo.author" value="${booksInfo.author}" />${authorMsg}
	</div>
	<div>
		<label>译者</label>
		<input type="text" name="booksInfo.translator" value="${booksInfo.translator}" />
	</div>
	<div>
		<label>库存</label>
		<input type="text" name="booksInfo.stock" value="${booksInfo.stock}" />${stockMsg}
	</div>
	<div>
		<label>出版日期</label>
		<input type="text" id="booksInfo.publishDate" name="booksInfo.publishDate" onblur="isDate()" value="${booksInfo.publishDate}" />(YYYY-MM-DD)${publishDateMsg}
	</div>
	<div>
		<label>登记日期</label>
		<input disabled type="text" name="booksInfo.recordDate" value="${booksInfo.recordDate}" />
	</div>
	<div>
		<label>最后借出日期</label>
		<input disabled type="text" name="booksInfo.lastDate" value="${booksInfo.lastDate}" />
	</div>
	<div>
		<label>书籍价格</label>
		<input type="text" name="booksInfo.price" value="${booksInfo.price}" />
	</div>
	<div>
		<label>书籍页码</label>
		<input type="text" name="booksInfo.pageNum" value="${booksInfo.pageNum}" />
	</div>
	<div>
		<label>借阅次数</label>
		<input disabled type="text" name="booksInfo.borrowTimes" value="${booksInfo.borrowTimes}" />
	</div>
	<div>
		<label>借书期限</label>
		<select name="booksInfo.borrowDays">
		
		<c:choose>
			<c:when test="${booksInfo.borrowDays=='无期限'}">
				 <option value="无期限"  selected = "selected">无期限</option>
			</c:when>
			<c:otherwise>
			 <option value="无期限">无期限</option>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${booksInfo.borrowDays=='3天'}">
				 <option value="3天"  selected = "selected">3天</option>
			</c:when>
			<c:otherwise>
			 <option value="3天">3天</option>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${booksInfo.borrowDays=='7天'}">
				 <option value="7天"  selected = "selected">7天</option>
			</c:when>
			<c:otherwise>
			 <option value="7天">7天</option>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${booksInfo.borrowDays=='半个月'}">
				 <option value="半个月"  selected = "selected">半个月</option>
			</c:when>
			<c:otherwise>
			 <option value="半个月">半个月</option>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${booksInfo.borrowDays=='一个月'}">
				 <option value="一个月"  selected = "selected">一个月</option>
			</c:when>
			<c:otherwise>
			 <option value="一个月">一个月</option>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${booksInfo.borrowDays=='三个月'}">
				 <option value="三个月"  selected = "selected">三个月</option>
			</c:when>
			<c:otherwise>
			 <option value="三个月">三个月</option>
			</c:otherwise>
		</c:choose>
				<c:choose>
			<c:when test="${booksInfo.borrowDays=='半年'}">
				 <option value="半年"  selected = "selected">半年</option>
			</c:when>
			<c:otherwise>
			 <option value="半年">半年</option>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${booksInfo.borrowDays=='一年'}">
				 <option value="一年"  selected = "selected">一年</option>
			</c:when>
			<c:otherwise>
			 <option value="一年">一年</option>
			</c:otherwise>
		</c:choose>
		</select>
	</div>
	<div>
		<label>借书人</label>
		<input disabled type="text" name="booksInfo.userid" value="${booksInfo.userid}" />
	</div>
	<div>
		<label>内容简介</label>
		<textarea  rows="3" name="booksInfo.booksdesc">${booksInfo.booksdesc}</textarea>
	</div><br>
	<div>
		<label>摘要、目录</label>
		<textarea  rows="3" name="booksInfo.catalog">${booksInfo.catalog}</textarea>
	</div><br>
	<div>
		<label>&nbsp;</label>
		<input value="提交" type="submit"><label>&nbsp;</label>
		<input value="返回" type="button" onclick="javascript:history.go(-1);">
	</div>
</fieldset>
<script src="${contextPath}/js/jquery-1.4.4.min.js" type="text/javascript"></script>
<script>
	//判断日期类型是否为YYYY-MM-DD格式的类型    
	function isDate(){     
    	var str = document.getElementById('booksInfo.publishDate').value.trim();    
    	if(str.length!=0){    
    	var reg = /^\d{4}-\d{2}-\d{2}$/; 
        var r = str.match(reg);     
        if(r==null)    
            alert('对不起，您输入的日期格式不正确!');     
        }    
	}
	 function  openBooksClass(){
		 window.open("${contextPath}/booksClass/list",'newWin','modal=yes,width=560,height=420,resizable=no,scrollbars=no'); 
	 }
	 function  openBooksPublish(){
		 window.open("${contextPath}/booksPublish/list",'newWin','modal=yes,width=560,height=420,resizable=no,scrollbars=no'); 
	 }
</script>