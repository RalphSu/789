package com.icebreak.p2p.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.daointerface.TransferTradeDao;
import com.icebreak.p2p.dataobject.TransferTrade;

public class TransferTradeDaoImpl extends SqlMapClientDaoSupport implements
		TransferTradeDao {

	@Override
	public TransferTrade getTransferTrade(long tradeId, String roleCode) {
		List<TransferTrade> list = getTransferTrades(tradeId, roleCode);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public long getTradeAmount(long tradeId, String roleCode) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("code", roleCode);
		params.put("tradeId", tradeId);
		return (Long)getSqlMapClientTemplate().queryForObject("TRANSFERTRADE-GETLOANEDAMOUNT", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransferTrade> getTransferTrades(long tradeId, String... roles) {
		if(roles == null || roles.length < 1){
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tradeId", tradeId);
		params.put("roles", roles);
		return (List<TransferTrade>)getSqlMapClientTemplate().queryForList("TRANSFERTRADE-GETTRANSFERTRADES", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransferTrade> getDivisionTransferTrades(long tradeId, boolean status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tradeId", tradeId);
		params.put("status", status);
		return (List<TransferTrade>)getSqlMapClientTemplate().queryForList("TRANSFERTRADE-GETDIVISIONTRANSFERTRADES", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransferTrade> getBrokerTransferTrades(long tradeId, long roleId,
			List<Long> ids) {
		if(ids == null || ids.size() < 1){
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tradeId", tradeId);
		params.put("ids", ids);
		params.put("roleId", roleId);
		return (List<TransferTrade>)getSqlMapClientTemplate().queryForList("TRANSFERTRADE-GETBROKERTRANSFERTRADES", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransferTrade> getMarketingTransferTrades(long tradeId,
			long roleId, List<Long> ids) {
		if(ids == null || ids.size() < 1){
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tradeId", tradeId);
		params.put("ids", ids);
		params.put("roleId", roleId);
		return (List<TransferTrade>)getSqlMapClientTemplate().queryForList("TRANSFERTRADE-GETMARKETINGTRANSFERTRADES", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransferTrade> getPhaseTransferTrades(long tradeId, String transferPhase, String tradeDetailStatus, String[] roles) {
//		if(roles == null || roles.length < 1){
//			return null;
//		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tradeId", tradeId);
		params.put("transferPhase", transferPhase);
		params.put("roles", roles);
		params.put("tradeDetailStatus", tradeDetailStatus);
		return (List<TransferTrade>)getSqlMapClientTemplate().queryForList("TRANSFERTRADE-GETPHASETRANSFERTRADES", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransferTrade> getPhaseTransferTrades(long tradeId,String transferPhase,int periodNo ,String... roles){
		if(roles == null || roles.length < 1){
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tradeId", tradeId);
		params.put("transferPhase", transferPhase);
		params.put("roles", roles);
		params.put("periodNo", periodNo);
		return (List<TransferTrade>)getSqlMapClientTemplate().queryForList("TRANSFERTRADE-GETPHASETRANSFERTRADES", params);
	}
	

	@Override
	public TransferTrade getPhaseTransferTrade(long tradeId, String transferPhase,
			String roleCode) {
		List<TransferTrade> list = getPhaseTransferTrades(tradeId, transferPhase, null, new String[]{roleCode});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

    @SuppressWarnings("unchecked")
    @Override
    public List<TransferTrade> getPhaseTransferTradesCharge(long tradeId, String transferPhase, String... roles) {
        if(roles == null || roles.length < 1){
            return null;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("tradeId", tradeId);
        params.put("transferPhase", transferPhase);
        params.put("roles", roles);
        return (List<TransferTrade>)getSqlMapClientTemplate().queryForList("TRANSFERTRADE-GETPHASETRANSFERTRADES_CHARGE", params);
    }
}
