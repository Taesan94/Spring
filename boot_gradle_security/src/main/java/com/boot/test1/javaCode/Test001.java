package com.boot.test1.javaCode;

public class Test001 {

	public static void main(String[] args) {
		
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
