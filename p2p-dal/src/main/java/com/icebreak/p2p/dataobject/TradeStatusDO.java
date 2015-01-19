package com.icebreak.p2p.dataobject;

import java.util.ArrayList;
import java.util.List;

import com.icebreak.p2p.util.YrdConstants;

public class TradeStatusDO{
	private long tradeStateKey;
	private String tradeStateValue;
	/**
	 * 保障机构模板
	 */
	private String tradeStateValue1;
	
	static List<TradeStatusDO> tradeStatus = new ArrayList<TradeStatusDO>(); 
	public long getTradeStateKey() {
		return tradeStateKey;
	}
	public void setTradeStateKey(long tradeStateKey) {
		this.tradeStateKey = tradeStateKey;
		if(tradeStateKey==6)
		{
			tradeStateValue1="保障机构审核中";
		}
		
	}
	public String getTradeStateValue() {
		return tradeStateValue;
	}
	public void setTradeStateValue(String tradeStateValue) {
		
		this.tradeStateValue = tradeStateValue;
		if(tradeStateKey!=6)
			this.tradeStateValue1=this.tradeStateValue;
	}
	public String getTradeStateValue1() {
		return tradeStateValue1;
	}
	public void setTradeStateValue1(String tradeStateValue1) {
		this.tradeStateValue1 = tradeStateValue1;
	}
	public static TradeStatusDO getTradeStatus(int value)
	{
		 List<TradeStatusDO> list=buildTradeStatus();
		 for(TradeStatusDO statusDO:list)
		 {
			 if(statusDO.getTradeStateKey()==value)
				 return statusDO;
		 }
		 return null;
	}
	public static List<TradeStatusDO> buildTradeStatus() {
		
		if(tradeStatus.size()>0)return tradeStatus;
		
		TradeStatusDO tradeStatu = new TradeStatusDO();
		tradeStatu.setTradeStateKey(1);
		tradeStatu.setTradeStateValue(YrdConstants.GuaranteeTradeStatus.GUARANTEE_SIGN);
		
		TradeStatusDO tradeStatu1 = new TradeStatusDO();
		tradeStatu1.setTradeStateKey(2);
		tradeStatu1.setTradeStateValue(YrdConstants.GuaranteeTradeStatus.GUARANTEE_IMPLEMENT);
		
		TradeStatusDO tradeStatu2 = new TradeStatusDO();
		tradeStatu2.setTradeStateKey(3);
		tradeStatu2.setTradeStateValue(YrdConstants.GuaranteeTradeStatus.GUARANTEE_FINISH);
		
		TradeStatusDO tradeStatu7 = new TradeStatusDO();
		tradeStatu7.setTradeStateKey(4);
		tradeStatu7.setTradeStateValue(YrdConstants.GuaranteeTradeStatus.GUARANTEE_FAILD);
		
		TradeStatusDO tradeStatu4 = new TradeStatusDO();
		tradeStatu4.setTradeStateKey(5);
		tradeStatu4.setTradeStateValue(YrdConstants.GuaranteeTradeStatus.GUARANTEE_BROKEN);
		
		TradeStatusDO tradeStatu3 = new TradeStatusDO();
		tradeStatu3.setTradeStateKey(6);
		tradeStatu3.setTradeStateValue(YrdConstants.GuaranteeTradeStatus.GUARANTEE_AUDITING);
		
		TradeStatusDO tradeStatu5 = new TradeStatusDO();
		tradeStatu5.setTradeStateKey(7);
		tradeStatu5.setTradeStateValue(YrdConstants.GuaranteeTradeStatus.COMPENSATORY_REPAY_FINISH);
		
		TradeStatusDO tradeStatu6 = new TradeStatusDO();
		tradeStatu6.setTradeStateKey(8);
		tradeStatu6.setTradeStateValue(YrdConstants.TradeViewCanstants.DOREPAY);
		
		tradeStatus.add(tradeStatu);
		tradeStatus.add(tradeStatu1);
		tradeStatus.add(tradeStatu2);
		tradeStatus.add(tradeStatu3);
		tradeStatus.add(tradeStatu4);
		tradeStatus.add(tradeStatu5);
		tradeStatus.add(tradeStatu6);
		tradeStatus.add(tradeStatu7);
		
		return tradeStatus;
	}
}
