define(function(require){
	require('../comp/init.js');
	require('../Y-all/Y-script/Y-window.js');
	require('../Y-all/Y-script/Y-countdown.js');
	require('../Y-all/Y-script/Y-tip.js');

	var investForm = $('#investForm');
	$(function() {
		$(".tit li").each(function() {
			$(this).click(function() {
				$(".tit li").removeClass();
				$(this).attr("class", "light");
				var div_id = $(this).attr("name");
				if(div_id == "bidding") {
					$("#bidding").show();
					$("#loan").hide();
				} else {
					$("#loan").show();
					$("#bidding").hide();
				}
			});
		});

		/**
		 * 显示弹出层
		 */
		$("#withdraw").click(function() {
			$.ajax({
				url : '/bank/getAllBank',
				type : 'post',
				dataType : 'json',
				success : function(res) {
					if (res.code == 1) {
						$("[data=selectBranch]").html('');
						$("[data=selectBranch]").append(
							'<option value="">请选择银行</option>');
						for ( var i = 0; i < res.data.length; i++) {
							var data = res.data[i];
							$("[data=selectBranch]").append(
								'<option value="' + data.bankCode + '">'
								+ data.bankName + '</option>')
						}
						if ($('#bankType').val())
							$("[data=selectBranch]").val($('#bankType').val())
						fix_select("[data=selectBranch]");
					}
				}
			});

			$('body').Y('Window', {
				content: $("#dialog"),
				simple: true,
				key: 'bankLayer'
			});
			function fix_select(selector) {
				var i = $(selector).parent().find('div,ul').remove().css('zIndex');
				$(selector).unwrap().removeClass('jqTransformHidden').jqTransSelect();
				$(selector).parent().css('zIndex', i);
			}
			return false;
		});

		$("#confirm").click(function() {
			if(!investForm.valid()) {
				return false;
			};
			investForm.submit();
			//提交后关闭弹出层
			if(Y.getCmp('bankLayer'))
				Y.getCmp('bankLayer').close();
			return false;
		});

		/**
		 * 关闭弹出层
		 */
		$(".lyclose").click(function() {
			if(Y.getCmp('bankLayer'))
				Y.getCmp('bankLayer').close();
			return false;
		});
	});

	Y.create('TranslateTip',{
		key:'translatetip3',
		target:'#cardNo',
		content:'<div>div</div>',
		simple:true,
		translateType:'customMade',//xxxx xxxx xxxx xxxx
		spacing:5,
		isNumber:false,
		length:0,
		align:'top'
	});

	investForm.validate({
		errorClass: 'error-tip',
		errorElement: 'b',
		errorPlacement: function(error, element) {
			var elementName = element.attr('name');
			if(elementName === 'cvv2' || elementName == 'isAgree') {
				element.parent().append(error);
			} else {
				element.after(error);
			}
		},
		rules: {
			cardNo: {
				required: true,
				number: true,
				maxlength:19,
				customRemote:{
					url:'/bank/verifyBankCode',
					dataType:'json',
					data:{
						bankCode: function() {
							return $("[data=selectBranch]").val();
						},
						cardNo: function() {
							return $("input[name='cardNo']").val();
						}
					},
					customError:function(element,res){
						return res.message;
					},
					customSuccess: function() {
						var parent = $("input[name='cardNo']").parent();
						if(parent.has("b")) {
							parent.find("b").remove();
						}
					}
				}
			},
			amount: {
				required: true,
				number: true
			}/*,
			bankCode: {
				required: $("[data=selectBranch]").val() == "" ? false : true
			}*/
		},
		messages: {
			cardNo: {
				required: '请输入银行卡号',
				number: '银行卡号应为数字',
				maxlength:'银行卡号最多只能输入19位',
				customRemote:''
			},
			amount: {
				required: '请输入提现金额',
				number: '提现金额应为数字'
			}/*,
			bankCode: {
				required: '请选择银行'
			}*/
		},
		onkeyup:false
	});

});