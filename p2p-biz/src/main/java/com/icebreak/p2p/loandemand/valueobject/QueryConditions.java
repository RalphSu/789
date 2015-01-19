package com.icebreak.p2p.loandemand.valueobject;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.icebreak.util.service.Order;

public class QueryConditions implements Order {
	/** Comment for <code>serialVersionUID</code> */
	private static final long	serialVersionUID	= 85077599464527497L;
	/** 借款人ID */
	private long				loanerId;
	/** 申请号 */
	private String				tradeCode;
	/** 借款人用户名 */
	private String				userName;
	/** 最小借款金额 */
	private String				maiLoanAmount;
	/** 最大借款金额 */
	private String				maxLoanAmount;
	/** 担保机构名称 */
	private String				guaranteeName;
	/** 保荐机构名称 */
	private String				sponsorName;
	/** 最小时间 */
	private String				maiDemandDate;
	/** 最小时间 */
	private String				maxDemandDate;
	/**发布时间*/
	private String				publishDate;
	/** 状态 **/
	private List<String>		status;
	
	public long getLoanerId() {
		return loanerId;
	}
	
	public void setLoanerId(long loanerId) {
		this.loanerId = loanerId;
	}
	
	public String getTradeCode() {
		return tradeCode;
	}
	
	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getMaiLoanAmount() {
		return maiLoanAmount;
	}
	
	public void setMaiLoanAmount(String maiLoanAmount) {
		this.maiLoanAmount = maiLoanAmount;
	}
	
	public String getMaxLoanAmount() {
		return maxLoanAmount;
	}
	
	public void setMaxLoanAmount(String maxLoanAmount) {
		this.maxLoanAmount = maxLoanAmount;
	}
	
	public String getGuaranteeName() {
		return guaranteeName;
	}
	
	public void setGuaranteeName(String guaranteeName) {
		this.guaranteeName = guaranteeName;
	}
	
	public String getSponsorName() {
		return sponsorName;
	}
	
	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}
	
	public String getMaiDemandDate() {
		return maiDemandDate;
	}
	
	public void setMaiDemandDate(String maiDemandDate) {
		this.maiDemandDate = maiDemandDate;
	}
	
	public String getMaxDemandDate() {
		return maxDemandDate;
	}
	
	public void setMaxDemandDate(String maxDemandDate) {
		this.maxDemandDate = maxDemandDate;
	}
	
	public List<String> getStatus() {
		return status;
	}
	
	public void setStatus(List<String> status) {
		this.status = status;
	}
	
	public String getPublishDate() {
		return publishDate;
	}
	
	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	
	/**
	 * @return
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
	@Override
	public void check() {

		
	}
	
}
