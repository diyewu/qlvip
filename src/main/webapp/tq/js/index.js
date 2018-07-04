//自带js
var swiper ;
//自带js结束

window.onload = function() {
	swiper = new Swiper('.swiper-container', {
		pagination: '.swiper-pagination',
		paginationClickable: true,
		direction: 'vertical',
		onInit: function(swiper) { //Swiper2.x的初始化是onFirstInit
			swiperAnimateCache(swiper); //隐藏动画元素 
			swiperAnimate(swiper); //初始化完成开始动画
		},
		onSlideChangeEnd: function(swiper) {
			swiperAnimate(swiper); //每个slide切换结束时也运行当前slide动画
		}
	});
	
	//必须在微信Weixin JSAPI的WeixinJSBridgeReady才能生效
    document.addEventListener("WeixinJSBridgeReady", function () {
        document.getElementById('autoaudio').play();
    }, false);
}


//音乐操作
var music = document.getElementById("music");
var music_gif = music.getElementsByClassName('music_gif')[0];
var music_logo = music.getElementsByClassName('music_logo')[0];
var ad = music.getElementsByClassName('ad')[0];
var status = 2;
music.onclick = function() {
	//关闭音乐
	if(status == 2) {
		music_gif.style.display = 'none';
		music_logo.style.animation = 'none';
		ad.pause();
		status = 1;
	} else if(status == 1) { //开启音乐
		music_gif.style.display = 'block';
		music_logo.style.animation = 'run 2s linear infinite';
		ad.play();
		status = 2;
	}
}
//音乐操作结束


//罗杰处死
var dao=document.getElementsByClassName('dao')[0];
var go=document.getElementsByClassName('go')[0];
var lj=document.getElementsByClassName('lj')[0];
var word1=document.getElementsByClassName('word1')[0];
var word2=document.getElementsByClassName('word2')[0];
var word3=document.getElementsByClassName('word3')[0];
var word4=document.getElementsByClassName('word4')[0];
var word5=document.getElementsByClassName('word5')[0];
var word6=document.getElementsByClassName('word6')[0];
var chuan=document.getElementsByClassName('chuan')[0];
go.onclick=function(){
	dao.style.animation=' drun 1s linear';
	dao.style.left='-100%';
	lj.style.animation=' drun 1s linear';
	lj.style.left='-100%';
	word1.style.animation=' wrun 1s linear';
	word1.style.left='100%';
	word2.style.animation=' wrun 1s linear';
	word2.style.left='100%';
	word3.style.animation=' wrun 1s linear';
	word3.style.left='100%';
	word4.style.animation=' wrun 1s linear';
	word4.style.left='100%';
	word5.style.animation=' wrun 1s linear';
	word5.style.left='100%';
	word6.style.animation=' wrun 1s linear';
	word6.style.left='100%';
	this.style.animation=' wrun 1s linear';
	this.style.left='100%';
	chuan.style.display='block';
	chuan.style.animation=' move 2s linear infinite';
	
}
//罗杰处死结束


//翻盖
//var ct=document.getElementsByClassName("container")[0];
//var aCr=ct.getElementsByClassName('crew');
//var aM=ct.getElementsByClassName('m');
//var aItr=document.getElementsByClassName('itr');
////第一组
//aCr[0].onclick=function(){
//	aItr[0].animation="show 5s linear";
//}
//翻盖结束