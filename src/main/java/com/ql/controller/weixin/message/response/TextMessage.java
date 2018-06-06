package com.ql.controller.weixin.message.response;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.ql.controller.weixin.CDataAdapter;

/**  
 * 文本消息  
 */    
@XmlRootElement(name="xml")
public class TextMessage extends BaseMessage {    
    // 回复的消息内容    
	@XmlJavaTypeAdapter(CDataAdapter.class)
    private String Content;    
    
    public String getContent() {    
        return Content;    
    }    
    
    public void setContent(String content) {    
        Content = content;    
    }    
}   