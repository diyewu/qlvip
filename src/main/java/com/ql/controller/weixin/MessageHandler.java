package com.ql.controller.weixin;

import java.util.TreeMap;

import org.codehaus.jackson.map.ObjectMapper;

import com.ql.controller.weixin.message.response.TemplateMsgResult;
import com.ql.utils.HttpsUtil;

public class MessageHandler {
	private final static String tempLateUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	public static String processMsg(String content){
		String resp = "欢迎关注芊乐零食屋";
		if("connectus".equals(content)){
			resp = "电话：15162790722\n地址：通州区实验小学往东80米芊乐零食屋";
		}
		return resp;
	}
	
	/** 
     * 发送模板消息
     * @param accessToken
     * @param data 
     * @return 状态 
     */  
    public static TemplateMsgResult sendTemplate(String accessToken, String data) {  
        TemplateMsgResult templateMsgResult = null;  
        TreeMap<String,String> params = new TreeMap<String,String>();  
        params.put("access_token", accessToken); 
        String url = tempLateUrl.replace("ACCESS_TOKEN", accessToken);
        System.out.println("url="+url);
        String resp = HttpsUtil.doPostSSL(url, data);
        System.out.println("resp="+resp);
//        String result = HttpReqUtil.HttpsDefaultExecute(HttpReqUtil.POST_METHOD, WechatConfig.SEND_TEMPLATE_MESSAGE, params, data);  
//        templateMsgResult = JsonUtil.fromJson(result, TemplateMsgResult.class);  
        ObjectMapper objectMapper = new ObjectMapper();
        try {
        	templateMsgResult = objectMapper.readValue(resp, TemplateMsgResult.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return templateMsgResult;  
    }  
}
