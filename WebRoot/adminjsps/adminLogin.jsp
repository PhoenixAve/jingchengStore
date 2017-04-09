<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="zh-CN">

	<head>
		<meta charset="UTF-8">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<c:url value='/adminjsps/css/adminLogin.css'/>" />
		<title>管理员登录</title>
	</head>
	<script type="text/javascript" src="<c:url value='/js/jquery-1.11.2.min.js'/>"></script>
	<script type="text/javascript">
		function checkForm() {
			if(!$("#adminname").val()) {
				alert("管理员名称不能为空！");
				return false;
			}
			if(!$("#adminpwd").val()) {
				alert("管理员密码不能为空！");
				return false;
			}
			return true;
		}
	</script>
	<body class="login">

		<div class="login_m">
			<div class="login_logo"><img src="<c:url value='/img/adminLogo.png' />"></div>
			<div class="login_boder">
				<p class="msg" style="font-weight: 900; color: red">${msg }</p>
				<form action="<c:url value='/AdminServlet'/>" method="post" onsubmit="return checkForm();" target="_top">
				<input type="hidden" name="method" value="login"/>
					<div class="login_padding">
						<h2>管理员账号</h2>
						<label>
							<input type="text" name="adminname" id="adminname" class="txt_input txt_input2" placeholder="管理员账号">
						</label>
						<h2>密码</h2>
						<label>
							<input type="password" name="adminpwd" id="adminpwd" class="txt_input" placeholder="密码">
						</label>
						<div class="rem_sub">
							<input type="submit" class="sub_button" name="button" id="button" value="登录" style="opacity: 0.7;">
						</div>
					</div>
				</form>
			</div>
			<!--login_boder end-->
		</div>
		<!--login_m end-->

	</body>

</html>
