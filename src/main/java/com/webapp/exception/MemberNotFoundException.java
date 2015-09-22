package com.webapp.exception;

public class MemberNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public MemberNotFoundException() {
		super();
	}
	public MemberNotFoundException(String message) {
		super(message);
	}
	public MemberNotFoundException(Throwable cause) {
		super(cause);
	}
	public MemberNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
