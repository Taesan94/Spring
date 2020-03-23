package com.boot.test1.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.boot.test1.javaCode.APIUtills;
import com.boot.test1.vo.PerformanceInfo;

@Controller
public class ApiControllerXML {
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	// 공용 API 사용을위한 발급 KEY
	private String PERFORMANCE_KEY = "vDWln1xF6Puc10kOA44ETJ25WDiYYbpKt4Fl8FEbecL9jREmo1WVBGfJJPNRZ72PReOGo7YmBXswyxxmEEGB6Q%3D%3D";
	
	private APIUtills apiUtills = new APIUtills();
	
	// 공공API 호출, 공연정보
	@RequestMapping("/performanceAPI")
	public String callAPI_performance(HttpServletRequest request) throws IOException {

		String sido = request.getParameter("sido");
		String realmCode = request.getParameter("realmCode");
		String from = request.getParameter("from");
		String to   = request.getParameter("to");

		System.out.println( " sido : " + sido + ", realmCode : " + realmCode + ", from : " + from + ", to : " + to );

		StringBuilder urlBuilder = new StringBuilder("http://www.culture.go.kr/openapi/rest/publicperformancedisplays/realm"); /*URL*/
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + PERFORMANCE_KEY); /*Service Key*/
		urlBuilder.append("&" + URLEncoder.encode("sido","UTF-8") + "=" + URLEncoder.encode(sido, "UTF-8")); /**/
		urlBuilder.append("&" + URLEncoder.encode("realmCode","UTF-8") + "=" + URLEncoder.encode(realmCode, "UTF-8")); /*코드*/
		urlBuilder.append("&" + URLEncoder.encode("cPage","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*코드*/
		urlBuilder.append("&" + URLEncoder.encode("rows","UTF-8") + "=" + URLEncoder.encode("4", "UTF-8")); /**/
		urlBuilder.append("&" + URLEncoder.encode("from","UTF-8") + "=" + URLEncoder.encode(from, "UTF-8")); /**/
		urlBuilder.append("&" + URLEncoder.encode("to","UTF-8") + "=" + URLEncoder.encode(to, "UTF-8")); /**/

		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");

		System.out.println("Response code: " + conn.getResponseCode());

		BufferedReader rd;

		if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}

		StringBuilder sb = new StringBuilder();
		String line;

		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}

		rd.close();
		conn.disconnect();

		System.out.println(sb.toString());

		JSONObject jsonObject =  XML.toJSONObject(sb.toString());

		// String jsonString = jsonObject.toString();
		// System.out.println(" [ json 변환 ] " + jsonString );

		JSONObject jsonResponse = jsonObject.getJSONObject("response");
		// System.out.println(" [ response만 받아오기 ] " + jsonResponse.toString());

		JSONObject jsonResponseMsgBody = jsonResponse.getJSONObject("msgBody");
		// System.out.println(" [ msgBody만 받아오기 ] " + jsonResponseMsgBody.toString());

		int totalCount = jsonResponseMsgBody.getInt("totalCount");
		int rows = jsonResponseMsgBody.getInt("rows");
		
		// 최대페이지 수
		int maxPage = (totalCount%rows)==0 ? (totalCount/rows) : (totalCount/rows)+1;
		
		// 마지막 페이지 건수.. 1이면 JSON에 담아준다.
		int lastPageNum = totalCount - ( (maxPage-1) * rows);
		
		int cPage = jsonResponseMsgBody.getInt("cPage");

		log.info(" totalCount : " + totalCount + ", maxPage : " + maxPage + " lastpageNum : " + lastPageNum);
		log.info( " jsonResponseMsgBody : " + jsonResponseMsgBody.toString());

		if ( totalCount == 0 ) {
			log.info(" 해당 조건에 맞는 공연정보가 존재하지 않습니다.");
		}else {

			List<PerformanceInfo> performanceInfo = new ArrayList<PerformanceInfo>();

			// 데이터가 1건인 경우에는 JSON으로 받도록.
			if ( lastPageNum == 1 && cPage == maxPage || totalCount == 1 || rows == 1 ) {
				log.info(" 데이터가 1건입니다.");
				JSONObject perforInfo = (JSONObject)jsonResponseMsgBody.get("perforList");
				performanceInfo.add(apiUtills.makePeformerJsonResult(perforInfo));

			}else {
				JSONArray perforList = (JSONArray)jsonResponseMsgBody.get("perforList");

				for ( int i = 0 ; i < perforList.length(); i++ ) {
					JSONObject perforInfo = (JSONObject)perforList.get(i);
					performanceInfo.add(apiUtills.makePeformerJsonResult(perforInfo));
					log.info( i + " 번 째 perforValue : " + perforInfo.toString());
				}
			}
			
			request.setAttribute("performanceInfos", performanceInfo);
		}
		return "performanceAPI";
	}
	
	@RequestMapping("/goDetail")
	public String detail(HttpServletRequest request, int seq) throws IOException {
		
		log.info(" seq : " + seq );
		
	    StringBuilder urlBuilder = new StringBuilder("http://www.culture.go.kr/openapi/rest/publicperformancedisplays/d/"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=" +PERFORMANCE_KEY); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("seq","UTF-8") + "=" + URLEncoder.encode(String.valueOf(seq), "UTF-8")); /**/
        
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        }else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        
        StringBuilder sb = new StringBuilder();
        String line;
        
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());
        
		JSONObject jsonObject =  XML.toJSONObject(sb.toString());
		JSONObject jsonResponse = jsonObject.getJSONObject("response");
		JSONObject jsonResponseMsgBody = jsonResponse.getJSONObject("msgBody");
		
		JSONObject perforInfo = (JSONObject)jsonResponseMsgBody.get("perforInfo");
		
		request.setAttribute("title", perforInfo.get("title"));
		request.setAttribute("startDate", perforInfo.getInt("startDate"));
		request.setAttribute("endDate", perforInfo.getInt("endDate"));
		request.setAttribute("place" , perforInfo.get("place"));
		request.setAttribute("area", perforInfo.get("area"));
		request.setAttribute("price", perforInfo.get("price"));
		request.setAttribute("phone", perforInfo.get("phone"));
		request.setAttribute("imgUrl", perforInfo.get("imgUrl"));
		request.setAttribute("placeUrl", perforInfo.get("placeUrl"));

		return "performanceDetail";
	}
}
