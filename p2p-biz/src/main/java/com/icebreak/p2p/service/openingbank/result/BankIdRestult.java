package com.icebreak.p2p.service.openingbank.result;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.icebreak.p2p.ws.enums.SettleResultEnum;
import com.icebreak.p2p.ext.domain.ResultBase;

public class BankIdRestult extends ResultBase {
	
	/** Comment for <code>serialVersionUID</code> */
	private static final long	serialVersionUID	= -2549498738906898533L;
	
	private SettleResultEnum	resultCode			= SettleResultEnum.UN_KNOWN_EXCEPTION;
	
	private String				bankId				= null;
	
	@Override
	public boolean isExecuted() {
		
		return resultCode == SettleResultEnum.EXECUTE_SUCCESS ? true : false;
	}
	
	public SettleResultEnum getResultCode() {
		return resultCode;
	}
	
	public void setResultCode(SettleResultEnum resultCode) {
		this.resultCode = resultCode;
	}
	
	public String getBankId() {
		return bankId;
	}
	
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	
	/**
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
