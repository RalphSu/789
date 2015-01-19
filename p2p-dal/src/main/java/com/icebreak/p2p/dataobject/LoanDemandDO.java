package com.icebreak.p2p.dataobject;

import java.io.Serializable;
import java.util.Date;

import com.icebreak.p2p.dataobject.DOEnum.LoanTypeEnum;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.icebreak.util.lang.util.StringUtil;

public class LoanDemandDO implements Serializable {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4282603875229233564L;

	// ========== properties ==========

	private long demandId;

	private String tradeCode;

	private long loanerId;

	private String userName;

	private String loanName;

	private String dimensions;

	private long loanAmount;
	
	private String loanPassword; //定向融资密码

	private double interestRate;

	private String timeLimitUnit;

	private int timeLimit;

	private Date deadline = null;

	private long leastInvestAmount;

	private String saturationConditionMethod;

	private String saturationCondition;

	private String loanPurpose;

	private String selfDefinedTitle;

	private String loanNote;

	private String loanStatement;

	private long guaranteeId;

	private String guaranteeName;

	private String guaranteeStatement;

	private String guaranteeLicenseNo;
	
	private String guaranteeLicenseName;
	
	private String guaranteeLicenseUrl;

	private long sponsorId;

	private String sponsorName;

	private String sponsorStatement;

	private String status;

	private long divisionTemplateId;

	private Date demandDate;

	private Date publishDate;

	private String dismissReason;
	/*上传最终担保函状态标识*/
	private String guaranteeAudit;
	/*担保函pdf文件路径*/
	private String letterPdfUrl;
	/*担保履约合同pdf文件路径*/
	private String contractPdfUrl;
	
	private Date investAvalibleTime =null;
	/*业务类型*/
	private String bizType;
	
	private String repayDivisionWay;
	
	private String repayDivisionWayMsg;
	
	/*保障方式*/
	private String insureWay="0000000000";//默认为：其他
	
	private String insureWayMsg;
	/*所属地区*/
	private String  areaNbNo;

	/*是否参与活动（Y = 是 | N = 否）*/
	private String isJoinActivity = "N";

	/*借款类型*/
	private LoanTypeEnum loanType;

	// ========== getters and setters ==========

	
	public String getRepayDivisionWay() {
		return repayDivisionWay;
	}

	public String getRepayDivisionWayMsg() {
		return repayDivisionWayMsg;
	}

	public void setRepayDivisionWayMsg(String repayDivisionWayMsg) {
		this.repayDivisionWayMsg = repayDivisionWayMsg;
	}

	public String getLoanPassword() {
		return loanPassword;
	}

	public void setLoanPassword(String loanPassword) {
		this.loanPassword = loanPassword;
	}

	public void setRepayDivisionWay(String repayDivisionWay) {
		this.repayDivisionWay = repayDivisionWay;
	}

	public long getDemandId() {
		return demandId;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public void setDemandId(long demandId) {
		this.demandId = demandId;
	}

	public String getTradeCode() {
		return tradeCode;
	}

	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}

	public long getLoanerId() {
		return loanerId;
	}

	public void setLoanerId(long loanerId) {
		this.loanerId = loanerId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoanName() {
		if(StringUtil.isNotBlank(loanName)){
			loanName = loanName.trim();
		}
		return loanName;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

	public String getDimensions() {
		return dimensions;
	}

	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
	}

	public long getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(long loanAmount) {
		this.loanAmount = loanAmount;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public String getTimeLimitUnit() {
		return timeLimitUnit;
	}

	public void setTimeLimitUnit(String timeLimitUnit) {
		this.timeLimitUnit = timeLimitUnit;
	}

	public int getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}

	public long getLeastInvestAmount() {
		return leastInvestAmount;
	}

	public void setLeastInvestAmount(long leastInvestAmount) {
		this.leastInvestAmount = leastInvestAmount;
	}

	public String getSaturationConditionMethod() {
		return saturationConditionMethod;
	}

	public void setSaturationConditionMethod(String saturationConditionMethod) {
		this.saturationConditionMethod = saturationConditionMethod;
	}

	public String getSaturationCondition() {
		return saturationCondition;
	}

	public void setSaturationCondition(String saturationCondition) {
		this.saturationCondition = saturationCondition;
	}

	public String getLoanPurpose() {
		return loanPurpose;
	}

	public void setLoanPurpose(String loanPurpose) {
		this.loanPurpose = loanPurpose;
	}

	public String getSelfDefinedTitle() {
		return selfDefinedTitle;
	}

	public void setSelfDefinedTitle(String selfDefinedTitle) {
		this.selfDefinedTitle = selfDefinedTitle;
	}

	public String getLoanNote() {
		return loanNote;
	}

	public void setLoanNote(String loanNote) {
		this.loanNote = loanNote;
	}

	public String getLoanStatement() {
		return loanStatement;
	}

	public void setLoanStatement(String loanStatement) {
		this.loanStatement = loanStatement;
	}

	public long getGuaranteeId() {
		return guaranteeId;
	}

	public void setGuaranteeId(long guaranteeId) {
		this.guaranteeId = guaranteeId;
	}

	public String getGuaranteeName() {
		return guaranteeName;
	}

	public void setGuaranteeName(String guaranteeName) {
		this.guaranteeName = guaranteeName;
	}

	public String getGuaranteeStatement() {
		return guaranteeStatement;
	}

	public void setGuaranteeStatement(String guaranteeStatement) {
		this.guaranteeStatement = guaranteeStatement;
	}

	public String getGuaranteeLicenseUrl() {
		return guaranteeLicenseUrl;
	}

	public void setGuaranteeLicenseUrl(String guaranteeLicenseUrl) {
		this.guaranteeLicenseUrl = guaranteeLicenseUrl;
	}

	public long getSponsorId() {
		return sponsorId;
	}

	public void setSponsorId(long sponsorId) {
		this.sponsorId = sponsorId;
	}

	public String getSponsorName() {
		return sponsorName;
	}

	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}

	public String getSponsorStatement() {
		return sponsorStatement;
	}

	public void setSponsorStatement(String sponsorStatement) {
		this.sponsorStatement = sponsorStatement;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getDivisionTemplateId() {
		return divisionTemplateId;
	}

	public void setDivisionTemplateId(long divisionTemplateId) {
		this.divisionTemplateId = divisionTemplateId;
	}

	public Date getDemandDate() {
		return demandDate;
	}

	public void setDemandDate(Date demandDate) {
		this.demandDate = demandDate;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getDismissReason() {
		return dismissReason;
	}

	public void setDismissReason(String dismissReason) {
		this.dismissReason = dismissReason;
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
	

	public String getGuaranteeAudit() {
		return guaranteeAudit;
	}

	public void setGuaranteeAudit(String guaranteeAudit) {
		this.guaranteeAudit = guaranteeAudit;
	}

	public Date getInvestAvalibleTime() {
		return investAvalibleTime;
	}

	public void setInvestAvalibleTime(Date investAvalibleTime) {
		this.investAvalibleTime = investAvalibleTime;
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
	
	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getInsureWay() {
		return insureWay;
	}

	public void setInsureWay(String insureWay) {
		this.insureWay = insureWay;
	}

	public String getInsureWayMsg() {
		return insureWayMsg;
	}

	public void setInsureWayMsg(String insureWayMsg) {
		this.insureWayMsg = insureWayMsg;
	}
	

	public String getAreaNbNo() {
		return areaNbNo;
	}

	public void setAreaNbNo(String areaNbNo) {
		this.areaNbNo = areaNbNo;
	}

	public String getIsJoinActivity() {
		return isJoinActivity;
	}

	public void setIsJoinActivity(String isJoinActivity) {
		this.isJoinActivity = isJoinActivity;
	}

	public LoanTypeEnum getLoanType() {
		return loanType;
	}

	public void setLoanType(LoanTypeEnum loanType) {
		this.loanType = loanType;
	}

	/**
	 * @return
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
