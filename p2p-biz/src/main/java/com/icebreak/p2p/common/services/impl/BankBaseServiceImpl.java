package com.icebreak.p2p.common.services.impl;

import com.icebreak.p2p.common.services.BankBaseService;
import com.icebreak.p2p.daointerface.BankInfoDao;
import com.icebreak.p2p.daointerface.TradeDao;
import com.icebreak.p2p.dataobject.BankInfo;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.util.CommonUtil;
import com.icebreak.p2p.ws.enums.PayTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.*;

public class BankBaseServiceImpl implements BankBaseService {
	@Autowired
	TradeDao			tradeDao;
	private final int	START	= 0;
	private final int	SIZE	= 10;
	private BankInfoDao	bankInfoDao;
	
	public BankInfoDao getBankInfoDao() {
		return bankInfoDao;
	}
	
	public void setBankInfoDao(BankInfoDao bankInfoDao) {
		this.bankInfoDao = bankInfoDao;
	}
	
	@Override
	public void addBank(BankInfo bank) {

		bankInfoDao.insert(bank);
		
	}
	
	@Override
	public int updateBank(BankInfo bank) {

		return bankInfoDao.update(bank);
	}
	
	@Override
	public List<BankInfo> queryByConditions(BankInfo bankInfo, PageParam pageParam) {

		Map<String, Object> conditions = this.setQueryBankInfoPar(bankInfo);
		if (pageParam != null) {
			conditions.put("limitStart", (pageParam.getPageNo() - 1) * pageParam.getPageSize());
			conditions.put("pageSize", pageParam.getPageSize());
		} else {
			conditions.put("limitStart", START);
			conditions.put("pageSize", SIZE);
		}
		return bankInfoDao.getBankListByCondtions(conditions);
	}
	
	@Override
	public long queryCountByConditions(BankInfo bankInfo) {
		Map<String, Object> conditions = this.setQueryBankInfoPar(bankInfo);
		return bankInfoDao.getBankCountByCondtions(conditions);
	}
	
	@Override
	public int deleteBankInfoById(long bankId) {

		return bankInfoDao.deleteById(bankId);
	}
	
	/**
	 * 设置查询参数
	 * @param bankInfo
	 * @return
	 */
	public Map<String, Object> setQueryBankInfoPar(BankInfo bankInfo) {
		Map<String, Object> conditions = new HashMap<String, Object>();
		if (bankInfo.getBankCode() != null && !"".equals(bankInfo.getBankCode())) {
			conditions.put("bankCode", bankInfo.getBankCode());
		}
		if (bankInfo.getBankId() != 0) {
			conditions.put("bankId", bankInfo.getBankId());
		}
		if (bankInfo.getBankName() != null && !"".equals(bankInfo.getBankName())) {
			conditions.put("bankName", bankInfo.getBankName());
		}
		if (bankInfo.getDeduct() != null && !"".equals(bankInfo.getDeduct())) {
			conditions.put("deduct", bankInfo.getDeduct());
		}
		if (bankInfo.getSynChannel() != null && !"".equals(bankInfo.getSynChannel())) {
			conditions.put("synChannel", bankInfo.getSynChannel());
		}
		if (bankInfo.getWithdrawal() != null && !"".equals(bankInfo.getWithdrawal())) {
			conditions.put("withdrawal", bankInfo.getWithdrawal());
		}
		return conditions;
	}
	
	@Override
	public BankInfo query19BankInfo(String bankCode) {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("bankCode", bankCode);
		conditions.put("limitStart", START);
		conditions.put("pageSize", SIZE);
		List<BankInfo> lst = bankInfoDao.getBankListByCondtions(conditions);
		BankInfo bankInfo = null;
		if (lst != null) {
			bankInfo = lst.get(0);
		}
		return bankInfo;
	}
	
	@Override
	public double getDifference(BankInfo bankInfo, String bankCardNo, String payType) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String strDate = format.format(date);
		long userId = SessionLocalManager.getSessionLocal().getUserId();
		List<Integer> status = new ArrayList<Integer>();
		status.add(1);//成功
		status.add(2);//处理中
		double limit = 0.0;
		if (PayTypeEnum.DEDUCT.code().equals(payType)) {
			limit = bankInfo.getOddDeductLimit();
		} else if (PayTypeEnum.WITHDRAW.code().equals(payType)) {
			limit = bankInfo.getOddWithdrawalLimit();
		}
		
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("bankAccountNo", bankCardNo);
		condition.put("payType", payType);
		condition.put("endTime", strDate + " 23:59:59");
		condition.put("startTime", strDate + " 00:00:00");
		condition.put("status", status);
		condition.put("userId", userId);
		double lastAmount = tradeDao.getUserAmount(condition);
		double difference = CommonUtil.sub(limit, lastAmount);
		return difference;
	}
	
}
