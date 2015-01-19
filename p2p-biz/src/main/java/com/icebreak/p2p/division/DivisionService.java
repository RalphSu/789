package com.icebreak.p2p.division;

import java.util.List;

import com.icebreak.p2p.dataobject.DivisionDetail;
import com.icebreak.p2p.dataobject.DivisionTemplate;
import com.icebreak.p2p.dataobject.DivsionRuleRole;
import com.icebreak.p2p.dataobject.LoanDemandDO;
import com.icebreak.p2p.dataobject.Trade;
import com.icebreak.p2p.dataobject.TradeDetail;
import com.icebreak.p2p.dataobject.TransferTrade;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.Pagination;

public interface DivisionService {
    /**
     * 分润
     * @param tradeId
     * @throws Exception 
     */
	public void division(long tradeId) throws Exception;
	/**
	 * 添加投资
	 * @param detail
	 * @throws Exception 
	 */
	public void invest(TradeDetail detail) throws Exception;
	/**
	 * 新建交易
	 * @param demandDO
	 */
	public void createNewTrade(LoanDemandDO demandDO) throws Exception;
	/**
	 * 添加一条分润模版
	 * @param template 模版
	 * @param roleIds 角色ID
	 * @param percentages 百分比
	 */
	public void addDivisionTemplate(DivisionTemplate template, int[] roleIds, double[] percentages);
	/**
	 * 修改分润模版
	 * @param template 模版
     * @param roleIds 角色ID
	 * @param percentages 百分比
	 */
	public void modifyDivisionTemplate(DivisionTemplate template, int[] roleIds, double[] percentages);
	/**
	 * 根据分润模版ID删除一套分润模版
	 * @param templateId
	 */
	public void deleteDivisionTemplate(long templateId);
	/**
	 * 根据模版ID获取一套模版
	 * @param templateId
	 * @return
	 */
	public DivisionTemplate getByTemplateId(long templateId);
	/**
	 * 根据条件查询分润模版
	 * @param name
	 * @param status
	 * @param start
	 * @param size
	 * @return
	 */
	public Pagination<DivisionTemplate> getDivisionTemplatesByCondition(String name, String status, int start, int size);
	/**
	 * 根据就分润阶段查询分润模版
	 * @param phase 分润阶段
	 * @return
	 */
	public List<DivisionTemplate> getDivisionTemplatesByPhase(String phase,String status);
	/**
	 * 根据用户或者交易查询分润信息
	 * @param userId
	 * @param tradeId
	 * @return
	 */
	public Page<DivisionDetail> getDivisionByConditions(Long userId, Long tradeId);
	/**
	 * 判断是否满标
	 * @param trade
	 * @return
	 */
	public boolean isFullScale(Trade trade);
	/**
	 * 转账交易
	 * @param trade
	 * @throws Exception 
	 */
	public void transfer(Trade trade, long userId) throws Exception;
	/**
	 * 获取rule
	 * @param name
	 * @return
	 */
	public List<DivsionRuleRole> getRuleRole(String name);
	/**
	 * 增加审核流水
	 * @param userId
	 * @param tradeId
	 */
	public void addLoanAuthRecord(Long userId, long tradeId,  String authType) throws Exception;

    public void changeDivisionTemplateStatus(long templateId,String status);

    public boolean isUseDivisionTemplate(long templateId);

    public void clearCache();

    //投资人的利润转账
    public List<TransferTrade> addRepayInvestorDivisionAmountFlow(long tradeId,String repayYjfUserId,long repayUserId) throws Exception;
    
    public List<TransferTrade> queryRepayDivision(long tradeId) throws Exception;
}
