package com.ql.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ql.common.SmartParkDictionary;
import com.ql.entity.SmartOrder;
import com.ql.utils.SortableUUID;

@Service
@Transactional
public class SmartOrderService {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Map<String, Object>> getOrderByParkCar(String parkId, String carId, int orderState) {
		String sql = " select * from smart_order where car_id = ? and park_id = ? and order_state_id = ? ORDER BY create_time desc ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, carId, parkId, orderState);
		return list;
	}

	/**
	 * 根据会员编号获取订单总数，分页使用
	 * 
	 * @param memberId
	 * @return
	 */
	public int getOrderCountByMemberId(String memberId) {
		int count = 0;
		String sql = " SELECT so.id, sosd.show_name FROM smart_order so LEFT JOIN smart_order_state_dictionory sosd ON so.order_state_id = sosd.id LEFT JOIN smart_car sc ON so.car_id = sc.id WHERE sc.member_id = ? ORDER BY so.create_time desc ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, memberId);
		if (list != null) {
			count = list.size();
		}
		return count;
	}

	/**
	 * 根据会员编号获取订单
	 * 
	 * @param memberId
	 * @param min
	 * @param max
	 * @return
	 */
	public List<Map<String, Object>> getOrderListByMemberId(String memberId, int min, int max) {
		String sql = " SELECT so.id, sosd.id AS status,so.receivable_amount, sosd.show_name, sp.park_name FROM smart_order so LEFT JOIN smart_order_state_dictionory sosd ON so.order_state_id = sosd.id LEFT JOIN smart_car sc ON so.car_id = sc.id LEFT JOIN smart_park sp ON so.park_id = sp.id WHERE sc.member_id = ? ORDER BY so.create_time desc limit ? , ? ";
		if (max == 0) {
			max = 10;
		}
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, memberId, min, max);
		return list;
	}

	/**
	 * 获取待支付的订单 根据订单Id获取订单详细信息
	 * 
	 * @param orderId
	 * @return
	 */
	public List<Map<String, Object>> getOrderInfoById(String orderId) {
		String sql = " select id, begin_time,end_time,receivable_amount from smart_order where id = ? ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, orderId);
		return list;
	}

	/**
	 * 根据订单编号和状态获取订单
	 * 
	 * @param orderId
	 * @param state
	 * @return
	 */
	public List<Map<String, Object>> getOrderInfoByIdAndState(String orderId, int state) {
		String sql = " select id, begin_time,end_time,receivable_amount from smart_order where id = ? and order_state_id = ? ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, orderId, state);
		return list;
	}

	/**
	 * 判断车辆是否允许进入停车场
	 * 如果当前车辆存在订单状态为：停车中、申请驶出停车场，待支付 的订单，则不允许车辆进入。
	 * @param carId
	 * @param parkId
	 * @return
	 */
	public List<Map<String, Object>> checkCarForbidParking(String carId, String parkId) {
		// TODO 检测该车辆是否存在未付款订单，该业务逻辑需要讨论
		String sql = " select * from smart_order where car_id = ? and order_state_id in(?,?) ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, carId,SmartParkDictionary.orderState.CAR_PARKING.ordinal(),SmartParkDictionary.orderState.ASK_OUT.ordinal());
		return list;
	}

	/**
	 * 申请成功后创建订单，返回订单编号信息
	 * 
	 * @param carId
	 * @param parkId
	 * @param spaceId
	 * @param parkingType
	 * @return
	 */
	public String createOrder(String carId, String parkId, int orderType, String spaceId) {
		String orderId = SortableUUID.randomUUID();
		// 默认取当前停车场第一种停车位
		if (StringUtils.isBlank(spaceId)) {
			String sql = " select sps.id from smart_park sp left join smart_park_space sps on  sp.id = sps.park_id where 1 = 1 and sp.id = ? order by sps.space_type ";
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, parkId);
			if (list != null && list.size() > 0) {
				spaceId = (String) list.get(0).get("id");
			}
		}
		String orderSql = " insert into smart_order(id,car_id,park_id,space_id,order_type,order_state_id,create_time)values(?,?,?,?,?,?,NOW()) ";
		jdbcTemplate.update(orderSql, orderId, carId, parkId, spaceId, orderType,
				SmartParkDictionary.orderState.ASK_IN.ordinal());
		return orderId;
	}

	/**
	 * 根据车辆、场地、订单状态获取订单信息
	 * 
	 * @param carId
	 * @param parkId
	 * @param orderStateId
	 * @return
	 */
	public List<Map<String, Object>> getOrderId(String carId, String parkId, int orderStateId) {
		String sql = " select * from smart_order where car_id = ? and park_id = ? and order_state_id = ? ORDER BY create_time desc ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, carId, parkId, orderStateId);
		return list;
	}

	/**
	 * 根据订单编号查询 开始停车时间 和 汽车驶入记录 ID
	 * 
	 * @param orderId
	 * @return
	 */
	public List<Map<String, Object>> getOrderInfo(String orderId) {
		String sql = " SELECT so.begin_time, so.record_in_id, sps.space_price_perhour, sps.space_name, sp.park_name FROM smart_order so LEFT JOIN ( SELECT a.id,a.space_price_perhour, b.space_name FROM smart_park_space a LEFT JOIN smart_park_space_dictionary b ON a.space_type = b.id ) sps ON so.space_id = sps.id LEFT JOIN smart_park sp ON so.park_id = sp.id WHERE so.id = ? ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, orderId);
		return list;
	}

	/**
	 * 停车
	 * 
	 * @param carNumber
	 * @param parkId
	 * @param spaceId
	 * @param parkingType
	 *            return
	 */
	public String parkIngIn(String carId, String parkId, String spaceId, String entranceId, int parkingType) {
		String recordId = SortableUUID.randomUUID();
		String sql = " insert into smart_car_park_record(id,car_id,park_id,space_id,entrance_id,parking_type,create_time)VALUE(?,?,?,?,?,?,NOW()) ";
		jdbcTemplate.update(sql, recordId, carId, parkId, spaceId, entranceId, parkingType);
		return recordId;
	}

	/**
	 * 校验订单编号和会员编号是否一致
	 * 
	 * @param orderId
	 * @param memberId
	 * @return
	 */
	public List<Map<String, Object>> checkOrderIdAndMemberId(String orderId, String memberId) {
		String sql = " SELECT so.* FROM smart_order so LEFT JOIN smart_car sc ON so.car_id = sc.id LEFT JOIN smart_member sm ON sc.member_id = sm.id WHERE so.id = ? AND sm.id = ? ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, orderId, memberId);
		return list;
	}

	/**
	 * 动态更新smart_order
	 * 
	 * @param smartOrder
	 */
	public void updateSmartOrder(SmartOrder smartOrder) {
		if (StringUtils.isNotBlank(smartOrder.getId())) {
			StringBuilder sb = new StringBuilder();
			List<Object> param = new ArrayList<Object>();
			sb.append(" update smart_order set update_time=NOW() ");
			param.add(smartOrder.getId());
			if (StringUtils.isNotBlank(smartOrder.getCarId())) {
				sb.append(" ,car_id = ? ");
				param.add(smartOrder.getCarId());
			}
			if (StringUtils.isNotBlank(smartOrder.getParkId())) {
				sb.append(" ,park_id = ? ");
				param.add(smartOrder.getParkId());
			}
			if (StringUtils.isNotBlank(smartOrder.getSpaceId())) {
				sb.append(" ,space_id = ? ");
				param.add(smartOrder.getSpaceId());
			}
			if (smartOrder.getOrderType() != null) {
				sb.append(" ,order_type = ? ");
				param.add(smartOrder.getOrderType());
			}
			if (smartOrder.getOrderStateId() != null) {
				sb.append(" ,order_state_id = ? ");
				param.add(smartOrder.getOrderStateId());
			}
			if (StringUtils.isNotBlank(smartOrder.getBeginTime())) {
				sb.append(" ,begin_time = ? ");
				param.add(smartOrder.getBeginTime());
			}
			if (StringUtils.isNotBlank(smartOrder.getEndTime())) {
				sb.append(" ,end_time = ? ");
				param.add(smartOrder.getEndTime());
			}
			if (smartOrder.getReceivableAmount() != null) {
				sb.append(" ,receivable_amount = ? ");
				param.add(smartOrder.getReceivableAmount());
			}
			if (smartOrder.getActualAmount() != null) {
				sb.append(" ,actual_amount = ? ");
				param.add(smartOrder.getActualAmount());
			}

			if (StringUtils.isNotBlank(smartOrder.getEndTime())) {
				sb.append(" ,end_time = ? ");
				param.add(smartOrder.getEndTime());
			}
			if (smartOrder.getReceivableAmount() != null) {
				sb.append(" ,receivable_amount = ? ");
				param.add(smartOrder.getReceivableAmount());
			}
			if (smartOrder.getActualAmount() != null) {
				sb.append(" ,actual_amount = ? ");
				param.add(smartOrder.getActualAmount());
			}
			if (StringUtils.isNotBlank(smartOrder.getPayWayId())) {
				sb.append(" ,pay_way_id = ? ");
				param.add(smartOrder.getPayWayId());
			}
			if (StringUtils.isNotBlank(smartOrder.getRecordInId())) {
				sb.append(" ,record_in_id = ? ");
				param.add(smartOrder.getRecordInId());
			}
			if (StringUtils.isNotBlank(smartOrder.getRecordOutId())) {
				sb.append(" ,record_out_id = ? ");
				param.add(smartOrder.getRecordOutId());
			}
			if (StringUtils.isNotBlank(smartOrder.getOrderRefundId())) {
				sb.append(" ,order_refund_id = ? ");
				param.add(smartOrder.getOrderRefundId());
			}
			if (StringUtils.isNotBlank(smartOrder.getTransactionId())) {
				sb.append(" ,transaction_id = ? ");
				param.add(smartOrder.getTransactionId());
			}
			if (StringUtils.isNotBlank(smartOrder.getTotalFee())) {
				sb.append(" ,total_fee = ? ");
				param.add(smartOrder.getTotalFee());
			}
			if (StringUtils.isNotBlank(smartOrder.getCashFee())) {
				sb.append(" ,cash_fee = ? ");
				param.add(smartOrder.getCashFee());
			}
			if (StringUtils.isNotBlank(smartOrder.getCouponFee())) {
				sb.append(" ,coupon_fee = ? ");
				param.add(smartOrder.getCouponFee());
			}

			sb.append(" where id = ? ");
			param.add(smartOrder.getId());
			jdbcTemplate.update(sb.toString(), param.toArray());
		}
	}

	/**
	 * 查询微信订单-校验订单信息是否正确
	 * 
	 * @param orderNo
	 * @param receivableAmount
	 * @param openId
	 * @return
	 */
	public boolean checkOrderInfo(String orderNo, double receivableAmount, String openId) {
		String sql = " select 1 FROM smart_order so LEFT JOIN smart_car sc ON so.car_id = sc.id LEFT JOIN "
				+ "smart_member sm ON sc.member_id = sm.id WHERE so.id = ? AND so.receivable_amount = ? AND sm.open_id = ? ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, orderNo,receivableAmount, openId);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * 插入支付操作记录
	 * @param orderNo
	 * @param progress
	 * @param map
	 */
	public void insertIntoWeixinPayDetail(String orderNo,int progress,Map<String, String> map){
		ObjectMapper mapper = new ObjectMapper();
		String recordId = SortableUUID.randomUUID();
		String sql = " insert into smart_pay_detail_record (id,order_id,type,description,create_time)value(?,?,?,?,NOW()) ";
		try {
			jdbcTemplate.update(sql, recordId,orderNo,progress,mapper.writeValueAsString(map));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
