define(function(require, exports, module){
	require("../Y-script/Y-base.js");
	require("../Y-script/Y-window.js");
	require("../Y-script/Y-htmlupload.js");
	
	$('#load').click(function(){
		var htmlupload = Y.create('HtmlUploadify',{
	    key:'up1',
        uploader:"http:///upload.test/uploadify.php",
        multi:true,
        auto:true,
        fileTypeExts:'*.jpg; *.jpeg',
        fileObjName:'UploadFile',
        onQueueComplete:function(a,b){
        },
		renderTo:'body'
	});
	htmlupload.show();
	})
});