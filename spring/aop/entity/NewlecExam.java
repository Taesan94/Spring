package spring.aop.entity;

public class NewlecExam implements Exam {

	private int kor;
	private int eng;
	private int math;
	private int com;

	public NewlecExam() {

	}

	public NewlecExam(int kor, int eng, int math, int com) {
		this.kor=kor;
		this.eng=eng;
		this.math=math;
		this.com=com;
	}

	@Override
	public int total() {

		int result = kor+eng+math+com;

		if ( kor > 100 || eng > 100 || math > 100 || com > 100 ) 
			throw new IllegalArgumentException(" 점수는 100점을 넘을 수 없습니다.");

		return result;
	}

	@Override
	public float avg() {
		float result = total() / 4.0f;
		return result;
	}

	@Override
	public String toString() {
		return "NewlecExam [kor=" + kor + ", eng=" + eng + ", math=" + math + ", com=" + com + "]";
	}

	public int getKor() {
		return kor;
	}

	public void setKor(int kor) {
		this.kor = kor;
	}

	public int getEng() {
		return eng;
	}

	public void setEng(int eng) {
		this.eng = eng;
	}

	public int getMath() {
		return math;
	}

	public void setMath(int math) {
		this.math = math;
	}

	public int getCom() {
		return com;
	}

	public void setCom(int com) {
		this.com = com;
	}

}
