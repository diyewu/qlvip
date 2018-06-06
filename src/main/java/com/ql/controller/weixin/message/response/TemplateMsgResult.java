package com.ql.controller.weixin.message.response;

/**
 * 模板消息 返回的结果
 */
public class TemplateMsgResult extends ResultState {
	/** 
	*  
	*/
	private static final long serialVersionUID = 3198012785950215862L;

	private String msgid; // 消息id(发送模板消息)
	private int errcode; //
	private String errmsg; //

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

}