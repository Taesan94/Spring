package com.boot.test1.javaCode;

public class Test001 {

	public static void main(String[] args) {
		
		String a = "2월 19일13:00 **음식점 → 북가좌2동,남가좌2동, 홍은2동주민센터 → 15:30 가좌보건지소, 북가좌1동주민센터 → **모텔 숙박 →?18:43 편의점";
		System.out.println("BEFORE : " + a);
		a= a.replaceAll("\\?", "");
		System.out.println("AFTER : " + a);
		
		String title = "<div class=\"board-title-text txt\" style=\"max-width: 630px;\">"+ "abcd" + "</div>";
		System.out.println(" title : " + title );
		
		String sido = "sido0101";
		sido = sido.substring(3,5);
		System.out.println(" sido : " + sido );

		String date = "3월 16일 15:00 뉴욕 → 인천국제공항(대한항공KE082)" ;
		
		int type2 = date.indexOf("~");
		
		int index = date.indexOf("일")+1;
		
		if ( type2 != -1 ) {
			// 기본10자면 M월DD일~MM일이 가능하지만, 띄어쓰기가 규칙적이지않아 여유있게 13을 줌.
			String date2 =date.substring(0,13);
			// 뒤에있는 "일"의 index를 가지고온다.
			index = date2.lastIndexOf("일") +1;
		}
		
		String resultDate = date.substring(0,index).trim();
		String resultContent = date.substring(index,date.length()).trim();
		
		
		System.out.println( "resultDate :" + resultDate );
		System.out.println( "resultContent :" + resultContent );
	}

}
