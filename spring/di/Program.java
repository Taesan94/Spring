package spring.di;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import spring.di.ui.ExamConsole;

public class Program {

	public static void main(String[] args) {

		ApplicationContext context = 
				//new ClassPathXmlApplicationContext("spring/di/setting.xml");
				new AnnotationConfigApplicationContext(ExamAppConfig.class);
		
		//ExamConsole console = (ExamConsole)context.getBean("ExamConsole.class");
		ExamConsole console = (ExamConsole)context.getBean("console");
		
		console.print();

	}

}
