package com.icebreak.p2p.service.JoinApplication;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.icebreak.p2p.dal.dataobject.O2oJoinApplicationDO;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.service.JoinApplication.queryOrder.JoinApplicationOrder;


public interface JoinApplicationService {
	
	 public long insert(JoinApplicationOrder appliOrder) throws DataAccessException;

	  public O2oJoinApplicationDO findById(long id) throws DataAccessException;

	  public int deleteById(long id) throws DataAccessException;

	  public int updateById(JoinApplicationOrder queryConditions) throws DataAccessException;
	 
	  public Page<O2oJoinApplicationDO> findByCondition(JoinApplicationOrder queryConditions,
				PageParam pageParam) throws DataAccessException;

	  public long countByCondition(Map<String, Object> condition) throws DataAccessException;


}
