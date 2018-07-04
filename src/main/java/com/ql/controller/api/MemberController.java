package com.ql.controller.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ql.common.ServerResult;
import com.ql.controller.BaseController;
import com.ql.controller.weixin.WeixinConstants;
import com.ql.model.json.JsonModel;
import com.ql.service.SmartMemberService;
import com.ql.utils.DateHelper;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("member")
public class MemberController extends BaseController{
	
	@Autowired
	private SmartMemberService smartMemberService;
	
	
	@ApiOperation(value = "会员消费记录", notes = "会员消费记录", httpMethod = "POST")
	@RequestMapping("consume")
	@ResponseBody
	public JsonModel memberRegist(
			){
		String msg = null;
		int code = 0;
//		Map<String,Object> respMap = new HashMap<String, Object>();
		List<Map<String, Object>> resp = new ArrayList<Map<String,Object>>();
		HttpSession session = getRequest().getSession();
		String sessionOpenId = (String)session.getAttribute(WeixinConstants.SESSION_WEIXIN_OPEN_ID);
		if(StringUtils.isBlank(sessionOpenId)){
			code = ServerResult.RESULT_AUTH_VALIDATE_ERROR;
		}
		if(code == 0){
			resp = smartMemberService.getMemebrConsumeInfo(sessionOpenId);
			System.out.println(resp);
			Map<String, Object> tMap = new HashMap<String, Object>();
			/*
			if(tempList != null && tempList.size()>0){
				for(int i=0;i<tempList.size();i++){
					tMap = new HashMap<String, Object>();
					tMap = tempList.get(i);
					long time = Long.parseLong((tMap.get("inputdatetime")+""));
					Date date = new Date();  
				    date.setTime(time);
				    String dateStr = DateHelper.paraseDateToString(date, "yyyy年MM月dd日 HH时mm分ss秒");
				    tMap.put("inputdatetime", dateStr);
				    resp.add(tMap);
				}
			}
			*/
		}
		
		return new JsonModel(code, ServerResult.getCodeMsg(code, msg), resp);
	}
	
}
