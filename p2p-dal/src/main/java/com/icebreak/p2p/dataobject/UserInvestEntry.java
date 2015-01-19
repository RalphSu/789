package com.icebreak.p2p.dataobject;

import java.io.Serializable;
import java.util.Date;


public class UserInvestEntry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 投资ID
	 */
	private long id = 0;
	/**
	 * 交易ID
	 */
	private long tradeId = 0;
	/**
	 * 交易号
	 */
	private String tradeCode = null;
	/**
	 * 投资流水号
	 */
	private String investflowCode = null;
	/**
	 * 交易名称
	 */
	private String tradeName = null;
	/**
	 * 投资日期
	 */
	private Date date = null;
	/**
	 * 交易状态
	 */
	private short tradeStatus = 0;
	/**
	 * 投资金额
	 */
	private long amount = 0;
	/**
	 * 投资人ID
	 */
	private long investorId = 0;
	/**
	 * 投资人用户名
	 */
	private String investorUserName = null;
	/**
	 * 投资人真实姓名
	 */
	private String investorRealName = null;
	/**
	 * 借款人ID
	 */
	private long loanerId = 0;
	/**
	 * 借款人用户名
	 */
	private String loanerUserName = null;
	/**
	 * 借款人真实姓名
	 */
	private String loanerRealName = null;
	/**
	 * 转账阶段
	 */
	private String transferPhase;
	//到期时间
	private Date expireDateTime;
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTradeId() {
		return tradeId;
	}

	public void setTradeId(long tradeId) {
		this.tradeId = tradeId;
	}

	public String getTradeCode() {
		return tradeCode;
	}

	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public short getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(short tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public long getInvestorId() {
		return investorId;
	}

	public void setInvestorId(long investorId) {
		this.investorId = investorId;
	}

	public String getInvestorUserName() {
		return investorUserName;
	}

	public void setInvestorUserName(String investorUserName) {
		this.investorUserName = investorUserName;
	}

	public String getInvestorRealName() {
		return investorRealName;
	}

	public void setInvestorRealName(String investorRealName) {
		this.investorRealName = investorRealName;
	}

	public long getLoanerId() {
		return loanerId;
	}

	public void setLoanerId(long loanerId) {
		this.loanerId = loanerId;
	}

	public String getLoanerUserName() {
		return loanerUserName;
	}

	public void setLoanerUserName(String loanerUserName) {
		this.loanerUserName = loanerUserName;
	}

	public String getLoanerRealName() {
		return loanerRealName;
	}

	public void setLoanerRealName(String loanerRealName) {
		this.loanerRealName = loanerRealName;
	}
	
	public String getTransferPhase() {
		return transferPhase;
	}

	public void setTransferPhase(String transferPhase) {
		this.transferPhase = transferPhase;
	}
	
	public String getInvestflowCode() {
		return investflowCode;
	}

	public void setInvestflowCode(String investflowCode) {
		this.investflowCode = investflowCode;
	}
	
	public Date getExpireDateTime() {
		return expireDateTime;
	}

	public void setExpireDateTime(Date expireDateTime) {
		this.expireDateTime = expireDateTime;
	}

	public String getStatus(){
		if(tradeStatus == 1){
			return "待成立";
		}else if(tradeStatus == 2){
			return  "未到期";
		}else if(tradeStatus == 3){
			return "正常收款";
		}else if(tradeStatus == 4){
			return "未成立";
		}else if(tradeStatus == 5){
			return "违约处理中";
		}else if(tradeStatus == 6){
			return "担保公司审核中";
		}else if(tradeStatus == 8){
			return "等待还款";
		}else if(tradeStatus == 7){
			return "超期收款";
		}
		return "";
	}
	
	public String getStatusColor(){
		if(tradeStatus == 1){
			return "#666";
		}else if(tradeStatus == 2){
			return  "#666";
		}else if(tradeStatus == 3){
			return "green";
		}else if(tradeStatus == 4){
			return "gray";
		}else if(tradeStatus == 5){
			return "red";
		}else if(tradeStatus == 6){
			return "#666";
		}else if(tradeStatus == 8){
			return "#666";
		}else if(tradeStatus == 7){
			return "green";
		}
		return "";
	}
	@Override
	public String toString() {
		return "UserInvestEntry [getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	
}
