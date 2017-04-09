$(function() {
	//顶部导航切换
	$(".all > li").hover(function(){
		$(this).children("a").css({backgroundColor:"#009EEB"});
		$(this).children("ul").stop().slideDown(300);
	},function(){
		$(this).children("a").css({backgroundColor:"#0089CB"});
		$(this).children("ul").stop().slideUp(300);
	})
	$(".user-info").hover(function(){
		$(this).children(".user-name").css({"backgroundColor":"#0089CB", "color":"#fff"});
		$(this).children("ul").stop().slideDown(200);
	},function(){
		$(this).children(".user-name").css({"backgroundColor":"", "color":"#333"});
			$(this).children("ul").stop().slideUp(200);
	})
})