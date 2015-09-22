package com.webapp.exception;


public class idPasswordNotMatchException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;//객체간의 버전관리를 위해서

	public idPasswordNotMatchException() {
		super();
	}
	
	public idPasswordNotMatchException(String message) {
		super(message);
	}
	
	public idPasswordNotMatchException(Throwable cause) {
		super(cause);
	}
	
	public idPasswordNotMatchException(String message, Throwable cause) {
		super(message, cause);
	}
}
