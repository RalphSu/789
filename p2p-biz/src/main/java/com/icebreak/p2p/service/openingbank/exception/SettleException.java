package com.icebreak.p2p.service.openingbank.exception;

import com.icebreak.p2p.ws.enums.SettleResultEnum;

public class SettleException extends RuntimeException {
	
	/** Comment for <code>serialVersionUID</code> */
	private static final long	serialVersionUID	= 6390850120399606380L;
	
	SettleResultEnum			settleResultEnum	= null;
	
	public SettleException(SettleResultEnum resultEnum) {
		super(resultEnum.code());
		this.settleResultEnum = resultEnum;
	}
	
	public SettleResultEnum getCode() {
		return settleResultEnum;
	}
}
