package com.icebreak.p2p.dal.daointerface;


import com.icebreak.p2p.dal.dataobject.CommonBranchInfoExtendDO;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface CommonBranchInfoExtendDAO {
	public List<CommonBranchInfoExtendDO> findBankInfosByBankId(String bankId)
																				throws DataAccessException;
	
	public List<CommonBranchInfoExtendDO> findBankInfosByDistrict(String bankId, String districtNo)
																									throws DataAccessException;
	
	public List<CommonBranchInfoExtendDO> findOpeningBankByDistrict(String bankId, String nbNo,
																	String flag);
	
}
