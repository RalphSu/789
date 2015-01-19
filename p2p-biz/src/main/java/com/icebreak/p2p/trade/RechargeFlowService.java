package com.icebreak.p2p.trade;

import com.icebreak.p2p.dataobject.RechargeFlow;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;

import java.util.Date;
import java.util.List;

public interface RechargeFlowService {

	public void addRechargeFlow(RechargeFlow rechargeFlow) throws Exception;

	public List<RechargeFlow> getFlow(String payType,int status,Date startTime,Date endTime);

	public RechargeFlow queryByOutBizNo(String outBizNo);

	public RechargeFlow queryByLocalNo(String localNo);

	public int updateStatus(RechargeFlow rechargeFlow);

	public double getUserSumAmount(RechercheFlowOrder rechercheFlowOrder);

	public Page<RechargeFlow> getFlow(QueryTradeOrder queryTradeOrder,PageParam pageParam);
	
}
