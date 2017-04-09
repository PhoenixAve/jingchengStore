$(function() {
	var total = 0;
	$(".subtotal").each(function() {
		total += Number($(this).text());
	});
	$("#total").text(round(total, 2));
});