package com.icebreak.p2p.dataobject;

public class BankInfo {
	private long bankId;
	/*银行名称*/
	private String bankName;
	/*银行英文简称*/
	private String bankCode;
	/*银行logo地址*/
	private String logoUrl;
	/*是否支持代扣中 IS:是,NO:否*/
	private String deduct;
	/*单笔代扣限额 ,单位:万元*/
	private int singleDeductLimit;
	/*单日代扣限额 ,单位:万元*/
	private int oddDeductLimit;
	/*是否支持同步渠道,同步-Y,异步-N*/
	private String synChannel;
	/*是否支持提现 IS:是,NO:否*/
	private String withdrawal;
	/*单笔提现金额 单位:万元*/
	private int singleWithdrawalLimit;
	/*单日提现金额 单位:万元*/
	private int oddWithdrawalLimit;
	/*是否支持对公账户 Y-是,N-否*/
	private String publicAccounts;
	
	public long getBankId() {
		return bankId;
	}
	public void setBankId(long bankId) {
		this.bankId = bankId;
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
	public String getLogoUrl() {
		return logoUrl;
	}
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	public String getDeduct() {
		return deduct;
	}
	public void setDeduct(String deduct) {
		this.deduct = deduct;
	}
	public String getWithdrawal() {
		return withdrawal;
	}
	public void setWithdrawal(String withdrawal) {
		this.withdrawal = withdrawal;
	}
	
	public int getSingleDeductLimit() {
		return singleDeductLimit;
	}
	public void setSingleDeductLimit(int singleDeductLimit) {
		this.singleDeductLimit = singleDeductLimit;
	}
	public int getOddDeductLimit() {
		return oddDeductLimit;
	}
	public void setOddDeductLimit(int oddDeductLimit) {
		this.oddDeductLimit = oddDeductLimit;
	}
	public int getSingleWithdrawalLimit() {
		return singleWithdrawalLimit;
	}
	public void setSingleWithdrawalLimit(int singleWithdrawalLimit) {
		this.singleWithdrawalLimit = singleWithdrawalLimit;
	}
	public int getOddWithdrawalLimit() {
		return oddWithdrawalLimit;
	}
	public void setOddWithdrawalLimit(int oddWithdrawalLimit) {
		this.oddWithdrawalLimit = oddWithdrawalLimit;
	}
	public String getSynChannel() {
		return synChannel;
	}
	public void setSynChannel(String synChannel) {
		this.synChannel = synChannel;
	}
	public String getPublicAccounts() {
		return publicAccounts;
	}
	public void setPublicAccounts(String publicAccounts) {
		this.publicAccounts = publicAccounts;
	}
	

}
