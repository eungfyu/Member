package com.webapp.command;

public class Hobby {
	String code;
	String label;
	
	public Hobby() {
		
	}
	public Hobby(String code, String label) {
		this.code = code;
		this.label = label;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	
}
