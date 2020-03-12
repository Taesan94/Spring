package com.boot.test1.controller;

import javax.servlet.http.HttpServletRequest;

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

@Controller
public class AccountController {

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

	// goHome
	@RequestMapping("/goHome")
	public String goHome() {
		return "index";
	}


}
