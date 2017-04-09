<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <title>信息板</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link rel="stylesheet" type="text/css" href="<c:url value='/css/msg.css '/>">

  </head>
  
  <body>
  <c:choose>
  	<c:when test="${code eq 'success' }"><%--如果code是成功，它显示对号图片 --%>
  		<c:set var="img" value="/img/duihao.jpg"/>
  		<c:set var="title" value="成功"/>
  	</c:when>
  	<c:when test="${code eq 'error' }"><%--如果code是失败，它显示错号图片 --%>
  		<c:set var="img" value="/img/cuohao.png"/>
  		<c:set var="title" value="失败"/>
  	</c:when>
  	
  </c:choose>
<div class="divBody">
	<div class="divTitle">
		<span class="spanTitle">${title }</span>
	</div>
	<div class="divContent">
	  <div style="margin: 20px;">
		<img style="float: left; margin-right: 30px;" src="<c:url value='${img }'/>" width="150"/>
		<span style="font-size: 30px; color: #c30; font-weight: 900;">${msg }</span>
		<br/>
		<br/>
		<br/>
		<br/>
		<input type="button" style="border: 1px solid #ccc; border-radius: 3px; padding: 5px 10px; cursor: pointer;" value="返回" onclick="history.go(-1)"/>
	  </div>
	</div>
</div>


  </body>
</html>
