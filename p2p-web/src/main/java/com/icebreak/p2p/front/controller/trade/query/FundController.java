package com.icebreak.p2p.front.controller.trade.query;

import com.icebreak.p2p.base.UserAccountInfoBaseController;
import com.icebreak.p2p.dataobject.RechargeFlow;
import com.icebreak.p2p.integration.openapi.info.DepositInfo;
import com.icebreak.p2p.integration.openapi.info.QueryWithdrawInfo;
import com.icebreak.p2p.integration.openapi.order.QueryDepositOrder;
import com.icebreak.p2p.integration.openapi.order.WithdrawQueryOrder;
import com.icebreak.p2p.integration.openapi.result.DepositQueryResult;
import com.icebreak.p2p.integration.openapi.result.WithdrawQueryResult;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.session.SessionLocal;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.trade.QueryTradeOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("usercenter")
public class FundController extends UserAccountInfoBaseController {
	private final String user_vm_path = "/front/user/fundrecord/";
	
	@RequestMapping("fundRecord")
	public String fundRecord(Model model, PageParam pageParam) {
		SessionLocal sessionLocal = SessionLocalManager.getSessionLocal();
		QueryTradeOrder queryTradeOrder = new QueryTradeOrder();
		queryTradeOrder.setUserId(sessionLocal.getUserId());
		Page<RechargeFlow> page = rechargeFlowService.getFlow(queryTradeOrder, pageParam);
		model.addAttribute("page", page);
		if("public|loaner".equals(sessionLocal.getRoleCodes())) {
			model.addAttribute("loaner", "loaner");
		}
		setCss("funds", "fundMenu", model);
		return user_vm_path + "fundRecord.vm";
	}

	@RequestMapping("depositRecord")
	public String depositRecord(Model model, HttpServletRequest request,
									HttpServletResponse response) {
		initAccountInfo(model);
		getUserBaseInfo(request.getSession(), model);
		String userId = SessionLocalManager.getSessionLocal().getAccountId();
		if(null == userId || "" == userId){
			//没有注册资金账号的时候,返回空值
			model.addAttribute("page", new Page());
		}else{
			QueryDepositOrder queryOrder = new QueryDepositOrder();
			queryOrder.setUserId(userId);
			Integer pageNo = 1;
			try {
				pageNo = Integer.valueOf(request.getParameter("pageNo"));
			} catch (NumberFormatException e) {
			}
			queryOrder.setCurrPage(pageNo);

			DepositQueryResult result = depositQueryService.depositQueryService(queryOrder);
			Page page = new Page<DepositInfo>((result.getCurrPage()-1)*result.getPageSize(), result.getSize(),
				result.getPageSize(), result.getData());
			model.addAttribute("page", page);
			setCss("funds","depositMenu",model);
		}
		return user_vm_path + "depositRecord.vm";
	}

	@RequestMapping("withdrawRecord")
	public String withdrawRecord(Model model, HttpServletRequest request,
									HttpServletResponse response) {
		initAccountInfo(model);
		getUserBaseInfo(request.getSession(), model);
		String userId = SessionLocalManager.getSessionLocal().getAccountId();
		if(null == userId || "" == userId){
			//没有注册资金账号的时候,返回空值
			model.addAttribute("page", new Page());
		}else{
			WithdrawQueryOrder queryOrder = new WithdrawQueryOrder();
			queryOrder.setUserId(userId);
			Integer pageNo = 1;
			try {
				pageNo = Integer.valueOf(request.getParameter("pageNo"));
			} catch (NumberFormatException e) {
			}
			queryOrder.setCurrPage(pageNo);
			WithdrawQueryResult result = withdrawQueryService.queryWithdrawService(queryOrder);
			Page page = new Page<QueryWithdrawInfo>((result.getCurrPage()-1)*result.getPageSize(), result.getSize(),
				result.getPageSize(), result.getData());
			model.addAttribute("page", page);
			setCss("funds","withdrawMenu",model);
		}
		return user_vm_path + "withdrawRecord.vm";
	}
	
}
