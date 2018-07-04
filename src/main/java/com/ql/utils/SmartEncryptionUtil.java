package com.ql.utils;

import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class SmartEncryptionUtil {
	
	public static String encryParam(Map<String, String> param, String key,String code){
		String time = param.get("time");
		String value = param.get(key);
		String sign = value + time + code ;
		sign = MD5.encrypt(sign);
		return sign;
	}
	
	/**
	 * 参数加密校验
	 * @param param
	 * @param key
	 * @return
	 */
	public static String checkParameter(Map<String, String> param, String key,String code) {
		String msg = null;
		
		String time = param.get("time");
		String sign = param.get("sign");
		String value = param.get(key);
		
		if(!NumberUtils.isNumber(time)) {
			msg = "time 参数有误!";
		} else if(sign == null) {
			msg = "sign 参数有误!";
		} else if(value == null) {
			msg = key + " 参数有误!";
		} else {
			if(Math.abs(System.currentTimeMillis() - NumberUtils.toLong(time)) > 24 * 60 * 60 * 1000) {
				msg = "处理时间超过时限!";
			} else {
				String validCode = value + time + code;
				validCode = MD5.encrypt(validCode);
				if(!validCode.equals(sign) ){
					msg = "参数校验错误!";
				}
			}
		}
		return msg;
	}
}
