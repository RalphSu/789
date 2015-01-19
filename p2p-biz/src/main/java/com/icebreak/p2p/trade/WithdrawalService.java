package com.icebreak.p2p.trade;

import com.alibaba.fastjson.JSONObject;

public interface WithdrawalService {
	/**
	 * openApi提现
	 * @param withdrawInfo
	 * @param service
	 * @param coupons
	 * @return
	 * @throws Exception
	 */
	public JSONObject applyWithdrawOpenApi(String amount, String coupons) throws Exception;
	
}
