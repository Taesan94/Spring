package com.boot.test1.vo;

//코로나 확진자 이동경로 
public class PatientsRoute {

	// 연번 
	private Integer serialNumber;

	// 경로seq 
	private Integer routeSeq;

	// 경로 방문일자 
	private String visitedDate;

	// 경로상세 
	private String routeDetail;

	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Integer getRouteSeq() {
		return routeSeq;
	}

	public void setRouteSeq(Integer routeSeq) {
		this.routeSeq = routeSeq;
	}

	public String getVisitedDate() {
		return visitedDate;
	}

	public void setVisitedDate(String visitedDate) {
		this.visitedDate = visitedDate;
	}

	public String getRouteDetail() {
		return routeDetail;
	}

	public void setRouteDetail(String routeDetail) {
		this.routeDetail = routeDetail;
	}

	@Override
	public String toString() {
		return "Patientsroute [serialNumber=" + serialNumber + ", routeSeq=" + routeSeq + ", visitedDate=" + visitedDate
				+ ", routeDetail=" + routeDetail + "]";
	}

}