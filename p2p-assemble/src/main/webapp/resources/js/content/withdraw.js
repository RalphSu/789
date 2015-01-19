define(function(require) {
    var Site = require('../comp/init.js');

    var submitForm = $("#fm-withdraw");

    submitForm.validate({
        errorClass: 'error-tip',
        errorElement: 'b',
        errorPlacement: function(error, element) {
            var elementName = element.attr('name');
            if(elementName === 'cvv2' || elementName == 'isAgree') {
                element.parent().append(error);
            } else {
                element.after(error);
            }
        },
        rules: {
            cardNo: {
                required: true,
                number: true,
                maxlength:19,
                customRemote:{
                    url:'/bank/verifyBankCode',
                    dataType:'json',
                    data:{
                        bankCode: function() {
                            return $("[data=selectBranch]").val();
                        },
                        cardNo: function() {
                            return $("input[name='cardNo']").val();
                        }
                    },
                    customError:function(element,res){
                        return res.message;
                    },
                    customSuccess: function() {
                        var parent = $("input[name='cardNo']").parent();
                        if(parent.has("b")) {
                            parent.find("b").remove();
                        }
                    }
                }
            },
            amount: {
                required: true,
                number: true
            }
        },
        messages: {
            cardNo: {
                required: '请输入银行卡号',
                number: '银行卡号应为数字',
                maxlength:'银行卡号最多只能输入19位',
                customRemote:''
            },
            amount: {
                required: '请输入提现金额',
                number: '提现金额应为数字'
            }
        },
        onkeyup:false
    });

    var lis = $("#select_bank_list").children("li");
    if(lis.length > 1) {
        lis.first().addClass("cur");
        $("input[name='pactNo']").val(lis.first().attr("id"));
    }

    lis.click(function() {
        lis.each(function() {
            $(this).removeClass();
        });
        $("#bindCard").hide();
        if($(this).find("a").hasClass("add-b")) {
            $("#bindCard").show();
            $("input[name='pactNo']").val("");//清空
        } else {
            $("input[name='pactNo']").val($(this).attr("id"));
        }
        $(this).addClass("cur");
    });


    $('.fm-btn').click(function() {

        $("#fm-withdraw").submit();
    });


    $("#getGoldExp").click(function() {
        $.ajax({
            url : '/anon/getGoldExp',
            type : 'post',
            dataType : 'json',
            data : {
                userId : $("input[name='userId']").val()
            },
            success : function(res) {
                alert(res.msg);
            }
        });
        return false;
    });
});