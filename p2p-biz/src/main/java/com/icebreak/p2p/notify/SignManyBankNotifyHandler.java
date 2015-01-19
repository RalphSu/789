package com.icebreak.p2p.notify;

import com.icebreak.p2p.dataobject.SignCardInfo;
import com.icebreak.p2p.sign.SignCardInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.yiji.openapi.sdk.message.BaseNotify;
import com.yiji.openapi.sdk.message.yzz.SignManyBankNotify;
import com.yiji.openapi.sdk.notify.NotifyHandler;

import javax.annotation.Resource;

@Service("signManyBankNotifyHandler")
public class SignManyBankNotifyHandler implements NotifyHandler {

	private static Logger logger = LoggerFactory.getLogger(SignManyBankNotifyHandler.class);

	@Resource
	private SignCardInfoService signCardInfoService;

	@Override
	public void handleNotify(BaseNotify notify) {

		logger.info("通知默认处理[打印]：" + notify);

		SignManyBankNotify nty = (SignManyBankNotify) notify;
		SignCardInfo signCard = new SignCardInfo();
		BeanUtils.copyProperties(nty, signCard);
		signCard.setSignType("SIGN");
		signCard.setStatus("PACT");
		try {
			signCardInfoService.add(signCard);
		} catch (Exception e) {
			logger.error("", e);
		}
	}

}
