$(document).ready(function(){					   
$(".item1").hover(function(){$("#tit_fc1").slideDown("normal");	}, function() {$("#tit_fc1").slideUp("fast");});				
$(".item2").hover(function(){$("#tit_fc2").slideDown("normal");	}, function() {$("#tit_fc2").slideUp("fast");});
$(".item3").hover(function(){$("#tit_fc3").slideDown("normal");	}, function() {$("#tit_fc3").slideUp("fast");});
});			   
var currentindex=1;
$("#flashBg").css("background",$("#flash1").attr("name"));
function changeflash(i) {	
currentindex=i;
for (j=1;j<=3;j++){//此处的5代表你想要添加的幻灯片的数量与下面的5相呼应
	if (j==i) 
	{$("#flash"+j).fadeIn("normal");
	$("#flash"+j).css("display","block");
	$("#f"+j).removeClass();
	$("#f"+j).addClass("dq");
	var bg = $("#flashBg");
	bg.css("background",$("#flash"+j).attr("name"));
	$("#flash"+j).find(".changeSizeImg")
				 .css("width",bg.css("width"))
				 .css("height",bg.css("height"))
				 .css("position","relative")
				 .css("left",-115);
	}
	else
	{$("#flash"+j).css("display","none");
	$("#f"+j).removeClass();
	$("#f"+j).addClass("no");}
}}
function startAm(){
	timerID = setInterval("timer_tick()",5000);//8000代表间隔时间设置
}
function stopAm(){
	clearInterval(timerID);
}
function timer_tick() {
    currentindex=currentindex>=3?1:currentindex+1;//此处的5代表幻灯片循环遍历的次数
	changeflash(currentindex);}
$(document).ready(function(){
$(".flash_bar div").mouseover(function(){stopAm();}).mouseout(function(){startAm();});
startAm();
});