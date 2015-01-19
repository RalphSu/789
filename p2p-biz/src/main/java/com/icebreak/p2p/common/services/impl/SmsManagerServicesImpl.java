package com.icebreak.p2p.common.services.impl;

import com.icebreak.p2p.base.OpenApiBaseService;
import com.icebreak.p2p.common.enums.SmsCodeEnum;
import com.icebreak.p2p.common.info.SessionMobileSend;
import com.icebreak.p2p.common.result.SmsCodeResult;
import com.icebreak.p2p.common.services.SmsManagerService;
import com.icebreak.p2p.session.Session;
import com.icebreak.p2p.session.SessionLocal;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.util.Constraints;
import com.icebreak.p2p.util.ValidateCode;
import com.icebreak.p2p.ws.enums.SmsBizType;
import com.icebreak.p2p.ws.order.OperationJournalAddOrder;
import com.icebreak.p2p.ws.result.P2PBaseResult;
import com.icebreak.p2p.ws.service.OperationJournalService;
import com.icebreak.p2p.ws.service.ResultEnum;
import com.icebreak.util.env.Env;
import com.icebreak.util.lang.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("smsManagerService")
public class SmsManagerServicesImpl extends OpenApiBaseService implements SmsManagerService {
	@Autowired
    OperationJournalService operationJournalService;
	static final int	VALIDATE_CODE_FAIL_MAX_TIMES	= 5;
	
	@Override
	public SmsCodeResult verifySmsCode(String mobileNumber, SmsBizType bizCode,
										String validateCode, boolean del) {
		SmsCodeResult smsCodeResult = new SmsCodeResult();
		SessionLocal sessionLocal = SessionLocalManager.getSessionLocal();
		SessionMobileSend sessionMobileSend = null;
		if(sessionLocal == null) {
			sessionMobileSend = (SessionMobileSend)Session.getAttribute("session_mobile_send");
		}else{
			sessionMobileSend = sessionLocal.getSessionMobileSend();
		}
		
		logger.info("validateCode=={}", mobileNumber);
		boolean result = false;
		
		if (StringUtil.isNotEmpty(validateCode)) {
			
			if (sessionMobileSend == null || StringUtil.isBlank(sessionMobileSend.getCode())) {
				smsCodeResult.setSmsCodeEnum(SmsCodeEnum.ExpectSms);
				smsCodeResult.setSuccess(false);
				smsCodeResult.setMessage("请先获取校验码");
			} else {
				if (sessionMobileSend.getEqualCount() < VALIDATE_CODE_FAIL_MAX_TIMES) {
					result = StringUtil.equalsIgnoreCase(validateCode, sessionMobileSend.getCode());
					if (result) {
						sessionLocal.setSessionMobileSend(null);
						smsCodeResult.setSuccess(true);
						smsCodeResult.setSmsCodeEnum(SmsCodeEnum.Success);
						if (del) {
							SessionLocalManager.setSessionLocal(sessionLocal);
							logger.info("删除验证码validateCode={}", validateCode);
						}
					} else {
						smsCodeResult.setResultEnum(ResultEnum.EXECUTE_FAILURE);
						smsCodeResult.setSuccess(false);
						smsCodeResult.setMessage("校验码错误");
					}
					
					if (!result) {
						sessionMobileSend.setEqualCount(sessionMobileSend.getEqualCount() + 1);
						
					}
				} else {
					smsCodeResult.setResultEnum(ResultEnum.EXECUTE_FAILURE);
					smsCodeResult.setSuccess(false);
					smsCodeResult.setMessage("连续错误" + VALIDATE_CODE_FAIL_MAX_TIMES + "次，请重新获取验证码");
				}
			}
			
		} else {
			smsCodeResult.setResultEnum(ResultEnum.INCOMPLETE_REQ_PARAM);
			smsCodeResult.setSuccess(false);
			smsCodeResult.setMessage("校验码不能为空");
		}
		return smsCodeResult;
		
	}
	
	/**
	 * @param mobileNumber
	 * @param bizType
	 * @return
	 * @see com.icebreak.p2p.common.services.SmsManagerService#sendSmsCode(java.lang.String, com.icebreak.p2p.ws.enums.SmsBizType)
	 */
	@Override
	public P2PBaseResult sendSmsCode(String mobileNumber, SmsBizType bizType) {
		P2PBaseResult baseResult = new P2PBaseResult();

		//验证短信发送
		validateSmsCode(baseResult, mobileNumber, bizType);

		if(!baseResult.isSuccess()){
			return baseResult;
		}

		String code = ValidateCode.getCode(6, 0);
		if(Env.isDev()){
			code="8888";
		}

		SessionLocal sessionLocal = SessionLocalManager.getSessionLocal();
		SessionMobileSend preSessionMobileSend = null;
		if(sessionLocal != null) {
			preSessionMobileSend = sessionLocal.getSessionMobileSend();
		}

		if (preSessionMobileSend != null) {
			if (StringUtil.isNotEmpty(preSessionMobileSend.getCode())
					&& StringUtil.equals(mobileNumber, preSessionMobileSend.getMoblie())
					&& bizType == preSessionMobileSend.getSmsBizType()) {
				code = preSessionMobileSend.getCode();
			}
		}
		logger.info("sendCode=={}", code);
		SessionMobileSend sessionMobileSend = new SessionMobileSend();
		sessionMobileSend.setCode(code);
		sessionMobileSend.setLastSendDate(new Date());
		sessionMobileSend.setMoblie(mobileNumber);
		sessionMobileSend.setSmsBizType(bizType);
		if(sessionLocal != null) {
			sessionLocal.setSessionMobileSend(sessionMobileSend);
			SessionLocalManager.setSessionLocal(sessionLocal);
		}else{
			Session.setAttribute("session_mobile_send", sessionMobileSend);
		}

		baseResult = smsService.sendValidateCode(bizType, code, mobileNumber,
			this.getOpenApiContext());
        //添加日志
        OperationJournalAddOrder operationJournalAddOrder = new OperationJournalAddOrder();
        if (sessionLocal == null) {
            operationJournalAddOrder.setOperatorId(-1);
            operationJournalAddOrder.setOperatorName("系统自动");
            operationJournalAddOrder.setOperatorIp("127.0.0.1");
        } else {
            operationJournalAddOrder.setOperatorId(sessionLocal.getUserId());
            operationJournalAddOrder.setOperatorName(sessionLocal.getRealName());
            operationJournalAddOrder.setOperatorIp(sessionLocal.getRemoteAddr());
        }
        operationJournalAddOrder.setBaseModuleName(bizType.getMessage());
        operationJournalAddOrder.setPermissionName(bizType.getMessage());
        operationJournalAddOrder.setOperationContent("发送短信");
        operationJournalAddOrder.setMemo("手机号码：" + mobileNumber + " 验证码：" + code);
        operationJournalService.addOperationJournalInfo(operationJournalAddOrder);

		return baseResult;
		
	}
	
	public P2PBaseResult validateSmsCode(P2PBaseResult baseResult, String mobileNumber,
											SmsBizType bizType) {
		try {
			if (bizType.getDaySendCount() > 10) {
				Constraints.DATA_CON.check(mobileNumber + bizType.code());

			} else {
				Constraints.SMS_OR_MAIL_CON.check(mobileNumber + bizType.code());

			}
			Constraints.DATA_CON_MAX.check(mobileNumber);
			baseResult.setSuccess(true);
		} catch (RuntimeException e) {
			logger.error("Constraints check", e);
			if ("超过间隔限制".equals(e.getMessage())) {
				baseResult.setMessage("不能频繁发送，请30秒后再试");
			} else {
				baseResult.setMessage(e.getMessage());
			}
			return baseResult;
		}
		return baseResult;
	}


}
