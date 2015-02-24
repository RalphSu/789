$(function(){
	$("#pageTitle").css("width","156px").text($("#productName").val());
	//现在还可投金额
	var availableNow = $("#availableNow").text();
	if(availableNow != "0"){
		$("#availableNow").text(availableNow.substring(0,availableNow.indexOf(".")));
	}
	
	//完成度进度条
	$("#percentBarDone").css("width",$("#percentBarDoneHidden").val());
});