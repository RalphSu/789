package com.icebreak.p2p.front.controller.account;

import com.icebreak.p2p.base.BaseAutowiredController;
import com.icebreak.p2p.dataobject.DOEnum.ActivityTypeEnum;
import com.icebreak.p2p.dataobject.*;
import com.icebreak.p2p.gold.GoldExperienceService;
import com.icebreak.p2p.gold.UserGoldExperienceService;
import com.icebreak.p2p.integration.openapi.result.CustomerResult;
import com.icebreak.p2p.service.openingbank.enums.CardTypeEnum;
import com.icebreak.p2p.service.openingbank.order.OpeningBankQueryOrder;
import com.icebreak.p2p.service.openingbank.result.BankInfoResult;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.user.result.InstitutionsReturnEnum;
import com.icebreak.p2p.user.result.PersonalReturnEnum;
import com.icebreak.p2p.util.SysCommand;
import com.icebreak.p2p.ws.base.info.BankBasicInfo;
import com.icebreak.util.lang.exception.ResultException;
import com.icebreak.util.lang.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("anon")
public class ActivateController extends BaseAutowiredController {
	private final String	vm_path	= "/front/anon/activate/";

	@Resource
	private GoldExperienceService goldExperienceService;

	@Resource
	private UserGoldExperienceService userGoldExperienceService;

	@RequestMapping("activate/{MD5UserBaseId}")
	public String activationUser(HttpSession session, @PathVariable String MD5UserBaseId,
									Model model) throws Exception {
		UserBaseInfoDO userBaseInfo = userBaseInfoManager.queryByMD5UserBaseId(MD5UserBaseId);
		model.addAttribute("userName", userBaseInfo.getUserName());
		model.addAttribute("userBaseId", userBaseInfo.getUserBaseId());
		String token = UUID.randomUUID().toString();
		session.setAttribute("token", token);
		if (userBaseInfo.getState() == null || "inactive".equals(userBaseInfo.getState())) {
			//激活账户
			userBaseInfo.setState("normal");
			userBaseInfoManager.updateUserBaseInfo(userBaseInfo);

			return vm_path + "regActiveSuccess.vm";
		} else {
			if(null == userBaseInfo.getAccountId() || "".equals(userBaseInfo.getAccountId())) {
				return vm_path + "regYJFAccount.vm";
			}
		}
		model.addAttribute("activated", "true");
		return vm_path + "activateSuccess.vm";
	}
	
	@RequestMapping("toRegYJFAccount/{userName}")
	public String toRegYJFAccount(HttpServletRequest request, @PathVariable String userName,
									Model model) throws Exception {
		if(null == userName){
			userName = SessionLocalManager.getSessionLocal().getUserName();
		}
		model.addAttribute("userName", userName);
		return vm_path + "regYJFAccount.vm";
	}
	
	@RequestMapping("regYJFAccount")
	public String regYJFAccount(HttpServletRequest request, Model model) throws Exception {
		String userName = request.getParameter("userName");
		String realName = request.getParameter("realName");
		String certNo = request.getParameter("certNo");
		String businessPeriod = request.getParameter("businessPeriod");
		UserBaseInfoDO userBaseInfo = userBaseInfoManager.queryByUserName(userName, 12L);
		model.addAttribute("userName", userBaseInfo.getUserName());
		model.addAttribute("userBaseId", userBaseInfo.getUserBaseId());
		model.addAttribute("userId", userBaseInfo.getUserId());
		userBaseInfo.setRealName(realName);
		
		PersonalInfoDO person = personalInfoManager.query(userBaseInfo.getUserBaseId());
		person.setCertNo(certNo);
		person.setBusinessPeriod(businessPeriod);
		person.setRealName(realName);
		try {
			registYJFAccountAndUpdateLocal(person,userBaseInfo,model);
			//查询注册发放体验金是否上架
			model.addAttribute("goldExp", queryGoldExp(ActivityTypeEnum.REGISTER_GOLD));
		} catch (ResultException e) {
			e.printStackTrace();
			logger.info("注册易记付账户失败" + e.getMessage());
			model.addAttribute("errorMsg", e.getMessage());
			return vm_path + "reYJFAccountFail.vm";
		}
		return vm_path + "activateSuccess.vm";
	}

	@ResponseBody
	@RequestMapping("getGoldExp")
	public Object getGoldExp(HttpServletRequest request, HttpServletResponse response, UserGoldExperienceDO userGoldExp) {
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			GoldExperienceDO goldExp = queryGoldExp(ActivityTypeEnum.REGISTER_GOLD);
			//判断体验金数量，如果发放数量大于0，则判断剩余数量是否大于0
			if (goldExp.getQuantity() > 0 && goldExp.getSurplusQuantity() == 0) {
				msg.put("msg", "对不起，体验金是被领完，请关注下一次平台相关活动");
				return msg;
			}
			//用户添加体验金
			userGoldExp.setAmount(goldExp.getAmount());
			userGoldExp.setStatus("1");
			userGoldExp.setGoldExpId(goldExp.getId());
			userGoldExperienceService.addUserGoldExp(userGoldExp);
			msg.put("msg", "恭喜你，你已获得" + goldExp.getAmount() + "元的体验金！");
		} catch (Exception e) {
			e.printStackTrace();
			msg.put("msg", "对不起，体验金领取失败");
		}
		return msg;
	}

	/**
	 *
	 * @param activityTypeEnum
	 * @return
	 */
	private GoldExperienceDO queryGoldExp(ActivityTypeEnum activityTypeEnum) {
		GoldExperienceDO goldExp = new GoldExperienceDO();
		goldExp.setActivityType(activityTypeEnum);
		goldExp.setStatus(0);//已开启的体验金活动
		goldExp = goldExperienceService.query(goldExp);
		return goldExp;
	}

	@RequestMapping("forwardyjf/{accountId}")
	public void forwardYJFSit(HttpServletResponse response, @PathVariable String accountId,
								  Model model) throws Exception {
		CustomerResult result = customerService.gotoYjfSit(accountId);
		response.sendRedirect(result.getUrl());
	}

	@RequestMapping("realNameAuth")
	public String realNameAuth(Model model, HttpSession session) {
		if (SessionLocalManager.getSessionLocal() == null) {
			return "/help/nopermission";
		}
		UserBaseInfoDO userBaseInfo = null;
		try {
			this.queryUserInfo(model);
			userBaseInfo = userBaseInfoManager.queryByUserBaseId(SessionLocalManager
				.getSessionLocal().getUserBaseId());
		} catch (Exception e) {
			logger.error("查询银行信息异常", e);
		}
		session.setAttribute("token", UUID.randomUUID().toString());
		if (SysCommand.GR.equals(userBaseInfo.getType()))
			return vm_path + "realNameAuthentication.vm";
		else
			return vm_path + "enterpriserealNameAuthentication.vm";
	}
	
	@RequestMapping("activeYjfAccount")
	public String activeYjfAccount(Model model, HttpSession session, HttpServletResponse response) {
		if (SessionLocalManager.getSessionLocal() == null) {
			return "/help/nopermission";
		}
		UserBaseInfoDO userBaseInfo = null;
		try {
			userBaseInfo = userBaseInfoManager.queryByUserBaseId(SessionLocalManager
				.getSessionLocal().getUserBaseId());
			CustomerResult customerResult = null;
			//TODO
//			CustomerResult customerResult = this.customerService.applyAccountActivate(
//				userBaseInfo.getAccountId(), this.getOpenApiContext());
			response.sendRedirect(customerResult.getUrl());
		} catch (Exception e) {
			logger.error("查询银行信息异常", e);
		}
		return null;
	}
	
	@RequestMapping("goto3Acount")
	public String goto3Acount(Model model, HttpSession session, HttpServletResponse response) {
		if (SessionLocalManager.getSessionLocal() == null) {
			return "/help/nopermission";
		}
		UserBaseInfoDO userBaseInfo = null;
		try {
			userBaseInfo = userBaseInfoManager.queryByUserBaseId(SessionLocalManager
				.getSessionLocal().getUserBaseId());
			CustomerResult customerResult = this.customerService.gotoYjfSit(
				userBaseInfo.getAccountId());
			response.sendRedirect(customerResult.getUrl());
		} catch (Exception e) {
			logger.error("查询银行信息异常", e);
		}
		return null;
	}
	
	@RequestMapping("allFlowSuccess")
	public String allFlowSuccess(HttpSession session, Model model) throws Exception {
		if (SessionLocalManager.getSessionLocal() == null) {
			return "/help/nopermission";
		}
		model.addAttribute("allFlowSuccess", "true");
		String userBaseId = SessionLocalManager.getSessionLocal().getUserBaseId();
		UserBaseInfoDO userBaseInfo = userBaseInfoManager.queryByUserBaseId(userBaseId);
		model.addAttribute("userName", userBaseInfo.getUserName());
		return vm_path + "activateSuccess.vm";
	}
	
	private void queryUserInfo(Model model) throws Exception {
		String userBaseId = SessionLocalManager.getSessionLocal().getUserBaseId();
		UserBaseInfoDO userBaseInfoDO = userBaseInfoManager.queryByUserBaseId(userBaseId);
		if (userBaseInfoDO.getRealNameAuthentication() == null) {
			model.addAttribute("realNameStatus", "N");
		} else {
			model.addAttribute("realNameStatus", userBaseInfoDO.getRealNameAuthentication());
		}
		String bankCardNo = null;
		String bankName = null;
		String banklog = null;
		if ("GR".equals(userBaseInfoDO.getType())) {
			PersonalInfoDO personalInfo = personalInfoManager.query(userBaseId);
			bankCardNo = personalInfo.getBankCardNo();
			bankName = personalInfo.getBankType();
			model.addAttribute("info", personalInfo);
		}
		if ("JG".equals(userBaseInfoDO.getType())) {
			InstitutionsInfoDO institutionsInfo = institutionsInfoManager.query(userBaseId);
			bankCardNo = institutionsInfo.getBankCardNo();
			bankName = institutionsInfo.getBankType();
			model.addAttribute("info", institutionsInfo);
		}
		
		if (StringUtil.isNotBlank(bankCardNo) && StringUtil.isNotBlank(bankName)) {
			model.addAttribute("bankCardNo",
				bankCardNo.substring(bankCardNo.length() - 4, bankCardNo.length()));
			List<BankBasicInfo> bankList = bankDataService.getBankBasicInfos();
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
	
	@RequestMapping("signBankCard")
	public String signBankCard(Model model) throws Exception {
		if (SessionLocalManager.getSessionLocal() == null) {
			return "/help/nopermission";
		}
		return vm_path + "signBankCard.vm";
	}
	
	@RequestMapping("addBankCard")
	public String addBankCard(String signed, Model model) throws Exception {
		if (SessionLocalManager.getSessionLocal() == null) {
			return "/help/nopermission";
		}
		if (!"yes".equals(signed)) {
			model.addAttribute("fail", "未签约");
			return vm_path + "signBankCard.vm";
		}
		List<BankBasicInfo> bankList = new ArrayList<BankBasicInfo>();
		List<BankBasicInfo> bankListOpenApi = this.bankDataService.getDeductBank();
		if (bankListOpenApi != null && bankListOpenApi.size() > 0) {
			List<String> listBankCode = new ArrayList<String>();
			listBankCode.add("ABC");//农业银行
			listBankCode.add("ICBC");//中国工商银行
			listBankCode.add("CCB");//中国建设银行
			listBankCode.add("CEB");//中国光大银行
			listBankCode.add("CIB");//兴业银行
			listBankCode.add("CMBC");//民生银行
			listBankCode.add("CITIC");//中信银行  
			listBankCode.add("CMB");//招商银行
			for (BankBasicInfo bank : bankListOpenApi) {
				if (listBankCode.contains(bank.getBankCode())) {
					bankList.add(bank);
				}
			}
		} else {
			List<BankBasicInfo> bankListPPM = bankDataService.getBankBasicInfos();//获取所有银行
			List<String> listBankCode = new ArrayList<String>();
			listBankCode.add("ABC");//农业银行
			listBankCode.add("ICBC");//中国工商银行
			listBankCode.add("CCB");//中国建设银行
			listBankCode.add("CEB");//中国光大银行
			listBankCode.add("CIB");//兴业银行
			listBankCode.add("CMBC");//民生银行
			listBankCode.add("CQRCB");//重庆农村商业银行
			listBankCode.add("CITIC");//中信银行
			listBankCode.add("CMB");//招商银行
			for (BankBasicInfo bank : bankListPPM) {
				if (listBankCode.contains(bank.getBankCode())) {
					bankList.add(bank);
				}
			}
		}
		String userBaseId = SessionLocalManager.getSessionLocal().getUserBaseId();
		UserBaseInfoDO userBaseInfo = userBaseInfoManager.queryByUserBaseId(userBaseId);
		PersonalInfoDO personalInfo = null;
		InstitutionsInfoDO institutionsInfo = null;
		if ("GR".equals(userBaseInfo.getType())) {
			try {
				personalInfo = personalInfoManager.query(userBaseId);
			} catch (Exception e) {
				logger.error("查询用户信息失败", e);
			}
			model.addAttribute("realName", personalInfo.getRealName());
			model.addAttribute("bankType", personalInfo.getBankType());
			model.addAttribute("bankCardNo", personalInfo.getBankCardNo());
			model.addAttribute("province", personalInfo.getBankProvince());
			model.addAttribute("city", personalInfo.getBankCity());
			String bankCardNo = personalInfo.getBankCardNo();
			model.addAttribute("type", "GR");
			if (StringUtil.isNotBlank(bankCardNo)) {
				model.addAttribute("bankCardNo", bankCardNo);
			}
			if (StringUtil.isNotBlank(personalInfo.getBankOpenName())) {
				model.addAttribute("oldBankOpenName", personalInfo.getBankOpenName());
			}
		} else {
			try {
				institutionsInfo = institutionsInfoManager.query(userBaseId);
			} catch (Exception e) {
				logger.error("查询机构异常", e);
			}
			model.addAttribute("realName", institutionsInfo.getEnterpriseName());
			model.addAttribute("bankType", institutionsInfo.getBankType());
			model.addAttribute("bankCardNo", institutionsInfo.getBankCardNo());
			model.addAttribute("province", institutionsInfo.getBankProvince());
			model.addAttribute("city", institutionsInfo.getBankCity());
			model.addAttribute("type", "JG");
			String bankCardNo = institutionsInfo.getBankCardNo();
			if (StringUtil.isNotBlank(bankCardNo)) {
				model.addAttribute("bankCardNo", bankCardNo);
			}
			if (StringUtil.isNotBlank(institutionsInfo.getBankOpenName())) {
				model.addAttribute("oldBankOpenName", institutionsInfo.getBankOpenName());
			}
		}
		model.addAttribute("certNo",
			this.getCertNo(SessionLocalManager.getSessionLocal().getAccountId()));
		model.addAttribute("bankList", bankList);
		return vm_path + "addBankCard.vm";
	}
	
	@RequestMapping("addBankCardSubmit")
	public String addBankCardSubmit(String bankOpenName, String bankCardNo, String bankType,
									String bankKey, String bankProvince, String bankCity,
									String bankAddress, Model model) throws Exception {
		if (SessionLocalManager.getSessionLocal() == null) {
			return "/help/nopermission";
		}
		if (StringUtil.isEmpty(bankOpenName) || StringUtil.isEmpty(bankCardNo)
			|| StringUtil.isEmpty(bankType) || StringUtil.isEmpty(bankProvince)
			|| StringUtil.isEmpty(bankCity)) {
			model.addAttribute("reason", "绑定银行卡信息不全，请检查重新填写！");
			return vm_path + "addBankCardFail.vm";
		}
		String userBaseId = SessionLocalManager.getSessionLocal().getUserBaseId();
		UserBaseInfoDO userBaseInfo = userBaseInfoManager.queryByUserBaseId(userBaseId);
		PersonalInfoDO personalInfo = null;
		InstitutionsInfoDO institutionsInfo = null;
		boolean bool = false;
		if ("GR".equals(userBaseInfo.getType())) {
			personalInfo = personalInfoManager.query(userBaseId);
			if (personalInfo.getCertNo() == null || "".equals(personalInfo.getCertNo())) {
				model.addAttribute("reason", "处理绑卡失败,未进行实名认证申请！");
				return vm_path + "addBankCardFail.vm";
			}
			
			//			ValidationBankCardInfo validationCardInfo = new ValidationBankCardInfo();
			//			validationCardInfo.setExtendId(BusinessNumberUtil.gainNumber());
			//			validationCardInfo.setAccountName(bankOpenName);
			//			validationCardInfo.setAccountNo(bankCardNo);
			//			validationCardInfo.setBankCode(bankType);
			//			//"C", "信用卡类型" \"D", "借记卡类型" 平台只接受借记卡
			//			validationCardInfo.setCardType("D");
			//			String cardNo = personalInfo.getCertNo();//二代身份证
			//			String cardOne = cardNo.substring(0, 6) + cardNo.substring(8, 17);//一代身份证
			//			validationCardInfo.setCertNo(cardNo);
			OpeningBankQueryOrder queryOrder = new OpeningBankQueryOrder();
			queryOrder.setCardNumber(bankCardNo);
			queryOrder.setAccountName(bankOpenName);
			queryOrder.setBankCode(bankType);
			queryOrder.setCardType(CardTypeEnum.JJ);
			queryOrder.setCertNo(personalInfo.getCertNo());
			BankInfoResult bankInfoResult = openingBankService.findCardByCardNo(queryOrder);
			boolean isValidate = false;
			if (bankInfoResult.getCardInfo() != null) {
				if (bankInfoResult.getCardInfo().getCardType() == CardTypeEnum.JJ) {
					isValidate = true;
				}
			}
			if (!isValidate) {
				logger.error("绑定银行卡失败原因:" + bankInfoResult);
				model.addAttribute("reason", "验证银行卡信息失败！");
				return vm_path + "addBankCardFail.vm";
			}
			
		} else {
			institutionsInfo = institutionsInfoManager.query(userBaseId);
		}
		
		if ("GR".equals(userBaseInfo.getType())) {
			personalInfo.setBankOpenName(bankOpenName);
			personalInfo.setBankCardNo(bankCardNo);
			personalInfo.setBankType(bankType);
			personalInfo.setBankKey(bankKey);
			personalInfo.setBankProvince(bankProvince);
			personalInfo.setBankCity(bankCity);
			personalInfo.setBankAddress(bankAddress);
			bool = updatePersonal(personalInfo, userBaseInfo);
		}
		
		if ("JG".equals(userBaseInfo.getType())) {
			institutionsInfo.setBankOpenName(bankOpenName);
			institutionsInfo.setBankCardNo(bankCardNo);
			institutionsInfo.setBankType(bankType);
			institutionsInfo.setBankKey(bankKey);
			institutionsInfo.setBankProvince(bankProvince);
			institutionsInfo.setBankCity(bankCity);
			institutionsInfo.setBankAddress(bankAddress);
			bool = updateInstitutions(institutionsInfo, userBaseInfo);
		}
		if (StringUtil.isNotBlank(bankCardNo)) {
			model.addAttribute("bankCardNo",
				bankCardNo.substring(bankCardNo.length() - 4, bankCardNo.length()));
		}
		if (bool) {
			return vm_path + "addBankCardSuccess.vm";
		} else {
			model.addAttribute("reason", "处理绑卡失败！");
			return vm_path + "addBankCardFail.vm";
		}
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
	
	public String getCertNo(String accountId) {
		String certNo = "fail";
		if (!accountId.equals("") && accountId != null) {
			certNo = personalInfoManager.getCertNoByAccountId(accountId);
		}
		return certNo;
	}
}
