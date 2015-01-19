package com.icebreak.p2p.integration.openapi;

import com.icebreak.p2p.integration.openapi.context.OpenApiContext;
import com.icebreak.p2p.integration.openapi.order.QuerySignBankOrder;
import com.icebreak.p2p.integration.openapi.order.SignOrder;
import com.icebreak.p2p.integration.openapi.result.SignBankResult;
import com.icebreak.p2p.integration.openapi.result.SignResult;

public interface SignService {

    /**
     * 签约银行卡
     *
     * @param signOrder
     * @return
     */
    SignResult sign(SignOrder signOrder);

    /**
     * 查询签约银行卡
     *
     * @param querySignBankOrder
     * @return
     */
    SignBankResult querySignBankCard(QuerySignBankOrder querySignBankOrder, OpenApiContext openApiContext);

}
