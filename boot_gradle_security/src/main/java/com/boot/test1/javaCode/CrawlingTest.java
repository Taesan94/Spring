package com.boot.test1.javaCode;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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

		Elements patients = doc.select("div#patients");

		Elements patient = patients.select(".patient");



		System.out.println("=================================================================================");

		// System.out.println("patient : " + patient.get(0));

		Elements patient_01 = patient.get(0).select("td");
		for ( int i = 0 ; i < patient_01.size(); i++ ) {

			Element patientE = patient_01.get(i);
			
			if( i == 0 ) {
				String[] splitS = patientE.text().split(" ");
				System.out.println(splitS[0]);
				System.out.println(splitS[1].substring(1,6));
			}else
				System.out.println(patientE.text());
		}

		Elements tdl = patients.select(".tdl");

		// System.out.println("tdl : " + tdl.get(0));

		Elements tdl_1 = tdl.get(0).select("li");
		for ( int i = 0 ; i < tdl_1.size(); i++ ) {
			System.out.println(tdl_1.get(i).text());
		}

		System.out.println(" tdl Info : " + tdl.size() );

		System.out.println(" patient Info : " + patient.size() );

		// System.out.println("tdl : " + tdl.text());

		//		for ( Element el : element.select("td")) {
		//				System.out.println(el.text());
		//		}


		//		Elements divs = element.select("div");
		//		
		//		
		//		for(Element el : divs.select("tbody")) { 
		//			
		//			for ( Element el2 : el.select("tr")) {
		//				
		//				String row = el2.select("th").text();
		//				System.out.println(" row : " + row );
		//				Elements data = el2.select("td");
		//				
		//				for ( Element td : data ) {
		//					System.out.println(td.text());
		//				}
		//				
		//				
		//				// System.out.println(" row : " + row + "\n" + " data : " + data );
		//			}
		//			
		//		}






	}

}
