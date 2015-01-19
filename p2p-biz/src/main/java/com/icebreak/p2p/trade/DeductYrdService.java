package com.icebreak.p2p.trade;

import com.icebreak.p2p.integration.openapi.order.DepositDeductOrder;
import com.icebreak.p2p.integration.openapi.result.DeductDepositResult;

public interface DeductYrdService {

	
	DeductDepositResult deductDeposit(DepositDeductOrder deductOrder) throws Exception;
	
}
