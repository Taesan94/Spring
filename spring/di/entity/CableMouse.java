package spring.di.entity;

public class CableMouse implements Mouse{
	@Override
	public void clickR() {
		System.out.println("[ 유선 R ]");		
	}

	@Override
	public void clickL() {
		System.out.println("[ 유선 L ]");	
	}

	@Override
	public void mouseCheck() {
		System.out.println(" 유선마이스 연결 확인 ");
		clickR();
		clickL();
	}
}
