package com.icebreak.p2p.dataobject;

import java.io.Serializable;
import java.util.Date;


public class FundsRecord implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*表id*/
	private String tbId;
	/*用户baseId*/
	
	private long userId;
	/*真实姓名*/
	private String realName;
	/*身份证号*/
	private String certNo;
	/*平台用户名*/
	private String userName;
	/*平台平台资金流水号*/
	private String outBizNo;
	/*托管机构平台资金流水号*/
	private String payNo;
	/*资金来源*/
	private String fundsFrom;
	/*资金流向*/
	private String fundsTo;
	/*交易金额*/
	private double amounts;
	/*手续费*/
	private double charges;
	/*资金账户名*/
	private String acountName;
	/*资金账户ID*/
	private String acountId;
	/*银行名称*/
	private String bankName;
	/*银行账号*/
	private String bankAcountNo;
	/*银行开户名*/
	private String bankOpenName;
	/*支付时间*/
	private Date payTime;
	/*支付类型*/
	private String payType;
	/*支付状态 0-失败,1-成功,2-处理中*/
	private int status;
	
	public String getTbId() {
		return tbId;
	}
	public void setTbId(String tbId) {
		this.tbId = tbId;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOutBizNo() {
		return outBizNo;
	}
	public void setOutBizNo(String outBizNo) {
		this.outBizNo = outBizNo;
	}
	public String getPayNo() {
		return payNo;
	}
	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}
	public String getFundsFrom() {
		return fundsFrom;
	}
	public void setFundsFrom(String fundsFrom) {
		this.fundsFrom = fundsFrom;
	}
	public String getFundsTo() {
		return fundsTo;
	}
	public void setFundsTo(String fundsTo) {
		this.fundsTo = fundsTo;
	}
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public double getAmounts() {
		return amounts;
	}
	public void setAmounts(double amounts) {
		this.amounts = amounts;
	}
	public double getCharges() {
		return charges;
	}
	public void setCharges(double charges) {
		this.charges = charges;
	}
	public String getAcountName() {
		return acountName;
	}
	public void setAcountName(String acountName) {
		this.acountName = acountName;
	}
	public String getAcountId() {
		return acountId;
	}
	public void setAcountId(String acountId) {
		this.acountId = acountId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankAcountNo() {
		return bankAcountNo;
	}
	public void setBankAcountNo(String bankAcountNo) {
		this.bankAcountNo = bankAcountNo;
	}
	public String getBankOpenName() {
		return bankOpenName;
	}
	public void setBankOpenName(String bankOpenName) {
		this.bankOpenName = bankOpenName;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	

}
