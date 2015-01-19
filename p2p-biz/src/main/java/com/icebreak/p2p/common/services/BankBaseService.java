package com.icebreak.p2p.common.services;

import java.util.List;

import com.icebreak.p2p.dataobject.BankInfo;
import com.icebreak.p2p.page.PageParam;

public interface BankBaseService {
	/**
	 * 插入一条新的银行信息记录
	 * @param bank
	 */
	public void addBank(BankInfo bank);
	/**
	 * 更新一条银行信息记录
	 * @param bank
	 * @return
	 */
	public int updateBank(BankInfo bank);
	/**
	 * 根据条件查询银行信息
	 * @param condtions
	 * @return
	 */
	public List<BankInfo> queryByConditions(BankInfo bankInfo,PageParam pageParam);
	/**
	 * 查询银行数量
	 * @param condtions
	 * @return
	 */
	public long queryCountByConditions(BankInfo bankInfo);
	/**
	 * 删除一条银行信息记录
	 * @param bankId
	 * @return
	 */
	public int deleteBankInfoById(long bankId);
	/**
	 * 查询银行信息
	 * @param bank
	 * @return
	 */
	public BankInfo query19BankInfo(String bankCode);
	/**
	 * 获取用户当日所绑定的银行卡提现、充值限额剩余的额度
	 * @param bankInfo
	 * @param payType
	 * @return
	 */
	public double getDifference(BankInfo bankInfo,String bankCardNo,String payType);
	


}
