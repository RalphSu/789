package com.icebreak.p2p.common.services;

import java.util.List;

import com.icebreak.p2p.dal.dataobject.SysParamDO;
import com.icebreak.p2p.ws.order.SysParamOrder;
import com.icebreak.p2p.ws.result.P2PBaseResult;
import com.icebreak.p2p.ws.service.query.order.SysParamQueryOrder;
import com.icebreak.p2p.ws.service.query.result.QueryBaseBatchResult;

public interface SysParameterService {
	public String getSysParameterValue(String paramName);
	
	public SysParamDO getSysParameterValueDO(String paramName);
	
	public P2PBaseResult updateSysParameterValueDO(SysParamOrder sysParamOrder);
	
	public P2PBaseResult insertSysParameterValueDO(SysParamOrder sysParamOrder);
	
	public void deleteSysParameterValue(String paramName);
	
	public List<SysParamDO> getSysParameterValueList(String paramName);

    QueryBaseBatchResult<SysParamDO> querySysPram(SysParamQueryOrder sysParamQueryOrder);
	
	public void clearCache();
}
