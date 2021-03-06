<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <title>添加分类</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/adminjsps/css/adminAdd.css'/>" />
	<script type="text/javascript" src="<c:url value='/js/jquery-1.11.2.min.js'/>"></script>
	<script type="text/javascript">
		function checkForm() {
			if(!$("#cname").val()) {
				alert("分类名不能为空！");
				return false;
			}
			if(!$("#pid").val()) {
				alert("一级分类不能为空！");
				return false;
			}
			if(!$("#desc").val()) {
				alert("分类描述不能为空！");
				return false;
			}
			return true;
		}
	</script>
  </head>
  <body>
  	<jsp:include page="../adminHeader.jsp" />
    <div class="con">
    	<h3>添加2级分类</h3>
	    <h1></h1>
	    <p style="font-weight: 900; color: red">${msg }</p>
	    <form action="<c:url value='/admin/AdminCategoryServlet'/>" method="post" onsubmit="return checkForm()">
	    	<input type="hidden" name="method" value='addChild'/>
	    	分类名称：<input type="text" name="cname" id="cname"/><br/>
	    	一级分类：<select name="pid" id="pid">
	    		<option value="">===选择1级分类===</option>
				<c:forEach items="${parents }" var="parent">
	    		<option value="${parent.cid }" <c:if test="${parent.cid eq pid }">selected</c:if> >${parent.cname }</option>
				</c:forEach>
	    	</select><br/>
	    	分类描述：<textarea rows="5" cols="50" name="desc" id="desc"></textarea><br/>
	    	<input type="submit" value="添加二级分类"/>
	    	<input type="button" value="返回" onclick="history.go(-1)"/>
	    </form>
    </div>
  </body>
</html>
