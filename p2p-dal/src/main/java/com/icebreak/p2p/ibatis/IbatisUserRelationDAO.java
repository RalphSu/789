package com.icebreak.p2p.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.daointerface.UserRelationDAO;
import com.icebreak.p2p.dataobject.ProfitAsignInfo;
import com.icebreak.p2p.dataobject.UserRelationDO;

public class IbatisUserRelationDAO extends SqlMapClientDaoSupport implements UserRelationDAO {
	
	@Override
	public long insert(UserRelationDO userRelation) throws DataAccessException {
		if (userRelation == null) {
			throw new IllegalArgumentException("Can't insert a null data object into db.");
		}
		getSqlMapClientTemplate().insert("MS-USER-RELATION-INSERT", userRelation);
		return userRelation.getUserRelationId();
	}
	
	@Override
	public int update(UserRelationDO userRelation) throws DataAccessException {
		if (userRelation == null) {
			throw new IllegalArgumentException("Can't update by a null data object.");
		}
		return getSqlMapClientTemplate().update("MS-USER-RELATION-UPDATE", userRelation);
	}
	
	@Override
	public int delete(long userRelationId) throws DataAccessException {
		Long param = new Long(userRelationId);
		return getSqlMapClientTemplate().delete("MS-USER-RELATION-DELETE", param);
	}
	
	@Override
	public int getRelationsCountByConditions(Map<String, Object> conditions) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
			"MS-USER-RELATIONS-COUNTBYCONDITIONS", conditions);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserRelationDO> getRelationsByConditions(Map<String, Object> conditions) {
		return getSqlMapClientTemplate().queryForList("MS-USER-RELATIONS-BYCONDITIONS", conditions);
	}
	
	@Override
	public int countInvestorsInThisJG(long userId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
			"MS-USER-RELATIONS-COUNTINVESTORSBYJGID", userId);
	}
	
	@Override
	public void addProfit(ProfitAsignInfo profitAsignInfo) throws DataAccessException {
		getSqlMapClientTemplate().insert("MS-PROFITASIGNINFO-INSERT", profitAsignInfo);
		
	}
	
	@Override
	public int updateProfit(ProfitAsignInfo profitAsignInfo) {
		return getSqlMapClientTemplate().update("MS-PROFITASIGNINFO-UPDATE", profitAsignInfo);
	}
	
	@Override
	public ProfitAsignInfo getProfit(long receiveId) {
		return (ProfitAsignInfo) getSqlMapClientTemplate().queryForObject(
			"MS-PROFITASIGNINFO-QUERY", receiveId);
	}
	
	@Override
	public ProfitAsignInfo getProfit(long receiveId, long distributionId) {
		Map<String, Object> condMap = new HashMap<String, Object>();
		condMap.put("receiveId", receiveId);
		condMap.put("distributionId", distributionId);
		return (ProfitAsignInfo) getSqlMapClientTemplate().queryForObject(
			"MS-PROFITASIGNINFO-QUERY", condMap);
	}
	
	@Override
	public int deleteProfitAsign(String tblBaseId) {
		
		return getSqlMapClientTemplate().delete("MS-PROFITASIGNINFO-DELETE", tblBaseId);
	}
	
}
