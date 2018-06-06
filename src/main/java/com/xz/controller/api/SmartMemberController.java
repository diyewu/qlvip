package com.xz.controller.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xz.common.ServerResult;
import com.xz.controller.BaseController;
import com.xz.model.json.AppJsonModel;
import com.xz.service.SmartMemberService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("smartMember")
public class SmartMemberController extends BaseController{
	
	@Autowired
	private SmartMemberService smartMemberService;
	
	
	@ApiOperation(value = "会员注册", notes = "会员注册,输入姓名、性别", httpMethod = "POST")
	@RequestMapping("memberRegist")
	@ResponseBody
	public AppJsonModel memberRegist(
			 @ApiParam(name = "memberName", value = "会员名称", required = true) @RequestParam("memberName") String memberName,
			 @ApiParam(name = "memberSex", value = "会员性别", required = true) @RequestParam("memberSex") String memberSex
			){
		String msg = null;
		int memberSexInt = Integer.parseInt(memberSex);
		String memberId = "";
		int code = 0;
		try {
			memberId = smartMemberService.memberRegist(memberName, memberSexInt);
		} catch (Exception e) {
			msg = e.getMessage();
			e.printStackTrace();
			code = 1;
		}
		Map<String,String> respMap = new HashMap<String, String>();
		respMap.put("memberId", memberId);
		
		return new AppJsonModel(code, ServerResult.getCodeMsg(code, msg), respMap);
	}
	
	@ApiOperation(value = "微信公众号根据openid检查会员是否注册", notes = "判断依据为是否有手机号码", httpMethod = "POST")
	@RequestMapping("checkMemberByOpenId")
	@ResponseBody
	public AppJsonModel checkMemberByOpenId(
			@ApiParam(name = "openId", value = "微信openId", required = true) @RequestParam("openId") String openId
			){
		String msg = null;
		Map<String,String> respMap = new HashMap<String, String>();
		int code = 0;
		
		return new AppJsonModel(code, ServerResult.getCodeMsg(code, msg), respMap);
	}
	
	
	
}
