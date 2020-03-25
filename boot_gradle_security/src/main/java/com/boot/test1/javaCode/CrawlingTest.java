package com.boot.test1.javaCode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.boot.test1.vo.CoronaPatients;
import com.boot.test1.vo.PatientsRoute;

public class CrawlingTest {

	public static void main(String[] args) {
		seoul();
	}

	private static void seoul() {

		String url = "http://www.seoul.go.kr/coronaV/coronaStatus.do";
		Document doc = null ;

		try {
			doc = Jsoup.connect(url).get();
		}catch(IOException e) {
			e.printStackTrace();
		}

		// 환자정보리스트
		Elements patients = doc.select("div#patients");

		// 환자정보 ( 연번(#환자번호), 인적사항(성별,출생년도), 감염경로, 확진일, 거주지, 격리시설 )
		Elements patient = patients.select(".patient");

		// 이동경로
		Elements patientRoutes = patients.select(".tdl");

		// 환자정보 건수 == 이동경로 건수 동일한지 확인.
		if ( patient.size() != patientRoutes.size() ) {
			System.out.println(" [ ### Crawling Error ### ], 환자정보("+patient.size()+")와 이동경로("+patientRoutes.size()+")의 건수가 동일하지않습니다");
		}else {
			System.out.println("============================== Crawling Start ==============================");

			List<CoronaPatients> patientList = new ArrayList<CoronaPatients>();
			List<PatientsRoute> routeList = new ArrayList<PatientsRoute>();

			// 모든 환자정보 수만큼 for
			for ( int i = 0; i < patient.size(); i++ ) {

				// 환자 1명의 정보
				Elements patientInfo = patient.get(i).select("td");

				CoronaPatients patientVo = new CoronaPatients();
				for ( int j = 0 ; j < patientInfo.size(); j++ ) {

					String info = patientInfo.get(j).text();

					if(j==0) {
						String[] infoSplit = info.split(" ");
						patientVo.setSerialNumber(Integer.valueOf(infoSplit[0]));
						patientVo.setPatientNumber(infoSplit[1].substring(1,infoSplit[1].length()));
					}else if(j==1) {
						patientVo.setPatientInfos(info);
					}else if(j==2) {
						patientVo.setInfectionRoute(info);
					}else if(j==3) {
						patientVo.setDefiniteDate(info);
					}else if(j==4) {
						patientVo.setAddress(info);
					}else if(j==5) {
						patientVo.setHospital(info);
					}
				}//for2

				patientList.add(patientVo);

				// 환자 1명의 이동경로
				Elements patientRoute = patientRoutes.get(i).select("li");

				int serialNumber = patientVo.getSerialNumber();

				for ( int k = 0 ; k < patientRoute.size(); k++ ) {

					PatientsRoute routeVo = new PatientsRoute();
					String info = patientRoute.get(k).text();

					routeVo.setSerialNumber(serialNumber);
					routeVo.setRouteSeq(k+1);


					String visitedDate = "확인중";
					String routeDetail = info;

					if ( info.length() > 12 ) {

						int typeCheck = info.indexOf("~");
						int index = info.indexOf("일")+1;

						// ~가존재하는경우, M월 DD일~ 의경우 index는 6이지만 띄어쓰기를 고려하여 2의여유를 더 주었음.
						if ( typeCheck != -1 && typeCheck < 8) {
							
							// 기본10자면 M월DD일~MM일이 가능하지만, 띄어쓰기가 규칙적이지않아 여유있게 11을 줌.
							String dateCheck =info.substring(0,11);
							
							// 뒤에있는 "일"의 index를 가지고온다.
							index = dateCheck.lastIndexOf("일") +1;
						}

						visitedDate = info.substring(0,index).trim();
						routeDetail = info.substring(index,info.length()).trim();
					}

					routeVo.setVisitedDate(visitedDate);
					routeVo.setRouteDetail(routeDetail);

					routeList.add(routeVo);

				}//for3
			}//for 1
			show( 20, patientList, routeList);
		}// else

	}

	static int j = 0 ;

	private static void show( int count, List<CoronaPatients> patientList , List<PatientsRoute> routeList  ) {
		for ( int i = 0 ; i < count; i ++ ) {

			CoronaPatients p = patientList.get(i);
			PatientsRoute r = routeList.get(j);

			System.out.println(i + " 번째 patient : " + p.toString());

			//			System.out.println(" p.getSerialNumber() : " + p.getSerialNumber() + ", r.getSerialNumber() : " + r.getSerialNumber());
			//			System.out.println(" boolean : "  + p.getSerialNumber().equals(r.getSerialNumber()) );

			int seq = 0;
			while ( j < routeList.size() && p.getSerialNumber().equals( r.getSerialNumber()) ) {
				System.out.println(p.getSerialNumber() + "의 " + seq++ +"번째 경로 : " + routeList.get(j).toString());
				r = routeList.get(++j);
			}
		}
	}

}
