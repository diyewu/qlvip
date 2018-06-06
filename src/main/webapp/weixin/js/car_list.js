var	contextPath;
$(function(){
	loadCar();
    contextPath = $("#contextPath").val();
    setCookie("homepageRefreshFlag", 1);//设置首页刷新flag,1则返回首页要刷新页面
});


function loadCar(){
	$("body").showLoadingView();
    $.ajax({
        type: "post",
        dateType: "json",
        url: "../smartCar/loadCarInfo/",
        data: {
        },
        success: function(result) {
            $("body").hiddenLoadingView();
            if (result.success == true) {
                if(result.data){
                	 var html = " <div class=\"car_list_div\">"+
				      "<div class=\"car_list_num\">"+
				        "<div class=\"car_list_num_img\">"+
				          "<img src=\"images/carno.png\" >"+
				        "</div>"+
				        "<div class=\"car_list_num_text\"><span>#carNum</span></div>"+
				      "</div>"+
				      "<div class=\"car_list_operate\">"+
				        "<div class=\"car_list_operate_button\" onclick=\"jumpAddCar('#carNum','#carId');\" style=\"border-right: 1px solid RGB(227, 227, 227);\">"+
				          "<div class=\"car_list_operate_img\">"+
				            "<img src=\"images/icon_vehiclemanagemengt_edit_n@3x.png\" >"+
				          "</div>"+
				          "<div class=\"car_list_operate_text\"><span>编辑</span></div>"+
				        "</div>"+
				        "<div class=\"car_list_operate_button\" onclick=\"deleteCarNum(this,'#carId');\">"+
				          "<div class=\"car_list_operate_img\">"+
				            "<img src=\"images/icon_vehiclemanagemengt_delete_n@3x.png\" ></div>"+
				          "<div class=\"car_list_operate_text\"><span>删除</span></div></div></div></div>";
                	 $.each(result.data, function (index, obj) {
                		 $("#car_content").append(html.replaceAll("#carNum",obj.car_number).replaceAll("#carId",obj.id));
			         });
                	 if(result.data.length == 3){
                		 $(".car_list_button").hide();
                	 }
                }else{//当前没有数据，直接跳转到添加页面
                	window.location.href = 'addCar.html';
                }
            } else {
                $("body").alertDialog({
                    title: "提示",
                    text: result.msg
                });
            }
        }
    });
}

function deleteCarNum(obj, carId){
    $("body").confirmDialog({
        title: "提示",
        text: "是否删除该车牌？",
        okFtn: function(){
            $("body").showLoadingView();
            $.ajax({
                type: "post",
                dateType: "json",
                url: "../smartCar/carDelete/",
                data: {
                	carId : carId
                },
                success: function(result) {
                    $("body").hiddenLoadingView();
                    if (result.success == true) {
                        location.reload();
                    } else {
                        $("body").alertDialog({
                            title: "提示",
                            text: result.msg
                        });
                    }
                }
            });
        }
    });
}


function jumpAddCar(carNum,carId){
	window.location.href = 'addCar.html?carNum='+carNum+"&carId="+carId;
}