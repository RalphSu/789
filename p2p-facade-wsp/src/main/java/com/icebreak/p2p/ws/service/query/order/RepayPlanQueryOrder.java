package com.icebreak.p2p.ws.service.query.order;

import com.icebreak.p2p.ws.service.query.YrdQueryPageBase;
import com.icebreak.util.lang.util.money.Money;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Date;
import java.util.List;


public class RepayPlanQueryOrder extends YrdQueryPageBase {
	

	
	/** 执行操作时间开始 */
	private Date startDate;
	
	/** 执行操作时间结束 */
	private Date endDate;

	private Date startActualDate;
	
	private Date endActualDate;
	
    private List<String> statusList;

    private String tradeName;

    private long repayUserId;
    
	private long repayPlanId;

	private int periodNo;

	private int periodCount;

	private long tradeId;

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

    private long guaranteeId;


    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<String> status) {
        this.statusList = status;
    }


    public long getRepayUserId() {
        return repayUserId;
    }

    public void setRepayUserId(long repayUserId) {
        this.repayUserId = repayUserId;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

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

	public long getTradeId() {
		return tradeId;
	}

	public void setTradeId(long tradeId) {
		this.tradeId = tradeId;
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


    public long getGuaranteeId() {
        return guaranteeId;
    }

    public void setGuaranteeId(long guaranteeId) {
        this.guaranteeId = guaranteeId;
    }

    @Override
	public void check() {
	}
	
	public Date getStartActualDate() {
		return startActualDate;
	}

	public void setStartActualDate(Date startActualDate) {
		this.startActualDate = startActualDate;
	}

	public Date getEndActualDate() {
		return endActualDate;
	}

	public void setEndActualDate(Date endActualDate) {
		this.endActualDate = endActualDate;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RepayPlanQueryOrder [startDate=");
		builder.append(startDate);
		builder.append(", endDate=");
		builder.append(endDate);
		builder.append(", startActualDate=");
		builder.append(startActualDate);
		builder.append(", endActualDate=");
		builder.append(endActualDate);
		builder.append(", statusList=");
		builder.append(statusList);
		builder.append(", tradeName=");
		builder.append(tradeName);
		builder.append(", repayUserId=");
		builder.append(repayUserId);
		builder.append(", repayPlanId=");
		builder.append(repayPlanId);
		builder.append(", periodNo=");
		builder.append(periodNo);
		builder.append(", periodCount=");
		builder.append(periodCount);
		builder.append(", tradeId=");
		builder.append(tradeId);
		builder.append(", repayUserName=");
		builder.append(repayUserName);
		builder.append(", repayUserRealName=");
		builder.append(repayUserRealName);
		builder.append(", repayDivisionWay=");
		builder.append(repayDivisionWay);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", originalAmount=");
		builder.append(originalAmount);
		builder.append(", status=");
		builder.append(status);
		builder.append(", repayDate=");
		builder.append(repayDate);
		builder.append(", actualRepayDate=");
		builder.append(actualRepayDate);
		builder.append(", rawAddTime=");
		builder.append(rawAddTime);
		builder.append(", rawUpdateTime=");
		builder.append(rawUpdateTime);
		builder.append(", note=");
		builder.append(note);
		builder.append("]");
		return builder.toString();
	}
}
