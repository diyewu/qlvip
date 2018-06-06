var	contextPath;
var pageIndex = 1;
var pageSize = 10;
$(function(){
    contextPath = $("#contextPath").val();
    getCoupon(1);
    refresher.init({
        id: "wrapper",
        pullDownAction: Refresh,
        pullUpAction: Load
    })
})

function Refresh() {
    setTimeout(function () {
        getCoupon(1);
    }, 1000);

}

function Load() {
    setTimeout(function () {
        getCoupon(2);
    }, 1000);
}

/**
 * 获取数据
 * @param type （1：下拉；2：上拉加载更多）
 */
function getCoupon(type){
    if (type == 1){
        pageIndex = 1;
    }
    if (type == 2 && pageIndex == -1){
        wrapper.refresh();
        return;
    }
    $.ajax({
        type: "get",
        dateType: "json",
        url: contextPath + "/weixin/park/record/list",
        data: {openid : getCookie('openid'), pageIndex : pageIndex, pageSize : pageSize},
        success: function(result) {
            if (result.resCode == '000000') {
                if (type == 1){
                    $(".scroller ul").html(
                        "<li class='receipt_list' onclick='jumpReceiptPage();'>" +
                        "<div>开发票</div>" +
                        "</li>");
                }
                if (result.data.totalSize == 0){
                    $(".scroller ul").append(
                        "<li class='order_none_div' style='height: " + (document.body.offsetHeight) + "px'>" +
                        "<div class='order_none_img'>" +
                        "<img src='" + contextPath + "/resources/images/weChat/park_record_list/pic_discount_unavailable@3x.png'>" +
                        "</div>" +
                        "<div class='order_none_text'>暂无停车记录</div>" +
                        "</li>");
                    wrapper.refresh();
                    return;
                }
                var totalPage = result.data.totalPage;
                if (totalPage <= pageIndex){
                    pageIndex = -1;
                } else {
                    pageIndex++;
                }
                for (var i = 0; i < result.data.data.length; i++){
                    var record = result.data.data[i];
                    var parkingType = record.parkingType;//0：临停 2：包月
                    var parkingTypeDiv = '';
                    if (parkingType == 0){
                        parkingTypeDiv = "<div class='record_text_type'>临停</div>";
                    } else if (parkingType == 2){
                        parkingTypeDiv = "<div class='record_text_type orange_back'>包月</div>";
                    }
                    var status = record.status;//1已进场，2已出场 4手动离场
                    var statusDiv = '';
                    if (status == 1){
                        statusDiv = "<div class='record_text_left blue_font'>进行中</div>";
                    } else {
                        statusDiv = "<div class='record_text_left'>已出场</div>";
                    }
                    $(".scroller ul").append(
                        "<li class='record_div' " + (parkingType == 0 ? "onclick='jumpRecordDetailPage(" + record.id + ")'" : "") + ">" +
                            "<div class='record_text1'>" +
                                "<div class='record_text_title'>" + record.parkName + "</div>" + parkingTypeDiv +
                            "</div>" +
                            "<div class='record_text2'>" +
                                "<div class='record_text_time_left'>入场时间</div>" +
                                "<div class='record_text_time_right'>" + record.arriveTimeStr + "</div>" +
                            "</div>" +
                            "<div class='record_text2'>" +
                                "<div class='record_text_time_left'>出场时间</div>" +
                                "<div class='record_text_time_right'>" + (record.leaveTimeStr == null ? '未出场' : record.leaveTimeStr) + "</div>" +
                            "</div>" +
                            "<div class='record_text3'>" +
                                statusDiv +
                                (parkingType == 2 ? "" : "<img class='record_text_arrow' src='" + contextPath + "/resources/images/weChat/park_record_list/arrow_walletlist@3x.png'>") +
                                (record.arrearsAmt == 0 ? "" : "<div class='record_text_right'>欠费：<span>" + parseFloat(record.arrearsAmt) + "</span>元</div>") +
                            "</div>" +
                        "</li>");
                }
                wrapper.refresh();
            } else {
                $("body").alertDialog({
                    title: "提示",
                    text: result.msg
                });
            }

        }
    });
}

function jumpRecordDetailPage(parkingLogId){
    window.location.href = contextPath + "/weixin/pay/detail?parkingLogId=" + parkingLogId + "&openid="
        + getCookie('openid') + "&webSourceType=" + getCookie('webSourceType') + "&platType=" + getCookie('platType');
}

function jumpReceiptPage(){
    window.location.href = contextPath + "/weixin/receipt/record/list/init";
}