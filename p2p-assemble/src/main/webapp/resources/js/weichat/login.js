$(function(){
	$("#pageTitle").text("用户登录");
	//登录按钮
	$("#login").on("click",function(){
		var username = $("#username").val();
		var password = $("#password").val();
		if(undefined == username || "" == username){
			alert("请输入用户名！");
			return;
		}
		if(undefined == password || "" == password){
			alert("请输入密码！");
			return;
		}
		$("#login_Form").submit();
	});
});