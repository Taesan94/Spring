package com.boot.test1.vo;

public class Authority {

	private String userName;
	private String authorityName;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAuthorityName() {
		return authorityName;
	}
	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}
	
	@Override
	public String toString() {
		return "Authority [userName=" + userName + ", authorityName=" + authorityName + "]";
	}

}
