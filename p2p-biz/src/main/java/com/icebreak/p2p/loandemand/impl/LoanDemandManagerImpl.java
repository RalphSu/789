package com.icebreak.p2p.loandemand.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icebreak.p2p.base.BaseBizService;
import com.icebreak.p2p.daointerface.LoanAuthRecordDao;
import com.icebreak.p2p.daointerface.TradeDao;
import com.icebreak.p2p.daointerface.TradeDetailDao;
import com.icebreak.p2p.dataobject.LoanAuthRecord;
import com.icebreak.p2p.dataobject.LoanDemandDO;
import com.icebreak.p2p.dataobject.Trade;
import com.icebreak.p2p.dataobject.TradeFlowCode;
import com.icebreak.p2p.dataobject.TradeQueryDetail;
import com.icebreak.p2p.dataobject.TradeStatusDO;
import com.icebreak.p2p.dataobject.viewObject.LoanDemandTradeVO;
import com.icebreak.p2p.division.DivisionService;
import com.icebreak.p2p.ext.domain.ResultBase;
import com.icebreak.p2p.loandemand.LoanDemandManager;
import com.icebreak.p2p.loandemand.result.LoanDemandResultEnum;
import com.icebreak.p2p.loandemand.valueobject.QueryConditions;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.page.PageParamUtil;
import com.icebreak.p2p.trade.InvestService;
import com.icebreak.p2p.trade.QueryTradeOrder;
import com.icebreak.p2p.util.BusinessNumberUtil;
import com.icebreak.p2p.util.CommonUtil;
import com.icebreak.p2p.util.DateUtil;
import com.icebreak.p2p.util.YrdConstants;
import com.icebreak.p2p.ws.enums.DivisionWayEnum;
import com.icebreak.p2p.ws.enums.InsureWayEnum;
import com.icebreak.p2p.ws.result.AddLoanDemandResult;
import com.icebreak.p2p.ws.service.ResultEnum;
import com.icebreak.util.lang.util.StringUtil;

@Service
public class LoanDemandManagerImpl extends BaseBizService implements LoanDemandManager {
	
	@Autowired
	protected TradeDao			tradeDao;
	@Autowired
	protected TradeDetailDao	tradeDetailDao;
	@Autowired
	protected LoanAuthRecordDao	loanAuthRecordDao;
	@Autowired
	protected InvestService		investService;
	
	@Autowired
	protected DivisionService	divisionService;
	
	@Override
	public AddLoanDemandResult addLoanDemand(LoanDemandDO loanDemandDO) throws Exception {
		logger.info("发布借款需求，入参：{}", loanDemandDO);
		AddLoanDemandResult addLoanDemandResult=new AddLoanDemandResult();
		if(loanDemandDO.getRepayDivisionWay()==null){  //设置默认值 sit
			loanDemandDO.setRepayDivisionWay(DivisionWayEnum.SIT_WAY.getCode());
		}
        if(StringUtil.isEmpty(loanDemandDO.getAreaNbNo())){
            loanDemandDO.setAreaNbNo("001");
        }
		long demandId = loanDemandDAO.insert(loanDemandDO);
		logger.info("发布借款需求 ，出参：[{}]", demandId);
		if (demandId > 0) {
			addLoanDemandResult.setResultEnum(ResultEnum.EXECUTE_SUCCESS);
		}
		
		addLoanDemandResult.setDemandId(demandId);
		return addLoanDemandResult;
	}
	
	@Override
	public LoanDemandDO queryLoanDemandByDemandId(long demandId) throws Exception {
		LoanDemandDO loanDemandInfo = loanDemandDAO.queryLoanDemandByDemandId(demandId);
		loanDemandInfo.setInsureWayMsg(InsureWayEnum.getByCode(loanDemandInfo.getInsureWay()==null?"0000000000":loanDemandInfo.getInsureWay()).getMessage());
		
		if(StringUtil.isEmpty(loanDemandInfo.getRepayDivisionWay())){
			loanDemandInfo.setRepayDivisionWay(DivisionWayEnum.SIT_WAY.code());
		}
		loanDemandInfo.setRepayDivisionWayMsg(DivisionWayEnum.getByCode(loanDemandInfo.getRepayDivisionWay()).getMessage());
		return loanDemandInfo;
	}
	
	@Override
	public LoanDemandResultEnum updateLoanDemand(LoanDemandDO loanDemandDO) throws Exception {
		LoanDemandResultEnum loanDemandResult = LoanDemandResultEnum.EXECUTE_FAILURE;
		if(loanDemandDO.getRepayDivisionWay()==null){  //设置默认值 sit
			loanDemandDO.setRepayDivisionWay(DivisionWayEnum.SIT_WAY.getCode());
		}
        if(StringUtil.isEmpty(loanDemandDO.getAreaNbNo())){
            loanDemandDO.setAreaNbNo("001");
        }

		int number = loanDemandDAO.update(loanDemandDO);
		if (number > 0) {
			loanDemandResult = LoanDemandResultEnum.EXECUTE_SUCCESS;
		}
		return loanDemandResult;
	}
	
	@Override
	public Page<LoanDemandDO> pageQueryLoanDemand(QueryConditions queryConditions,
													PageParam pageParam) throws Exception {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("limitStart", (pageParam.getPageNo() - 1) * pageParam.getPageSize());
		condition.put("pageSize", pageParam.getPageSize());
		if (queryConditions.getLoanerId() > 0) {
			condition.put("loanerId", queryConditions.getLoanerId());
		}
		condition.put("tradeCode", queryConditions.getTradeCode());
		condition.put("userName", queryConditions.getUserName());
		if (queryConditions.getMaiLoanAmount() != null
			&& !"".equals(queryConditions.getMaiLoanAmount())) {
			condition.put("maiLoanAmount",
				Double.parseDouble(queryConditions.getMaiLoanAmount()) * 100);
		} else {
			condition.put("maiLoanAmount", queryConditions.getMaiLoanAmount());
		}
		if (queryConditions.getMaxLoanAmount() != null
			&& !"".equals(queryConditions.getMaxLoanAmount())) {
			condition.put("maxLoanAmount",
				Double.parseDouble(queryConditions.getMaxLoanAmount()) * 100);
		} else {
			condition.put("maxLoanAmount", queryConditions.getMaxLoanAmount());
		}
		condition.put("guaranteeName", queryConditions.getGuaranteeName());
		condition.put("sponsorName", queryConditions.getSponsorName());
		if (StringUtil.isBlank(queryConditions.getMaiDemandDate())) {
			Date maiDemandDate = DateUtil.getThreeMonthDay(new Date());
			condition.put("maiDemandDate", maiDemandDate);
			queryConditions.setMaiDemandDate(DateUtil.simpleFormatYmdhms(maiDemandDate));
		} else {
			condition.put("maiDemandDate", DateUtil.parse(queryConditions.getMaiDemandDate()));
		}
		if (StringUtil.isBlank(queryConditions.getMaxDemandDate())) {
			Date maxDemandDate = new Date();
			condition.put("maxDemandDate", maxDemandDate);
			queryConditions.setMaxDemandDate(DateUtil.simpleFormatYmdhms(maxDemandDate));
		} else {
			condition.put("maxDemandDate", DateUtil.parse(queryConditions.getMaxDemandDate()));
		}
		condition.put("status", queryConditions.getStatus());
		long totalSize = loanDemandDAO.queryCountByCondition(condition);
		List<LoanDemandDO> result = loanDemandDAO.queryListByCondition(condition);
		List<LoanDemandDO> voLists = new ArrayList<LoanDemandDO>();
		if (result != null && result.size() > 0) {
			for (LoanDemandDO loan : result) {
				if (YrdConstants.GuaranteeAuthFilterCanstants.FILTEREDGUARANTEES.indexOf(loan
					.getGuaranteeName()) >= 0) {
					Map<String, Object> conditions = new HashMap<String, Object>();
					conditions.put("loanDemandId", loan.getDemandId());
					conditions.put("authType", "DEPLOYLVTWO");
					long count = countLoanAuthRecordByCondition(conditions);
					if (count > 0) {
						loan.setGuaranteeStatement("已审核");
					} else {
						loan.setGuaranteeStatement("待审核");
					}
					voLists.add(loan);
				} else {
					loan.setGuaranteeStatement("默认已审核");
					voLists.add(loan);
				}
			}
		}
		int start = PageParamUtil.startValue((int) totalSize, pageParam.getPageSize(),
			pageParam.getPageNo());
		Page<LoanDemandDO> page = new Page<LoanDemandDO>(start, totalSize, pageParam.getPageSize(),
			voLists);
		return page;
	}
	
	@Override
	public long getWiteCountsLoandeamnd(String status) {
		Map<String, Object> condition = new HashMap<String, Object>();
		Date maiDemandDate = DateUtil.getThreeMonthDay(new Date());
		condition.put("maiDemandDate", maiDemandDate);
		Date maxDemandDate = new Date();
		condition.put("maxDemandDate", maxDemandDate);
		List<String> statusLst = new ArrayList<String>();
		statusLst.add(status);
		condition.put("status", statusLst);
		long totalSize = loanDemandDAO.queryCountByCondition(condition);
		return totalSize;
	}
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public int passInDismiss(long demandId, String status, String publishDate, String dismissReason)
																									throws Exception {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("demandId", demandId);
		condition.put("status", status);
		if (StringUtil.isNotBlank(publishDate)) {
			condition.put("publishDate", DateUtil.parse(publishDate));
		}
		condition.put("dismissReason", dismissReason);
		if ("pass".equals(status)) {
			try {
				LoanDemandDO demandDO = loanDemandDAO.queryLoanDemandByDemandId(demandId);
				divisionService.createNewTrade(demandDO);
				Trade trade = tradeDao.getByDemandId(demandId);
				TradeFlowCode tradeFlow = new TradeFlowCode();
				tradeFlow.setTblBaseId(BusinessNumberUtil.gainNumber());
				Map<String, Object> condition2 = new HashMap<String, Object>();
				condition2.put("userId", demandDO.getLoanerId());
				condition2.put("roleId", 13l);
				condition2.put("tradeId", trade.getId());
				List<TradeQueryDetail> details = tradeDetailDao
					.getTradeDetailByConditions(condition2);
				if (details != null && details.size() > 0) {
					tradeFlow.setTradeDetailId(details.get(0).getId());
				} else {
					throw new Exception("未找到融资交易");
				}
				tradeFlow.setTradeFlowCode(demandDO.getGuaranteeLicenseNo() + "R");
				tradeFlow.setRowAddTime(new Date());
				tradeFlow.setNote("融资流水号");
				tradeDao.addTradeFlowCode(tradeFlow);
			} catch (Exception e1) {
				logger.error("创建交易失败", e1);
				throw new Exception("创建交易失败--demandId=" + demandId);
			}
		}
		int number = loanDemandDAO.passInDismiss(condition);
		return number;
	}
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public void executeOnTimePublishLoanDemandJob() throws Exception {
		QueryConditions queryConditions = new QueryConditions();
		List<String> status = new ArrayList<String>();
		status.add("wite");
		queryConditions.setStatus(status);
		queryConditions.setPublishDate(DateUtil.simpleFormatYmdhms(new Date()));
		List<LoanDemandDO> loanDemands = pageQueryLoanDemandForJob(queryConditions);
		if (loanDemands != null && loanDemands.size() > 0) {
			for (LoanDemandDO loanDemandDO : loanDemands) {
				LoanDemandDO demandDO = loanDemandDAO.queryLoanDemandByDemandId(loanDemandDO
					.getDemandId());
				divisionService.createNewTrade(demandDO);
				Trade trade = tradeDao.getByDemandId(loanDemandDO.getDemandId());
				TradeFlowCode tradeFlow = new TradeFlowCode();
				tradeFlow.setTblBaseId(BusinessNumberUtil.gainNumber());
				Map<String, Object> condition2 = new HashMap<String, Object>();
				condition2.put("userId", demandDO.getLoanerId());
				condition2.put("roleId", 13l);
				condition2.put("tradeId", trade.getId());
				List<TradeQueryDetail> details = tradeDetailDao
					.getTradeDetailByConditions(condition2);
				if (details != null && details.size() > 0) {
					tradeFlow.setTradeDetailId(details.get(0).getId());
				} else {
					throw new Exception("未找到融资交易");
				}
				tradeFlow.setTradeFlowCode(demandDO.getGuaranteeLicenseNo() + "R");
				tradeFlow.setRowAddTime(new Date());
				tradeFlow.setNote("融资流水号");
				tradeDao.addTradeFlowCode(tradeFlow);
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("demandId", loanDemandDO.getDemandId());
				condition.put("status", "pass");
				condition.put("publishDate",
					DateUtil.parse(DateUtil.simpleFormatYmdhms(new Date())));
				int number = loanDemandDAO.passInDismiss(condition);
				if (number > 0) {
					logger.info("借款需求ID---" + loanDemandDO.getDemandId() + ",通过成功！");
				} else {
					logger.error("借款需求ID---" + loanDemandDO.getDemandId() + ",通过失败！");
				}
			}
			
		}
	}
	
	private List<LoanDemandDO> pageQueryLoanDemandForJob(QueryConditions queryConditions) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("status", queryConditions.getStatus());
		condition.put("publishDate", queryConditions.getPublishDate());
		List<LoanDemandDO> result = loanDemandDAO.queryListByConditionForJob(condition);
		return result;
	}
	
	@Override
	public Page<LoanDemandTradeVO> pageQueryTradeForGuarantee(QueryTradeOrder queryConditions,
																PageParam pageParam) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("guaranteeId", queryConditions.getGuaranteeId());
		condition.put("guaranteeLicenseNo", queryConditions.getGuaranteeLicenseNo());
		condition.put("guaranteeLicenseName", queryConditions.getGuaranteeLicenseName());
		if (StringUtil.isBlank(queryConditions.getStartDate())) {
			condition.put("maiDemandDate", null);
		} else {
			condition.put("maiDemandDate",
				DateUtil.parse(queryConditions.getStartDate() + " 00:00:00"));
		}
		if (StringUtil.isBlank(queryConditions.getEndDate())) {
			Date maxDemandDate = new Date();
			condition.put("maxDemandDate", maxDemandDate);
		} else {
			condition.put("maxDemandDate",
				DateUtil.parse(queryConditions.getEndDate() + " 23:59:59"));
		}
		condition.put("tradeStatus", queryConditions.getSingleState());
		long totalSize = loanDemandDAO.queryCountByConditionForGuarantee(condition);
		condition.put(
			"limitStart",
			PageParamUtil.startValue((int) totalSize, pageParam.getPageSize(),
				pageParam.getPageNo()));
		condition.put("pageSize", pageParam.getPageSize());
		List<LoanDemandDO> result = loanDemandDAO.queryListByConditionForGuarantee(condition);
		int start = PageParamUtil.startValue((int) totalSize, pageParam.getPageSize(),
			pageParam.getPageNo());
		List<LoanDemandTradeVO> resultList = new ArrayList<LoanDemandTradeVO>();
		if (result != null && totalSize > 0) {
			for (LoanDemandDO loanDemandDO : result) {
				LoanDemandTradeVO loanDemandTradeVO = new LoanDemandTradeVO();
				loanDemandTradeVO.setDemandId(loanDemandDO.getDemandId());
				loanDemandTradeVO.setGuaranteeLicenseNo(loanDemandDO.getGuaranteeLicenseNo());
				loanDemandTradeVO.setGuaranteeLicenseName(loanDemandDO.getGuaranteeLicenseName());
				loanDemandTradeVO.setDemandDate(loanDemandDO.getDemandDate());
				
				if (!"pass".equals(loanDemandDO.getStatus())) {
					loanDemandTradeVO
						.setGuaranteeStatus(YrdConstants.GuaranteeTradeStatus.GUARANTEE_CREATE);
				} else {
					Trade trade = tradeDao.getByDemandId(loanDemandDO.getDemandId());
					if (trade != null) {
						Map<String, Object> conditions = new HashMap<String, Object>();
						conditions.put("userId", queryConditions.getGuaranteeId());
						conditions.put("roleId", 8L);
						conditions.put("tradeId", trade.getId());
						List<TradeQueryDetail> tradeDetails = getTradeDetailByConditions(conditions);
						if (tradeDetails != null && tradeDetails.size() > 0) {
							loanDemandTradeVO.setBenefitAmount(tradeDetails.get(0).getAmount());
							loanDemandTradeVO.setTradeId(trade.getId());
						}
						TradeStatusDO statusDO=TradeStatusDO.getTradeStatus(trade.getStatus());
						loanDemandTradeVO.setTradeStatusDO(statusDO);
						loanDemandTradeVO.setGuaranteeStatus(statusDO.getTradeStateValue());
					
						
					}
				}
				resultList.add(loanDemandTradeVO);
			}
		}
		
		return new Page<LoanDemandTradeVO>(start, totalSize, pageParam.getPageSize(), resultList);
	}
	
	@Override
	public List<TradeQueryDetail> getTradeDetailByConditions(Map<String, Object> conditions) {
		List<TradeQueryDetail> tradeDetails = tradeDetailDao.getTradeDetailByConditions(conditions);
		return tradeDetails;
	}
	
	@Override
	public long checkNO(String licenseNo, String id) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("licenseNo", licenseNo);
		map.put("demandId", id);
		return loanDemandDAO.checkLicenceNo(map);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	public int updateFileUploadUrlById(long id, String newUrl, long loanAmout, String audit) {
		Map<String, Object> map = new HashMap<String, Object>();
		Trade trade = tradeDao.getByDemandId(id);
		if (trade != null) {
			loanAmout = trade.getLoanedAmount();
			trade.setAmount(trade.getLoanedAmount());
		} else {
			loanAmout = loanAmout * 100;
		}
		if (id != 0) {
			map.put("id", id);
			map.put("newUrl", newUrl);
			map.put("loanAmout", loanAmout);
			map.put("audit", audit);
		}
		int tn = 0;
		//int tl=0;
		try {
			//tl = tradeDao.updateTradeAmount(trade.getId(), loanAmout);
			if (trade.getStatus() == 2) {
				tn = loanDemandDAO.updateFileUploadUrlById(map);
			} else {
				LoanDemandDO demand = loanDemandDAO.queryLoanDemandByDemandId(id);
				demand.setGuaranteeLicenseUrl(newUrl);
				loanDemandDAO.update(demand);
			}
		} catch (Exception e) {
			logger.error("更新失败", e);
			return 0;
		}
		if (tn > 0) {
			return 1;
		}
		return 0;
	}
	
	public void setTradeDao(TradeDao tradeDao) {
		this.tradeDao = tradeDao;
	}
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public void addLoanAuthRecord(LoanAuthRecord record) {
		loanAuthRecordDao.addLoanAuthRecord(record);
	}
	
	@Override
	public long countLoanAuthRecordByCondition(Map<String, Object> conditions) {
		return loanAuthRecordDao.countLoanAuthRecordByCondition(conditions);
	}
	
	@Override
	public Page<LoanDemandTradeVO> pageQueryTradeForGuaranteeAuth(QueryTradeOrder queryConditions,
																	PageParam pageParam) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("guaranteeId", queryConditions.getGuaranteeId());
		condition.put("guaranteeLicenseNo", queryConditions.getGuaranteeLicenseNo());
		condition.put("guaranteeLicenseName", queryConditions.getGuaranteeLicenseName());
		List<String> status = new ArrayList<String>();
		status.add("wite");
		condition.put("status", status);
		long totalSize = loanDemandDAO.queryCountByCondition(condition);
		condition.put(
			"limitStart",
			PageParamUtil.startValue((int) totalSize, pageParam.getPageSize(),
				pageParam.getPageNo()));
		condition.put("pageSize", pageParam.getPageSize());
		List<LoanDemandDO> result = loanDemandDAO.queryListByCondition(condition);
		int start = PageParamUtil.startValue((int) totalSize, pageParam.getPageSize(),
			pageParam.getPageNo());
		List<LoanDemandTradeVO> resultList = new ArrayList<LoanDemandTradeVO>();
		if (result != null && totalSize > 0) {
			for (LoanDemandDO loanDemandDO : result) {
				LoanDemandTradeVO loanDemandTradeVO = new LoanDemandTradeVO();
				loanDemandTradeVO.setDemandId(loanDemandDO.getDemandId());
				loanDemandTradeVO.setGuaranteeLicenseNo(loanDemandDO.getGuaranteeLicenseNo());
				loanDemandTradeVO.setGuaranteeLicenseName(loanDemandDO.getGuaranteeLicenseName());
				loanDemandTradeVO.setDemandDate(loanDemandDO.getDemandDate());
				Map<String, Object> conditions = new HashMap<String, Object>();
				conditions.put("loanDemandId", loanDemandDO.getDemandId());
				conditions.put("authType", "DEPLOYLVTWO");
				long count = countLoanAuthRecordByCondition(conditions);
				if (count == 0) {
					resultList.add(loanDemandTradeVO);
				}
			}
		}
		
		return new Page<LoanDemandTradeVO>(start, resultList.size(), pageParam.getPageSize(),
			resultList);
	}
	
	@Override
	public List<LoanAuthRecord> getLoanAuthRecordByCondition(Map<String, Object> conditions) {
		
		return loanDemandDAO.getLoanAuthRecordByCondition(conditions);
	}
	
	@Override
	public Page<LoanDemandDO> pageQueryOfflineLoanDemand(QueryConditions queryConditions,
															PageParam pageParam) throws Exception {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("limitStart", (pageParam.getPageNo() - 1) * pageParam.getPageSize());
		condition.put("pageSize", pageParam.getPageSize());
		if (queryConditions.getLoanerId() > 0) {
			condition.put("loanerId", queryConditions.getLoanerId());
		}
		condition.put("tradeCode", queryConditions.getTradeCode());
		condition.put("userName", queryConditions.getUserName());
		if (queryConditions.getMaiLoanAmount() != null
			&& !"".equals(queryConditions.getMaiLoanAmount())) {
			condition.put("maiLoanAmount",
				Double.parseDouble(queryConditions.getMaiLoanAmount()) * 100);
		} else {
			condition.put("maiLoanAmount", queryConditions.getMaiLoanAmount());
		}
		if (queryConditions.getMaxLoanAmount() != null
			&& !"".equals(queryConditions.getMaxLoanAmount())) {
			condition.put("maxLoanAmount",
				Double.parseDouble(queryConditions.getMaxLoanAmount()) * 100);
		} else {
			condition.put("maxLoanAmount", queryConditions.getMaxLoanAmount());
		}
		condition.put("guaranteeName", queryConditions.getGuaranteeName());
		condition.put("sponsorName", queryConditions.getSponsorName());
		if (StringUtil.isBlank(queryConditions.getMaiDemandDate())) {
			Date maiDemandDate = DateUtil.getWeekdayBeforeDate(new Date());
			condition.put("maiDemandDate", maiDemandDate);
			queryConditions.setMaiDemandDate(DateUtil.simpleFormatYmdhms(maiDemandDate));
		} else {
			condition.put("maiDemandDate", DateUtil.parse(queryConditions.getMaiDemandDate()));
		}
		if (StringUtil.isBlank(queryConditions.getMaxDemandDate())) {
			Date maxDemandDate = new Date();
			condition.put("maxDemandDate", maxDemandDate);
			queryConditions.setMaxDemandDate(DateUtil.simpleFormatYmdhms(maxDemandDate));
		} else {
			condition.put("maxDemandDate", DateUtil.parse(queryConditions.getMaxDemandDate()));
		}
		condition.put("status", queryConditions.getStatus());
		long totalSize = loanDemandDAO.queryOfflineCountByCondition(condition);
		List<LoanDemandDO> result = loanDemandDAO.queryOfflineListByCondtion(condition);
		List<LoanDemandDO> voLists = new ArrayList<LoanDemandDO>();
		if (result != null && result.size() > 0) {
			for (LoanDemandDO loan : result) {
				if (YrdConstants.GuaranteeAuthFilterCanstants.FILTEREDGUARANTEES.indexOf(loan
					.getGuaranteeName()) >= 0) {
					Map<String, Object> conditions = new HashMap<String, Object>();
					conditions.put("loanDemandId", loan.getDemandId());
					conditions.put("authType", "DEPLOYLVTWO");
					long count = countLoanAuthRecordByCondition(conditions);
					if (count > 0) {
						loan.setGuaranteeStatement("已审核");
					} else {
						loan.setGuaranteeStatement("待审核");
					}
					voLists.add(loan);
				} else {
					loan.setGuaranteeStatement("默认已审核");
					voLists.add(loan);
				}
			}
		}
		int start = PageParamUtil.startValue((int) totalSize, pageParam.getPageSize(),
			pageParam.getPageNo());
		Page<LoanDemandDO> page = new Page<LoanDemandDO>(start, totalSize, pageParam.getPageSize(),
			voLists);
		return page;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	public void onlineTrade(long loanId) throws Exception {
		LoanDemandDO demandDO = loanDemandDAO.queryLoanDemandByDemandId(loanId);
		try {
			divisionService.createNewTrade(demandDO);
			Trade trade = tradeDao.getByDemandId(loanId);
			TradeFlowCode tradeFlow = new TradeFlowCode();
			tradeFlow.setTblBaseId(BusinessNumberUtil.gainNumber());
			Map<String, Object> condition2 = new HashMap<String, Object>();
			condition2.put("userId", demandDO.getLoanerId());
			condition2.put("roleId", 13l);
			condition2.put("tradeId", trade.getId());
			List<TradeQueryDetail> details = tradeDetailDao.getTradeDetailByConditions(condition2);
			if (details != null && details.size() > 0) {
				tradeFlow.setTradeDetailId(details.get(0).getId());
			} else {
				throw new Exception("未找到融资交易");
			}
			tradeFlow.setTradeFlowCode(demandDO.getGuaranteeLicenseNo() + "R");
			tradeFlow.setRowAddTime(new Date());
			tradeFlow.setNote("融资流水号");
			tradeDao.addTradeFlowCode(tradeFlow);
		} catch (Exception e) {
			throw new Exception("上线线出错了--demandId" + loanId, e);
		}
	}
	
	@Override
	public long getEstablishCounts(QueryTradeOrder queryOrder) {
		Map<String, Object> condition = this.setQueryCondition(queryOrder);
		CommonUtil.clearMap(condition);
		long counts = loanDemandDAO.getEstablishCounts(condition);
		return counts;
	}
	
	@Override
	public long getAmountsByStatus(QueryTradeOrder queryOrder) {
		Map<String, Object> condition = this.setQueryCondition(queryOrder);
		CommonUtil.clearMap(condition);
		long amounts = loanDemandDAO.getAmountsByStatus(condition);
		return amounts;
	}
	
	@Override
	public Page<LoanDemandDO> pageEstablish(QueryTradeOrder queryOrder, PageParam pageParam) {
		Map<String, Object> condition = this.setQueryCondition(queryOrder);
		long totalSize = loanDemandDAO.getEstablishCounts(condition);
		condition.put("limitStart", (pageParam.getPageNo() - 1) * pageParam.getPageSize());
		condition.put("pageSize", pageParam.getPageSize());
		List<LoanDemandDO> lst = loanDemandDAO.queryEstablishList(condition);
		int start = PageParamUtil.startValue((int) totalSize, pageParam.getPageSize(),
			pageParam.getPageNo());
		Page<LoanDemandDO> page = new Page<LoanDemandDO>(start, totalSize, pageParam.getPageSize(),
			lst);
		CommonUtil.clearMap(condition);
		return page;
	}
	
	@Override
	public Page<Trade> pageWaitRepayLoan(QueryTradeOrder queryOrder, PageParam pageParam) {
		Map<String, Object> condition = this.setQueryCondition(queryOrder);
		long totalSize = loanDemandDAO.getCountsWaitRepayLoan(condition);
		condition.put("limitStart", (pageParam.getPageNo() - 1) * pageParam.getPageSize());
		condition.put("pageSize", pageParam.getPageSize());
		List<Trade> lst = loanDemandDAO.queryWaitRepayLoanList(condition);
		int start = PageParamUtil.startValue((int) totalSize, pageParam.getPageSize(),
			pageParam.getPageNo());
		Page<Trade> page = new Page<Trade>(start, totalSize, pageParam.getPageSize(), lst);
		CommonUtil.clearMap(condition);
		return page;
	}
	
	/**
	 * 设置查询参数
	 * @param queryOrder
	 * @return
	 */
	public Map<String, Object> setQueryCondition(QueryTradeOrder queryOrder) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("startDate", queryOrder.getStartDate());
		condition.put("endDate", queryOrder.getEndDate());
		condition.put("startExpireDate", queryOrder.getStartExpireDate());
		condition.put("endExpireDate", queryOrder.getEndExpireDate());
		condition.put("letterPdfUrl", queryOrder.getLetterPdfUrl());
		condition.put("contractPdfUrl", queryOrder.getContractPdfUrl());
		condition.put("userName", queryOrder.getUserName());
		condition.put("guaranteeName", queryOrder.getGuaranteeName());
		condition.put("status", queryOrder.getStatus());
		condition.put("tradeName", queryOrder.getTradeName());
		condition.put("guaranteeAudit", queryOrder.getGuaranteeAudit());
		return condition;
	}
	

	/**
	 * 业务规则验证
	 * @param loanDemandDO
	 * @return
	 */
	public ResultBase checkRules(LoanDemandDO loanDemandDO){
		
		ResultBase result = new ResultBase();
		result.setSuccess(true);
		
		if(DivisionWayEnum.MONTH_WAY.code().equals(loanDemandDO.getRepayDivisionWay())){
			if ("D".equals(loanDemandDO.getTimeLimitUnit())) {
				result.setSuccess(false);
				result.setMessage("按月还息不支持以天计息");
			}
		}
		
		return result;
	}

	public void delete(long demandId) {
		loanDemandDAO.delete(demandId);
	}
	
}
