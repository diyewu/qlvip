package com.xz.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xz.common.SessionConstant;
import com.xz.model.json.JsonModel;
import com.xz.utils.QRCodeUtil;
import com.xz.utils.VerifyCodeUtils;

@RequestMapping("authimg")
@Controller
public class AuthImageController extends BaseController{
	
	@RequestMapping("generateImage")
	@ResponseBody
	public void generateImage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
        response.setHeader("Pragma", "No-cache"); 
        response.setHeader("Cache-Control", "no-cache"); 
        response.setDateHeader("Expires", 0); 
        response.setContentType("image/jpeg"); 
           
        //生成随机字串 
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4); 
        //存入会话session 
        HttpSession session = request.getSession(true); 
        //删除以前的
        session.removeAttribute(SessionConstant.WEB_IMG_CODE);
        session.setAttribute(SessionConstant.WEB_IMG_CODE, verifyCode.toLowerCase()); 
        //生成图片 
        int w = 100, h = 30; 
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode); 
   
    }
	
	@RequestMapping("generateQrcode")
	@ResponseBody
	public JsonModel generateQrcode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		String code = request.getParameter("code");
		String msg = null;
		int width = 300; // 二维码图片的宽
		int height = 300; // 二维码图片的高
		String format = "png"; // 二维码图片的格式
		String path =  request.getSession().getServletContext().getRealPath("/")+"demo"+File.separator+"img"+File.separator;
		System.out.println("_________"+path);
		if(StringUtils.isBlank(code)){
			msg = "请填写二维码内容";
		}
		if(msg == null){
			try {
				// 生成二维码图片，并返回图片路径
				String pathName = QRCodeUtil.generateQRCode(code, width, height, format,path+"qrcode.png");
						
				System.out.println("生成二维码的图片路径： " + pathName);
			} catch (Exception e) {
				e.printStackTrace();
				msg = e.getMessage();
			}
		}
		return new JsonModel(msg == null, msg);
    }
}
