function _change() {
	$("#vCode").attr("src", "/jingchengStore/VerifyCodeServlet?" + new Date().getTime());
}