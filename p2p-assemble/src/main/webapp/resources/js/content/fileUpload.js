/**
 * 文件上传功能集成
 */

define(function(require, exports, module) {
  
	require('../plugins/jquery.uploadify.js');
	require('../Y-all/Y-script/Y-htmluploadify.js');
	var uploadHost = $("#uploadHost").val();
  $('#businessLicensePathUpload').uploadify({
		height : 26,
		width : 100,
		buttonClass : 'u-btn u-btn-gray',
		buttonText : '选择上传图片',
		fileTypeExts : '*.png;*.jpg;*.bmp',
		multi : false,
		swf : '/resources/swf/uploadify.swf',
		uploader : uploadHost+'/upload/imagesUpload;jsessionid='+ $_GLOBAL.sessionID,
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
			handdleResult(data, "businessLicensePath_imgcontainer", "businessLicensePathImg", "businessLicensePathImgLink", "businessLicensePath");
		    Y.getCmp('lodding').close();
		},
		onUploadError : function(file, errorCode, errorMsg,
				errorString) {
			Y.getCmp('lodding').close();
			alert("图片上传失败！异常信息：" + errorString);
		},
		onCancel : function(file) {
			Y.getCmp('lodding').close();
			alert("已取消！");
		}
	});
	$('#businessLicenseCachetPathUpload').uploadify({
		height : 26,
		width : 100,
		buttonClass : 'u-btn u-btn-gray',
		buttonText : '选择上传图片',
		fileTypeExts : '*.png;*.jpg;*.bmp',
		multi : false,
		swf : '/resources/swf/uploadify.swf',
		uploader : uploadHost+'/upload/imagesUpload;jsessionid='+ $_GLOBAL.sessionID,
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
			handdleResult(data, "businessLicenseCachetPath_imgcontainer", "businessLicenseCachetPathImg", "businessLicenseCachetPathImgLink", "businessLicenseCachetPath");
			 Y.getCmp('lodding').close();
		},
		onUploadError : function(file, errorCode, errorMsg,
				errorString) {
			 Y.getCmp('lodding').close();
			alert("图片上传失败！异常信息：" + errorString);
		},
		onCancel : function(file) {
			 Y.getCmp('lodding').close();
			alert("已取消！");
		}
	});
	$('#certFrontPathUpload').uploadify({
		height : 26,
		width : 100,
		buttonClass : 'u-btn u-btn-gray',
		buttonText : '选择上传图片',
		fileTypeExts : '*.png;*.jpg;*.bmp',
		multi : false,
		swf : '/resources/swf/uploadify.swf',
		uploader : uploadHost+'/upload/imagesUpload;jsessionid='+ $_GLOBAL.sessionID,
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
			handdleResult(data, "certFrontPath_imgcontainer", "certFrontPathImg", "certFrontPathImgLink", "certFrontPath");
			Y.getCmp('lodding').close();
		},
		onUploadError : function(file, errorCode, errorMsg,
				errorString) {
			Y.getCmp('lodding').close();
			alert("图片上传失败！异常信息：" + errorString);
		},
		onCancel : function(file) {
			Y.getCmp('lodding').close();
			alert("已取消！");
		}
	});
	
	$('#certBackPathUpload').uploadify({
		height : 26,
		width : 100,
		buttonClass : 'u-btn u-btn-gray',
		buttonText : '选择上传图片',
		fileTypeExts : '*.png;*.jpg;*.bmp',
		multi : false,
		swf : '/resources/swf/uploadify.swf',
		uploader : uploadHost+'/upload/imagesUpload;jsessionid='+ $_GLOBAL.sessionID,
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
			handdleResult(data, "certBackPath_imgcontainer", "certBackPathImg", "certBackPathImgLink", "certBackPath");
			Y.getCmp('lodding').close();
		},
		onUploadError : function(file, errorCode, errorMsg,
				errorString) {
			Y.getCmp('lodding').close();
			alert("图片上传失败！异常信息：" + errorString);
		},
		onCancel : function(file) {
			Y.getCmp('lodding').close();
			alert("已取消！");
		}
	});
	$('#openingLicensePathUpload').uploadify({
		height : 26,
		width : 100,
		buttonClass : 'u-btn u-btn-gray',
		buttonText : '选择上传图片',
		fileTypeExts : '*.png;*.jpg;*.bmp',
		multi : false,
		swf : '/resources/swf/uploadify.swf',
		uploader : uploadHost+'/upload/imagesUpload;jsessionid='+ $_GLOBAL.sessionID,
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
			handdleResult(data, "openingLicensePath_imgcontainer", "openingLicensePathImg", "openingLicensePathImgLink", "openingLicensePath");
			 Y.getCmp('lodding').close();
		},
		onUploadError : function(file, errorCode, errorMsg,
				errorString) {
			 Y.getCmp('lodding').close();
			alert("图片上传失败！异常信息：" + errorString);
		},
		onCancel : function(file) {
			 Y.getCmp('lodding').close();
			alert("已取消！");
		}
	});
	
	$('#guaranteeLicenseUrl_Upload').uploadify({
		height : 26,
		width : 100,
		buttonClass : 'u-btn u-btn-gray',
		buttonText : '选择上传图片',
		fileTypeExts : '*.png;*.jpg;*.bmp',
		multi : false,
		swf : '/resources/swf/uploadify.swf',
		uploader : uploadHost+'/upload/imagesUpload;jsessionid='+ $_GLOBAL.sessionID,
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
			handdleResult(data, "guaranteeLicenseUrl_imgcontainer", "guaranteeLicenseUrl_Img", "guaranteeLicenseUrl_ImgLink", "guaranteeLicenseUrl");
			 Y.getCmp('lodding').close();
		},
		onUploadError : function(file, errorCode, errorMsg,
				errorString) {
			 Y.getCmp('lodding').close();
			alert("图片上传失败！异常信息：" + errorString);
		},
		onCancel : function(file) {
			 Y.getCmp('lodding').close();
			alert("已取消！");
		}
	});
	$('#certFrontPath_Upload').uploadify({
		height : 26,
		width : 100,
		buttonClass : 'u-btn u-btn-gray',
		buttonText : '点击上传',
		fileTypeExts : '*.png;*.jpg;*.bmp;*.gif',
		multi : false,
		swf : '/resources/swf/uploadify.swf',
		uploader : uploadHost+'/upload/imagesUpload;jsessionid='+ $_GLOBAL.sessionID,
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
			handdleResult(data, "", "certFrontPath_Img", "certFrontPath_ImgLink", "certFrontPath");
			Y.getCmp('lodding').close();
		},
		onUploadError : function(file, errorCode, errorMsg,
				errorString) {
			 Y.getCmp('lodding').close();
			alert("图片上传失败！异常信息：" + errorString);
		},
		onCancel : function(file) {
			 Y.getCmp('lodding').close();
			alert("已取消！");
		}
	});
	$('#cardFrontPath_Upload').uploadify({
		height : 26,
		width : 100,
		buttonClass : 'u-btn u-btn-gray',
		buttonText : '点击上传',
		fileTypeExts : '*.png;*.jpg;*.bmp;*.gif',
		multi : false,
		swf : '/resources/swf/uploadify.swf',
		uploader : uploadHost+'/upload/imagesUpload;jsessionid='+ $_GLOBAL.sessionID,
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
			handdleResult(data, "", "cardFrontPath_Img", "cardFrontPath_ImgLink", "cardFrontPath");
			 Y.getCmp('lodding').close();
		},
		onUploadError : function(file, errorCode, errorMsg,
				errorString) {
			 Y.getCmp('lodding').close();
			alert("图片上传失败！异常信息：" + errorString);
		},
		onCancel : function(file) {
			 Y.getCmp('lodding').close();
			alert("已取消！");
		}
	});
	
	$('#certBackPath_Upload').uploadify({
		height : 26,
		width : 100,
		buttonClass : 'u-btn u-btn-gray',
		buttonText : '点击上传',
		fileTypeExts : '*.png;*.jpg;*.bmp;*.gif',
		multi : false,
		swf : '/resources/swf/uploadify.swf',
		uploader : uploadHost+'/upload/imagesUpload;jsessionid='+ $_GLOBAL.sessionID,
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
			handdleResult(data, "", "certBackPath_Img", "certBackPath_ImgLink", "certBackPath");
			 Y.getCmp('lodding').close();
		},
		onUploadError : function(file, errorCode, errorMsg,
				errorString) {
			 Y.getCmp('lodding').close();
			alert("图片上传失败！异常信息：" + errorString);
		},
		onCancel : function(file) {
			 Y.getCmp('lodding').close();
			alert("已取消！");
		}
	});
	
	function handdleResult(data, containerId, imgId, linkId, storePathId){
		var result = "";
		if (data.indexOf("pre") > 0) {
			var startIndex = data.indexOf(">") + 1;
			var endIndex = data.length - 6;
			data = data.substring(startIndex, endIndex);
			data = eval("(" + data + ")");
			if (data.code == 0) {
				var imgUrl = data.resData;
				$("#"+imgId).attr("src", imgUrl);
				//$("#"+linkId).attr("href", imgUrl);
				$("#"+storePathId).val(imgUrl);
				
			} else {
				result = "<span style='color:red;'>"+data.resData+"</span>"
			}
		} else {
			data = eval("(" + data + ")");
			if (data.code == 0) {
				var imgUrl =data.resData;
				$("#"+imgId).attr("src", imgUrl);
				//$("#"+linkId).attr("href", imgUrl);
				$("#"+storePathId).val(imgUrl);
				
				if($('#'+linkId).data('jqzoom')){
					$('#'+linkId).data('jqzoom').largeimage.node.src=imgUrl;
				};
				
				$("#"+imgId).parents('.item').css('height',360);
			} else {
				result = "<span style='color:red;'>"+data.resData+"</span>"
			}
		}
		$("#"+containerId).append(result);
		
		$('#' + containerId).show(1500);
		setTimeout(function(){ $('#'+containerId).height(75);},1000);
	}

	$('#proImgUrl_Upload').uploadify({
		height : 26,
		width : 120,
		buttonClass : 'u-btn u-btn-gray',
		buttonText : '选择上传项目缩略图',
		fileTypeExts : '*.png;*.jpg;*.bmp',
		multi : false,
		swf : '/resources/swf/uploadify.swf',
		uploader : uploadHost+'/upload/imagesUpload;jsessionid='+ $_GLOBAL.sessionID,
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
			handdleResult(data, "proImgUrl_imgcontainer", "proImgUrl_Img", "proImgUrl_ImgLink", "proImgUrl");
			Y.getCmp('lodding').close();
			var demandid=$('input[name=demandid]').val();
			var url = $('#proImgUrl').val();
			var amount = $('[name=loanAmount]').val();
			$.ajax({
				url : '/backstage/updateProUpLoadUrl',
				type : 'post',
				dataType : 'json',
				data : {
					id : demandid,
					newUrl : url,
					loanAmount : amount
				},
				success : function(res) {
					if(res.code==1){
						alert(res.message);
						window.location.href="/backstage/updateLoanDemand?demandId="+demandid+"&info=info";
					}

				},error:function(e){
					console.log(e);
				}
			});
		},
		onUploadError : function(file, errorCode, errorMsg,
								 errorString) {
			Y.getCmp('lodding').close();
			alert("附件上传失败！异常信息：" + errorString);
		},
		onCancel : function(file) {
			Y.getCmp('lodding').close();
			alert("已取消！");
		}
	});
	
	$('#guaranteeLicenseUrl_Upload_update').uploadify({
		height : 26,
		width : 120,
		buttonClass : 'u-btn u-btn-gray',
		buttonText : '选择上传担保函图片',
		fileTypeExts : '*.png;*.jpg;*.bmp',
		multi : false,
		swf : '/resources/swf/uploadify.swf',
		uploader : uploadHost+'/upload/imagesUpload;jsessionid='+ $_GLOBAL.sessionID,
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
			handdleResult(data, "guaranteeLicenseUrl_imgcontainer", "guaranteeLicenseUrl_Img", "guaranteeLicenseUrl_ImgLink", "guaranteeLicenseUrl");
			 Y.getCmp('lodding').close();
			var demandid=$('input[name=demandid]').val();
			var url = $('#guaranteeLicenseUrl').val();
			var amount = $('[name=loanAmount]').val();
			$.ajax({
				url : '/backstage/updateFileUpLoadUrl',
				type : 'post',
				dataType : 'json',
				data : {
					id : demandid,
					newUrl : url,
					loanAmount : amount,
					audit : "IS"
				},
				success : function(res) {
					if(res.code==1){
						alert(res.message);
						window.location.href="/backstage/updateLoanDemand?demandId="+demandid+"&info=info";
					}
					
				},error:function(e){
					console.log(e);
				}
			});
		},
		onUploadError : function(file, errorCode, errorMsg,
				errorString) {
			 Y.getCmp('lodding').close();
			alert("附件上传失败！异常信息：" + errorString);
		},
		onCancel : function(file) {
			 Y.getCmp('lodding').close();
			alert("已取消！");
		}
	});
	
	$('#letterPdf_Url_Upload_update').uploadify({
		height : 26,
		width : 120,
		buttonClass : 'u-btn u-btn-gray',
		buttonText : '选择上传担保函附件',
		fileTypeExts : '*.pdf',
		multi : false,
		swf : '/resources/swf/uploadify.swf',
		uploader : uploadHost+'/upload/imagesUpload;jsessionid='+ $_GLOBAL.sessionID,
		fileSizeLimit : '7MB',
		successTimeout : 180000,
		onUploadStart:function(){
			$('body').Y('Window',{
				content:"<span><img src='/resources/images/common/loadingScroll.gif' /><br/>上传中，请稍后...</span>",
				key:'lodding',
				simple:true
			});
		},
		onUploadSuccess : function(file, data, response) {
			data = eval("(" + data + ")");
			 Y.getCmp('lodding').close();
			var demandid=$('input[name=demandid]').val();
			var fileUrl = data.resData;
			$.ajax({
				url : '/backstage/updatePdfUrl',
				type : 'post',
				dataType : 'json',
				data : {
					id : demandid,
					pdfUrl : fileUrl,
					type : 'letter'
				},
				success : function(res) {
					if(res.code==1){
						alert(res.message);
						window.location.href="/backstage/updateLoanDemand?demandId="+demandid+"&info=info";
					}
					
				},error:function(e){
					console.log(e);
				}
			});
		},
		onUploadError : function(file, errorCode, errorMsg,
				errorString) {
			 Y.getCmp('lodding').close();
			alert("附件上传失败！异常信息：" + errorString);
		},
		onCancel : function(file) {
			 Y.getCmp('lodding').close();
			alert("已取消！");
		}
	});
	
	$('#contractPdfUrl_Upload_update').uploadify({
		height : 26,
		width : 120,
		buttonClass : 'u-btn u-btn-gray',
		buttonText : '选择上传合同附件',
		fileTypeExts : '*.pdf',
		multi : false,
		swf : '/resources/swf/uploadify.swf',
		uploader : uploadHost+'/upload/imagesUpload;jsessionid='+ $_GLOBAL.sessionID,
		fileSizeLimit : '7MB',
		successTimeout : 180000,
		onUploadStart:function(){
			$('body').Y('Window',{
				content:"<span><img src='/resources/images/common/loadingScroll.gif' /><br/>上传中，请稍后...</span>",
				key:'lodding',
				simple:true
			});
		},
		onUploadSuccess : function(file, data, response) {
			data = eval("(" + data + ")");
			Y.getCmp('lodding').close();
			var demandid=$('input[name=demandid]').val();
			var fileUrl = data.resData;
			$.ajax({
				url : '/backstage/updatePdfUrl',
				type : 'post',
				dataType : 'json',
				data : {
					id : demandid,
					pdfUrl : fileUrl,
					type : 'contract'
				},
				success : function(res) {
					if(res.code==1){
						alert(res.message);
						window.location.href="/backstage/updateLoanDemand?demandId="+demandid+"&info=info";
					}
					
				},error:function(e){
					console.log(e);
				}
			});
		},
		onUploadError : function(file, errorCode, errorMsg,
				errorString) {
			 Y.getCmp('lodding').close();
			alert("附件上传失败！异常信息：" + errorString);
		},
		onCancel : function(file) {
			 Y.getCmp('lodding').close();
			alert("已取消！");
		}
	});
	
	//上传正式担保函
	$('#guaranteeLicenseUrl_Upload1').uploadify({
		height : 26,
		width : 100,
		buttonClass : 'u-btn u-btn-gray',
		buttonText : '选择上传图片',
		fileTypeExts : '*.png;*.jpg;*.bmp',
		multi : false,
		swf : '/resources/swf/uploadify.swf',
		uploader : uploadHost+'/upload/imagesUpload;jsessionid='+ $_GLOBAL.sessionID,
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
			handdleResult(data, "guaranteeLicenseUrl_imgcontainer1", "guaranteeLicenseUrl_Img1", "guaranteeLicenseUrl_ImgLink1", "guaranteeLicenseUrl1");
			 Y.getCmp('lodding').close();
		},
		onUploadError : function(file, errorCode, errorMsg,
				errorString) {
			 Y.getCmp('lodding').close();
			alert("图片上传失败！异常信息：" + errorString);
		},
		onCancel : function(file) {
			 Y.getCmp('lodding').close();
			alert("已取消！");
		}
	});
	
	
	
});