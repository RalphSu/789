package com.icebreak.p2p.common.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icebreak.p2p.base.BaseAutowiredService;
import com.icebreak.p2p.common.services.INotifierService;
import com.icebreak.p2p.daointerface.TradeDetailDao;
import com.icebreak.p2p.dataobject.InvestDetailDO;
import com.icebreak.p2p.dataobject.Trade;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.trade.TradeService;
import com.icebreak.p2p.user.UserBaseInfoManager;
import com.icebreak.p2p.util.YrdConstants;

@Service
public class NotifierServiceImpl  extends BaseAutowiredService implements INotifierService{
	@Autowired
	protected TradeDetailDao tradeDetailDao;
	@Autowired
	protected TradeService tradeService;
	@Autowired
	protected UserBaseInfoManager userBaseInfoManager;
	
	@Override
	public void notifierInvestorPDFOK(long tradeId) throws Exception {
		Trade trade = tradeService.getByTradeId(tradeId);
		Map<String, Object> investCondition = new HashMap<String, Object>();
		investCondition.put("tradeId", tradeId);
		List<InvestDetailDO>investDetails = tradeDetailDao.queryInvestDetail(investCondition);
		//通知投资人。。
		for (InvestDetailDO investDetailDO : investDetails) {
			StringBuilder toInvestorMessage = new  StringBuilder();
			String content =YrdConstants.MessageNotifyConstants.PDF_OK_TO_INVESTOR;
			content = content.replace("var1", trade.getName());
			toInvestorMessage.append(content);
			UserBaseInfoDO investor = null;
			List<UserBaseInfoDO> investors = userBaseInfoManager.queryByType(null, String.valueOf(investDetailDO.getUserId()));
			if(investors != null &&  investors.size() > 0){
				investor = investors.get(0);
			}else{
				logger.error("查询投资人信息异常，userId，{}", investDetailDO.getUserId());
			}
			messageService.notifyUser(investor, toInvestorMessage.toString());
		}
	}

}
