package spring.di.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import spring.di.entity.Exam;

@Component("console")
public class InlineExamConsole implements ExamConsole {

	//@Qualifier("exam1")
	@Autowired(required=false)
	private Exam exam;		

	public InlineExamConsole() {
		System.out.println("기본생성자");
	}

	public InlineExamConsole(Exam exam) {		
		this.exam = exam;
	}

	@Override
	public void print() {
		if(exam != null ) {
			System.out.println(" 현재 생성된 Exam 의 클래스는 ?? " + exam.getClass());
			System.out.printf("total is %d, avg is %f\n", exam.total(), exam.avg());
		}
		else
			System.out.println(" null 임다 ");
	}

	@Override
	public void setExam(Exam exam) {
		this.exam=exam;
	}

}
