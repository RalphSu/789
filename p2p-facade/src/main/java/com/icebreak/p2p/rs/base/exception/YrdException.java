package com.icebreak.p2p.rs.base.exception;

import com.icebreak.p2p.rs.base.enums.YrdServiceExceptionEnum;

public class YrdException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public YrdException() {
		super();
	}
	
	
	public YrdException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public YrdException(String message) {
		super(message);
	}
	
	public YrdException(Throwable cause) {
		super(cause);
	}
	
	public YrdException(YrdServiceExceptionEnum yrdServiceExceptionEnum){
		super(yrdServiceExceptionEnum.getCode());
	}

}
