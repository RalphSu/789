package com.icebreak.p2p.trade;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.icebreak.p2p.dataobject.AgentInvestorTrade;
import com.icebreak.p2p.dataobject.AgentLoanerTrade;
import com.icebreak.p2p.dataobject.Investment;
import com.icebreak.p2p.dataobject.RechargeFlow;
import com.icebreak.p2p.dataobject.Trade;
import com.icebreak.p2p.dataobject.TradeDetail;
import com.icebreak.p2p.dataobject.TradeFlowCode;
import com.icebreak.p2p.dataobject.TradeQueryDetail;
import com.icebreak.p2p.dataobject.UserInvestEntry;
import com.icebreak.p2p.dataobject.viewObject.AmounFlowVO;
import com.icebreak.p2p.dataobject.viewObject.TradeDivisionDetailVO;
import com.icebreak.p2p.integration.openapi.order.RepayOrder;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.page.Pagination;
import com.icebreak.p2p.ws.order.CalculateLoanCostOrder;
import com.icebreak.p2p.ws.result.CalculateLoanCostResult;
import com.yiji.openapi.sdk.message.yzz.RepayNotify;

public interface TradeService {

	public void saveInvest(Trade trade, Long amount, String orderNo);

	public Pagination<Investment> getByProperties(long tradeId, int start,
			int size, Integer status, String receiveRealName,
			String receiveUserName, String payRealName, String payUserName,
			Long startAmount, Long endAmount, Date startDate, Date endDate);

	/**
	 * 修改交易状态
	 * 
	 * @param tradeId
	 * @param status
	 */
	public void modifyStatus(long tradeId, short status);

	/**
	 * 修改交易状态
	 * 
	 * @param tradeId
	 * @param status
	 * @param tradeDetail
	 */
	public void modifyStatus(long tradeId, short status, TradeDetail tradeDetail);

	/**
	 * 根据ID 查询
	 * 
	 * @param id
	 * @return
	 */
	public Investment getById(long id);

	/**
	 * 根据交易ID查询交易
	 * 
	 * @param tradeId
	 * @return
	 */
	public Trade getByTradeId(long tradeId);

	/**
	 * 还款
	 * 
	 * @param repayUserId
	 *            还款人用户ID
	 * @param tradeId
	 *            交易ID
	 * @return
	 * @throws Exception
	 */
	public RepayOrder gotoYJFRepay(long repayUserId, long demandId,
			long repayPlanId) throws Exception;

	/**
	 * 获取用户的投资记录
	 * 
	 * @param userId
	 * @param start
	 * @param size
	 * @param status
	 * @param startDate
	 * @param endDate
	 * @param code
	 * @param loanRealName
	 * @param loanUserName
	 * @param startAmount
	 * @param endAmount
	 * @param name
	 * @return
	 */
	public Pagination<UserInvestEntry> getUserInvestEntries(long userId,
			int start, int size, Integer status, Date startDate, Date endDate,
			String code, String loanRealName, String loanUserName,
			Long startAmount, Long endAmount, String name);

	/**
	 * 统计投资金额
	 * 
	 * @param userId
	 * @param start
	 * @param size
	 * @param status
	 * @param startDate
	 * @param endDate
	 * @param code
	 * @param loanRealName
	 * @param loanUserName
	 * @param startAmount
	 * @param endAmount
	 * @param name
	 * @return
	 */
	public long getAllAmount(long userId, Integer status, Date startDate,
			Date endDate, String code, String loanRealName,
			String loanUserName, Long startAmount, Long endAmount, String name);

	/**
	 * 查询金额
	 * 
	 * @param userId
	 * @param roleCode
	 * @param statuses
	 * @return
	 */
	public long getAmount(long userId, String roleCode, short... statuses);

	/***
	 * 分页查询借款需求
	 * 
	 * @param queryConditions
	 * @param pageParam
	 * @return
	 * @throws Exception
	 */
	public Page<Trade> pageQueryTrade(QueryTradeOrder queryTradeOrder,
			PageParam pageParam) throws Exception;

	/**
	 * 查询所有融资金额
	 * 
	 * @param queryTradeOrder
	 * @return
	 */
	public long getAllLoanedAmountByOrder(QueryTradeOrder queryTradeOrder);

	/**
	 * 根据交易ID获取投资条目
	 * 
	 * @param tradeId
	 * @return
	 */
	public List<UserInvestEntry> getEntriesByTradeId(long tradeId);

	/**
	 * 根据交易ID查询和角色交易明细
	 * 
	 * @param tradeId
	 * @return
	 */
	public List<TradeDetail> getByTradeIdAndRoles(long tradeId, String... roles);

	/**
	 * 校验交易
	 * 
	 * @param tradeId
	 * @param userId
	 * @param role
	 * @return
	 */
	public boolean check(long tradeId, long userId, String role);

	/**
	 * 设置生效时间和还款时间
	 * 
	 * @param tradeId
	 * @return
	 */
	public void addEffectiveDateTime(Long tradeId);

	/**
	 * 用于定时任务的交易查询，确保线程安全
	 * 
	 * @param queryTradeOrder
	 * @param pageParam
	 * @return
	 */
	public Page<Trade> pageQueryTradeForJob(QueryTradeOrder queryTradeOrder,
			PageParam pageParam);

	/**
	 * 执行定时查询到期交易处理任务
	 */
	public void executeOverDueLoanDemandProcessJob();

	/**
	 * 按条件查询交易
	 * 
	 * @param newCondition
	 * @return
	 */
	public List<Trade> getTradesByCondition(Map<String, Object> newCondition);

	/**
	 * 更新分润机构的转账金额
	 * 
	 * @param roleId
	 * @param id
	 * @param anount
	 */
	public void updateTradeDetailForOrg(int roleId, long tradeId, double anount);

	/**
	 * 经纪人交易查询
	 * 
	 * @param queryConditions
	 * @param pageParam
	 * @return
	 */
	public Page<AgentInvestorTrade> pageQueryBrokerInvestorTrade(
			QueryTradeOrder queryTradeOrder, PageParam pageParam);

	/**
	 * 获取tradeDetails
	 * 
	 * @param conditions
	 * @return
	 */
	public List<TradeQueryDetail> getTradeDetailByConditions(
			Map<String, Object> conditions);

	/**
	 * 担保机构交易
	 * 
	 * @param queryConditions
	 * @param pageParam
	 * @return
	 */
	public Page<AgentLoanerTrade> pageQueryAgencyLoanerTrade(
			QueryTradeOrder queryConditions, PageParam pageParam);

	/**
	 * 查询经纪下投资人投资金额
	 * 
	 * @param queryConditions
	 * @return
	 */
	public long queryAllAmount(QueryTradeOrder queryTradeOrder);

	/**
	 * 到期还款提前通知接口
	 */
	public void executeRepayAdvanceNotificationJob() throws Exception;

	/**
	 * 根据tradeId获取分润信息
	 * 
	 * @param id
	 * @return
	 */
	public List<TradeDivisionDetailVO> getDevisionDetailsByTradeId(long tradeId)
			throws Exception;

	/**
	 * 获取交易amount flow 转账流水
	 * 
	 * @param tradeId
	 * @return
	 */
	public List<AmounFlowVO> getTradeFlowByTradeId(long tradeId)
			throws Exception;

	/**
	 * 获取分润flow流水
	 * 
	 * @param tradeId
	 * @return
	 */
	public List<AmounFlowVO> getDivisionFlowByTradeId(long tradeId)
			throws Exception;

	/**
	 * 根据交易ID获取投资条目
	 * 
	 * @param tradeId
	 *            ， detailID
	 * @return
	 */
	public List<UserInvestEntry> getEntriesByTradeIdAndDetailId(long tradeId,
			long detailId);

	/**
	 * 到期还款转入待还款状态任务
	 */
	public void executeAutoChangeExpireLoanJob();

	/**
	 * 融资确认后创建相关联的交易
	 * 
	 * @param trade
	 */
	public void createRelativeTrades(Trade trade) throws Exception;

	/**
	 * 加锁查询trade
	 * 
	 * @param tradeId
	 * @return
	 */
	public Trade getByTradeIdWithRowLock(long tradeId);

	/**
	 * 添加充值记录
	 * 
	 * @param rechargeFlow
	 */
	public long addRechargeFlow(RechargeFlow rechargeFlow) throws Exception;

	/**
	 * 查询充值记录
	 * 
	 * @param payType
	 * @param status
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<RechargeFlow> getFlow(String payType, int status,
			Date startTime, Date endTime);

	/**
	 * 根据流水号查询
	 * 
	 * @param outBizNo
	 * @return
	 */
	public RechargeFlow queryByOutBizNo(String outBizNo);

	/**
	 * 更新状态
	 * 
	 * @param rechargeFlow
	 * @return
	 */
	public int updateStatus(RechargeFlow rechargeFlow);

	/**
	 * 统计用户的充值、提现金额
	 * 
	 * @param rechercheFlowOrder
	 * @return
	 */
	public double getUserSumAmount(RechercheFlowOrder rechercheFlowOrder);

	/**
	 * 充值流水page
	 * 
	 * @param queryTradeOrder
	 * @param pageParam
	 * @return
	 */
	public Page<RechargeFlow> getFlow(QueryTradeOrder queryTradeOrder,
			PageParam pageParam);

	/**
	 * 投资人数统计
	 * 
	 * @param tradeId
	 * @return
	 */
	public long countInvestedTransactions(long tradeId);

	/**
	 * 投资编号记录
	 * 
	 * @param tradeId
	 * @return
	 */
	public void addTradeFlowCode(TradeFlowCode tradeFlow);

	/**
	 * 所有投资流水号
	 * 
	 * @param tradeId
	 * @return
	 */
	public List<String> queryInvestFlowCodesByTradeId(long tradeId);

	/**
	 * 根据交易详情ID查询TradeFlowCode
	 * 
	 * @param tradeDetailId
	 * @return
	 */
	public TradeFlowCode queryFlowCodeByDetailId(long tradeDetailId);

	/**
	 * 单笔投资流水号
	 * 
	 * @param detailId
	 * @return
	 */
	public TradeFlowCode queryInvestFlowCodesByTradeDetailId(long detailId);

	/**
	 * 根据投资ID查询分润交易
	 * 
	 * @param detailId
	 * @return
	 */
	public List<TradeDetail> getInvestProfitTrade(long detailId);

	/**
	 * 新增流水号
	 * 
	 * @param guaranteeId
	 * @param tradeId
	 * @throws Exception
	 */
	public void addGuaranteeTradeFlowCode(long guaranteeId, long tradeId)
			throws Exception;

	/**
	 * 通知用户未满标，退回冻结金额
	 * 
	 * @param trade
	 * @throws Exception
	 */
	public void denyloanDemandUnfull(Trade trade) throws Exception;

	/**
	 * 修订还款时间
	 * 
	 * @param expireDate
	 */
	public void updateTradeExpireDate(Date expireDate, long tradeId);

	/**
	 * 分润信息分页显示
	 * 
	 * @param queryTradeOrder
	 * @param pageParam
	 * @param tradeId
	 * @return
	 */
	public Page<TradeDivisionDetailVO> getDevisionDetailsPage(
			Map<String, Object> condtions, PageParam pageParam)
			throws Exception;

	/**
	 * 投资还款转账流水
	 * 
	 * @param conditions
	 * @param pageParam
	 * @param tradeId
	 * @return
	 */
	public Page<AmounFlowVO> getTradeFlowPageByParams(
			Map<String, Object> conditions, PageParam pageParam)
			throws Exception;

	/**
	 * 分润还款转账流水
	 * 
	 * @param conditions
	 * @param pageParam
	 * @param tradeId
	 * @return
	 */
	public Page<AmounFlowVO> getDivisionTradeFlowPageByParams(
			Map<String, Object> conditions, PageParam pageParam)
			throws Exception;

	/**
	 * 更新合同号
	 * 
	 * @param guaranteeId
	 * @param tradeId
	 * @param tradeFlow
	 */
	public void updateGuaranteeTradeFlowCode(long guaranteeId, long tradeId,
			TradeFlowCode tradeFlow) throws Exception;

	/**
	 * 交易撤销
	 * 
	 * @param tradeId
	 * @return
	 */
	public long offLineTrade(long tradeId) throws Exception;

	/**
	 * 通过借款Id获取交易
	 * 
	 * @param demandId
	 * @return
	 */
	public Trade getByDemandId(long demandId);

	/**
	 * 查詢已收款金額
	 * 
	 * @param userId
	 * @return
	 */
	public Long sumPaidDivisionAmount(Long userId);

	/**
	 * 统计金额
	 * 
	 * @param params
	 * @return
	 */
	public long countAmountByParams(Map<String, Object> params);

	/**
	 * 统计收益金额
	 * 
	 * @param params
	 * @return
	 */
	public long countInterestAmountByParams(Map<String, Object> params);

	/**
	 * 查询某个用户的对某融资需求的投资次数，（参数demandId，tradeId必须任填一项）
	 * 
	 * @param userBaseId
	 * @param demandId
	 * @param tradeId
	 * @return
	 * @throws Exception
	 */
	long countInvestTimes(String userBaseId, Long demandId, Long tradeId)
			throws Exception;

	/**
	 * 收款信息
	 * 
	 * @param condtions
	 * @param pageParam
	 * @return
	 * @throws Exception
	 */
	public Page<TradeQueryDetail> queryCollectionPage(
			Map<String, Object> condtions, PageParam pageParam)
			throws Exception;

	public void addRepayPlan(long tradeId) throws Exception;

	CalculateLoanCostResult calculateLoanCost(
			CalculateLoanCostOrder loanCostOrder);

	public int repayNotify(RepayNotify notify) throws Exception;

}
