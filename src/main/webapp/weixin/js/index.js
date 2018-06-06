$(function() {
//	alert(getParam("code"));
	init();
	//homepages
	contextPath = $("#contextPath").val();
    //首页刷新flag,1则返回首页要刷新页面
    var homepageRefreshFlag = getCookie("homepageRefreshFlag");
    if (homepageRefreshFlag == 1){
        setCookie("homepageRefreshFlag", 0);
        location.reload();
    }
    setTitle();//设置特殊的title
    getActList();
    getUnpayCount();
    initParkInfo();
    initOwlcarousel();
    initEffect();
    if ($('.run_timer').length > 0){
        window.setInterval("runTimer();", 1000);
    }
})


function init(){
	var code = getParam("code");
//	alert(code);
	getCarParkInfo(code);
	
}
function getCarParkInfo(code){
	$("body").showLoadingView();
	
	jQuery.ajax({  
	    url: "../wechat/getCarParkInfoByCode/",  
	    type: "post",  
	    dataType: "json",  
	    async: false,  
	    data: {
	    	authCode:code
	    },  
	    success: function(result){  
	    	$("body").hiddenLoadingView();
			if(result.success == true){//登陆成功
				var data = result.data;
//				alert(data.state);
				if(data.state){
					var html1 = '<div class="item">'+
					'<div class="park_detail">'+
					'<img class="park_detail_no_img" src="images/parking3.png">'+
					'<div class="park_detail_no_tips">#parking</div></div>';
					var addCarHtml = '<div class="park_operate_div">'+
					'<div class="park_operate_button" onclick="jumpUrl(\'addCar.html\');">添加车辆</div>'+
					'</div>';
					var html2 = '<div class="park_info">'+
					'<div class="park_info_left">时长</div>'+
					'<div class="park_info_right">#time</div>'+
					'</div>'+
					'</div>';
					if("1" == data.state){//已经绑定过手机，展示当前停车信息
						var carNumArr = [];
						if(data.carstate && data.carstate.length >0){//该会员名下存在车辆信息，将车辆信息加载页面，一个车牌号码数组，一个是车辆停车情况
						   $.each(data.carstate, function (index, obj) {
							   carNumArr.push(obj.car_number);
							   if(obj.park_name){
								   $(".owl_carousel").append(html1.replace("#parking", "您正停在"+obj.park_name)+html2.replace("#time", obj.diff_time));
							   }else{
								   $(".owl_carousel").append(html1.replace("#parking", "您未驶入停车场")+html2.replace("#time", obj.diff_time));
							   }
				           });
						}else{//查询不到车辆信息
							carNumArr.push('无');
							$(".owl_carousel").append(html1.replace("#parking", "")+addCarHtml+html2.replace("#time", "0小时0分钟"));
						}
						$("#car_num_list").val(carNumArr.toString());
//						alert(carNumArr.toString());
					}else{//未绑定手机，跳转手机页面
						window.location.href = "validateMobile.html";
					}
				}else{
					$("body").alertDialog({
						title: "提示",
						text: result.msg,
						okFtn: function(){
							window.location.href = "validateMobile.html";
						}
					});
				}
			}else {//验证失败
				$("body").alertDialog({
                    title: "提示",
                    text: result.msg,
                    okFtn: function(){
                        window.location.href = "validateMobile.html";
                    }
                });
			}
	    }  
	});  
}


function consoleLog(data){
	$.post("../wechat/console/", 
	{
		data:data
	},
	function(result){
		
	},'json');
}