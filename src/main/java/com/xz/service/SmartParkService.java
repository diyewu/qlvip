package com.xz.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xz.entity.CategoryTreeBean;
import com.xz.entity.SmartOrder;
import com.xz.entity.SmartPark;
import com.xz.entity.SmartSpace;
import com.xz.utils.SortableUUID;

@Service
@Transactional
public class SmartParkService {
	@Autowired  
	private JdbcTemplate jdbcTemplate; 
	/**
	 * 注册停车场主体信息
	 * @param parkName
	 * @param parkDescription
	 * @param parkLongitude
	 * @param parkLatitude
	 * @param managerId
	 * @param operateUserId
	 * @return
	 */
	public String registPark(String parkName,String parkDescription,String parkLongitude,String parkLatitude,String managerId,String operateUserId){
		String parkId = SortableUUID.randomUUID();
		String sql = " insert into smart_park(id,park_name,park_description,park_longitude,park_latitude,create_time,update_time,manager_id,operate_user_id)VALUES(?,?,?,?,?,NOW(),NOW(),?,?) ";
		jdbcTemplate.update(sql, parkId,parkName,parkDescription,parkLongitude,parkLatitude,managerId,operateUserId);
		return parkId;
	}
	
	/**
	 * 注册停车场入口信息，一个停车场可以有多个入口
	 * @param parkId
	 * @param entranceName
	 * @param entranceLongitude
	 * @param entranceLatitude
	 * @param entranceDescription
	 * @return
	 */
	public String registParkEntrance(String parkId,String entranceName,String entranceLongitude,String entranceLatitude,String entranceDescription){
		String entranceId = SortableUUID.randomUUID();
		String sql = " insert into smart_park_entrance(id,park_id,entrance_name,entrance_longitude,entrance_latitude,entrance_description,create_time,update_time)values(?,?,?,?,?,?,NOW(),NOW()) ";
		jdbcTemplate.update(sql, entranceId,parkId,entranceName,entranceLongitude,entranceLatitude,entranceDescription);
		return entranceId;
	}
	
	/**
	 * 注册停车场车位信息，一个停车场可以有多种车位
	 * @param parkId
	 * @param spaceType
	 * @param spaceTotal
	 * @param spaceUsed
	 * @param spacePricePerhour
	 * @param spaceDescription
	 * @return
	 */
	public String registParkSpace(String parkId,String spaceType,int spaceTotal,int spaceUsed,double spacePricePerhour,String spaceDescription){
		String spaceId = SortableUUID.randomUUID();
		String sql = " insert into smart_park_space(id,park_id,space_type,space_total,space_used,space_price_perhour,space_description,create_time,update_time)values(?,?,?,?,?,?,?,NOW(),NOW()) ";
		jdbcTemplate.update(sql, spaceId,parkId,spaceType,spaceTotal,spaceUsed,spacePricePerhour,spaceDescription);
		return spaceId;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Map<String, Object>> listPark(){
		String sql = " select id,park_name from smart_park ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		return list;
	}
	
	public List<Map<String, Object>> listSpaceByParkId(String parkId){
		String sql = " select sps.id,spsd.space_name from smart_park_space sps left join smart_park_space_dictionary spsd on sps.space_type = spsd.id where park_id = ? ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		return list;
	}

	
	//订单相关 service****************************************************************
	/**
	 * 判断车辆是否允许进入停车场
	 * @param carId
	 * @param parkId
	 * @return
	 */
	public int checkCarForbidParking(String carId,String parkId){
		//TODO 
		return 0;
	}
	
	
	/**
	 * 申请成功后创建订单，返回订单编号信息
	 * @param carId
	 * @param parkId
	 * @param spaceId
	 * @param parkingType
	 * @return
	 */
	public String createOrder(String carId,String parkId,int orderType){
		String orderId = SortableUUID.randomUUID();
		String orderSql = " insert into smart_order(id,car_id,park_id,order_type,order_state_id,create_time)values(?,?,?,?,?,?,NOW()) ";
		jdbcTemplate.update(orderSql, orderId,carId,parkId,orderType,1);
		return orderId;
	}
	
	/**
	 * 根据车辆、场地、订单状态获取订单信息
	 * @param carId
	 * @param parkId
	 * @param orderStateId
	 * @return
	 */
	public List<Map<String, Object>> getOrderId(String carId,String parkId,int orderStateId){
		String sql = " select * from smart_order where car_id = ? and park_id = ? and order_state_id = ? ORDER BY create_time desc ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, carId,parkId,orderStateId);
		return list;
	}
	
	/**
	 * 根据订单编号查询 开始停车时间 和 汽车驶入记录 ID
	 * @param orderId
	 * @return
	 */
	public List<Map<String, Object>> getOrderInfo(String orderId){
		String sql = " SELECT so.begin_time, so.record_in_id, sps.space_price_perhour, sps.space_name, sp.park_name FROM smart_order so LEFT JOIN ( SELECT a.space_price_perhour, b.space_name FROM smart_park_space a LEFT JOIN smart_park_space_dictionary b ON a.space_type = b.id ) sps ON so.space_id = sps.id LEFT JOIN smart_park sp ON so.park_id = sp.id WHERE id = ? ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, orderId);
		return list;
	}
	
	
	/**
	 * 停车
	 * @param carNumber
	 * @param parkId
	 * @param spaceId
	 * @param parkingType
	 * return 
	 */
	public String parkIngIn(String carId,String parkId,String spaceId,String entranceId,int parkingType){
		String recordId = SortableUUID.randomUUID();
		String sql = " insert into smart_car_park_recoder(id,car_id,park_id,space_id,entrance_id,parking_type,create_time)VALUE(?,?,?,?,?,?,NOW()) ";
		jdbcTemplate.update(sql, recordId,carId,parkId,spaceId,entranceId,parkingType);
		return recordId;
	}
	
	/**
	 * 校验订单编号和会员编号是否一致
	 * @param orderId
	 * @param memberId
	 * @return
	 */
	public List<Map<String, Object>>  checkOrderIdAndMemberId(String orderId,String memberId){
		String sql = " SELECT so.* FROM smart_order so LEFT JOIN smart_car sc ON so.car_id = sc.id LEFT JOIN smart_member sm ON sc.member_id = sm.id WHERE so.id = ? AND sm.id = ? ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, orderId,memberId);
		return list;
	}
	
	/**
	 * 动态更新smart_order
	 * @param smartOrder
	 */
	public void updateSmartOrder(SmartOrder smartOrder){
		if(StringUtils.isNotBlank(smartOrder.getId())){
			StringBuilder sb = new StringBuilder();
			List<Object> param = new ArrayList<Object>();
			sb.append(" update smart_order set id = ? ");
			param.add(smartOrder.getId());
			if(StringUtils.isNotBlank(smartOrder.getCarId())){
				sb.append(" ,car_id = ? ");
				param.add(smartOrder.getCarId());
			}
			if(StringUtils.isNotBlank(smartOrder.getParkId())){
				sb.append(" ,park_id = ? ");
				param.add(smartOrder.getParkId());
			}
			if(StringUtils.isNotBlank(smartOrder.getSpaceId())){
				sb.append(" ,space_id = ? ");
				param.add(smartOrder.getSpaceId());
			}
			if(smartOrder.getOrderType() != null){
				sb.append(" ,order_type = ? ");
				param.add(smartOrder.getOrderType());
			}
			if(smartOrder.getOrderStateId() != null){
				sb.append(" ,order_state_id = ? ");
				param.add(smartOrder.getOrderStateId());
			}
			if(StringUtils.isNotBlank(smartOrder.getBeginTime())){
				sb.append(" ,begin_time = ? ");
				param.add(smartOrder.getBeginTime());
			}
			if(StringUtils.isNotBlank(smartOrder.getEndTime())){
				sb.append(" ,end_time = ? ");
				param.add(smartOrder.getEndTime());
			}
			if(smartOrder.getReceivableAmount() != null){
				sb.append(" ,receivable_amount = ? ");
				param.add(smartOrder.getReceivableAmount());
			}
			if(smartOrder.getActualAmount() != null){
				sb.append(" ,actual_amount = ? ");
				param.add(smartOrder.getActualAmount());
			}
			
			if(StringUtils.isNotBlank(smartOrder.getEndTime())){
				sb.append(" ,end_time = ? ");
				param.add(smartOrder.getEndTime());
			}
			if(smartOrder.getReceivableAmount() != null){
				sb.append(" ,receivable_amount = ? ");
				param.add(smartOrder.getReceivableAmount());
			}
			if(smartOrder.getActualAmount() != null){
				sb.append(" ,actual_amount = ? ");
				param.add(smartOrder.getActualAmount());
			}
			if(StringUtils.isNotBlank(smartOrder.getPayWayId())){
				sb.append(" ,pay_way_id = ? ");
				param.add(smartOrder.getPayWayId());
			}
			if(StringUtils.isNotBlank(smartOrder.getRecordInId())){
				sb.append(" ,record_in_id = ? ");
				param.add(smartOrder.getRecordInId());
			}
			if(StringUtils.isNotBlank(smartOrder.getRecordOutId())){
				sb.append(" ,record_out_id = ? ");
				param.add(smartOrder.getRecordOutId());
			}
			sb.append(" where id = ? ");
			param.add(smartOrder.getId());
			jdbcTemplate.update(sb.toString(), param.toArray());
		}
	}
	
	//订单相关 service****************************************************************
	
	/**
	 * 获取全部停车场列表
	 * @return
	 */
	public List<SmartPark> getParkList(String keyWord){
		StringBuilder sb = new StringBuilder();
		sb.append(" select sp.id,sp.park_name,sp.park_longitude,sp.park_latitude,sp.support_mobile_pay ");
		sb.append(" from smart_park sp ");
		sb.append(" where 1=1 ");
		List<String> param = new ArrayList<String>();
		if(StringUtils.isNotBlank(keyWord)){
			sb.append(" and sp.name like ? ");
			param.add("%"+keyWord+"%");
		}
		List<SmartPark> list = (List<SmartPark>)jdbcTemplate.query(sb.toString(),new SmartParkBeanRowMapper(),param.toArray());
		return list;
	}
	
	
	public List<SmartSpace> getParkSpaceByParkId(String parkId){
		String sql = " select * from smart_park_space where park_id = ? ";
		List<SmartSpace> list = (List<SmartSpace>)jdbcTemplate.query(sql,new SmartSpaceBeanRowMapper(),parkId);
		return list;
	}
	public static class SmartParkBeanRowMapper implements RowMapper<SmartPark> {
		@Override
		public SmartPark mapRow(ResultSet rs, int rowNum) throws SQLException {
			SmartPark sp = new SmartPark();
			sp.setId(rs.getString("id"));
			sp.setName(rs.getString("park_name"));
			sp.setLongitude(rs.getDouble("park_longitude"));
			sp.setLatitude(rs.getDouble("park_latitude"));
//			sp.setSpacePricePerhour(rs.getDouble("space_price_perhour"));
			sp.setSupportMobilePay(rs.getInt("support_mobile_pay"));
			return sp;
		}
	}
	public static class SmartSpaceBeanRowMapper implements RowMapper<SmartSpace> {
		@Override
		public SmartSpace mapRow(ResultSet rs, int rowNum) throws SQLException {
			SmartSpace sp = new SmartSpace();
			sp.setId(rs.getString("id"));
			sp.setParkId(rs.getString("park_id"));
			sp.setSpaceType(rs.getInt("space_type"));
			sp.setSpaceTotal(rs.getInt("space_total"));
			sp.setSpaceUsed(rs.getInt("space_used"));
			sp.setSpacePricePerhour(rs.getDouble("space_price_perhour"));
			sp.setSpaceDescription(rs.getString("space_description"));
			return sp;
		}
	}
	
	
	
}
