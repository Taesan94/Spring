/**
 * 
 */
package com.boot.test1.javaCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.json.JSONObject;

import com.boot.test1.vo.MaskInfos;
import com.boot.test1.vo.PerformanceInfo;

/**
 * @author API Utils
 *
 */
public class APIUtills {
	
	/**
	 * 
	 * NAVER 검색 API 연동 시 Connection정보 get
	 * @param apiUrl
	 * @param requestHeaders
	 * @return
	 */
	public String get(String apiUrl, Map<String, String> requestHeaders){
		HttpURLConnection con = connect(apiUrl);
		try {
			con.setRequestMethod("GET");
			for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
				con.setRequestProperty(header.getKey(), header.getValue());
			}

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
				return readBody(con.getInputStream());
			} else { // 에러 발생
				return readBody(con.getErrorStream());
			}
		} catch (IOException e) {
			throw new RuntimeException("API 요청과 응답 실패", e);
		} finally {
			con.disconnect();
		}
	}

	/**
	 * NAVER 검색 API 연동 시 connect기능 수행
	 * @param apiUrl
	 * @return
	 */
	public HttpURLConnection connect(String apiUrl){
		try {
			URL url = new URL(apiUrl);
			return (HttpURLConnection)url.openConnection();
		} catch (MalformedURLException e) {
			throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
		} catch (IOException e) {
			throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
		}
	}

	/**
	 * NAVER 검색 API 연동 시 body정보  받아오기.
	 * @param body
	 * @return
	 */
	public String readBody(InputStream body){
		InputStreamReader streamReader = new InputStreamReader(body);

		try (BufferedReader lineReader = new BufferedReader(streamReader)) {
			StringBuilder responseBody = new StringBuilder();

			String line;
			while ((line = lineReader.readLine()) != null) {
				responseBody.append(line);
			}

			return responseBody.toString();
		} catch (IOException e) {
			throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
		}
	}
	/**
	 * 공연정보 JSON객체 만들기
	 * @param perforInfo
	 * @return
	 */
	public PerformanceInfo makePeformerJsonResult(JSONObject perforInfo) {
		
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
	
	/**
	 * mask정보 JSON만들기 
	 * @param perforInfo
	 * @return
	 */
	public MaskInfos makeMaskJsonResult(org.json.simple.JSONObject maskInfo) {
		
		MaskInfos mask = new MaskInfos();
		
		mask.setAddr(checkNull(maskInfo.get("addr")).toString());
		mask.setCode(checkNull(maskInfo.get("code")).toString());
		mask.setCreated_at(checkNull(maskInfo.get("created_at")).toString());
		mask.setLat(checkNull(maskInfo.get("lat")).toString());
		mask.setLng(checkNull(maskInfo.get("lng")).toString());
		mask.setName(checkNull(maskInfo.get("name")).toString());
		mask.setRemain_stat(checkNull(maskInfo.get("remain_stat")).toString());
		mask.setStock_at(checkNull(maskInfo.get("stock_at")).toString());
		mask.setType(checkNull(maskInfo.get("type")).toString());
		
		return mask;
	}
	
	private Object checkNull( Object word ) {
		if ( word == null ) {
			return ""; 
		}
		return word;
	}
	

}
