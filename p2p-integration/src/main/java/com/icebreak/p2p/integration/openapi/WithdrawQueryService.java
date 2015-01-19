package com.icebreak.p2p.integration.openapi;

import com.icebreak.p2p.integration.openapi.order.WithdrawQueryOrder;
import com.icebreak.p2p.integration.openapi.result.WithdrawQueryResult;

public interface WithdrawQueryService {
	WithdrawQueryResult queryWithdrawService(WithdrawQueryOrder order);
}
