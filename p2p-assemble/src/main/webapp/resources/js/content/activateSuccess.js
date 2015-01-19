define(function(require) {
    var Site = require('../comp/init.js');

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
                $("#getGoldExp").remove();
                return false;
            }
        });
        return false;
    });
});