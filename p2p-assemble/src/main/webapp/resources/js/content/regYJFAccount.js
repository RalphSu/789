define(function(require) {
    var Site = require('../comp/init.js');

    //$("#businessPeriod").datepicker({dateFormat: 'yy-mm-dd'});

    $('#businessPeriod').click(function() {
        WdatePicker({
            businessPeriod : 'yy-mm-dd'
        });
    });

    var regeistForm = $("#registForm");

    regeistForm.validate({
        rules:{
            realName:{
              required:true
            },
            certNo:{
                required:true
                /*checkID : true,
                customRemote : {
                    url : '/anon/checkCertNo?dateTag=' + new Date().getTime(),
                    customError : function(element, res) {
                        return res.message;
                    }
                }*/
            },
            businessPeriod:{
                required:true
            }
        },
        messages:{
            realName:{
                required:'请输入真实姓名'
            },
            certNo:{
                required:'请输入证件号码'
                /*checkID:'请输入正确的身份证号码'*/
            },
            businessPeriod:{
              required:'请选择有效期时间'
            }
        },
        errorPlacement: function(error, element) { //指定错误信息位置
            if (element.is(':radio') || element.is(':checkbox')) { //如果是radio或checkbox
                var eid = element.attr('name'); //获取元素的name属性
                error.appendTo(element.parent()); //将错误信息添加当前元素的父结点后面
            } else {
                error.appendTo(element.parent().next());
            }
        }
    });

    $("#confirm").click(function(){
    	if(undefined == $("#userName").val() || "" == $("#userName").val()){
    		//如果没获取到用户名
    		alert("登录已失效，请重新登录!");
    		return;
    	}
        regeistForm.submit();
        return false;
    });
});