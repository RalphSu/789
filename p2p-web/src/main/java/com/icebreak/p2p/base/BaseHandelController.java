package com.icebreak.p2p.base;

import com.icebreak.p2p.common.services.LoanTypeService;
import com.icebreak.p2p.dataobject.IndexTrade;
import com.icebreak.p2p.dataobject.LoanTypeDO;
import com.icebreak.p2p.dataobject.PopInfoDO;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.page.Pagination;
import com.icebreak.p2p.pop.IPopService;
import com.icebreak.p2p.util.MoneyUtil;
import com.icebreak.p2p.ws.enums.TradeStatusEnum;
import com.icebreak.p2p.ws.service.query.order.IndexQueryOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class BaseHandelController extends BaseAutowiredController {
	private String vm_path = "/";
	@Autowired
	IPopService popService;

	@Resource
	private LoanTypeService loanTypeService;
	
	@RequestMapping("error.htm")
	public String signBankCard(Model model) throws Exception {
		return vm_path + "common/error.htm";
	}

	@RequestMapping("goto.htm")
	public String turnPage(Model model) throws Exception {
		return vm_path + "common/goto.htm";
	}
	
	@RequestMapping("gotoInvest.htm")
	public String turnInvestPage(Model model) throws Exception {
		return vm_path + "common/gotoInvest.htm";
	}


	@RequestMapping("")
	public String index(PageParam pageParam, Model model) throws Exception {

		//统计数据
		long totalUserCount = userStatisticsService
			.countUserByConditions(new HashMap<String, Object>());
		Map<String, Object> params = new HashMap<String, Object>();
		long dealAmount = tradeService.countAmountByParams(params);
		List<Integer> stats = new ArrayList<Integer>();
		stats.add(TradeStatusEnum.REPAY_FINISH.ordinal());
		stats.add(TradeStatusEnum.COMPENSATORY_REPAY_FINISH.ordinal());
		params.put("status", stats);
		long repayAmount = tradeService.countAmountByParams(params);
		long interestAmount = tradeService.countInterestAmountByParams(params);
		String dealedAmount = MoneyUtil.getFormatAmount(dealAmount);
		String repaidAmount = MoneyUtil.getFormatAmount(repayAmount + interestAmount);
        String benefitAmount = MoneyUtil.getFormatAmount(interestAmount);
		/*model.addAttribute("dealedAmount",
			dealedAmount.substring(0, dealedAmount.lastIndexOf(".") + 1));*/
		model.addAttribute("dealedAmount", dealedAmount);
		model.addAttribute("repaidAmount",
			repaidAmount.substring(0, repaidAmount.lastIndexOf(".") + 1));
		model.addAttribute("dealedSupAmount",
			dealedAmount.substring(dealedAmount.lastIndexOf(".") + 1));
		model.addAttribute("repaidSupAmount",
			repaidAmount.substring(repaidAmount.lastIndexOf(".") + 1));
		model.addAttribute("totalUserCount", totalUserCount);
        //收益金额  .substring(0,benefitAmount.indexOf("."))  去掉小数
        model.addAttribute("benefitAmount",benefitAmount);
        //收益金额小数点后面的数
        model.addAttribute("benefitSupAmount",benefitAmount.substring(benefitAmount.lastIndexOf(".") + 1));
		
		//资讯动态
		
		Map<String, Object> conditions1 = new HashMap<String, Object>();
		List<Integer> types1 = new ArrayList<Integer>();

		types1.add(10);
		conditions1.put("type", types1);
		conditions1.put("status", 2);
		pageParam.setPageSize(7);
		Page<PopInfoDO> page1 = popService.getPageByConditions(pageParam, conditions1);
		List<PopInfoDO> informationHelp = page1.getResult();
		model.addAttribute("informationHelp", informationHelp);
		//end
        
		//新闻
		Map<String, Object> conditions2 = new HashMap<String, Object>();
		List<Integer> types2 = new ArrayList<Integer>();

		types2.add(11);
		conditions2.put("type", types2);
		conditions2.put("status", 2);
		
		Page<PopInfoDO> page2 = popService.getPageByConditions(pageParam, conditions2);
		List<PopInfoDO> newsHelp = page2.getResult();
		model.addAttribute("newsHelp", newsHelp);
		//end
		//活动
		Map<String, Object> conditions3 = new HashMap<String, Object>();
		List<Integer> types3 = new ArrayList<Integer>();

		types3.add(12);
		conditions3.put("type", types3);
		conditions3.put("status", 2);
		
		Page<PopInfoDO> page3 = popService.getPageByConditions(pageParam, conditions3);
		List<PopInfoDO> activityHelp = page3.getResult();
		model.addAttribute("activityHelp", activityHelp);
		//end
		//会员
		Map<String, Object> conditions4 = new HashMap<String, Object>();
		List<Integer> types4 = new ArrayList<Integer>();

		types4.add(13);
		conditions4.put("type", types4);
		conditions4.put("status", 2);
		
		Page<PopInfoDO> page4 = popService.getPageByConditions(pageParam, conditions4);
		List<PopInfoDO> membersHelp = page4.getResult();
		model.addAttribute("membersHelp", membersHelp);
		//end
		
		
		
		Map<String, Object> conditions = new HashMap<String, Object>();
		List<Integer> types = new ArrayList<Integer>();
		types.add(1);
		types.add(2);
		conditions.put("type", types);
		conditions.put("status", 2);
		pageParam.setPageSize(3);
		Page<PopInfoDO> page = popService.getPageByConditions(pageParam, conditions);
		List<PopInfoDO> popNotices = page.getResult();
		model.addAttribute("noticesIndexs", popNotices);
		Map<String, Object> helpConditions = new HashMap<String, Object>();
		List<Integer> helpTypes = new ArrayList<Integer>();
		helpTypes.add(4);
		helpConditions.put("type", helpTypes);
		helpConditions.put("status", 2);
		pageParam.setPageSize(5);
		Page<PopInfoDO> pageHelp = popService.getPageByConditions(pageParam, helpConditions);
		List<PopInfoDO> popHelps = pageHelp.getResult();
		model.addAttribute("helpIndexs", popHelps);
		
		//帮助模块，index页
		Map<String, Object> con = new HashMap<String, Object>();
		List<Integer> typesH= new ArrayList<Integer>();
		typesH.add(4);
		typesH.add(5);
		con.put("type", typesH);
		con.put("status", 2);
		List<PopInfoDO> showList = popService.getListByConditions(con);
		
		model.addAttribute("helps", showList);
		Map<String, Object> conditionsH = new HashMap<String, Object>();
		List<Integer> typesH2= new ArrayList<Integer>();
		typesH2.add(5);
		conditionsH.put("type", typesH2);
		conditionsH.put("status", 2);
		List<PopInfoDO> moduleList = popService.getListByConditions(conditionsH);
		model.addAttribute("helpModules", moduleList);
		//end


		//项目列表
		IndexQueryOrder indexQueryOrder = new IndexQueryOrder();
		indexQueryOrder.setStatus(TradeStatusEnum.TRADING.ordinal());
		Pagination<IndexTrade> indexTrade = indexTradeService.getIndexTrades(0,8, indexQueryOrder);

		model.addAttribute("page", fillDetail(indexTrade));
		//model.addAttribute("status", status);

		//借款类型
		LoanTypeDO loanTypeDO = new LoanTypeDO();
		loanTypeDO.setHidden("N");

		List<LoanTypeDO> loanTypes = loanTypeService.queryList(loanTypeDO);
		model.addAttribute("loanTypes", loanTypes);

		return vm_path + "front/index/index.vm";
	}


	public static void main(String[] args)
	{
			System.out.println(TradeStatusEnum.REPAY_FINISH.ordinal());
			System.out.println(TradeStatusEnum.REPAY_FINISH.getCode());
			System.out.println(TradeStatusEnum.REPAY_FINISH.getMessage());
			System.out.println(TradeStatusEnum.REPAY_FINISH.code());
			System.out.println(TradeStatusEnum.REPAY_FINISH.message());

	}

	
}
