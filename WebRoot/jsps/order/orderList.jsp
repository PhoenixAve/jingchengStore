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
    
    <title>My JSP 'orderList.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/orderList.css' />" />

  </head>
  
  <body>
    <!-- 头部 -->
  	<jsp:include page="../header1.jsp" />
  	
  	<div id="con-wrap">
		<div class="con">
			<div class="divMain">
				<div class="divTitle">
					<span style="display: inline-block; width: 661px; text-align: center;">商品信息</span>
					<span style="display: inline-block; width: 151px; text-align: center;">金额</span>
					<span style="display: inline-block; width: 151px; text-align: center;">订单状态</span>
					<span style="display: inline-block; width: 221px; text-align: center;">操作</span>
				</div>
				<br/>
				<table align="center" border="0" width="100%" cellpadding="0" cellspacing="0">
				<c:forEach items="${pb.beanList }" var="order">
					<tr class="tt">
						<td width="350px">订单号：<a href="<c:url value='/OrderServlet?method=load&oid=${order.oid }' />">${order.oid }</a></td>
						<td width="300px">下单时间：${order.ordertime }</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td colspan="2">
						<c:forEach items="${order.orderItemList }" var="orderItem">
							<a class="link2" href="<c:url value='/BookServlet?method=load&bid=${orderItem.book.bid }' />">
								<img border="0" width="70" src="<c:url value='/img/${orderItem.book.image_b }' />" />
							</a>
						</c:forEach>
						</td>
						<td width="150px" align="center">
							<span class="price_t">&yen;${order.total }</span>
						</td>
						<td width="150px" align="center">
						<c:choose>
							<c:when test="${order.status eq 1 }">(等待付款)</c:when>
							<c:when test="${order.status eq 2 }">(准备发货)</c:when>
							<c:when test="${order.status eq 3 }">(等待确认)</c:when>
							<c:when test="${order.status eq 4 }">(交易成功)</c:when>
							<c:when test="${order.status eq 5 }">(已取消)</c:when>
						</c:choose>
						</td>
						<td align="center">
							<a href="<c:url value='/OrderServlet?method=load&oid=${order.oid }' />">查看</a><br/>
							<c:if test="${order.status eq 1 }">
							<a href="<c:url value='/OrderServlet?method=paymentPre&oid=${order.oid }' />">支付</a><br/>
							<a href="<c:url value='/OrderServlet?method=load&oid=${order.oid }&btn=cancle' />">取消</a><br/>
							</c:if>
							<c:if test="${order.status eq 3 }">
							<a href="<c:url value='/OrderServlet?method=load&oid=${order.oid }&btn=confirm' />">确认收货</a><br/>
							</c:if>
						</td>
					</tr>
				</c:forEach>
				</table>
				<jsp:include page="../pager/pager.jsp" />
			</div>
		</div>
	</div>
  	
  	<!-- 尾部 -->
  	<jsp:include page="../footer.jsp" />
  </body>
</html>
