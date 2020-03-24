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

		List<CoronaPatients> patientList = new ArrayList<CoronaPatients>();
		List<PatientsRoute> routeList = new ArrayList<PatientsRoute>();

		// 환자정보 건수 == 이동경로 건수 동일한지 확인.
		if ( patient.size() != patientRoutes.size() ) {
			System.out.println(" [ ### Crawling Error ### ], 환자정보("+patient.size()+")와 이동경로+"+patientRoutes.size()+")의 건수가 동일하지않습니다");
		}else {
			System.out.println("============================== Crawling Start ==============================");

			//			List<CoronaPatients> patientList = new ArrayList<CoronaPatients>();
			//			List<PatientsRoute> routeList = new ArrayList<PatientsRoute>();

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
				}

				// 환자 1명의 이동경로
				Elements patientRoute = patientRoutes.get(i).select("li");

				int serialNumber = patientVo.getSerialNumber();
				PatientsRoute routeVo = new PatientsRoute();

				for ( int k = 0 ; k < patientRoute.size(); k++ ) {
					String info = patientRoute.get(k).text();

					routeVo.setSerialNumber(serialNumber);
					routeVo.setRouteSeq(k);
					routeVo.setRouteDetail(info);
				}
				patientList.add(patientVo);
				routeList.add(routeVo);
			}
		}// else

		for ( int i = 0 ; i < 3; i ++ ) {
			System.out.println(i + " 번째 patient : " + patientList.get(i).toString());
			System.out.println(i + " 번째 routeList : " + routeList.get(i).toString());
		}

	}

}
