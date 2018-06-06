package com.xz.controller.weixin.message.response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.xz.controller.weixin.CDataAdapter;

/**  
 * 消息基类（公众帐号 -> 普通用户）  
 *   
 */    
public class BaseMessage {    
    // 接收方帐号（收到的OpenID）    
	@XmlJavaTypeAdapter(CDataAdapter.class)
    private String ToUserName;    
    // 开发者微信号    
	
	@XmlJavaTypeAdapter(CDataAdapter.class)
    private String FromUserName;    
    // 消息创建时间 （整型）    
	@XmlJavaTypeAdapter(CDataAdapter.class)
    private String CreateTime;    
    // 消息类型（text/music/news）    
	@XmlJavaTypeAdapter(CDataAdapter.class)
    private String MsgType;  
    
    // 位0x0001被标志时，星标刚收到的消息    
    private int FuncFlag;    
    public String getToUserName() {    
        return ToUserName;    
    }    
    
    
    public void setToUserName(String toUserName) {    
        ToUserName = toUserName;    
    }    
    
    public String getFromUserName() {    
        return FromUserName;    
    }    
    
    public void setFromUserName(String fromUserName) {    
        FromUserName = fromUserName;    
    }    
    
    
    public String getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {    
        return MsgType;    
    }    
    
    public void setMsgType(String msgType) {    
        MsgType = msgType;    
    }    
    
    public int getFuncFlag() {    
        return FuncFlag;    
    }    
    
    public void setFuncFlag(int funcFlag) {    
        FuncFlag = funcFlag;    
    }    
}    
