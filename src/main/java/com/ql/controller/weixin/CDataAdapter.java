package com.ql.controller.weixin;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class CDataAdapter extends XmlAdapter<String, String> {    
    
	@Override
    public String marshal (String v) throws Exception
    {
        return "<![CDATA[" + v + "]]>";
    }
	//从xml到javabean的适配方法
    @Override
    public String unmarshal(String str) throws Exception {
        return str;
    }
    
    
} 