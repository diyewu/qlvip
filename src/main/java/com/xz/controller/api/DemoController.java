package com.xz.controller.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xz.common.ServerResult;
import com.xz.common.SmartParkDictionary;
import com.xz.controller.BaseController;
import com.xz.entity.SmartOrder;
import com.xz.model.json.AppJsonModel;
import com.xz.service.SmartCarService;
import com.xz.service.SmartParkService;
import com.xz.utils.DateHelper;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
@RestController
@RequestMapping("demo")
public class DemoController extends BaseController{
	@Autowired
	private SmartParkService smartParkService;
	@Autowired
	private SmartCarService smartCarService;
	
	@ApiOperation(value = "汽车申请驶入停车场地", notes = "由客户端识别汽车牌照", httpMethod = "POST")
	@RequestMapping("askInParking")
	@ResponseBody
	public AppJsonModel askInParking(
			 @ApiParam(name = "carNumber", value = "准备驶入停车场汽车牌照编号，有客户端自动识别或手动填写", required = true) @RequestParam(value = "carNumber", required = false) String carNumber,
			 @ApiParam(name = "parkId", value = "停车场编号", required = true) @RequestParam(value = "parkId", required = true) String parkId
			){
		String msg = null;
		int code = 0;
		Map<String,Object> respMap = new HashMap<String, Object>();
		String orderId = "";
		try {
			//TODO 进场，出场，产生订单
			//获取carid,
			String carId = smartCarService.getCarIdByNumber(carNumber);
			if(StringUtils.isBlank(carId)){//车辆信息不存在
				code = ServerResult.RESULT_CAR_NOT_REGIST_ERROR;
			}
			if(code == 0){
				//车辆申请停车，TODO 判断是否允许车辆停车,需要判断车位剩余数量信息  判断车辆 是否需要付钱
				code = smartParkService.checkCarForbidParking(carId, parkId);
				if(code == 0){//申请成功，允许车辆进入，创建订单
					orderId = smartParkService.createOrder(carId, parkId, 0);
				}
			}
		} catch (Exception e) {
			msg = e.getMessage();
			e.printStackTrace();
		}
		respMap.put("orderId", orderId);
		return new AppJsonModel(code, ServerResult.getCodeMsg(code, msg), respMap);
	}
	
	
	@ApiOperation(value = "汽车申请驶出停车场地", notes = "由机器客户端识别汽车牌照，返回：开始停车时间、结束停车时间、停车场地、车位、总共产生停车费用，推送到APP客户端，等待会员支付", httpMethod = "POST")
	@RequestMapping("askOutParking")
	@ResponseBody
	public AppJsonModel askOutParking(
			@ApiParam(name = "carNumber", value = "准备驶出停车场汽车牌照编号，有客户端自动识别或手动填写", required = true) @RequestParam(value = "carNumber", required = false) String carNumber,
			@ApiParam(name = "parkId", value = "停车场编号", required = true) @RequestParam(value = "parkId", required = true) String parkId
			){
		String msg = null;
		int code = 0;
		Map<String,Object> respMap = new HashMap<String, Object>();
		String orderId = "";
		try {
			//获取carid,
			String carId = smartCarService.getCarIdByNumber(carNumber);
			if(StringUtils.isBlank(carId)){//车辆信息不存在
				code = ServerResult.RESULT_CAR_NOT_REGIST_ERROR;
			}
			if(code == 0){
				//车辆申请驶出场地，返回，开始停车时间、结束停车时间、停车场地、车位、总共产生停车费用，到会员客户端，等待会员支付
				//step 1、查看是否有满足条件的订单，carId，parkId，order_state_id=2
				//TODO 如何处理多条  同样carId，parkId，order_state_id=2 订单，暂时先按照第一条处理
				List<Map<String, Object>> list = smartParkService.getOrderId(carId, parkId, 2);
				if (list != null && list.size() > 0) {
					orderId = list.get(0).get("id")+"";
				}else{
					code = ServerResult.RESULT_ORDER_DONOT_EXSIT_ERROR;
				}
				//step 2、根据订单信息，获取进场record，计算停车费用
				if(code == 0){
					List<Map<String, Object>> orderList = smartParkService.getOrderInfo(orderId);
					if (orderList != null && orderList.size() > 0) {
						String format = "yyyy-MM-dd HH:mm:ss";
						String beginTimeStr = orderList.get(0).get("begin_time")+"";
						Date beginTime = DateHelper.paraseStringToDate(beginTimeStr, format);
						Date endTime = new Date();
						String endTimeStr = DateHelper.paraseDateToString(endTime, format);
						long between=(endTime.getTime()-beginTime.getTime())/1000;//除以1000是为了转换成秒
						long min=between/60;//获取分钟数
						double hour = min/60;
						long yushu = min%60;//取整数小时候，余 分钟数,不满30分钟，按照0.5小时计算，大于30分钟按1小时计算
						if(yushu <= 30){
							hour = hour+0.5;
						}else{
							hour = hour + 1;
						}
						double spacePricePerhour = 0;
						try {
							spacePricePerhour = Double.parseDouble(orderList.get(0).get("space_price_perhour")+"");
						} catch (Exception e) {
							e.printStackTrace();
						}
						double totalParkingFee = hour * spacePricePerhour;
						String parkName = orderList.get(0).get("park_name")+"";
						String spaceName = orderList.get(0).get("space_name")+"";
						
						respMap.put("orderId", orderId);//用于用户返回更新订单状态
						respMap.put("parkName", parkName);
						respMap.put("spaceName", spaceName);
						respMap.put("beginTime", beginTimeStr);
						respMap.put("endTime", endTimeStr);
						respMap.put("totalParkingFee", totalParkingFee);
						
						//step 3 更新订单状态
						SmartOrder smartOrder = new SmartOrder();
						smartOrder.setId(orderId);
//						smartOrder.setOrderStateId(SmartParkDictionary.ASK_OUT);
						smartOrder.setOrderStateId(SmartParkDictionary.orderState.ASK_OUT.ordinal());
						smartOrder.setBeginTime(beginTimeStr);
						smartOrder.setEndTime(endTimeStr);
						smartOrder.setReceivableAmount(totalParkingFee);
						smartParkService.updateSmartOrder(smartOrder);
					}
				}
			}
		} catch (Exception e) {
			msg = e.getMessage();
			e.printStackTrace();
		}
		return new AppJsonModel(code, ServerResult.getCodeMsg(code, msg), respMap);
	}
	

	@ApiOperation(value = "用户支付", notes = "用户收到服务器推送消息，在线支付应收费用，web页面支付完成后更新订单", httpMethod = "POST")
	@RequestMapping("onlinePay")
	@ResponseBody
	public AppJsonModel onlinePay(
			@ApiParam(name = "memberId", value = "会员编号", required = true) @RequestParam(value = "memberId", required = true) String memberId,
			@ApiParam(name = "orderId", value = "订单编号", required = true) @RequestParam(value = "orderId", required = true) String orderId,
			@ApiParam(name = "payWayId", value = "支付方式", required = true) @RequestParam(value = "payWayId", required = true) String payWayId,
			@ApiParam(name = "PaymentReceipt", value = "支付回执", required = true) @RequestParam(value = "PaymentReceipt", required = true) String PaymentReceipt
			){
		String msg = null;
		int code = 0;
		Map<String,Object> respMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		try {
			double actualAmount = 0;//
			//step1 校验订单编号和会员编号是否一致
			List<Map<String, Object>> chechList = smartParkService.checkOrderIdAndMemberId(orderId, memberId);
			if (chechList == null || chechList.size() == 0) {
				code = ServerResult.RESULT_ORDERID_MEMBERID_NOTMATCH_ERROR;
			}
			//step2 TODO 校验支付回执，并取出支付款数额，作为订单实付金额
			actualAmount = 0;//TODO
			//step3 根据订单编号更新订单
			if (code == 0) {
				SmartOrder smartOrder = new SmartOrder();
				smartOrder.setId(orderId);
				smartOrder.setPayWayId(payWayId);
				smartOrder.setActualAmount(actualAmount);
//				smartOrder.setOrderStateId(SmartParkDictionary.PAY_FINISHED);
				smartOrder.setOrderStateId(SmartParkDictionary.orderState.PAY_FINISHED.ordinal());
			} 
		} catch (Exception e) {
			msg = e.getMessage();
			e.printStackTrace();
		}
		return new AppJsonModel(code, ServerResult.getCodeMsg(code, msg), list);
	}
	
	@ApiOperation(value = "汽车驶入或驶出停车场地", notes = "由客户端识别汽车牌照", httpMethod = "POST")
	@RequestMapping("parking")
	@ResponseBody
	public AppJsonModel parking(
			@ApiParam(name = "carNumber", value = "准备驶入停车场汽车牌照编号，有客户端自动识别或手动填写", required = true) @RequestParam(value = "carNumber", required = false) String carNumber,
			@ApiParam(name = "parkId", value = "停车场编号", required = true) @RequestParam(value = "parkId", required = true) String parkId,
			@ApiParam(name = "spaceId", value = "停车位编号", required = false) @RequestParam(value = "spaceId", required = true) String spaceId,
			@ApiParam(name = "entranceId", value = "停车场入口编号", required = false) @RequestParam(value = "entranceId", required = true) String entranceId,
			@ApiParam(name = "parkingType", value = "停车类型，0：驶入 1：驶出", required = true) @RequestParam(value = "parkingType", required = true) int parkingType,
			@ApiParam(name = "description", value = "停车说明", required = false) @RequestParam(value = "description", required = false) int description
			){
		String msg = null;
		int code = 0;
		Map<String,Object> respMap = new HashMap<String, Object>();
		if(code == 0){
			try {
				//获取carid,
				String carId = smartCarService.getCarIdByNumber(carNumber);
				if(StringUtils.isBlank(carId)){//车辆信息不存在
					code = ServerResult.RESULT_CAR_NOT_REGIST_ERROR;
				}
				String orderId = "";
				String recordId = "";
				if(code == 0){
					int orderState = 10000;
					SmartOrder smartOrder = new SmartOrder();
					if(parkingType == 0){//驶入
						orderState = SmartParkDictionary.orderState.ASK_IN.ordinal();
						String format = "yyyy-MM-dd HH:mm:ss";
						String beginTime = DateHelper.paraseDateToString(new Date(), format);
						smartOrder.setId(orderId);
						smartOrder.setBeginTime(beginTime);
						smartOrder.setOrderStateId(SmartParkDictionary.orderState.CAR_PARKING.ordinal());
						
					}else{//驶出
						orderState = SmartParkDictionary.orderState.CAR_PARKING.ordinal();
						smartOrder.setId(orderId);
						smartOrder.setOrderStateId(SmartParkDictionary.orderState.ORDER_FINISHED.ordinal());
					}
					//step1 根据parkid和carid获取状态为“申请驶进停车场”订单信息，TODO 如何处理存在多条记录   如果获取不到，TODO 则走人工流程
					List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
					try {
						list = smartParkService.getOrderId(carId, parkId, orderState);
						if (list != null && list.size() > 0) {
							orderId = list.get(0).get("id") + "";
						} else {
							code = ServerResult.RESULT_ORDER_DONOT_EXSIT_ERROR;
						} 
					} catch (Exception e) {
						msg = e.getMessage();
						code = ServerResult.RESULT_SERVER_ERROR;
						e.printStackTrace();
					}
					//step2 创建停车出入场地记录
					if(code == 0){
						recordId = smartParkService.parkIngIn(carId, parkId, spaceId, entranceId,parkingType);
						if(StringUtils.isBlank(recordId)){
							code = ServerResult.RESULT_RECORD_CREATE_ERROR;
						}
					}
					//step3 更新订单状态
					if(code == 0){
						if(parkingType == 0){
							smartOrder.setRecordInId(recordId);
						}else{
							smartOrder.setRecordOutId(recordId);
						}
						smartParkService.updateSmartOrder(smartOrder);
					}
				}
			} catch (Exception e) {
				msg = e.getMessage();
				e.printStackTrace();
			}
		}
		respMap.put("parkId", parkId);
		return new AppJsonModel(code, ServerResult.getCodeMsg(code, msg), respMap);
	}
	
	
	@ApiOperation(value = "加载车辆信息", notes = "加载车辆信息", httpMethod = "POST")
	@RequestMapping("listCar")
	@ResponseBody
	public AppJsonModel listCar(){
		String msg = null;
		int code = 0;
		Map<String,Object> respMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		if(code == 0){
			try {
				list = smartCarService.listCar();
			} catch (Exception e) {
				msg = e.getMessage();
				e.printStackTrace();
			}
		}
		return new AppJsonModel(code, ServerResult.getCodeMsg(code, msg), list);
	}
	
	@ApiOperation(value = "加载停车场信息", notes = "加载停车场信息", httpMethod = "POST")
	@RequestMapping("listPark")
	@ResponseBody
	public AppJsonModel listPark(){
		String msg = null;
		int code = 0;
		Map<String,Object> respMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		if(code == 0){
			try {
				list = smartParkService.listPark();
			} catch (Exception e) {
				msg = e.getMessage();
				e.printStackTrace();
			}
		}
		return new AppJsonModel(code, ServerResult.getCodeMsg(code, msg), list);
	}
	
	@ApiOperation(value = "根据停车场地加载车位信息", notes = "根据停车场地加载车位信息", httpMethod = "POST")
	@RequestMapping("listSpaceByPark")
	@ResponseBody
	public AppJsonModel listSpaceByPark(
			@ApiParam(name = "parkId", value = "停车场地编号", required = true) @RequestParam(value = "parkId", required = true) String parkId
			){
		String msg = null;
		int code = 0;
		Map<String,Object> respMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		if(code == 0){
			try {
				list = smartParkService.listSpaceByParkId(parkId);
			} catch (Exception e) {
				msg = e.getMessage();
				e.printStackTrace();
			}
		}
		return new AppJsonModel(code, ServerResult.getCodeMsg(code, msg), list);
	}
	
}
