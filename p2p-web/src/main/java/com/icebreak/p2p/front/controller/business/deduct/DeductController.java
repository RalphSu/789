//package com.icebreak.p2p.front.controller.business.deduct;
//
//import com.alibaba.fastjson.JSONObject;
//import com.icebreak.p2p.base.BaseAutowiredController;
//import com.icebreak.p2p.dataobject.BankInfo;
//import com.icebreak.p2p.dataobject.InstitutionsInfoDO;
//import com.icebreak.p2p.dataobject.PersonalInfoDO;
//import com.icebreak.p2p.dataobject.UserBaseInfoDO;
//import com.icebreak.p2p.session.SessionLocalManager;
//import com.icebreak.p2p.trade.WithdrawalService;
//import com.icebreak.p2p.user.result.UserBaseReturnEnum;
//import com.icebreak.p2p.util.AESEncrypt;
//import com.icebreak.p2p.util.AppConstantsUtil;
//import com.icebreak.p2p.util.DateUtil;
//import com.icebreak.p2p.util.SysCommand;
//import com.icebreak.p2p.ws.enums.UserTypeEnum;
//import com.icebreak.util.lang.util.StringUtil;
//import com.icebreak.util.lang.util.money.MoneyUtil;
//import com.itextpdf.text.*;
//import com.itextpdf.text.pdf.*;
//import com.yiji.openapi.sdk.message.common.YzzUserAccountQueryResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.*;
//import java.text.NumberFormat;
//import java.util.Date;
//import java.util.UUID;
//
//@Controller
//@RequestMapping("deduct")
//public class DeductController extends BaseAutowiredController {
//	@Autowired
//	protected WithdrawalService withdrawalService;
//	
//	/** 返回页面路径 */
//	String DEDUCTION_MANAGE_PATH = "/front/business/deduct/";
//	
//	/**
//	 * 跳转代扣充值
//	 * */
//	@RequestMapping("launchDeduction")
//	public String launchDeduction(Model model, HttpSession session) {
//		logger.info("代扣,入参--");
//		String userBaseId = SessionLocalManager.getSessionLocal().getUserBaseId();
//		String bankCardNo = "";
//		BankInfo bankInfo = null;
//		UserBaseInfoDO userBaseInfo = null;
//		try {
//			userBaseInfo = userBaseInfoManager.queryByUserBaseId(userBaseId);
//		} catch (Exception e) {
//			logger.error("获取银行卡信息失败！", e);
//		}
//		if (!"IS".equals(userBaseInfo.getRealNameAuthentication())
//			|| !"IS".equals(userBaseInfo.getMobileBinding())) {
//			model.addAttribute("fail", "实名认证未通过或者手机未绑定！");
//			return DEDUCTION_MANAGE_PATH + "launchDeduction.vm";
//		}
//		if ("JG".equals(userBaseInfo.getType())) {
//			InstitutionsInfoDO institutionsInfo = null;
//			try {
//				institutionsInfo = institutionsInfoManager.query(userBaseId);
//			} catch (Exception e) {
//				logger.error("获取银行卡信息失败！", e);
//			}
//			bankCardNo = institutionsInfo.getBankCardNo();
//			if (!institutionsInfo.getEnterpriseName().equals(institutionsInfo.getBankOpenName())) {
//				model.addAttribute("fail", "已绑卡银行帐户名与实名信息不匹配！");
//				if (StringUtil.isNotBlank(institutionsInfo.getBankCardNo())) {
//					model.addAttribute("notMathch", "已绑卡银行帐户名与实名信息不匹配！");
//				} else {
//					model.addAttribute("notMathch", "还未绑定银行卡！");
//					model.addAttribute("noCard", "还未绑定银行卡！");
//				}
//				return DEDUCTION_MANAGE_PATH + "launchDeduction.vm";
//			}
//			bankInfo = bankBaseService.query19BankInfo(institutionsInfo.getBankType());
//			model.addAttribute("info", institutionsInfo);
//			model.addAttribute("bankInfo", bankInfo);
//			if (StringUtil.isNotBlank(institutionsInfo.getBankCardNo())) {
//				model.addAttribute(
//					"bankCardNo_4",
//					institutionsInfo.getBankCardNo().substring(
//						institutionsInfo.getBankCardNo().length() - 4,
//						institutionsInfo.getBankCardNo().length()));
//			} else {
//				model.addAttribute("bankCardNo_4", "");
//			}
//			
//		}
//		if ("GR".equals(userBaseInfo.getType())) {
//			PersonalInfoDO personalInfo = null;
//			try {
//				personalInfo = personalInfoManager.query(userBaseId);
//				bankCardNo = personalInfo.getBankCardNo();
//			} catch (Exception e) {
//				logger.error("获取银行卡信息失败！", e);
//			}
//			if (!personalInfo.getRealName().equals(personalInfo.getBankOpenName())) {
//				model.addAttribute("fail", "已绑卡银行帐户名与实名信息不匹配！");
//				if (StringUtil.isNotBlank(personalInfo.getBankCardNo())) {
//					model.addAttribute("notMathch", "已绑卡银行帐户名与实名信息不匹配！");
//				} else {
//					model.addAttribute("notMathch", "还未绑定银行卡！");
//					model.addAttribute("noCard", "还未绑定银行卡！");
//				}
//				return DEDUCTION_MANAGE_PATH + "launchDeduction.vm";
//			}
//			if (!"normal".equals(userBaseInfo.getState())) {
//				model.addAttribute("fail", "帐户状态异常,无法代扣申请！");
//				return DEDUCTION_MANAGE_PATH + "launchDeduction.vm";
//			}
//			bankInfo = bankBaseService.query19BankInfo(personalInfo.getBankType());
//			model.addAttribute("info", personalInfo);
//			model.addAttribute("bankInfo", bankInfo);
//			if (StringUtil.isNotBlank(personalInfo.getBankCardNo())) {
//				model.addAttribute(
//					"bankCardNo_4",
//					personalInfo.getBankCardNo().substring(
//						personalInfo.getBankCardNo().length() - 4,
//						personalInfo.getBankCardNo().length()));
//			} else {
//				model.addAttribute("bankCardNo_4", "");
//			}
//		}
//		if (bankInfo != null) {
//			double difference = bankBaseService.getDifference(bankInfo, bankCardNo, "DEDUCT");
//			NumberFormat nf = NumberFormat.getInstance();
//			nf.setGroupingUsed(false);
//			model.addAttribute("difference", nf.format(difference));
//		}
//		return DEDUCTION_MANAGE_PATH + "launchDeduction.vm";
//	}
//	
//	/**
//	 * 校验划入金额
//	 * @param amount
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping("validationDeductAmount")
//	public Object validationDeductAmount(String amount) {
//		JSONObject json = new JSONObject();
//		String userBaseId = SessionLocalManager.getSessionLocal().getUserBaseId();
//		String bankCardNo = "";
//		BankInfo bankInfo = null;
//		json.put("code", 0);
//		json.put("message", "未查询到划入配置信息");
//		UserBaseInfoDO userBaseInfo = null;
//		try {
//			userBaseInfo = userBaseInfoManager.queryByUserBaseId(userBaseId);
//		} catch (Exception e) {
//			logger.error("验证用户划入金额时，查询用户异常", e);
//			json.put("code", 0);
//			json.put("message", "验证用户划入金额时，查询用户异常");
//		}
//		if (SysCommand.GR.equals(userBaseInfo.getType())) {
//			PersonalInfoDO personalInfo = null;
//			try {
//				personalInfo = personalInfoManager.query(userBaseId);
//				bankCardNo = personalInfo.getBankCardNo();
//				bankInfo = bankBaseService.query19BankInfo(personalInfo.getBankType());
//			} catch (Exception e) {
//				logger.error("获取个人用户银行卡信息失败！", e);
//				json.put("code", 0);
//				json.put("message", "获取个人用户银行卡信息失败");
//			}
//		} else if (SysCommand.JG.equals(userBaseInfo.getType())) {
//			InstitutionsInfoDO institutionsInfo = null;
//			try {
//				institutionsInfo = institutionsInfoManager.query(userBaseId);
//				bankCardNo = institutionsInfo.getBankCardNo();
//				bankInfo = bankBaseService.query19BankInfo(institutionsInfo.getBankType());
//			} catch (Exception e) {
//				logger.error("获取机构银行卡信息失败！", e);
//				json.put("code", 0);
//				json.put("message", "获取机构用户银行卡信息失败");
//			}
//		}
//		if (Double.parseDouble(amount) > bankInfo.getOddDeductLimit()) {
//			json.put("code", 0);
//			json.put(
//				"message",
//				"您本次划入金额超出所绑定银行单笔限额。您当前使用的" + bankInfo.getBankName() + "单笔限额:"
//						+ bankInfo.getSingleDeductLimit() + "元,单日限额:"
//						+ bankInfo.getOddDeductLimit() + "元");
//			return json;
//		}
//		if (bankInfo != null) {
//			double difference = bankBaseService.getDifference(bankInfo, bankCardNo, "DEDUCT");
//			if (Double.parseDouble(amount) > difference) {
//				NumberFormat nf = NumberFormat.getInstance();
//				nf.setGroupingUsed(false);
//				json.put("code", 0);
//				json.put(
//					"message",
//					"您本次划入超出可划入额度。您当前使用的" + bankInfo.getBankName() + "单笔限额:"
//							+ bankInfo.getSingleDeductLimit() + "元,单日限额:"
//							+ bankInfo.getOddDeductLimit() + "元。您本次还可划入" + nf.format(difference)
//							+ "元");
//				return json;
//			} else {
//				json.put("code", 1);
//				json.put("message", "本次可划入");
//			}
//		}
//		return json;
//	}
//	
//	@RequestMapping("signDeduction")
//	public String signDeduction(String bankCardNo_4, String banklogo, String real, String fees,
//								String amount, HttpSession session, Model model) throws Exception {
//		String userBaseId = SessionLocalManager.getSessionLocal().getUserBaseId();
//		UserBaseInfoDO userBaseInfo = userBaseInfoManager.queryByUserBaseId(userBaseId);
//		if ("GR".equals(userBaseInfo.getType())) {
//			PersonalInfoDO personalInfo = personalInfoManager.query(userBaseId);
//			model.addAttribute("cardNo", personalInfo.getCertNo());
//			model.addAttribute("bankOpenName", personalInfo.getBankOpenName());
//			model.addAttribute("bankName",
//				bankBaseService.query19BankInfo(personalInfo.getBankType()).getBankName());
//			model.addAttribute("bankCardNo", personalInfo.getBankCardNo());
//		}
//		if ("JG".equals(userBaseInfo.getType())) {
//			InstitutionsInfoDO institutionsInfo = institutionsInfoManager.query(userBaseId);
//			model.addAttribute("cardNo", institutionsInfo.getLegalRepresentativeCardNo());
//			model.addAttribute("bankOpenName", institutionsInfo.getBankOpenName());
//			model.addAttribute("bankName",
//				bankBaseService.query19BankInfo(institutionsInfo.getBankType()).getBankName());
//			model.addAttribute("bankCardNo", institutionsInfo.getBankCardNo());
//		}
//		model.addAttribute("userName", SessionLocalManager.getSessionLocal().getUserName());
//		model.addAttribute("bankCardNo_4", bankCardNo_4);
//		model.addAttribute("banklogo", banklogo);
//		model.addAttribute("realName", SessionLocalManager.getSessionLocal().getRealName());
//		model.addAttribute("real", real);
//		model.addAttribute("fees", fees);
//		model.addAttribute("amount", amount);
//		model.addAttribute("date", DateUtil.simpleDateFormatmdhChinese(new Date()));
//		return DEDUCTION_MANAGE_PATH + "signDeduction.vm";
//	}
//	
//	@SuppressWarnings("unused")
//	@RequestMapping("confirmDeduction")
//	private String confirmDeduction(String bankCardNo_4, String banklogo, String real, String fees,
//									String amount, HttpSession session, Model model)
//																					throws Exception {
//		if (StringUtil.isBlank(bankCardNo_4)) {
//			model.addAttribute("reason", "未关联银行卡或者重复刷新");
//			this.getAccount(model);
//			return DEDUCTION_MANAGE_PATH + "completeDeductionNO.vm";
//		}
//		if (StringUtil.isBlank(banklogo)
//			|| StringUtil.isBlank(SessionLocalManager.getSessionLocal().getRealName())) {
//			this.launchDeduction(model, session);
//		}
//		String userBaseId = SessionLocalManager.getSessionLocal().getUserBaseId();
//		UserBaseInfoDO userBaseInfo = userBaseInfoManager.queryByUserBaseId(userBaseId);
//		if ("GR".equals(userBaseInfo.getType())) {
//			PersonalInfoDO personalInfo = personalInfoManager.query(userBaseId);
//			model.addAttribute("bankOpenName", personalInfo.getBankOpenName());
//			model.addAttribute("bankCardNo_4", personalInfo.getBankCardNo());
//		}
//		if ("JG".equals(userBaseInfo.getType())) {
//			InstitutionsInfoDO institutionsInfo = institutionsInfoManager.query(userBaseId);
//			model.addAttribute("bankOpenName", institutionsInfo.getAccountId());
//			model.addAttribute("bankCardNo_4", institutionsInfo.getBankCardNo());
//		}
//		model.addAttribute("amount", amount);
//		model.addAttribute("banklogo", banklogo);
//		model.addAttribute("realName", SessionLocalManager.getSessionLocal().getRealName());
//		model.addAttribute("real", real);
//		model.addAttribute("fees", fees);
//		model.addAttribute("bundPhone", userBaseInfo.getMobile());
//		model.addAttribute("date",
//			DateUtil.simpleDateFormatmdhChinese(DateUtil.getAfterDay(new Date())));
//		String token = UUID.randomUUID().toString();
//		session.setAttribute("token", token);
//		return DEDUCTION_MANAGE_PATH + "completeDeduction.vm";
//	}
//	
//	@SuppressWarnings("unused")
//	@RequestMapping("completeDeduction")
//	private String completeDeduction(String amount, String payPassword, String business,
//										String mobile, String code, String token,
//										HttpSession session, Model model) throws Exception {
//		String getToken = (String) session.getAttribute("token");
//		String key = (String) session.getAttribute(session.getId());
//		AESEncrypt aesEncrypt = new AESEncrypt();
//		aesEncrypt.setIvParameter(key);
//		aesEncrypt.setsKey(key);
//		try {
//			payPassword = aesEncrypt.decrypt(payPassword);
//		} catch (Exception e) {
//			logger.error("密码安全处理异常", e);
//			model.addAttribute("reason", "密码安全处理异常");
//			return DEDUCTION_MANAGE_PATH + "completeDeductionNO.vm";
//		}
//		//		String returnMessage = messageServiceFacade.verifySmsCode(mobile, business, code, false);
//		//		if(!"发送成功".equals(returnMessage)){
//		//			model.addAttribute("reason", "验证码校验失败");
//		//			return DEDUCTION_MANAGE_PATH + "completeDeductionNO.vm";
//		//		}
//		UserBaseReturnEnum returnEnum = userBaseInfoManager.validationPayPassword(
//			SessionLocalManager.getSessionLocal().getUserBaseId(), payPassword);
//		model.addAttribute("date",
//			DateUtil.simpleDateFormatmdhChinese(DateUtil.getAfterDay(new Date())));
//		model.addAttribute("amount", amount);
//		this.getAccount(model);
//		if (returnEnum == UserBaseReturnEnum.PASSWORD_SUCCESS && token.equals(getToken)) {
//			session.removeAttribute("token");
//			//TODO 调取代扣接口//------加入数据库记录
//			JSONObject bool = new JSONObject();
//			this.getAccount(model);
//			if ((Integer) bool.get("code") == 1) {
//				session.setAttribute("ws", "success");
//				return DEDUCTION_MANAGE_PATH + "completeDeductionOK.vm";
//			} else {
//				model.addAttribute("reason", bool.get("message"));
//				return DEDUCTION_MANAGE_PATH + "completeDeductionNO.vm";
//			}
//		} else {
//			String ws = (String) session.getAttribute("ws");
//			if (null != ws) {
//				return DEDUCTION_MANAGE_PATH + "completeDeductionOK.vm";
//			} else {
//				model.addAttribute("reason", "处理代扣失败");
//				return DEDUCTION_MANAGE_PATH + "completeDeductionNO.vm";
//			}
//		}
//	}
//	
//	private void getAccount(Model model) {
//		YzzUserAccountQueryResponse account = apiTool.queryUserAccount(SessionLocalManager
//			.getSessionLocal().getAccountId());
//		
//		if (account.success()) {
//			
//			model.addAttribute("balance", MoneyUtil.toStandardString(account.getAvailableBalance()));
//			model.addAttribute("freezeAmount",
//				MoneyUtil.toStandardString(account.getFreezeBalance()));
//			model
//				.addAttribute("availableBalance", MoneyUtil.toStandardString(account.getAvailableBalance()));
//		} else {
//			model.addAttribute("balance", "资金信息查询失败");
//			model.addAttribute("freezeAmount", "资金信息查询失败");
//			model.addAttribute("availableBalance", "资金信息查询失败");
//		}
//	}
//	
//	@RequestMapping("deductReceipt")
//	public String deductReceipt(String bankName, String bankCardNo, String amount, String date,
//								HttpSession session, Model model) throws Exception {
//		String userBaseId = SessionLocalManager.getSessionLocal().getUserBaseId();
//		UserBaseInfoDO userBaseInfo = userBaseInfoManager.queryByUserBaseId(userBaseId);
//		if ("GR".equals(userBaseInfo.getType())) {
//			PersonalInfoDO personalInfo = personalInfoManager.query(userBaseId);
//			model.addAttribute("cardNo", personalInfo.getCertNo());
//			model.addAttribute("bankOpenName", personalInfo.getBankOpenName());
//		}
//		if ("JG".equals(userBaseInfo.getType())) {
//			InstitutionsInfoDO institutionsInfo = institutionsInfoManager.query(userBaseId);
//			model.addAttribute("cardNo", institutionsInfo.getLegalRepresentativeCardNo());
//			model.addAttribute("bankOpenName", institutionsInfo.getBankOpenName());
//		}
//		model.addAttribute("bankCardNo", bankCardNo);
//		model.addAttribute("bankName", java.net.URLDecoder.decode(bankName, "UTF-8"));
//		model.addAttribute("userName", SessionLocalManager.getSessionLocal().getUserName());
//		model.addAttribute("realName", SessionLocalManager.getSessionLocal().getRealName());
//		model.addAttribute("amount", amount);
//		model.addAttribute("date", date);
//		return DEDUCTION_MANAGE_PATH + "deductReceipt.vm";
//	}
//	
//	@RequestMapping("deductReceiptDownLoad")
//	public void deductReceiptDownLoad(String date, String bankName, String bankCardNo,
//										String amount, HttpServletRequest request,
//										HttpServletResponse response, Model model) throws Exception {
//		String userBaseId = SessionLocalManager.getSessionLocal().getUserBaseId();
//		UserBaseInfoDO userBaseInfo = userBaseInfoManager.queryByUserBaseId(userBaseId);
//		String cardNo = null;
//		String bankOpenName = null;
//		if (UserTypeEnum.GR.code().equals(userBaseInfo.getType())) {
//			PersonalInfoDO personalInfo = personalInfoManager.query(userBaseId);
//			cardNo = personalInfo.getCertNo();
//			bankOpenName = personalInfo.getBankOpenName();
//		}
//		if (UserTypeEnum.JG.code().equals(userBaseInfo.getType())) {
//			InstitutionsInfoDO institutionsInfo = institutionsInfoManager.query(userBaseId);
//			cardNo = institutionsInfo.getLegalRepresentativeCardNo();
//			bankOpenName = institutionsInfo.getBankOpenName();
//		}
//		bankName = java.net.URLDecoder.decode(bankName, "UTF-8");
//		String userName = SessionLocalManager.getSessionLocal().getUserName();
//		String realName = SessionLocalManager.getSessionLocal().getRealName();
//		FileInputStream fis = null;
//		BufferedInputStream buff = null;
//		OutputStream servletOS = null;
//		PdfReader reader = null;
//		String servletPath = request.getServletContext().getRealPath("/");
//		String filePath = servletPath + "/resources/pdf/deductReceiptInitial"
//							+ System.currentTimeMillis() + ".pdf";
//		String fileOutPath = servletPath + "/resources/pdf/deductReceipt"
//								+ System.currentTimeMillis() + ".pdf";
//		String imagePath = servletPath + "/resources/themes/default/image/main_logo.gif";
//		FileOutputStream fos = null;
//		String extName = "划入凭证.pdf";
//		Document doc = new Document(PageSize.A4);
//		try {
//			fos = new FileOutputStream(filePath);
//			PdfWriter writer = PdfWriter.getInstance(doc, fos);
//			doc.open();
//			// 解决中文问题  
//			BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
//				BaseFont.NOT_EMBEDDED);
//			Font titleChinese = new Font(bfChinese, 20, Font.BOLD); // 模板抬头的字体     
//			Paragraph title = new Paragraph("快捷划入代扣授权委托书", titleChinese);// 抬头
//			title.setAlignment(Element.ALIGN_CENTER); // 居中设置
//			title.setLeading(1f);//设置行间距//设置上面空白宽度
//			doc.add(title);
//			
//			Font fontZH = new Font(bfChinese, 12, Font.NORMAL);
//			String content = "委托人：";
//			Paragraph paragraph = new Paragraph(content, fontZH);
//			doc.add(paragraph);
//			content = "姓名：" + realName;
//			paragraph = new Paragraph(content, fontZH);
//			doc.add(paragraph);
//			content = "证件类型：二代身份证";
//			paragraph = new Paragraph(content, fontZH);
//			doc.add(paragraph);
//			content = "证件号码：" + cardNo;
//			paragraph = new Paragraph(content, fontZH);
//			doc.add(paragraph);
//			content = "用户名：" + userName;
//			paragraph = new Paragraph(content, fontZH);
//			doc.add(paragraph);
//			content = "日期： " + date;
//			paragraph = new Paragraph(content, fontZH);
//			doc.add(paragraph);
//			content = "被委托人：" + AppConstantsUtil.getPlatformName() + "";
//			paragraph = new Paragraph(content, fontZH);
//			doc.add(paragraph);
//			content = "就委托人通过被委托人代为发起从委托人的银行帐户向委托人在被委托人指定的第三方支付机构所设立的资金托管账户快捷划入资金的相关事宜向被委托人授权如下：";
//			paragraph = new Paragraph(content, fontZH);
//			doc.add(paragraph);
//			doc.add(Chunk.NEWLINE);
//			content = "一、授权内容：委托人授权被委托人根据委托人的快捷划入指令代为向被委托人指定的第三方支付机构发起从本授权委托书第二条所述的委托人银行账户扣划相当于本授权委托书第二条所述的快捷划入金额的款项至委托人在该第三方支付机构所设立的资金托管账户（“快捷划入服务”）。";
//			paragraph = new Paragraph(content, fontZH);
//			doc.add(paragraph);
//			doc.add(Chunk.NEWLINE);
//			content = "二、 委托人的银行账户及快捷划入金额如下：";
//			paragraph = new Paragraph(content, fontZH);
//			doc.add(paragraph);
//			content = "户名：" + bankOpenName;
//			paragraph = new Paragraph(content, fontZH);
//			doc.add(paragraph);
//			content = "账号：" + bankCardNo;
//			paragraph = new Paragraph(content, fontZH);
//			doc.add(paragraph);
//			content = "开户银行：" + bankName;
//			paragraph = new Paragraph(content, fontZH);
//			doc.add(paragraph);
//			content = "划入金额：人民币" + amount + "元（含第三方需收的手续费【如有】）";
//			paragraph = new Paragraph(content, fontZH);
//			doc.add(paragraph);
//			content = "委托人已经通过本授权委托书确认上述银行账户信息，在代扣的过程中，被委托人指定的第三方支付机构根据本授权委托书提供的银行账户信息进行相关操作，无需再向委托人确认银行账户信息和密码。";
//			paragraph = new Paragraph(content, fontZH);
//			doc.add(paragraph);
//			doc.add(Chunk.NEWLINE);
//			content = "三、委托人知晓并确认，本授权委托书系使用委托人在被委托人处开立的"
//						+ AppConstantsUtil.getProductName()
//						+ "金融服务平台用户名、以网络在线点击确认的方式向被委托人发出。本授权委托书自委托人在被委托人的平台网站点击确认申请书时生效。本授权委托书一经生效即不可撤销。";
//			paragraph = new Paragraph(content, fontZH);
//			doc.add(paragraph);
//			doc.add(Chunk.NEWLINE);
//			content = "四、委托人知晓并同意，除非被委托人有过错，被委托人不对委托人快捷划入服务的资金到账时间做任何承诺。被委托人指定的第三方支付机构仅根据本授权委托书进行相关操作，被委托人及其指定的第三方支付机构不对根据本授权委托书所采取的全部行动和措施的时效性和结果承担任何责任。";
//			paragraph = new Paragraph(content, fontZH);
//			doc.add(paragraph);
//			content = "委托人确认并承诺，被委托人指定的第三方支付机构根据本授权委托书所采取的全部行动和措施的法律后果均由委托人承担。";
//			paragraph = new Paragraph(content, fontZH);
//			doc.add(paragraph);
//			doc.add(Chunk.NEWLINE);
//			content = "特此授权。";
//			paragraph = new Paragraph(content, fontZH);
//			doc.add(paragraph);
//			//	            XMLWorkerHelper.getInstance().parseXHtml(writer, doc,
//			//	            		new ByteArrayInputStream(str.getBytes()));
//			doc.close();
//			logger.info("创建文档完成");
//			reader = new PdfReader(filePath, "ry".getBytes());//选择需要印章的pdf
//			fos = new FileOutputStream(fileOutPath);
//			PdfStamper stamp = new PdfStamper(reader, fos);//加完印章后的pdf
//			PdfContentByte over = stamp.getOverContent(1);//设置在第几页打印印章
//			Image img = Image.getInstance(imagePath);//选择图片
//			img.setAlignment(1);
//			img.scaleAbsolute(70, 20);//控制图片大小
//			img.setAbsolutePosition(35, 805);//控制图片位置
//			over.addImage(img);
//			stamp.close();
//		} catch (Exception e) {
//			logger.error("密码安全处理异常", e);
//		} finally {
//			if (reader != null) {
//				reader.close();
//			}
//		}
//		File file = new File(fileOutPath);
//		try {
//			fis = new FileInputStream(file);
//			buff = new BufferedInputStream(fis);
//		} catch (FileNotFoundException e) {
//			logger.error("FileNotFoundException异常", e);
//		}
//		response.setContentType("application/pdf");
//		try {
//			response.addHeader("Content-Disposition",
//				"attachment; filename=" + new String(extName.getBytes("GB2312"), "ISO8859-1"));
//		} catch (UnsupportedEncodingException e1) {
//			logger.error("UnsupportedEncodingException异常", e1);
//		}
//		byte[] b = new byte[1024];
//		long k = 0;
//		try {
//			servletOS = response.getOutputStream();
//			while (k < file.length()) {
//				int j = buff.read(b, 0, 1024);
//				k += j;
//				servletOS.write(b, 0, j);
//			}
//			servletOS.flush();
//			servletOS.close();
//			fis.close();
//			file.delete();
//			File fileInit = new File(filePath);
//			fileInit.delete();
//		} catch (IOException e) {
//			logger.error("UnsupportedEncodingException异常", e);
//		} finally {
//			if (fis != null) {
//				fis.close();
//			}
//			if (fos != null) {
//				fos.close();
//			}
//		}
//	}
//}
