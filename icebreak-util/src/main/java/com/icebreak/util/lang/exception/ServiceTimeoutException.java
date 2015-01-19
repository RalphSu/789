package com.icebreak.util.lang.exception;

public class ServiceTimeoutException extends Exception {
	
	/** serialVersionUID */
	private static final long serialVersionUID = -8455422633637648945L;
	
	public ServiceTimeoutException() {
	}
	
	public ServiceTimeoutException(String s) {
		super(s);
	}
	
	public ServiceTimeoutException(String s, Throwable throwable) {
		super(s, throwable);
	}
	
	public ServiceTimeoutException(Throwable throwable) {
		super(throwable);
	}
	
}
