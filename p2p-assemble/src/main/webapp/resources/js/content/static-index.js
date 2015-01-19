
define(function(require, exports, module) {
  
	  require('../comp/init.js');
	  require("../Y-all/Y-script/Y-base.js");
	  require("../Y-all/Y-script/Y-marquee.js");
//	  var v = Y.create('Marquee',{
//		target: '#marquee',//jq选择字符串 或者字符串数组
//		time:20,
//		gotoType:'left',
//		speed:1
//	  });
	  $("#bannerBg").Slide({//banner
          effect:"fade",
          speed:600,
          timer:4000
      });
	//异步加载iFrame
		function setIframeSrc(obj) {
		    var s = obj;
		    var $iframe1 = $("#iframe1");
		    $iframe1.attr('src', s);
		    iframe = document.getElementById("iframe1");
		    if (iframe.attachEvent){  
		        iframe.attachEvent("onload", function(){
		        	reinitIframe();
		        	$iframe1.css("visibility","visible");
		        });  
		    } else {  
		        iframe.onload = function(){  
		        	reinitIframe();
		        	$iframe1.css("visibility","visible");
		        };  
		    }
		    reinitIframe();
		}
		
		setIframeSrc("/index/staticIndex/5/1?status=1");
		
		$("#newFoundation").click(function(){
			$('#newFoundation,#newPaid,#ytGuarantee,#eduGuarantee').parents("li").removeClass('focus');
			$('#newFoundation,#newPaid,#ytGuarantee,#eduGuarantee').removeClass('ocur');
			$(this).parents("li").addClass('focus');
			$(this).addClass('ocur');
			var s ="/index/staticIndex/10/1?status=1";
			setIframeSrc(s);
		});
		$("#newPaid").click(function(){
			$('#newFoundation,#newPaid,#ytGuarantee,#eduGuarantee').parents("li").removeClass('focus');
			$('#newFoundation,#newPaid,#ytGuarantee,#eduGuarantee').removeClass('ocur');
			$(this).parents("li").addClass('focus');
			$(this).addClass('ocur');
			var s ="/index/staticIndex/10/1?status=2";
			setIframeSrc(s);
		});
		
		$("#ytGuarantee").click(function(){
			$('#newFoundation,#newPaid,#ytGuarantee,#eduGuarantee').parents("li").removeClass('focus');
			$('#newFoundation,#newPaid,#ytGuarantee,#eduGuarantee').removeClass('ocur');
			$(this).parents("li").addClass('focus');
			$(this).addClass('ocur');
			var s ="/index/staticIndex/10/1?status=1&guarantee=YT";
			setIframeSrc(s);
		});
		
		$("#eduGuarantee").click(function(){
			$('#newFoundation,#newPaid,#ytGuarantee,#eduGuarantee').parents("li").removeClass('focus');
			$('#newFoundation,#newPaid,#ytGuarantee,#eduGuarantee').removeClass('ocur');
			$(this).parents("li").addClass('focus');
			$(this).addClass('ocur');
			var s ="/index/staticIndex/10/1?status=1&guarantee=EDU";
			setIframeSrc(s);
		});
		
		function reinitIframe(){
			var iframe = document.getElementById("iframe1");
			try{
				var bHeight = iframe.contentWindow.document.body.scrollHeight;
				var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
				var height = Math.max(bHeight, dHeight);
				iframe.height =  height;
			}catch (ex){}
		}
		
		function refreshAmount(){
			var url = '/index/refreshAmount.htm';
			var data = {};
			var result = $_GLOBAL.ajax(url, data);
			if(result.dealedAmount != $("#dealedAmount").text()){
				$("#dealedAmount").fadeOut("slow");
				$("#dealedAmount").text("");
				$("#dealedAmount").text(result.dealedAmount);
				$("#dealedAmount").fadeIn("slow");
			}
			
			if(result.repaidAmount != $("#repaidAmount").text()){
				$("#repaidAmount").fadeOut("slow");
				$("#repaidAmount").text("");
				$("#repaidAmount").text(result.repaidAmount);
				$("#repaidAmount").fadeIn("slow");
			}
			
			if(result.dealedSupAmount != $("#dealedSupAmount").text()){
				$("#dealedSupAmount").fadeOut("slow");
				$("#dealedSupAmount").text("");
				$("#dealedSupAmount").text(result.dealedSupAmount);
				$("#dealedSupAmount").fadeIn("slow");
			}
			
			if(result.repaidSupAmount != $("#repaidSupAmount").text()){
				$("#repaidSupAmount").fadeOut("slow");
				$("#repaidSupAmount").text("");
				$("#repaidSupAmount").text(result.repaidSupAmount);
				$("#repaidSupAmount").fadeIn("slow");
			}
    	}
	
		$(window).load(function (){ 
			var intvalAmount=self.setInterval(refreshAmount, 300000);
		});
});