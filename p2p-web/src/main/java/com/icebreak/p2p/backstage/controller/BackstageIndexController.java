package com.icebreak.p2p.backstage.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.icebreak.p2p.session.SessionLocal;
import com.icebreak.p2p.session.SessionLocalManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.icebreak.p2p.base.BaseAutowiredController;
import com.icebreak.p2p.trade.QueryTradeOrder;
import com.icebreak.p2p.user.valueobject.QueryConditions;
import com.icebreak.p2p.util.DateUtil;
import com.icebreak.p2p.util.MoneyUtil;

@Controller
@RequestMapping("backstage")
public class BackstageIndexController extends BaseAutowiredController {
	
	@RequestMapping("backstageIdex")
	public String backstageIdex(Model model) {
		return "/backstage/index.vm";
	}
	@RequestMapping("header")
	public String header(Model model) {
		String userName = SessionLocalManager.getSessionLocal().getUserName();
		model.addAttribute("userName", userName);
		return "/backstage/layout/header.vm";
	}
	
	@RequestMapping("left")
	public String left(Model model) {
		return "/backstage/layout/backLeft.vm";
	}
	@RequestMapping("main")
	public String main(Model model) {
		QueryConditions queryConditions = new QueryConditions();
		queryConditions.setRealNameAuthentication("IN");
		long inRealNamAuthentication = personalInfoManager.queryCountsRealNameSta(queryConditions);//实名认证中
		model.addAttribute("inRealNamAuthentication", inRealNamAuthentication);
		long witeLoanDemand = loanDemandManager.getWiteCountsLoandeamnd("wite");//待审核项目
		model.addAttribute("witeLoanDemand", witeLoanDemand);
		QueryTradeOrder queryOrder = new QueryTradeOrder();
		List<String> status = new ArrayList<String>();
		status.add("2");
		status.add("3");
		status.add("8");
		queryOrder.setStatus(status);
		queryOrder.setLetterPdfUrl(null);
		queryOrder.setContractPdfUrl(null);
		queryOrder.setGuaranteeAudit("IS");   //实际上所有数据都为NO
		long noLetterContract = loanDemandManager.getEstablishCounts(queryOrder);//待上传合同、担保函项目
		model.addAttribute("noLetterContract", noLetterContract);


		status.remove(1);
		queryOrder.setStatus(status);
		queryOrder.setStartExpireDate(DateUtil.simpleFormat(new Date()) + " 00:00:00");
		queryOrder.setEndExpireDate(DateUtil.getWeekdayAfterDateNow(new Date())+" 23:59:59");
		queryOrder.setLetterPdfUrl("Y");
		queryOrder.setContractPdfUrl("Y");
		queryOrder.setGuaranteeAudit(null);
		long waitRepay = loanDemandManager.getEstablishCounts(queryOrder);//一周内待还款项目
		model.addAttribute("waitRepay", waitRepay);

		queryOrder.setStartExpireDate("");
		queryOrder.setEndExpireDate("");
		queryOrder.setStatus(null);
		long issueLoanDemand = loanDemandManager.getEstablishCounts(queryOrder);//已发行的项目数
		long issueAmounts = loanDemandManager.getAmountsByStatus(queryOrder);
		model.addAttribute("issueLoanDemand", issueLoanDemand);
		model.addAttribute("issueAmounts", MoneyUtil.getFormatAmount(issueAmounts));

		status.add("3");
		status.add("5");
		status.add("6");
		status.add("7");
		queryOrder.setStatus(status);
		long establishLoanDemand = loanDemandManager.getEstablishCounts(queryOrder);//成立的项目数
		long establishAmounts = loanDemandManager.getAmountsByStatus(queryOrder);//成立金额
		model.addAttribute("establishLoanDemand", establishLoanDemand);
		model.addAttribute("establishAmounts", MoneyUtil.getFormatAmount(establishAmounts));

		List<String> status2 = new ArrayList<String>();
		status2.add("3");
		status2.add("7");
		queryOrder.setStatus(status2);
		long relieveLoanDemand = loanDemandManager.getEstablishCounts(queryOrder);//成立的项目数
		long relieveAmounts = loanDemandManager.getAmountsByStatus(queryOrder);//成立金额
		model.addAttribute("relieveLoanDemand", relieveLoanDemand);
		model.addAttribute("relieveAmounts", MoneyUtil.getFormatAmount(relieveAmounts));

		List<String> status3 = new ArrayList<String>();
		status3.add("2");
		status3.add("5");
		status3.add("6");
		status3.add("8");
		queryOrder.setStatus(status3);
		long guaranteeLoanDemand = loanDemandManager.getEstablishCounts(queryOrder);//成立的项目数
		long guaranteeAmounts = loanDemandManager.getAmountsByStatus(queryOrder);//成立金额
		model.addAttribute("guaranteeLoanDemand", guaranteeLoanDemand);
		model.addAttribute("guaranteeAmounts", MoneyUtil.getFormatAmount(guaranteeAmounts));
		return "/backstage/layout/main.vm";
	}
	@RequestMapping("footer")
	public String footer(Model model) {
		return "/backstage/layout/footer.vm";
	}
	
	@RequestMapping("nopermission")
	public String hasNoPermission() {
		return "/backstage/nopermission.vm";
	}
	
}
