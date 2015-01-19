package com.icebreak.p2p.rs.service.accountManage;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.dataobject.InstitutionsInfoDO;
import com.icebreak.p2p.dataobject.PersonalInfoDO;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.rs.base.ServiceBase;
import com.icebreak.p2p.rs.base.enums.APIServiceEnum;
import com.icebreak.p2p.rs.service.base.AppService;
import com.icebreak.p2p.service.openingbank.enums.CardTypeEnum;
import com.icebreak.p2p.service.openingbank.order.OpeningBankQueryOrder;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.user.result.InstitutionsReturnEnum;
import com.icebreak.p2p.user.result.PersonalReturnEnum;
import com.icebreak.p2p.util.YrdConstants;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class AppAddBankCardService extends ServiceBase implements AppService {
	
	@Override
	public JSONObject execute(Map<String, Object> params, HttpServletRequest request) {
		JSONObject json = new JSONObject();
		
		if (SessionLocalManager.getSessionLocal() == null) {
			json.put("code", 0);
			json.put("message", "未登录");
			return json;
		}
		String userBaseId = SessionLocalManager.getSessionLocal().getUserBaseId();
		try {
			String bankOpenName = params.get("bankOpenName").toString();
			String bankCardNo = params.get("bankCardNo").toString();
			String bankCode = params.get("bankCode").toString();
			String bankProvince = params.get("bankProvince").toString();
			String bankCity = params.get("bankCity").toString();
			logger.info("银行卡验证信息参数,accountName=" + bankOpenName + ",accountNo=" + bankCardNo
						+ ",bankCode=" + bankCode + ",bankProvince=" + bankProvince + ",bankCity="
						+ bankCity);
			//String userBaseId = SessionLocalManager.getSessionLocal().getUserBaseId();
			UserBaseInfoDO userBaseInfo = userBaseInfoManager.queryByUserBaseId(userBaseId);
			PersonalInfoDO personalInfo = null;
			InstitutionsInfoDO institutionsInfo = null;
			if ("GR".equals(userBaseInfo.getType())) {
				personalInfo = personalInfoManager.query(userBaseId);
			} else {
				institutionsInfo = institutionsInfoManager.query(userBaseId);
			}
			OpeningBankQueryOrder validationCardInfo = new OpeningBankQueryOrder();
			//validationCardInfo.setExtendId(BusinessNumberUtil.gainNumber());
			validationCardInfo.setAccountName(bankOpenName);
			validationCardInfo.setCardNumber(bankCardNo);
			validationCardInfo.setBankCode(bankCode);
			validationCardInfo.setCardType(CardTypeEnum.JJ);
			String cardNo = personalInfo.getCertNo();//二代身份证
			String cardOne = cardNo.substring(0, 6) + cardNo.substring(8, 17);//一代身份证
			if ("GR".equals(userBaseInfo.getType())) {
				validationCardInfo.setCertNo(cardNo);
				//调用openAPI 验证银行信息
				JSONObject jsonobj = userBaseInfoManager
					.validationBankCardByOpenApi(validationCardInfo);
				if (YrdConstants.BankCodes.filteredBankCodes.indexOf(bankCode) < 0) {
					if (0 == (Integer) jsonobj.get("code")) {
						validationCardInfo.setCertNo(cardOne);
						jsonobj = userBaseInfoManager
							.validationBankCardByOpenApi(validationCardInfo);
						if (0 == (Integer) jsonobj.get("code")) {
							json.put("code", 0);
							json.put("message", jsonobj.get("message"));
							return json;
						}
					}
				}
				
			} else {
				validationCardInfo.setCertNo(institutionsInfo.getLegalRepresentativeCardNo());
			}
			boolean bool = false;
			if ("GR".equals(userBaseInfo.getType())) {
				personalInfo.setBankOpenName(bankOpenName);
				personalInfo.setBankCardNo(bankCardNo);
				personalInfo.setBankType(bankCode);
				personalInfo.setBankProvince(bankProvince);
				personalInfo.setBankCity(bankCity);
				bool = updatePersonal(personalInfo, userBaseInfo);
				if (bool) {
					json.put("code", 1);
					json.put("message", "绑定银行卡成功");
				} else {
					json.put("code", 0);
					json.put("message", "绑定银行卡保存失败");
				}
			}
			
			if ("JG".equals(userBaseInfo.getType())) {
				institutionsInfo.setBankOpenName(bankOpenName);
				institutionsInfo.setBankCardNo(bankCardNo);
				institutionsInfo.setBankType(bankCode);
				institutionsInfo.setBankProvince(bankProvince);
				institutionsInfo.setBankCity(bankCity);
				bool = updateInstitutions(institutionsInfo, userBaseInfo);
				if (bool) {
					json.put("code", 1);
					json.put("message", "绑定银行卡成功");
				} else {
					json.put("code", 0);
					json.put("message", "绑定银行卡保存失败");
				}
			}
		} catch (Exception e) {
			json.put("code", 0);
			json.put("message", "网络或参数异常,绑定银行卡失败");
		}
		
		return json;
	}
	
	@Override
	public APIServiceEnum getServiceType() {

		return APIServiceEnum.addBankCardService;
	}
	
	private boolean updatePersonal(PersonalInfoDO personalInfo, UserBaseInfoDO userBaseInfo)
																							throws Exception {
		PersonalReturnEnum personalReturnEnum = personalInfoManager.updatePersonalInfo(
			personalInfo, userBaseInfo, false, new int[] {});
		if (personalReturnEnum == PersonalReturnEnum.EXECUTE_SUCCESS) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean updateInstitutions(InstitutionsInfoDO institutionsInfo,
										UserBaseInfoDO userBaseInfo) throws Exception {
		InstitutionsReturnEnum institutionsReturnEnum = institutionsInfoManager
			.updateInstitutionsInfo(institutionsInfo, userBaseInfo, false, new int[] {});
		if (institutionsReturnEnum == InstitutionsReturnEnum.EXECUTE_SUCCESS) {
			return true;
		} else {
			return false;
		}
	}
	
}
