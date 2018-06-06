package com.xz.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.xz.entity.CustomConfig;
import com.xz.service.SmartMemberService;

@Component
public class MobileRemainTask {
	
	@Autowired
	private SmartMemberService smartMemberService;
	
	@Autowired  
    private CustomConfig customConfig; 
	
	/**
	 * 每天23:59时执行一次
	 * https://www.cnblogs.com/softidea/p/5833248.html
	 */
	@Scheduled(cron="0 59 23 * * ?")
//	@Scheduled(fixedRate = 1000)
	public void initRemainTime(){
		try {
			int times = Integer.parseInt(customConfig.getSendtimes());
			smartMemberService.initMobileRemain(times);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
