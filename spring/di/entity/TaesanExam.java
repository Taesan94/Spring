package spring.di.entity;

import org.springframework.beans.factory.annotation.Value;

public class TaesanExam implements Exam {

	@Value("10")
	private int kor;
	@Value("20")
	private int eng;
	@Value("30")
	private int math;
	@Value("40")
	private int com;
	
	public TaesanExam() {
		
	}
	
	public TaesanExam(int kor, int eng, int math, int com) {
		this.kor=kor;
		this.eng=eng;
		this.math=math;
		this.com=com;
	}
	
	@Override
	public int total() {
		return kor+eng+math+com;
	}

	@Override
	public float avg() {
		return total() / 4.0f;
	}

	@Override
	public String toString() {
		return "NewlecExam [kor=" + kor + ", eng=" + eng + ", math=" + math + ", com=" + com + "]";
	}
	
	
}
