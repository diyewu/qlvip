package com.ql.utils;

public class SMSUtil {
	private static String utf8BaseUrl = "http://211.149.203.162:8868/sms.aspx";
	private static String GBKBaseUrl = "http://211.149.203.162:8868/smsGBK.aspx";
	private static String SMSID="882";
	private static String SMSAccount="wu_di_ye1";
	private static String SMSpassword="18936483081";
	
	public static void sendSMS(String mobile,String content){
		String url = utf8BaseUrl+"?action=send&userid=882&account=wu_di_ye1&password=18936483081&mobile="+mobile+"&content="+content+"&sendTime=&extno=";
		try {
			String string = HttpUtil.httpPostRequest(url);
			System.out.println("__________"+string);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		String content = "【芊乐零食屋】尊敬的用户：您的验证码为：111000（60分钟内有效），为了保证您的账户安全，请勿向任何人提供此验证码。";
		sendSMS("18936483081", content);
	}
}
