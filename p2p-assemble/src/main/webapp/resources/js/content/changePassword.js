define(function(require) {
	var Site = require('../comp/init.js');
	require('../plugins/jquery.box.js');
	require('../plugins/jquery.combobox.js');
	require('../comp/security.js');
	
	var password_form = $('#doSetNewPassword_form');
	
	if (password_form.length) {
		password_form.validate({
			errorClass : 'error-tip',
			errorElement : 'b',
			errorPlacement : function(error, element) {
				if (element.attr('name') == 'businessPeriod') {
					element.parent().next().after(error);
				} else {
					element.after(error);
				}
			},
			rules : {
				mobile : {
					required : true
				},
				newLogPassword : {
					required: true,
					minlength: 6
				},
				reNewLogPassword : {
					required : true,
					equalTo : $("input[name=newLogPassword]")
				}
			},
			messages : {
				mobile : {
					required : "手机号不能为空"
				},
				newLogPassword : {
					required: '请填入登录密码',
					range: '登录密码为6-20位'
				},
				reNewLogPassword : {
					required: '请再次确认登录密码',
					equalTo: '两次输入的不一致'
				}
			},
			onkeyup : false

		});

	}
	
	$("#sendMsg").click(function() {
		var mobile = $("input[name='mobile']").val();
		var userName = $("input[name='userName']").val();
		$("#msgError").html("");
		if(null == mobile || "" == mobile) {
			$("#msgError").html("手机号不能为空");
			return false;
		}
		var data = {'mobile' : mobile,'userName':userName};
		var result = $_GLOBAL.ajax("/anon/sendSmsVerifyCode", data);
		if(result.res == "success") {
			//$("#msgError").html("发送短信成功");
			alert("发送短信成功");
		} else {
			alert("发送短信失败");
			//$("#msgError").html("发送短信失败");
		}
		return false;
	
	});
	
	$("#next_changePsw").click(function() {
		var mobile = $("input[name='mobile']").val();
		$("#msgError").html("");
		if(null == mobile || "" == mobile) {
			$("#msgError").html("手机号不能为空");
			return false;
		}
		var verifyCode = $("input[name='verifyCode']").val();
		$("#sendError").html("");
		if(null == verifyCode || "" == verifyCode) {
			$("#sendError").html("验证码不能为空");
			return false;
		}
		var data = {'mobile' : mobile, 'verifyCode' : verifyCode};
		var result = $_GLOBAL.ajax("/PasswordManage/verifyCode", data);
		if(result.res != "success") {
			$("#nextError").html("短信校验失败");
			return false;
		}
		window.location.href = "/PasswordManage/changePassword?md5UserBaseId=" + $("input[name='md5UserBaseId']").val();
		return false;
	});
	
	$("#updatePsw").click(function() {
		password_form.submit();
		return false;
	});
});