package com.icebreak.p2p.charge.interceptor;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.icebreak.p2p.charge.ChargeService;
import com.icebreak.p2p.charge.TradeChargeService;
import com.icebreak.p2p.charge.TradeChargeServiceFactory;
import com.icebreak.p2p.daointerface.ChargeProjectDao;
import com.icebreak.p2p.daointerface.ChargeTemplateDao;
import com.icebreak.p2p.daointerface.TradeDao;
import com.icebreak.p2p.daointerface.TradeDetailDao;
import com.icebreak.p2p.dataobject.ChargeProject;
import com.icebreak.p2p.dataobject.ChargeTemplate;
import com.icebreak.p2p.dataobject.Trade;
import com.icebreak.p2p.dataobject.TradeDetail;
import com.icebreak.p2p.ws.enums.SysUserRoleEnum;

public class ChargeInterceptor implements MethodInterceptor {
	/**
	 * 代理的方式名称
	 */
	private String[]					methodNames	= null;
	
	private TradeDao					tradeDao;
	
	private ChargeTemplateDao			chargeTemplateDao;
	
	private TradeDetailDao				tradeDetailDao;
	
	private ChargeProjectDao			chargeProjectDao;
	
	private TradeChargeServiceFactory	chargeServiceFactory;
	
	private ChargeService				chargeService;
	
	public void setMethodNames(String[] methodName) {
		this.methodNames = methodName;
	}
	
	public void setChargeService(ChargeService chargeService) {
		this.chargeService = chargeService;
	}
	
	public void setChargeServiceFactory(TradeChargeServiceFactory chargeServiceFactory) {
		this.chargeServiceFactory = chargeServiceFactory;
	}
	
	public void setChargeProjectDao(ChargeProjectDao chargeProjectDao) {
		this.chargeProjectDao = chargeProjectDao;
	}
	
	public void setTradeDetailDao(TradeDetailDao detailDao) {
		this.tradeDetailDao = detailDao;
	}
	
	public void setChargeTemplateDao(ChargeTemplateDao chargeTemplateDao) {
		this.chargeTemplateDao = chargeTemplateDao;
	}
	
	public void setTradeDao(TradeDao tradeDao) {
		this.tradeDao = tradeDao;
	}
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object[] args = invocation.getArguments();
		Method method = invocation.getMethod();
		Object result = method.invoke(invocation.getThis(), args);
		if (!proxy(method.getName())) {
			return result;
		}
		long tradeId = (Long) args[0];
		int status = (Short) args[1];
		if (args.length > 2) {
			investCharge(tradeId, (TradeDetail) args[2], -1);
		} else {
			tradeCharge(tradeId, status);
		}
		return result;
	}
	
	/**
	 * 基于投资的收费
	 * @param tradeId
	 * @param status
	 * @param tradeDetail
	 * @throws Exception 
	 */
	private void investCharge(long tradeId, TradeDetail tradeDetail, int status) throws Exception {
		Trade trade = tradeDao.getByTradeId(tradeId);
		if (trade == null) {
			return;
		}
		charge(trade, tradeDetail, status);
	}
	
	/**
	 * 基于交易的收费
	 * @param tradeId
	 * @param status
	 * @throws Exception 
	 */
	private void tradeCharge(long tradeId, int status) throws Exception {
		Trade trade = tradeDao.getByTradeId(tradeId);
		if (trade == null) {
			return;
		}
		List<TradeDetail> tradeDetails = tradeDetailDao.getByTradeIdAndRoles(tradeId,
			SysUserRoleEnum.BROKER.getRoleCode(), SysUserRoleEnum.GUARANTEE.getRoleCode(),
			SysUserRoleEnum.INVESTOR.getRoleCode(), SysUserRoleEnum.LOANER.getRoleCode(),
			SysUserRoleEnum.MARKETING.getRoleCode(), SysUserRoleEnum.SPONSOR.getRoleCode());
		for (TradeDetail tradeDetail : tradeDetails) {
			charge(trade, tradeDetail, status);
		}
	}
	
	/**
	 * 收费
	 * @param trade
	 * @param userId
	 * @param templateId
	 * @param status
	 * @throws Exception 
	 */
	private void charge(Trade trade, TradeDetail tradeDetail, int status) throws Exception {
		ChargeTemplate chargeTemplate = chargeTemplateDao.getChargeTemplateByUserId(
			tradeDetail.getUserId(), tradeDetail.getRoleId());
		if (chargeTemplate == null) {
			return;
		}
		List<ChargeProject> chargeProjects = chargeProjectDao.getByTemplateIdAndStatus(
			chargeTemplate.getId(), status);
		if (chargeProjects == null || chargeProjects.size() < 1) {
			return;
		}
		for (ChargeProject chargeProject : chargeProjects) {
			TradeChargeService tradeChargeService = chargeServiceFactory
				.getTradeChargeService(chargeProject.getCode());
			if (tradeChargeService == null) {
				continue;
			}
			if (status < 0) {
				chargeService.charge(tradeDetail.getUserId(),
					tradeChargeService.getAmount(tradeDetail), chargeTemplate.getId(), new Date(),
					tradeDetail.getId());
			} else {
				chargeService.charge(tradeDetail.getUserId(), tradeChargeService.getAmount(trade),
					chargeTemplate.getId(), new Date(), null);
			}
		}
	}
	
	/**
	 * 是否代理
	 * @return
	 */
	private boolean proxy(String name) {
		if (methodNames == null || methodNames.length < 1) {
			return false;
		}
		for (String methodName : methodNames) {
			if (methodName.equals(name)) {
				return true;
			}
		}
		return false;
	}
}
