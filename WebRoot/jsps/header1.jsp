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
    
    <title>My JSP 'header.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/header1.css'/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/left1.css'/>" />
	<link rel="shortcut icon" href="<c:url value='/favicon.ico'/>"/>
	<script src="<c:url value='/js/jquery-1.11.2.min.js'/>" type="text/javascript" charset="utf-8"></script>
	<script src="<c:url value='/js/header1.js'/>" type="text/javascript" charset="utf-8"></script>
  </head>
  <body>
    <header>
		<div id="quick">
			<div class="quick-wrap">
			<c:choose>
				<c:when test="${empty sessionScope.sessionUser }">
					<div class="quick-regist"><a href="<c:url value='/jsps/user/regist.jsp' />">注册</a></div>
					<div class="quick-login">您好，请<a href="<c:url value='/jsps/user/login.jsp' />">登录</a></div>
				</c:when>
				<c:otherwise>
					<div class="user-info">
						<a class="user-name">${sessionScope.sessionUser.loginname }</a>
						<ul>
							<li><a href="<c:url value='/CartItemServlet?method=myCart' />">我的购物车</a></li>
							<li><a href="<c:url value='/OrderServlet?method=myOrders' />">我的订单</a></li>
							<li><a href="<c:url value='/jsps/user/pwd.jsp' />">修改密码</a></li>
							<li><a href="<c:url value='/UserServlet?method=quit' />">退出</a></li>
							<!-- <li><a href="<c:url value='' />">联系我们</a></li> -->
						</ul>
					</div>
				</c:otherwise>
			</c:choose>	
			</div>
		</div>
		<div id="search">
			<div class="logo">
				<a href="<c:url value='/index.jsp'/>"></a>
			</div>
			<form class="search" action="<c:url value='/BookServlet'/>">
				<input type="hidden" name="method" value="findByBname"/>
				<input class="sear-con" name="bname" type="text" placeholder="搜索您想要的图书名称" />
				<input class="sear-btn" type="submit" value="搜索" />
			</form>
			<div class="myCar">
				<a href="<c:url value='/CartItemServlet?method=myCart' />">我的购物车</a>
			</div>
		</div>
	</header>
	<nav>
		<div class="nav-wrap">
				<ul class="all">
					<li><a href="<c:url value='/index.jsp'/>">首页</a></li>
					<c:forEach items="${parents }" var="parent">
						<li>
							<a href="javascript:;">${parent.cname }</a>
							<ul>
								<c:forEach items="${parent.children }" var="child">
									<li><a href="<c:url value='/BookServlet?method=findByCategory&cid=${child.cid }'/>">${child.cname }</a></li>
								</c:forEach>
							</ul>
						</li>
					</c:forEach>
				</ul>
				<!-- 
				<ul>
					<li>
						<a href="javascript:;">程序设计程序设计程序设计程序设计<span></span></a>
						<ul>
							<li><a href="">Java JavaScript</a></li>
							<li><a href="">Jsp</a></li>
							<li><a href="">C C++ VC VC++</a></li>
							<li><a href="">HTML XML</a></li>
							<li><a href="">PHP</a></li>
							<li><a href="">.NET</a></li>
							<li><a href="">hah</a></li>
						</ul>
					</li>
					<li>
						<a href="javascript:;">办公室用书<span></span></a>
						<ul>
							<li><a href="">微软Office</a></li>
							<li><a href="">计算机初级入门</a></li>
							<li><a href="">购买指南 组装指南 维修</a></li>
							<li><a href="">因特网 电子邮件</a></li>
						</ul>
					</li>
					<li>
						<a href="javascript:;">图形  图像  多媒体<span></span></a>
						<ul>
							<li><a href="">Photoshop</a></li>
							<li><a href="">3DS MAX</a></li>
							<li><a href="">网页设计</a></li>
							<li><a href="">Flush</a></li>
							<li><a href="">Dreamweaver</a></li>
							<li><a href="">Flash</a></li>
						</ul>
					</li>
					<li>
						<a href="javascript:;">操作系统/系统开发<span></span></a>
						<ul>
							<li><a href="">Windows</a></li>
							<li><a href="">Unix</a></li>
							<li><a href="">系统开发</a></li>
						</ul>
					</li>
					<li>
						<a href="javascript:;">数据库<span></span></a>
						<ul>
							<li><a href="">数据仓库与数据挖掘</a></li>
							<li><a href="">数据库理论</a></li>
						</ul>
					</li>
					<li>
						<a href="javascript:;">网络与数据通信<span></span></a>
						<ul>
							<li><a href="">电子商务 电子政务</a></li>
						</ul>
					</li>
				</ul>
				 -->
			<!--</div>-->
			<!--<ul class="nav">-->
				<!--<li><a href="<c:url value='/index.jsp'/>">首页</a></li>-->
				<!-- <li><a href="<c:url value='/BookServlet?method=findAll'/>">列表</a></li> -->
				<!--<li><a href="">联系我们</a></li>-->
			<!--</ul>-->
		</div>
	</nav>
  </body>
</html>
