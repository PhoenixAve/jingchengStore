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
    
    <title>My JSP 'adminHeader.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/adminjsps/css/adminHeader.css'/>" />
	<script src="<c:url value='/js/jquery-1.11.2.min.js' />" type="text/javascript" charset="utf-8"></script>
  </head>
  <body>
	  <header>
	  	<div class="admin_logo"><img src="<c:url value='/img/adminLogo1.png' />"></div>
	  	<form class="search" action="<c:url value='/admin/AdminBookServlet'/>">
			<input type="hidden" name="method" value="findByBname"/>
			<input class="sear-con" name="bname" type="text" placeholder="搜索您想要的图书名称" />
			<input class="sear-btn" type="submit" value="搜索" />
		</form>
	  	<nav>
	  		<div class="header-left">
	  			<span>管理员：${admin.adminname }</span>
	  			<a class="exit" href="<c:url value='/AdminServlet?method=quit'/>">退出</a>
	  		</div>
		  	<a href="<c:url value='/adminjsps/admin/category/categoryIndex.jsp' />">分类管理</a>
		  	<a href="<c:url value='/adminjsps/admin/book/bookIndex.jsp' />">图书管理</a>
		  	<a href="<c:url value='/admin/AdminOrderServlet?method=findAll' />">订单管理</a>
	  	</nav>
	  </header>
  </body>
</html>
