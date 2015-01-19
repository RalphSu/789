define(function(require) {
	var Site = require('../comp/init.js');
	require('../content/chineseAmountExchange.js');
	require('../Y-all/Y-script/Y-imgplayer.js');
	require('../content/fileUpload.js');
			var stateType;
		 	liveUplodify('upfile');
		 	
		var _attach;
		
		function liveUplodify(id){
		    var input = $('#'+id);
		    input.uploadify({
				height        : 31,
				width         : 160,
				buttonText : '<span class="u-btn u-btn-gray">选择上传图片</span>',
				fileTypeExts  : '*.jpg; *.jpeg; *.bmp;  *.png',  
				multi           : true,
				queueSizeLimit: '5',
				auto: true,
				queueID: 'queueDiv',
				swf           : '/resources/swf/uploadify.swf?tag='+new Date().getTime(),
				uploader      : '/upload/imagesUpload;jsessionid='+ $_GLOBAL.sessionID,
				fileSizeLimit : '4MB',
				scriptData: { JSESSIONID: $_GLOBAL.sessionID},
				//formData		: 'oldFilePath',
				onInit: function () {   
		         },
				onUploadSuccess : function(file, data, response) {
		            var info = $.parseJSON(data);
				    if(!info) return;
				    $('.uploadimg').attr('src',info.resData);
					var _img = $('<img>');
					var imgcss = {
					      width: '50px',
					      height: '50px'
					};
					_img.attr('src',info.resData);
					_img.css(imgcss);
					_img.attr('serverPath',info.serverPath);
					
					_attach.parent().parent().append(_img);
					alert(_img.printArea());
				},
		
				onUploadError : function(file, errorCode, errorMsg, errorString) {
		            
				}, 
				onQueueComplete:function(queueData){
					var successs = queueData.uploadsSuccessful;
					var errors = queueData.uploadsErrored;
					var allnum = input.data('fileNum');
					if(successs >= allnum || errors > 0) {
						//submitUpload();
					}
				},
				onDialogClose : function(swfuploadifyQueue){
					input.data('fileNum',swfuploadifyQueue.queueLength);
				},
			    onCancel : function(file) {		    	
			    }
			});			
		}
		
		$('.attach').click(function(){
			Y.create('Window',{
				content: '.upload-scan',
				title: '上传扫描件',
				key: 'uplodWin'
			}).show();
			_attach = $(this);
		});
		
		$('.upcancel,.loanChecckSubmit').click(function(){
			Y.getCmp('uplodWin').close();
		});	
		
		
		
	 // 验证值必须大于特定值(不能等于)
    jQuery.validator.addMethod("lt", function(value, element, param) {
        return value <= $(param).val();
    });

    jQuery.validator.addMethod("gt", function(value, element, param) {
        return value >= $(param).val();
    });
	
	 Y.create('ImgPlayer',{
			eleArr:'#guaranteeLicenseUrl_Img',
			titleInfo: 'alt',
			content:'',
			pathInfo: function(){
			  return $(this).attr('src');
			}
		});
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

	if($('textarea[name=loanNote]')){
		$('textarea[name=loanNote]').xheditor({});
	}

	$('input[name=loanAmount]').change(function(){
		var num = Number($(this).val()) || -1;
		if(num != -1){
			var dimensions = ['50,000 ~ 100,000',//5w-10w
				'100,000 ~ 500,000',//10w-50w
				'500,000 ~ 1,000,000',//50w-100w
				'1,000,000 ~ 5,000,000',//100w-500w
				'5,000,000 ~ 10,000,000',//500w-1000w
				'10,000,000 ~ 20,000,000',//1000w-2000w
				'20,000,000 ~ 50,000,000',//2000w-5000w
				'50,000,000 ~ 100,000,000'];//5000w-1M
			if(num >= 50000 && num <= 100000000){
				if(num < 100000) $('#hidDimensions').val(dimensions[0]);
				else if(num < 500000) $('#hidDimensions').val(dimensions[1]);
				else if(num < 1000000) $('#hidDimensions').val(dimensions[2]);
				else if(num < 5000000) $('#hidDimensions').val(dimensions[3]);
				else if(num < 10000000) $('#hidDimensions').val(dimensions[4]);
				else if(num < 20000000) $('#hidDimensions').val(dimensions[5]);
				else if(num < 50000000) $('#hidDimensions').val(dimensions[6]);
				else  $('#hidDimensions').val(dimensions[7]);
			}else{
				$('#hidDimensions').val('');
			}
		}
	});

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
						$('input[name=realName]').val(res.message);
					}else{
						$('input[name=realName]').val("");
					}
					
				}else{
					$('input[name=realName]').val("");
				//	$('#user_Name').after("<font color='red'>"+res.message+"</font>");
				}
				
			},error:function(e){
				console.log(e);
			}
		})
	});

	$('input[name=isDirectional]').click(togglePWInput);
	var pwTempl='<input type="password"  maxlength="8" class="u-input w170 " name="loanPassword" id="loanPassword"/>',
	pwCb=$("#ps"),
	flag;

	function togglePWInput(){
		if(pwCb.prop('checked')&&!flag){
			$(pwTempl).insertBefore('#passwordHint');
			flag=true;
		}else if(!pwCb.prop('checked')&&flag){
			$('#loanPassword').remove();
			$('b[for=loanPassword]').remove();
			flag=false;
		}
	}
	togglePWInput();
	
	$('input[name=timeLimitUnit]').click(function(){
		if("fixedTime" == $(this).attr("id")) {
			var radios = $("#fixedTime").parent().parent().find("input").each(function(i) {
				$(this).attr("disabled", false);
				if(i == 2) {
					$(this).attr("checked", "checked");
				}
			});
		} else {
			var radios = $("#fixedTime").parent().parent().find("input").each(function() {
				if("fixedTime" != $(this).attr("id")) {
					$(this).attr("disabled", true);
					$(this).attr("checked", false);
				}
			});
		}

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
	
	//$("#investInterestRate").change(function(){
	//	var investInterestRate = $("#investInterestRate").val();
	//	if($.trim(investInterestRate) == ""){
	//		investInterestRate = 0;
	//	}
	//	$("#loanInterest").text(formatFloat(parseFloat(investInterestRate),2));
	//});

    $('select[name=timeLimit]').each(function(i,item){
        item.onchange=function(){
            if($("#divisionTemplateId1").val()!="")
                templateChange("divisionTemplateId1");
        }
    });
    $('input[name=loanAmount]').change(function(){
        if($("#divisionTemplateId1").val()!="")
            templateChange("divisionTemplateId1");

    });

    $_GLOBAL.templateChangeOnchange=function(){
        templateChange("divisionTemplateId1");
    }

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
		//allsel.attr('disabled',true);
		//sel.removeAttr('disabled');
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
					var investInterestRate = $("#investInterestRate").val();
					if($.trim(investInterestRate) == ""){
						investInterestRate = 0;
					}
					var totalInterestRate = parseFloat(investInterestRate) + parseFloat(res.loanInterest);
					if(s=="筹资阶段："){
						$("#s1").text(res.message);
						$("#loanInterest").text(totalInterestRate)
					}else{
						$("#s2").text(res.message);
						$("#loanInterest").text(totalInterestRate)
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
				if(element.attr('name') == 'userName'||element.attr('name') == 'interestRate'){
					element.next().after(error);
				}else if(element.attr('name') == 'loanAmount'){
					element.next().next().after(error);
				}else if(element.attr('name') == 'dimensions'||element.attr('name') == 'timeLimitUnit'){
					element.parent().parent().after(error);
				}else if(element.attr('name') == 'timeLimitUnit'||element.attr('name') == 'saturationConditionMethod'){
					element.parent().after(error);
				}else{
					element.after(error);
				}
			},
			rules : {
				//userName : {
				//	required : true,
				//	customRemote : {
				//		url : '/backstage/checkBorrower',
				//		customError : function(element, res) {
				//			return res.message;
				//		}
				//	}
				//},
				loanName : {
					required : true
				},
				//dimensions : {
				//	required: true,
				//	noZh:true
				//},
				loanAmount : {
					required:true,
					number : true,
					firstNum : true
				},
				loanPassword : {
					required : true,
					rangelength:[6,8],
					chrnumComb:true
				},
				interestRate : {
					required:true,
					number : true
				},
				loanPurpose : {
					required:true
				},
				deadline : {
					required:true,
                    gt:"#investAvalibleTime"
				},
				investAvalibleTime : {
					required:true,
                    lt:"#deadline"
				},
				timeLimitUnit : {
					required:true
				},
				saturationConditionMethod : {
					required:true
				},
				guaranteeLicenseNo: {
					required:true,
					customRemote : {
						url : '/anon/checkGuaranteeLicenseNo',
						customError : function(element, res) {
							return res.message;
						}
					}
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
					number : '输入数字',
					firstNum : '第一个数字必须大于0'
				},
				loanPassword : {
					required:'请输入密码',
					rangelength:'密码长度必须为{0}至{1}位'
				},
				interestRate : {
					required:'请输入年利率',
					number : '输入数字'
				},
				loanPurpose : {
					required:'请输入融资用途'
				},
				deadline : {
					required:"请选择截止时间",
                    gt:"截止日期要大于可投资时间"
				},
				investAvalibleTime : {
					required:"请输入可投资时间",
		            lt:"可投资时间要小于截止日期"
				},
				timeLimitUnit : {
					required:'请选择期限'
				},
				saturationConditionMethod : {
					required:'请选择满条件'
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
		    			if(res.code == 1){
							if("front" == $('input[name=source]').val()) {
								//window.location.href="/";
							} else if('wite'== $('input[name=status]').val()){
			    				window.location.href="/backstage/pageQueryLoanDemand?module=WITE";
			    			}else{
			    				window.location.href="/backstage/pageQueryLoanDemand?module=DRAFT";
			    			}
		    			}
		    		}
		    	});
			}
		});
	}
	
	function setImageValue()
	{
		for(var i=0;i<$('.attach').length;i++)
		{
			var parentA=$('.attach').eq(i);
			var code=parentA.attr('code');
			var _imgs1 = $('.attach').eq(i).parent().parent().find('img');
			var _attachPaths="";
			for(var j = 0;j<_imgs1.length;j++){
				_attachPaths += ';'+_imgs1.eq(j).attr('src')+','+_imgs1.eq(j).attr('serverPath');
			}
			$('#pathHiddenId_'+(i+1)).val(_attachPaths);
		}
	}
	
	$('.fn-submit1').click(function(){
		setImageValue();
		$('input[name=status]').val('wite');
		submitForm();
	});
    $('.fn-submit2').click(function(){
    	$('input[name=status]').val('draft');
    	submitForm();
    	
	});
    //$('[name=interestRate]').val(($('[name=interestRate]').val()|0||''));
    function submitForm(){
    	$('[name=interestRate]').val(($('[name=interestRate]').val()));
    	if($("#leastInvestAmountCkbox").attr('checked')){
    		var leastInvestAmount = $("#leastInvestAmountTxt").val();
    		if($.trim(leastInvestAmount)=="" || isNaN($.trim(leastInvestAmount))){
    			alert("最低投资金额输入错误！");
    			return false;
    		}
    	}
    	
    	 var saturationConditionMethod =  $('input[name="saturationConditionMethod"]:checked').val();
         if(saturationConditionMethod == "date"){
             var saturationCondition = $('input[name="saturationCondition"].time').val();
             if(saturationCondition > $("#deadline").val()){
                 alert("满标条件的固定时间应该投资截止时间之前!");
                 return false;
             }
         }

    	addForm.submit();
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
    $("#leastInvestAmountCkbox").click(function(){
    	$("#leastInvestAmountTxt").val("");
    	$("#leastInvestAmounttChinese").text("");
    	if($(this).attr('checked')){
    		$("#leastInvestAmountTxt").removeAttr("disabled");
    		$("#leastInvestAmountSel").attr("disabled",true);
    	} else {
    		$("#leastInvestAmountTxt").attr("disabled",true);
    		$("#leastInvestAmountSel").removeAttr("disabled");
    	}
    });
    $("#leastInvestAmountTxt").change(
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
    $(".fn-submit10").click(
		 	function(){
		 		if(window.confirm("确认通知投资人？")){
		 			$.ajax({
						url : '/backstage/notifyInvestorPdfOK',
						data : {'demandId' : $(this).attr('data')},
						type : 'post',
						dataType : 'json',
						success : function(res){
							alert(res.message);
							document.location.href = document.location.href;
						}
					});
		 		}
			}
	 );
    
    
    $("textarea").keyup(
        	function () {

        		var obj= $(this);
        		var checklen= $(this).attr('checklen');
        		if(checklen==null){
        			return ;
        		}

        		var maxlen= $(this).attr('maxlen');  //多少个汉字
        		maxlen = maxlen * 2;

        		var v = obj.val(), charlen = 0, maxlen = !maxlen ? 2000 : maxlen, curlen = maxlen, len = v.length;
    	    	for(var i = 0; i < v.length; i++) {
					if(v.charCodeAt(i) < 0 || v.charCodeAt(i) > 255) {
    	    			curlen -= 1;
    	    		}
    	    	}
    	    	if(curlen >= len) {
    		    	$("#"+checklen).html("<label class='u-tip'>还可输入"+Math.floor((curlen-len)/2)+"个字</label>").css('color', '');
    		    	//$("#subBtn").removeAttr("disabled");
    	    	} else {
    		    	$("#"+checklen).html(" 多余的字符将被截取").css('color', '#FF0000');
    		    	$(this).val(""+v.substr(0,Math.floor((maxlen)/2))) ;

    	    	}
        	}
        );

});