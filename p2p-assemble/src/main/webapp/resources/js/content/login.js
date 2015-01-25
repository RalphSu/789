define(function(require) {
	var Site = require('../comp/init.js');
	require('../Y-all/Y-script/Y-tip.js');
	var verify = $('.new_captcha');
	//require('../content/securityPassword.js')(210,31);
	require('../content/securityPassword.js');

    var newcaptcha = $('#newcaptcha');
	verify.click(function() {
		var img = new Image();
		img.src = '/anon/getImgCode?dateTag=' + new Date().getTime();
		img.onload = function(){
			var obj = $(img).addClass('code-img vt new_captcha');
			obj.attr('title',newcaptcha.attr('title')).css({width:85,height:20});
			newcaptcha.after(obj);
			newcaptcha.remove();
			obj.click(function(){
				verify.click();
			});
			newcaptcha = obj;
		};
	});
	$('[name=userName]').val(getCookie("userName"));
	var login_form = $('#login_form');
	
	$('#submit-a').click(function() {
		var captcha = $("#verifyCode").val();
		var url = "/login/validateCaptcha";
		var data = {'captcha' : captcha};
		var result = $_GLOBAL.ajax(url, data);
		if(1!=result.code){
			$(".validIcon").removeClass("correctCircleIcon");
			$(".validIcon").addClass("failureCircleIcon");
			$(".err").text("验证码校验失败！");
			$(".err").css("display", "inline-block");
			return false;
		}
		login_form.submit();
		return false;
	})

	login_form.submit(function(){
		return validateForm();
	});
	
	
	function validateForm(){
		var user = $('input[name=userName]');
		if ($.trim(user.val()) == '' || $('#password').val()==''){
			$('.err').eq(0).html('请填写账户或密码，密码不小于6位!');
			$('.err').show();
			return false;
		}
		setCookie("userName", $('[name=userName]').val());
//		$.getCookie("name");
//		$.deleteCookie("name");
		return true;
	}
	$("#verifyCode").Y('ToolTip',{
	    content:'验证码为全英文字符, 不区分大小写',
	    align: 'top'
	});
	
	$("#verifyCode").blur(
			function (){
				var captcha = $(this).val();
				var url = "/login/validateCaptcha";
				var data = {'captcha' : captcha};
				var result = $_GLOBAL.ajax(url, data);
				if(1==result.code){
					$(".validIcon").removeClass("failureCircleIcon");
					$(".validIcon").addClass("correctCircleIcon");
				}else{
					$(".validIcon").removeClass("correctCircleIcon");
					$(".validIcon").addClass("failureCircleIcon");
				}
			}		
	);
	
	/*
	jQuery cookie
	*/
	 function setCookie (sName, sValue, oExpires, sPath, sDomain, bSecure) {
		var sCookie = sName + '=' + encodeURIComponent(sValue);
		if (oExpires) {
			sCookie += '; expires=' + oExpires.toGMTString();
		};
		if (sPath) {
			sCookie += '; path=' + sPath;
		};
		if (sDomain) {
			sCookie += '; domain=' + sDomain;
		};
		if (bSecure) {
			sCookie += '; secure';
		};
		document.cookie = sCookie;
	};
	
	 function getCookie(sName) {
		var sRE = '(?:; )?' + sName + '=([^;]*)';
		var oRE = new RegExp(sRE);
		if (oRE.test(document.cookie)) {
			return decodeURIComponent(RegExp['$1']);
		} else {
			return null;
		};
	}
	
	function deleteCookie(sName, sPath, sDomain) {
		this.setCookie(sName, '', new Date(0), sPath, sDomain);
	}



     var $inp = $('input:text');
     $inp.bind('keydown', function (e) {
            var key = e.which;
            if (key == 13) {
                e.preventDefault();
                $('#submit-a').trigger("click");
            }

    });

     
    var loginForm = $("#login_Form");
 	if (loginForm.length) {
 		loginForm.validate({
 			
 			errorClass : 'error-tip',
 			errorElement : 'b',
 			errorPlacement : function(error, element) {
 				if (element.attr('name') == 'imgCode'||element.attr('name') == 'license') {
 					element.next().next().after(error);
 				} else {
 					element.after(error);
 				}
 			},
 			rules : {
 				userName : {
 					required : true,
 				},
 				password : {
 					required : true
 				},
 				captcha : {
 					required : true,
 				}
 			},
 			messages : {
 				userName : {
 					required : ' 请输入手机号码'
 				},
 				password : {
 					required : ' 请输入密码'
 				},
 				captcha : {
 					required : ' 请输入验证码'
 				}
 			},
 			onkeyup : false

 		});
 	}
 	$("#confirm").click(function() {
 		var option = {
 			url : '/login/checkImgCode',
 			dataType : 'json',
			data : {
				captcha : $("#captcha").val()
			},
			async : false,
			success : function(res) {
				if (res.code == 1) {
					loginForm.submit();
				} else {
					$("#messageSpan").html("验证码输入有误!");
				}
			},
			error : function() {
				$("#messageSpan").html("网络连接失效，请刷新页面！");
			}
 		};
 		$.ajax(option);
 		return false;
 	});
 	
 	$("#forgetPassWord").click(function() {
 		var userName = $("input[name = 'userName']").val();
 		if(null == userName || '' == userName) {
 			return ;
 		}
 		window.location.href = "/PasswordManage/newLogPasswordMail?userName=" + userName;
 		return false;
 	});
});