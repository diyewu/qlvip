package com.ql.controller.weixin;

import java.util.TreeMap;

import org.codehaus.jackson.map.ObjectMapper;

import com.ql.controller.weixin.message.response.TemplateMsgResult;
import com.ql.controller.weixin.message.response.WechatTemplateMsg;

public class WeixinSendTeleplate {
	/**
	 * 待支付订单通知
	 * @param openId
	 * @param url
	 * @param orderNo
	 * @param pay
	 * @param payWay
	 * @param failureTime
	 */
	public static void sendUnpaidInfo(String openId, String url, String orderNo, String pay, String payWay,
			String failureTime,String appId,String appSecret,String titleContent) {
		String msg = null;
		TemplateMsgResult templateMsgResult = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			TreeMap<String, TreeMap<String, String>> params = new TreeMap<String, TreeMap<String, String>>();
			// 根据具体模板参数组装
			params.put("first", WechatTemplateMsg.item(titleContent, "#000000"));
			params.put("keyword1", WechatTemplateMsg.item(orderNo, "#000000")); // 订单编号
			params.put("keyword2", WechatTemplateMsg.item(pay+"元", "#FF0033")); // 待付金额
			params.put("keyword3", WechatTemplateMsg.item(payWay, "#33CC33")); // 支付方式
			params.put("keyword4", WechatTemplateMsg.item(failureTime, "#FF0033")); // 失效时间：
			params.put("remark", WechatTemplateMsg.item("若对此订单有疑问，请及时联系客服人员：021-0000000", "#000000"));
			WechatTemplateMsg wechatTemplateMsg = new WechatTemplateMsg();
			wechatTemplateMsg.setTemplate_id("gENGskvy7kJ6RHALE9KUBEeQGoxebSz1Ei9Pv88Dqww"); // 消费成功模板ID
			wechatTemplateMsg.setTouser(openId);
			wechatTemplateMsg.setUrl(url);
			wechatTemplateMsg.setData(params);
			String data = "";
			try {
				data = mapper.writeValueAsString(wechatTemplateMsg);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String token = WeixinHelper.getAccessToken(appId, appSecret);
			templateMsgResult = MessageHandler.sendTemplate(token, data);
			System.out.println(templateMsgResult);
		} catch (Exception e) {
			msg = e.getMessage();
			e.printStackTrace();
		}
//		if(!"0".equals(templateMsgResult.getErrcode())){
			System.out.println("_______"+templateMsgResult);
//		}
		
		
	}
}
