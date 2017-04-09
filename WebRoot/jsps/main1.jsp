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
    
    <title>惊城书屋</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  
  <body>	
  	<jsp:include page="../CategoryServlet?method=findAll"/>
  	<div id="con-wrap">
		<div class="banner">

		</div>
		<div class="con">
		<script type="text/javascript">
		  	$(function(){
		  		$(".change").click(function(){
			  		var tr = $(".tr").val();
			  		var pc = Math.floor(parseInt(tr)*Math.random());
			  		$(".pc").val(pc);
			  		$("form1").submit();
			  	})
		  	})
		  </script>
			<div class="tuijian">
				<h2>最新推荐<a href="javascript:;" class="change">换一换</a></h2>
				<ul>
					<c:forEach items="${recommendPb.beanList }" var="book">
					<li>
						<div class="inner">
							<a class="pic" href=""><img src="<c:url value='/img/${book.image_b }'/>" border="0" /></a>
							<p class="price">
								<span class="price_n">&yen;${book.currPrice }</span>
								<span class="price_r">&yen;${book.price }</span> (
								<span class="price_s">${book.discount }折</span>)
							</p>
							<p><a id="bookname" title="${book.bname }" href="<c:url value='/BookServlet?method=findById&bname=${book.bname }'/>">${book.bname }</a></p>
							<p><a href="<c:url value='/BookServlet?method=findById&bname=${book.bname }'/>" name='P_zz' title='${book.author }'>${book.author }</a></p>
							<p class="publishing">
								<span>出 版 社：</span><a href="">${book.press }</a>
							</p>
							<p class="publishing_time"><span>出版时间：</span>${book.publishtime }</p>
						</div>
					</li>
					</c:forEach>
				</ul>
			</div>
			<form class="form1" action="<c:url value='/BookServlet' />" method="post">
			  	<input class="pc" type="hidden" name="pc" value="2">
			  	<input class="method" type="hidden" name="method" value="findRecommend">
			  	<input class="tr" type="hidden" value="${recommendPb.tr }">
			</form>
 				<!-- <jsp:include page="/BookServlet?method=findRecommend&pc=a" />
 				<jsp:include page="/BookServlet?method=findLikeJava"/> -->
 		</div>
 	</div>
  	<jsp:include page="footer.jsp"/>
  </body>
</html>
