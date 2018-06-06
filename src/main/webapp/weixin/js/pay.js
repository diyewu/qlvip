$(function(){
})

function runTime(){
	var m=29;
	var s=59;
	setInterval(function(){
		if(s<10){
			$('#left_time').html(m+':0'+s);
		}else{
			$('#left_time').html(m+':'+s);
		}
		s--;
		if(s<0){
			s=59;
			m--;
		}
	},1000)
}