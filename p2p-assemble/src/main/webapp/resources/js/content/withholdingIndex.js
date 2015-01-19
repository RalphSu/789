define(function(require){
	require("../comp/init");
	require("../Y-all/Y-script/Y-window");
	require("../Y-all/Y-script/Y-tip");
	require("../Y-all/Y-script/Y-countdown");
	
	
	$('#daikou').click(function() {
		$(this).parent("li").attr("class", "deposit-click");
		$("#wangyin").parent("li").attr("class", "deposit-default");
		$("#yinhang").show();
		$("#curType").val("daikou");
         //var _this = document.getElementById('yinhang');
         //_this.style.display = "block";
         //this.className += " select-on";
         //var wangyin = document.getElementById("wangyin");
         //wangyin.className = "tab mr20";
			//document.getElementById('curType').value="daikou";
         return false;
		
	});	
	
	
	$('#wangyin').click(function() {
		$(this).parent("li").attr("class", "deposit-click");
		$("#daikou").parent("li").attr("class", "deposit-default");
		$("#yinhang").hide();
		$("#curType").val("wangyin");
          //var _this = document.getElementById('yinhang');
          //_this.style.display = "none";
          //this.className += " select-on";
          //var daikou = document.getElementById("daikou");
          //daikou.className = "tab";
			//document.getElementById('curType').value="wangyin";
          return false;
		
	});	
	
	
	$("#btnConfirm").click(function(){
     	if($("#validateCode").val()!="") {
     		if($("#sendCode").val()!="yes") {
     			alert("请发送验证码！");
     		} else {
     			deductForm1.submit();
     		}
     		
     	} else {
     		alert("请输入验证码！");
     	}
    });
	
	
	
	$('#btnChongzhi').click(function() {

      	if(document.getElementById('curType').value=="wangyin")
		{
			window.open('/usercenter/rechargePage?rechargeAmount='+$("#rechargeAmount").val(),"_blank");
		}
		else
		{
			if($("#bankCode").val()=="")
			{
				alert("请选择充值的银行！");
				return;
			}		
			else if($("#rechargeAmount").val()=="")
			{
				alert("请输入充值金额！");
				return;
			}
			else
			{
				$("#ffmoney").val($('#rechargeAmount').val());
				var countdown = Y.getCmp("getCode1");
				$('#getCode1').click(function(){
				    sendMobile('','',countdown);	

				    function sendMobile(business, mobile,countdown) {
						business = "deposit";
						$.ajax({
							url : '/anon/sendSmsCode',
							dataType : 'json',
							data : {
								mobile : "",
								business : business
							},
							cache : false,
							success : function(res) {
								if (res.code == 1) {
									$("#sendCode").val("yes");
								} else {
									alert(res.message);
									if(countdown)
										countdown.close();
								}
							},
							error : function() {
								alert('获取验证码失败');
								if(countdown)
									countdown.close();
							}
						});
					}
				});
				var wnd = Y.create("Window",{
					content:'#wbndwindow',
					simple:true,
					closeEle:'.u-close'
				});
				wnd.show();
				$(".consider").click(function(){
					wnd.close();
					countdown.close();
					$("#sendCode").val("");
			    	return false;
			    });
				$("#sendCode").val("");
			}
		}
        return false;
		
	});	
	
	
	$(".bank-state").click(function(){
		$(".bank-state").each(function(index){
			$(this).removeClass("signing");
		});
		$(this).addClass("signing");
		$("#bankCode").val($(this).attr("bankcode"));
	}); 
	
	$(".customTip").each(function(){
		var _this = $(this);
		
		Y.create('TranslateTip',{
		    key:'translatetip2',
			target:_this,
		  	content:'<div>div</div>',
		  	simple:true,
			translateType:['fmoney',3,2,","],//转大写
			spacing:5,
			align:'bottom'
		});
	});
	
	
/*	
	
	var old = window.onload;
	window.onload = function(){
	    if(old) old();

        document.getElementById('daikou').onclick = function () {
            var _this = document.getElementById('yinhang');
            _this.style.display = "block";
            this.className += " select-on";
            var wangyin = document.getElementById("wangyin");
            wangyin.className = "tab mr20";
			document.getElementById('curType').value="daikou";
            return false;
        }

        document.getElementById("wangyin").onclick = function () {
            var _this = document.getElementById('yinhang');
            _this.style.display = "none";
            this.className += " select-on";
            var daikou = document.getElementById("daikou");
            daikou.className = "tab";
			document.getElementById('curType').value="wangyin";
            return false;
        }
        $("#btnConfirm").click(function(){
        	if($("#validateCode").val()!="")
        	{
        		if($("#sendCode").val()!="yes")
        		{
        			alert("请发送验证码！");
        		}
        		else
        		{
        			deductForm1.submit();	
        		}
        		
        	}
        	else
        	{
        		alert("请输入验证码！");
        	}
        });
		document.getElementById("btnChongzhi").onclick = function () {
			
          	if(document.getElementById('curType').value=="wangyin")
			{
				window.location.href='/userManage/rechargePage?rechargeAmount='+$("#rechargeAmount").val();
			}
			else
			{
				if(document.getElementById('bankCode').value=="")
				{
					alert("请选择充值的银行！");
					return;
				}		
				else if(document.getElementById('rechargeAmount').value=="")
				{
					alert("请输入充值金额！");
					return;
				}
				else
				{
					$("#ffmoney").val($('#rechargeAmount').val());
					var countdown = Y.getCmp("getCode1");
					$('#getCode1').click(function(){
					    sendMobile('','',countdown);	

					    function sendMobile(business, mobile,countdown) {
							business = "deposit";
							$.ajax({
								url : '/anon/sendSmsCode',
								dataType : 'json',
								data : {
									mobile : "",
									business : business
								},
								cache : false,
								success : function(res) {
									if (res.code == 1) {
										$("#sendCode").val("yes");
									} else {
										alert(res.message);
										if(countdown)
											countdown.close();
									}
								},
								error : function() {
									alert('获取验证码失败');
									if(countdown)
										countdown.close();
								}
							});
						}
					});
					var wnd = Y.create("Window",{
						content:'#wbndwindow',
						simple:true,
						closeEle:'.u-close'
					});
					wnd.show();
					$(".consider").click(function(){
						wnd.close();
						countdown.close();
						$("#sendCode").val("");
				    	return false;
				    });
					$("#sendCode").val("");
				}
			}
            return false;
        }
		
		
		

		
		$(".bank-state").click(function(){
			$(".bank-state").each(function(index){
				$(this).removeClass("signing");
			});
			$(this).addClass("signing");
			$("#bankCode").val($(this).attr("bankcode"));
		}); 
		
		$(".customTip").each(function(){
			var _this = $(this);
			
			Y.create('TranslateTip',{
			    key:'translatetip2',
				target:_this,
			  	content:'<div>div</div>',
			  	simple:true,
				translateType:['fmoney',3,2,","],//转大写
				spacing:5,
				align:'bottom'
			});
		});
	}*/
});