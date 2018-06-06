package com.xz.config.interceptors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.xz.common.SessionConstant;

/**
 * 自定义拦截器
 *
 */

public class AuthInterceptor implements HandlerInterceptor {
	
	private static Set<String> excludeUrls = new HashSet<String>(); // 不需要拦截的资源
	private static List<String> whitelist = new ArrayList<String>();
	static{
		whitelist.add("/authimg/generateImage");//验证码
		whitelist.add("/app/getImgBydetailId");//获取图片
		whitelist.add("/login");//登陆
		whitelist.add("/userlogin");//登陆
		whitelist.add("/forgetPwdCheckNameAndCode");//忘记密码
		whitelist.add("/uploadwxradio/");//上传语音
		whitelist.add("/manager/");//上传语音
		whitelist.add(".jpg");//下载图片
		whitelist.add(".png");//下载图片
	}
	
	/**
	 * 这个方法的主要作用是用于清理资源的，当然这个方法也只能在当前这个Interceptor的preHandle方法的返回值为true时才会执行。
	 */
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 在Controller的方法调用之后执行
	 */
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	/** 
     * preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用，
     * SpringMVC中的Interceptor拦截器是链式的，可以同时存在多个Interceptor，
     * 然后SpringMVC会根据声明的前后顺序一个接一个的执行，而且所有的Interceptor中的preHandle方法都会在Controller方法调用之前调用。
     * SpringMVC的这种Interceptor链式结构也是可以进行中断的，这种中断方式是令preHandle的返 
     * 回值为false，当preHandle的返回值为false的时候整个请求就结束了。 
     */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		/*
		HttpSession session = request.getSession();
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url = requestURI.substring(contextPath.length() + 1);
		//判断是否是异步请求	
		boolean isAjax = false;
		String requestType = request.getHeader("X-Requested-With");
		if (requestType != null && requestType.equals("XMLHttpRequest")) {// 异步
			isAjax = true;
		}
		for(String tem:whitelist){
			if(requestURI.contains(tem)){
				return true;
			}
		}
		//APP 接口
		if(requestURI.contains("/app/")){
			String phoneId = request.getHeader("phoneId");
			String token = request.getHeader("token");
			if(StringUtils.isBlank(phoneId) || StringUtils.isBlank(token)){
				return false;
			}
			return true;
		}else if(requestURI.contains("/mini/")){
			String randomKey = request.getParameter("randomKey");
			String token = request.getParameter("token");
			if(StringUtils.isBlank(randomKey) && StringUtils.isBlank(token)){
				return false;
			}
			return true;
		}else{
			//后台和WEB PC请求
			String adminUserId = (String)session.getAttribute("userId");
			String adminUserName = (String)session.getAttribute("userName");
			String webUserId = (String)session.getAttribute(SessionConstant.WEB_USER_ID);
			String wenUserName = (String)session.getAttribute(SessionConstant.WEB_USER_NAME);
			if(StringUtils.isBlank(adminUserId) && StringUtils.isBlank(adminUserName)
					&& StringUtils.isBlank(webUserId)&& StringUtils.isBlank(wenUserName)){
				System.out.println("URI:"+requestURI+"被拦截");
				if(requestURI.contains("/webctrl/")){
					response.sendRedirect("../../demo/login.jsp");
				}else{
					response.sendRedirect("../../admin/login.jsp");
				}
				return false;
			}
		}
		*/
		
//		if(requestURI.contains("/projectmgr/") 
//				|| 	requestURI.contains("/operate/")
//				|| requestURI.contains("/user/")
//				){
//			String userId = (String)session.getAttribute("userId");
//			if(StringUtils.isBlank(userId)){
//				response.sendRedirect("../admin/login.jsp");
//				return false;
//			}
//		}
		
//		if(requestURI.contains("/operate/")){
//			
//		}
		
		return true;
	}

	/**
	 * 验证url是否在允许访问的url列表里面，即未登录既可以访问的url
	 * @param url
	 * @return
	 */
	private boolean validateUrls(String url) {
		url = "/" + url;
		
		if (excludeUrls.contains(url)) {
			return true;
		}
		for (String string : excludeUrls) {
			if (string.endsWith("*") && 
					url.indexOf(string.substring(0, string.length() - 1)) != -1) {
				return true;
			}
		}
		return false;
	}
	
}
