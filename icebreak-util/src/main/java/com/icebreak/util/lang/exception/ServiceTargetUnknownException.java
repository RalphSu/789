package com.icebreak.util.lang.exception;

public class ServiceTargetUnknownException extends Exception {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 3379473765670332019L;
	
	public ServiceTargetUnknownException() {
	}
	
	public ServiceTargetUnknownException(String s) {
		super(s);
	}
	
	public ServiceTargetUnknownException(String s, Throwable throwable) {
		super(s, throwable);
	}
	
	public ServiceTargetUnknownException(Throwable throwable) {
		super(throwable);
	}
}
