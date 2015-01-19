package com.icebreak.p2p.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.daointerface.IdentityObtainer;
import com.icebreak.p2p.daointerface.PopInfoDao;
import com.icebreak.p2p.dataobject.PopInfoDO;
import com.icebreak.p2p.dataobject.TradeDetail;

public class PopInfoDaoImpl extends SqlMapClientDaoSupport implements PopInfoDao{
	private IdentityObtainer identityObtainer;
	
	public void setIdentityObtainer(IdentityObtainer identityObtainer) {
		this.identityObtainer = identityObtainer;
	}
	@Override
	public void addNotice(PopInfoDO info) {
		getSqlMapClientTemplate().insert("POPINFO-ADD", info);
	}
	@Override
	public long queryCountByCondition(Map<String, Object> conditions) {
		
		return (Long)getSqlMapClientTemplate().queryForObject("POPINFO-QUERYBYPROPERTIESCOUNT", conditions);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<PopInfoDO> queryListByCondition(Map<String, Object> conditions) {
		return (List<PopInfoDO>)getSqlMapClientTemplate().queryForList("POPINFO-QUERYBYPROPERTIES", conditions);
	}
	@Override
	public PopInfoDO getByPopId(long popId) {
		return (PopInfoDO)getSqlMapClientTemplate().queryForObject("POPINFO-QUERYBYID", popId);
	}
	@Override
	public void updateNotice(PopInfoDO info) {
		getSqlMapClientTemplate().update("POPINFO-MODIFY", info);
	}

}
