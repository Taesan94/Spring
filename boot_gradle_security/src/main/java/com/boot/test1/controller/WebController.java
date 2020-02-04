package com.boot.test1.controller;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.boot.test1.mapper.AccountMapper;
import com.boot.test1.mapper.TestMapper;
import com.boot.test1.vo.Account;

@MapperScan("com.boot.test1.mapper")
@Controller
public class WebController {
	
	@Autowired
	TestMapper testMapper;
	
	@Autowired
	AccountMapper accountMapper;
	
	@RequestMapping("/")
	public String startPoint() {
		System.out.println(" / 타는지 ");
		System.out.println(" DB에서 가지고온 현재시간 : " + testMapper.getCurrentTime() );
		
		return "index";
	}	
}
