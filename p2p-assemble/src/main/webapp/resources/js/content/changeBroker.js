define(function(require) {
	var Site = require('../comp/init.js');
	
	$("#formSubmit").click(function(){
		var userName = $('[name=userName]').val();
		var borkerNo = $('[name=brokerNo]').val();
		var token    = $('[name=token]').val();
		if(!userName.length>0){
			alert("请输入投资人用户名");
			return;
		}
		if(!borkerNo.length>0){
			alert("请输入要更改到经纪人的编号");
			return;
		}
		$.ajax({
			url : '/backstage/userManage/changeBrokerSubmit',
			type : 'post',
			dataType : 'json',
			data : {
				userName : userName,
				borkerNo : borkerNo,
				token    : token
			},
			success : function(res){
				alert(res.message);
				document.location.href = document.location.href;
			}
		});
	})
	
	$("#changeMarketing").click(function(){
		var brokerUserName = $('[name=brokerUserName]').val();
		var markettingUserName = $('[name=markettingUserName]').val();
		var token    = $('[name=token]').val();
		if(!brokerUserName.length>0){
			alert("请输入经纪人用户名");
			return;
		}
		if(!markettingUserName.length>0){
			alert("请输入要更改到营销机构的用户名");
			return;
		}
		$.ajax({
			url : '/backstage/userManage/changeMarkettingSubmit',
			type : 'post',
			dataType : 'json',
			data : {
				brokerUserName : brokerUserName,
				markettingUserName : markettingUserName,
				token    : token
			},
			success : function(res){
				alert(res.message);
				document.location.href = document.location.href;
			}
		});
	})
});