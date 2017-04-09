<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>订单列表</title>
	
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
		<link rel="stylesheet" type="text/css" href="styles.css">
		-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/adminjsps/css/orderList.css'/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/css.css'/>" />

</head>

<body>
	<jsp:include page="../adminHeader.jsp" />
	<script type="text/javascript" charset="utf-8">
		$(function() {
			$("nav a").eq(3).addClass("active");
		})
	</script>
	<div class="con">
		<jsp:include page="/admin/AdminOrderServlet?method=findAll" />
	</div>
</body>
</html>
