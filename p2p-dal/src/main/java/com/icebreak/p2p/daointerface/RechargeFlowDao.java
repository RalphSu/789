package com.icebreak.p2p.daointerface;

import com.icebreak.p2p.dataobject.RechargeFlow;

import java.util.List;
import java.util.Map;

public interface RechargeFlowDao {
	

	public void addRechargeFlow(RechargeFlow rechargeFlow);

	public List<RechargeFlow> getFlowList(Map<String, Object> condition);

	public RechargeFlow getFlow(RechargeFlow rechargeFlow);

	public int update(RechargeFlow rechargeFlow);

	public long getFlowCount(Map<String, Object> condition);
}
