package com.icebreak.p2p.notify;

import com.icebreak.p2p.daointerface.DivisionDetailDao;
import com.yiji.openapi.sdk.message.BaseNotify;
import com.yiji.openapi.sdk.message.yzz.FastpayNotify;
import com.yiji.openapi.sdk.notify.NotifyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("fastpayNotifyHandler")
public class FastpayNotifyHandler implements NotifyHandler {

	private static Logger logger = LoggerFactory
			.getLogger(FastpayNotifyHandler.class);

	@Resource
	private DivisionDetailDao divisionDetailDao;

	@Override
	public void handleNotify(BaseNotify notify) {
		logger.info("通知默认处理[打印]：" + notify);

		FastpayNotify nty = (FastpayNotify) notify;

		if ("EXECUTE_SUCCESS".equals(nty)) {

		}

	}

}
