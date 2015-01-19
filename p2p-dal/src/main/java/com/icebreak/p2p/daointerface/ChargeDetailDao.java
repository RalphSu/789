package com.icebreak.p2p.daointerface;

import java.util.Date;
import java.util.List;

import com.icebreak.p2p.dataobject.ChargeDetail;

public interface ChargeDetailDao {
	/**
	 * 添加
	 * @param detail
	 */
	public void addChargeDetail(ChargeDetail detail);
	/**
	 * 根据用户ID查询
	 * @param id
	 * @return
	 */
	public List<ChargeDetail> getByUserId(long id);
	/**
	 * 修改
	 * @param id
	 * @param status
	 * @return
	 */
	public int modify(long id, boolean status);
	/**
	 * 根据用户ID获取最后一次收费时间
	 * @param userId
	 * @return
	 */
	public Date getLastChargeDate(long userId);

}
