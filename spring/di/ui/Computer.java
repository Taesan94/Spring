package spring.di.ui;

import spring.di.entity.Mouse;

public class Computer {
	private Mouse mouse ;
	
	public void connectMouse(Mouse mouse) {
		this.mouse=mouse;
	};
	public void mouseCheck() {
		mouse.mouseCheck();
	};
}
