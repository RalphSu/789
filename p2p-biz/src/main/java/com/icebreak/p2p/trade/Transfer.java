package com.icebreak.p2p.trade;

import java.io.Serializable;

public class Transfer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 出账账户
	 */
	private String accountOut;
	/**
	 * 入账账户
	 */
	private String accountIn;
	/**
	 * 交易金额
	 */
	private String amount;
	/**
	 * 交易名称
	 */
	private String tradeName;
	/**
	 * 产品编号
	 */
	private String tradeBizProductCode;
	/**
	 * 备注u
	 */
	private String tradeMemo;

	public Transfer() {
	}

	public Transfer(String accountOut, String amount, 
			String accountIn, String tradeName, String tradeBizProductCode,
			String tradeMemo) {
		this.accountOut = accountOut;
		this.accountIn = accountIn;
		this.amount = amount;
		this.tradeName = tradeName;
		this.tradeBizProductCode = tradeBizProductCode;
		this.tradeMemo = tradeMemo;
	}
	
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getAccountOut() {
		return accountOut;
	}

	public void setAccountOut(String accountOut) {
		this.accountOut = accountOut;
	}

	public String getAccountIn() {
		return accountIn;
	}

	public void setAccountIn(String accountIn) {
		this.accountIn = accountIn;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public String getTradeBizProductCode() {
		return tradeBizProductCode;
	}

	public void setTradeBizProductCode(String tradeBizProductCode) {
		this.tradeBizProductCode = tradeBizProductCode;
	}

	public String getTradeMemo() {
		return tradeMemo;
	}

	public void setTradeMemo(String tradeMemo) {
		this.tradeMemo = tradeMemo;
	}
}
