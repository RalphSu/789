$(function(){
	$(window).scroll(function(){
        var scrollTop = $(this).scrollTop();
        var scrollHeight = $(document).height();
		var windowHeight = $(window).height();
        if ((scrollTop + windowHeight >= scrollHeight)) {
  			addItems();
	    };
    });
});

/**
 * 增加list的项
 */
var gettingItems = 0; //信号量
function addItems(){
	if(gettingItems == 1){
		return;
	}
	var pageSize = $("#pageSize").val();
	if(undefined == pageSize || "" == pageSize || "0" == pageSize){
		return;
	}
	var totalCount = $("#totalCount").val();
	if(undefined == totalCount || "" == totalCount || "0" == totalCount){
		return;
	}
	var total = $("#productList").find("li").length;
	if(total >= parseInt(totalCount) || (total % parseInt(pageSize)) != 0){
		return;
	}
	var pageNo = (total / parseInt(pageSize));
	gettingItems = 1;
	var option = {
			url:'/weichat/getProducts.json',
			type:'post',
			dataType:'json',
			data : {
				size : pageSize,
				page : parseInt(pageNo) + 1
			},
			success : function(data){
				if(data.result.length > 0){
					$("#pageNo").val(parseInt(pageNo) + 1);
					appendLi(data.result);
				}
			},
			complete : function(){
				gettingItems = 0;
			}
		};
	$.ajax(option);
}

function appendLi(items){
	var appendLiString = "";
	for(var i = 0; i < items.length; i++){
		var item = items[i];
		var liString = "<li><a href='/weichat/" + item.demandId + "/"+ item.tradeId +"' data-ajax='false'>" + "<div class='ui-grid-a'>"
					  +"<div class='ui-block-a' style='width:110px;margin-right:8px'>" 
					  +"<span class='highlight' style='font-size:28px'>" + item.strRaate + "</span>"
					  +"<span>" + item.timeLimit + "天</span>" + "<span>" + item.leastInvestAmount +" 元起</span>"
					  +"<span>" + item.amount +"</span></div>"
					  +"<div class='ui-block-b'>"
					  +"<span style='font-size:20px;margin-bottom:8px'>" + item.name + "</span>"
					  +"<span class='highlight'>融资进度:" + item.p + "</span>"
					  +"<span>开始时间：" + item.investAvalibleTime + "</span>"
					  +"<span>完成时间：" + item.deadline + "</span>"
					  +"</div></div></a></li>";
		if(i == 0){
			appendLiString = liString;
		}else{
			appendLiString = appendLiString + liString;
		}
	}
	if(appendLiString != ""){
		$("#productList").append(appendLiString);
		$('ul').listview('refresh');  
        $("#productList").find("li:last").slideDown(300); 
	}
}















