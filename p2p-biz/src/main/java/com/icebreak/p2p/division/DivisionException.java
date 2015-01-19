package com.icebreak.p2p.division;

public class DivisionException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DivisionException(Exception e, String message){
		super(message, e);
	}
	
	public DivisionException(Exception e){
		super("分润出现异常", e);
	}
	
	public DivisionException(String message){
		super(message);
	}

}
