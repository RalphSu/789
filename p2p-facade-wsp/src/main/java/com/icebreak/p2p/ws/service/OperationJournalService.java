package com.icebreak.p2p.ws.service;

import com.icebreak.p2p.ws.info.OperationJournalInfo;
import com.icebreak.p2p.ws.order.OperationJournalAddOrder;
import com.icebreak.p2p.ws.result.OperationJournalServiceResult;
import com.icebreak.p2p.ws.service.query.order.OperationJournalQueryOrder;
import com.icebreak.p2p.ws.service.query.result.QueryBaseBatchResult;

import javax.jws.WebService;

@WebService
public interface OperationJournalService {
	
	/**
	 * 添加一条操作日志
	 * @param operationJournalAddOrder
	 * @return
	 */
	public OperationJournalServiceResult addOperationJournalInfo(OperationJournalAddOrder operationJournalAddOrder);


    public QueryBaseBatchResult<OperationJournalInfo> queryOperationJournalInfo(OperationJournalQueryOrder operationJournalQueryOrder) ;
	
}
