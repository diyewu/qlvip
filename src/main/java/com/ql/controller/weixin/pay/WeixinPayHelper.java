package com.ql.controller.weixin.pay;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants.SignType;
import com.ql.config.weixin.WeixinConfig;

@Component
public class WeixinPayHelper {
//    @Autowired  
//    private WeixinConfig config; 
	/**
	 * 统一下单
	 * @param config
	 * @param body
	 * @param orderNo
	 * @param totalFee
	 * @param spbillCreateIp
	 * @param notifyUrl
	 * @param openId
	 * @return
	 */
	public static Map<String, String> unifiedOrder(WeixinConfig config,String body, String orderNo, String totalFee, String spbillCreateIp,
			String notifyUrl, String openId,boolean sandBox) {
		System.out.println("totalFee="+totalFee);
		//沙箱测试，正式环境需要把 useSandbox 改为false
		System.out.println("________key="+config.getKey());
		WXPay wxpay = new WXPay(config,SignType.MD5,sandBox);
		Map<String, String> data = new HashMap<String, String>();
		data.put("body", body);// "停车缴费订单支付"
		data.put("out_trade_no", orderNo);// 商户订单号
		data.put("device_info", "WEB");// 自定义参数，可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB"
		data.put("fee_type", "CNY");
		data.put("total_fee", totalFee);// 订单总金额，单位为分
		data.put("spbill_create_ip", spbillCreateIp);// APP和网页支付提交用户端ip
		data.put("notify_url", notifyUrl);// 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
		data.put("trade_type", "JSAPI"); // 此处指定为扫码支付
		data.put("openid", openId);
		
		Map<String, String> resp = new HashMap<String, String>();
		try {
			resp = wxpay.unifiedOrder(data);
			System.out.println(resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 申请退款
	 * @param config
	 * @param orderNo
	 * @param outRefundNo
	 * @param totalFee
	 * @param refundFee
	 * @param refundDesc
	 * @param notifyUrl
	 * @param sandBox
	 * @return
	 */
	public static Map<String, String> refund(WeixinConfig config,String orderNo,String outRefundNo,String totalFee,String refundFee,
			String refundDesc,String notifyUrl,boolean sandBox){
		WXPay wxpay = new WXPay(config,SignType.MD5,sandBox);
		Map<String, String> data = new HashMap<String, String>();
		data.put("out_trade_no", orderNo);// 商户订单号
		data.put("out_refund_no", outRefundNo);// 商户退款单号
		data.put("total_fee", totalFee);// 订单金额
		data.put("refund_fee", refundFee);// 退款金额
		data.put("refund_fee_type", "CNY");// 退款货币种类
		data.put("refund_desc", refundDesc);// 退款原因
		data.put("notify_url", notifyUrl);// 退款结果通知ur
		Map<String, String> resp = new HashMap<String, String>();
		try {
			resp = wxpay.refund(data);//申请退款
			System.out.println(resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 查询退款
	 * @param config
	 * @param outTradeNo
	 * @param sandBox
	 * @return
	 */
	public static Map<String, String> refundQuery(WeixinConfig config,String outTradeNo,boolean sandBox){
		WXPay wxpay = new WXPay(config,SignType.MD5,sandBox);
		Map<String, String> data = new HashMap<String, String>();
		data.put("out_trade_no", outTradeNo);
		Map<String, String> refundQuery = new HashMap<String, String>();
		try {
			refundQuery = wxpay.refundQuery(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return refundQuery;
	}
	
	/**
	 * 查询订单
	 * @param config
	 * @param data
	 * @return
	 */
	public static Map<String, String> orderQuery(WeixinConfig config,Map<String, String> data,boolean sandBox){
		//沙箱测试，正式环境需要把 useSandbox 改为false
		WXPay wxpay = new WXPay(config,SignType.MD5,sandBox);
		Map<String, String> query = new HashMap<String, String>();
		try {
			query = wxpay.orderQuery(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return query;
	}
	
	
	/**
	 * 下载对账单
	 * @param config
	 * @param billDate
	 * @param sandBox
	 * @return
	 */
	public static Map<String, String> downloadBill(WeixinConfig config,String billDate,boolean sandBox){
		WXPay wxpay = new WXPay(config,SignType.MD5,sandBox);
		Map<String, String> data = new HashMap<String, String>();
		data.put("bill_date", billDate);
		data.put("bill_type", "ALL");
		Map<String, String> downloadBill  = new HashMap<String, String>();
		try {
			downloadBill = wxpay.downloadBill(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return downloadBill;
	}
	
	/**
	 * 判断支付结果通知中的sign是否有效
	 * @param config
	 * @param billDate
	 * @param sandBox
	 * @param reqData
	 * @return
	 */
	public static boolean isPayResultNotifySignatureValid(WeixinConfig config,boolean sandBox,Map<String, String> reqData){
		WXPay wxpay = new WXPay(config,SignType.MD5,sandBox);
		try {
			return wxpay.isPayResultNotifySignatureValid(reqData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	

	/**
	 * 获取客户端IP
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
