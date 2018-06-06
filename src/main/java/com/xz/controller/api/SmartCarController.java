package com.xz.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xz.common.ServerResult;
import com.xz.controller.BaseController;
import com.xz.controller.weixin.WeixinConstants;
import com.xz.model.json.JsonModel;
import com.xz.service.SmartCarService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("smartCar")
public class SmartCarController extends BaseController implements InitializingBean{
	
	
	@Autowired
	private SmartCarService smartCarService;
	
	
	@ApiOperation(value = "车辆信息注册", notes = "填写车辆信息", httpMethod = "POST")
	@RequestMapping("carRegist")
	@ResponseBody
	public JsonModel carRegist(
			 @ApiParam(name = "carId", value = "车辆编号", required = false) @RequestParam(value = "carId", required = false) String carId,
			 @ApiParam(name = "carNumber", value = "车牌号码", required = true) @RequestParam("carNumber") String carNumber,
			 @ApiParam(name = "carType", value = "车辆类型", required = true) @RequestParam("carType") String carType,
			 @ApiParam(name = "isOwn", value = "车辆是否属于会员本人，0：不属于，1：属于", required = false) @RequestParam(value = "isOwn", required = false) String isOwn,
			 @ApiParam(name = "carOwnerName", value = "车主姓名", required = false) @RequestParam(value = "carOwnerName", required = false) String carOwnerName,
			 @ApiParam(name = "carOwnerAddress", value = "车主联系地址", required = false) @RequestParam(value = "carOwnerAddress", required = false) String carOwnerAddress,
			 @ApiParam(name = "carOwnerPhone", value = "车主联系方式", required = false) @RequestParam(value = "carOwnerPhone", required = false) String carOwnerPhone
			){
		String msg = null;
		int code = 0;
		Map<String,String> respMap = new HashMap<String, String>();
		/*
		if(StringUtils.isBlank(isOwn) && StringUtils.isBlank(carOwnerName)){
			code = ServerResult.RESULT_OWNERINFO_ERROR;
		}
		*/
		try {
			//TODO 统一检测参数 memberId
			HttpSession session = getRequest().getSession();
			String memberId = (String) session.getAttribute(WeixinConstants.SESSION_MEMBER_ID);
			if (StringUtils.isBlank(memberId)) {
				code = ServerResult.RESULT_AUTH_VALIDATE_ERROR;
			}
			if(code == 0){//检查会员名下车辆总数
				if(StringUtils.isBlank(carId)){//更新汽车牌号是不需要校验数量，需要校验是否属于该会员
					List<Map<String, Object>> list = smartCarService.checkMaxCar(memberId);
					if(list != null && list.size() >= 3){
						code = ServerResult.RESULT_MAX_CAR_ERROR;
					}
				}else{
					if(!smartCarService.checkCarIsOwnMember(carId, memberId)){//汽车不属于该会员拥有，则不能更新
						code = ServerResult.RESULT_CAR_AUTH_VALIDATE_ERROR;
					}
				}
			}
			//注册车主信息
			if (code == 0) {
				String ownerId = "";
				//会员即是车主
				if (StringUtils.isBlank(isOwn)) {
//					ownerId = smartCarService.ownerIsMemberRegist(memberId);
					ownerId = memberId;
				} else {
					ownerId = smartCarService.ownerRegist(carOwnerName, carOwnerAddress, carOwnerPhone);
				}
				if (StringUtils.isNotBlank(ownerId)) {
					int carTypeInt = Integer.parseInt(carType);
					String savedCarId = smartCarService.carRegist(memberId, carNumber, ownerId, carTypeInt,carId);
				}
			} 
		} catch (Exception e) {
			code = ServerResult.RESULT_SERVER_ERROR;
			msg = e.getMessage();
			e.printStackTrace();
		}
		return new JsonModel(code, ServerResult.getCodeMsg(code, msg), respMap);
	}
	@ApiOperation(value = "车辆信息删除", notes = "车辆信息删除", httpMethod = "POST")
	@RequestMapping("carDelete")
	@ResponseBody
	public JsonModel carDelete(
			@ApiParam(name = "carId", value = "车辆编号", required = true) @RequestParam(value = "carId", required = true) String carId
			){
		String msg = null;
		int code = 0;
		Map<String,String> respMap = new HashMap<String, String>();
		try {
			//TODO 统一检测参数 memberId
			HttpSession session = getRequest().getSession();
			String memberId = (String) session.getAttribute(WeixinConstants.SESSION_MEMBER_ID);
			if (StringUtils.isBlank(memberId)) {
				code = ServerResult.RESULT_AUTH_VALIDATE_ERROR;
			}
			//检查该车辆是否是该会员名下，防止请求被篡改
			if(code == 0){
				if(!smartCarService.checkCarOwner(memberId,carId)){
					code  = ServerResult.RESULT_CAR_VALIDATE_ERROR;
				}
			}
			//校验无误后进行删除
			if(code == 0){
				smartCarService.deleteCarById(carId);
			}
			
		} catch (Exception e) {
			code = ServerResult.RESULT_SERVER_ERROR;
			msg = e.getMessage();
			e.printStackTrace();
		}
		return new JsonModel(code, ServerResult.getCodeMsg(code, msg), respMap);
	}
	@ApiOperation(value = "加载会员名下车辆信息", notes = "加载会员名下车辆信息", httpMethod = "POST")
	@RequestMapping("loadCarInfo")
	@ResponseBody
	public JsonModel loadCarInfo(){
		String msg = null;
		int code = 0;
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			//统一检测参数 memberId
			HttpSession session = getRequest().getSession();
			String memberId = (String) session.getAttribute(WeixinConstants.SESSION_MEMBER_ID);
			if (StringUtils.isBlank(memberId)) {
				code = ServerResult.RESULT_AUTH_VALIDATE_ERROR;
			}
			if(code == 0){
				list = smartCarService.getCarListByMemberId(memberId);
			}
		} catch (Exception e) {
			code = ServerResult.RESULT_SERVER_ERROR;
			msg = e.getMessage();
			e.printStackTrace();
		}
		return new JsonModel(code, ServerResult.getCodeMsg(code, msg), list);
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		
	}
	
	
	
}
