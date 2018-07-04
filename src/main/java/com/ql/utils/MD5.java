package com.ql.utils;

import java.security.*;

import org.apache.log4j.Logger;

//The example encrypt and decrypt data of byte array.
public class MD5
{
	private static Logger log = Logger.getLogger(MD5.class);
	
	public static String encrypt(String src)
	{
		src = src.trim();

		String Digest = "";
		try
		{
			MessageDigest currentAlgorithm = MessageDigest.getInstance("md5");
			currentAlgorithm.reset();
			byte[] mess = src.getBytes();
			byte[] hash = currentAlgorithm.digest(mess);
			for(int i = 0;i< hash.length; i++)
			{
				int v = hash[i];
				if(v<0)
					 v = 256+v;
				if(v<16)Digest+="0";
				Digest+=Integer.toString(v,16).toUpperCase() + "";
			}
		}
		catch(NoSuchAlgorithmException e)
		{
			// System.out.println("MD5�㷨û��װ�أ�");
			//e.printStackTrace();
			log.error("com.cssweb.common.encrypt.MD5.encrypt",e);
		}
		return Digest;
	}
	
	public static void main(String args[])
	{
		// System.out.println(MD5.encrypt("111111"));
	}
}
