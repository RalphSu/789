package com.oms.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icebreak.p2p.dal.daointerface.ExtendAttrDAO;
import com.icebreak.p2p.dataobject.IndexTrade;
import com.icebreak.p2p.dataobject.LoanDemandDO;
import com.icebreak.p2p.dataobject.Trade;
import com.icebreak.p2p.front.controller.boot.BootController;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.page.Pagination;
import com.icebreak.p2p.session.SessionLocal;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.util.MoneyUtil;
import com.icebreak.p2p.web.util.RateUtil;
import com.icebreak.p2p.ws.enums.TradeStatusEnum;
import com.icebreak.p2p.ws.service.query.order.IndexQueryOrder;
import com.icebreak.util.lang.util.DateUtil;

@Controller
@RequestMapping("weichat")
public class WeiIndexController extends BootController {
	@Autowired
    ExtendAttrDAO extendAttrDAO;
	private final String wc_path = "/weichat/";
	
	@RequestMapping("index.do/{size}/{page}")
	public String index(@PathVariable int size, @PathVariable int page, String guarantee,
            String timeUnit, Integer startTime, Integer endTime, Long startAmount,
            Long endAmount, Double startRate, Double endRate, Model model){
		super.initAccountInfo(model);
		Pagination<IndexTrade> indexTrade = indexTradeService.getIndexTrades((page - 1) * size,
                size, guarantee, timeUnit, startTime, endTime, startAmount, endAmount, startRate,
                endRate);

        model.addAttribute("page", fillDetail(indexTrade));
        model.addAttribute("status", 1);
		return wc_path + "index.vm";
	}
	
	@ResponseBody
	@RequestMapping("getProducts.json")
	public Object getProductsByPage(int size, int page, String guarantee,
            String timeUnit, Integer startTime, Integer endTime, Long startAmount,
            Long endAmount, Double startRate, Double endRate){
		Map<String, Object> result = new HashMap<String, Object>();
		Pagination<IndexTrade> indexTrade = indexTradeService.getIndexTrades((page - 1) * size,
                size, guarantee, timeUnit, startTime, endTime, startAmount, endAmount, startRate,
                endRate);
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		for(IndexTrade trade : indexTrade.getResult()){
			Map<String, Object> tradeMap = new HashMap<String, Object>();
			tradeMap.put("demandId", trade.getDemandId());
			tradeMap.put("tradeId", trade.getTradeId());
			tradeMap.put("strRaate", RateUtil.getRate(trade.getRate()));
			tradeMap.put("timeLimit", trade.getTime());
			tradeMap.put("leastInvestAmount", MoneyUtil.getFormatAmount(trade.getLeastInvestAmount()));
			if(trade.getStatus() == 1){
				//已完成
				tradeMap.put("amount", MoneyUtil.getMoneyByw(trade.getAmount()));
				tradeMap.put("p", "100%");
			}else{
				tradeMap.put("amount", MoneyUtil.getMoneyByw(trade.getLoanedAmount()));
				tradeMap.put("p", MoneyUtil.getPercentage(trade.getLoanedAmount(), trade.getAmount(),trade.getLeastInvestAmount()));
			}
			tradeMap.put("name", trade.getName());
			tradeMap.put("investAvalibleTime", DateUtil.dtSimpleFormat(trade.getInvestAvalibleTime()));
			tradeMap.put("deadline", DateUtil.dtSimpleFormat(trade.getDeadline()));
			resultList.add(tradeMap);
		}
		result.put("result", resultList);
		return result;
	}
	
	@RequestMapping("detail.do/{demandId}/{tradeId}")
	public String productDetail(@PathVariable long demandId, @PathVariable long tradeId, HttpSession session, Model model) throws Exception{
		SessionLocal sessionLocal = SessionLocalManager.getSessionLocal();
		//查询当前用户是否存在体验金
        if(null != sessionLocal) {
            model.addAttribute("userGoldExp", super.queryUserGoldExp(sessionLocal.getUserId()));
            super.initAccountInfo(model);
        }
        //trade主题对象
        Trade trade = tradeService.getByTradeId(tradeId);
        if(trade.getStatus() == 2) {
            model.addAttribute("finishTrade", "true");
        }
      //到期无条件兑付银行
        String paymentBankName = extendAttrDAO.getExtendValue(demandId, "LOANDEMAND_PAYMENTBANKNAME");
        model.addAttribute("paymentBankName", paymentBankName);

        model.addAttribute("investableAmount", trade.getAmount() - trade.getLoanedAmount());

        LoanDemandDO loanDemand = loanDemandManager.queryLoanDemandByDemandId(demandId);
        model.addAttribute("loanDemand", loanDemand);
        Date date = new Date();
        if(date.getTime() > loanDemand.getDeadline().getTime()) {
            model.addAttribute("overDeadLine", "true");
        }
        String percent = "0%";

        if (trade.getDeadline().before(new Date())) {
            if (isFullScale(trade)) {
                percent = "100.0%";
            } else {
                percent = MoneyUtil.getPercentage(trade.getLoanedAmount(), trade.getAmount(), trade.getLeastInvestAmount());
            }
        } else {
            percent = MoneyUtil.getPercentage(trade.getLoanedAmount(), trade.getAmount(), trade.getLeastInvestAmount());
        }
        model.addAttribute("percent", percent);
        model.addAttribute("tradeId", tradeId);
        model.addAttribute("demandId", demandId);
        model.addAttribute("trade", trade);
      //计算最迟还款日（到期日期(结息日期)+3个工作日）
        Date expireDate = trade.getExpireDateTime();
        if(expireDate != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(expireDate);
            cal.add(Calendar.DAY_OF_YEAR, 3);//直接加3天
//            //一周第一天是否为星期天
            model.addAttribute("lastExpireDate", cal.getTime());
        }
        //新建一个投资token对象
        String token = UUID.randomUUID().toString();
        session.setAttribute("token", token);
        boolean investAvlTimeReached = (new Date().after(loanDemand.getInvestAvalibleTime()));
        model.addAttribute("investAvlTimeReached", investAvlTimeReached);
        boolean deadlineReached = (new Date().after(loanDemand.getDeadline()));
        model.addAttribute("deadlineReached", deadlineReached);
		return wc_path + "detail.vm";
	}
	
	@RequestMapping("first.do")
	public String first(PageParam pageParam, Model model){
		//显示项目
		IndexQueryOrder indexQueryOrder = new IndexQueryOrder();
		indexQueryOrder.setStatus(TradeStatusEnum.TRADING.ordinal());
		Pagination<IndexTrade> indexTrade = indexTradeService.getIndexTrades(0,8, indexQueryOrder);

		model.addAttribute("trade", fillDetail(indexTrade).getResult().get(0));
		return wc_path + "first.vm";
	}
	
}
