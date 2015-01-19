define(function(require) {
	var Site = require('../comp/init.js');

	$(".submitbtn_form").click(function(){
		var radio1=$('input[name="radio1"]:checked').val();
		var radio2=$('input[name="radio2"]:checked').val();
		var radio3=$('input[name="radio3"]:checked').val();
		var referees=$(".referees").val();
		if(referees!=null && ""!=referees){
			if(radio1==1 && radio2==1 && radio3==1){
				changeTOLeke_form.submit();
			}else{
				alert("请认真阅读以下3条信息");
			}
		}else{
			alert("请输入经纪人编号");
		}

	});

});