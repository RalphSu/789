package com.icebreak.p2p.integration.openapi.info;

public class DepositInfo extends TransactionInfo {

	private String depositType;
	private String depositCode;

	public String getDepositType() {
		return depositType;
	}
	
	public void setDepositType(String depositType) {
		this.depositType = depositType;
	}
	
	public String getDepositCode() {
		return depositCode;
	}
	
	public void setDepositCode(String depositCode) {
		this.depositCode = depositCode;
	}
	
}
