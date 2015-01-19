package com.icebreak.p2p.dal.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.dal.daointerface.CommonBranchInfoExtendDAO;
import com.icebreak.p2p.dal.dataobject.CommonBranchInfoExtendDO;

@SuppressWarnings("unchecked")
public class IbatisCommonBranchInfoExtendDAO extends SqlMapClientDaoSupport implements
																			CommonBranchInfoExtendDAO {
	
	@Override
	public List<CommonBranchInfoExtendDO> findBankInfosByBankId(String bankId)
																				throws DataAccessException {
		return getSqlMapClientTemplate().queryForList(
			"MS-COMMON-BRANCH-INFO-FIND-BANK-INFOS-BY-BANK-ID", bankId);
		
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<CommonBranchInfoExtendDO> findBankInfosByDistrict(String bankId, String districtNo)
																									throws DataAccessException {
		Map param = new HashMap();
		param.put("bankId", bankId);
		param.put("districtNo", districtNo);
		return getSqlMapClientTemplate().queryForList(
			"MS-COMMON-BRANCH-INFO-FIND-BANK-INFOS-BY-DISTRICT-NO", param);
	}
	
	@Override
	public List<CommonBranchInfoExtendDO> findOpeningBankByDistrict(String bankId, String nbNo,
																	String flag) {
		Map param = new HashMap();
		param.put("bankId", bankId);
		param.put("nbNo", nbNo);
		param.put("branchBankFlag", flag);
		return getSqlMapClientTemplate().queryForList(
			"MS-COMMON-BRANCH-INFO-FIND-OPENING-BANK-BY-DISTRICT-NO", param);
	}
	
}
