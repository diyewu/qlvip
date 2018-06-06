package com.ql.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ql.entity.CustomConfig;
import com.ql.service.SmartMemberService;

@Component
public class MobileRemainTask {
	
	@Autowired
	private SmartMemberService smartMemberService;
	
	/**
	 * 每天30秒执行一次
	 * https://www.cnblogs.com/softidea/p/5833248.html
	 */
	@Scheduled(cron="30 * * * * ?")
//	@Scheduled(fixedRate = 1000)
	public void initRemainTime(){
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
