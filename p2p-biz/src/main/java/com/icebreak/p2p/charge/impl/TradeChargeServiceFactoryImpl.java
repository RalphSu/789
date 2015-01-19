package com.icebreak.p2p.charge.impl;

import java.util.Map;

import com.icebreak.p2p.charge.TradeChargeService;
import com.icebreak.p2p.charge.TradeChargeServiceFactory;

public class TradeChargeServiceFactoryImpl implements TradeChargeServiceFactory {
	
	private Map<String, TradeChargeService> tradeChargeService;
	
	public void setTradeChargeService(Map<String, TradeChargeService> tradeChargeService) {
		this.tradeChargeService = tradeChargeService;
	}

	@Override
	public TradeChargeService getTradeChargeService(String chargeCode) {
		if(tradeChargeService != null){
			return tradeChargeService.get(chargeCode);
		}
		return null;
	}

}
