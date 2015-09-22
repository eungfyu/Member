package com.webapp.exception;

public class MemberModifyEmptyException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public MemberModifyEmptyException() {
		super();
	}
	public MemberModifyEmptyException(String message) {
		super(message);
	}
	public MemberModifyEmptyException(Throwable cause) {
		super(cause);
	}
	public MemberModifyEmptyException(String message, Throwable cause) {
		super(message, cause);
	}
}
