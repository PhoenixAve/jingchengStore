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
    
    <title>My JSP 'bookDetail.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/detail.css' />" />

  </head>
  
  <body>
  	<!-- 头部 -->
  	<jsp:include page="../header1.jsp" />
  	
  	<script src="<c:url value='/js/detail.js' />" type="text/javascript" charset="utf-8"></script>
    <div id="con-wrap">
		<div class="con">
			<div class="book-wrap">
				<div class="divBookName">${book.bname }</div>
				<img align="top" src="<c:url value='/img/${book.image_w }' />" class="img_image_w" />
				<div class="divBookDesc">
					<ul>
						<li>商品编号：${book.bid }</li>
						<li>现价：<span class="price_n">&yen;${book.currPrice }</span></li>
						<li>定价：<span class="spanPrice">&yen;${book.price }</span>　折扣：<span style="color: #c30;">${book.discount }</span>折</li>
					</ul>
					<hr class="hr1" />
					<table>
						<tr>
							<td colspan="3">
								作者：${book.author } 著
							</td>
						</tr>
						<tr>
							<td colspan="3">
								出版社：${book.press }
							</td>
						</tr>
						<tr>
							<td colspan="3">出版时间：${book.publishtime }</td>
						</tr>
						<tr>
							<td>版次：${book.edition }</td>
							<td>页数：${book.pageNum }</td>
							<td>字数：${book.wordNum }</td>
						</tr>
						<tr>
							<td width="180">印刷时间：${book.printtime }</td>
							<td>开本：${book.booksize } 开</td>
							<td>纸张：${book.paper }</td>
						</tr>
					</table>
					<div class="divForm">
						<form id="form1" action="<c:url value='/CartItemServlet'/>" method="post">
							<input type="hidden" name="method" value="add" />
							<input type="hidden" name="bid" value="${book.bid }" /> 我要买：
							<input id="cnt" type="text" name="quantity" value="1" />件
						</form>
						<a id="btn" href="javascript:$('#form1').submit();"></a>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 尾部 -->
  	<jsp:include page="../footer.jsp" />
  </body>
</html>
