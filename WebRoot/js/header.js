$(function() {
	//顶部导航切换
	$(".all").hover(function(){
		$(this).children("ul").stop().slideDown(300);
	},function(){
		$(this).children("ul").stop().slideUp(300);
		$(".all > ul > li span").css({
			"-webkit-transform": "rotate(0deg)"
		})
		$(".all ul ul").stop().slideUp(100);
	})
	$(".all > ul > li").click(function() {
		$(this).siblings("li").children("a").removeClass("active");
		$(this).siblings("li").children("a").children("span").css({
			"-webkit-transform": "rotate(0deg)"
		})
		$(this).siblings("li").children("ul").stop().slideUp();
		$(this).children("a").addClass("active");
		if ($(this).children("ul").css("display") == "" || $(this).children("ul").css("display") == "none") {
			$(this).children("ul").stop().slideDown();
			$(this).children("a").children("span").css({
				"-webkit-transform": "rotate(45deg)"
			})
		} else {
			$(this).children("ul").stop().slideUp();
			$(this).children("a").children("span").css({
				"-webkit-transform": "rotate(0deg)"
			})
		}
	})
	$(".user-info").hover(function(){
		$(this).children(".user-name").css({"backgroundColor":"#0089CB", "color":"#fff"});
		$(this).children("ul").stop().slideDown(200);
	},function(){
		$(this).children(".user-name").css({"backgroundColor":"", "color":"#333"});
			$(this).children("ul").stop().slideUp(200);
	})
//	$("aside > ul > li").hover(function() {
//		$(this).children("ul").show();
//		$(this).children("a").children("span").css({
//				"-webkit-transform": "rotate(135deg)"
//		});
//	},function(){
//		$("aside > ul > li > ul").hide();
//		$("aside > ul > li a span").css({
//				"-webkit-transform": "rotate(45deg)"
//		});
//	})
})