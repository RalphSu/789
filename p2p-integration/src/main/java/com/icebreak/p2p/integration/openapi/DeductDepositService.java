package com.icebreak.p2p.integration.openapi;

import com.icebreak.p2p.integration.openapi.context.OpenApiContext;
import com.icebreak.p2p.integration.openapi.order.DepositDeductOrder;
import com.icebreak.p2p.integration.openapi.order.EBankDepositDeductOrder;
import com.icebreak.p2p.integration.openapi.result.DeductDepositResult;
import com.yiji.openapi.sdk.message.common.funds.DeductDepositApplyResponse;

public interface DeductDepositService {

	DeductDepositApplyResponse applyDeductDeposit(DepositDeductOrder order, OpenApiContext openApiContext);
	
	DeductDepositResult applyEBankDeposit(EBankDepositDeductOrder order);
}
