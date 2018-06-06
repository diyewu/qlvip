var	contextPath;
$(function() {
    contextPath = $("#contextPath").val();
    $('.multi_switch').multiSwitch();
})

function flagChange(type, obj){
    $("body").showLoadingView();
    $("body").hiddenLoadingView();
    /*
    $.ajax({
        type: "get",
        dateType: "json",
        url: contextPath + "/weixin/mem/setting/" + getCookie('openid') + "/" + type + "/" + $(obj).find("input:checkbox").val(),
        data: null,
        success: function(result) {
            $("body").hiddenLoadingView();
            if (result.resCode == '000000') {
            } else {
                $("body").alertDialog({
                    title: "提示",
                    text: result.msg
                });
            }
        }
    });
    */
}

function loginOut(obj){
    $("body").confirmDialog({
        title: "提示",
        text: "是否退出登录？",
        okFtn: function () {
            if (ajaxButtonRequest(obj)){
                return;
            }
            $.ajax({
                type: "get",
                dateType: "json",
                url: contextPath + "/weixin/mem/login/out/" + getCookie('openid'),
                data: null,
                success: function(result) {
                    if (result.resCode == '000000') {
                        delCookie('login_mobile');
                        delCookie('login_psw');
                        window.location.href = contextPath + "/weixin/index//homepage/init?openid=" + getCookie('openid');
                    } else {
                        $("body").alertDialog({
                            title: "提示",
                            text: result.msg
                        });
                    }
                }
            });
        }
    })
}