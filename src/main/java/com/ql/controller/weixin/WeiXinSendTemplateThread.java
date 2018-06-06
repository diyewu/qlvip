package com.ql.controller.weixin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.ql.controller.weixin.message.response.TemplateMsgResult;
import com.ql.controller.weixin.message.response.WechatTemplateMsg;

public class WeiXinSendTemplateThread extends Thread {
	private List<Map<String, Object>> list;

	public WeiXinSendTemplateThread(List<Map<String, Object>> list) {
		this.list = list;
	}

	public void run() {
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {// 根据openid发送模板消息
				String orderNo = list.get(i).get("order_no") + "";// 订单编号
				String actuallypaid = list.get(i).get("actuallypaid") + "";// 实际支付
				String deposit = list.get(i).get("deposit") + "";// 余额
				String memberNo = list.get(i).get("member_no") + "";// 会员编号
				String phone = list.get(i).get("phone") + "";// 手机号
				String openId = list.get(i).get("open_id") + "";// 微信关注的open_id
				String date = list.get(i).get("inputdatetime") + "";// date
				processTemplate(openId, "", memberNo, date, actuallypaid, deposit);
			}
		}
	}

	public void processTemplate(String openId, String url, String memberNo, String date, String actuallypaid,
			String deposit) {
		ObjectMapper mapper = new ObjectMapper();
		TemplateMsgResult templateMsgResult = null;
		TreeMap<String, TreeMap<String, String>> params = new TreeMap<String, TreeMap<String, String>>();
		// 根据具体模板参数组装
		params.put("first", WechatTemplateMsg.item("尊敬的会员，您的订单已经支付成功", "#000000"));
		params.put("keyword1", WechatTemplateMsg.item(memberNo, "#000000")); // 会员编号
		params.put("keyword2", WechatTemplateMsg.item(date, "#000000")); // 消费时间
		params.put("keyword3", WechatTemplateMsg.item(actuallypaid, "#000000")); // 消费金额：
		params.put("keyword4", WechatTemplateMsg.item(deposit, "#000000")); // 卡中余额：
		params.put("remark", WechatTemplateMsg.item("若对此订单有疑问，请及时联系店员小姐姐哦，谢谢！", "#000000"));
		WechatTemplateMsg wechatTemplateMsg = new WechatTemplateMsg();
		wechatTemplateMsg.setTemplate_id("qpXgZFCca3YI5Ktb-nobddSe1VqqoDOjKVTIuaKu18s"); // 消费成功模板ID
		wechatTemplateMsg.setTouser(openId);
		wechatTemplateMsg.setUrl(url);
		wechatTemplateMsg.setData(params);
		String data = "";
		try {
			data = mapper.writeValueAsString(wechatTemplateMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String token = WeixinHelper.getAccessToken("", "");
		templateMsgResult = MessageHandler.sendTemplate(token, data);
		if(!"0".equals(templateMsgResult.getErrcode())){
			System.out.println("出错啦……"+templateMsgResult.getErrmsg());
		}
	}

	public static void main(String[] args) {
		Thread thread = new WeiXinSendTemplateThread(new ArrayList<Map<String, Object>>());
		thread.start();
	}
}