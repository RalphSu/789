package com.icebreak.p2p.daointerface;

import java.util.List;
import java.util.Map;

import com.icebreak.p2p.dataobject.Investment;

public interface InvestmentDao {
	/**
	 * 根据条件查询
	 * @param params
	 * @return
	 */
	public List<Investment> getByProperties(Map<String, Object> params);
	
	/**
	 * 根据条件查询条数
	 * @param params
	 * @return
	 */
	public long getCountByProperties(Map<String, Object> params);
	/**
	 * 根据ID 查询
	 * @param id
	 * @return
	 */
	public Investment getById(long id);

}
