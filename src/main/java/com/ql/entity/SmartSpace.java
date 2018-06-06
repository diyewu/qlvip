package com.ql.entity;

public class SmartSpace {
	private String id;
	private String parkId;
	private int spaceType;
	private int spaceTotal;
	private int spaceUsed;
	private double spacePricePerhour;
	private String spaceDescription;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParkId() {
		return parkId;
	}
	public void setParkId(String parkId) {
		this.parkId = parkId;
	}
	public int getSpaceType() {
		return spaceType;
	}
	public void setSpaceType(int spaceType) {
		this.spaceType = spaceType;
	}
	public int getSpaceTotal() {
		return spaceTotal;
	}
	public void setSpaceTotal(int spaceTotal) {
		this.spaceTotal = spaceTotal;
	}
	public int getSpaceUsed() {
		return spaceUsed;
	}
	public void setSpaceUsed(int spaceUsed) {
		this.spaceUsed = spaceUsed;
	}
	public double getSpacePricePerhour() {
		return spacePricePerhour;
	}
	public void setSpacePricePerhour(double spacePricePerhour) {
		this.spacePricePerhour = spacePricePerhour;
	}
	public String getSpaceDescription() {
		return spaceDescription;
	}
	public void setSpaceDescription(String spaceDescription) {
		this.spaceDescription = spaceDescription;
	}
	
}
