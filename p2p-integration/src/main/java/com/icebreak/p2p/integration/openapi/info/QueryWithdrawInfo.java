package com.icebreak.p2p.integration.openapi.info;

public class QueryWithdrawInfo extends TransactionInfo {

	/** 提现后余额 */
	private String	balance;

	private String accountName;

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}
}
