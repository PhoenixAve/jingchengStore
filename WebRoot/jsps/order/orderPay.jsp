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
    
    <title>My JSP 'pay.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/pay.css' />" />

  </head>
  
  <body>
    <!-- 头部 -->
  	<jsp:include page="../header1.jsp" />
  	
  	<div id="con-wrap">
		<div class="con">
			<div class="divContent">
				<span class="spanPrice">支付金额</span><span class="price_t">&yen;${order.total }</span>
				<span class="spanOid">编号：${order.oid }</span>
			</div>
			<form action="<c:url value='/OrderServlet'/>" method="post" id="form1" target="_top">
				<input type="hidden" name="method" value="payment" />
				<input type="hidden" name="oid" value="${order.oid }" />
				<div class="divBank">
					<div class="divText">选择网上银行</div>
					<div style="margin-left: 20px;">
						<!--<div style="margin-bottom: 20px;">-->
							<input id="ICBC-NET-B2C" type="radio" name="yh" value="ICBC-NET-B2C" checked="checked" />
							<label for="ICBC-NET-B2C"><img name="ICBC-NET-B2C" align="middle" src="img/bank_img/icbc.bmp" /></label>

							<input id="CMBCHINA-NET-B2C" type="radio" name="yh" value="CMBCHINA-NET-B2C" />
							<label for="CMBCHINA-NET-B2C"><img name="CMBCHINA-NET-B2C" align="middle" src="img/bank_img/cmb.bmp" /></label>

							<input id="ABC-NET-B2C" type="radio" name="yh" value="ABC-NET-B2C" />
							<label for="ABC-NET-B2C"><img name="ABC-NET-B2C" align="middle" src="img/bank_img/abc.bmp" /></label>

							<input id="CCB-NET-B2C" type="radio" name="yh" value="CCB-NET-B2C" />
							<label for="CCB-NET-B2C"><img name="CCB-NET-B2C" align="middle" src="img/bank_img/ccb.bmp" /></label>
						<!--</div>-->
						<!--<div style="margin-bottom: 20px;">-->
							<input id="BCCB-NET-B2C" type="radio" name="yh" value="BCCB-NET-B2C" />
							<label for="BCCB-NET-B2C"><img name="BCCB-NET-B2C" align="middle" src="img/bank_img/bj.bmp" /></label>

							<input id="BOCO-NET-B2C" type="radio" name="yh" value="BOCO-NET-B2C" />
							<label for="BOCO-NET-B2C"><img name="BOCO-NET-B2C" align="middle" src="img/bank_img/bcc.bmp" /></label>

							<input id="CIB-NET-B2C" type="radio" name="yh" value="CIB-NET-B2C" />
							<label for="CIB-NET-B2C"><img name="CIB-NET-B2C" align="middle" src="img/bank_img/cib.bmp" /></label>

							<input id="NJCB-NET-B2C" type="radio" name="yh" value="NJCB-NET-B2C" />
							<label for="NJCB-NET-B2C"><img name="NJCB-NET-B2C" align="middle" src="img/bank_img/nanjing.bmp" /></label>
						<!--</div>-->
						<!--<div style="margin-bottom: 20px;">-->
							<input id="CMBC-NET-B2C" type="radio" name="yh" value="CMBC-NET-B2C" />
							<label for="CMBC-NET-B2C"><img name="CMBC-NET-B2C" align="middle" src="img/bank_img/cmbc.bmp" /></label>

							<input id="CEB-NET-B2C" type="radio" name="yh" value="CEB-NET-B2C" />
							<label for="CEB-NET-B2C"><img name="CEB-NET-B2C" align="middle" src="img/bank_img/guangda.bmp" /></label>

							<input id="BOC-NET-B2C" type="radio" name="yh" value="BOC-NET-B2C" />
							<label for="BOC-NET-B2C"><img name="BOC-NET-B2C" align="middle" src="img/bank_img/bc.bmp" /></label>

							<input id="PINGANBANK-NET" type="radio" name="yh" value="PINGANBANK-NET" />
							<label for="PINGANBANK-NET"><img name="PINGANBANK-NET" align="middle" src="img/bank_img/pingan.bmp" /></label>
						<!--</div>-->
						<!--<div style="margin-bottom: 20px;">-->
							<input id="CBHB-NET-B2C" type="radio" name="yh" value="CBHB-NET-B2C" />
							<label for="CBHB-NET-B2C"><img name="CBHB-NET-B2C" align="middle" src="img/bank_img/bh.bmp" /></label>

							<input id="HKBEA-NET-B2C" type="radio" name="yh" value="HKBEA-NET-B2C" />
							<label for="HKBEA-NET-B2C"><img name="HKBEA-NET-B2C" align="middle" src="img/bank_img/dy.bmp" /></label>

							<input id="NBCB-NET-B2C" type="radio" name="yh" value="NBCB-NET-B2C" />
							<label for="NBCB-NET-B2C"><img name="NBCB-NET-B2C" align="middle" src="img/bank_img/ningbo.bmp" /></label>

							<input id="ECITIC-NET-B2C" type="radio" name="yh" value="ECITIC-NET-B2C" />
							<label for="ECITIC-NET-B2C"><img name="ECITIC-NET-B2C" align="middle" src="img/bank_img/zx.bmp" /></label>
						<!--</div>-->
						<!--<div style="margin-bottom: 20px;">-->
							<input id="SDB-NET-B2C" type="radio" name="yh" value="SDB-NET-B2C" />
							<label for="SDB-NET-B2C"><img name="SDB-NET-B2C" align="middle" src="img/bank_img/sfz.bmp" /></label>

							<input id="GDB-NET-B2C" type="radio" name="yh" value="GDB-NET-B2C" />
							<label for="GDB-NET-B2C"><img name="GDB-NET-B2C" align="middle" src="img/bank_img/gf.bmp" /></label>

							<input id="SHB-NET-B2C" type="radio" name="yh" value="SHB-NET-B2C" />
							<label for="SHB-NET-B2C"><img name="SHB-NET-B2C" align="middle" src="img/bank_img/sh.bmp" /></label>

							<input id="SPDB-NET-B2C" type="radio" name="yh" value="SPDB-NET-B2C" />
							<label for="SPDB-NET-B2C"><img name="SPDB-NET-B2C" align="middle" src="img/bank_img/shpd.bmp" /></label>
						<!--</div>-->
						<!--<div style="margin-bottom: 20px;">-->
							<input id="POST-NET-B2C" type="radio" name="yh" value="POST-NET-B2C" />
							<label for="POST-NET-B2C"><img name="POST-NET-B2C" align="middle" src="img/bank_img/post.bmp" /></label>

							<input id="BJRCB-NET-B2C" type="radio" name="yh" value="BJRCB-NET-B2C" />
							<label for="BJRCB-NET-B2C"><img name="BJRCB-NET-B2C" align="middle" src="img/bank_img/beijingnongshang.bmp" /></label>

							<input id="HXB-NET-B2C" type="radio" name="yh" value="HXB-NET-B2C" />
							<label for="HXB-NET-B2C"><img name="HXB-NET-B2C" align="middle" src="img/bank_img/hx.bmp" /></label>

							<input id="CZ-NET-B2C" type="radio" name="yh" value="CZ-NET-B2C" />
							<label for="CZ-NET-B2C"><img name="CZ-NET-B2C" align="middle" src="img/bank_img/zheshang.bmp" /></label>
						<!--</div>-->
					</div>
					<div style="margin: 40px;">
						<a href="javascript:void $('#form1').submit();" class="linkNext">下一步</a>
					</div>
				</div>
		</div>
	</div>
  	
  	<!-- 尾部 -->
  	<jsp:include page="../footer.jsp" />
  </body>
</html>
