package com.icebreak.p2p.dataobject.viewObject;

import java.util.Date;

import com.icebreak.p2p.dataobject.AmountFlow;
import com.icebreak.p2p.util.DateUtil;

public class AmounFlowVO extends AmountFlow{

	
	private static final long serialVersionUID = 1L;
	/**
	 * 流水类型
	 */
	private  String flowType;
	/**
	 *入账账户姓名 realName
	 */
	private  String inUserName;
	/**
	 * 出账账户姓名realName
	 */
	private  String outUserName;
	public AmounFlowVO() {
		super();
	}

	public AmounFlowVO(String flowCode, String amountOut, String amountIn,
			long amount, int status, String note, Date date, long inUserId,
			long outUserId, String type) {
		super(flowCode, amountOut, amountIn, amount, status, note, date, inUserId,
				outUserId, type);
	}

	public String getFlowType() {
		return flowType;
	}

	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}

	public String getInUserName() {
		return inUserName;
	}

	public void setInUserName(String inUserName) {
		this.inUserName = inUserName;
	}

	public String getOutUserName() {
		return outUserName;
	}

	public void setOutUserName(String outUserName) {
		this.outUserName = outUserName;
	}
	
	public String getConvertDate() {
		return DateUtil.simpleFormatYmdhms(this.getDate());
	}

	@Override
	public String toString() {
		return "AmounFlowVO [getFlowCode()=" + getFlowCode()
				+ ", getInUserId()=" + getInUserId() + ", getOutUserId()="
				+ getOutUserId() + ", getType()=" + getType() + ", getId()="
				+ getId() + ", getAmountOut()=" + getAmountOut()
				+ ", getAmountIn()=" + getAmountIn() + ", getAmount()="
				+ getAmount() + ", getStatus()=" + getStatus() + ", getNote()="
				+ getNote() + ", toString()=" + super.toString()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ "]";
	}
	
	
}
