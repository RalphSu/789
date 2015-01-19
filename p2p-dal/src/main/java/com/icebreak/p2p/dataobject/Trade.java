package com.icebreak.p2p.dataobject;

import java.io.Serializable;
import java.util.Date;

import com.icebreak.p2p.dataobject.DOEnum.LoanTypeEnum;
import com.icebreak.util.lang.util.StringUtil;

public class Trade implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Trade(){}
	
	public Trade(String code, long demandId, String name, long amount,
			double interestRate, long leastInvestAmount, String saturationConditionMethod,
			String saturationCondition, short status, String timeLimitUnit,
			int timeLimit, Date deadline, long loanedAmount, Date createDate, String note) {
		super();
		this.code = code;
		this.demandId = demandId;
		this.name = name;
		this.amount = amount;
		this.interestRate = interestRate;
		this.saturationConditionMethod = saturationConditionMethod;
		this.saturationCondition = saturationCondition;
		this.status = status;
		this.timeLimitUnit = timeLimitUnit;
		this.timeLimit = timeLimit;
		this.deadline = deadline;
		this.loanedAmount = loanedAmount;
		this.createDate = createDate;
		this.note = note;
		this.leastInvestAmount = leastInvestAmount;
	}

	/**
	 * 交易ID
	 */
	private long id = 0;
	/**
	 * 交易号
	 */
	private String code = null;
	/**
	 * 需求ID
	 */
	private long demandId = 0;
	/**
	 * 交易名称
	 */
	private String name = null;
	/**
	 * 金额
	 */
	private long amount = 0;
	/**
	 * 利率
	 */
	private double interestRate = 0.0F;
	/**
	 * 最低投资金额
	 */
	private long leastInvestAmount = 0;
	/**
	 * 交易标满条件计算方式，默认是金额
	 */
	private String saturationConditionMethod = "amount";
	/**
	 * 标满条件
	 */
	private String saturationCondition = null;
	/**
	 * 交易状态
	 */
	private short status = 0;
	/**
	 * 借款期限计算单位，默认为以月为计算单位
	 */
	private String timeLimitUnit = "M";
	/**
	 * 借款期限
	 */
	private int timeLimit = 0;
	/**
	 * 已借款金额
	 */
	private long loanedAmount = 0;
	/**
	 * 有效期
	 */
	private Date deadline = null;
	/**
	 * 交易创建时间
	 */
	private Date createDate = new Date();
	/**
	 * 成立时间
	 */
	private Date effectiveDateTime = null;
	/**
	 * 还款时间
	 */
	private Date expireDateTime = null;
	/**
	 * 交易完成时间
	 */
	private Date finishDate = null;
	/**
	 * 描述
	 */
	private String note = null;
	/**
	 * 到期前是否已通知借款人
	 */
	private String isNotifyLoaner = null;
	/**
	 * 是否参与活动（Y = 是 | N = 否）
	 */
	private String isJoinActivity;
	/*借款类型*/
	private LoanTypeEnum loanType;

	public LoanTypeEnum getLoanType() {
		return loanType;
	}

	public void setLoanType(LoanTypeEnum loanType) {
		this.loanType = loanType;
	}

	public long getLeastInvestAmount() {
		return leastInvestAmount;
	}

	public void setLeastInvestAmount(long leastInvestAmount) {
		this.leastInvestAmount = leastInvestAmount;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public long getDemandId() {
		return demandId;
	}

	public void setDemandId(long demandId) {
		this.demandId = demandId;
	}

	public String getName() {
		if(StringUtil.isNotBlank(name)){
			name = name.trim();
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
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

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
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

	public long getLoanedAmount() {
		return loanedAmount;
	}

	public void setLoanedAmount(long loanedAmount) {
		this.loanedAmount = loanedAmount;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public Date getEffectiveDateTime() {
		return effectiveDateTime;
	}

	public void setEffectiveDateTime(Date effectiveDateTime) {
		this.effectiveDateTime = effectiveDateTime;
	}

	public Date getExpireDateTime() {
		return expireDateTime;
	}

	public void setExpireDateTime(Date expireDateTime) {
		this.expireDateTime = expireDateTime;
	}

	public String getIsNotifyLoaner() {
		return isNotifyLoaner;
	}

	public void setIsNotifyLoaner(String isNotifyLoaner) {
		this.isNotifyLoaner = isNotifyLoaner;
	}

	public String getIsJoinActivity() {
		return isJoinActivity;
	}

	public void setIsJoinActivity(String isJoinActivity) {
		this.isJoinActivity = isJoinActivity;
	}

	public int getMonthCount() {
		int monthCount = 1;
		if ("D".equals(this.getTimeLimitUnit())) {
			return monthCount;
		} else if ("Y".equals(this.getTimeLimitUnit())) {
			monthCount = timeLimit * 12;
		} else {
			monthCount = timeLimit;
		}
		return monthCount;
	}
	
	@Override
	public String toString() {
		return "Trade [id=" + id + ", code=" + code + ", demandId=" + demandId
				+ ", name=" + name + ", amount=" + amount + ", interestRate="
				+ interestRate + ", leastInvestAmount=" + leastInvestAmount
				+ ", saturationConditionMethod=" + saturationConditionMethod
				+ ", saturationCondition=" + saturationCondition + ", status="
				+ status + ", timeLimitUnit=" + timeLimitUnit + ", timeLimit="
				+ timeLimit + ", loanedAmount=" + loanedAmount + ", deadline="
				+ deadline + ", createDate=" + createDate
				+ ", effectiveDateTime=" + effectiveDateTime
				+ ", expireDateTime=" + expireDateTime + ", finishDate="
				+ finishDate + ", note=" + note + ", isNotifyLoaner="
				+ isNotifyLoaner + "]";
	}
	
}
