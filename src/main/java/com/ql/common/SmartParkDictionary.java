package com.ql.common;

public class SmartParkDictionary {
//	public final static int ORDER_FINISHED = 0;//完结，正常驶出停车场
//	public final static int ASK_IN = 1;//申请驶进停车场
//	public final static int CAR_PARKING = 2;//停车中
//	public final static int ASK_OUT = 3;//申请驶出停车场，待支付
//	public final static int PAY_FINISHED = 4;//支付完成，待出场
//	public final static int FORCE_FINISH = 5;//强制完结，无法正常完结
	
	public enum orderState{
		ORDER_FINISHED,//完结，正常驶出停车场
		ASK_IN,//申请驶进停车场
		CAR_PARKING,//停车中
		ASK_OUT,//申请驶出停车场，待支付
		PAY_FINISHED,//支付完成，待出场
		FORCE_FINISH//强制完结，无法正常完结
	}
	
	public enum parkingType{
		PARK_IN,//驶入
		PARK_OUT//驶出
	}
	
}
