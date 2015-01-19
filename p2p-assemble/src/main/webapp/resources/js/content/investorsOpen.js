define(function(require) {
	var Site = require('../comp/init.js');
	require('../Y-all/Y-script/Y-tip.js');
	require('../Y-all/Y-script/Y-countdown.js');
	
	$('input[name=realName]').Y('RareWordTip',{
		showEle: $('b.fn-tip a')
	});
	
	var newImgCod = $('.newImgCod');
	newImgCod.click(function() {
		$('#newImgCod').attr('src','/anon/getImgCode?dateTag=' + new Date().getTime());
	});
	
	var investorsOpen_form = $('#investorsOpen_form');
	if (investorsOpen_form.length) {
		investorsOpen_form.validate({
			
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
					rangelength:[6,20],
					NumandLetter_ : true,
					customRemote : {
						url : '/anon/checkUserName?dateTag=' + new Date().getTime(),
						customError : function(element, res) {
							return res.message;
						}
					}
				},
				realName : {
					required : true
				},
				logPassword : {
					required : true,
					rangelength:[6,20]
				},
				confirmLogPassword : {
					required : true,
//					checkCname: $('input[name=confirmLogPassword]').val() ==  $('input[name=logPassword]').val()
					equalTo : $('input[name=logPassword]')
				},
//				certNo : {
//					required : true,
//					checkID : true,
//					customRemote : {
//						url : '/anon/checkCertNo?dateTag=' + new Date().getTime(),
//						customError : function(element, res) {
//							return res.message;
//						}
//					}
//				},
//				businessPeriod : {
//					required : true
//				},
				mobile : {
					required : true
				},
				mail : {
					required : false,
					customRemote : {
						url : '/anon/checkEmailOrMobile?dateTag=' + new Date().getTime(),
						data : {
							email : function() {
								return $('input[name=mail]').val();
							},
							checkType : 'investor'
						},
						customError : function(element, res) {
							return res.message;
						}
					}
				},
				//imgCode : {
				//	required : true,
				//	customRemote : {
				//		url : '/anon/checkImgCode?dateTag=' + new Date().getTime(),
				//		customError : function(element, res) {
				//			return res.message;
				//		}
				//	}
				//},
				license : {
					required : true
				},
				checkOK:{
					required:true
				}
			},
			messages : {
				accountName : {
					required : '请输入账户名'
				},
				checkOK:{
					required:'请勾选同意'
				},
				userName : {
					required : '请输入用户名',
					rangelength :'请输入6-20位字符',
					NumandLetter_ : '只能为数字、字母、下划线'
				},
				realName : {
					required : '请输入真实姓名'
				},
				logPassword : {
					required : '请输入密码',
					rangelength :'密码位数6-20'
				},
				confirmLogPassword : {
					required : '请输入确认密码',
					equalTo: '两次输入的密码不一致'
				},
//				certNo : {
//					required : '请输入证件号码',
//					checkID : "请输入正确的身份证号"
//				},
//				businessPeriod : {
//					required : '请输入证件到期时间'
//				},
				mobile : {
					required : '请输入手机号'
				},
				mail : {
					required : '请输入常用电子邮箱'
				},
				imgCode : {
					required : '请输入验证码'
				},
				license : {
					required : '请先阅读平台协议'
				}

			},
			errorPlacement: function(error, element) { //指定错误信息位置
				if (element.is(':radio') || element.is(':checkbox')) { //如果是radio或checkbox
					var eid = element.attr('name'); //获取元素的name属性
					error.appendTo(element.parent()); //将错误信息添加当前元素的父结点后面
				} else {
					error.insertAfter(element);
				}
			},
			onkeyup : false

		});
	}

	$('#isForever').click(function() {
		var input = $('.fn-isdate');
		if ($(this).attr('checked')) {
			input.val('').attr('disabled', true).rules('remove', 'required');
		} else {
			input.removeAttr('disabled').rules('add', 'required');
		}
	});

	if ($('#isForever').attr('checked')) {
		var input = $('.fn-isdate');
		input.val('').attr('disabled', true).rules('remove', 'required');
	}
	$('.fm-license').find('*').css('position','static');
	
	$("#accountTypeGR").click(function() {
		window.location.href = window.location.href;
	});
	$("#accountTypeJG").click(function() {
		alert("暂未开放，敬请期待！");
		return;
		$("[name='type']").val("JG");
		$("#realNameText").text("单位名称:");
		$("#accountTypeGR").css("background","#d6d6d6");
		$("#accountTypeGR").css("color","#787878");
		$(this).css("background","#a60000");
		$(this).css("color","#fff");
	});
	
	$("[name='realName']").change(function(){
		$("[name='enterpriseName']").val($("[name='realName']").val());
	});
	
	/**
	 * 注册
	 */
	$("#confirm").click(function() {
		
		investorsOpen_form.submit();
		return false;
	});
	
	/**
	 * 发邮件
	 */
	$("#confirmEmail").click(function() {
		investorsOpen_form.submit();
		return false;
	});


	// -------------------------------------发送手机验证码-----------------------------------------------
	var countdown = null;
	$('#getCode').click(function() {
		var business = $("#smsBizType").val();
		var mobile = $("#validPhone").val();
		countdown = Y.getCmp('getCode');
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
					alert(res.message);
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