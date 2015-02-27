$(function(){
	var images = $(".nav").find("img");
	var maxWidth = 800;
	var minWidth = 320;
	var maxHeight = 141;
	var windowWidth = $(window).width();
	var height = maxHeight * (windowWidth / maxWidth);
	$("#flashBg").css("height", height + "px");
	$("#flashLine").css("height", height + "px");
	$("#flash").css("height", height + "px")
			   .find("a").css("height", height + "px");
	images.css("width", windowWidth + "px");
	images.css("height", height + "px");
	var documentWidth = (windowWidth > maxWidth ? maxWidth : (windowWidth < minWidth ? windowWidth : windowWidth));
	$(".flash_bar").css("left", ((documentWidth - $(".flash_bar").width()) / 2) + "px")
				   .css("top", $("#flashBg").height() - 23 + "px");
});