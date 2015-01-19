package com.icebreak.p2p.trade;

import com.icebreak.p2p.dataobject.FundsRecord;

public interface FundsRecordService {
	/**
	 * 新增一条资金记录
	 * @param fundsRecord
	 */
	public void addFundsRecord(FundsRecord fundsRecord);
	/**
	 * 更新一条资金记录
	 * @param fundsRecord
	 * @return
	 */
	public int updateFundsRecord(FundsRecord fundsRecord);
	/**
	 * 取得查询记录数
	 * @return
	 */
	public long getFundsRecordCounts();

}
