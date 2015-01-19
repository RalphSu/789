package com.icebreak.p2p.base.data.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.icebreak.p2p.base.covert.BankInfoConvertor;
import com.icebreak.p2p.base.data.BankDataService;
import com.icebreak.p2p.dal.daointerface.BankBaseInfoDAO;
import com.icebreak.p2p.dal.dataobject.BankBaseInfoDO;
import com.icebreak.p2p.util.MiscUtil;
import com.icebreak.p2p.ws.base.info.BankBasicInfo;
import com.icebreak.p2p.ws.base.info.BankConfigInfo;
import com.icebreak.util.lang.util.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service("bankDataService")
public class BankDataServiceImpl implements BankDataService {
	
	protected Logger								logger			= LoggerFactory
																		.getLogger(getClass());
	protected static Map<String, BankConfigInfo>	bankConfigCache	= new HashMap<String, BankConfigInfo>();
	
	@Autowired
	BankBaseInfoDAO									bankBaseInfoDAO;
	
	@Override
	public List<BankConfigInfo> loadAllBankConfigInfo() {
		List<BankConfigInfo> bankList = new ArrayList<BankConfigInfo>();
		try {
			List<BankBaseInfoDO> bankConfigList = bankBaseInfoDAO.findAllBankConfig();
			
			if (bankConfigList != null) {
				MiscUtil.convertList(bankConfigList, bankList, BankConfigInfo.class);
			}
		} catch (DataAccessException e) {
			logger.error("加载赎楼银行贷款银行信息失败:{}", e.getMessage(), e);
		} catch (Exception e) {
			logger.error("加载赎楼银行贷款银行信息失败:{}", e.getMessage(), e);
		}
		return bankList;
	}
	
	@Override
	public BankConfigInfo loadBankConfigInfo(String bankCode) {
		BankConfigInfo bankConfigInfo = bankConfigCache.get(bankCode);
		if (bankConfigInfo == null) {
			synchronized (bankConfigCache) {
				bankConfigInfo = bankConfigCache.get(bankCode);
				if (bankConfigInfo == null) {
					List<BankBaseInfoDO> bankConfigList = bankBaseInfoDAO.findAllBankConfig();
					for (BankBaseInfoDO estateBankConfig : bankConfigList) {
						bankConfigInfo = new BankConfigInfo();
						MiscUtil.copyPoObject(bankConfigInfo, estateBankConfig);
						bankConfigCache.put(bankConfigInfo.getBankCode(), bankConfigInfo);
					}
					bankConfigInfo = bankConfigCache.get(bankCode);
				}
			}
		}
		return bankConfigInfo;
	}
	
	@Override
	public void clearCache() {
		synchronized (bankConfigCache) {
			bankConfigCache.clear();
		}
	}
	
	@Override
	public List<BankConfigInfo> loadAllBankConfigInfoIgnoredStatus() {
		
		List<BankConfigInfo> bankList = new ArrayList<BankConfigInfo>();
		try {
			List<BankBaseInfoDO> bankConfigList = bankBaseInfoDAO.findAllBankConfigIgnoredStatus();
			if (bankConfigList != null) {
				MiscUtil.convertList(bankConfigList, bankList, BankConfigInfo.class);
			}
		} catch (DataAccessException e) {
			logger.error("加载赎楼银行贷款银行信息失败:{}", e.getMessage(), e);
		} catch (Exception e) {
			logger.error("加载赎楼银行贷款银行信息失败:{}", e.getMessage(), e);
		}
		return bankList;
	}
	
	/**
	 * @return
	 * @see com.icebreak.p2p.base.data.BankDataService#getDeductBank()
	 */
	@Override
	public List<BankBasicInfo> getDeductBank() {
		List<BankBaseInfoDO> bankConfigList = bankBaseInfoDAO.findAllBankConfig();
		List<BankBasicInfo> bankBasicInfos = new ArrayList<BankBasicInfo>();
		Money money1W = new Money(10000);
		for (BankBaseInfoDO baseInfoDO : bankConfigList) {
			if (baseInfoDO.getWithholdingAmount().greaterThan(money1W)) {
				BankBasicInfo basicInfo = new BankBasicInfo();
				BankInfoConvertor.BankInfoDOConvertInfo(baseInfoDO, basicInfo);
				bankBasicInfos.add(basicInfo);
			}
		}
		return bankBasicInfos;
	}
	
	@Override
	public List<BankBasicInfo> getBankBasicInfos() {
		List<BankBaseInfoDO> bankConfigList = bankBaseInfoDAO.findAllBankConfig();
		List<BankBasicInfo> bankBasicInfos = new ArrayList<BankBasicInfo>();
		for (BankBaseInfoDO baseInfoDO : bankConfigList) {
			BankBasicInfo basicInfo = new BankBasicInfo();
			BankInfoConvertor.BankInfoDOConvertInfo(baseInfoDO, basicInfo);
			bankBasicInfos.add(basicInfo);
		}
		return bankBasicInfos;
	}
}
