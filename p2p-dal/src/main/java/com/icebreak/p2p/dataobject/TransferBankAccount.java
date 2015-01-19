package com.icebreak.p2p.dataobject;

public class TransferBankAccount {
	/**
	 * 付款方资金账户ID(不可空)
	 */
	private String 	buyerUserId;
	/**
	 * 收款方的真实姓名(不可空)
	 */
	private String 	realName;
	/**
	 * 收款方的身份证号(不可空)
	 */
	private String 	certNo;
	/**
	 * 收款方的手机号码(不可空)
	 */
	private String 	mobile;
	/**
	 * 转账金额(不可空)
	 */
	private String 	tradeAmount;
	/**
	 * 收款方的银行卡号(不可空)
	 */
	private String 	bankAccountNo;
	/**
	 * 银行名字(不可空)
	 */
	private String 	bankName;
	/**
	 * 银行简码(不可空)
	 */
	private String 	bankCode;
	/**
	 * 银行省名(不可空)
	 */
	private String 	provName;
	/**
	 * 银行市名(不可空)
	 */
	private String 	cityName;
	/**
	 *业务产品编号(不可空)
	 */
	private String 	tradeBizProductCode;
	/**
	 * 可选参数为：true,false（ture表示包含手续费，
	 * false表示包不含手续费）(不可空)
	 */
	private String 	isContainInTradeAmount;
	/**
	 * 交易备注(可空)
	 */
	private String 	tradeMemo;
	private String banklogo;
	private String transferType;
	public String getBuyerUserId() {
		return buyerUserId;
	}
	public void setBuyerUserId(String buyerUserId) {
		this.buyerUserId = buyerUserId;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getCertNo() {
		return certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getTradeAmount() {
		return tradeAmount;
	}
	public void setTradeAmount(String tradeAmount) {
		this.tradeAmount = tradeAmount;
	}
	public String getBankAccountNo() {
		return bankAccountNo;
	}
	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getProvName() {
		return provName;
	}
	public void setProvName(String provName) {
		this.provName = provName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getTradeBizProductCode() {
		return tradeBizProductCode;
	}
	public void setTradeBizProductCode(String tradeBizProductCode) {
		this.tradeBizProductCode = tradeBizProductCode;
	}
	public String getIsContainInTradeAmount() {
		return isContainInTradeAmount;
	}
	public void setIsContainInTradeAmount(String isContainInTradeAmount) {
		this.isContainInTradeAmount = isContainInTradeAmount;
	}
	public String getTradeMemo() {
		return tradeMemo;
	}
	public void setTradeMemo(String tradeMemo) {
		this.tradeMemo = tradeMemo;
	}
	public String getBanklogo() {
		return banklogo;
	}
	public void setBanklogo(String banklogo) {
		this.banklogo = banklogo;
	}
	public String getTransferType() {
		return transferType;
	}
	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}
	

}
