package com.webapp.exception;

public class AlreadyExistingMemberException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public AlreadyExistingMemberException() {
		super();
	}
	public AlreadyExistingMemberException(String message) {
		super(message);
	}
	public AlreadyExistingMemberException(Throwable cause) {
		super(cause);
	}
	public AlreadyExistingMemberException(String message, Throwable cause) {
		super(message, cause);
	}
}
