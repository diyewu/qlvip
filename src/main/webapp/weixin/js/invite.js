var	contextPath;
$(function() {
    contextPath = $("#contextPath").val();
    wx.config({
        debug: false,
        appId: $("#appId").val(),
        timestamp: $("#timestamp").val(),
        nonceStr: $("#nonceStr").val(),
        signature: $("#signature").val(),
        jsApiList: ['onMenuShareTimeline', 'onMenuShareAppMessage']
        // 所有要调用的 API 都要加到这个列表中
    });
})

wx.ready(function(){
    wx.onMenuShareAppMessage({
        title: $("#activityTitle").val(), // 分享标题
        desc: $("#activityContent").val(), // 分享描述
        link: $("#activityUrl").val(), // 分享链接
        imgUrl: getWebAddress() + '/resources/images/weChat/invite/share.png', // 分享图标
        type: '', // 分享类型,music、video或link，不填默认为link
        dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
        success: function () {
            // 用户确认分享后执行的回调函数
        },
        cancel: function () {
            // 用户取消分享后执行的回调函数
        }
    });

    wx.onMenuShareTimeline({
        title: $("#activityTitle").val(), // 分享标题
        link: $("#activityUrl").val(), // 分享链接
        imgUrl: getWebAddress() + '/resources/images/weChat/invite/share.png', // 分享图标
        success: function () {
            // 用户确认分享后执行的回调函数
        },
        cancel: function () {
            // 用户取消分享后执行的回调函数
        }
    });
});

//显示提示页
function showSharePage(){
    $(".shareRemind").css("display", "block");
}
//隐藏提示页
function hiddenSharePage(){
    $(".shareRemind").css("display", "none");
}