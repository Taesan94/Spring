package spring.di.entity;

public class WireLessMouse implements Mouse {

	@Override
	public void clickR() {
		System.out.println("[ 무선 R ]");
		
	}

	@Override
	public void clickL() {
		System.out.println("[ 무선 L ]");		
	}

	@Override
	public void mouseCheck() {
		System.out.println(" 무선마이스 연결 확인 ");
		clickR();
		clickL();		
	}
}
