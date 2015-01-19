package com.icebreak.p2p.integration.openapi;

import com.icebreak.p2p.integration.openapi.order.QueryDepositOrder;
import com.icebreak.p2p.integration.openapi.result.DepositQueryResult;

public interface DepositQueryService {

	DepositQueryResult depositQueryService(QueryDepositOrder depositOrder);

}
