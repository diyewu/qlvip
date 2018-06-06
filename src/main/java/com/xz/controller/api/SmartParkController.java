package com.xz.controller.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import com.xz.entity.LngLat;
import com.xz.entity.SmartPark;
import com.xz.entity.SmartSpace;
import com.xz.model.json.AppJsonModel;
import com.xz.model.json.JsonModel;
import com.xz.service.SmartParkService;
import com.xz.utils.AMapUtils;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("smartPark")
public class SmartParkController extends BaseController implements InitializingBean{
	
	/**
	 * 更新停车场信息时注意更新该数据,考虑到成员变量线程不安全问题和场地信息变化较少，故为了效率暂时可以忽略线程不安全问题。
	 */
	private static List<SmartPark> parkList = new ArrayList<SmartPark>();
	
	@Autowired
	private SmartParkService smartParkService;
	
	
	@ApiOperation(value = "场地信息注册", notes = "填写场地信息", httpMethod = "POST")
	@RequestMapping("parkRegist")
	@ResponseBody
	public AppJsonModel parkRegist(
			 @ApiParam(name = "memberId", value = "场地管理员编号，可不填，场地管理员或后台操作管理员不能同时为空", required = false) @RequestParam(value = "memberId", required = false) String memberId,
			 @ApiParam(name = "operateUserId", value = "后台操作管理员编号，可不填，场地管理员或后台操作管理员不能同时为空", required = false) @RequestParam(value = "operateUserId", required = false) String operateUserId,
			 @ApiParam(name = "parkName", value = "停车场地名称", required = true) @RequestParam(value = "parkName", required = true) String parkName,
			 @ApiParam(name = "parkLongitude", value = "停车场地经度", required = true) @RequestParam(value ="parkLongitude", required = true) String parkLongitude,
			 @ApiParam(name = "parkLatitude", value = "停车场地维度", required = true) @RequestParam(value = "parkLatitude", required = false) String parkLatitude,
			 @ApiParam(name = "parkDescription", value = "停车场地描述", required = false) @RequestParam(value = "parkDescription", required = false) String parkDescription
			){
		String msg = null;
		int code = 0;
		Map<String,Object> respMap = new HashMap<String, Object>();
		if(StringUtils.isBlank(memberId) && StringUtils.isBlank(operateUserId)){
			code = ServerResult.RESULT_PARK_BLANK_ERROR;
		}
		String parkId = "";
		//注册场地信息
		if(code == 0){
			try {
				parkId = smartParkService.registPark(parkName, parkDescription, parkLongitude, parkLatitude, memberId,
						operateUserId);
			} catch (Exception e) {
				msg = e.getMessage();
				e.printStackTrace();
			}
		}
		//重新加载停车场缓存数据
		if(code == 0){
			initParkListCache();
		}
		respMap.put("parkId", parkId);
		return new AppJsonModel(code, ServerResult.getCodeMsg(code, msg), respMap);
	}
	
	@ApiOperation(value = "场地入口信息注册", notes = "填写场地入口信息", httpMethod = "POST")
	@RequestMapping("parkEntranceRegist")
	@ResponseBody
	public AppJsonModel parkEntranceRegist(
			@ApiParam(name = "parkId", value = "停车场地编号", required = true) @RequestParam(value = "parkId", required = true) String parkId,
			@ApiParam(name = "entranceName", value = "场地入口名称", required = true) @RequestParam(value ="entranceName", required = true) String entranceName,
			@ApiParam(name = "entranceLongitude", value = "停车场地入口经度", required = true) @RequestParam(value = "entranceLongitude", required = false) String entranceLongitude,
			@ApiParam(name = "entranceLatitude", value = "停车场地入口纬度", required = true) @RequestParam(value = "entranceLatitude", required = false) String entranceLatitude,
			@ApiParam(name = "entranceDescription", value = "停车场地入口描述", required = false) @RequestParam(value = "entranceDescription", required = false) String entranceDescription
			){
		String msg = null;
		int code = 0;
		Map<String,String> respMap = new HashMap<String, String>();
		String entranceId = "";
		//注册场地入口信息
		if(code == 0){
			try {
				entranceId = smartParkService.registParkEntrance(parkId, entranceName, entranceLongitude,
						entranceLatitude, entranceDescription);
			} catch (Exception e) {
				msg = e.getMessage();
				e.printStackTrace();
			}
		}
		//重新加载停车场缓存数据
		if(code == 0){
			initParkListCache();
		}
		respMap.put("entranceId", entranceId);
		return new AppJsonModel(code, ServerResult.getCodeMsg(code, msg), respMap);
	}
	
	@ApiOperation(value = "场地车位信息注册", notes = "填写场地入口信息", httpMethod = "POST")
	@RequestMapping("parkSpaceRegist")
	@ResponseBody
	public AppJsonModel parkSpaceRegist(
			@ApiParam(name = "parkId", value = "停车场地编号", required = true) @RequestParam(value = "parkId", required = true) String parkId,
			@ApiParam(name = "spaceType", value = "场地车位类型", required = true) @RequestParam(value ="spaceType", required = true) String spaceType,
			@ApiParam(name = "spaceTotal", value = "场地该类型车位总数", required = true) @RequestParam(value = "spaceTotal", required = true) int spaceTotal,
			@ApiParam(name = "spaceUsed", value = "场地该类型车位已占用数", required = true) @RequestParam(value = "spaceUsed", required = true) int spaceUsed,
			@ApiParam(name = "spacePricePerhour", value = "场地该类型车位每小时停车费用", required = false) @RequestParam(value = "spacePricePerhour", required = false) double spacePricePerhour,
			@ApiParam(name = "spaceDescription", value = "场地该类型车位描述", required = false) @RequestParam(value = "spaceDescription", required = false) String spaceDescription
			){
		String msg = null;
		int code = 0;
		Map<String,String> respMap = new HashMap<String, String>();
		String spaceId = "";
		//注册车位信息
		if(code == 0){
			spaceId = smartParkService.registParkSpace(parkId, spaceType, spaceTotal, spaceUsed, spacePricePerhour, spaceDescription);
		}
		//重新加载停车场缓存数据
		if(code == 0){
			initParkListCache();
		}
		respMap.put("spaceId", spaceId);
		return new AppJsonModel(code, ServerResult.getCodeMsg(code, msg), respMap);
	}
	
	
	@ApiOperation(value = "根据位置获取停车场地列表,数据根据距离排序", notes = "根据位置获取停车场地列表", httpMethod = "POST")
	@RequestMapping("getParkListByGeo")
	@ResponseBody
	public JsonModel getParkListByGeo(
			@ApiParam(name = "lng", value = "位置经度", required = true) @RequestParam(value = "lng", required = true) double lng,
			@ApiParam(name = "lat", value = "位置维度", required = true) @RequestParam(value ="lat", required = true) double lat
			){
		String msg = null;
		int code = 0;
		List<SmartPark> respParkList = new ArrayList<SmartPark>();
		try {
			//统一检测参数 memberId
			HttpSession session = getRequest().getSession();
			String memberId = (String) session.getAttribute(WeixinConstants.SESSION_MEMBER_ID);
			if (StringUtils.isBlank(memberId)) {
				code = ServerResult.RESULT_AUTH_VALIDATE_ERROR;
			}
			if(parkList == null || parkList.size() == 0){
				parkList = smartParkService.getParkList(null);
			}
			respParkList = processParkList(lng, lat, parkList);
			Collections.sort(respParkList);
		} catch (Exception e) {
			code = ServerResult.RESULT_SERVER_ERROR;
			msg = e.getMessage();
			e.printStackTrace();
		}
		return new JsonModel(code, ServerResult.getCodeMsg(code, msg), respParkList);
	}
	@ApiOperation(value = "根据关键字获取停车场地列表,数据根据距离排序", notes = "根据关键字获取停车场地列表", httpMethod = "POST")
	@RequestMapping("getParkListByKeyWord")
	@ResponseBody
	public JsonModel getParkListByKeyWord(
			@ApiParam(name = "keyWord", value = "关键字", required = true) @RequestParam(value = "keyWord", required = true) String keyWord,
			@ApiParam(name = "lng", value = "我的位置经度，用来计算距离", required = true) @RequestParam(value = "lng", required = true) double lng,
			@ApiParam(name = "lat", value = "我的位置纬度，用来计算距离", required = true) @RequestParam(value ="lat", required = true) double lat
			){
		String msg = null;
		int code = 0;
		List<SmartPark> respParkList = new ArrayList<SmartPark>();
		try {
			//统一检测参数 memberId
			HttpSession session = getRequest().getSession();
			String memberId = (String) session.getAttribute(WeixinConstants.SESSION_MEMBER_ID);
			if (StringUtils.isBlank(memberId)) {
				code = ServerResult.RESULT_AUTH_VALIDATE_ERROR;
			}
			List<SmartPark> list = smartParkService.getParkList(keyWord);
			respParkList = processParkList(lng, lat, list);
			Collections.sort(respParkList);
		} catch (Exception e) {
			code = ServerResult.RESULT_SERVER_ERROR;
			msg = e.getMessage();
			e.printStackTrace();
		}
		return new JsonModel(code, ServerResult.getCodeMsg(code, msg), respParkList);
	}
	
	
	
	
	
	
	public static List<SmartPark> processParkList(double lng,double lat,List<SmartPark> sourceList){
		double dbLng = 0;
		double dbLat = 0;
		List<SmartPark> respParkList = new ArrayList<SmartPark>();
		LngLat start = new LngLat(lng, lat);
		SmartPark sp = new SmartPark();
		if(sourceList != null){
			for (int i = 0; i < sourceList.size(); i++) {
				sp = new SmartPark();
				sp = sourceList.get(i);
				dbLng = sp.getLongitude();
				dbLat = sp.getLatitude();
				LngLat end = new LngLat(dbLng, dbLat);
				double distance = AMapUtils.calculateLineDistance(start, end);
				sp.setDistance(distance);
				respParkList.add(sp);
			}
		}
		return respParkList;
	}
	

	private static int count = 0;
	/**
	 * 重新加载停车场缓存数据
	 */
	public void initParkListCache(){
		parkList = new ArrayList<SmartPark>();
		parkList = smartParkService.getParkList(null);
		if(parkList != null){
			List<SmartSpace> list = new ArrayList<SmartSpace>();
			for(int i = 0;i<parkList.size();i++){
				list = new ArrayList<SmartSpace>();
				list = smartParkService.getParkSpaceByParkId(parkList.get(i).getId());
				parkList.get(i).setSpaceList(list);
				if(list != null){
					int remain = 0;
					double min = 0;
					double max = 0;
					for(int k=0;k<list.size();k++){
						if(k == 0){
							min = max = list.get(k).getSpacePricePerhour();
						}
						remain += (list.get(k).getSpaceTotal()-list.get(k).getSpaceUsed());
						if(list.get(k).getSpacePricePerhour()>=max){
							max = list.get(k).getSpacePricePerhour();
						}
						if(list.get(k).getSpacePricePerhour()<=min){
							min = list.get(k).getSpacePricePerhour();
						}
					}
					if(min == max){
						parkList.get(i).setSpacePricePerhour(min+"");
					}else{
						parkList.get(i).setSpacePricePerhour(min+"-"+max);
					}
					parkList.get(i).setFreeSpace(remain);
				}
			}
		}
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		if (count == 0) {
			initParkListCache();
		}
		count++;
	}
	
}
