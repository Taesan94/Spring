<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

</head>
<body>
	<h1>User Page</h1>
	
	<div id="move-cont2" class="move-cont">
			<!-- 확진자 이동경로 -->
		<div class="status-confirm">
			<h3 class="display-none" style="margin-top: 10px;">
				<span>서울 확진자 이동경로</span>
			</h3>
			<p style="text-align: right;">
				<a href="http://mediahub.seoul.go.kr/archives/1270596" class="jsTimerpopup-1200-1200-10-10 btn-new-default btn-purple-down ico-right" style="text-align: center !important;" target="_blank" title="새창" data-start="2020-01-16 09:00" data-end="2020-12-19 00:01" rel="noopener noreferrer">서울 확진자 이동경로 카드뉴스</a>
			</p>
			<p style="text-align: left;"></p>
			<h5 class="update-date">
				<strong>('20.03.23.00시 기준)</strong>
			</h5>
			<div class="btn-group">
				<button id="openAll" style="border: 1px solid #ccc;">전체 이동경로 펼치기</button>
				<button id="closeAll" style="border: 1px solid #ccc;">전체 이동경로 닫기</button>
			</div>
			<div id="patients">
				<strong>※ 서울 확진자 총 330명</strong>
				<div class="cont-page-wrap">
					<div id="cont-move-page4" class="cont-page">
						<table>
							<caption>서울 확진자 현황</caption>
							<colgroup>
								<col style="width: 13%;" />
								<col style="width: 20%;" />
								<col style="width: 20%;" />
								<col style="width: 10%;" />
								<col style="width: 12%;" />
								<col style="width: 25%;" />
							</colgroup>
							<thead>
								<tr>
									<th scope="col">연번<br />(#환자번호)
									</th>
									<th scope="col">인적사항<br />(성별, 출생년도)
									</th>
									<th scope="col">감염경로</th>
									<th scope="col">확진일</th>
									<th scope="col">거주지</th>
									<th scope="col">격리시설</th>
								</tr>
							</thead>
							<tbody>
								<tr id="patient" class="patient">
									<td rowspan="2">
										<p>330</p>
										<p>(#8917)</p>
									</td>
									<td class="name">한국인 (여, '96)</td>
									<td>#8709 접촉 추정</td>
									<td>3/22</td>
									<td>은평구</td>
									<td>서남병원</td>
								</tr>
								<tr>
									<td class="tdl" colspan="5">
										<ul>
											<li><strong>3월 20일~22일</strong> 확진자(#8709) 가족으로 자택에서 격리</li>
											<li><strong>3월 22일</strong> 09:40 은평구보건소 선별진료소(*앰블란스,마스크 착용) → 자택 (*앰블란스, 마스크 착용) → 20:00 국가지정격리병상(서남병원)으로 이송</li>
										</ul>
										<p style="text-align: right;">&lt;출처 : 은평구청 홈페이지&gt;</p>
									</td>
								</tr>
								<tr id="patient" class="patient">
									<td rowspan="2">
										<p>329</p>
										<p>(#8918)</p>
									</td>
									<td class="name">한국인 (여, '73)</td>
									<td>콜센터직원 접촉</td>
									<td>3/22</td>
									<td>서대문구</td>
									<td>생활치료센터</td>
								</tr>
								<tr>
									<td class="tdl" colspan="5">
										<ul>
											<li><strong>3월 11일~20일</strong> 확진자(#7805)의 접촉자로 자가격리</li>
											<li><strong>3월 21일</strong> 서대문구보건소 선별진료소에서 검체 체취</li>
											<li><strong>3월 22일</strong> 09:00시경 양성판정 → 14:30 생활치료센터로 이송</li>
										</ul>
										<p style="text-align: right;">&lt;출처 : 서대문청 홈페이지&gt;</p>
									</td>
								</tr>
								<tr id="patient" class="patient">
									<td rowspan="2">
										<p>328</p>
										<p>(#8915)</p>
									</td>
									<td class="name">한국인 (여, '63)</td>
									<td>콜센터직원 접촉</td>
									<td>3/22</td>
									<td>서대문구</td>
									<td>서북병원</td>
								</tr>
								<tr>
									<td class="tdl" colspan="5">
										<ul>
											<li><strong>3월 11일~20일</strong> 확진자(#7805)의 접촉자로 자가격리</li>
											<li><strong>3월 21일</strong> 서대문구보건소 선별진료소에서 검체 체취</li>
											<li><strong>3월 22일</strong> 09:00시경 양성판정 → 14:30 서북병원으로 이송</li>
										</ul>
										<p style="text-align: right;">&lt;출처 : 서대문청 홈페이지&gt;</p>
									</td>
								</tr>
								<tr id="patient" class="patient">
									<td rowspan="2">
										<p>327</p>
										<p>(#8914)</p>
									</td>
									<td class="name">한국인 (여, '93)</td>
									<td>해외 접촉 추정(미국)</td>
									<td>3/22</td>
									<td>강서구</td>
									<td>서남병원</td>
								</tr>
								<tr>
									<td class="tdl" colspan="5">
										<ul>
											<li><strong>1월 23일~ 3월 20일</strong> 미국 업무차 출장</li>
											<li><strong>3월 20일</strong> 16:30 인천공항 → 자택(자가용 이용, 마스크 착용) * 접촉자 없음</li>
											<li><strong>3월 21일</strong> 16:00 자택 → 강서보건소 선별진료소 검사(자가용 이용, 마스크 착용) *접촉자 없음)</li>
											<li><strong>3월 22일</strong> 09:00 양성판정 → 14:00 자택 → 서남병원(강서보건소 구급) 국가지정병상 이송</li>
										</ul>
										<p style="text-align: right;">&lt;출처 : 강서구청 홈페이지&gt;</p>
									</td>
								</tr>
								<tr id="patient" class="patient">
									<td rowspan="2">
										<p>326</p>
										<p>(#8916)</p>
									</td>
									<td class="name">한국인 (남, '97)</td>
									<td>해외 접촉 추정(스페인)</td>
									<td>3/22</td>
									<td>도봉구</td>
									<td>생활치료센터</td>
								</tr>
								<tr>
									<td class="tdl" colspan="5">
										<ul>
											<li><strong>3월 17일</strong> 스페인 체류 중 인천공항 귀국(마스크 착용, 공항리무진버스) 자택으로 귀가 후 주로 자택에서 휴식</li>
											<li><strong>3월 21일</strong> 17:00 동행한 친구가 확진판정을 받았다는 소식을 접한 후, 도봉구 선별진료소를 통한 검사를 실시</li>
											<li><strong>3월 22일</strong> 오전 확진판정 ※ 가족 이외에는 밀접접촉자가 없음</li>
										</ul>
										<p style="text-align: right;">&lt;출처 : 도봉구청 홈페이지&gt;</p>
									</td>
								</tr>
								<tr id="patient" class="patient">
									<td rowspan="2">
										<p>325</p>
										<p>(#8919)</p>
									</td>
									<td class="name">한국인 (남, '61)</td>
									<td>해외 접촉 추정(미국)</td>
									<td>3/22</td>
									<td>성북구</td>
									<td>국립중앙의료원</td>
								</tr></body>
</html>