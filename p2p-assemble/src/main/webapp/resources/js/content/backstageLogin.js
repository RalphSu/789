define(function(require) {
	var Site = require('../comp/init.js');
	var form = $('#backstageLogin_form');
	$('#backstageLogin').click(function() {
		if(validateForm()){
			form.submit();
		}
		return false;
	})
	
	/* validate */
	function validateForm(){
		var user = $('input[name=userName]');
		if ($.trim(user.val()) === ''){
			$('.err').eq(0).html('请填写账户或密码，密码不小于6位!');
			$('.err').show();
			return false;
		}
		return true;
	}
	
	if (form.length) {
		form.validate({
			errorClass : 'error-tip',
			errorElement : 'b',
			errorPlacement : function(error, element) {
				if (element.attr('name') == 'imgCode') {
					element.next().next().after(error);
				} else {
					element.after(error);
				}
			},
			rules : {
				userName : {
					required : true
				},
				logPassword : {
					required : true
				}

			},
			messages : {
				userName : {
					required : '请输入用户名'
				},
				logPassword : {
					required : '请输入登录密码'
				}
			},
			onkeyup : false
		});
	}


    var $inp = $('input:text');
    $inp.bind('keydown', function (e) {
        var key = e.which;
        if (key == 13) {
            e.preventDefault();
            $('#backstageLogin').trigger("click");
        }

    });


});