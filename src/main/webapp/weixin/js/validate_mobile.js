/**
 */
var	contextPath;
$(function() {
    contextPath = $("#contextPath").val();

    $('#mobile').bind('input propertychange', function() {
        var mobile = $.trim($("#mobile").val());
        if (mobile.length > 11){
            $("#mobile").val(mobile.substring(0, 11));
        }
    });
    $('#smsCode').bind('input propertychange', function() {
        var smsCode = $.trim($("#smsCode").val());
        if (smsCode.length > 6){
            $("#smsCode").val(smsCode.substring(0, 6));
        }
    });

    if (getCookie('webSourceType') == 7){//如果振华
        $(".xieyi a").html("《用户协议》");
    } else {
        $(".xieyi a").html("《用户协议》");
    }
})

function getCode(){
    if ($("#code_button").attr("status") == 1){
        return;
    }
    var mobile = $.trim($("#mobile").val());
    if (mobile == ""){
        $("body").alertDialog({
            title: "提示",
            text: "请输入手机号"
        });
        return;
    }
    if (!mobile.match("^[1][0-9]{10}$")) {
        $("body").alertDialog({
            title: "提示",
            text: "请输入正确格式的手机号"
        });
        return;
    }
    $("body").showLoadingView();
    $("#code_button").html("120s后重新发送");
    $("#code_button").attr("status", 1);
    $.ajax({
        type: "post",
        dateType: "json",
        url: '../wechat/sendValidateMobileCode/',
        data: {
        	mobileNumber:mobile
        },
        success: function(result) {
            $("body").hiddenLoadingView();
            if (result.success == true) {
                var timer = setInterval(function(){
                    var btnVal = $("#code_button").html().split("s");
                    var s = parseInt(btnVal[0],10);
                    if (s > 0) {
                        $("#code_button").html((s - 1) + 's后重新发送');
                    } else {
                    	$("#code_button").html("获得验证码");
                    	$("#code_button").attr("status", 0);
                        clearInterval(timer);
                    }
                },1000);
                $("body").alertDialog({
                	title: "测试验证码：",
                	text: result.data.code
                });
            } else {
                $("body").alertDialog({
                    title: "提示",
                    text: result.msg
                });
                $("#code_button").html("获得验证码");
                $("#code_button").attr("status", 0);
            }

        }
    });
}

function register(obj){
    var mobile = $.trim($("#mobile").val());
    var smsCode = $.trim($("#smsCode").val());
    var openid = getCookie('openid');
    if (mobile == ""){
        $("body").alertDialog({
            title: "提示",
            text: "请输入手机号"
        });
        return;
    }
    if (!mobile.match("^[1][0-9]{10}$")) {
        $("body").alertDialog({
            title: "提示",
            text: "请输入正确格式的手机号"
        });
        return;
    }
    if (smsCode == ""){
        $("body").alertDialog({
            title: "提示",
            text: "请输入手机验证码"
        });
        return;
    }
    /*
    if (openid == undefined || openid == '' || openid == null || openid == 'null'){
        $("body").alertDialog({
            title: "提示",
            text: "未获取openid，请先关注公众号"
        });
        return;
    }
    */
    if (ajaxButtonRequest(obj)){
        return;
    }
    $.ajax({
        type: "post",
        dateType: "json",
        url: "../wechat/checkMobileCode/",
        data: {
			mobileValidateCode : smsCode, 
			webSourceType : getCookie('webSourceType')
		},
        success: function(result) {
            ajaxButtonRespone(obj);
            if (result.success == true) {
            	window.location.href = 'index.html';
            } else {
                $("body").alertDialog({
                    title: "提示",
                    text: result.msg
                });
            }
        }
    });
}