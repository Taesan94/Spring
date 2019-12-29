package spring.di.ui;

import spring.di.entity.Mouse;

public class Computer {
	private Mouse mouse ;
	
	public void mouseCheck() {
		mouse.mouseCheck();
	};
	public void setMouse(Mouse mouse) {
		this.mouse=mouse;
	}
}
