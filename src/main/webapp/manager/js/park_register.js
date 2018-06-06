var globalMemberId = "";
$(document).ready(function() {
	globalMemberId = getParam('memberId');
	console.log(globalMemberId);
})

function turnnext(){
	window.location.href="simulate_parking.html"
}

/**
 *  注册停车场地主题信息
 */
function submitPark(){
    var parkName = $("#u58_input").val();
    var parkLongitude = $("#u64_input").val();
    var parkLatitude = $("#u67_input").val();
    var parkDescription = $("#u61_input").val();
    $.post("../smartPark/parkRegist/", 
	{
		memberId:globalMemberId,
		parkName:parkName,
		parkLongitude:parkLongitude,
		parkLatitude:parkLatitude,
		parkDescription:parkDescription
	},
	function(result){
		// console.log(result);
		var parkId = result.data.parkId;

		//注册地上停车位
		var spaceType = $("#u94_input").val();
		var spacePricePerhour = $("#u76_input").val();
		var spaceTotal = $("#u96_input").val();
		var spaceUsed = $("#u99_input").val();
		var spaceDescription = $("#u108_input").val();
		submitParkSpace(parkId,spaceType,spacePricePerhour,spaceTotal,spaceUsed,spaceDescription);
		//注册地下停车位
		spaceType = $("#u95_input").val();
		spacePricePerhour = $("#u81_input").val();
		spaceTotal = $("#u102_input").val();
		spaceUsed = $("#u105_input").val();
		spaceDescription = $("#u111_input").val();
		submitParkSpace(parkId,spaceType,spacePricePerhour,spaceTotal,spaceUsed,spaceDescription);

		//注册停车场正门入口信息
		var entranceName = $("#u46_input").val();
		var entranceLongitude = $("#u49_input").val();
		var entranceLatitude = $("#u52_input").val();
		var entranceDescription = $("#u116_input").val();
		submitParkEntrance(parkId,entranceName,entranceLongitude,entranceLatitude,entranceDescription);

		//注册停车场侧门入口信息
		entranceName = $("#u33_input").val();
		entranceLongitude = $("#u33_input").val();
		entranceLatitude = $("#u39_input").val();
		entranceDescription = $("#u119_input").val();
		submitParkEntrance(parkId,entranceName,entranceLongitude,entranceLatitude,entranceDescription);
	},'json');
}

function submitParkSpace(parkId,spaceType,spacePricePerhour,spaceTotal,spaceUsed,spaceDescription){
    $.post("../smartPark/parkSpaceRegist/", 
	{
		parkId:parkId,
		spaceType:spaceType,
		spacePricePerhour:spacePricePerhour,
		spaceTotal:spaceTotal,
		spaceUsed:spaceUsed,
		spaceDescription:spaceDescription
	},
	function(result){
		console.log(result);
	},'json');
}
function submitParkEntrance(parkId,entranceName,entranceLongitude,entranceLatitude,entranceDescription){
    $.post("../smartPark/parkEntranceRegist/", 
	{
		parkId:parkId,
		entranceName:entranceName,
		entranceLongitude:entranceLongitude,
		entranceLatitude:entranceLatitude,
		entranceDescription:entranceDescription
	},
	function(result){
		console.log(result);
	},'json');
}





/** 
 * 获取指定的URL参数值 
 * URL:http://www.quwan.com/index?name=tyler 
 * 参数：paramName URL参数 
 * 调用方法:getParam("name") 
 * 返回值:tyler 
 */ 
function getParam(paramName) { 
    paramValue = "", isFound = !1; 
    if (this.location.search.indexOf("?") == 0 && this.location.search.indexOf("=") > 1) { 
        arrSource = unescape(this.location.search).substring(1, this.location.search.length).split("&"), i = 0; 
        while (i < arrSource.length && !isFound) arrSource[i].indexOf("=") > 0 && arrSource[i].split("=")[0].toLowerCase() == paramName.toLowerCase() && (paramValue = arrSource[i].split("=")[1], isFound = !0), i++ 
    } 
    return paramValue == "" && (paramValue = null), paramValue 
}  