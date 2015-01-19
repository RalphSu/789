define(function(require) {
	var Site = require('../comp/init.js');
	require('../Y-all/Y-script/Y-msg.js');
	require('../plugins/jquery.window.js');
	require('../Y-all/Y-script/Y-msg.js');
	
	$('#money-add').click(function (){
  	  show();
  	});
	var wndEle;
	function show(){
		wndEle = Y.create('Window',{
  	      content:'#money',
  	      simple:true,
  	      closeEle:'#money-close'
  	   });
		wndEle.show();
  	}
	$("#money-submit").click(function() {
		var _this = this;
		var money = $('[name=m]').val();
		 var reg = new RegExp("^[1-9][0-9]*$");
		 if(reg.test(money)){
			Y.confirm('请选择','确定？',function(opn){
				if(opn=="yes"){
					wndEle.close();
				/*	$.ajax({
						url : '',
						type : 'post',
						dataType : 'json',
						data : {
							money:money
						},
						success : function(res) {
							$('#money').close();
							alert(11);
							
						}
					})*/
				}
			})
		 }else{
			 alert("请输入正确的数字");
		 }
	})
	$("#addbankLink").click(function(){
		window.location.href="/bank/signBankCard";
	});
	
	var this_timer0,this_timer1,this_timer2,this_timer3;
	function setTimers(){
		var count0 = 0;
		var str = "查询中";
		this_timer0 = setInterval(function(){
			$("#avBalance").html(str + Array(count0++%3 + 2).join('.'));
		},500);
		var count1 = 0;
		this_timer1 = setInterval(function(){
			$("#balanceTd").html(str + Array(count1++%3 + 2).join('.'));
		},500);
		var count2 = 0;
		this_timer2 = setInterval(function(){
			$("#avBalanceTd").html(str + Array(count2++%3 + 2).join('.'));
		},500);
		var count3 = 0;
		this_timer3 = setInterval(function(){
			$("#frzAmountTd").html(str + Array(count3++%3 + 2).join('.'));
		},500);
	}
	//setTimers();
	
	function clearTimers(){
		clearInterval(this_timer0);
		clearInterval(this_timer1);
		clearInterval(this_timer2);
		clearInterval(this_timer3);
	}
	
	function queryAccountInfo(obj){
		var url = '/userManage/queryAccountInfo';
		var data = {};
		var result = $_GLOBAL.ajax(url, data);
		if(result.code==1){
			var banlance = result.balance;
			var freezeAmount = result.freezeAmount;
			var availableBalance = result.availableBalance;
			clearTimers();
			if("ref"== obj){
				alert("刷新成功");
			}
			$("#avBalance").text(availableBalance);
			$("#balanceTd").text(banlance);
			$("#avBalanceTd").text(availableBalance);
			$("#frzAmountTd").text(freezeAmount);
		}else{
			alert(result.message +",请点击刷新重新获取");
		}
		if(result.userStatus=="W")
		{
			$(".paper-on").text("激活托管机构账户");
		}
		else
		{
			var realNameAnth = result.realNameAuth;
			if("IS" == realNameAnth){
				$(".paper-on").text("已认证");
			}else if("NO" == realNameAnth){
				$(".paper-on").text("认证未通过");
			}else if("IN" == realNameAnth){
				$(".paper-on").text("认证中");
			}else{
				$(".paper-on").text("未认证");
			}
		}
		
	}
	$("#avBalance").prev().before('<a id="refreshLink" href="javascript:;"> 刷新</a>');
	//加载资金信息
	//queryAccountInfo();
	$("#refreshLink").click(function(){
		setTimers();
		queryAccountInfo("ref");
	});
	
	
	//setTimeout(setIframeSrc, 5);
	function reinitIframe(){
		var iframe = document.getElementById("iframe1");
		try{
			var bHeight = iframe.contentWindow.document.body.scrollHeight;
			var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
			var height = Math.max(bHeight, dHeight);
			iframe.height =  height;
		}catch (ex){}
	}
});