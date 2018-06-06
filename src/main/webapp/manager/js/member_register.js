var globalMemberId = "";
$(document).ready(function() {

})

function submit(){
    var memberName = $("#u4_input").val();
    var memberSex = $("#u28_input").val();
    $.post("../smartMember/memberRegist/", 
	{
		memberName:memberName,
		memberSex:memberSex
	},
	function(result){
		// console.log(result);
		var memberId = result.data.memberId;
		globalMemberId = memberId;
		submitCar(memberId);
	},'json');
}


function submitCar(memberId){
	var carNumber = $("#u13_input").val();
	var carType = $("#u23_input").val();
	var isOwn = 0;
	var carOwnerName = $("#u20_input").val();

	$.post("../smartCar/carRegist/", 
	{
		memberId:memberId,
		carNumber:carNumber,
		carType:carType,
		isOwn:isOwn,
		carOwnerName:carOwnerName
	},
	function(result){
		// console.log(result);
		if(result.code == 0){
			alert("注册成功！");
		}
	},'json');
}

function turnnext(){
	window.location.href="park_register.html?memberId="+globalMemberId
}

