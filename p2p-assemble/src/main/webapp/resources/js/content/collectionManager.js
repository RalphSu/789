define(function(require) {
	var Site = require('../comp/init.js');
	require('../Y-all/Y-script/Y-window.js');

    //----tab 切换------------------------------
	    $("#allLi").click(function(){
	        $("#allDiv").show();
	        $("#waitDiv").hide();
	        $("#doneDiv").hide();
	        $('#waitLi').removeClass('light');
	        $('#doneLi').removeClass('light');
	        $(this).addClass('light');
		});

		$("#waitLi").click(function(){
			$("#allDiv").hide();
			$("#waitDiv").show();
			$("#doneDiv").hide();
			$('#allLi').removeClass('light');
			$('#doneLi').removeClass('light');
			$(this).addClass('light');
		});
		
		$("#doneLi").click(function(){
			$("#allDiv").hide();
			$("#waitDiv").hide();
			$("#doneDiv").show();
			$('#allLi').removeClass('light');
			$('#waitLi').removeClass('light');
			$(this).addClass('light');
		});
		
		
	
});
