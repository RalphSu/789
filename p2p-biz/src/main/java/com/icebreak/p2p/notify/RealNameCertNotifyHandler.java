package com.icebreak.p2p.notify;

import com.yiji.openapi.sdk.message.BaseNotify;
import com.yiji.openapi.sdk.message.yzz.RealNameCertNotify;
import com.yiji.openapi.sdk.notify.NotifyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("realNameCertNotifyHandler")
public class RealNameCertNotifyHandler implements NotifyHandler {

	private static Logger logger = LoggerFactory.getLogger(RealNameCertNotifyHandler.class);

	@Override
	public void handleNotify(BaseNotify notify) {

		logger.info("通知默认处理[打印]：" + notify);

		RealNameCertNotify nty = (RealNameCertNotify) notify;

		if ("EXECUTE_SUCCESS".equals(nty)) {

		}

	}

}
