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
    
    <title>My JSP 'body.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/index.css'/>" />
	<script src="<c:url value='/js/list.js'/>" type="text/javascript" charset="utf-8"></script>
  </head>
  
  <body>
	<div class="java">
		<h2>Java JavaScript<a href="<c:url value='BookServlet?method=findByCategory&cid=5F79D0D246AD4216AC04E9C5FAB3199E' />">更多&gt;&gt;</a></h2>
		<ul>
			<c:forEach items="${javaPb.beanList }" var="book">
			<li>
				<div class="inner">
					<a class="pic" href="<c:url value='/BookServlet?method=load&bid=${book.bid }'/>"><img src="<c:url value='/img/${book.image_b }'/>" border="0" /></a>
					<p class="price">
						<span class="price_n">&yen;${book.currPrice }</span>
						<span class="price_r">&yen;${book.price }</span> (
						<span class="price_s">${book.discount }折</span>)
					</p>
					<p><a id="bookname" title="${book.bname }" href="<c:url value='/BookServlet?method=findById&bname=${book.bname }'/>">${book.bname }</a></p>
					<p><a href="<c:url value='/BookServlet?method=findById&bname=${book.bname }'/>" name='P_zz' title='${book.author }'>${book.author }</a></p>
					<p class="publishing">
						<span>出 版 社：</span><a href="">${book.press }</a>
					</p>
					<p class="publishing_time"><span>出版时间：</span>${book.publishtime }</p>
				</div>
			</li>
			</c:forEach>
		</ul>
	</div>
  </body>
</html>
