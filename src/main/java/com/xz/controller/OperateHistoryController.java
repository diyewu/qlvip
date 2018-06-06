package com.xz.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xz.service.OperateHistoryService;

@RequestMapping("operate")
@Controller
public class OperateHistoryController extends BaseController{

	private static final long serialVersionUID = 3275714765223850730L;
	
	@Autowired
	private OperateHistoryService operateHistoryService;
	@RequestMapping("list")
	public void list(HttpServletRequest request,HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException{
		String msg = "";
		String outPut = "";
		try{
			Map<String,String> condition = new HashMap<String, String>();
			String operateUser = request.getParameter("operateUser");
			String createDateStart = request.getParameter("createDateStart");
			String createDateEnd = request.getParameter("createDateEnd");
			String type = request.getParameter("type");
			String start = request.getParameter("start");
			String limit = request.getParameter("limit");;
			if (StringUtils.isNotBlank(operateUser)) {
				condition.put("operateUser", operateUser);
			}
			if (createDateStart != null && !"".equals(createDateStart.trim())) {
				condition.put("createDateStart", createDateStart);
			}
			if (createDateEnd != null && !"".equals(createDateEnd.trim())) {
				condition.put("createDateEnd", createDateEnd);
			}
			if (start != null && !"".equals(start.trim())) {
				condition.put("start", start);
			}
			if (limit != null && !"".equals(limit.trim())) {
				condition.put("limit", limit);
			}
			if(!"3".equals(type) && StringUtils.isNotBlank(type)){
				condition.put("type", type);
			}
			try {
				outPut = operateHistoryService.getOperateList(condition);
			} catch (Exception e) {
				e.printStackTrace();
				msg = e.getMessage();
			}
			this.printData(response, outPut);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@RequestMapping("listForLogin")
	public void listForLogin(HttpServletRequest request,HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException{
		String msg = "";
		String outPut = "";
		try{
			Map<String,String> condition = new HashMap<String, String>();
			String operateUser = request.getParameter("operateUser");
			String createDateStart = request.getParameter("createDateStart");
			String createDateEnd = request.getParameter("createDateEnd");
			String start = request.getParameter("start");
			String limit = request.getParameter("limit");;
			if (StringUtils.isNotBlank(operateUser)) {
				condition.put("operateUser", operateUser);
			}
			if (createDateStart != null && !"".equals(createDateStart.trim())) {
				condition.put("createDateStart", createDateStart);
			}
			if (createDateEnd != null && !"".equals(createDateEnd.trim())) {
				condition.put("createDateEnd", createDateEnd);
			}
			if (start != null && !"".equals(start.trim())) {
				condition.put("start", start);
			}
			if (limit != null && !"".equals(limit.trim())) {
				condition.put("limit", limit);
			}
			try {
				outPut = operateHistoryService.getOperateListForlogin(condition);
			} catch (Exception e) {
				e.printStackTrace();
				msg = e.getMessage();
			}
			this.printData(response, outPut);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping("downloadExcelByid")
	@ResponseBody
	public void downloadExcelByid(HttpServletRequest request,HttpServletResponse response){
        response.setHeader("Pragma", "No-cache"); 
        response.setHeader("Cache-Control", "no-cache"); 
        response.setDateHeader("Expires", 0); 
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.addHeader("Content-Disposition","inline; attachment; filename=" + System.currentTimeMillis()+".xlsx");
        String detailId = request.getParameter("id");
        if(StringUtils.isBlank(detailId)){
        	return;
        }
        List<Map<String, Object>> list = operateHistoryService.getFilePath(detailId);
        File excelFile = null;
        if(list != null && list.size()>0){
        	String path = list.get(0).get("downfile_path")==null?"":list.get(0).get("downfile_path")+"";
        	if(StringUtils.isNotBlank(path)){
        		excelFile = new File(path);
        	}
        }
        if(excelFile != null){
            OutputStream os = null;
            FileInputStream fos = null;
			try {
				os = response.getOutputStream();
				byte[] buffer = new byte[2048];
				fos = new FileInputStream(excelFile.getPath());
				int count;
				while ((count = fos.read(buffer)) > 0) {
					os.write(buffer, 0, count);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally{
				try {
					if (os != null)
						os.close();
					if (fos != null)
					fos.close();
				} catch (IOException e) {
				}
			}
        }
	}
	@RequestMapping("checkDownloadExcelByid")
	@ResponseBody
	public void checkDownloadExcelByid(HttpServletRequest request,HttpServletResponse response){
		String detailId = request.getParameter("id");
		if(StringUtils.isBlank(detailId)){
			return;
		}
		String msg = null;
		Map<String ,String > map = new HashMap<String, String>();
		List<Map<String, Object>> list = operateHistoryService.getFilePath(detailId);
		File excelFile = null;
		if(list != null && list.size()>0){
			String path = list.get(0).get("downfile_path")==null?"":list.get(0).get("downfile_path")+"";
			if(StringUtils.isNotBlank(path)){
				excelFile = new File(path);
			}
		}
		if(!excelFile.exists()){
			msg = "文件不存在或文件丢失。";
		}
		
		if(msg == null){
			map.put("i_type", "success");
		}else{
			map.put("i_type", "error");
			map.put("i_msg", msg);
		}
		this.printData(response, map);
	}

}
