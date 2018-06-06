package wzh.Http;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class MiniProHttpTest {

	public static void main(String[] args) throws UnsupportedEncodingException {
//		login();
//		getMapInfoByuserRole();
//		getNextMapInfoByKey();
//		getCoordinateInfo();
		getGeocoderLatitude();
	}
	
	public static void login() throws UnsupportedEncodingException{
//		String url = "http://localhost:8095/gismgr/mini/login";
		String url = "https://zhonglestudio.cn/gismgr/mini/login";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", "admin");
		params.put("userPwd", "admin");
		params.put("randomkey", "011KYMn91e3U4Q1Vq4o91bxCn91KYMnC");
		String resp = HttpUtil.httpPostRequest(url, params);
		System.out.println(resp);
	}
	public static void getMapInfoByuserRole() throws UnsupportedEncodingException{
		String url = "http://localhost:8095/gismgr/mini/getMapInfoByuserRole";
//		String url = "https://zhonglestudio.cn/gismgr/mini/getMapInfoByuserRole";
		
		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("userName", "admin");
//		params.put("userPwd", "admin");
		params.put("randomkey", "011KYMn91e3U4Q1Vq4o91bxCn91KYMnC");
		params.put("token", "29dcc6e493bf4927b7c892b98f1844d2");
		String resp = HttpUtil.httpPostRequest(url, params);
		System.out.println(resp);
	}
	public static void getNextMapInfoByKey() throws UnsupportedEncodingException{
		String url = "http://localhost:8095/gismgr/mini/getNextMapInfoByKey";
//		String url = "https://zhonglestudio.cn/gismgr/mini/getNextMapInfoByKey";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("randomkey", "011KYMn91e3U4Q1Vq4o91bxCn91KYMnC");
		params.put("token", "29dcc6e493bf4927b7c892b98f1844d2");
		params.put("cacheKey", "c9a5075af19d4cc789396425da75fbbc");
		params.put("key", "3100000000000010");
		params.put("currentLevel", "6");
		
		String resp = HttpUtil.httpPostRequest(url, params);
		System.out.println(resp);
	}
	public static void getPreMapInfoByKey() throws UnsupportedEncodingException{
		String url = "http://localhost:8095/gismgr/mini/getPreMapInfoByKey";
//		String url = "https://zhonglestudio.cn/gismgr/mini/getNextMapInfoByKey";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("randomkey", "011KYMn91e3U4Q1Vq4o91bxCn91KYMnC");
		params.put("token", "29dcc6e493bf4927b7c892b98f1844d2");
		params.put("cacheKey", "c9a5075af19d4cc789396425da75fbbc");
		params.put("key", "31");
		params.put("currentLevel", "1");
		
		String resp = HttpUtil.httpPostRequest(url, params);
		System.out.println(resp);
	}
	
	
	public static void getCoordinateInfo() throws UnsupportedEncodingException{
		String url = "http://localhost:8095/gismgr/mini/getCoordinateInfo";
//		String url = "https://zhonglestudio.cn/gismgr/mini/getNextMapInfoByKey";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("randomkey", "011KYMn91e3U4Q1Vq4o91bxCn91KYMnC");
		params.put("token", "d462c8cedb7742869a9c27529ad3adfa");
		params.put("cacheKey", "dace06e0fbee4fadaa5b883b7d209d07");
		params.put("ids", "001521712028608052425cc5d4d2e6f0");
		
		String resp = HttpUtil.httpPostRequest(url, params);
		System.out.println(resp);
	}
	public static void getGeocoderLatitude() throws UnsupportedEncodingException{
//		String url = "http://localhost:8095/gismgr/mini/getCoordinateInfo";
		String url = "https://zhonglestudio.cn/gismgr/mini/getGeocoderLatitude";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("randomkey", "011KYMn91e3U4Q1Vq4o91bxCn91KYMnC");
		params.put("token", "1bc09acff6034a2286007d469a6514cc");
		params.put("address", "上海市中山公园");
		
		String resp = HttpUtil.httpPostRequest(url, params);
		System.out.println(resp);
	}
}
