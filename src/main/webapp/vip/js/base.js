
//日期转换
Date.prototype.toLocaleString = function() {
          return this.getFullYear() + "年" + (this.getMonth() + 1) + "月" + this.getDate() + "日 " + this.getHours() + "点" + this.getMinutes() + "分"/* + this.getSeconds() + "秒"*/;
    };

String.prototype.replaceAll = function(s1,s2){
    return this.replace(new RegExp(s1,"gm"),s2);
}
//数组方法
Array.prototype.remove=function(dx)
{
    if(isNaN(dx)||dx>this.length){return false;}
    for(var i=0,n=0;i<this.length;i++)
    {
        if(this[i]!=this[dx])
        {
            this[n++]=this[i]
        }
    }
    this.length-=1
}

Array.prototype.removeValue=function(value)
{
    if(isNaN(value)){return false;}
    for(var i=0,n=0;i<this.length;i++)
    {
        if(this[i]!=value)
        {
            this[n++]=this[i]
        }
    }
    this.length-=1
}

//从 file 域获取 本地图片 url
function getFileUrl(sourceId) {
    var url;
    if (navigator.userAgent.indexOf("MSIE")>=1) { // IE
        url = document.getElementById(sourceId).value;
    } else {
        url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0));
    }
    return url;
}

//将本地图片 显示到浏览器上
function preImg(sourceId, targetId) {
    var url = getFileUrl(sourceId);
    var imgPre = document.getElementById(targetId);
    imgPre.src = url;
}

/****************************************************cookies*********************************************************/
//写cookies
function setCookie(name, value) {
    var Days = 30;
    var exp = new Date();
    exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
    document.cookie = name + "=" + encodeURI(value) + ";expires=" + exp.toGMTString() + ";path=/";
}

//读取cookies
function getCookie(name) {
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");

    if (arr = document.cookie.match(reg))
        return decodeURI(arr[2]);
    else
        return null;
}
//删除cookies
function delCookie(name) {
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval = getCookie(name);
    if (cval != null)
        document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString() + ";path=/";
}
/************************************请求按钮变换***********************************************/
//请求后，按钮样式变换
function ajaxButtonRequest(obj, content) {
    if ($(obj).attr("status") == "1") {
        return true;
    } else {

        if (content == undefined) {
            if ($(obj).html() == "") {
                $(obj).val($(obj).val() + "···");
            } else {
                $(obj).html($(obj).html() + "&nbsp;·&nbsp;·&nbsp;·");
            }
        } else {
            if ($(obj).html() == "") {
                $(obj).val(content);
            } else {
                $(obj).html(content);
            }
        }
        $(obj).css("opacity", "0.6");
        $(obj).css("ilter", "alpha(opacity=60)");
        $(obj).attr("status", "1");
        return false;
    }
}
//请求返回后，按钮样式变换
function ajaxButtonRespone(obj, content) {
    if ($(obj).attr("status") == "1") {
        if (content == undefined) {
            if ($(obj).html() == "") {
                $(obj).val($(obj).val().substring(0, $(obj).val().indexOf("·")));
            } else {
                $(obj).html($(obj).html().substring(0, $(obj).html().indexOf("&")));
            }
        } else {
            if ($(obj).html() == "") {
                $(obj).val(content);
            } else {
                $(obj).html(content);
            }
        }
        $(obj).css("opacity", "1");
        $(obj).css("filter", "alpha(opacity=100)");
        $(obj).attr("status", "0");
    }
}
//获取url参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
}
//返回网站地址
function getWebAddress(){
    var url = window.location.href;
    var urlArray = url.split("//");
    url = urlArray[0] + "//" + urlArray[1].substring(0, urlArray[1].indexOf("/"));
    return url;
}
//百度坐标转高德坐标
function bdToGg(bd_lng, bd_lat){
    var x = bd_lng - 0.0065, y = bd_lat - 0.006;
    var z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * Math.PI);
    var theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * Math.PI);
    gg_lng = z * Math.cos(theta);
    gg_lat = z * Math.sin(theta);
    return {"gg_lng" : gg_lng, "gg_lat" : gg_lat};
}
//高德坐标转百度坐标
function ggToBd(gg_lng, gg_lat) {
    var x = gg_lng, y = gg_lat;
    var z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * Math.PI);
    var theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * Math.PI);
    bd_lng = z * Math.cos(theta) + 0.0065;
    bd_lat = z * Math.sin(theta) + 0.006;
    return {"bd_lng" : bd_lng, "bd_lat" : bd_lat};
}


function jumpUrl(url){
	window.location.href = url;
}

/** 
 * 获取指定的URL参数值 
 * URL:http://www.quwan.com/index?name=tyler 
 * 参数：paramName URL参数 
 * 调用方法:getParam("name") 
 * 返回值:tyler 
 */ 
function getParam(name) {   
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");   
    var r = window.location.search.substr(1).match(reg);   
    if (r != null) return decodeURI(r[2]); return null;   
}  