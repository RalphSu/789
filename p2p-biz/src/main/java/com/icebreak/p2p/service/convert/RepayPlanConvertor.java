package com.icebreak.p2p.service.convert;

import com.icebreak.p2p.dal.dataobject.RepayPlanDO;
import com.icebreak.p2p.ws.info.RepayPlanInfo;
import com.icebreak.p2p.ws.service.query.order.RepayPlanQueryOrder;
import org.springframework.beans.BeanUtils;

public class RepayPlanConvertor {
    public static void convert(RepayPlanQueryOrder queryOrder,
                               RepayPlanDO repayPlanDO) {
        BeanUtils.copyProperties(queryOrder, repayPlanDO);

    }

    public static void convert(RepayPlanDO operationJournalDO,
                               RepayPlanInfo repayPlanInfo) {
        BeanUtils.copyProperties(operationJournalDO, repayPlanInfo);

    }
}
