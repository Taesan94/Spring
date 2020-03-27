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

		String url = "http://news.seoul.go.kr/welfare/archives/513105";
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
					
					info = info.replaceAll("'", "");
					info = info.replaceAll("‵", "");
					info = info.replaceAll("\\?", "");

					if(j==0) {
						String[] infoSplit = info.split(" ");
						patientVo.setSerialNumber(Integer.valueOf(infoSplit[0]));
						patientVo.setPatientNumber(infoSplit[1].substring(1,infoSplit[1].length()-1));
					}else if(j==1) {
						// System.out.println(" info : " + info );
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
					info = info.replaceAll("\\?", "");
					
					routeVo.setSerialNumber(serialNumber);
					routeVo.setRouteSeq(k+1);
					routeVo.setRouteDetail(info);

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
