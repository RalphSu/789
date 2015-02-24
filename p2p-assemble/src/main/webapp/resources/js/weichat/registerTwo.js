$(function(){
	var opt = {
	        preset: 'date', //日期
	        theme: 'jqm', //皮肤样式
	        display: 'modal', //显示方式 
	        mode: 'clickpick', //日期选择模式
	        dateFormat: 'yyyy-mm-dd', // 日期格式
	        setText: '确定', //确认按钮名称
	        cancelText: '取消',//取消按钮名籍我
	        dateOrder: 'yymmdd', //面板中日期排列格式
	        dayText: '日', monthText: '月', yearText: '年', //面板中年月日文字
	        beginYear:2015,
	        endYear:2050 //结束年份
	    };
	$('input:jqmData(role="datebox")').mobiscroll(opt);
	$("#submit").on("click",function(){
		var userName = $("#userName").val();
		if(undefined == userName || "" == userName){
			alert("信息有误，请刷新页面！");
			return;
		}
		var realName = $("#realName").val();
		if(undefined == realName || "" == realName){
			alert("请输入真实姓名！");
			return;
		}
		var certNo = $("#certNo").val();
		if(undefined == certNo || "" == certNo){
			alert("请输入身份证号码！");
			return;
		}
		var businessPeriod = $("#businessPeriod").val();
		if(undefined == businessPeriod || "" == businessPeriod){
			alert("请输入身份证有效期！");
			return;
		}
		$("#register_Form").submit();
	});
});

function processRegex(text,reg){
	if(text == ""){
		return false;
	}
	var re = new RegExp (reg);
	return re.test(text);
}