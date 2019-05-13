package com.ql.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
@Component
public class SMSUtil {
//	private static String utf8BaseUrl = "http://211.149.203.162:8868/sms.aspx";
//	private static String utf8BaseUrl = "http://39.98.198.70:8868/sms.aspx";
//	private static String GBKBaseUrl = "http://39.98.198.70:8868/smsGBK.aspx";
//	private static String SMSID="882";
//	private static String SMSAccount="wu_di_ye1";
//	private static String SMSpassword="eAklUhSd6z";
	private static String utf8BaseUrl ;
	private static String GBKBaseUrl ;
	private static String SMSID;
	private static String SMSAccount;
	private static String SMSpassword;

	public static String getUtf8BaseUrl() {
		return utf8BaseUrl;
	}

	@Value("${custom.utf8BaseUrl}")
	public  void setUtf8BaseUrl(String utf8BaseUrl) {
		SMSUtil.utf8BaseUrl = utf8BaseUrl;
	}

	public static String getGBKBaseUrl() {
		return GBKBaseUrl;
	}
	@Value("${custom.gbkbaseurl}")
	public  void setGBKBaseUrl(String GBKBaseUrl) {
		SMSUtil.GBKBaseUrl = GBKBaseUrl;
	}

	public static String getSMSID() {
		return SMSID;
	}

	@Value("${custom.smsid}")
	public  void setSMSID(String SMSID) {
		SMSUtil.SMSID = SMSID;
	}

	public static String getSMSAccount() {
		return SMSAccount;
	}

	@Value("${custom.smsaccount}")
	public void setSMSAccount(String SMSAccount) {
		SMSUtil.SMSAccount = SMSAccount;
	}

	public static String getSMSpassword() {
		return SMSpassword;
	}

	@Value("${custom.smspassword}")
	public void setSMSpassword(String SMSpassword) {
		SMSUtil.SMSpassword = SMSpassword;
	}

	public static void sendSMS(String mobile, String content) {
		try {
			String url = utf8BaseUrl + "?action=send&userid="+SMSID+"&account="+SMSAccount+"&password="+SMSpassword+"&mobile=" + mobile + "&content=" + URLEncoder.encode(content,"UTF-8") + "&sendTime=&extno=";
			String string = HttpUtil.httpPostRequest(url);
			System.out.println("__________"+string);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	public static void main(String[] args) {
		String content = "【芊乐零食屋】尊敬的用户：您的验证码为：111000（60分钟内有效），为了保证您的账户安全，请勿向任何人提供此验证码。";
		sendSMS("18936483081", content);
	}
}
