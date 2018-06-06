package com.ql.controller.weixin;

public class MessageHandler {
	public static String processMsg(String content){
		String resp = "欢迎使用停车系统！";
		if("connectus".equals(content)){
			resp = "电话：021-0000000\n地址：上海市黄浦区南苏州路1305号3号门";
		}
		return resp;
	}
}
