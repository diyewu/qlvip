package com.xz.model.json;

/**
 * json 模型 用于返回操作结果
 */
@SuppressWarnings("serial")
public class AppJsonModel implements java.io.Serializable{
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
	

	public AppJsonModel() {
		super();
	}
	
	
	public AppJsonModel(Object data) {
		this.data = data;
	}
	
	public AppJsonModel(Integer code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	public AppJsonModel( String msg, Object data) {
		this.msg = msg;
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
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
