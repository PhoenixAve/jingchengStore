$(function(){
	$('#password1').complexify({minimumChars:6, strengthScaleFactor:0.7}, function(valid, complexity){
		var calculated = (complexity/100)*268 - 134;
		var prop = 'rotate('+(calculated)+'deg)';
		
		// Rotate the arrow
		$('#main .arrow').css({
			'-moz-transform':prop,
			'-webkit-transform':prop,
			'-o-transform':prop,
			'-ms-transform':prop,
			'transform':prop
		});
	});
});
