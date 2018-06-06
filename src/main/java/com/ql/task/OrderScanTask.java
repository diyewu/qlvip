package com.ql.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ql.controller.weixin.WeiXinSendTemplateThread;
import com.ql.service.OrderService;

@Component
public class OrderScanTask {
	
	@Autowired
	private OrderService orderService;
	
	private static String orderNo = "";
	/**
	 * 每天30秒执行一次
	 * https://www.cnblogs.com/softidea/p/5833248.html
	 */
	@Scheduled(cron="30 * * * * ?")
//	@Scheduled(fixedRate = 1000)
	public void initRemainTime(){
		try {
			System.out.println("init orderNo = " +orderNo);
			if(StringUtils.isBlank(orderNo)){
				orderNo = orderService.getLastOneOrder();
			}else{
				List<Map<String, Object>> list = orderService.getLastestOrder(orderNo);
				if( list != null && list.size() > 0 ){//会员有新订单，微信发送通知
					for(int i=0;i<list.size();i++){
						if(orderNo.compareTo(list.get(i).get("order_no")+"") < 0){
							orderNo = list.get(i).get("order_no")+"";
						}
					}
					Thread thread = new WeiXinSendTemplateThread(list);//多线程处理，尽量不影响下一次
					thread.start();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
