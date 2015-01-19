package com.icebreak.p2p.base.data;

import java.util.List;

import com.icebreak.p2p.ws.base.info.BankBasicInfo;
import com.icebreak.p2p.ws.base.info.BankConfigInfo;

public interface BankDataService {
	/**
	 * 加载启用的全部银行
	 * @return
	 */
	public List<BankConfigInfo> loadAllBankConfigInfo();
	
	/**
	 * 加载全部银行
	 * @return
	 */
	public List<BankConfigInfo> loadAllBankConfigInfoIgnoredStatus();
	
	public BankConfigInfo loadBankConfigInfo(String bankCode);
	
	public List<BankBasicInfo> getDeductBank();
	
	/**
	* 
	*/
	void clearCache();
	
	/**
	 * @return
	 */
	List<BankBasicInfo> getBankBasicInfos();
	
}
