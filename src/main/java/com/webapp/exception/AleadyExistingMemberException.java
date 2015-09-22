package com.webapp.exception;


public class AleadyExistingMemberException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;//객체간의 버전관리를 위해서

	public AleadyExistingMemberException() {
		super();
	}
	
	public AleadyExistingMemberException(String message) {
		super(message);
	}
	
	public AleadyExistingMemberException(Throwable cause) {
		super(cause);
	}
	
	public AleadyExistingMemberException(String message, Throwable cause) {
		super(message, cause);
	}
}
