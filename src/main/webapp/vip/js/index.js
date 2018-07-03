$(function() {
	init();
})


function init(){
	var code = getParam("code");
//	alert(code);
	getUserInfo(code);
	
}
function getUserInfo(code){
//	$("body").showLoadingView();
	jQuery.ajax({  
	    url: "../wechat/getMemberInfoByCode/",  
	    type: "post",  
	    dataType: "json",  
	    async: false,  
	    data: {
	    	authCode:code
	    },  
	    success: function(result){  
//	    	$("body").hiddenLoadingView();
	    	console.log(result);
			if(result.success == true){//登陆成功
				var data = result.data;
				if("1" == data.exist_state){//存在
					$("#cardNO").html(data.cardNo);
					$("#memberyue").html(Math.round(data.deposit*100)/100);
					
				}else{//未绑定手机，跳转手机页面
					window.location.href = "validateMobile.html";
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
