$(function() {
	//顶部导航切换
	var temp = 0;
	$(".all > ul > li").click(function() {
		var temp = $(this).children("ul").is(":visible");
		var index = $(this).index();
		$(".all > ul > li").eq(index).children("ul").slideToggle();
		$(this).siblings().children("ul").slideUp();
		$(this).siblings().children("a").children("span").css({
				"-webkit-transform": "rotate(0deg)"
			});
		if (temp == 0) {
//			$(this).children("ul").slideDown();
			$(this).children("a").children("span").css({
				"-webkit-transform": "rotate(135deg)"
			});
			temp = 1;
		} else {
//			$(this).siblings().children("ul").slideDown();
			$(this).children("ul").slideUp();
			$(".all > ul > li a span").css({
				"-webkit-transform": "rotate(0deg)"
			});
			temp = 0;
		}
	})
})