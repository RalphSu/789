package com.icebreak.p2p.integration.openapi.result;

import java.util.ArrayList;
import java.util.List;

import com.icebreak.p2p.integration.openapi.info.RepayTradeShardInfo;

import com.icebreak.p2p.integration.openapi.info.RepayTradeSimpleInfo;

public class RepayTradeQueryResult extends TradeResult {
	
	private static final long serialVersionUID = 9086185514887765864L;
	
	private List<RepayTradeSimpleInfo> simpleInfos = new ArrayList<RepayTradeSimpleInfo>();
	private List<RepayTradeShardInfo> tradeShardInfos = new ArrayList<RepayTradeShardInfo>();
	
	public List<RepayTradeSimpleInfo> getSimpleInfos() {
		return this.simpleInfos;
	}
	
	public void setSimpleInfos(List<RepayTradeSimpleInfo> simpleInfos) {
		this.simpleInfos = simpleInfos;
	}
	
	public List<RepayTradeShardInfo> getTradeShardInfos() {
		return this.tradeShardInfos;
	}
	
	public void setTradeShardInfos(List<RepayTradeShardInfo> tradeShardInfos) {
		this.tradeShardInfos = tradeShardInfos;
	}
	
}
