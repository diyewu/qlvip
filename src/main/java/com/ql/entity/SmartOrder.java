package com.ql.entity;

public class SmartOrder {
	private String id;
	private String carId;
	private String parkId;
	private String spaceId;
	private Integer orderType;
	private Integer orderStateId;
	private String beginTime;
	private String endTime;
	private Double receivableAmount;
	private Double actualAmount;
	private String createTime;
	private String payWayId;
	private String recordInId;
	private String recordOutId;
	private String orderRefundId;//退款订单编号
	private String transactionId;//微信订单号-微信生成的订单号，在支付通知中有返回
	private String totalFee;//应收总金额
	private String cashFee;//实收金额
	private String couponFee;//优惠券金额
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
	public String getParkId() {
		return parkId;
	}
	public void setParkId(String parkId) {
		this.parkId = parkId;
	}
	public String getSpaceId() {
		return spaceId;
	}
	public void setSpaceId(String spaceId) {
		this.spaceId = spaceId;
	}
	
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public Integer getOrderStateId() {
		return orderStateId;
	}
	public void setOrderStateId(Integer orderStateId) {
		this.orderStateId = orderStateId;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Double getReceivableAmount() {
		return receivableAmount;
	}
	public void setReceivableAmount(Double receivableAmount) {
		this.receivableAmount = receivableAmount;
	}
	public Double getActualAmount() {
		return actualAmount;
	}
	public void setActualAmount(Double actualAmount) {
		this.actualAmount = actualAmount;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getPayWayId() {
		return payWayId;
	}
	public void setPayWayId(String payWayId) {
		this.payWayId = payWayId;
	}
	public String getRecordInId() {
		return recordInId;
	}
	public void setRecordInId(String recordInId) {
		this.recordInId = recordInId;
	}
	public String getRecordOutId() {
		return recordOutId;
	}
	public void setRecordOutId(String recordOutId) {
		this.recordOutId = recordOutId;
	}
	public String getOrderRefundId() {
		return orderRefundId;
	}
	public void setOrderRefundId(String orderRefundId) {
		this.orderRefundId = orderRefundId;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
	public String getCashFee() {
		return cashFee;
	}
	public void setCashFee(String cashFee) {
		this.cashFee = cashFee;
	}
	public String getCouponFee() {
		return couponFee;
	}
	public void setCouponFee(String couponFee) {
		this.couponFee = couponFee;
	}
	
	
	
}
