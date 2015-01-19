package com.icebreak.p2p.ws.service;

import java.util.Date;

import com.icebreak.p2p.ws.info.RepayPlanInfo;
import com.icebreak.p2p.ws.service.query.order.RepayPlanQueryOrder;
import com.icebreak.p2p.ws.service.query.result.QueryBaseBatchResult;

import javax.jws.WebService;

@WebService
public interface RepayPlanService {
	
    public QueryBaseBatchResult<RepayPlanInfo> queryRepayPlanInfo(RepayPlanQueryOrder repayPlanQueryOrder) ;

    public QueryBaseBatchResult<RepayPlanInfo> queryRepayPlanInfoGuarantee(RepayPlanQueryOrder repayPlanQueryOrder) ;

    public RepayPlanInfo findByRepayPlanId(long repayPlanId);
    
    public RepayPlanInfo findByRepayPlanIdwithrowLock(long repayPlanId);
    
    public void updateStatus(long repayPlanId,String status);
    
    public void updateActualRepayDate(long repayPlanId,Date actualRepayDate);
	
}
