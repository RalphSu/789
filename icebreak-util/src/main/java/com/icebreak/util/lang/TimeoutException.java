package com.icebreak.util.lang;

public class TimeoutException extends RunException {

	/** 版本号 */
	private static final long serialVersionUID = 5419788348258892009L;

	public TimeoutException() {

	}

	public TimeoutException(String msg) {
		super(msg);
	}

	public TimeoutException(StringBuilder msg) {
		super(msg);
	}

	public TimeoutException(StringBuffer msg) {
		super(msg);
	}
}
