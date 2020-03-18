package com.boot.test1.javaCode;

import java.io.BufferedReader;
import java.io.IOException;
/* Java 샘플 코드 */
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 시군구정보 API
 *
 */
public class ApiTest3 {
	
	private static Logger log = LoggerFactory.getLogger("ApiTest3");
	
	private final static String SERVICE_KEY = "1A56913E-E5F4-34E9-B111-F807AAB7E2E3";
	private final static String DOMAIN_URL = "http://www.test.com";

    public static void main(String[] args) throws IOException {
    	
    	 sigungu_JSON(); // 시군구정보조회
    	// sigungu_XML();
    	
    }
    
    private static void sigungu_JSON() throws IOException {
    	
    	 StringBuilder urlBuilder = new StringBuilder("http://api.vworld.kr/req/data?service=data&request=GetFeature&data=LT_C_ADSIGG_INFO&key="+SERVICE_KEY+"&domain="+DOMAIN_URL); /*URL*/
         
    	 String area ="용인";
    	 
         urlBuilder.append("&attrFilter=sig_kor_nm:like:"+ URLEncoder.encode(area, "UTF-8")); /**/
         
         System.out.println(urlBuilder.toString());

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
			
			JSONObject response = (JSONObject)jsonObject.get("response");
			
			String status = response.get("status").toString();
			
			if ( status.equals("NOT_FOUND")) {
				System.out.println(" 데이터 없음. ");
				return ;
			}
			
			// 여기에 total 가져오는 코드로 테스트 한번해보자.. !!
			JSONObject page = (JSONObject)response.get("page");
			String total = page.get("total").toString();
			System.out.println(" total : " + total );
			
			JSONObject result = (JSONObject)response.get("result");
			JSONObject featureCollection = (JSONObject)result.get("featureCollection");
			JSONArray features = (JSONArray)featureCollection.get("features");
			
			for ( int i = 0 ; i < features.size(); i ++ ) {
				
				JSONObject feature = (JSONObject)features.get(i);
				
				JSONObject properties = (JSONObject)feature.get("properties");
				
				String full_nm = (String)properties.get("full_nm");
				String sig_kor_nm = (String)properties.get("sig_kor_nm");
				
				System.out.println(" sig_kor_nm : " + sig_kor_nm );
				
				String answer = "";
				
				if ( full_nm != null && ( full_nm.contains("특별시") || full_nm.contains("광역시") )) {
					answer = "[ **특별시 또는 **광역시 데이터 ] +" + " " + sig_kor_nm ;
				}else {
					
					String si = area; // 선택된 값.
					
					// 조회결과에 시가 포함되어있다면
					String checkSi = si + "시";
					
					if ( sig_kor_nm.contains(checkSi)) {
						
						System.out.println(" 포함되있음 "); 
						
						int index = sig_kor_nm.indexOf(checkSi);
						index += checkSi.length();
						
						System.out.println(" index : " + index );
						
						String prefix = sig_kor_nm.substring(0,index);
						String suffix = sig_kor_nm.substring(index);
						
						System.out.println( " prefix : " + prefix);
						System.out.println( " suffix : " + suffix);
						
						answer = prefix + " " + suffix ;
						
					}
				}//else
				
				System.out.println( i + " 번째  answer : " + answer );
				
			}//for 
    }
    
    private static void sigungu_XML() throws IOException {
    	
   	 StringBuilder urlBuilder = new StringBuilder("http://api.vworld.kr/req/data?service=data&request=GetFeature&data=LT_C_ADSIGG_INFO&key="+SERVICE_KEY+"&domain="+DOMAIN_URL); /*URL*/
        
   	 String area ="용인";
   	 
        urlBuilder.append("&attrFilter=sig_kor_nm:like:"+ URLEncoder.encode(area, "UTF-8")); /**/
        urlBuilder.append("&format=xml");
        
        System.out.println(urlBuilder.toString());

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/xml");
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
        
   }
}