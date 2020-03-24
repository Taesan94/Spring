package com.boot.test1.vo;

//코로나 환자정보 
public class CoronaPatients {

 // 연번 
 private Integer serialNumber;

 // 환자번호 
 private String patientNumber;

 // 인적사항 
 private String patientInfos;

 // 감염경로 
 private String infectionRoute;

 // 확진일 
 private String definiteDate;

 // 거주지 
 private String address;

 // 격리시설 
 private String hospital;

 public Integer getSerialNumber() {
     return serialNumber;
 }

 public void setSerialNumber(Integer serialNumber) {
     this.serialNumber = serialNumber;
 }

 public String getPatientNumber() {
     return patientNumber;
 }

 public void setPatientNumber(String patientNumber) {
     this.patientNumber = patientNumber;
 }

 public String getPatientInfos() {
     return patientInfos;
 }

 public void setPatientInfos(String patientInfos) {
     this.patientInfos = patientInfos;
 }

 public String getInfectionRoute() {
     return infectionRoute;
 }

 public void setInfectionRoute(String infectionRoute) {
     this.infectionRoute = infectionRoute;
 }

 public String getDefiniteDate() {
     return definiteDate;
 }

 public void setDefiniteDate(String definiteDate) {
     this.definiteDate = definiteDate;
 }

 public String getAddress() {
     return address;
 }

 public void setAddress(String address) {
     this.address = address;
 }

 public String getHospital() {
     return hospital;
 }

 public void setHospital(String hospital) {
     this.hospital = hospital;
 }

@Override
public String toString() {
	return "CoronaPatients [serialNumber=" + serialNumber + ", patientNumber=" + patientNumber + ", patientInfos="
			+ patientInfos + ", infectionRoute=" + infectionRoute + ", definiteDate=" + definiteDate + ", address="
			+ address + ", hospital=" + hospital + "]";
}
 
}
