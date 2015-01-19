/**
 * 我要融资
 */

define(function(require, exports, module) {
	var Site = require('../comp/init.js');
	require('../plugins/jquery.uploadify.js');
	require('../Y-all/Y-script/Y-htmluploadify.js');
	
	var uploadHost = $("#uploadHost").val();
	/*个人融资*/
	$('#personUrl_Upload').uploadify({
		buttonClass : 'u-btn u-btn-gray',
		buttonText : '选择上传资料',
		fileTypeExts : '*.rar;*.zip',
		multi : false,
		swf : '/resources/swf/uploadify.swf',
		uploader : '/upload/imagesUpload;jsessionid='+ $_GLOBAL.sessionID,
		fileSizeLimit : '3MB',
		successTimeout : 180000,
		onUploadStart:function(){
			$('body').Y('Window',{
				content:"<span><img src='/resources/images/common/loadingScroll.gif' /><br/>上传中，请稍后...</span>",
				key:'lodding',
				simple:true
			});
		},
		onUploadSuccess : function(file, data, response) {
			handdleResult(data, "personUrl_container",  "loanRequestUrl");
			 Y.getCmp('lodding').close();
		},
		onUploadError : function(file, errorCode, errorMsg,
				errorString) {
			 Y.getCmp('lodding').close();
			alert("资料上传失败！异常信息：" + errorString);
		},
		onCancel : function(file) {
			 Y.getCmp('lodding').close();
			alert("已取消！");
		}
	});
	/*企业融资*/
	$('#businessUrl_Upload').uploadify({
		buttonClass : 'u-btn u-btn-gray',
		buttonText : '选择上传资料',
		fileTypeExts : '*.rar;*.zip',
		multi : false,
		swf : '/resources/swf/uploadify.swf',
		uploader : '/upload/imagesUpload;jsessionid='+ $_GLOBAL.sessionID,
		fileSizeLimit : '3MB',
		successTimeout : 180000,
		onUploadStart:function(){
			$('body').Y('Window',{
				content:"<span><img src='/resources/images/common/loadingScroll.gif' /><br/>上传中，请稍后...</span>",
				key:'lodding',
				simple:true
			});
		},
		onUploadSuccess : function(file, data, response) {
			handdleResult(data, "businessUrl_container",  "loanRequestUrl");
			 Y.getCmp('lodding').close();
		},
		onUploadError : function(file, errorCode, errorMsg,
				errorString) {
			 Y.getCmp('lodding').close();
			alert("资料上传失败！异常信息：" + errorString);
		},
		onCancel : function(file) {
			 Y.getCmp('lodding').close();
			alert("已取消！");
		}
	});
	
	
	function handdleResult(data, containerId, storePathId){
		var result = "";
		if (data.indexOf("pre") > 0) {
			var startIndex = data.indexOf(">") + 1;
			var endIndex = data.length - 6;
			data = data.substring(startIndex, endIndex);
			data = eval("(" + data + ")");
			if (data.code == 0) {
				var imgUrl = data.resData;
				$("#"+storePathId).val(imgUrl);
			} else {
				result = "<span style='color:red;'>"+data.resData+"</span>"
			}
		} else {
			data = eval("(" + data + ")");
			if (data.code == 0) {
				var imgUrl =data.resData;
				
				$("#"+storePathId).val(imgUrl);
	
			} else {
				result = "<span style='color:red;'>"+data.resData+"</span>"
			}
		}
		$("#"+containerId).append(result);
		setTimeout(function(){ $('#'+containerId).height(75);},1000);
	}
	var personfnc_form = $("#personfnc_form");
	$('.fn-submit0').click(function() {
		if(isNotNull(1 ,13)){
			personfnc_form.submit();
		}else{
			alert("请完善表单信息！");
		}
		
	})
	$('.fn-submit1').click(function() {
		if(isNotNull(3 ,13)){
			companyfnc_form.submit();
		}else{
			alert("请完善表单信息！");
		}
	})
	
	function  isNotNull(num1 ,num2){
		for(var i=num1;i<=num2;i++){
			var values=$("."+i).val();
			if("" != values && null!=values ){
				//若还有错误提示
				if($('.show'+i).css('display') != 'none'){
					return false;
				}

				if(i==num2){
					return true;
					//update();
				}
			
			}else{
				return false;
				//update();
			}
		}
	}

	function validStrMaxLen(obj, str, tipName, maxLenth) {
		var zlen = 0;// 全角
		var elen = 0;// 半角
		for (var i = 0; i < str.length; i++) {
			if (str.substring(i, i + 1).match(/[^\x00-\xff]/ig) != null) {
				zlen += 1;
			} else {
				elen += 1;
			}
			// 返回当前字符串字节长度
			var len = (zlen * 2) + elen;
			if (len > maxLenth) {
				obj.html("*"+tipName + '不能超过' + maxLenth + "个字符").css("display","block").css("color","red");
			}else{
				obj.css("display","none");
			}
		}
	}

	function showNullErrorTip(obj,isError,tipName){
		if(isError){
			obj.html("*请填写"+tipName).css("display","block").css("color","red");
		}else{
			obj.css("display","none");
		}

	}

	
	$(".1").blur(function(){
		var name=$('.1').val();
		var tipName = "姓名";
		var isEmpty = (name.length==0);
		var errorTipObj = $('.show1');
		showNullErrorTip(errorTipObj, isEmpty, tipName);
		if(!isEmpty){
			validStrMaxLen(errorTipObj, name, tipName, 20);
		}
	});
	$(".2").blur(function(){
		var name=$('.2').val();
		if(name.length==18){
			$('.show2').css("display","none");
		}else{
			$('.show2').css("display","block").css("color","red");
		}
			
	});
	$(".3").blur(function(){
		var name=$('.3').val();
		var re = /^1\d{10}$/;

		if(name.length==11 && re.test(name)){
			$('.show3').css("display","none");
		}else{
			$('.show3').css("display","block").css("color","red");
		}
			
	});
	$(".4").blur(function(){
		var name=$('.4').val();
		if(name.length==0){
			$('.show4').css("display","block").css("color","red");
		}else{
			$('.show4').css("display","none");
		}
	});
	$(".5").blur(function(){
		var name=$('.5').val();
		if(name.length==0){
			$('.show5').css("display","block").css("color","red");
		}else{
			$('.show5').css("display","none");
		}
	});
	$(".6").blur(function(){
		var name=$('.6').val();
		if(name.length==0){
			$('.show6').css("display","block").css("color","red");
		}else{
			$('.show6').css("display","none");
		}
	});
	$(".7").blur(function(){
		var name=$('.7').val();
		if(name.length==0){
			$('.show7').css("display","block").css("color","red");
		}else{
			$('.show7').css("display","none");
		}
	});
	$(".8").blur(function(){
		var name=$('.8').val();
		if(name.length==0 || !(/^\d+(\.\d+)?$/.test(name))){
			$('.show8').css("display","block").css("color","red");
		}else{
			$('.show8').css("display","none");
		}
	});
	$(".9").blur(function(){
		var name=$('.9').val();
		if(name.length==0 || !(/^\d*$/.test(name)) || parseFloat(name) <= 0){
			$('.show9').css("display","block").css("color","red");
		}else{
			$('.show9').css("display","none");
		}
	});
	$(".10").blur(function(){
		var name=$('.10').val();
		if(name.length==0 || !(/^\d+(\.\d+)?$/.test(name))){
			$('.show10').css("display","block").css("color","red");
		}else{
			$('.show10').css("display","none");
		}
	});
	$(".11").blur(function(){
		var name=$('.11').val();
		if(name.length==0){
			$('.show11').css("display","block").css("color","red");
		}else{
			$('.show11').css("display","none");
		}
	});
	$(".12").blur(function(){
		var name=$('.12').val();
		if(name.length==0){
			$('.show12').css("display","block").css("color","red");
		}else{
			$('.show12').css("display","none");
		}
	});
	$(".13").blur(function(){
		var name=$('.13').val();
		if(name.length==0){
			$('.show13').css("display","block").css("color","red");
		}else{
			$('.show13').css("display","none");
		}
	});
});