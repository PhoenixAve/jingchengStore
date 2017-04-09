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
    
    <title>My JSP 'footer.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/footer.css'/>" />

  </head>
  
  <body>
    <footer>
		<p>2015-2016黄淮学院信息工程学院毕业设计</p>
		<p>Copyright © 2015-2016 余京成版权所有</p>
		<p><span>专业：计算机科学与技术</span><span>班级：1201B班</span></p>
		<p><span>学号：1234110119</span><span>联系电话：15565999182</span></p>
	</footer>
  </body>
</html>
