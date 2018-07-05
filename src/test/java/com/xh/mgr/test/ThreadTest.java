package com.xh.mgr.test;

import com.ql.utils.HttpUtil;

public class ThreadTest extends Thread {
	public void run() {
		// 编写自己的线程代码
//		System.out.println(Thread.currentThread().getName());
		String resp = HttpUtil.httpPostRequest("https://zhonglestudio.cn/qlvip/wechat/getMemberInfoByCode?authCode=11&authOpenId=oqH-90dqpKds3VAM6dRSGNIO4yh4");
		System.out.println(resp);
	}

	public static void main(String[] args) {
		for(int i=0;i<100;i++){
			ThreadTest threadDemo01 = new ThreadTest();
			threadDemo01.setName("我是自定义的线程"+i);
			threadDemo01.start();
		}
//		System.out.println(Thread.currentThread().toString());
	}
	
	
	
}
