package com.xz.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebUtil {
	
    /** 
     * 对字符加星号处理：除前面几位和后面几位外，其他的字符以星号代替 
     *  
     * @param content 
     *            传入的字符串 
     * @param frontNum 
     *            保留前面字符的位数 
     * @param endNum 
     *            保留后面字符的位数 
     * @return 带星号的字符串 
     */  
  
    public static String getStarString2(String content, int frontNum, int endNum) {  
  
        if (frontNum >= content.length() || frontNum < 0) {  
            return content;  
        }  
        if (endNum >= content.length() || endNum < 0) {  
            return content;  
        }  
        if (frontNum + endNum >= content.length()) {  
            return content;  
        }  
        String starStr = "";  
        for (int i = 0; i < (content.length() - frontNum - endNum); i++) {  
            starStr = starStr + "*";  
        }  
        return content.substring(0, frontNum) + starStr  
                + content.substring(content.length() - endNum, content.length());  
  
    }  
    
    public static String regExEmail(String email){
        String regex = "(.{2}).+(.{2}@.+)g";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher("64565664@qq.com");
        boolean rs = matcher.find();
        System.out.println(rs);
        return null;
    	
    }
    
    public static void main(String[] args) {
		String email = "12345@qq.com";
		System.out.println(regExEmail(email));
	}
}
