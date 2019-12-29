package spring.di;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import spring.di.ui.Computer;

public class Student {

	public static void main(String[] args) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("spring/di/setting2.xml");

		//Computer computer= context.getBean(Computer.class);
		Computer computer = (Computer)context.getBean("computer");
		
		computer.mouseCheck();
	}
}
