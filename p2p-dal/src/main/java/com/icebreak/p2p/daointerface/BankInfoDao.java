package com.icebreak.p2p.daointerface;

import java.util.List;
import java.util.Map;

import com.icebreak.p2p.dataobject.BankInfo;

public interface BankInfoDao {
	/**
	 * 插入一条新的银行信息记录
	 * @param bank
	 */
	public void insert(BankInfo bank);
	/**
	 * 更新银行信息记录
	 * @param bank
	 * @return
	 */
	public int update(BankInfo bank);
	/**
	 * 根据条件查询出银行信息
	 * @param conditions
	 * @return
	 */
	public List<BankInfo> getBankListByCondtions(Map<String,Object> conditions); 
	/**
	 * 根据条件查询出银行数量
	 * @param conditions
	 * @return
	 */
	public long getBankCountByCondtions(Map<String,Object> conditions);
	/**
	 * 根据ID删除一条记录
	 * @param bankId
	 * @return
	 */
	public int deleteById(long bankId);

}
