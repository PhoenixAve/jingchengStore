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
    
    <title>惊城书屋</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  
  <body>	
  	<jsp:include page="/CategoryServlet?method=findAll"/>
  	<div id="con-wrap">
		<div class="banner">

		</div>
		<div class="con">
			<jsp:include page="/BookServlet?method=findRecommend"/>
			<jsp:include page="/BookServlet?method=findByPrinttime&cid=5F79D0D246AD4216AC04E9C5FAB3199E"/>
 		</div>
 	</div>
  	<jsp:include page="footer.jsp"/>
  </body>
</html>
