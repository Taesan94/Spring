package spring.di;

import spring.di.entity.CableMouse;
import spring.di.entity.Mouse;
import spring.di.ui.Computer;

public class Student {

	public static void main(String[] args) {
		
		Computer computer = new Computer();
		
		Mouse mouse = new CableMouse(); // 유선마우스
		//Mouse mouse = new WireLessMouse(); // 무선마우스
		
		// 작업을 위한 준비. 마우스 연결
		computer.connectMouse(mouse);
		// 연결확인
		computer.mouseCheck();
		
	}
}
