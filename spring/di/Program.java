package spring.di;

import spring.di.entity.Exam;
import spring.di.entity.NewlecExam;
import spring.di.ui.ExamConsole;
import spring.di.ui.GridExamConsole;
import spring.di.ui.InlineExamConsole;

public class Program {

	public static void main(String[] args) {

		Exam exam = new NewlecExam();
		ExamConsole console = new InlineExamConsole(exam); // DI
		//ExamConsole console = new GridExamConsole(exam);

		/*
			new InlineExamConsole(exam)
			new FridExamConsole(exam)
			
			같은 메서드를 호출하게되는 위 코드의 수정을 대신해줄 수 있도록 하는 기능이 Spring의 DI다.

		 */
		console.print();

	}

}
