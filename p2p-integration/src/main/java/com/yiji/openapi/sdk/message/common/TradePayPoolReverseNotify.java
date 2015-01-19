package com.yiji.openapi.sdk.message.common;

import java.util.List;
import java.util.Map;

import com.yiji.openapi.sdk.message.BaseNotify;
import com.yiji.openapi.sdk.util.BeanMapper;
import com.yiji.openapi.sdk.util.JsonMapper;

public class TradePayPoolReverseNotify extends BaseNotify {
	private String executeAction;
	private String executeDate;
	private String executeStatus;
	private String notifyTime;
	private String parentNo;
	private String parentOrderNo;
	private String tradeSimpleInfos;
	private List<SubOrder> subOrders;

	@SuppressWarnings("unchecked")
	@Override
	protected void doUnmarshall(Map<String, String> data) {
		List<Map<String, String>> temp = JsonMapper.nonDefaultMapper().fromJson(this.tradeSimpleInfos, List.class);
		this.subOrders = BeanMapper.mapList(temp, SubOrder.class);
		temp.clear();
		temp = null;
		this.tradeSimpleInfos = null;
	}

	public String getExecuteAction() {
		return executeAction;
	}

	public void setExecuteAction(String executeAction) {
		this.executeAction = executeAction;
	}

	public String getExecuteDate() {
		return executeDate;
	}

	public void setExecuteDate(String executeDate) {
		this.executeDate = executeDate;
	}

	public String getExecuteStatus() {
		return executeStatus;
	}

	public void setExecuteStatus(String executeStatus) {
		this.executeStatus = executeStatus;
	}

	public String getNotifyTime() {
		return notifyTime;
	}

	public void setNotifyTime(String notifyTime) {
		this.notifyTime = notifyTime;
	}

	public String getParentNo() {
		return parentNo;
	}

	public void setParentNo(String parentNo) {
		this.parentNo = parentNo;
	}

	public String getParentOrderNo() {
		return parentOrderNo;
	}

	public void setParentOrderNo(String parentOrderNo) {
		this.parentOrderNo = parentOrderNo;
	}

	public String getTradeSimpleInfos() {
		return tradeSimpleInfos;
	}

	public void setTradeSimpleInfos(String tradeSimpleInfos) {
		this.tradeSimpleInfos = tradeSimpleInfos;
	}

	public List<SubOrder> getSubOrders() {
		return subOrders;
	}

	public void setSubOrders(List<SubOrder> subOrders) {
		this.subOrders = subOrders;
	}

}
