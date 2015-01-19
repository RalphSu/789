package com.icebreak.p2p.dataobject;

import com.icebreak.p2p.util.AppConstantsUtil;
import com.icebreak.util.lang.util.StringUtil;

import java.io.Serializable;
import java.util.Date;

public class IndexTrade implements Serializable {
//	项目默认缩略图已配置到系统参数中，通过AppConstantsUtil.getProjectDefaultThumbnailUrl()可以获取
//	public static final String DEFAULT_PRO_IMAGE = "/resources/images/new/pro_pic.png";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    /**
     * 交易ID
     */
	private long tradeId = 0;
	/**
	 * 借款需求ID
	 */
	private long demandId = 0;
	/**
	 * 名称
	 */
	private String name = null;
	/**
	 * 金额
	 */
	private long amount = 0;
	/**
	 * 利率
	 */
	private double rate = 0.0F;
	
	private String strRaate;
	/**
	 * 借款期限
	 */
	private int time = 0;
	/**
	 * 借款期限单位
	 */
	private String timeUnit = null;
	/**
	 * 借款发布日期
	 */
	private Date createDate = null;
	/**
	 * 投资开始日期
	 */
	private Date investAvalibleTime = null;
	/**
	 * 借款截止日期
	 */
	private Date deadline = null;
	/**
	 * 借款成立日期
	 */
	private Date effectiveDate = null;
	/**
	 * 还款日期
	 */
	private Date finishDate = null;
	/**
	 * 担保机构ID
	 */
	private long guaranteeId = 0;
	/**
	 * 担保机构名称
	 */
	private String guaranteeName = null;
	/**
	 * 担保机构ID
	 */
	private Integer sponsorId = 0;
	/**
	 * 保荐机构名称
	 */
	private String sponsorName = null;
	/**
	 * 已借款金额
	 */
	private long loanedAmount = 0;
	/**
	 * 最低投资金额
	 */
	private long leastInvestAmount = 0;
	
	/**
	 * 交易状态
	 */
	private short status = 0;

    /**
     * 进度条
     */
    private String percent;
    /**
     * 进度条数值
     */
    private String percentnum;
    /**
     * 还款方式
     */
    private String repayDivisionWayMsg;
    
    /**
     * 保障方式
     */
    private String insureWay; 
    
	private String insureWayMsg;
	
	/**
	 * 标的所属地区（地区代码）
	 */
	private String areaNbNo; 
	
	private String areaName; 


	/**
	 * 投资完成日期
	 */
	private Date investCompleteTime = null;

	/**
	 * 项目缩略图
	 */
	private String proImage = null;

	/**
	 * 描述
	 */
	private String tradeNote = null;


	/***
	 * 扩展属性 无条件兑付银行
	 */
	private String paymentBankName;



	public String getTradeNote() {
		return tradeNote;
	}

	public void setTradeNote(String tradeNote) {
		this.tradeNote = tradeNote;
	}

	public long getDemandId() {
		return demandId;
	}

	public void setDemandId(long demandId) {
		this.demandId = demandId;
	}

	public long getTradeId() {
		return tradeId;
	}

	public void setTradeId(long tradeId) {
		this.tradeId = tradeId;
	}



	public String getName() {
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

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	public Integer getSponsorId() {
		return sponsorId;
	}

	public void setSponsorId(Integer sponsorId) {
		this.sponsorId = sponsorId;
	}

	public String getSponsorName() {
		return sponsorName;
	}

	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}

	public long getLoanedAmount() {
		return loanedAmount;
	}

	public void setLoanedAmount(long loanedAmount) {
		this.loanedAmount = loanedAmount;
	}
	
	public String getStrRaate() {
		return strRaate;
	}

	public void setStrRaate(String strRaate) {
		this.strRaate = strRaate;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public long getLeastInvestAmount() {
		return leastInvestAmount;
	}

	public void setLeastInvestAmount(long leastInvestAmount) {
		this.leastInvestAmount = leastInvestAmount;
	}
	
	public Date getInvestAvalibleTime() {
		return investAvalibleTime;
	}

	public void setInvestAvalibleTime(Date investAvalibleTime) {
		this.investAvalibleTime = investAvalibleTime;
	}
	
	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}
	
	public Date getInvestCompleteTime() {
		return investCompleteTime;
	}

	public void setInvestCompleteTime(Date investCompleteTime) {
		this.investCompleteTime = investCompleteTime;
	}
	
	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }
    
    public String getPercentnum() {
		return percentnum;
	}

	public void setPercentnum(String percentnum) {
		this.percentnum = percentnum;
	}

	public String getRepayDivisionWayMsg() {
		return repayDivisionWayMsg;
	}

	public void setRepayDivisionWayMsg(String repayDivisionWayMsg) {
		this.repayDivisionWayMsg = repayDivisionWayMsg;
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

	public String getProImage() {
		if(proImage == null) return AppConstantsUtil.getProjectDefaultThumbnailUrl();
		return proImage;
	}

	public void setProImage(String proImage) {
		if(StringUtil.isEmpty(proImage))
			return;

		this.proImage = proImage;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getPaymentBankName() {
		return paymentBankName;
	}

	public void setPaymentBankName(String paymentBankName) {
		this.paymentBankName = paymentBankName;
	}

	/***
	 * 获取投资状态
	 * @return
	 */
	public long getDiffInvestAvalibleTime(){
		long avalibleTime = 0l;
		if(this.getInvestAvalibleTime() != null){
			avalibleTime = this.getInvestAvalibleTime().getTime();
		}
		return new Date().getTime() - avalibleTime;
	}

	public long getInvestDeadTime(){
		long avalibleTime = 0l;
		if(this.getDeadline() != null){
			avalibleTime = this.getDeadline().getTime();
		}
		return new Date().getTime() - avalibleTime;
	}

	@Override
	public String toString() {
		return "IndexTrade [tradeId=" + tradeId + ", demandId=" + demandId
				+ ", name=" + name + ", amount=" + amount + ", rate=" + rate
				+ ", strRaate=" + strRaate + ", time=" + time + ", timeUnit="
				+ timeUnit + ", createDate=" + createDate
				+ ", investAvalibleTime=" + investAvalibleTime + ", deadline="
				+ deadline + ", effectiveDate=" + effectiveDate
				+ ", finishDate=" + finishDate + ", guaranteeId=" + guaranteeId
				+ ", guaranteeName=" + guaranteeName + ", sponsorId="
				+ sponsorId + ", sponsorName=" + sponsorName
				+ ", loanedAmount=" + loanedAmount + ", leastInvestAmount="
				+ leastInvestAmount + ", status=" + status + ", percent="
				+ percent +", percentnum=" + percentnum+", repayDivisionWayMsg=" + repayDivisionWayMsg  
				+ ", insureWay=" + insureWay
				+ ", investCompleteTime=" + investCompleteTime + ",proImage=" + proImage
				+ ",paymentBankName="+paymentBankName+"]";
	}

	
}
