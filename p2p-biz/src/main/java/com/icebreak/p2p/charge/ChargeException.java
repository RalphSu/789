package com.icebreak.p2p.charge;

public class ChargeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ChargeException(Exception e, String message){
		super(message, e);
	}
	
	public ChargeException(Exception e){
		super(e);
	}
	
	public ChargeException(String message){
		super(message);
	}

}
