define(function(require) {
	var Site = require('../comp/init.js');
	require('../Y-all/Y-script/Y-msg.js');
	require('../content/fileUpload.js');
	require('../plugins/jquery.box.js');
	require('../plugins/jquery.combobox.js');
	require('../plugins/jquery.window.js');
	
	$('.fn-time').click(function() {
		WdatePicker({
			startDate : '%y-%M-01',
			dateFmt : 'yyyy-MM-dd'
		});
	});
	
	$(".udateState").click(function() {
		var _this = this;
		Y.confirm('请选择','确定执行该操作？',function(opn){
			if(opn=="yes"){
				$.ajax({
					url : '/userManage/userManage/updateState',
					type : 'post',
					dataType : 'json',
					data : {
						userBaseId : $(_this).next().val(),
						state : $(_this).attr("state")
					},
					success : function(res) {
						alert(res.message);
						location.reload();
					}
				})
			}
		})
	
	})
	$(".resetPayPassword").click(function() {
		var _this = this;
		Y.confirm('请选择','确定执行该操作？',function(opn){
			if(opn=="yes"){
				$.ajax({
					url : '/backstage/userManage/resetPayPassword',
					type : 'post',
					dataType : 'json',
					data : {
						userBaseId :  $(_this).next().val(),
					},
					success : function(res) {
						alert(res.message);
						location.reload();
					}
				})
			}
		})
	})
	
	$('.queryBalance').click(function() {
		var url = '/backstage/userManage/userBaseInfoManage/queryBalance';
		var data = {'userBaseId':$(this).attr('data')};
		var res = $_GLOBAL.ajax(url, data);
		if(res.code==1){
			alert("余额：￥"+res.balance+"  可用余额：￥"+res.availableBalance+"  冻结金额：￥"+res.freezeAmount);
		}else{
			alert("查询失败！");
		}
	});


	/** 通过弹出层 */
	$(".goldExp").click(function() {

		$("input[name='userId']").val($(this).attr("userId"));
		$('body').window({
			content : '#pass',
			simple : true,
			closeEle : '.u-btn-gray'
		});
	});

	$("#goldExpId").change(function() {
		var amount = $(this).find("option:selected").attr("amount");
		$("input[name='amount']").val(amount);
	});

	$(".fn-ok").click(function() {
		var maxAmount = $("#goldExpId").find("option:selected").attr("amount");
		var realAmount = $("input[name='amount']").val();
		if(maxAmount < realAmount){
			//发放的体验金不得高于当前选择活动设置的体验金
			return;
		}
		$.ajax({
			url : '/backstage/userManage/adduserexp',
			type : 'post',
			dataType : 'json',
			data : {
				userId : $("input[name='userId']").val(),
				amount : realAmount,
				goldExpId : $("#goldExpId").val(),
				status : '1'
			},
			success : function(res) {
				if(res.success) {
					alert(res.msg);
				}
			}
		});
	});

});