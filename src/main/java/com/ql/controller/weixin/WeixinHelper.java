package com.ql.controller.weixin;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ql.controller.weixin.message.response.TextMessage;
import com.ql.entity.CustomConfig;
import com.ql.utils.AgingCache;
import com.ql.utils.HttpsUtil;
import com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler;

@Component
public class WeixinHelper {
	@Autowired  
    private CustomConfig customConfig; 
	public static void main(String[] args) {
		// System.out.println(getAccessToken("wxc03cad31c1ceb8f3",
		// "d4624c36b6795d1d99dcf0547af5443d"));
		TextMessage textMessage = new TextMessage();
		textMessage.setMsgType(WeixinConstants.MESSAGE_TEXT);
		textMessage.setToUserName("1111");
		textMessage.setFromUserName("2222222");
		textMessage.setCreateTime(System.currentTimeMillis() + "");
		textMessage.setContent("3asdasdasdsa");
		System.out.println(convertToXml(textMessage));
	}

	/**
	 * 基础支持的access_token
	 * @param appId
	 * @param appSecret
	 * @return
	 */
	public static String getAccessToken(String appId, String appSecret) {
		ObjectMapper mapper = new ObjectMapper();
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appId+"&secret="+appSecret+"";
		// String url = "https://api.weixin.qq.com/cgi-bin/token?";
		System.out.println("__url="+url);
		String accessToken = "";
		try {
			accessToken = AgingCache.getCacheInfo(WeixinConstants.WEIXIN_TOKEN) != null
					? (String) AgingCache.getCacheInfo(WeixinConstants.WEIXIN_TOKEN).getValue() : null;
			if (StringUtils.isBlank(accessToken)) {
				// 到接口中获取
				Map<String, Object> params = new HashMap<String, Object>();
				// params.put("appid", appId);
				// params.put("secret", appSecret);
				// params.put("grant_type", "client_credential");
				String resp = HttpsUtil.doPostSSL(url, params);
				System.out.println("获取token="+resp);
				Map<String, Object> map = mapper.readValue(resp, Map.class);
				if (map.containsKey("access_token")) {
					accessToken = (String) map.get("access_token");
					int expiresIn = (Integer) map.get("expires_in");
					AgingCache.putCacheInfo(WeixinConstants.WEIXIN_TOKEN, accessToken, expiresIn / 60 - 1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return accessToken;
	}
	/**
	 * 网页授权接口调用凭证access_token
	 * @param appId
	 * @param appSecret
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,String> getWebAuthOpenIdAndAccessToken(String appId, String appSecret,String authCode) {
		ObjectMapper mapper = new ObjectMapper();
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
		url = url.replace("APPID", appId).replace("SECRET", appSecret).replace("CODE", authCode);
		String accessToken = "";
		System.out.println("appId="+appId);
		System.out.println("appSecret="+appSecret);
		System.out.println("authCode="+authCode);
		System.out.println("url="+url);
		Map<String,String> respMap = new HashMap<String, String>();
		try {
			// 到接口中获取
			Map<String, Object> params = new HashMap<String, Object>();
			String resp = HttpsUtil.doPostSSL(url, params);
			Map<String, Object> map = mapper.readValue(resp, Map.class);
			if (map.containsKey("access_token")) {
				accessToken = (String) map.get("access_token");
				int expiresIn = (Integer) map.get("expires_in");
				respMap.put(WeixinConstants.WEIXIN_WEB_AUTH_TOKEN, accessToken);
				respMap.put(WeixinConstants.WEIXIN_OPEN_ID, (String)map.get("openid"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respMap;
	}

	/**
	 * 将xml转化为Map集合
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> xmlToMap(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		InputStream ins = null;
		try {
			ins = request.getInputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Document doc = null;
		try {
			doc = reader.read(ins);
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
		Element root = doc.getRootElement();
		@SuppressWarnings("unchecked")
		List<Element> list = root.elements();
		for (Element e : list) {
			map.put(e.getName(), e.getText());
		}
		try {
			ins.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return map;
	}

	public static String processRequest(HttpServletRequest request) {
		Map<String, String> map = xmlToMap(request);
		// 发送方帐号（一个OpenID）
		String fromUserName = map.get("FromUserName");
		// 开发者微信号
		String toUserName = map.get("ToUserName");
		// 消息类型
		String msgType = map.get("MsgType");
//		System.out.println("msgType=" + msgType);

		// 默认回复一个"success"
		String responseMessage = "success";
		// 对消息进行处理
		if (WeixinConstants.MESSAGE_TEXT.equals(msgType)) {// 文本消息
			// 消息内容
			String msgContent = map.get("Content");
			String resp = MessageHandler.processMsg(msgContent);
			System.out.println("resp=" + resp);
			TextMessage textMessage = new TextMessage();
			textMessage.setMsgType(WeixinConstants.MESSAGE_TEXT);
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(System.currentTimeMillis() + "");
			textMessage.setContent(resp);
			responseMessage = textMessageToXml(textMessage);
		}else if(WeixinConstants.MESSAGE_EVENT.equals(msgType)){//click
			String Event = map.get("Event");
			String resp = "";
			if("subscribe".equals(Event)){//subscribe(订阅)
				resp = "如果停留在首页等待，则表示当前为调试时间段，如需测试完整功能，请联系管理员：18936483081 进行开放处理！";
			}else if(WeixinConstants.MESSAGE_EVENT_CLICK.equals(Event)){
				String EventKey = map.get("EventKey");
				resp = MessageHandler.processMsg(EventKey);
			}
			TextMessage textMessage = new TextMessage();
			textMessage.setMsgType(WeixinConstants.MESSAGE_TEXT);
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(System.currentTimeMillis() + "");
			textMessage.setContent(resp);
			responseMessage = textMessageToXml(textMessage);
		}
		return responseMessage;

	}

	/**
	 * 文本消息转化为xml
	 * 
	 * @param textMessage
	 * @return
	 */
	public static String textMessageToXml(TextMessage textMessage) {
		return convertToXml(textMessage);
	}

	public static String convertToXml(Object obj) {
		return convertToXml(obj, "UTF-8");
	}

	/**
	 * JavaBean转换成xml
	 * 
	 * @param obj
	 * @param encoding
	 * @return
	 */
	public static String convertToXml(Object obj, String encoding) {
		String result = null;
		try {
			JAXBContext context = JAXBContext.newInstance(obj.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);

			// 去掉生成xml的默认报文头。
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

			// 转换所有的适配字符，包括xml实体字符&lt;和&gt;，xml实体字符在好多处理xml

			// 的框架中是处理不了的，除非序列化。
			marshaller.setProperty("com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler",
					new CharacterEscapeHandler() {
						@Override
						public void escape(char[] ch, int start, int length, boolean isAttVal, Writer writer)
								throws IOException {
							writer.write(ch, start, length);
						}
					});

			StringWriter writer = new StringWriter();

			// 添加自己想要的xml报文头
			writer.write("<?xml version=\'1.0\' encoding=\'" + encoding + "\'?>\n");
			marshaller.marshal(obj, writer);
			result = writer.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * xml转换成JavaBean
	 * 
	 * @param xml
	 * @param c
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T converyToJavaBean(String xml, Class<T> c) {
		T t = null;
		try {
			JAXBContext context = JAXBContext.newInstance(c);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			t = (T) unmarshaller.unmarshal(new StringReader(xml));
		} catch (Exception ex) {
		}
		return t;
	}

}
