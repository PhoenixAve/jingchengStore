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
    
    <title>My JSP 'showItem.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/showItem.css' />" />

  </head>
  
  <body>
  	<!-- 头部 -->
  	<jsp:include page="../header1.jsp" />
  	
	
    <div id="con-wrap">
		<div class="con">
			<form id="form1" action="<c:url value='/OrderServlet' />" method="post">
				<input type="hidden" name="cartItemIds" value="${cartItemIds }" />
				<input type="hidden" name="method" value="createOrder" />
				<table width="100%" align="center" cellpadding="0" cellspacing="0">
					<tr bgcolor="#efeae5">
						<td width="400px" colspan="5"><span style="font-weight: 900;">生成订单</span></td>
					</tr>
					<tr align="center">
						<td width="10%">&nbsp;</td>
						<td width="50%">图书名称</td>
						<td>单价</td>
						<td>数量</td>
						<td>小计</td>
					</tr>
					<c:forEach items="${cartItemList }" var="cartItem">
						<tr align="center">
							<td align="right">
								<a class="linkImage" href="<c:url value='/BookServlet?method=load&bid=${cartItem.book.bid }' />"><img border="0" width="54" align="top" src="<c:url value='/img/${cartItem.book.image_b }' />"/></a>
							</td>
							<td align="left">
								<a href="<c:url value='/BookServlet?method=load&bid=${cartItem.book.bid }' />"><span>${cartItem.book.bname }</span></a>
							</td>
							<td>&yen;${cartItem.book.currPrice }</td>
							<td>${cartItem.quantity }</td>
							<td>
								<span class="price_n">&yen;<span class="subtotal">${cartItem.subtotal }</span></span>
							</td>
						</tr>
					</c:forEach>

					
					<tr>
						<td colspan="6" align="right">
							<span>总计：</span><span class="price_t">&yen;<span id="total">${total }</span></span>
						</td>
					</tr>
					<tr>
						<td colspan="5" bgcolor="#efeae5"><span style="font-weight: 900">收货地址</span></td>
					</tr>
					<tr>
						<td colspan="6">
							<input id="addr" type="text" name="address" value="驻马店市  驿城区  开源大道  黄淮学院北区  #11431  余京成" />
						</td>
					</tr>
					<tr>
						<td style="border-top-width: 4px;" colspan="5" align="right">
							<a id="linkSubmit" href="javascript:$('#form1').submit();">提交订单</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	
	<!-- 尾部 -->
  	<jsp:include page="../footer.jsp" />
  </body>
</html>
