package com.ql.controller.weixin;

import java.util.TreeMap;

import org.codehaus.jackson.map.ObjectMapper;

import com.ql.controller.weixin.message.response.TemplateMsgResult;
import com.ql.utils.HttpsUtil;

public class MessageHandler {
	
	private final static String tempLateUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	public static String processMsg(String content){
		String resp = "欢迎光临芊乐零食屋！\n绑定会员请点击“绑定手机”菜单，绑定之后就可以接收消费信息啦!\n更多功能，敬请期待。";
		if("connectus".equals(content)){
			resp = "联系电话：15162790722\n地址：南通市通州区实验小学往东80米";
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
        String resp = HttpsUtil.doPostSSL(url, data);
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
