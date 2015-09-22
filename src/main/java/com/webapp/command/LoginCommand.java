package com.webapp.command;


public class LoginCommand {
	String email;
	String password;
	boolean remember;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isRemember() {
		return remember;
	}
	public void setRemember(boolean remember) {
		this.remember = remember;
	}
	
	@Override
	public String toString() {

		return "email = "+ email + "password = "+ password + "remember = " + remember;
	}
	
}
