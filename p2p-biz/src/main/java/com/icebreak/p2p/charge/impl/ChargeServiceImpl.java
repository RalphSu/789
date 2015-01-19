package com.icebreak.p2p.charge.impl;

import com.icebreak.p2p.charge.ChargeException;
import com.icebreak.p2p.charge.ChargeService;
import com.icebreak.p2p.daointerface.*;
import com.icebreak.p2p.dataobject.*;
import com.icebreak.p2p.page.Pagination;
import com.icebreak.p2p.transfer.TransferService;
import com.icebreak.p2p.user.UserBaseInfoManager;
import com.icebreak.p2p.util.AppConstantsUtil;
import com.icebreak.p2p.util.DateUtil;
import com.icebreak.p2p.util.YrdConstants;
import com.icebreak.p2p.ws.enums.ChargeRuleModelEnum;
import com.icebreak.p2p.ws.enums.ChargeTemplateMethodEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChargeServiceImpl implements ChargeService {
	/**
	 * 收费项目缓存：根据参数缓存
	 */
	private static Map<String, List<ChargeProject>>	projectsCache		= new HashMap<String, List<ChargeProject>>();
	
	private ChargeTemplateDao						chargeTemplateDao;
	
	private ChargeRuleDao							chargeRuleDao;
	
	private ChargeProjectDao						chargeProjectDao;
	
	private ChargeTemplateProjectDao				chargeTemplateProjectDao;
	
	private AmountFlowDao							amountFlowDao;
	
	private ChargeDetailDao							chargeDetailDao;
	
	private TransferService						transferService;
	
	private TradeDetailDao							tradeDetailDao;
	
	@Autowired
	private UserBaseInfoManager						userBaseInfoManager;
	
	private long									userId;

	private boolean									isInitializeAcount	= false;
	
	public void setTradeDetailDao(TradeDetailDao tradeDetailDao) {
		this.tradeDetailDao = tradeDetailDao;
	}
	
	public void setTransferService(TransferService transferService) {
		this.transferService = transferService;
	}
	
	public void setChargeDetailDao(ChargeDetailDao chargeDetailDao) {
		this.chargeDetailDao = chargeDetailDao;
	}
	
	public void setAmountFlowDao(AmountFlowDao amountFlowDao) {
		this.amountFlowDao = amountFlowDao;
	}
	
	public void setChargeTemplateProjectDao(ChargeTemplateProjectDao chargeTemplateProjectDao) {
		this.chargeTemplateProjectDao = chargeTemplateProjectDao;
	}
	
	public void setChargeProjectDao(ChargeProjectDao chargeProjectDao) {
		this.chargeProjectDao = chargeProjectDao;
	}
	
	public void setChargeRuleDao(ChargeRuleDao chargeRuleDao) {
		this.chargeRuleDao = chargeRuleDao;
	}
	
	public void setChargeTemplateDao(ChargeTemplateDao chargeTemplateDao) {
		this.chargeTemplateDao = chargeTemplateDao;
	}
	
	@Override
	public Pagination<ChargeTemplate> getChargeTemplates(String name, String type, int start,
															int size, String... roles) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		params.put("type", type);
		params.put("start", start);
		params.put("size", size);
		params.put("roles", roles);
		return new Pagination<ChargeTemplate>(start,
			chargeTemplateDao.getCountByProperties(params), size,
			chargeTemplateDao.getByProperties(params));
	}
	
	@Override
	public ChargeTemplate lookup(long id) {
		ChargeTemplate template = chargeTemplateDao.getById(id);
		template.setChargeRules(chargeRuleDao.getByTemplateId(id));
		template.setProjects(chargeProjectDao.getByTemplateId(id));
		return template;
	}
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public void modify(ChargeTemplate template, long[] projectIds, long[] ruleStarts,
						long[] ruleEnds, double[] ruleValues, String[] ruleMethods) {
		chargeRuleDao.deleteByTemplateId(template.getId());
		chargeTemplateProjectDao.deleteByTemplateId(template.getId());
		if (projectIds != null && projectIds.length > 0) {
			for (long id : projectIds) {
				chargeTemplateProjectDao.addChargeTemplateProject(new ChargeTemplateProject(id,
					template.getId()));
			}
		}
		if (ruleValues != null && ruleValues.length > 0) {
			if (template.getRuleModel().equalsIgnoreCase(
				ChargeRuleModelEnum.CHARGE_RULE_MODEL_INTERVAL.code())) {
				for (int i = 0; i < ruleValues.length; i++) {
					chargeRuleDao.addChargeRule(new ChargeRule(template.getId(), ruleMethods[i],
						ruleStarts[i], ruleEnds[i], ruleValues[i]));
				}
			} else {
				for (int i = 0; i < ruleValues.length; i++) {
					chargeRuleDao.addChargeRule(new ChargeRule(template.getId(), ruleMethods[i],
						ruleValues[i]));
				}
			}
		}
		chargeTemplateDao.modify(template);
	}
	
	@Override
	public List<ChargeProject> getChargeProjectsByMethod(String... methods) {
		StringBuilder builder = new StringBuilder("getChargeProjectsByMethod");
		if (methods != null && methods.length > 0) {
			for (String s : methods) {
				builder.append(s);
			}
		}
		String key = builder.toString();
		if (projectsCache.containsKey(key)) {
			return projectsCache.get(key);
		} else {
			List<ChargeProject> projects = chargeProjectDao.getByMethods(methods);
			projectsCache.put(key, projects);
			return projects;
		}
	}
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public void addChargeProjects(String[] methods, String[] projects, Integer[] statuses) {
		if (methods != null && methods.length > 0 && projects != null
			&& methods.length == projects.length) {
			for (int i = 0; i < projects.length; i++) {
				chargeProjectDao.addChargeProject(new ChargeProject(methods[i], projects[i],
					statuses[i]));
			}
			projectsCache.clear();
		}
	}
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public void deleteProjects(long... ids) {
		if (ids != null && ids.length > 0) {
			for (long id : ids) {
				chargeTemplateProjectDao.deleteByProjectId(id);
				chargeProjectDao.deleteById(id);
			}
			projectsCache.clear();
		}
	}
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public void addChargeTemplate(ChargeTemplate template, long[] projectIds, long[] ruleStarts,
									long[] ruleEnds, double[] ruleValues, String[] ruleMethods) {
		if (!validate(template)) {
			throwChargeException("收费方式为“" + template.getMethod() + "”时，收费时间不能为空。。。");
		}
		chargeTemplateDao.addChargeTemplate(template);
		if (projectIds != null && projectIds.length > 0) {
			for (long id : projectIds) {
				chargeTemplateProjectDao.addChargeTemplateProject(new ChargeTemplateProject(id,
					template.getId()));
			}
		}
		if (ruleValues != null && ruleValues.length > 0) {
			if (template.getRuleModel().equalsIgnoreCase(
				ChargeRuleModelEnum.CHARGE_RULE_MODEL_INTERVAL.code())) {
				for (int i = 0; i < ruleValues.length; i++) {
					chargeRuleDao.addChargeRule(new ChargeRule(template.getId(), ruleMethods[i],
						ruleStarts[i], ruleEnds[i], ruleValues[i]));
				}
			} else {
				for (int i = 0; i < ruleValues.length; i++) {
					chargeRuleDao.addChargeRule(new ChargeRule(template.getId(), ruleMethods[i],
						ruleValues[i]));
				}
			}
		}
	}
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public void charge(long userId, int roleId) throws Exception {
		ChargeTemplate template = chargeTemplateDao.getChargeTemplateByUserId(userId, roleId);
		if (template == null) {
			return;
		}
		Date start = getStartChargeDate(userId, template);
		Date end = getEndChargeDate(start, template);
		if (shouldCharge(end)) {
			charge(userId, amountFlowDao.getTotalAmountFlow(userId, start, end), template.getId(),
				end, null);
		}
	}
	
	/**
	 * 收费
	 * @param userId
	 * @param amountFlow
	 * @param templateId
	 * @param date
	 * @throws Exception 
	 */
	@Override
	public void charge(long userId, long amountFlow, long templateId, Date date, Long tradeDetailId)
																									throws Exception {
		if (amountFlow == 0) {
			return;
		}
		ChargeRule chargeRule = chargeRuleDao.getByAmount(templateId, amountFlow);
		if (chargeRule == null) {
			return;
		}
		long amount = calculateAmount(chargeRule, amountFlow);//应收金额
		doCharge(userId, amount, date, tradeDetailId);
	}
	
	/**
	 * 执行收费操作
	 * @param userId 用户ID
	 * @param amount 金额
	 * @param date 日期
	 * @return
	 * @throws Exception 
	 */
	private boolean doCharge(long userId, long amount, Date date, Long tradeDetailId)
																						throws Exception {
		initializePlatformAccountInfo();
		String out = tradeDetailDao.getYjfUserNameByUserId(userId);
		if (out == null || out.length() < 1) {
			throwChargeException("该用户没有资金账户，用户ID：" + userId);
		}
		String accountId = tradeDetailDao.getYjfUserNameByUserId(this.userId);
		boolean b = transferService.doTransfer(accountId, out, amount,
			YrdConstants.ChargeDetail.CHARGE_DETAIL_NOTE, date);
		ChargeDetail chargeDetail = new ChargeDetail(userId, amount, b, date,
			YrdConstants.ChargeDetail.CHARGE_DETAIL_NOTE, tradeDetailId);
		chargeDetailDao.addChargeDetail(chargeDetail);
		return b;
	}
	
	/**
	 * 获取结束时间
	 * @return
	 */
	private Date getStartChargeDate(long userId, ChargeTemplate template) {
		Date start = chargeDetailDao.getLastChargeDate(userId);
		return start == null ? template.getChargeDate() : start;
	}
	
	/**
	 * 获取下次收费的时间
	 * @param template
	 * @return
	 */
	private Date getEndChargeDate(Date start, ChargeTemplate template) {
		if (template.getMethod().equalsIgnoreCase(
			ChargeTemplateMethodEnum.CHARGE_TEMPLATE_METHOD_MONTH.code())) {
			return DateUtil.getDateByMonth(start, 1);
		} else if (template.getMethod().equalsIgnoreCase(
			ChargeTemplateMethodEnum.CHARGE_TEMPLATE_METHOD_SEASON.code())) {
			return DateUtil.getDateByMonth(start, 3);
		} else if (template.getMethod().equalsIgnoreCase(
			ChargeTemplateMethodEnum.CHARGE_TEMPLATE_METHOD_YEAR.code())) {
			return DateUtil.getDateByMonth(start, 12);
		}
		return null;
	}
	
	/**
	 * 是否应该收费
	 * @return
	 */
	private boolean shouldCharge(Date end) {
		if (end == null) {
			return false;
		}
		return System.currentTimeMillis() >= end.getTime();
	}
	
	/**
	 * 计算收费的金额
	 * @param rule
	 * @param amount
	 * @return
	 */
	private long calculateAmount(ChargeRule rule, long amount) {
		if (rule.getMethod().equalsIgnoreCase(ChargeRuleModelEnum.CHARGE_RULE_MODEL_AMOUNT.code())) {
			return (long) rule.getChargeValue();
		} else if (rule.getMethod().equalsIgnoreCase(
			ChargeRuleModelEnum.CHARGE_RULE_MODEL_INTERVAL.code())) {
			return 0;
		} else {
			return (long) (rule.getChargeValue() * amount);
		}
	}
	
	/**
	 * 数据校验:收费模版数据是否合法
	 * @param template
	 * @return
	 */
	private boolean validate(ChargeTemplate template) {
		String method = template.getMethod();
		return ((method.equalsIgnoreCase(ChargeTemplateMethodEnum.CHARGE_TEMPLATE_METHOD_MONTH
			.code())
					|| method
						.equalsIgnoreCase(ChargeTemplateMethodEnum.CHARGE_TEMPLATE_METHOD_SEASON
							.code()) || method
			.equalsIgnoreCase(ChargeTemplateMethodEnum.CHARGE_TEMPLATE_METHOD_YEAR.code())) && template
			.getChargeDate() != null)
				|| method.equalsIgnoreCase(ChargeTemplateMethodEnum.CHARGE_TEMPLATE_METHOD_TRADE
					.code());
	}
	
	/**
	 * 抛出异常
	 * @param message
	 */
	private void throwChargeException(String message) {
		throw new ChargeException(message);
	}
	
	private void initializePlatformAccountInfo() throws Exception {
		if (!isInitializeAcount) {
			UserBaseInfoDO financeUser = userBaseInfoManager.queryByAccountId(AppConstantsUtil
				.getProfitSharingAccount());
			this.userId = financeUser.getUserId();
			isInitializeAcount = true;
		}
		
	}
}
