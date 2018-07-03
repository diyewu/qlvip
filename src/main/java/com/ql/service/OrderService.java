package com.ql.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {
	@Autowired  
	private JdbcTemplate jdbcTemplate; 
	
	public List<Map<String, Object>> getLastestOrder(String preOrderNo){
		StringBuilder sb = new StringBuilder();
		sb.append(" select sb.no as order_no,sb.actuallypaid,sb.inputdatetime,sm.deposit,sb.member_no as smn ,sm.cardno as member_no,sm.phone,wa.open_id ");
		sb.append(" from sc_bill sb ");
		sb.append(" LEFT JOIN sc_member sm on sb.member_no = sm.no ");
		sb.append(" left join weixin_account wa on sm.phone = wa.mobile ");
		sb.append(" where sb.no > ? ");
		sb.append(" and phone is not null ");
		sb.append(" and wa.open_id is not null ");
		sb.append(" order by sb.no ");
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sb.toString(), preOrderNo);
		return list;
	}
	
	public String getLastOneOrder(){
		String sql = " select max(no) as no from sc_bill ";
		String orderNo = "";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		if(list != null && list.size()>0){
			orderNo = list.get(0).get("no")+"";
		}
		return orderNo;
	}
	
	/**
	 * 根据open_id获取会员信息
	 * @param openId
	 * @return
	 */
	public List<Map<String, Object>> getMemberInfoByOpenId(String openId){
		String sql = " SELECT * FROM weixin_account wa LEFT JOIN sc_member sm ON wa.mobile = sm.phone WHERE wa.open_id = ? ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,openId);
		return list;
	}
	
	public void insertOpenid(String openId,String mobile){
		String sql = " insert into weixin_account (open_id,mobile)values(?,?) ";
		jdbcTemplate.update(sql, openId,mobile);
	}
	
	/**
	 * 检查是否是线下会员
	 */
	public boolean checkMobileIsOffline(String mobile){
		String sql = " select * from sc_member where phone = ?  ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, mobile);
		if(list != null && list.size()>0){
			return true;
		}
		return false;
	}
	
	public boolean checkBind(String mobile){
		String sql = " select * from weixin_account where mobile = ? ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, mobile);
		if(list != null && list.size()>0){
			return true;
		}
		return false;
	} 
	
}
