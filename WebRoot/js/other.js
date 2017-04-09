function placeHolder(goal,str){
	$('.password').focus(function(event) {
			$(this).attr('type', 'password');
		}).blur(function(event) {
			if ($(this).val()=='') {
				$(this).attr('type', 'text');									
			};
		});
	$(goal).focus(function(event) {
		if ($(this).val() == str) {
			$(this).val('');
		}
	});
	$(goal).blur(function(event) {
		if ($(this).val() == '' || $(goal).val() == null) {
			$(this).val(str);
		}
	})
}