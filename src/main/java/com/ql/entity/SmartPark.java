package com.ql.entity;

import java.util.List;

public class SmartPark implements Comparable{
	private String id;
	private String name;
	private double longitude;
	private double latitude;
	private String spacePricePerhour;
	private int supportMobilePay;
	private double distance;
	private List<SmartSpace> spaceList;
	private int freeSpace;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getSpacePricePerhour() {
		return spacePricePerhour;
	}
	public void setSpacePricePerhour(String spacePricePerhour) {
		this.spacePricePerhour = spacePricePerhour;
	}
	public int getSupportMobilePay() {
		return supportMobilePay;
	}
	public void setSupportMobilePay(int supportMobilePay) {
		this.supportMobilePay = supportMobilePay;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	
	public List<SmartSpace> getSpaceList() {
		return spaceList;
	}
	public void setSpaceList(List<SmartSpace> spaceList) {
		this.spaceList = spaceList;
	}
	
	
	public int getFreeSpace() {
		return freeSpace;
	}
	public void setFreeSpace(int freeSpace) {
		this.freeSpace = freeSpace;
	}
	@Override
	public int compareTo(Object o) {
		return new Double(this.distance).compareTo(new Double(((SmartPark)o).getDistance()));
	}
}
