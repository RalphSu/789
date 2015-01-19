package com.icebreak.p2p.ibatis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.daointerface.ChargeDetailDao;
import com.icebreak.p2p.daointerface.IdentityObtainer;
import com.icebreak.p2p.dataobject.ChargeDetail;

public class ChargeDetailDaoImpl extends SqlMapClientDaoSupport implements
		ChargeDetailDao {
	
	private IdentityObtainer identityObtainer;
	
	public void setIdentityObtainer(IdentityObtainer identityObtainer) {
		this.identityObtainer = identityObtainer;
	}

	@Override
	public void addChargeDetail(ChargeDetail detail) {
      getSqlMapClientTemplate().insert("CHARGEDETAIL-ADD", detail);
      detail.setId(identityObtainer.getPrimaryKey());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ChargeDetail> getByUserId(long id) {
		return (List<ChargeDetail>)getSqlMapClientTemplate().queryForList("CHARGEDETAIL-GETBYUSERID", id);
	}

	@Override
	public int modify(long id, boolean status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("status", status);
		params.put("date", new Date());
		return getSqlMapClientTemplate().update("CHARGEDETAIL-MODIFY", params);
	}

	@Override
	public Date getLastChargeDate(long userId) {
		return (Date)getSqlMapClientTemplate().queryForObject("CHARGEDETAIL-GETLASTCHARGEDATE", userId);
	}
}
