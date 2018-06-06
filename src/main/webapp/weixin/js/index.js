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
			if(result.success == true){//登陆成功
				var data = result.data;
				if(data.state){
					if("1" == data.state){
						
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
                window.location.href = "validateMobile.html";
			}
	    }  
	});  
}
