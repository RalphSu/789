package com.icebreak.p2p.base;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.icebreak.p2p.util.Constants;
import org.springframework.ui.Model;

import com.icebreak.p2p.dataobject.InstitutionsInfoDO;
import com.icebreak.p2p.dataobject.PersonalInfoDO;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.integration.openapi.info.ThirdPartAccountInfo;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.util.CommonUtil;
import com.icebreak.p2p.util.YrdConstants;
import com.icebreak.p2p.ws.base.info.BankBasicInfo;
import com.icebreak.util.lang.util.StringUtil;
import com.yiji.openapi.sdk.message.common.YzzUserAccountQueryResponse;

public class UserAccountInfoBaseController extends BaseAutowiredController {
	
	
	protected void initAccountInfo(Model model, boolean isInitUserBase) {
		if (isInitUserBase){
			if(null != SessionLocalManager.getSessionLocal() && null != SessionLocalManager.getSessionLocal().getUserBaseId() && "" != SessionLocalManager.getSessionLocal().getUserBaseId()){
				//初始化用户基本信息
				this.getUserBaseInfo(null, model);
			}
		}
		//设置用户第三方支付账户信息
		if (SessionLocalManager.getSessionLocal() != null
			&& StringUtil.isNotEmpty(SessionLocalManager.getSessionLocal().getAccountId())) {
			String accoountId = SessionLocalManager.getSessionLocal().getAccountId();
			
			YzzUserAccountQueryResponse account = apiTool.queryUserAccount(accoountId);
			logger.info("【查询易记付账户】" + account);
			model.addAttribute("thirdPartAccountInfo", convert(account));
		}
		
	}
	
	protected void initAccountInfo(Model model) {
		initAccountInfo(model, true);
	}
	
	protected ThirdPartAccountInfo getAccountInfo(Model model) {
		ThirdPartAccountInfo accountInfo = (ThirdPartAccountInfo) model.asMap().get("thirdPartAccountInfo");
		if (accountInfo == null) {
			initAccountInfo(model);
			accountInfo = (ThirdPartAccountInfo) model.asMap().get("thirdPartAccountInfo");
		}
		return accountInfo;
	}
	
	/**
	 * @param session
	 * @param model
	 * @param userBaseInfo
	 * @return
	 */
	protected UserBaseInfoDO getUserBaseInfo(HttpSession session, Model model) {
		UserBaseInfoDO userBaseInfo = null;
		try {
			userBaseInfo = userBaseInfoManager.queryByUserBaseId(SessionLocalManager
				.getSessionLocal().getUserBaseId());
			if ("JG".equals(userBaseInfo.getType())) {
				if (session != null)
					session.setAttribute("type", "JG");
				String filteredGuarantees = YrdConstants.GuaranteeAuthFilterCanstants.FILTEREDGUARANTEES;
				if (filteredGuarantees.indexOf(userBaseInfo.getRealName()) >= 0) {
					if (session != null)
						session.setAttribute("DBSH", "YES");
				}
			} else {
				if (session != null)
					session.setAttribute("type", "GR");
			}
		} catch (Exception e) {
			logger.error("未找到当前登录的用户", e);
		}
		model.addAttribute("userBaseInfo", userBaseInfo);
		try {
			this.queryUserInfo(model);
		} catch (Exception e) {
			logger.error("查询银行信息异常", e);
		}
		
		if (CommonUtil.checkEmail(userBaseInfo.getMail())) {
			model.addAttribute("mailBinding", "IS");
		} else {
			model.addAttribute("mailBinding", "NO");
		}
		return userBaseInfo;
	}
	
	protected void queryUserInfo(Model model) throws Exception {
		String msg = "暂无";
		
		UserBaseInfoDO userBaseInfoDO = (UserBaseInfoDO) model.asMap().get("userBaseInfo");
		if (userBaseInfoDO.getRealNameAuthentication() == null) {
			model.addAttribute("realNameStatus", "N");
		} else {
			model.addAttribute("realNameStatus", userBaseInfoDO.getRealNameAuthentication());
		}
		String bankCardNo = null;
		String bankName = null;
		String banklog = null;
		if ("GR".equals(userBaseInfoDO.getType())) {
			PersonalInfoDO personalInfo = personalInfoManager.query(userBaseInfoDO.getUserBaseId());
			bankCardNo = personalInfo.getBankCardNo();
			bankName = personalInfo.getBankType();
			model.addAttribute("info", personalInfo);
			String mobile = personalInfo.getMobile();
			if (mobile != null && !"".equals(mobile)) {
				model.addAttribute(
					"mobile",
					mobile.substring(0, 3) + "*****"
							+ mobile.subSequence(mobile.length() - 3, mobile.length()));
			} else {
				model.addAttribute("mobile", msg);
			}
			String mail = personalInfo.getMail();
			if (mail != null && !"".equals(mail)) {
				String[] strMail = mail.split("@");
				model.addAttribute("mail", strMail[0].substring(0, 2) + "*******" + "@"
											+ strMail[1]);
			} else {
				model.addAttribute("mail", msg);
			}
			String certNo = personalInfo.getCertNo();
			if (certNo != null && !"".equals(certNo)) {
				model.addAttribute(
					"certNo",
					certNo.substring(0, 2) + "**************"
							+ certNo.subSequence(certNo.length() - 2, certNo.length()));
			} else {
				model.addAttribute("certNo", msg);
			}
		}
		if ("JG".equals(userBaseInfoDO.getType())) {
			InstitutionsInfoDO institutionsInfo = institutionsInfoManager.query(userBaseInfoDO
				.getUserBaseId());
			bankCardNo = institutionsInfo.getBankCardNo();
			bankName = institutionsInfo.getBankType();
			model.addAttribute("info", institutionsInfo);
			String contactCertNo = institutionsInfo.getContactCertNo();//联系人身份证号
			if (contactCertNo != null && !"".equals(contactCertNo)) {
				model.addAttribute(
					"contactCertNo",
					contactCertNo.substring(0, 2)
							+ "*************"
							+ contactCertNo.subSequence(contactCertNo.length() - 2,
								contactCertNo.length()));
			} else {
				model.addAttribute("contactCertNo", msg);
			}
			String legalCrdNo = institutionsInfo.getLegalRepresentativeCardNo();
			if (legalCrdNo != null && !"".equals(legalCrdNo)) {
				model.addAttribute(
					"legalCrdNo",
					legalCrdNo.substring(0, 2)
							+ "**************"
							+ contactCertNo.subSequence(contactCertNo.length() - 2,
								contactCertNo.length()));
			} else {
				model.addAttribute("legalCrdNo", msg);
			}
			String mobile = institutionsInfo.getMobile();
			if (mobile != null && !"".equals(mobile)) {
				model.addAttribute(
					"mobile",
					mobile.substring(0, 2) + "*******"
							+ mobile.subSequence(mobile.length() - 2, mobile.length()));
			} else {
				model.addAttribute("mobile", msg);
			}
			
			String mail = institutionsInfo.getMail();
			if (mail != null && !"".equals(mail)) {
				String[] strMail = mail.split("@");
				model.addAttribute("mail", strMail[0].substring(0, 2) + "*******" + "@"
											+ strMail[1]);
			} else {
				model.addAttribute("mail", msg);
			}
			
		}
		
		if (StringUtil.isNotBlank(bankCardNo) && StringUtil.isNotBlank(bankName)) {
			model.addAttribute("bankCardNo",
				bankCardNo.substring(bankCardNo.length() - 4, bankCardNo.length()));
			List<BankBasicInfo> bankList = this.bankDataService.getBankBasicInfos();
			for (BankBasicInfo bankBasicInfo : bankList) {
				if (bankName.equals(bankBasicInfo.getBankCode())) {
					bankName = bankBasicInfo.getBankName();
					banklog = bankBasicInfo.getLogoUrl();
				}
			}
			model.addAttribute("bankName", bankName);
			model.addAttribute("banklog", banklog);
		}
		model.addAttribute("type", userBaseInfoDO.getType());
	}


	protected void setCss(String topMenu, String subMenu, Model model) {
		model.addAttribute(topMenu, Constants.selectedCSS);
		model.addAttribute(subMenu, Constants.selectedCSS);
	}

}
