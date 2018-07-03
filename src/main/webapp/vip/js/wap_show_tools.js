(function($){
    $.fn.confirmDialog = function (params) {
        this.each(function(){
            var _this = $(this);
            if (_this.children(".confirm_dialog_div").length > 0) {
                _this.children(".confirm_dialog_div").remove();
            }
            _this.append(
                "<div class='confirm_dialog_div'>" +
                    "<div class='confirm_dialog_content'>" +
                        "<div class='confirm_dialog_content_title'>" + (params.title == undefined ? "" : params.title)  + "</div>" +
                        "<div class='confirm_dialog_content_text'>" + params.text + "</div>" +
                        "<div class='confirm_dialog_content_operate'>" +
                            "<div class='confirm_dialog_content_cancel'>取消</div>" +
                            "<div class='confirm_dialog_content_ok'>确定</div>" +
                        "</div>" +
                    "</div>" +
                    "<div class='confirm_dialog_back'>" +
                    "</div>" +
                "</div>"
            );

            $(".confirm_dialog_content_operate div").bind("touchstart", function(){
                $(this).css('background', 'RGB(220, 220, 220)');
            });
            $(".confirm_dialog_content_operate div").bind("touchend", function(){
                $(this).css('background', 'RGB(232, 232, 232)');
            });

            $(".confirm_dialog_content_operate div").bind("click", function(){
                $(".confirm_dialog_div").css("display", "none");
            });
            if (params.cancelFtn != undefined){
                $(".confirm_dialog_content_cancel").bind("click", params.cancelFtn);
            }
            if (params.okFtn != undefined){
                $(".confirm_dialog_content_ok").bind("click", params.okFtn);
            }

            $(".confirm_dialog_div").css("display", "block");
        });
    }

    $.fn.alertDialog = function (params) {
        this.each(function(){
            var _this = $(this);
            if (_this.children(".alert_dialog_div").length > 0) {
                _this.children(".alert_dialog_div").remove();
            }
            _this.append(
                "<div class='alert_dialog_div'>" +
                    "<div class='alert_dialog_content'>" +
                        "<div class='alert_dialog_content_title'>" + (params.title == undefined ? "" : params.title)  + "</div>" +
                        "<div class='alert_dialog_content_text'>" + params.text + "</div>" +
                        "<div class='alert_dialog_content_operate'>" +
                            "<div class='alert_dialog_content_ok'>确定</div>" +
                        "</div>" +
                    "</div>" +
                    "<div class='alert_dialog_back'>" +
                    "</div>" +
                "</div>"
            );

            $(".alert_dialog_content_operate div").bind("touchstart", function(){
                $(this).css('background', 'RGB(220, 220, 220)');
            });
            $(".alert_dialog_content_operate div").bind("touchend", function(){
                $(this).css('background', 'RGB(232, 232, 232)');
            });

            $(".alert_dialog_content_operate div").bind("click", function(){
                $(".alert_dialog_div").css("display", "none");
            });
            if (params.okFtn != undefined){
                $(".alert_dialog_content_ok").bind("click", params.okFtn);
            }

            $(".alert_dialog_div").css("display", "block");
        });
    }

    $.fn.showLoadingView = function (params) {
        this.each(function(){
            var _this = $(this);
            if (_this.children(".loading_view_div").length <= 0) {
                _this.append(
                    "<div class='loading_view_div'>" +
                        "<div class='loading_view_content'>" +
                            "<div class='loading_view_content_img'>" +
                                "<div class='loading_view_content_img_leaf loading_view_content_img_leaf_0'></div>" +
                                "<div class='loading_view_content_img_leaf loading_view_content_img_leaf_1'></div>" +
                                "<div class='loading_view_content_img_leaf loading_view_content_img_leaf_2'></div>" +
                                "<div class='loading_view_content_img_leaf loading_view_content_img_leaf_3'></div>" +
                                "<div class='loading_view_content_img_leaf loading_view_content_img_leaf_4'></div>" +
                                "<div class='loading_view_content_img_leaf loading_view_content_img_leaf_5'></div>" +
                                "<div class='loading_view_content_img_leaf loading_view_content_img_leaf_6'></div>" +
                                "<div class='loading_view_content_img_leaf loading_view_content_img_leaf_7'></div>" +
                                "<div class='loading_view_content_img_leaf loading_view_content_img_leaf_8'></div>" +
                                "<div class='loading_view_content_img_leaf loading_view_content_img_leaf_9'></div>" +
                                "<div class='loading_view_content_img_leaf loading_view_content_img_leaf_10'></div>" +
                                "<div class='loading_view_content_img_leaf loading_view_content_img_leaf_11'></div>" +
                            "</div>" +
                        "</div>" +
                        "<div class='loading_view_back'>" +
                        "</div>" +
                    "</div>"
                );
            }

            $(".loading_view_div").css("display", "block");
        });
    }

    $.fn.hiddenLoadingView = function (params) {
        this.each(function(){
            var _this = $(this);
            if (_this.children(".loading_view_div").length > 0) {
                $(".loading_view_div").css("display", "none");
            }
        });
    }
})(jQuery);