package com.icebreak.p2p.loandemand;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.icebreak.p2p.dataobject.LoanAuthRecord;
import com.icebreak.p2p.dataobject.LoanDemandDO;
import com.icebreak.p2p.dataobject.Trade;
import com.icebreak.p2p.dataobject.TradeQueryDetail;
import com.icebreak.p2p.dataobject.viewObject.LoanDemandTradeVO;
import com.icebreak.p2p.loandemand.result.LoanDemandResultEnum;
import com.icebreak.p2p.loandemand.valueobject.QueryConditions;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.trade.QueryTradeOrder;
import com.icebreak.p2p.ws.result.AddLoanDemandResult;
import com.icebreak.p2p.ext.domain.ResultBase;

public interface LoanDemandManager {
	/***
	 * 发布借款需求
	 * 
	 * @param loanDemandDO
	 * @return 
	 * @throws Exception
	 */
	public AddLoanDemandResult addLoanDemand(LoanDemandDO loanDemandDO) throws Exception;
	
	public long checkNO(String licenseNo, String id) throws Exception;
	
	/***
	 * 修改借款需求
	 * 
	 * @param loanDemandDO
	 * @return 
	 * @throws Exception
	 */
	public LoanDemandResultEnum updateLoanDemand(LoanDemandDO loanDemandDO) throws Exception;
	
	/***
	 * 分页查询借款需求
	 * @param queryConditions
	 * @param pageParam
	 * @return
	 * @throws Exception
	 */
	public Page<LoanDemandDO> pageQueryLoanDemand(QueryConditions queryConditions,
													PageParam pageParam) throws Exception;
	
	/**
	 * 根据条件统计项目数量
	 * @param queryConditions
	 * @return
	 */
	public long getWiteCountsLoandeamnd(String status);
	
	/**
	 * 分页查询下线需求
	 * @param queryConditions
	 * @param pageParam
	 * @return
	 * @throws Exception
	 */
	public Page<LoanDemandDO> pageQueryOfflineLoanDemand(QueryConditions queryConditions,
															PageParam pageParam) throws Exception;
	
	/**
	 * 根据条件查询,获得借款需求
	 * 
	 * @param demandId
	 * @return 
	 * @throws DataAccessException
	 * */
	public LoanDemandDO queryLoanDemandByDemandId(long demandId) throws Exception;
	
	/**
	 * 审核借款需求
	 * 
	 * @param demandId
	 * @param publishDate
	 * @param demandId
	 * @return 
	 * @throws DataAccessException
	 * */
	public int passInDismiss(long demandId, String status, String publishDate, String dismissReason)
																									throws Exception;
	
	/**
	 * 定时任务执行定时发布需求
	 * @return
	 */
	public void executeOnTimePublishLoanDemandJob() throws Exception;
	
	/**
	 * 查询担保机构下的借款
	 * @param queryConditions
	 * @param pageParam
	 * @return
	 */
	public Page<LoanDemandTradeVO> pageQueryTradeForGuarantee(QueryTradeOrder queryConditions,
																PageParam pageParam);
	
	/**
	 * 获取tradeDetails
	 * @param conditions
	 * @return
	 */
	public List<TradeQueryDetail> getTradeDetailByConditions(Map<String, Object> conditions);
	
	/**
	 * 更新上传图片地址
	 * @param id
	 * @param newUrl
	 * @return
	 */
	public int updateFileUploadUrlById(long id, String newUrl, long loanAmount, String audit);
	
	/**
	  * 新增借款审核记录
	   * @param LoanAuthRecord
	   */
	public void addLoanAuthRecord(LoanAuthRecord record) throws DataAccessException;
	
	/**
	 * 根据map信息查询是否有该条记录
	 * @return
	 */
	public long countLoanAuthRecordByCondition(Map<String, Object> conditions);
	
	/**
	 * 查询担保机构下可以审核的借款
	 * @param queryConditions
	 * @param pageParam
	 * @return
	 */
	public Page<LoanDemandTradeVO> pageQueryTradeForGuaranteeAuth(QueryTradeOrder queryConditions,
																	PageParam pageParam);
	
	/**
	 * 审核记录查询
	 * @param conditions
	 * @return
	 */
	public List<LoanAuthRecord> getLoanAuthRecordByCondition(Map<String, Object> conditions);
	
	/**
	 * 交易上线
	 * @param loanId
	 */
	public void onlineTrade(long loanId) throws Exception;
	
	/**
	 * 根据条件统计成立项目
	* @return
	*/
	public long getEstablishCounts(QueryTradeOrder queryOrder);
	
	/**
	 * 根据条件查询项目成立表
	 * @param queryOrder
	 * @param pageParam
	 * @return
	 */
	public Page<LoanDemandDO> pageEstablish(QueryTradeOrder queryOrder, PageParam pageParam);
	
	/**
	 * 查询待还款项目
	 * @param queryOrder
	 * @param pageParam
	 * @return
	 */
	public Page<Trade> pageWaitRepayLoan(QueryTradeOrder queryOrder, PageParam pageParam);
	
	/**
	 * 根据状态统计项目金额
	 * @param queryOrder
	 * @return
	 */
	public long getAmountsByStatus(QueryTradeOrder queryOrder);
	
	/**
	 * 业务规则验证
	 * @param loanDemandDO
	 * @return
	 */
	public ResultBase checkRules(LoanDemandDO loanDemandDO);

	/**
	 * 根据ID删除
	 * @param demandId
	 */
	public void delete(long demandId);
	
}
