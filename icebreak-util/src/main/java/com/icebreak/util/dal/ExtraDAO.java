package com.icebreak.util.dal;

import java.util.Date;

import org.springframework.dao.DataAccessException;

public interface ExtraDAO {
	/**
	 * 获取系统时间
	 * 
	 * @return
	 */
	public Date getSysdate();
	
	/**
	 * 获取Seq
	 * 
	 * @param name
	 * @return
	 * @throws DataAccessException
	 */
	public long getNextSeq(String name) throws DataAccessException;
}
