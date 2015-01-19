package com.icebreak.p2p.service.convert;

import org.springframework.beans.BeanUtils;

import com.icebreak.p2p.dal.dataobject.OperationJournalDO;
import com.icebreak.p2p.ws.info.OperationJournalInfo;
import com.icebreak.p2p.ws.order.OperationJournalAddOrder;
import com.icebreak.p2p.ws.service.query.order.OperationJournalQueryOrder;

public class OperationJournalConvertor {
	
	/**
	 * 将operationJournalAddOrder数据转换为operationJournalDO数据
	 * @param operationJournalAddOrder
	 * @param operationJournalDO
	 */
	public static void convert(OperationJournalAddOrder operationJournalAddOrder,
								OperationJournalDO operationJournalDO) {
		BeanUtils.copyProperties(operationJournalAddOrder, operationJournalDO,
			new String[] { "operatorId", "baseModuleName" });
		if (operationJournalAddOrder.getOperatorId() > 0) {
			operationJournalDO.setOperatorId(operationJournalAddOrder.getOperatorId());
		}
		if (operationJournalAddOrder.getBaseModuleName() != null) {
			operationJournalDO.setBaseModuleName(operationJournalAddOrder.getBaseModuleName());
		}
	}
	
	/**
	 * 将operationJournalQueryOrder数据转换为operationJournalDO数据
	 * @param operationJournalQueryOrder
	 * @param operationJournalDO
	 */
	public static void convert(OperationJournalQueryOrder operationJournalQueryOrder,
								OperationJournalDO operationJournalDO) {
		BeanUtils.copyProperties(operationJournalQueryOrder, operationJournalDO,
			new String[] { "operatorId", "baseModuleName" });
		if (operationJournalQueryOrder.getOperatorId() > 0) {
			operationJournalDO.setOperatorId(operationJournalQueryOrder.getOperatorId());
		}
		if (operationJournalQueryOrder.getBaseModuleName() != null) {
			operationJournalDO.setBaseModuleName(operationJournalQueryOrder.getBaseModuleName());
		}
	}
	
	/**
	 * 将operationJournalDO数据转换为operationJournalInfo数据
	 * @param operationJournalDO
	 * @param operationJournalInfo
	 */
	public static void convert(OperationJournalDO operationJournalDO,
								OperationJournalInfo operationJournalInfo) {
		BeanUtils.copyProperties(operationJournalDO, operationJournalInfo,
			new String[] { "identity", "operatorId", "baseModuleName" });
		operationJournalInfo.setIdentity(operationJournalDO.getIdentity());
		operationJournalInfo.setOperatorId(operationJournalDO.getOperatorId());
		operationJournalInfo.setOperatorTime(operationJournalDO.getRawAddTime());
		if (operationJournalDO.getBaseModuleName() != null) {
			operationJournalInfo.setBaseModuleName(operationJournalDO.getBaseModuleName());
		}
	}
}
