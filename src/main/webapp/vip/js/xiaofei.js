$(function() {
	init();
})


function init() {
	getUserXiaofeiInfo();
}

function getUserXiaofeiInfo() {
	jQuery.ajax({
		url : "../member/consume/",
		type : "post",
		dataType : "json",
		async : false,
		data : {
		},
		success : function(result) {
			//	    	$("body").hiddenLoadingView();
			console.log(result);
			if (result.success == true) { //查询成功
				var html = "<li>" +
					"<div class=\"kuang_1\">" +
					"<div class=\"yue\">" +
					"<div class=\"yue_11\">$(time)</div>" +
					"<div class=\"yue_2222\">消费：$(pay)元 </div>" +
					"</div>" +
					"</div>" +
					"</li>";
				if (result.data && result.data.length > 0) {
					for (var i = 0; i < result.data.length; i++) {
						var time = result.data[i].inputdatetime;
						var unixTimestamp = new Date(time);
						$("#consumeContent").append(html.replace("$(time)", 
								unixTimestamp.toLocaleString()).replace("$(orderId)", result.data[i].no)
								.replace("$(pay)", (result.data[i].actuallyPaid).toFixed(2)));
					}
				}
			} else { //验证失败

			}
		}
	});
}