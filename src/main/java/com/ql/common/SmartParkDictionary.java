package com.ql.common;

public class SmartParkDictionary {
	public enum orderState{
		ORDER_FINISHED,//完结，正常驶出停车场
		ASK_IN,//申请驶进停车场
		CAR_PARKING,//停车中
		ASK_OUT,//申请驶出停车场，待支付
		PAY_FINISHED,//支付完成，待出场
		FORCE_FINISH,//强制完结，无法正常完结
		ORDER_CANCEL,//已取消
		ORDER_REFUND//已退款
	}
	
	public enum parkingType{
		PARK_IN,//驶入
		PARK_OUT//驶出
	}
	
	public enum weixinPayProgress{
		获取预支付交易单,
		查询订单结果,
		微信通知,
		申请退款,
		查询退款结果
	}
}
