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
	<link rel="stylesheet" type="text/css" href="<c:url value='/adminjsps/css/bookLeft.css' />" />
	<script src="<c:url value='/adminjsps/js/adminLeft.js' />" type="text/javascript" charset="utf-8"></script>
  </head>
  
  <body>
    <div class="all">
		<ul>
			<c:forEach items="${parents }" var="parent">
				<li>
					<a href="javascript:;">${parent.cname }<span></span></a>
					<ul>
						<c:forEach items="${parent.children }" var="child">
							<li><a href="<c:url value='/admin/AdminBookServlet?method=findByCategory&cid=${child.cid }'/>">${child.cname }</a></li>
						</c:forEach>
					</ul>
				</li>
			</c:forEach>
		</ul>
	</div>
  </body>
</html>
