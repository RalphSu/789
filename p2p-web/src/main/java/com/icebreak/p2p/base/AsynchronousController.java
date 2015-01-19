package com.icebreak.p2p.base;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icebreak.p2p.activity.ActivityEnum;
import com.icebreak.p2p.dataobject.GiftInfo;
import com.icebreak.p2p.dataobject.GiftUseRecord;
import com.icebreak.p2p.dataobject.RechargeFlow;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.user.result.UserBaseReturnEnum;
import com.icebreak.p2p.user.valueobject.QueryConditions;
import com.icebreak.p2p.util.YrdConstants;
import com.icebreak.p2p.util.YrdConstants.CommonConfig;
import com.icebreak.util.lang.util.StringUtil;


@Controller
@RequestMapping("asynchronous")
public class AsynchronousController extends BaseAutowiredController {
	
	/**
	 * 异步返回提现结果
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("asysGetWithdrawalRsult")
	public String sysGetWithdrawalRsult(HttpServletRequest request) {
		String strReturn = "";
		int status = 0;
		try {
			String payType = request.getParameter("payType");
			String amount = request.getParameter("amount");
			String outBizNo = request.getParameter("outBizNo");
			String success = request.getParameter("success");
			logger.info("提现异步返回结果:payType=" + payType + ",amount=" + amount + ",outBizNo="
						+ outBizNo + ",isSuccess=" + success);
			RechargeFlow rechargeFlow = tradeService.queryByOutBizNo(outBizNo);
			if ("true".equals(success)) {
				status = 1;
				rechargeFlow.setStatus(1);
				logger.info("提现成功");
			} else {
				status = 0;
				rechargeFlow.setStatus(0);
				logger.info("提现失败");
			}
			int flow = tradeService.updateStatus(rechargeFlow);
			
			GiftUseRecord giftUseRecord = iActivityService.getGiftUseRecordByBizNo(outBizNo);
			if (giftUseRecord != null) {
				giftUseRecord.setStatus(status);
				int upate = iActivityService.updateGiftUsedRecord(giftUseRecord);
				if (upate > 0) {
					strReturn = "success";
					if (0 == status) {
						Map<String, Object> conditions = new HashMap<String, Object>();
						conditions.put("giftName", giftUseRecord.getGiftName());
						List<GiftInfo> gifts = iActivityService.queryGiftByConditions(conditions);
						if (gifts != null && gifts.size() > 0) {
							GiftInfo gift = gifts.get(0);
							iActivityService.addGiftCountAmount(giftUseRecord.getUserId(),
								gift.getGiftType(), giftUseRecord.getUseAmount());
						} else {
							logger.error("查询礼品失败");
						}
					}
				}
			} else {
				strReturn = "success";
			}
			if (flow <= 0) {
				strReturn = "";
				logger.error("用户:" + rechargeFlow.getUserId() + "的" + rechargeFlow.getOutBizNo()
								+ "划出更新状态失败");
			}
			
		} catch (Exception e) {
			logger.error("提现异步返回时,参数异常", e);
		}
		return strReturn;
	}
	
	/**
	 * 异步返回充值结果
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("asysGetDeductResult")
	public String asysGetDeductResult(HttpServletRequest request) {
		String strReturn = "";

		try {
            Date notifyTime = new Date();
            try{
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
               notifyTime  = sdf.parse(request.getParameter("notifyTime"));
            }catch (Exception e){
                logger.error("时间格式化错误", e);
                notifyTime = new Date();
            }

			String signType = request.getParameter("signType");
			String sign = request.getParameter("sign");
			String resultCode = request.getParameter("resultCode");
			String resultMessage = request.getParameter("resultMessage");
			String payNo = request.getParameter("payNo");
			String outBizNo = request.getParameter("outBizNo");
			String amount = request.getParameter("amount");
			String success = request.getParameter("success");
			String message = request.getParameter("message");
			logger.info("充值异步返回结果信息:notifyTime=" + notifyTime + ",signType=" + signType + ",sign="
						+ sign + ",resultCode=" + resultCode + ",resultMessage=" + resultMessage
						+ ",payNo=" + payNo + ",outBizNo=" + outBizNo + ",amount=" + amount
						+ ",success=" + success + ",message=" + message);
			RechargeFlow rechargeFlow = tradeService.queryByOutBizNo(outBizNo);
			if (rechargeFlow != null) {
				if ("true".equals(success)) {
					rechargeFlow.setStatus(1);
				} else {
					rechargeFlow.setStatus(0);
				}
				int flow = tradeService.updateStatus(rechargeFlow);
				if (flow > 0) {
					strReturn = "success";
				}
			} else {
				strReturn = "success";
			}
		} catch (Exception e) {
			logger.error("充值异步调用方法参数或网络异常", e);
		}
		return strReturn;
	}
	
	/**
	 * 异步调用转账到卡结果
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("asysGetTransferBankAccountResult")
	public String asysGetTransferBankAccountResult(HttpServletRequest request) {
		String strReturn = "";
		try {
			String sign = request.getParameter("sign");
			String notifyTime = request.getParameter("notifyTime");
			String tradeSimpleInfos = request.getParameter("tradeSimpleInfos");
			logger.info("转账到卡异步调用方法返回参数:sign=" + sign + ",notifyTime=" + notifyTime
						+ ",tradeSimpleInfos" + tradeSimpleInfos);
			strReturn = "success";
		} catch (Exception e) {
			logger.error("转账到卡异步调用方法参数或网络异常", e);
		}
		return strReturn;
	}
	
	/**
	 * 异步返回实名结果
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("asysGetRealNameStatus")
	public String asysGetRealNameStatus(HttpServletRequest request) {
		String strReturn = " ";
		String sendMsg = "";
		try {
			String status = StringUtil.trim(request.getParameter("status"));
			String accountId = StringUtil.trim(request.getParameter("userId"));
			String message = request.getParameter("message");
			String authNo = StringUtil.trim(request.getParameter("authNo"));
			logger.info("实名异步接收到的参数信息,status=" + status + ",accountId=" + accountId + ",authNo="
						+ authNo + ",message=" + message);
			UserBaseInfoDO userBaseInfo = new UserBaseInfoDO();
			QueryConditions queryConditions = new QueryConditions();
			queryConditions.setAccountId(accountId);
			PageParam pageParam = new PageParam();
			Page<UserBaseInfoDO> queryUserBase = userBaseInfoManager.allUserInfo(queryConditions,
				pageParam);//查询用户
			if (queryUserBase.getResult() != null && queryUserBase.getResult().size() > 0) {
				userBaseInfo = queryUserBase.getResult().get(0);
				if ("success".equals(status)) {
					userBaseInfo.setRealNameAuthentication("IS");
					sendMsg = "认证成功";
					if (iActivityService.checkIsUserJoinActivity(userBaseInfo.getUserId(),
						ActivityEnum.OBN)) {
						try {
							iActivityService.updateActivityOBNResult(userBaseInfo.getUserId());
						} catch (Exception e) {
							logger.error("更新活动结果异常---username=" + userBaseInfo.getUserName(), e);
						}
					}
				} else if ("fail".equals(status)) {
					userBaseInfo.setRealNameAuthentication("NO");
					sendMsg = "认证失败,原因：" + message;
				} else {
					sendMsg = "处理中";
				}
				
				UserBaseReturnEnum retrunEnum = userBaseInfoManager
					.updateUserBaseInfo(userBaseInfo);//更新用户实名状态
				
				strReturn = "success";
				
				if ((retrunEnum == UserBaseReturnEnum.EXECUTE_SUCCESS)
					&& ("success".equals(status) || "fail".equals(status))) {
					StringBuilder toMessage = new StringBuilder();
					if (userBaseInfo != null) {
						String content = YrdConstants.MessageNotifyConstants.REAL_NAME_AUTH_NOTIFY;
						content = content.replace("var1", userBaseInfo.getUserName());
						content = content.replace("var2", sendMsg);
						toMessage.append(content);
						String notifyType = CommonConfig.REAL_NAME_NOTIFY_TYPE;
						yrdMessageService.notifyUserByType(userBaseInfo, toMessage.toString(),
							notifyType);
					}
				}
			} else {
				logger.error("查询用户失败");
			}
			
		} catch (Exception e) {
			logger.error("异常获取参数异常", e);
		}
		return strReturn;
	}
	
}
