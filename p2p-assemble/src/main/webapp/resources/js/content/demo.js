/**
 * @fileoverview tab组件，支持click, hover事件
 * @author yangle | yorsal.coms
 * @created  2012-05-09
 * @updated  2012-05-09
 */


define(function(require, exports, module) {
  require('../comp/init.js');
  require('../Y-all/Y-script/Y-imgplayer.js');
    require('../Y-all/Y-script/Y-msg.js');
  Y.create('ImgPlayer',{
		eleArr:'#guaranteeLicenseUrl_Img',
		titleInfo: 'alt',
		content:'',
		pathInfo: function(){
		  return $(this).attr('src');
		}
	});
  
});