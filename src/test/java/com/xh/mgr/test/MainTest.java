package com.xh.mgr.test;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Joiner;

public class MainTest {
	public static void main(String[] args) throws UnsupportedEncodingException, ParseException {
		time();
	}
	
	
	public static void time( ) throws ParseException{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    try  
	    {  
	      Date d1 = df.parse("2018-03-26 13:31:40");  
	      Date d2 = df.parse("2018-03-26 11:30:24");  
	      long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别  
	      long days = diff / (1000 * 60 * 60 * 24);  
	   
	      long hours = (diff)/(1000* 60 * 60);  
	      long minutes = (diff-hours*(1000* 60 * 60))/(1000* 60);  
	      System.out.println(hours+"小时"+minutes+"分");  
	    }catch (Exception e)  
	    {  
	    }  
	}
	public static void joinList(){
		List<String> list = new ArrayList<String>();  
		list.add("a");  
		list.add("b");  
		list.add("c");  
		System.out.println(Joiner.on(",").join(list));
	}
	
	public static void urlEncode() throws UnsupportedEncodingException{
		 System.out.println(java.net.URLEncoder.encode("https://zhonglestudio.cn/smartparking/weixin/index.htm",   "utf-8"));   
	}
	
	public static void formatTest(){
		DecimalFormat df = new DecimalFormat("#.00000");
		double longitudeF = 121.411;
		double latitudeF = 30.803;
		String key = df.format(longitudeF) + df.format(longitudeF);
		System.out.println(key);
		
	}
	
	public static void splitTest(){
//		String s = "310000000000";
//		String zero = "00";
//		String resp = s.substring(0, s.length()-zero.length());
//		System.out.println(resp);
		String s = "3100000000000013BZ";
		int a =12;
		int b =16;
		
	}
	public static void listTest(){
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("1");
		list.add("1");
		System.out.println(StringUtils.join(list.toArray(), "','"));
	}
}
