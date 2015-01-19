package com.icebreak.p2p.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.daointerface.TradeDivisionDao;
import com.icebreak.p2p.dataobject.TradeDivision;

public class TradeDivisionDaoImpl extends SqlMapClientDaoSupport implements
		TradeDivisionDao {

	@Override
	public void addTradeDivision(TradeDivision tradeDivision) {
        getSqlMapClientTemplate().insert("TRADEDIVISION-ADD", tradeDivision);
	}

	@Override
	public TradeDivision getByTradeId(long tradeId) {
		return (TradeDivision)getSqlMapClientTemplate().queryForObject("TRADEDIVISION-GETBYTEMPLATEID", tradeId);
	}
}
