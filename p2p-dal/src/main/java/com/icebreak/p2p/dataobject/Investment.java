package com.icebreak.p2p.dataobject;

import java.io.Serializable;
import java.util.Date;

public class Investment implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 投资明细ID
	 */
	private long id = 0;
	/**
	 * 交易ID
	 */
	private long tradeId = 0;
	/**
	 * 需求ID
	 */
	private long demandId = 0;
	/**
	 * 交易号
	 */
	private String tradeCode = null;
	/**
	 * 交易名称
	 */
	private String tradeName = null;
	/**
	 * 交易状态
	 */
	private int tradeStatus = 0;
	/**
	 * 金额
	 */
	private long amount = 0;
	/**
	 * 收款方用户ID
	 */
	private long receiveId = 0;
	/**
	 * 收款方真实姓名
	 */
	private String receiveRealName = null;
	/**
	 * 收款方用户名
	 */
	private String receiveUserName = null;
	/**
	 * 付款方用户ID
	 */
	private long payId = 0;
	/**
	 * 付款方真实姓名
	 */
	private String payRealName = null;
	/**
	 * 付款方用户名
	 */
	private String payUserName = null;
	
	/**
	 * 付款方Email
	 */
	private String payUserEmail = null;
	
	/**
	 * 日期
	 */
	private Date payDate = null;
	/**
	 * 创建时间
	 */
	private Date createDate = null;
	/**
	 * 完成时间
	 */
	private Date finishDate = null;
	/**
	 * 付款描述
	 */
	private String payNote = null;
	/**
	 * 交易描述
	 */
	private String tradeNote = null;
	/**
	 * 商务服务费
	 */
	private Long chargeAmount = null;
	
	public Long getChargeAmount() {
		return chargeAmount == null ? 0 : chargeAmount;
	}
	
	public void setChargeAmount(Long chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
	
	public String getPayNote() {
		return payNote;
	}
	
	public void setPayNote(String payNote) {
		this.payNote = payNote;
	}
	
	public String getTradeNote() {
		return tradeNote;
	}
	
	public void setTradeNote(String tradeNote) {
		this.tradeNote = tradeNote;
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
	
	public long getDemandId() {
		return demandId;
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
	
	public String getTradeName() {
		return tradeName;
	}
	
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	
	public int getTradeStatus() {
		return tradeStatus;
	}
	
	public void setTradeStatus(int tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	
	public long getAmount() {
		return amount;
	}
	
	public void setAmount(long amount) {
		this.amount = amount;
	}
	
	public long getReceiveId() {
		return receiveId;
	}
	
	public String getReceiveRealName() {
		return receiveRealName;
	}
	
	public void setReceiveRealName(String receiveRealName) {
		this.receiveRealName = receiveRealName;
	}
	
	public String getReceiveUserName() {
		return receiveUserName;
	}
	
	public void setReceiveUserName(String receiveUserName) {
		this.receiveUserName = receiveUserName;
	}
	
	public String getPayRealName() {
		return payRealName;
	}
	
	public void setPayRealName(String payRealName) {
		this.payRealName = payRealName;
	}
	
	public String getPayUserName() {
		return payUserName;
	}
	
	public void setPayUserName(String payUserName) {
		this.payUserName = payUserName;
	}
	
	public void setReceiveId(long receiveId) {
		this.receiveId = receiveId;
	}
	
	public long getPayId() {
		return payId;
	}
	
	public void setPayId(long payId) {
		this.payId = payId;
	}
	
	public Date getPayDate() {
		return payDate;
	}
	
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	
	public String getPayUserEmail() {
		return this.payUserEmail;
	}
	
	public void setPayUserEmail(String payUserEmail) {
		this.payUserEmail = payUserEmail;
	}
	
	public String getStatus() {
		if (tradeStatus == 1) {
			return "待成立";
		} else if (tradeStatus == 2) {
			return "已成立";
		} else if (tradeStatus == 3) {
			return "正常还款";
		} else if (tradeStatus == 4) {
			return "未成立";
		} else if (tradeStatus == 5) {
			return "到期未还款";
		} else if (tradeStatus == 6) {
			return "担保机构审核中";
		} else if (tradeStatus == 7) {
			return "违约代偿完成";
		} else if (tradeStatus == 8) {
			return "等待融资人还款";
		} else {
			return "暂无数据";
		}
	}
}
