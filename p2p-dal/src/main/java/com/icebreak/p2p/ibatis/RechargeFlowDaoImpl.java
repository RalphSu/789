package com.icebreak.p2p.ibatis;

import com.icebreak.p2p.daointerface.IdentityObtainer;
import com.icebreak.p2p.daointerface.RechargeFlowDao;
import com.icebreak.p2p.dataobject.RechargeFlow;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;
import java.util.Map;


public class RechargeFlowDaoImpl extends SqlMapClientDaoSupport implements RechargeFlowDao {
	
	private IdentityObtainer identityObtainer;
	
	public void setIdentityObtainer(IdentityObtainer identityObtainer) {
		this.identityObtainer = identityObtainer;
	}

	@Override
	public void addRechargeFlow(RechargeFlow rechargeFlow) {
		getSqlMapClientTemplate().insert("RECHARGEFLOW-INSERT", rechargeFlow);
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<RechargeFlow> getFlowList(Map<String, Object> condition) {
		List<RechargeFlow> queryForList=null;
		try{
			queryForList = (List<RechargeFlow>)getSqlMapClientTemplate().queryForList("RECHARGEFLOW-SELECT", condition);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return queryForList;
	}
	@Override
	public RechargeFlow getFlow(RechargeFlow rechargeFlow){
		return (RechargeFlow)getSqlMapClientTemplate().queryForObject("RECHARGEFLOW-SELECT-OUTBIZNO", rechargeFlow);
	}

	@Override
	public int update(RechargeFlow rechargeFlow){
		return getSqlMapClientTemplate().update("RECHARGEFLOW-UPDATE", rechargeFlow);
	}

	@Override
	public long getFlowCount(Map<String, Object> condition) {
		try{
			return (Long)getSqlMapClientTemplate().queryForObject("RECHARGEFLOW-COUNT", condition);
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
		
	}
	
}
