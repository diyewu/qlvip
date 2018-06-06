package com.xz.controller.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xz.model.json.JsonModel;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
/**
 * restful风格接口服务
 * 访问地址： http://localhost:8080/swagger-ui.html
 */
//@RestController
public class ApiController {
	@ApiOperation(value = "测试获取字典列表", notes = "测试地址：", httpMethod = "GET")
	@RequestMapping("getList")
	@ResponseBody
	public JsonModel getList(
			@ApiParam(name = "type", value = "类型", required = true) @RequestParam(value = "type", required = true) String type) {
		type = (type == null) ? "" : type;
		JsonModel model = new JsonModel(0, "", type);
		return model;
	}
}
