package com.xz.entity;

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
	
}
