define(function(require) {
    var Site = require('../comp/init.js');
    require('../content/chineseAmountExchange.js');
    require('../Y-all/Y-script/Y-imgplayer.js');
    require('../content/fileUpload.js');


    /** 验证FORM表单 */
    var addForm = $('#addGoldExpForm');
    //if(addForm.length){
    //    addForm.validate({
    //        errorClass: 'error-tip',
    //        errorElement: 'b',
    //        errorPlacement: function(error, element) {
    //        },
    //        rules : {
    //            name : {
    //                required : true
    //            },
    //            type : {
    //                required: true,
    //                noZh:true
    //            },
    //            amount : {
    //                required:true,
    //                number : true,
    //                firstNum : true
    //            },
    //            startTime : {
    //                required:true,
    //                lt:"#endTime"
    //            },
    //            endTime : {
    //                required:true,
    //                gt:"#startTime"
    //            }
    //        },
    //        messages: {
    //            name:{
    //                required: "请输入体验金名称"
    //            },
    //            type : {
    //                required: "请输入体验金类型"
    //            },
    //            amount : {
    //                required:'请输入金额',
    //                number : '输入数字',
    //                firstNum : '第一个数字必须大于0'
    //            },
    //            startTime : {
    //                required:"请选择截止时间",
    //                gt:"开始日期要小于截止时间"
    //            },
    //            endTime : {
    //                required:"请输入可投资时间",
    //                lt:"截止时间要大于开始日期"
    //            }
    //        }
    //    });
    //}

    $(".fn-submit1").click(function() {
        $("input[name='surplusQuantity']").val($("input[name='quantity']").val());
        addForm.submit();
        return false;
    });

    $('.time').click(function() {
        WdatePicker({
            startDate : '%y-%M-01 HH:mm:ss',
            dateFmt : 'yyyy-MM-dd HH:mm:ss'
        });
    });

    $(".fn-submit2").click(function() {
        $("#updateGoldExpForm").submit();
        return false;
    });

    $("a[name='delete']").click(function() {
        if(window.confirm('你确定要删除吗？')){
           window.location.href = "/backstage/goldExp/deleteGoldExp?id=" + $(this).attr("goldExpId");
        }
        return false;
    });

});