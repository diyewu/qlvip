package com.xz.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component  
@ConfigurationProperties(prefix="custom") //接收application.yml中的myProps下面的属性  
public class CustomConfig {  
    private String clientFileUpladPath;
    private String smtp;
    private String port;
    private String user;
    private String pwd;
    private String appid;
    private String secret;
    private String baiduapikey;
    private String weixintoken;
    private String sendtimes;

	public String getClientFileUpladPath() {
		return clientFileUpladPath;
	}

	//String类型的一定需要setter来接收属性值；maps, collections, 和 arrays 不需要  
	public void setClientFileUpladPath(String clientFileUpladPath) {
		this.clientFileUpladPath = clientFileUpladPath;
	}

	public String getSmtp() {
		return smtp;
	}

	public void setSmtp(String smtp) {
		this.smtp = smtp;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getBaiduapikey() {
		return baiduapikey;
	}

	public void setBaiduapikey(String baiduapikey) {
		this.baiduapikey = baiduapikey;
	}

	public String getWeixintoken() {
		return weixintoken;
	}

	public void setWeixintoken(String weixintoken) {
		this.weixintoken = weixintoken;
	}

	public String getSendtimes() {
		return sendtimes;
	}

	public void setSendtimes(String sendtimes) {
		this.sendtimes = sendtimes;
	} 
	
    
}  