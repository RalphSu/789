$(function(){
	$("#pageTitle").text("789金融");
	var info = $("#info").text();
	if(undefined == info || info == ""){
		$("#info").text("您的手机号码将作为登录用户名使用");
	}
	$("#submit").on("click",function(){
		if(!processRegex($("#username").val())){
			alert("手机号码有误！");
			return;
		}
		var password = $("#username").val();
		if(undefined == password || password == ""){
			alert("请输入密码!");
		}
		if($("#password").val() != $("#password1").val()){
			$("#info").text("两次输入的密码不一致");
			return;
		}
		$("#register_Form").submit();
	});
});

function processRegex(text){
	if(text == ""){
		return false;
	}
	var re = new RegExp ("^[1][3,4,5,8][0-9]{9}$", "gi");
	return re.test(text);
}