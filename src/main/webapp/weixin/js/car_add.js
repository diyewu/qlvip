var	contextPath;
$(function(){
    contextPath = $("#contextPath").val();
    initNewFlag();//初始化是否是新能源车牌
    $("#car_num_input_hidden").val(getParam('carNum'));
    $("#car_num_input_hidden").carNumBoard();
});
//初始化是否是新能源车牌
function initNewFlag(){
    var updateCarNum = $("#update_car_num").val();
    if (updateCarNum.length <= 7){
        $("#new_flag").val(1);
        clickNewFlag();
    } else {
        $("#new_flag").val(0);
        clickNewFlag();
    }
}

function clickNewFlag(){
    var newFlag = $("#new_flag").val();
    if (newFlag == 0){
        $("#new_flag").val(1);
        // $(".new_flag img").attr("src", contextPath + "/resources/images/weChat/car_add/icon_addfunds_h@3x.png");
        $(".new_flag img").attr("src", "images/check.png");
        $(".car_add_input_left,.car_add_input_right").css("width", "12%");
        $(".car_add_input_mid").css("width", "4%");
        $(".car_num_input_8").parent(".car_add_input_right").css("display", "block");

        resetCarNumBoard();
        return;
    }
    if (newFlag == 1){
        $("#new_flag").val(0);
        // $(".new_flag img").attr("src", contextPath + "/weixin/images/icon_addfunds_n@3x.png");
        $(".new_flag img").attr("src", "images/icon_addfunds_n@3x.png");
        $(".car_num_input_8").parent(".car_add_input_right").css("display", "none");
        $(".car_add_input_left,.car_add_input_right").css("width", "13.5%");
        $(".car_add_input_mid").css("width", "5.5%");

        var carNumInputHidden = $("#car_num_input_hidden").val();
        if (carNumInputHidden.length == 8){
            $("#car_num_input_hidden").val(carNumInputHidden.substring(0, 7));
        }
        resetCarNumBoard();
        return;
    }
}

function resetCarNumBoard(){
    if ($(".board_div").size() <= 0){
        return;
    }
    $(".car_num_input").html("");
    var _this = $("#car_num_input_hidden");
    if (_this.val() != ''){
        var carLength = $("#new_flag").val() == 0 ? 7 : 8;
        for (var i = 0; i <  _this.val().length; i++){
            $(".board_second_div").css("display", "block");
            $(".board_first_div").css("display", "none");
            $(".car_num_input").css("borderColor", "RGB(227, 227, 227)");
            $(".car_num_input_" + (i + 1)).html(_this.val()[i]);
            if (_this.val().length < carLength){
                $(".car_num_input_" + (i + 2)).css("borderColor", "#3086c3");
                $(".car_add_button").removeClass("car_add_button_ok");
            } else {
                $(".car_num_input_" + carLength).css("borderColor", "#3086c3");
                $(".car_add_button").addClass("car_add_button_ok");
            }
        }
    } else {
        $(".car_num_input_1").css("borderColor", "#3086c3");
    }
}

function updateOrAddCarNum(obj){
    if (!$(".car_add_button").hasClass("car_add_button_ok")){
        return;
    }
    var carNum = $.trim($("#car_num_input_hidden").val());
    var sourceCarNum = $.trim($("#update_car_num").val());
    var carId = getParam('carId');
    var carNumMatch  = $("#new_flag").val() == 1 ? "^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9_港_澳]{6}$" : "^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9_港_澳]{5}$";
    if (!carNum.match(carNumMatch)) {
        alert("请输入正确格式的车牌号");
        return;
    }
    if (ajaxButtonRequest(obj)){
        return;
    }
    $.ajax({
        type: "post",
        dateType: "json",
        url: "../smartCar/carRegist/",
        data: {
        	carId : carId, 
        	carNumber : carNum,
        	carType:1
        	/*, 
        	openid : getCookie('openid')
        	*/
        },
        success: function(result) {
            ajaxButtonRespone(obj);
            if (result.success == true) {
                    var text = "修改车牌成功";
                    if (sourceCarNum == '' || sourceCarNum == undefined){
                        text = "添加车牌成功";
                    }
                    $("body").alertDialog({
                        title: "提示",
                        text: text,
                        okFtn: function(){
                            window.location.href = "carManager.html";
                        }
                    });
            } else {
                $("body").alertDialog({
                    title: "提示",
                    text: result.msg,
                    okFtn: function(){
                        window.location.href = "index.html";
                    }
                });
            }
        }
    });
}

(function($){
    $.fn.carNumBoard = function (params) {

        this.each(function(){
            var _this = $(this);
            $("body").append("<div class=\"board_div\"></div>");
            $(".board_div").append("<div class=\"board_first_div\"></div>");
            $(".board_div").append("<div class=\"board_second_div\"></div>");
            $(".board_first_div").append("<div class=\"board_first_line board_line\"></div>");
            var htmlContent = new Array("沪","京","津","冀","鲁","晋","蒙","辽","吉","黑");
            for (var i = 0; i < htmlContent.length; i++){
                $(".board_first_line").append("<div class=\"board_first_block\">" + htmlContent[i] + "</div>");
            }
            $(".board_first_div").append("<div class=\"board_sec_line board_line\"></div>");
            var htmlContent = new Array("苏","浙","皖","闽","赣","豫","鄂","湘","粤","桂");
            for (var i = 0; i < htmlContent.length; i++){
                $(".board_sec_line").append("<div class=\"board_first_block\">" + htmlContent[i] + "</div>");
            }
            $(".board_first_div").append("<div class=\"board_third_line board_line\"></div>");
            var htmlContent = new Array("渝","川","贵","云","藏","陕","甘","青");
            for (var i = 0; i < htmlContent.length; i++){
                $(".board_third_line").append("<div class=\"board_first_block\">" + htmlContent[i] + "</div>");
            }
            $(".board_first_div").append("<div class=\"board_fourth_line board_line\"></div>");
            var htmlContent = new Array("琼","新","宁","港","澳","台");
            for (var i = 0; i < htmlContent.length; i++){
                $(".board_fourth_line").append("<div class=\"board_first_block\">" + htmlContent[i] + "</div>");
            }
            //第二页
            $(".board_second_div").append("<div class=\"board_fifth_line board_line\"></div>");
            var htmlContent = new Array("1","2","3","4","5","6","7","8","9","0");
            for (var i = 0; i < htmlContent.length; i++){
                $(".board_fifth_line").append("<div class=\"board_first_block\">" + htmlContent[i] + "</div>");
            }
            $(".board_second_div").append("<div class=\"board_sixth_line board_line\"></div>");
            var htmlContent = new Array("港","Q","W","E","R","T","Y","U","P","澳");
            for (var i = 0; i < htmlContent.length; i++){
                $(".board_sixth_line").append("<div class=\"board_first_block\">" + htmlContent[i] + "</div>");
            }
            $(".board_second_div").append("<div class=\"board_seventh_line board_line\"></div>");
            var htmlContent = new Array("A","S","D","F","G","H","J","K","L");
            for (var i = 0; i < htmlContent.length; i++){
                $(".board_seventh_line").append("<div class=\"board_first_block\">" + htmlContent[i] + "</div>");
            }
            $(".board_second_div").append("<div class=\"board_eighth_line board_line\"></div>");
            var htmlContent = new Array("Z","X","C","V","B","N","M");
            for (var i = 0; i < htmlContent.length; i++){
                $(".board_eighth_line").append("<div class=\"board_first_block\">" + htmlContent[i] + "</div>");
            }
            $(".board_eighth_line").append("<div class=\"board_clean\"></div>");

            $(".board_first_block").bind("touchstart", function(){
                var carLength = $("#new_flag").val() == 0 ? 7 : 8;
                if (_this.val().length == 0){
                    _this.val($(this).html());
                    $(".car_num_input").css("borderColor", "RGB(227, 227, 227)");
                    $(".car_num_input_1").html($(this).html());
                    $(".car_num_input_2").css("borderColor", "#3086c3");
                    $(".board_second_div").css("display", "block");
                    $(".board_first_div").css("display", "none");
                } else if(_this.val().length < carLength) {
                    _this.val(_this.val() + $(this).html());
                    $(".car_num_input").css("borderColor", "RGB(227, 227, 227)");
                    $(".car_num_input_" + (_this.val().length)).html($(this).html());
                    if (_this.val().length < carLength){
                        $(".car_num_input_" + (_this.val().length + 1)).css("borderColor", "#3086c3");
                    } else {
                        $(".car_num_input_" + carLength).css("borderColor", "#3086c3");
                        $(".car_add_button").addClass("car_add_button_ok");
                    }
                }
            });

            $(".board_clean").bind("touchstart", function(){
                _this.val(_this.val().substring(0, _this.val().length - 1));
                $(".car_num_input").css("borderColor", "RGB(227, 227, 227)");
                $(".car_num_input_" + (_this.val().length + 1)).html($(this).html()).css("borderColor", "#3086c3");
                if ($(".car_add_button").hasClass("car_add_button_ok")){
                    $(".car_add_button").removeClass("car_add_button_ok");
                }
                if (_this.val().length == 0){
                    $(".board_first_div").css("display", "block");
                    $(".board_second_div").css("display", "none");
                }
            });

            if (_this.val() != ''){
                var carLength = $("#new_flag").val() == 0 ? 7 : 8;
                for (var i = 0; i <  _this.val().length; i++){
                    $(".board_second_div").css("display", "block");
                    $(".board_first_div").css("display", "none");
                    $(".car_num_input").css("borderColor", "RGB(227, 227, 227)");
                    $(".car_num_input_" + (i + 1)).html(_this.val()[i]);
                    if (_this.val().length < carLength){
                        $(".car_num_input_" + (i + 2)).css("borderColor", "#3086c3");
                    } else {
                        $(".car_num_input_" + carLength).css("borderColor", "#3086c3");
                        $(".car_add_button").addClass("car_add_button_ok");
                    }
                }
            } else {
                $(".car_num_input_1").css("borderColor", "#3086c3");
            }
        });
    }
})(jQuery);