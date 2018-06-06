var	contextPath;
var parkInfoArray;
$(function() {
    
})


function setTitle(){
    document.title = '智慧停车';
}

function initParkInfo(){
    var parkInfoList = $("#park_info_list").val().replace(/[\r\n]/g,"").replace(/[ ]/g,"").replace(/[']/g,"\"");
    if (parkInfoList.length > 0){
        parkInfoList = parkInfoList.substring(0, parkInfoList.length - 1);
    }
    parkInfoList = "[" + parkInfoList + "]";
    parkInfoArray = JSON.parse(parkInfoList);
}

function initOwlcarousel(){
    var carNumList = $.trim($("#car_num_list").val());
    if (carNumList.length > 0){
        carNumList = carNumList.substring(0, carNumList.length - 1);
    }
    var carNumArray;
    if (carNumList == ''){
        carNumArray = ['无'];
    } else {
        carNumArray = carNumList.split(",");
    }
    $('.owl_carousel').owlCarousel({
        carNum: carNumArray,
        margin: 0,
        loop: false,
        responsive: {
            0: {
                items: 1
            }
        }
    })
}

function initEffect(){
    $(".park_info_right").each(function(){
        $(this).html($(this).html().replace("小时", "<span>小时</span>").replace("分钟", "<span>分钟</span>").replace("元", "<span>元</span>"));
    });
}

function getActList(){
    if (getCookie('webSourceType') == 2){//如果虹桥商圈
    // if (true){//如果虹桥商圈
        $(".slider ul").append("<li><a href='http://weibo.com/u/3677658801'><img src='hqsq.jpg'/*tpa=http://ipark.oss-cn-hangzhou.aliyuncs.com/images/activity/hqsq.jpg*/></a></li>");
        $(".slider").yxMobileSlider({width:750,height:400,during:5000});
    } else if (getCookie('webSourceType') == 7){//如果振华
        $(".slider ul").append("<li><a href='" + contextPath + "/html/zhenhua/induce.html'><img src='" + contextPath + "/resources/images/weChat/homepage/zhenhua_banner@2x.png'></a></li>");
        $(".slider").yxMobileSlider({width:750,height:400,during:5000});
    } else {
        $(".slider ul").append("<li><a href=''><img src='images/bg.png'></a></li>");
        $(".slider").yxMobileSlider({width:750,height:400,during:5000});
    }
}

function getUnpayCount(){
    $.ajax({
        type: "get",
        dateType: "json",
        url: "",
        data: null,
        success: function(result) {
            if (result.data > 0) {
                $(".homepage_tips").html(
                    "<img src='" + contextPath + "/resources/images/weChat/homepage/news@3x.png'>" +
                    "<div>亲，您有" + result.data + "条待缴记录哦！</div>" +
                    "<a href='javascript:jumpUnpayPage();'>查看</a>"
                );
            }
        }
    });
}

function runTimer(){
    $(".run_timer").each(function(){
        var m = $($(this).find(".park_detail_info_minute").get(0));
        var s = $($(this).find(".park_detail_info_sec").get(0));
        var md = parseInt(m.html(),10);
        var sd = parseInt(s.html(),10);
        if (sd > 0) {
            if(sd  <= 10) {
                s.html('0'+(sd - 1));
            } else {
                s.html(sd - 1);
            }
        } else if(sd <= 0) {
            if(md > 0 ) {
                if(md <= 10) {
                    m.html('0'+(md-1));
                    s.html('59');
                } else {
                    m.html(md-1);
                    s.html('59');
                }
            } else if(md <= 0) {
                m.html('00');
                s.html('00');
                $('body').showLoadingView();
                window.location.reload();
            }
        }
    });
}

function jumpPayPage(parkingLogId){
    window.location.href = contextPath + "/weixin/pay/detail?parkingLogId=" + parkingLogId + "&openid="
        + getCookie('openid') + "&webSourceType=" + getCookie('webSourceType') + "&platType=" + getCookie('platType');
}

function jumpAddCarNumPage(){
    window.location.href = contextPath + "/weixin/car/list/init?openid=" + getCookie('openid');
}

function jumpMemInfoPage(){
    window.location.href = 'userHome.html';
}

function jumpParkListPage(){
    window.location.href = 'parkList.html';
}

function jumpOrderParkListPage(){
    //$("body").alertDialog({
    //    title: "提示",
    //    text: "暂未开放，敬请期待"
    //});
    window.location.href = 'order.html';
}

function jumpUnpayPage(){
    window.location.href = contextPath + "/weixin/pay/unpay?openid=" + getCookie('openid');
}

function jumpProblemPage(){
    var carNum = $(".active .carousel_carNum").html();
    if (carNum == "无"){
        window.location.href = contextPath + "/weixin/car/list/init?openid=" + getCookie('openid');
    } else {
        for(var i = 0; i < parkInfoArray.length; i++){
            var parkInfo = parkInfoArray[i];
            if (carNum == parkInfo['carNum']){
                if (parkInfo['status'] == 0){
                    $("body").alertDialog({
                        title: "提示",
                        text: "未检测到停车记录"
                    });
                } else {
                    window.location.href = contextPath + "/weixin/problem/init?parkingLogId=" + parkInfo['parkingLogId'] + "&openid=" + getCookie('openid');
                }
                break;
            }
        }
    }
}
