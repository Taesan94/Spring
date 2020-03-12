package com.boot.test1.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.boot.test1.javaCode.APIUtills;
import com.boot.test1.vo.CoronaVirusNews;
import com.boot.test1.vo.MaskInfos;

@Controller
public class ApiControllerJSON {
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	// 공용 API 사용을위한 발급 KEY
	private String PERFORMANCE_KEY = "1";
	
	// 다음 API 사용을위한 id, ( 비로그인 )
	private String clientId = "2"; //애플리케이션 클라이언트 아이디값"
	private String clientSecret = "3"; //애플리케이션 클라이언트 시크릿값"
	
	private APIUtills apiUtills = new APIUtills();
	
	// 검색 조회 페이지로 이동.
	@RequestMapping("/searchAPI")
	public String searchAPI(HttpServletRequest request) throws IOException {

		StringBuilder urlBuilder = new StringBuilder("https://openapi.naver.com/v1/search/news.json"); /*URL*/
		urlBuilder.append("?" + URLEncoder.encode("query","UTF-8") + "="+ URLEncoder.encode("코로나", "UTF-8")); /* 검색 키워드  */
		urlBuilder.append("&" + URLEncoder.encode("display","UTF-8") + "=" + URLEncoder.encode("30", "UTF-8")); /* 출력건수, 10(기본), 100(최대) */
		urlBuilder.append("&" + URLEncoder.encode("start","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /* 검색 시작위치, 1(기본), 1000(최대) */
		urlBuilder.append("&" + URLEncoder.encode("sort","UTF-8") + "=" + URLEncoder.encode("date", "UTF-8")); /* 정렬 옵션, sim(유사도순), date(날짜순) */

		Map<String, String> requestHeaders = new HashMap<>();
		requestHeaders.put("X-Naver-Client-Id", clientId);
		requestHeaders.put("X-Naver-Client-Secret", clientSecret);

		String responseBody = apiUtills.get(urlBuilder.toString(),requestHeaders);
		
		JSONObject jsonObject = new JSONObject();

		try {
			JSONParser parser = new JSONParser();
			jsonObject = (JSONObject)parser.parse(responseBody);
		}catch(ParseException e) {
			e.printStackTrace();
		}
		
		JSONArray items = (JSONArray)jsonObject.get("items");
		
		List<CoronaVirusNews> newsInfos = new ArrayList<CoronaVirusNews>();
		
		for ( int i=0; i < items.size(); i++ ) {
			
			JSONObject newsInfo = (JSONObject)items.get(i);
			CoronaVirusNews news = new CoronaVirusNews();
			
			news.setTitle(newsInfo.get("title").toString());
			news.setOriginallink(newsInfo.get("originallink").toString());
			news.setLink(newsInfo.get("link").toString());
			news.setDescription(newsInfo.get("description").toString());
			news.setPubDate(newsInfo.get("pubDate").toString());
			
			newsInfos.add(news);
		}
		
		request.setAttribute("newsInfos", newsInfos);

		System.out.println(responseBody);
		
		return "/searchAPI";
	}
	
	// mask정보 
		@RequestMapping("/maskAPI")
		public String coronaMask(HttpServletRequest request) throws IOException {

			StringBuilder urlBuilder = new StringBuilder("https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1/storesByAddr/json"); /*URL*/

			String area = "서울특별시 종로구";
			
			urlBuilder.append("?" + URLEncoder.encode("address","UTF-8") + "="+ URLEncoder.encode(area, "UTF-8")); /* 검색 키워드  */
			
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
			
			JSONObject jsonObject = new JSONObject();

			try {
				JSONParser parser = new JSONParser();
				jsonObject = (JSONObject)parser.parse(sb.toString());
			}catch(ParseException e) {
				e.printStackTrace();
			}
			
			Long count = (Long)jsonObject.get("count");
			
			log.info(" count : " + count );
			
			if ( count < 1 ) {
				log.info(" 정보없음 !!! " );
				return "maskAPI";
			}
			
			List<MaskInfos> maskInfos = new ArrayList<MaskInfos>();
			
			if ( count == 1 ) {
				JSONObject maskInfo = (JSONObject)jsonObject.get("stores");
				MaskInfos mask = apiUtills.makeMaskJsonResult(maskInfo);
				maskInfos.add(mask);
			}else {
				JSONArray stores = (JSONArray)jsonObject.get("stores");
				
				for ( int i=0; i < stores.size(); i++ ) {
					JSONObject maskInfo = (JSONObject)stores.get(i);
					MaskInfos mask = apiUtills.makeMaskJsonResult(maskInfo);
					maskInfos.add(mask);
				}
			}
			
			request.setAttribute("maskInfos", maskInfos);
			
			return "maskAPI";
		}
	
}
