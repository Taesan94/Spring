package com.boot.test1.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boot.test1.mapper.AccountMapper;
import com.boot.test1.service.AccountService;
import com.boot.test1.vo.PerformanceInfo;

@Controller
public class AccountController {

	// 공용 API 사용을위한 발급 KEY
	private String PERFORMANCE_KEY = "KEY 값 노출 없이..";

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
		String jsonString = jsonObject.toString();
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
				performanceInfo.add(makeJSONresult(perforInfo));

			}else {
				JSONArray perforList = (JSONArray)jsonResponseMsgBody.get("perforList");

				for ( int i = 0 ; i < perforList.length(); i++ ) {
					JSONObject perforInfo = (JSONObject)perforList.get(i);
					performanceInfo.add(makeJSONresult(perforInfo));
					log.info( i + " 번 째 perforValue : " + perforInfo.toString());
				}
			}
			
			request.setAttribute("performanceInfos", performanceInfo);
		}
		return "performanceAPI";
	}
	
	private PerformanceInfo makeJSONresult(JSONObject perforInfo) {
		
		PerformanceInfo info = new PerformanceInfo();

		info.setSeq( String.valueOf(perforInfo.getInt("seq"))) ;
		info.setStartDate(String.valueOf(perforInfo.getInt("startDate")));
		info.setEndDate(String.valueOf(perforInfo.getInt("endDate")));
		info.setTitle(perforInfo.getString("title"));
		info.setPlace(perforInfo.getString("place"));
		info.setRealmName(perforInfo.getString("realmName"));
		info.setArea(perforInfo.getString("area"));
		info.setThumbnail(perforInfo.getString("thumbnail"));
		
		return info;
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

	// goHome
	@RequestMapping("/goHome")
	public String goHome() {
		return "index";
	}


}
