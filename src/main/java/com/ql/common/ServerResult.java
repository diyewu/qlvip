package com.ql.common;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 *  0：成功
    1：未知服务器错误
    -----------------------smartparking----------------------
    50000:车主信息不能为空
    50001:场地管理员或后台操作管理员不能同时为空
    50002：车辆信息未注册
    50003：未查询到停车状态中的订单
    50004:订单编号和会员编号不匹配
    50005:停车驶入场地记录创建失败
    50006:手机号码验证失败
    50007:时间间隔小于120秒
    50008:今天手机发送次数已用完
    50009:尚未发送验证码
    50010:手机验证码验证失败
    50011:登陆信息验证失败
    50012:名下登记车辆总数超过最大数量
    50013:车辆信息校验错误
    50014:车辆权限信息校验失败
    50015:尚未查询到符合条件的订单，请确认已经发出停车申请
    50016:会员信息校验失败
    50017:参数校验失败
    50018:订单编号无效
    
 * @author 吴迪叶
 *
 */
public class ServerResult {
	public final static int RESULT_SUCCESS = 0;// success
	public final static int RESULT_SERVER_ERROR = 1;//未知服务器错误
	
	public final static String RESULT_SERVER_ERROR_MSG = "未知服务器错误";
	
	public final static int RESULT_OWNERINFO_ERROR = 50000;
	public final static String RESULT_OWNERINFO_ERROR_MSG = "车主信息不能为空";
	
	public final static int RESULT_PARK_BLANK_ERROR = 50001;
	public final static String RESULT_PARK_BLANK_ERROR_MSG = "场地管理员或后台操作管理员不能同时为空";
	
	public final static int RESULT_CAR_NOT_REGIST_ERROR = 50002;
	public final static String RESULT_CAR_NOT_REGIST_ERROR_MSG = "车辆信息未注册";
	
	public final static int RESULT_ORDER_DONOT_EXSIT_ERROR = 50003;
	public final static String RESULT_ORDER_DONOT_EXSIT_ERROR_MSG = "未查询到停车状态中的订单";
	
	public final static int RESULT_ORDERID_MEMBERID_NOTMATCH_ERROR = 50004;
	public final static String RESULT_ORDERID_MEMBERID_NOTMATCH_ERROR_MSG = "订单编号和会员编号不匹配";
	
	public final static int RESULT_RECORD_CREATE_ERROR = 50005;
	public final static String RESULT_RECORD_CREATE_ERROR_MSG = "停车驶入场地记录创建失败";
	
	public final static int RESULT_MOBILE_VALIDATE_ERROR = 50006;
	public final static String RESULT_MOBILE_VALIDATE_ERROR_MSG = "手机号码验证失败";
	
	public final static int RESULT_MOBILE_CODE_SEND_INTERVAL_ERROR = 50007;
	public final static String RESULT_MOBILE_CODE_SEND_INTERVAL_ERROR_MSG = "时间间隔小于120秒";
	
	public final static int RESULT_MOBILE_CODE_SEND_REMAINTIMES_ERROR = 50008;
	public final static String RESULT_MOBILE_CODE_SEND_REMAINTIMES_MSG = "今天手机发送次数已用完";
	
	public final static int RESULT_SESSION_MOBILE_CHECK_ERROR = 50009;
	public final static String RESULT_SESSION_MOBILE_CHECK_ERROR_MSG = "尚未发送验证码";
	
	public final static int RESULT_MOBILE_CODE_VALIDATE_ERROR = 50010;
	public final static String RESULT_MOBILE_CODE_VALIDATE_ERROR_MSG = "手机验证码验证失败";
	
	public final static int RESULT_AUTH_VALIDATE_ERROR = 50011;
	public final static String RESULT_AUTH_VALIDATE_ERROR_MSG = "权限信息验证失败";
	
	public final static int RESULT_MAX_CAR_ERROR = 50012;
	public final static String RESULT_MAX_CAR_ERROR_MSG = "名下登记车辆总数超过最大数量";
	
	public final static int RESULT_CAR_VALIDATE_ERROR = 50013;
	public final static String RESULT_CAR_VALIDATE_ERROR_MSG = "车辆信息校验错误";
	
	public final static int RESULT_CAR_AUTH_VALIDATE_ERROR = 50014;
	public final static String RESULT_CAR_AUTH_VALIDATE_ERROR_MSG = "车辆权限信息校验失败";

	public final static int RESULT_ORDER_STATE_ERROR = 50015;
	public final static String RESULT_ORDER_STATE_ERROR_MSG = "尚未查询到符合条件的订单";
	
	public final static int RESULT_MEMBER_AUTH_ERROR = 50016;
	public final static String RESULT_MEMBER_AUTH_ERROR_MSG = "会员信息校验失败";
	
	public final static int RESULT_PARAM_CHECK_ERROR = 50017;
	public final static String RESULT_PARAM_CHECK_ERROR_MSG = "参数校验失败";
	
	public final static int RESULT_ORDER_ID_ERROR = 50018;
	public final static String RESULT_ORDER_ID_ERROR_MSG = "订单编号无效";
	
	public final static int RESULT_GET_PREPAY_ID_ERROR = 50019;
	public final static String RESULT_GET_PREPAY_ID_ERROR_MSG = "预支付交易会话标识失败";
	
	public final static int RESULT_ORDER_FEE_ERROR = 50020;
	public final static String RESULT_ORDER_FEE_ERROR_MSG = "订单费用异常";
	
	public final static int RESULT_REFUND_ERROR = 50021;
	public final static String RESULT_REFUND_ERROR_MSG = "退款失败";
	
	public final static int RESULT_CAR_EXIST_PARKING_ORDER_ERROR = 50022;
	public final static String RESULT_CAR_EXIST_PARKING_ORDER_ERROR_MSG = "当前车辆存在未支付订单，请先支付未完结订单";
	
	public static Map<Integer,String> ServerResultMap = new HashMap<Integer, String>();
	static{
		ServerResultMap.put(RESULT_SUCCESS, "success");
		ServerResultMap.put(RESULT_OWNERINFO_ERROR, RESULT_OWNERINFO_ERROR_MSG);
		ServerResultMap.put(RESULT_PARK_BLANK_ERROR, RESULT_PARK_BLANK_ERROR_MSG);
		ServerResultMap.put(RESULT_CAR_NOT_REGIST_ERROR, RESULT_CAR_NOT_REGIST_ERROR_MSG);
		ServerResultMap.put(RESULT_ORDER_DONOT_EXSIT_ERROR, RESULT_ORDER_DONOT_EXSIT_ERROR_MSG);
		ServerResultMap.put(RESULT_ORDERID_MEMBERID_NOTMATCH_ERROR, RESULT_ORDERID_MEMBERID_NOTMATCH_ERROR_MSG);
		ServerResultMap.put(RESULT_RECORD_CREATE_ERROR, RESULT_RECORD_CREATE_ERROR_MSG);
		ServerResultMap.put(RESULT_MOBILE_VALIDATE_ERROR, RESULT_MOBILE_VALIDATE_ERROR_MSG);
		ServerResultMap.put(RESULT_MOBILE_CODE_SEND_INTERVAL_ERROR, RESULT_MOBILE_CODE_SEND_INTERVAL_ERROR_MSG);
		ServerResultMap.put(RESULT_MOBILE_CODE_SEND_REMAINTIMES_ERROR, RESULT_MOBILE_CODE_SEND_REMAINTIMES_MSG);
		ServerResultMap.put(RESULT_SESSION_MOBILE_CHECK_ERROR, RESULT_SESSION_MOBILE_CHECK_ERROR_MSG);
		ServerResultMap.put(RESULT_MOBILE_CODE_VALIDATE_ERROR, RESULT_MOBILE_CODE_VALIDATE_ERROR_MSG);
		ServerResultMap.put(RESULT_AUTH_VALIDATE_ERROR, RESULT_AUTH_VALIDATE_ERROR_MSG);
		ServerResultMap.put(RESULT_MAX_CAR_ERROR, RESULT_MAX_CAR_ERROR_MSG);
		ServerResultMap.put(RESULT_CAR_VALIDATE_ERROR, RESULT_CAR_VALIDATE_ERROR_MSG);
		ServerResultMap.put(RESULT_CAR_AUTH_VALIDATE_ERROR, RESULT_CAR_AUTH_VALIDATE_ERROR_MSG);
		ServerResultMap.put(RESULT_ORDER_STATE_ERROR, RESULT_ORDER_STATE_ERROR_MSG);
		ServerResultMap.put(RESULT_MEMBER_AUTH_ERROR, RESULT_MEMBER_AUTH_ERROR_MSG);
		ServerResultMap.put(RESULT_PARAM_CHECK_ERROR, RESULT_PARAM_CHECK_ERROR_MSG);
		ServerResultMap.put(RESULT_ORDER_ID_ERROR, RESULT_ORDER_ID_ERROR_MSG);
		ServerResultMap.put(RESULT_GET_PREPAY_ID_ERROR, RESULT_GET_PREPAY_ID_ERROR_MSG);
		ServerResultMap.put(RESULT_ORDER_FEE_ERROR, RESULT_ORDER_FEE_ERROR_MSG);
		ServerResultMap.put(RESULT_REFUND_ERROR, RESULT_REFUND_ERROR_MSG);
		ServerResultMap.put(RESULT_CAR_EXIST_PARKING_ORDER_ERROR, RESULT_CAR_EXIST_PARKING_ORDER_ERROR_MSG);
	}
	
	public static String getCodeMsg(int code,String defaultValue){
		String msg = null;
		if(StringUtils.isBlank(defaultValue)){
			msg = ServerResultMap.get(code);;
		}else{
			msg = defaultValue;
		}
		return msg;
	}
}
