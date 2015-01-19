package com.icebreak.p2p.ws.info;

import com.icebreak.util.lang.util.money.Money;

import java.io.Serializable;
import java.util.Date;

public class RepayPlanInfo implements Serializable {
	
    private static final long serialVersionUID = 1L;

	private long repayPlanId;

    private int periodNo;

    private int periodCount;

    private String tradeName;

    private long tradeId;

    private long repayUserId;

    private String repayUserName;

    private String repayUserRealName;

    private String repayDivisionWay;

    private Money amount = new Money(0, 0);

    private Money originalAmount = new Money(0, 0);

    private String status;

    private Date repayDate;

    private Date actualRepayDate;

    private Date rawAddTime;

    private Date rawUpdateTime;

    private String note;

    private String canPay;

    public long getRepayPlanId() {
        return repayPlanId;
    }

    public void setRepayPlanId(long repayPlanId) {
        this.repayPlanId = repayPlanId;
    }

    public int getPeriodNo() {
        return periodNo;
    }

    public void setPeriodNo(int periodNo) {
        this.periodNo = periodNo;
    }

    public int getPeriodCount() {
        return periodCount;
    }

    public void setPeriodCount(int periodCount) {
        this.periodCount = periodCount;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public long getTradeId() {
        return tradeId;
    }

    public void setTradeId(long tradeId) {
        this.tradeId = tradeId;
    }

    public long getRepayUserId() {
        return repayUserId;
    }

    public void setRepayUserId(long repayUserId) {
        this.repayUserId = repayUserId;
    }

    public String getRepayUserName() {
        return repayUserName;
    }

    public void setRepayUserName(String repayUserName) {
        this.repayUserName = repayUserName;
    }

    public String getRepayUserRealName() {
        return repayUserRealName;
    }

    public void setRepayUserRealName(String repayUserRealName) {
        this.repayUserRealName = repayUserRealName;
    }

    public String getRepayDivisionWay() {
        return repayDivisionWay;
    }

    public void setRepayDivisionWay(String repayDivisionWay) {
        this.repayDivisionWay = repayDivisionWay;
    }

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    public Money getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(Money originalAmount) {
        this.originalAmount = originalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(Date repayDate) {
        this.repayDate = repayDate;
    }

    public Date getActualRepayDate() {
        return actualRepayDate;
    }

    public void setActualRepayDate(Date actualRepayDate) {
        this.actualRepayDate = actualRepayDate;
    }

    public Date getRawAddTime() {
        return rawAddTime;
    }

    public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
    }

    public Date getRawUpdateTime() {
        return rawUpdateTime;
    }

    public void setRawUpdateTime(Date rawUpdateTime) {
        this.rawUpdateTime = rawUpdateTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public String getCanPay() {
        return canPay;
    }

    public void setCanPay(String canPay) {
        this.canPay = canPay;
    }
    
	public Money getInterest()
	{
		if(this.getPeriodNo()==this.getPeriodCount())
		{
			return this.getAmount().subtract(this.getOriginalAmount());
		}
		else
		{
			return this.getAmount();
		}
	}
}
