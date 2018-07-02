package com.ql.controller.weixin.pay;

import java.util.Map;

import com.ql.common.SmartParkDictionary;
import com.ql.config.weixin.WeixinConfig;
import com.ql.entity.SmartOrder;
import com.ql.service.SmartOrderService;

//@Component
public class PayOrderQueryProcess extends Thread  {
	private boolean isSandbox;
	private Map<String, String> queryMap;
	private WeixinConfig config;
//	private static ApplicationContext applicationContext;
	private static int count = 0;
	
//	@Autowired  
	private SmartOrderService smartOrderService;

	PayOrderQueryProcess(SmartOrderService smartOrderService,WeixinConfig config, Map<String, String> queryMap, boolean isSandbox) {
		super();
		this.config = config;
		this.queryMap = queryMap;
		this.isSandbox = isSandbox;
		this.smartOrderService = smartOrderService;
	}

	/**
	 * 查询订单任务
	 * 不论查询结果如何，最多只查询10次，如果中间查到已经支付成功，同样跳出任务。如果查询了10次以后扔为查询到支付成功结果，则以微信通知为准。
	 */
	@Override
	public void run() {
		try {
			System.out.println("___________in____________");
			count++;
			Map<String, String> resultQueryMap = WeixinPayHelper.orderQuery(config, queryMap, isSandbox);
			smartOrderService.insertIntoWeixinPayDetail(queryMap.get("out_trade_no"),
					SmartParkDictionary.weixinPayProgress.查询订单结果.ordinal(), resultQueryMap);
			System.out.println("resultQueryMap=" + resultQueryMap);
			if (resultQueryMap != null) {
				boolean queryFlag = false;
				if ("SUCCESS".equalsIgnoreCase(resultQueryMap.get("return_code"))) {
					if ("SUCCESS".equalsIgnoreCase(resultQueryMap.get("result_code"))) {
						/**
						 * SUCCESS—支付成功 REFUND—转入退款 NOTPAY—未支付 CLOSED—已关闭
						 * REVOKED—已撤销（刷卡支付） USERPAYING--用户支付中
						 * PAYERROR--支付失败(其他原因，如银行返回失败)
						 */
						if ("SUCCESS".equalsIgnoreCase(resultQueryMap.get("trade_state"))) {
							// 校验sign
							if (WeixinPayHelper.isPayResultNotifySignatureValid(config, isSandbox, resultQueryMap)) {
								// TODO 更新订单状态，通知用户支付状态,先查询订单状态再更新，防止重复更新
								// step 1: 校验订单信息是否一致
								if (smartOrderService.checkOrderInfo(resultQueryMap.get("out_trade_no"),
										Double.parseDouble(resultQueryMap.get("total_fee")) / 100,
										resultQueryMap.get("openid"))) {// 订单校验一致，更新数据库，订单状态为已支付，并更新‘微信订单号’
									queryFlag = true;// 跳出任务标志
									SmartOrder smartOrder = new SmartOrder();
									smartOrder.setId(resultQueryMap.get("out_trade_no"));
									smartOrder.setOrderStateId(SmartParkDictionary.orderState.PAY_FINISHED.ordinal());
									smartOrder.setTransactionId(resultQueryMap.get("transaction_id"));
									smartOrderService.updateSmartOrder(smartOrder);

								}
							} else {
								System.out.println("sign校验失败！");
							}
						}
					} else {
						System.out.println("查询订单失败：" + resultQueryMap);
					}
				} else {
					System.out.println("查询订单失败：" + resultQueryMap);
				}
				if (queryFlag || count > 10) {
					System.out.println("___________out____________");
					throw new RuntimeException("查询订单结束");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

//	@Override
//	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//		PayOrderQueryProcess.applicationContext = applicationContext;
//	}
//
//	public static ApplicationContext getApplicationContext() {
//		return applicationContext;
//	}
//
//	/**
//	 * 获取对象 这里重写了bean方法，起主要作用
//	 */
//	public static Object getBean(String beanId) throws BeansException {
//		return applicationContext.getBean(beanId);
//	}

}
