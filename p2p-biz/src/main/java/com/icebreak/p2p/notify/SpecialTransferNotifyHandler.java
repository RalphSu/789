package com.icebreak.p2p.notify;

import com.icebreak.p2p.daointerface.DivisionDetailDao;
import com.yiji.openapi.sdk.message.BaseNotify;
import com.yiji.openapi.sdk.message.yzz.SpecialTransferNotify;
import com.yiji.openapi.sdk.notify.NotifyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("specialTransferNotifyHandler")
public class SpecialTransferNotifyHandler implements NotifyHandler {

	private static Logger logger = LoggerFactory
			.getLogger(SpecialTransferNotifyHandler.class);

	@Resource
	private DivisionDetailDao divisionDetailDao;

	@Override
	public void handleNotify(BaseNotify notify) {
		logger.info("通知默认处理[打印]：" + notify);

		SpecialTransferNotify nty = (SpecialTransferNotify) notify;

		if ("EXECUTE_SUCCESS".equals(nty)) {

		}

	}

}
