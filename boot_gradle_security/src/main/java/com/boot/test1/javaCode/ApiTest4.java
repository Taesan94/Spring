package com.boot.test1.javaCode;

import java.io.BufferedReader;
import java.io.IOException;
/* Java 샘플 코드 */
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 공적마스크 재고정보 API
 *
 */
public class ApiTest4 {

	private static Logger log = LoggerFactory.getLogger("ApiTest4");

	public static void main(String[] args) throws IOException {

		sigungu_JSON(); // 시군구정보조회
		// sigungu_XML();

	}

	private static void sigungu_JSON() throws IOException {

		StringBuilder urlBuilder = new StringBuilder("https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1/storesByAddr/json"); /*URL*/

		String area ="서울특별시 강남구";

		urlBuilder.append("?address="+ URLEncoder.encode(area, "UTF-8")); /**/

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

	}
}