package com.ql.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Joiner;
import com.ql.common.SmartParkDictionary;
import com.ql.entity.SmartMember;
import com.ql.utils.DateHelper;
import com.ql.utils.SortableUUID;

@Service
@Transactional
public class SmartMemberService {
	@Autowired  
	private JdbcTemplate jdbcTemplate; 
	
	public String memberRegist(String memberName,int memberSex){
		String memberId = SortableUUID.randomUUID();
		String sql = " insert into smart_member(id,member_name,member_sex,create_time)VALUES(?,?,?,NOW()) ";
		jdbcTemplate.update(sql, memberId,memberName,memberSex);
		return memberId;
	}
	
	public List<Map<String, Object>> getMemberInfoByOpenId(String openId){
		String sql = " select * from smart_member where open_id = ? ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, openId);
		return list;
	}
	
	public void updateMember(SmartMember smartMember){
		if(StringUtils.isBlank(smartMember.getId())){
			insertMember(smartMember);
		}else{
			StringBuilder sb = new StringBuilder();
			List<String> param = new ArrayList<String>();
			sb.append(" update smart_member set update_time = NOW() ");
			if(StringUtils.isNotBlank(smartMember.getMemberName())){
				sb.append(" ,member_name=? ");
				param.add(smartMember.getMemberName());
			}
			if(StringUtils.isNotBlank(smartMember.getMemberSex())){
				sb.append(" ,member_sex=? ");
				param.add(smartMember.getMemberSex());
			}
			if(StringUtils.isNotBlank(smartMember.getOpenId())){
				sb.append(" ,open_id=? ");
				param.add(smartMember.getOpenId());
			}
			if(StringUtils.isNotBlank(smartMember.getMobile())){
				sb.append(" ,mobile=? ");
				param.add(smartMember.getMobile());
			}
			sb.append(" where id = ? ");
			param.add(smartMember.getId());
			jdbcTemplate.update(sb.toString(), param.toArray());
		}
	}
	
	public void insertMember(SmartMember smartMember){
		String uuid = SortableUUID.randomUUID();
		StringBuilder sb = new StringBuilder();
		List<String> symbols = new ArrayList<String>();
		List<String> param = new ArrayList<String>();
		sb.append(" insert into smart_member( id ");
		param.add(uuid);
		symbols.add("?");
		if(StringUtils.isNotBlank(smartMember.getMemberName())){
			sb.append(" ,member_name ");
			symbols.add("?");
			param.add(smartMember.getMemberName());
		}
		if(StringUtils.isNotBlank(smartMember.getMemberSex())){
			sb.append(" ,member_sex ");
			symbols.add("?");
			param.add(smartMember.getMemberSex());
		}
		if(StringUtils.isNotBlank(smartMember.getOpenId())){
			sb.append(" ,open_id ");
			symbols.add("?");
			param.add(smartMember.getOpenId());
		}
		if(StringUtils.isNotBlank(smartMember.getMobile())){
			sb.append(" ,mobile ");
			symbols.add("?");
			param.add(smartMember.getMobile());
		}
		sb.append(",create_time,update_time)values(");
		sb.append(Joiner.on(",").join(symbols)+",NOW(),NOW())");
		jdbcTemplate.update(sb.toString(), param.toArray());
	}
	
	
	
	public List<Map<String, Object>> checkMemberByOpenId(String openId){
		String sql = " select * from smart_member where open_id = ? ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, openId);
		return list;
	}
	
	public List<Map<String, Object>> getCarParkStateByMemId(String memberId){
		StringBuilder sb = new StringBuilder();
		sb.append(" select sc.car_number,so.order_state_id,sp.park_name ,sod.state_name ,so.begin_time ,max(sc.create_time)");
		sb.append(" from smart_car sc  ");
		sb.append(" left join smart_member sm on sc.member_id= sm.id ");
		sb.append(" left join smart_order so on so.car_id = sc.id ");
		sb.append(" left join smart_order_state_dictionory sod on so.order_state_id = sod.id ");
		sb.append(" left join smart_park sp on so.park_id = sp.id ");
		sb.append(" where sm.id = ? ");
		sb.append(" GROUP BY sc.id ");
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		if(StringUtils.isNotBlank(memberId)){
			list = jdbcTemplate.queryForList(sb.toString(), memberId);
		}
		return list;
	}
	
	/**
	 * 获取上一次发送验证码的时间
	 * @param mobileNumber
	 * @return
	 */
	public List<Map<String, Object>> getLastSendCodeTime(String mobileNumber){
		String lastSendTime = "";
		String sql = " select * from smart_mobile_code_send where mobile = ? ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, mobileNumber);
		return list;
	}
	
	public void updateMobileCodeSend(String mobileNumber,int code){
		String sql = " update smart_mobile_code_send set last_send_time = ? ,code = ?,remain_time = remain_time -1 where mobile = ? ";
		String now = DateHelper.paraseDateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
		jdbcTemplate.update(sql,now, code,mobileNumber);
		insertIntoMobileCodeSendHis(mobileNumber, code);
	}
	
	public void insertMobileCodeSend(String mobileNumber,int code,int remainTime){
		String sql = " insert into smart_mobile_code_send(mobile,last_send_time,code,remain_time)values(?,?,?,?) ";
		String now = DateHelper.paraseDateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
		jdbcTemplate.update(sql, mobileNumber,now,code,remainTime);
		insertIntoMobileCodeSendHis(mobileNumber, code);
	}
	
	public void insertIntoMobileCodeSendHis(String mobileNumber,int code ){
		String sql = " insert into smart_mobile_code_send_history(mobile,send_time,send_code)values(?,?,?) ";
		String now = DateHelper.paraseDateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
		jdbcTemplate.update(sql, mobileNumber,now,code);
	}
	
	/**
	 * 初始化发送验证码次数
	 * @param time
	 */
	public void initMobileRemain(int time){
		String sql = " update smart_mobile_code_send set remain_time = ? ";
		jdbcTemplate.update(sql, time);
		
	}
	
	/**
	 * 根据会员编号获取会员信息
	 * @param memberId
	 * @return
	 */
	public List<Map<String, Object>> getMemberInfoById(String memberId){
		String sql = " select * from smart_member where id = ? ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, memberId);
		return list;
	}
	
	/**
	 * 根据openId获取会员消费记录
	 * @param openId
	 * @return
	 */
	public List<Map<String, Object>> getMemebrConsumeInfo(String openId){
		List<Map<String, Object>> resp = new ArrayList<Map<String,Object>>();
		String sql = " SELECT sm.no FROM weixin_account wa LEFT JOIN sc_member sm ON wa.mobile = sm.phone WHERE open_id =  ? ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,openId);
		if(list != null && list.size()>0){
			String memberNo = (String)list.get(0).get("no");
			sql = " select no,inputdatetime,actuallyPaid from sc_bill where member_no = ? order by inputdatetime desc ";
			resp = jdbcTemplate.queryForList(sql,memberNo);
		}
		return resp;
	}
	
}
