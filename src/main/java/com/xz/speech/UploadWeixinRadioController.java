package com.xz.speech;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.xz.controller.BaseController;
import com.xz.entity.CustomConfig;


@RequestMapping("uploadwxradio")
@Controller
public class UploadWeixinRadioController extends BaseController {
	
	@Autowired  
    private CustomConfig customConfig; 
	/*
	 * 采用spring提供的上传文件的方法
	 */
	@RequestMapping("uploadFile")
	public void uploadFile(HttpServletRequest request,HttpServletResponse response) throws IllegalStateException, IOException {
    	String msg = null;
    	Map<String, String> map = new HashMap<String, String>();
    	String respText = "";
		try{
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			if (multipartResolver.isMultipart(request)) {
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				Iterator iter = multiRequest.getFileNames();
				while (iter.hasNext()) {
					// 一次遍历所有文件
					MultipartFile file = multiRequest.getFile(iter.next().toString());
					if (file != null) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
						Date d = new Date();  
				        String dateNowStr = sdf.format(d);
				        String uploadPath = customConfig.getClientFileUpladPath()+File.separator+dateNowStr;
				        File uploadPathFile =new File(uploadPath);
						if(!uploadPathFile.exists() && !uploadPathFile.isDirectory() ){
							uploadPathFile.mkdir();
						}
						String path=customConfig.getClientFileUpladPath()+File.separator+dateNowStr+File.separator+new Date().getTime()+file.getOriginalFilename();
						file.transferTo(new File(path));
						respText = BaiduRecognize.recognizeByBaidu(path);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			msg = e.getMessage();
		}
		map.put("success", "true");
		if (msg == null) {
			map.put("i_type", "success");
			map.put("i_msg", respText);
		} else {
			map.put("i_type", "error");
			map.put("i_msg", "识别失败：" + msg);
		}
		writeJson(map, response);
	}
}
