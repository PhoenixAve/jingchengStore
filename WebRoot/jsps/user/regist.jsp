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
    
    <title>注册</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/regist.css'/>">

	<script src="<c:url value='/js/jquery-1.11.2.min.js'/>" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript" src="<c:url value='/js/regist.js'/>"></script>
  </head>
  
  <body>
	<div id="divMain">
	  <div id="divTitle">
	    <span id="spanTitle">新用户注册</span>
	  </div>
	  <div id="divBody">
		<form action="<c:url value='/UserServlet'/>" method="post" id="registForm">
			<input type="hidden" name="method" value="regist"/>  
		    <table id="tableForm">
		      <tr>
		        <td class="tdText">用户名：</td>
		        <td class="tdInput">
		          <input class="inputClass" type="text" name="loginname" id="loginname" value="${form.loginname }" placeholder="请输入用户名"/>
		        </td>
		        <td class="tdError">
		          <label class="errorClass" id="loginnameError">${errors.loginname }</label>
		        </td>
		      </tr>
		      <tr>
		        <td class="tdText">登录密码：</td>
		        <td>
		          <input class="inputClass" type="password" name="loginpass" id="loginpass" value="${form.loginpass }" placeholder="请输入密码"/>
		        </td>
		        <td>
		          <label class="errorClass" id="loginpassError">${errors.loginpass }</label>
		        </td>
		      </tr>
		      <tr>
		        <td class="tdText">确认密码：</td>
		        <td>
		          <input class="inputClass" type="password" name="reloginpass" id="reloginpass" value="${form.reloginpass }" placeholder="请确认密码"/>
		        </td>
		        <td>
		          <label class="errorClass" id="reloginpassError">${errors.reloginpass}</label>
		        </td>
		      </tr>
		      <tr>
		        <td class="tdText">Email：</td>
		        <td>
		          <input class="inputClass" type="text" name="email" id="email" value="${form.email }" placeholder="请输入Email"/>
		        </td>
		        <td>
		          <label class="errorClass" id="emailError">${errors.email}</label>
		        </td>
		      </tr>
		      <tr>
		        <td class="tdText">验证码：</td>
		        <td>
		          <input class="inputClass" type="text" name="verifyCode" id="verifyCode" value="${form.verifyCode }" placeholder="请输入下方验证码"/>
		        </td>
		        <td>
		          <label class="errorClass" id="verifyCodeError">${errors.verifyCode}</label>
		        </td>
		      </tr>
		      <tr>
		        <td></td>
		        <td>
		          <div id="divVerifyCode"><img id="imgVerifyCode" src="<c:url value='/VerifyCodeServlet'/>"/></div>
		        </td>
		        <td>
		          <label><a class="change" href="javascript:_hyz()">看不清换一张</a></label>
		        </td>
		      </tr>
		      <tr>
		        <td></td>
		        <td>
		          <!--<input type="image" src="<c:url value='/img/regist1.jpg'/>" id="submitBtn"/>-->
		          <input type="submit" id="submitBtn" value="立即注册"/>
		        </td>
		        <td>
		          <label></label>
		        </td>
		      </tr>
		    </table>
		</form>    
	  </div>
	</div>
  </body>
</html>
