<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'bookList.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/adminjsps/css/bookIndex.css'/>" />
  </head>
  
  <body>
  <jsp:include page="../adminHeader.jsp" />
  <script type="text/javascript" charset="utf-8">
		$(function(){
			$("nav a").eq(2).addClass("active");
		})
	</script>
  <div class="con">
  	<div class="left">
  		<jsp:include page="/admin/AdminBookServlet?method=findCategoryAll"/>
  	</div>
  	<div class="right">
  		<jsp:include page="/admin/AdminBookServlet?method=addPre"></jsp:include>
  	</div>
  </div>
  </body>
</html>
