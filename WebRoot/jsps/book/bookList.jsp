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
    
    <title>My JSP 'list.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/list.css'/>" />

  </head>
  
  <body>
  	<!-- 头部 -->
  	<jsp:include page="../header1.jsp" />

	<script src="<c:url value='/js/list.js'/>" type="text/javascript" charset="utf-8"></script>
	<div id="con-wrap">
		<div class="con">
			<ul>
				<c:forEach items="${pb.beanList }" var="book">
				<li>
					<div class="inner">
						<a class="pic" href="<c:url value='/BookServlet?method=load&bid=${book.bid }'/>"><img src="<c:url value='/img/${book.image_b }' />" border="0" /></a>
						<p class="price">
							<span class="price_n">&yen;${book.currPrice }</span>
							<span class="price_r">&yen;${book.price }</span> (
							<span class="price_s">${book.discount }折</span>)
						</p>
						<p><a id="bookname" title="${book.bname }" href="<c:url value='/BookServlet?method=load&bid=${book.bid }'/> ">${book.bname }</a></p>
						<%-- url标签会自动对参数进行url编码 --%>
						<c:url value="/BookServlet" var="authorUrl">
							<c:param name="method" value="findByAuthor"/>
							<c:param name="author" value="${book.author }"/>
						</c:url>
						<c:url value="/BookServlet" var="pressUrl">
							<c:param name="method" value="findByPress"/>
							<c:param name="press" value="${book.press }"/>
						</c:url>
						<p><a href="${authorUrl }" name='P_zz' title='${book.author }'>${book.author }</a></p>
						<p class="publishing">
							<span>出 版 社：</span><a href="${pressUrl }">${book.press }</a>
						</p>
						<p class="publishing_time"><span>出版时间：</span>${book.publishtime }</p>
					</div>
				</li>
				</c:forEach>
			</ul>
			<jsp:include page="../pager/pager.jsp" />
		</div>
	</div>
	
	<!-- 尾部 -->
  	<jsp:include page="../footer.jsp" />
  </body>
</html>