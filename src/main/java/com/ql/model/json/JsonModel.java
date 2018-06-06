package com.ql.model.json;

/**
 * json 模型 用于返回操作结果
 */
@SuppressWarnings("serial")
public class JsonModel implements java.io.Serializable{
	/**
	 * 是否成功
	 */
	private boolean success = true;
	/**
	 * 返回状态码
	 */
	private Integer code = 0;
	/**
	 * 提示信息
	 */
	private String msg = "";
	/**
	 * 数据
	 */
	private Object data = null;
	/**
	 * 其它信息
	 */
	private Object obj = null;
	

	public JsonModel() {
		super();
	}
	
	
	public JsonModel(Object data) {
		this.data = data;
	}
	
	public JsonModel(boolean success, String msg) {
		this.success = success;
		this.msg = msg;
	}
	public JsonModel(boolean success, Object data) {
		this.success = success;
		this.data = data;
	}
	
	public JsonModel(boolean success, String msg, Object data) {
		this.success = success;
		this.msg = msg;
		this.data = data;
	}
	
	public JsonModel(Integer code, String msg, Object data) {
		this.success = (code == 0);
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	
	public JsonModel(Integer code, String msg, Object data, Object obj) {
		this.code = code;
		this.msg = msg;
		this.data = data;
		this.obj = obj;
	}
	
	public JsonModel(boolean success, String msg, Object data, Object obj) {
		this.success = success;
		this.msg = msg;
		this.data = data;
		this.obj = obj;
	}
	public JsonModel(int code, String msg, Object data, Object obj) {
		this.code = code;
		this.success = (code == 0);
		this.msg = msg;
		this.data = data;
		this.obj = obj;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}


	public Integer getCode() {
		return code;
	}


	public void setCode(Integer code) {
		this.code = code;
	}
	
}
