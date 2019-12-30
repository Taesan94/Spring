package spring.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import spring.di.entity.Exam;
import spring.di.entity.NewlecExam;

@ComponentScan( { "spring.di.ui", "spring.di.entity" })
@Configuration
public class ExamAppConfig {
	
	// 메소드 명이 bean id와같이 식별자 역할을 해준다.
	@Bean
	public Exam exam() {
		return new NewlecExam();
	}

}
