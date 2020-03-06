package com.boot.test1.vo;

public class PerformanceInfo {
	
	private String seq;      
	private String title;    
	private String startDate;
	private String endDate;  
	private String place;    
	private String realmName;
	private String area;     
	private String thumbnail;
	
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getRealmName() {
		return realmName;
	}
	public void setRealmName(String realmName) {
		this.realmName = realmName;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	@Override
	public String toString() {
		return "PerformanceInfo [seq=" + seq + ", title=" + title + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", place=" + place + ", realmName=" + realmName + ", area=" + area + ", thumbnail=" + thumbnail + "]";
	}
}
