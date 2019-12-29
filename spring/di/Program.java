package spring.di;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import spring.di.entity.Exam;
import spring.di.entity.NewlecExam;
import spring.di.ui.ExamConsole;

public class Program {

	public static void main(String[] args) {

		//ExamConsole console = new InlineExamConsole(exam); // DI
		//ExamConsole console = new GridExamConsole(exam);

		/*
			new InlineExamConsole(exam)
			new FridExamConsole(exam)
			
			같은 메서드를 호출하게되는 위 코드의 수정을 대신해줄 수 있도록 하는 기능이 Spring의 DI다.

		 */
		
		// 해당 클래스 사용하기위해서
		// Maven 프로젝트로 바꿔서 빌드툴을 사용하도록 하였고
		// Maven Repository에가서 Spring context를 dependency해주었음.
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("spring/di/setting.xml");

		
		//ExamConsole console = (ExamConsole)context.getBean("console");
		ExamConsole console = context.getBean(ExamConsole.class);
		
		console.print();

	}

}
