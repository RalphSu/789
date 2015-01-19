package com.icebreak.p2p.trade;

import java.util.Map;

import com.icebreak.p2p.dataobject.Trade;

public interface RemoteTradeService {
	
	public final static String SIGN_PARAM_NAME = "sign";
	
	/**
	 * 创建远程交易
	 * @param loanerId 借款人ID
	 * @param amount 金额已分为单位
	 * @param tradeName  交易名称
	 * @param memo 描述
	 * @return
	 */
	public void createTrade(String loanerId, String tradeBizProductCode, String amount, String tradeName, String memo, RemoteTradeCallBack callBack);
	
	/**
	 * 获取重定向url
	 * @param params
	 * @return
	 */
	public String getRedirectUrl(Map<String, String> params);
	/**
	 * 校验签名
	 * @param params
	 * @param sign
	 * @return
	 */
	public boolean check(Map<String, String> params, String sign);
	/**
	 * 集体付款
	 * @param tradeNo
	 */
	public void collectivePay(Trade trade, SplitProfit profit, RemoteTradeCallBack callBack);
	/**
	 * 转账
	 * @param accountOut 出账账户ID
	 * @param accountIn 入账账户ID
	 * @param amount 金额
	 * @param ctrlTransferType 转账类型
	 */
	public <T> T transfer(Transfer transfer, RemoteTradeCallBackWithResult<T> callBack);

}
