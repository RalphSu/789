define(function(require) {
	var Site = require('../comp/init.js');
	require('../Y-all/Y-script/Y-tip.js');
	require('../Y-all/Y-script/Y-countdown.js');

	$('#setNewPasswordButton').click(function(){
		//var userName = $('input[name=userName]').val();
		//if(userName == ''){
		//	$('#nextError').html('请输入用户名。');
		//	return;
		//}

		var mobile = $("#validPhone").val();
		if(mobile == ''){
			$('#nextError').html('请输入手机号码。');
			return;
		}
		if(!(/^1\d{10}$/.test(mobile))){
			$('#nextError').html('请输入正确的手机号码。');
			return;
		}

		var verifyCode = $('input[name=verifyCode]').val();
		if(verifyCode == ''){
			$('#nextError').html('请填写验证码。');
			return;
		}

		$('#setNewPasswordForm').submit();
	});

	// -------------------------------------发送手机验证码-----------------------------------------------
	var countdown = null;
	$('#sendMsg').click(function() {
		$('#nextError').html('');
		var business = $("#smsBizType").val();
		var mobile = $("#validPhone").val();

		countdown = Y.getCmp('sendMsg');
		if(mobile == ''){
			$('#nextError').html('请输入手机号码。');
			countdown.close(0);
			return;
		}
		if(!(/^1\d{10}$/.test(mobile))){
			$('#nextError').html('请输入正确的手机号码。');
			countdown.close(0);
			return;
		}
		if (!$("#validPhone").valid()) {
			countdown.close(0);
			return;
		} else {
			sendMobile(business, mobile, countdown);
		}
	});

	function sendMobile(business, mobile, conutdown) {
		$.ajax({
			url : '/anon/sendSmsCode',
			dataType : 'json',
			data : {
				mobile : mobile,
				business : business
			},
			cache : false,
			success : function(res) {
				if (res.code == 1) {
				} else {
					$('#nextError').html(res.message || '');
					if (countdown) {
						countdown.close();
					}
				}
			},
			error : function() {
				alert('获取动态验证码失败');
				if (countdown) {
					countdown.close();
				}
			}
		});
	}
});