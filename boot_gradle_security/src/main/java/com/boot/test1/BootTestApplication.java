package com.boot.test1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class BootTestApplication {
	
	public static void main(String[] args) {
		
		Logger logger = (Logger)LoggerFactory.getLogger(BootTestApplication.class);
		

		logger.trace("Hello world.");
		logger.debug("Hello world."); //debug level로 해당 메시지의 로그를 찍겠다.
		logger.info("Hello world.");
		logger.warn("Hello world.");
		logger.error("Hello world.");

		SpringApplication.run(BootTestApplication.class, args);
	}

}
