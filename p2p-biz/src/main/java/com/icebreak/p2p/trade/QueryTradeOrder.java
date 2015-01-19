package com.icebreak.p2p.trade;

import java.util.ArrayList;
import java.util.List;

import com.icebreak.util.service.Order;

public class QueryTradeOrder implements Order {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = 85077599464527497L;
	/** 用户ID */
	private long userId = 0;
	/** 角色ID **/
	private long roleId = 0;
	/** 申请号 */
	private String tradeCode;
	/** 交易名称 */
	private String tradeName;
	/** 借款人用户名 */
	private String userName;
	/** 最小借款金额 */
	private Long maiLoanAmount;
	/** 最大借款金额 */
	private Long maxLoanAmount;
	/** 担保机构名称 */
	private String guaranteeName;
	/** 担保函编号 */
	private String guaranteeLicenseNo;
	/** 担保函名称 */
	private String guaranteeLicenseName;
	/** 担保函pdf路径 */
	private String letterPdfUrl;
	/** 担保合同pdf路径 */
	private String contractPdfUrl;
	/** 保荐机构名称 */
	private String sponsorName;
	/** 最小时间 */
	private String startDate;
	/** 最大时间 */
	private String endDate;
	/** 最小还款时间 */
	private String startExpireDate;
	/** 最大还款时间 */
	private String endExpireDate;
	/** 单个状态 */
	private String singleState;
	/** 状态 **/
	private List<String> status = new ArrayList<String>();
	/** 用户ID */
	private long guaranteeId = 0;
	/** 可投资截止时间 **/
	private String executionDateTime;
	/** 借款生效时间 **/
	private String effectiveDateTime;
	/** 还款时间 **/
	private String expireDateTime;
	/*充值类型*/
	private String payType;
	private String accountId;
	private String guaranteeAudit;
	private String repayDivisionWay;
	
	public QueryTradeOrder() {
		
	}
	
	public QueryTradeOrder(long roleId, String tradeCode) {
		this.roleId = roleId;
		this.tradeCode = tradeCode;
	}
	
	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public long getRoleId() {
		return roleId;
	}
	
	public void setRoleId(long roleId) {
		this.roleId = roleId;
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
	
	public Long getMaiLoanAmount() {
		return maiLoanAmount;
	}
	
	public void setMaiLoanAmount(Long maiLoanAmount) {
		this.maiLoanAmount = maiLoanAmount;
	}
	
	public Long getMaxLoanAmount() {
		return maxLoanAmount;
	}
	
	public void setMaxLoanAmount(Long maxLoanAmount) {
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
	
	public String getStartDate() {
		return startDate;
	}
	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public String getEndDate() {
		return endDate;
	}
	
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public List<String> getStatus() {
		return status;
	}
	
	public void setStatus(List<String> status) {
		this.status = status;
	}
	
	public String getExecutionDateTime() {
		return executionDateTime;
	}
	
	public void setExecutionDateTime(String executionDateTime) {
		this.executionDateTime = executionDateTime;
	}
	
	public String getEffectiveDateTime() {
		return effectiveDateTime;
	}
	
	public void setEffectiveDateTime(String effectiveDateTime) {
		this.effectiveDateTime = effectiveDateTime;
	}
	
	public long getGuaranteeId() {
		return guaranteeId;
	}
	
	public void setGuaranteeId(long guaranteeId) {
		this.guaranteeId = guaranteeId;
	}
	
	public String getTradeName() {
		return tradeName;
	}
	
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	
	public String getStartExpireDate() {
		return startExpireDate;
	}
	
	public void setStartExpireDate(String startExpireDate) {
		this.startExpireDate = startExpireDate;
	}
	
	public String getEndExpireDate() {
		return endExpireDate;
	}
	
	public void setEndExpireDate(String endExpireDate) {
		this.endExpireDate = endExpireDate;
	}
	
	public String getGuaranteeLicenseNo() {
		return guaranteeLicenseNo;
	}
	
	public void setGuaranteeLicenseNo(String guaranteeLicenseNo) {
		this.guaranteeLicenseNo = guaranteeLicenseNo;
	}
	
	public String getGuaranteeLicenseName() {
		return guaranteeLicenseName;
	}
	
	public void setGuaranteeLicenseName(String guaranteeLicenseName) {
		this.guaranteeLicenseName = guaranteeLicenseName;
	}
	
	public String getSingleState() {
		return singleState;
	}
	
	public void setSingleState(String singleState) {
		this.singleState = singleState;
	}
	
	public String getExpireDateTime() {
		return expireDateTime;
	}
	
	public void setExpireDateTime(String expireDateTime) {
		this.expireDateTime = expireDateTime;
	}
	
	public String getPayType() {
		return payType;
	}
	
	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	public String getAccountId() {
		return accountId;
	}
	
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	public String getLetterPdfUrl() {
		return letterPdfUrl;
	}
	
	public void setLetterPdfUrl(String letterPdfUrl) {
		this.letterPdfUrl = letterPdfUrl;
	}
	
	public String getContractPdfUrl() {
		return contractPdfUrl;
	}
	
	public void setContractPdfUrl(String contractPdfUrl) {
		this.contractPdfUrl = contractPdfUrl;
	}
	
	public String getGuaranteeAudit() {
		return guaranteeAudit;
	}
	
	public void setGuaranteeAudit(String guaranteeAudit) {
		this.guaranteeAudit = guaranteeAudit;
	}
	
	public String getRepayDivisionWay() {
		return repayDivisionWay;
	}

	public void setRepayDivisionWay(String repayDivisionWay) {
		this.repayDivisionWay = repayDivisionWay;
	}

	@Override
	public String toString() {
		return "QueryTradeOrder [userId=" + userId + ", roleId=" + roleId
				+ ", tradeCode=" + tradeCode + ", tradeName=" + tradeName
				+ ", userName=" + userName + ", maiLoanAmount=" + maiLoanAmount
				+ ", maxLoanAmount=" + maxLoanAmount + ", guaranteeName="
				+ guaranteeName + ", guaranteeLicenseNo=" + guaranteeLicenseNo
				+ ", guaranteeLicenseName=" + guaranteeLicenseName
				+ ", letterPdfUrl=" + letterPdfUrl + ", contractPdfUrl="
				+ contractPdfUrl + ", sponsorName=" + sponsorName
				+ ", startDate=" + startDate + ", endDate=" + endDate
				+ ", startExpireDate=" + startExpireDate + ", endExpireDate="
				+ endExpireDate + ", singleState=" + singleState + ", status="
				+ status + ", guaranteeId=" + guaranteeId
				+ ", executionDateTime=" + executionDateTime
				+ ", effectiveDateTime=" + effectiveDateTime
				+ ", expireDateTime=" + expireDateTime + ", payType=" + payType
				+ ", accountId=" + accountId + ", guaranteeAudit="
				+ guaranteeAudit + ", repayDivisionWay=" + repayDivisionWay
				+ "]";
	}
	
	@Override
	public void check() {

	}
	
	
}
