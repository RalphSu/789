define(function(require) {
	var Site = require('../comp/init.js');
	require('../content/fileUpload.js');
	require('../content/chineseAmountExchange.js');
	$('.time').click(function() {
		WdatePicker({
			startDate : '%y-%M-01 HH:mm:ss',
			dateFmt : 'yyyy-MM-dd HH:mm:ss'
		});
	});

	function formatFloat(src, pos)
	{
	    return Math.round(src*Math.pow(10, pos))/Math.pow(10, pos);
	}
	
	$('textarea[name=loanNote]').xheditor({});
	//$('input[name=realName]').attr("disabled", true);
	$('input[name=userName]').blur( function(){
		var _this=$(this).val();
		$.ajax({
			url : '/backstage/getRealName',
			type : 'post',
			dataType : 'json',
			data : {
				userName : _this,
				roleId : 13
			},
			success : function(res) {
				if(res.code==1){
					if(res.message!=null){
						$('p[name=realName]').text(res.message);
					}else{
						$('p[name=realName]').text("");
					}
					
				}else{
					$('p[name=realName]').text("");
				//	$('#user_Name').after("<font color='red'>"+res.message+"</font>");
				}
				
			},error:function(e){
				console.log(e);
			}
		})
	});
	
	$('input[name=timeLimitUnit]').click(function(){
		var sel = $(this).parent().parent().find('select');
		var allsel =  $(this).parent().parent().parent().find('select');
		sel.parent().parent().parent().parent().find('.jqTransformSelectOpen').hide();
		sel.parent().find('.jqTransformSelectOpen').show();
		allsel.attr('disabled',true);
		sel.removeAttr('disabled');
	});
	
	$('input[name=saturationConditionMethod]').click(function(){
		var inp = $(this).parent().find('input[type=text]');
		var allinp = $(this).parent().parent().find('input[type=text]');
		allinp.attr('disabled',true);
		inp.removeAttr('disabled');
	});

    function templateChange(obj){
        var curId = obj;
        var timeLimitUnitObj=$('input[name="timeLimitUnit"]:checked');
        var timeLimitUnit =  timeLimitUnitObj.val();
        var sel =timeLimitUnitObj.parent().parent().find('select');
        $.ajax({
            url : '/backstage/queryRuleInfo',
            type : 'post',
            dataType : 'json',
            data : {
                name : $("#"+curId).val(),
                loanAmount : $('input[name=loanAmount]').val(),
                timeLimitUnit:timeLimitUnit,
                timeLimit:sel.val()
            },
            success : function(res) {
                //p.text(res.message);
                //sel.after("<p class="u-tip mt5">"+res.message+"</p>")
                var loanInterest1 = $("#templateRate1").val();
                var loanInterest2 = $("#templateRate2").val();
                if($.trim(loanInterest1) == ""){
                    loanInterest1 = 0;
                }
                if($.trim(loanInterest2) == ""){
                    loanInterest2 = 0;
                }

                var investRate1 = $("#investRate1").val();
                var investRate2 = $("#investRate2").val();
                if($.trim(investRate1) == ""){
                    investRate1 = 0;
                }
                if($.trim(investRate2) == ""){
                    investRate2 = 0;
                }
                var totalInterestRate = 0;
                var totalInvestRate = 0;
                if(curId == "divisionTemplateId1"){
                    $("#s1").text(res.message);
                    $("#templateRate1").val(parseFloat(res.loanInterest));
                    $("#investRate1").val(parseFloat(res.investorInterest));
                    totalInterestRate = parseFloat(loanInterest2) + parseFloat(res.loanInterest);
                    totalInvestRate = parseFloat(investRate2) + parseFloat(res.investorInterest);
                }else{
                    $("#templateRate2").val(parseFloat(res.loanInterest));
                    $("#investRate2").val(parseFloat(res.investorInterest));
                    totalInterestRate = parseFloat(loanInterest1) + parseFloat(res.loanInterest);
                    totalInvestRate = parseFloat(investRate1) + parseFloat(res.investorInterest);
                    $("#s2").text(res.message);
                }


                var viewText=""
                if(res.tradeChargeRate>0)
                {
                    var totalValue=formatFloat(totalInterestRate+res.tradeChargeRate, 2);
                    viewText="融资("+formatFloat(totalInterestRate, 2)+"%)+交易手续("+res.tradeChargeAmount+"元)=实际成本("+totalValue+"%)";
                }
                else
                {
                    viewText=formatFloat(totalInterestRate, 2)+"%";
                }

                $("#loanInterest_txt").val(viewText);

                $("#loanInterest").val(formatFloat(totalInterestRate, 2));
                $("#investInterestRate").val(formatFloat(totalInvestRate, 2));
            },error:function(e){
                console.log(e);
            }
        })
    }

    templateChange("divisionTemplateId1");

	document.getElementById("divisionTemplateId1").onchange = function(){
		templateChange("divisionTemplateId1");
	}
	document.getElementById("divisionTemplateId2").onchange = function(){
		templateChange("divisionTemplateId2");
	}
	
    $('input[name=divisionTimeLimitUnit]').click(function(){
		var sel = $(this).parent().parent().find('select');
		//var p=$(this).parent().parent().find('p').last();
		var allsel =  $(this).parent().parent().parent().find('select');
		sel.parent().parent().parent().parent().find('.jqTransformSelectOpen').hide();
		sel.parent().find('.jqTransformSelectOpen').show();
		allsel.attr('disabled',true);
		sel.removeAttr('disabled');
		var s=$(this).next().text();
		sel.get(0).onchange = function(){
			//	alert(sel.val())
			$.ajax({
				url : '/backstage/queryRuleInfo',
				type : 'post',
				dataType : 'json',
				data : {
					name : sel.val()
				},
				success : function(res) {		
					//p.text(res.message);
					//sel.after("<p class="u-tip mt5">"+res.message+"</p>")
					if(s=="筹资阶段："){
						$("#s1").text(res.message);
						$("#s2").text("");
					}else{
						$("#s2").text(res.message);
						$("#s1").text("");
					}
				},error:function(e){
					console.log(e);
				}
			})
		}
	});
   
    /**jquery validate验证规则添加*/
    var chrnum = /^([a-zA-Z0-9]+)$/,//限制字符在数字或者字母范围内
    	chrnum2=/^(?![0-9]+$|[a-zA-Z]+$)/; //排除纯数字或者纯字母
    jQuery.validator.addMethod("chrnumComb", function(value, element) { 
    	return this.optional(element) || (chrnum.test(value)&&chrnum2.test(value)); 
    }, "只能输入数字和字母的组合");
    
	/** 验证发布借款需求FORM表单 */
	var addForm=$('#add_loandemand_form');
	if(addForm.length){
		addForm.validate({
			errorClass: 'error-tip',
			errorElement: 'b',
			errorPlacement: function(error, element) {
				if(element.attr('name') == 'userName'||element.attr('name') == 'loanAmount'||element.attr('name') == 'interestRate'){
					element.next().after(error);
				}else if(element.attr('name') == 'dimensions'||element.attr('name') == 'timeLimitUnit'){
					element.parent().parent().after(error);
				}else{
					element.after(error);
				}
			},
			rules : {
				userName : {
					required : true,
					customRemote : {
						url : '/backstage/checkBorrower',
						customError : function(element, res) {
							return res.message;
						}
					}
				},
				loanName : {
					required : true
				},
				dimensions : {
					required: true,
					noZh:true
				},
				loanAmount : {
					required:true,
					number : true
				},
				loanPassword : {
					required : true,
					rangelength:[6,8],
					chrnumComb:true
				},
				leastInvestAmount : {
					required:true,
					number : true
				},
				interestRate : {
					required:true,
					number : true
				},
				loanPurpose : {
					required:true
				},
				timeLimitUnit : {
					required:true
				},
				deadline : {
					required:true
				},
				investAvalibleTime : {
					required:true
				},
				guaranteeLicenseNo: {
					required:true
					
				},
				guaranteeLicenseName : {
					required:true
				}
			},
			messages: {
				userName:{
					required: "请输入借款人用户名"
				},
				loanName : {
					required: "请输入借款标题"
				},
				dimensions : {
					required: "请选择贷款规模",
					noZh:'请选择贷款规模'
				},
				loanAmount : {
					required:'请输入金额',
					number : '输入数字'
				},
				loanPassword : {
					required:'请输入密码',
					rangelength:'密码长度必须为{0}至{1}位'
				},
				leastInvestAmount : {
					required:'请输入金额',
					number : '输入数字'
				},
				interestRate : {
					required:'请输入年利率',
					number : '输入数字'
				},
				loanPurpose : {
					required:'请输入金额',
				},
				timeLimitUnit : {
					required:'请选择期限',
				},
				deadline : {
					required:"请选择截止时间"
				},
				investAvalibleTime : {
					required:"请选择可投资时间"
				},
				guaranteeLicenseNo: {
					required:"请输入担保函编号"
				},
				guaranteeLicenseName : {
					required:"请输入担保函名称"
				}
			},
			submitHandler:function(){
				addForm.ajaxSubmit({
		    		success:function(res){
		    			alert(res.message);
		    			if('wite'==$('input[name=status]').val()){
		    				window.location.href="/backstage/pageQueryLoanDemand?module=WITE";
		    			}else if('pass'==$('input[name=status]').val()){
		    				window.location.href="/backstage/pageQueryOfflineLoanDemand";
		    			}else{
		    				window.location.href="/backstage/pageQueryLoanDemand?module=DRAFT";
		    			}
		    		}
		    	});
			}
		});
	}
	
	$('.fn-submit1').click(function(){
		if('pass' != $('input[name=status]').val()){
			$('input[name=status]').val('wite');
		}
		submitForm();
	});
    $('.fn-submit2').click(function(){
    	if('pass' != $('input[name=status]').val()){
    		$('input[name=status]').val('draft');
		}
    	submitForm();
	});
    
    //$('[name=interestRate]').val(($('[name=interestRate]').val()|0||''));
    
    function submitForm(){
    	$('[name=interestRate]').val(($('[name=interestRate]').val()));
    	addForm.submit();
    	//window.location.href="/backstage/pageQueryLoanDemand?module=DRAFT";
    }
    
    $('input[name=saturationConditionMethod],input[name=timeLimitUnit],input[name=divisionTimeLimitUnit]').each(function(i,item){
    	var bechecked = $(this).attr('bechecked');
    	if(bechecked) {
    		$(this).click();
    	}
    });
    $('[name=loanAmount]').change(
    		function(){
    			var amount = $(this).val();
    			var result = convertCurrency(amount);
    			if(result.indexOf("error") >= 0 || amount == ""){
    				$("#loanAmountChinese").text("");
    				return;
    			}
    			$("#loanAmountChinese").text(result);
    		}
    );
    $('[name=leastInvestAmount]').change(
    		function(){
    			var amount = $(this).val();
    			var result = convertCurrency(amount);
    			if(result.indexOf("error") >= 0 || amount == ""){
    				$("#leastInvestAmountChinese").text("");
    				return;
    			}
    			$("#leastInvestAmountChinese").text(result);
    		}
    );
    $('#saturationConditionAmount').change(
    		function(){
    			var amount = $(this).val();
    			var result = convertCurrency(amount);
    			if(result.indexOf("error") >= 0 || amount == ""){
    				$("#saturationConditionAmountChinese").text("");
    				return;
    			}
    			$("#saturationConditionAmountChinese").text(result);
    		}
    );
});