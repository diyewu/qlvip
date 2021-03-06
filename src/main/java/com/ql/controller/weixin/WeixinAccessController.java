package com.ql.controller.weixin;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ql.common.ServerResult;
import com.ql.controller.BaseController;
import com.ql.entity.CustomConfig;
import com.ql.entity.SmartMember;
import com.ql.model.json.JsonModel;
import com.ql.service.OrderService;
import com.ql.service.SmartMemberService;
import com.ql.utils.SMSUtil;
import com.ql.utils.SignUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RequestMapping("wechat")
@RestController
public class WeixinAccessController extends BaseController{
	@Autowired
	private SmartMemberService smartMemberService;
	@Autowired
	private OrderService orderService;
	@Autowired  
    private CustomConfig customConfig; 
	
	private final String smsTemplate = "【芊乐零食屋】尊敬的用户：您的验证码为：$code（60分钟内有效），为了保证您的账户安全，请勿向任何人提供此验证码。";
	
    private static final Logger LOGGER = LoggerFactory.getLogger(WeixinAccessController.class);
	/**
	 * weixin绑定服务器
	 * @param request
	 * @return
	 * @throws IOException 
	 */
    @RequestMapping(value="access",method = RequestMethod.GET)
	@ResponseBody
	public void bindWeixinServer(HttpServletRequest request, HttpServletResponse response) throws IOException{
	        try {
	                String signature = request.getParameter("signature");// 微信加密签名  
	                String timestamp = request.getParameter("timestamp");// 时间戳  
	                String nonce = request.getParameter("nonce");// 随机数  
	                String echostr = request.getParameter("echostr");//随机字符串  
	                if(SignUtil.checkSignature(customConfig.getWeixintoken(), signature, timestamp, nonce)){
	                // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败    
	                    LOGGER.info("Connect the weixin server successful.");
	                    this.printData(response, echostr);
	                } else {  
	                    LOGGER.error("Failed to verify the signature!"); 
	                    this.printData(response, "error!");
	                }
	        } catch (Exception e) {
	            LOGGER.error("Connect the weixin server error.");
	        }finally{
	//            out.close();
	        }
	}
    /**
     * weixin连接服务器
     * @param request
     * @return
     * @throws IOException 
     */
    @RequestMapping(value="access",method = RequestMethod.POST)
    @ResponseBody
    public void weixinCommunicate(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	System.out.println("__________________________come in____");
    	try {
    		String resp = WeixinHelper.processRequest(request);
    		System.out.println("___________________________resp="+resp);
    		this.printData(response, resp);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}finally{
    		
    	}
    }
    
    /**
     * { "access_token":"ACCESS_TOKEN",
		"expires_in":7200,
		"refresh_token":"REFRESH_TOKEN",
		"openid":"OPENID",
		"scope":"SCOPE" }
     * @param authCode
     */
    @ApiOperation(value = "根据微信网页授权code获取openId", notes = "根据微信网页授权code获取openId", httpMethod = "POST")
    @RequestMapping(value="getMemberInfoByCode",method = RequestMethod.POST)
    @ResponseBody
    public JsonModel getMemberInfoByCode(
    		@ApiParam(name = "authCode", value = "用户同意授权，获取code", required = true) @RequestParam("authCode") String authCode,
    		@ApiParam(name = "authOpenId", value = "测试用参数，authOpenId", required = false) @RequestParam(value = "authOpenId", required = false) String authOpenId
    		){
    	System.out.println("authCode="+authCode);
//    	return null;
    	
    	String msg = null;
		int code = 0;
		Map<String,Object> map = new HashMap<String, Object>();
		Map<String,String> respMap = new HashMap<String, String>();
		try {
			String openId = "";
			HttpSession session = getRequest().getSession(); 
			if(StringUtils.isBlank(authOpenId)){
				if(StringUtils.isNotBlank(authCode)){
					respMap = WeixinHelper.getWebAuthOpenIdAndAccessToken(customConfig.getAppid(), customConfig.getSecret(), authCode);
					openId = respMap.get(WeixinConstants.WEIXIN_OPEN_ID);
				}
			}else{
				openId = authOpenId;
			}
			if(StringUtils.isBlank(openId)){
				openId = (String)session.getAttribute(WeixinConstants.SESSION_WEIXIN_OPEN_ID);
			}
			System.out.println("openId="+openId);
			if(StringUtils.isBlank(openId)){
				code = ServerResult.RESULT_AUTH_VALIDATE_ERROR;
			}else{
				session.setAttribute(WeixinConstants.SESSION_WEIXIN_OPEN_ID, openId);
			}
			if(code == 0){
				// 得到openid走业务逻辑，如果数据库中存在则查询数据，如果没有绑定手机号
				List<Map<String, Object>> list = orderService.getMemberInfoByOpenId(openId);
				if(list != null && list.size()>0){//存在,则停留在这里,并展示相关信息
					map.put("exist_state", "1");
					map.putAll(list.get(0));
				}else{//不存在，跳转到手机注册页面
					map.put("exist_state", "0");
				}
			}
		} catch (Exception e) {
			code = ServerResult.RESULT_SERVER_ERROR;
			msg = e.getMessage();
			e.printStackTrace();
		}
		return new JsonModel(code, ServerResult.getCodeMsg(code, msg), map);
    }
    
    
    /**
     * 发送手机验证码
     * @param request
     * @return
     * @throws IOException 
     */
    @ApiOperation(value = "发送手机验证码", notes = "发送手机验证码", httpMethod = "POST")
    @RequestMapping(value="sendValidateMobileCode",method = RequestMethod.POST)
    @ResponseBody
    public JsonModel sendValidateMobileCode(
    		@ApiParam(name = "mobileNumber", value = "手机号码", required = true) @RequestParam("mobileNumber") String mobileNumber
    		) throws IOException{
    	String msg = null;
		int code = 0;
		Map<String,Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		HttpSession session = getRequest().getSession();
    	try {
			//校验手机号码
			boolean isMobile = isMobile(mobileNumber);
			int mobileCode = (int) ((Math.random() * 9 + 1) * 100000);
			String mobileStr = String.valueOf(mobileCode).replaceAll("4","6").replaceAll("7","8");
			String content= smsTemplate.replace("$code", mobileStr);
			if (!isMobile) {
				code = ServerResult.RESULT_MOBILE_VALIDATE_ERROR;
			}
			//校验是否有 openId
			if (code == 0) {
				String openId = (String)session.getAttribute(WeixinConstants.SESSION_WEIXIN_OPEN_ID);
				if(StringUtils.isBlank(openId)){
					code = ServerResult.RESULT_AUTH_VALIDATE_ERROR;
				}
			}
			
			//校验该手机号码是否已经是线下会员
			if (code == 0) {
				if(!orderService.checkMobileIsOffline(mobileNumber)){
					code = ServerResult.RESULT_MEMBER_CHECK_ERROR;
				}
			}
			//检查是否已经绑定，已经绑定则无需重新绑定
			if (code == 0) {
				if(orderService.checkBind(mobileNumber)){
					code = ServerResult.RESULT_MEMBER_REPEAT_BIND_ERROR;
				}
			}
			
			//校验当天剩余次数
			if (code == 0) {
				list = smartMemberService.getLastSendCodeTime(mobileNumber);
				if (list != null && list.size() > 0) {//数据库中存在mobile数据
					int remainTimes = (Integer) list.get(0).get("remain_time");
					if (remainTimes > 0) {//剩余次数大于0，继续校验上一次发送时间
						String lastSendCodeTime = (String) list.get(0).get("last_send_time");
						if (StringUtils.isNotBlank(lastSendCodeTime)) {
							DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							try {
								Date d1 = df.parse(lastSendCodeTime);
								Date nowTiem = new Date();
								long diff = nowTiem.getTime() - d1.getTime();// 这样得到的差值是微秒级别
								long second = diff / 1000;
								if (second > 120) {//2分钟之后才能继续发送验证码
									//TODO  发送验证码
									try {
										//插入数据库
										smartMemberService.updateMobileCodeSend(mobileNumber, Integer.parseInt(mobileStr));
										SMSUtil.sendSMS(mobileNumber, content);
										session.setAttribute(WeixinConstants.SESSION_WEIXIN_USER_MOBILE, mobileNumber);
										System.out.println("发送成功----"+mobileNumber);
										session.setAttribute(WeixinConstants.SESSION_MOBILE_VALIDATE_CODE, mobileStr);
										System.out.println("发验证码----"+mobileStr);
									} catch (Exception e) {
										code = ServerResult.RESULT_SERVER_ERROR;
										msg = e.getMessage();
										e.printStackTrace();
									}
								} else {
									code = ServerResult.RESULT_MOBILE_CODE_SEND_INTERVAL_ERROR;
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} else {
						code = ServerResult.RESULT_MOBILE_CODE_SEND_REMAINTIMES_ERROR;
					}
				} else {
					//TODO  第一次发送验证码，则直接发送，并存储数据库,存储次数为9
					try {
						SMSUtil.sendSMS(mobileNumber, content);
						session.setAttribute(WeixinConstants.SESSION_WEIXIN_USER_MOBILE, mobileNumber);
						System.out.println("发送成功--11--"+mobileNumber);
						session.setAttribute(WeixinConstants.SESSION_MOBILE_VALIDATE_CODE, mobileStr);
						System.out.println("发验证码--11--"+mobileStr);
					} catch (Exception e) {
						e.printStackTrace();
					}
					smartMemberService.insertMobileCodeSend(mobileNumber, Integer.parseInt(mobileStr), 9);
				}
			} 
		} catch (Exception e) {
			code = ServerResult.RESULT_SERVER_ERROR;
			msg = e.getMessage();
			e.printStackTrace();
		}
    	if(StringUtils.isNotBlank(msg)){
			msg = "o(╯□╰)o,短信丢失了,请联系店员";
		}
    	return new JsonModel(code, ServerResult.getCodeMsg(code, msg), map);
    }
    
    
    @ApiOperation(value = "检查手机验证码", notes = "检查手机验证码", httpMethod = "POST")
    @RequestMapping(value="checkMobileCode",method = RequestMethod.POST)
    @ResponseBody
    public JsonModel checkMobileCode(
    		@ApiParam(name = "mobileValidateCode", value = "手机验证码", required = true) @RequestParam("mobileValidateCode") String mobileValidateCode
    		) throws IOException{
    	String msg = null;
		int code = 0;
		Map<String,Object> map = new HashMap<String, Object>();
    	HttpSession session = getRequest().getSession();
    	try {
			String mobile = (String) session.getAttribute(WeixinConstants.SESSION_WEIXIN_USER_MOBILE);
			String openId = (String) session.getAttribute(WeixinConstants.SESSION_WEIXIN_OPEN_ID);
			String sessionValidateCode = session.getAttribute(WeixinConstants.SESSION_MOBILE_VALIDATE_CODE)+"";
			session.setAttribute(WeixinConstants.SESSION_MOBILE_VALIDATE_CODE, "");
			String memberId = "";
			System.out.println("openId="+openId);
			System.out.println("mobile="+mobile);
			System.out.println("sessionValidateCode="+sessionValidateCode);
			if (StringUtils.isBlank(sessionValidateCode)) {
				code = ServerResult.RESULT_MOBILE_SESSION_CODE_VALIDATE_ERROR;
			}
			if (code == 0) {
				if (StringUtils.isNotBlank(mobile)) {
					if (!sessionValidateCode.equals(mobileValidateCode)) {
						code = ServerResult.RESULT_MOBILE_CODE_VALIDATE_ERROR;
					}
				} else {
					code = ServerResult.RESULT_SESSION_MOBILE_CHECK_ERROR;
				}
			}
			//手机验证码验证成功之后，插入会员信息
			if (code == 0) {
				//根据openid获取会员信息
				List<Map<String, Object>> list = orderService.getMemberInfoByOpenId(openId);
				if(list != null && list.size()>0){
					memberId = (String)list.get(0).get("id");
				}
				if(StringUtils.isNotBlank(memberId)){//验证手机验证码，如果是已存在的会员，则无需更新
					session.setAttribute(WeixinConstants.SESSION_MEMBER_ID, memberId);
				}else{//插入会员
					orderService.insertOpenid(openId, mobile);
				}
			} 
		} catch (Exception e) {
			code = ServerResult.RESULT_SERVER_ERROR;
			msg = e.getMessage();
			e.printStackTrace();
		}
		return new JsonModel(code, ServerResult.getCodeMsg(code, msg), map);
    }
    
    /**
     * weixin连接服务器
     * @param request
     * @return
     * @throws IOException 
     */
    @ApiOperation(value = "TEST", notes = "TEST", httpMethod = "POST")
    @RequestMapping(value="console",method = RequestMethod.POST)
    @ResponseBody
    public void consoleLog() throws IOException{
    	try {
    		SmartMember sm = new SmartMember();
    		String id = getRequest().getParameter("id");
    		String member_name = getRequest().getParameter("member_name");
    		String member_sex = getRequest().getParameter("member_sex");
    		String open_id = getRequest().getParameter("open_id");
    		String mobile = getRequest().getParameter("mobile");
    		if(StringUtils.isNotBlank(id)){
    			sm.setId(id);
    		}
    		if(StringUtils.isNotBlank(member_name)){
    			sm.setMemberName(member_name);
    		}
    		if(StringUtils.isNotBlank(member_sex)){
    			sm.setMemberSex(member_sex);
    		}
    		if(StringUtils.isNotBlank(open_id)){
    			sm.setOpenId(open_id);
    		}
    		if(StringUtils.isNotBlank(mobile)){
    			sm.setMobile(mobile);
    		}
    		smartMemberService.updateMember(sm);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}finally{
    		
    	}
    }
}
