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

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boot.test1.javaCode.APIUtills;
import com.boot.test1.mapper.AccountMapper;
import com.boot.test1.service.AccountService;
import com.boot.test1.vo.CoronaVirusNews;
import com.boot.test1.vo.PerformanceInfo;

@Controller
public class AccountController {

	// 공용 API 사용을위한 발급 KEY
	private String PERFORMANCE_KEY = "1";
	
	// 다음 API 사용을위한 id, ( 비로그인 )
	private String clientId = "2"; //애플리케이션 클라이언트 아이디값"
	private String clientSecret = "3"; //애플리케이션 클라이언트 시크릿값"
	
	private APIUtills apiUtills = new APIUtills();
	
	@Autowired
	AccountService accountService;

	@Autowired
	AccountMapper accountMapper;

	Logger log = LoggerFactory.getLogger(this.getClass());

	// LOGIN
	@RequestMapping(value = "/login" )
	public String login(Model model, HttpServletRequest req) {

		log.info("### /login 입니다 ");
		return "loginPage";
	}

	// LOGIN SUCCESS	
	@RequestMapping("/loginSuccess")
	public String loginSuccess() {
		return "index";
	}

	// LOGIN Fail	
	@GetMapping("/loginFail")
	@ResponseBody
	public String loginFail() {
		return "Fail !";
	}

	// jqgrid 사용해보기
	@RequestMapping("/useJqGrid")
	public String useJqGrid() {
		return "useJqGrid";
	}

	// 공연정보 조회 페이지로 이동.
	@RequestMapping("/goPerformancePage")
	public String goPerformancePage() {
		return "/performanceSelectPage";
	}
	
	// 검색 조회 페이지로 이동.
	@RequestMapping("/goSearchPage")
	public String goSearchPage() {
		return "/searchPage";
	}
	
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
		
		org.json.simple.JSONObject jsonObject = new org.json.simple.JSONObject();

		try {
			JSONParser parser = new JSONParser();
			jsonObject = (org.json.simple.JSONObject)parser.parse(responseBody);
		}catch(ParseException e) {
			e.printStackTrace();
		}
		org.json.simple.JSONArray items = (org.json.simple.JSONArray)jsonObject.get("items");
		
		List<CoronaVirusNews> newsInfos = new ArrayList<CoronaVirusNews>();
		
		for ( int i=0; i < items.size(); i++ ) {
			
			org.json.simple.JSONObject newsInfo = (org.json.simple.JSONObject)items.get(i);
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

	// goHome
	@RequestMapping("/goHome")
	public String goHome() {
		return "index";
	}


}
