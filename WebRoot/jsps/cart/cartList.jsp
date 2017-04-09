<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<%=basePath%>">

<title>My JSP 'list.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/cartList.css'/>" />

</head>

<body>
	<!-- 头部 -->
	<jsp:include page="../header1.jsp" />

	<script src="<c:url value='/js/round.js'/>" type="text/javascript" charset="utf-8"></script>
	<script src="<c:url value='/js/cartList.js'/>" type="text/javascript" charset="utf-8"></script>
	<div id="con-wrap">
		<div class="con">
			<c:choose>
				<c:when test="${empty cartItemList }">
					<table width="100%" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td align="right">
								<img align="top" src="<c:url value='/img/icon_empty.png' />" />
							</td>
							<td>
								<span class="spanEmpty">您的购物车中暂时没有商品</span>
							</td>
						</tr>
					</table>
				</c:when>
				<c:otherwise>
					<table width="100%" align="center" cellpadding="0" cellspacing="0">
						<tr align="center" bgcolor="#efeae5">
							<td align="left" width="50px">
								<input type="checkbox" id="selectAll" checked="checked" /><label for="selectAll">全选</label>
							</td>
							<td colspan="2">商品名称</td>
							<td>单价</td>
							<td>数量</td>
							<td>小计</td>
							<td>操作</td>
						</tr>
						<c:forEach items="${cartItemList }" var="cartItem">
							<tr align="center">
								<td align="left">
									<input value="${cartItem.cartItemId }" type="checkbox" name="checkboxBtn" checked="checked" />
								</td>
								<td align="left" width="70px">
									<a class="linkImage" href="<c:url value='/BookServlet?method=load&bid=${cartItem.book.bid }' />"><img border="0" width="54" align="top" src="<c:url value='/img/${cartItem.book.image_b }' />" /> </a>
								</td>
								<td align="left" width="400px">
									<a href="<c:url value='/BookServlet?method=load&bid=${cartItem.book.bid }' />"><span>${cartItem.book.bname }</span></a>
								</td>
								<td>
									<span>&yen;<span class="currPrice" id="${cartItem.cartItemId }CurrPrice">${cartItem.book.currPrice }</span></span>
								</td>
								<td>
									<a class="jian" id="${cartItem.cartItemId }Jian"></a><input class="quantity" readonly="readonly" id="${cartItem.cartItemId }Quantity" type="text" value="${cartItem.quantity }" /><a class="jia" id="${cartItem.cartItemId }Jia"></a>
								</td>
								<td width="100px">
									<span class="price_n">&yen;<span class="subTotal" id="${cartItem.cartItemId }Subtotal">${cartItem.subtotal }</span></span>
								</td>
								<td>
									<a href="<c:url value='/CartItemServlet?method=batchDelete&cartItemIds=${cartItem.cartItemId }'/>">删除</a>
								</td>
							</tr>
						</c:forEach>

						<tr>
							<td colspan="4" class="tdBatchDelete">
								<a href="javascript:batchDelete();">批量删除</a>
							</td>
							<td colspan="3" align="right" class="tdTotal">
							<span>总计：</span><span class="price_t">&yen;<span id="total"></span></span>
							</td>
						</tr>
						<tr>
							<td colspan="7" align="right">
								<a href="javascript:jiesuan();" id="jiesuan" class="jiesuan"></a>
							</td>
						</tr>
					</table>
					<form id="jieSuanForm" action="<c:url value='/CartItemServlet'/>" method="post">
						<input type="hidden" name="cartItemIds" id="cartItemIds" />
						<input type="hidden" name="total" id="hiddenTotal" /> 
						<input type="hidden" name="method" value="loadCartItems" />
					</form>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<!-- 尾部 -->
	<jsp:include page="../footer.jsp" />
</body>
</html>
