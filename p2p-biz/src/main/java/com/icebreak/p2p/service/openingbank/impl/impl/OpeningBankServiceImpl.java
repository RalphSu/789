package com.icebreak.p2p.service.openingbank.impl.impl;

import com.icebreak.p2p.dal.daointerface.CommonBranchInfoExtendDAO;
import com.icebreak.p2p.dal.daointerface.CommonCardBinDAO;
import com.icebreak.p2p.dal.daointerface.CommonDistrictDAO;
import com.icebreak.p2p.dal.dataobject.CommonBranchInfoExtendDO;
import com.icebreak.p2p.dal.dataobject.CommonCardBinDO;
import com.icebreak.p2p.dal.dataobject.CommonDistrictDO;
import com.icebreak.p2p.service.openingbank.api.OpeningBankService;
import com.icebreak.p2p.service.openingbank.enums.CardTypeEnum;
import com.icebreak.p2p.service.openingbank.exception.SettleException;
import com.icebreak.p2p.service.openingbank.impl.convert.OpeningBankConvert;
import com.icebreak.p2p.service.openingbank.info.BankInfo;
import com.icebreak.p2p.service.openingbank.info.CardInfo;
import com.icebreak.p2p.service.openingbank.order.OpeningBankByDisNameAndBankIdOrder;
import com.icebreak.p2p.service.openingbank.order.OpeningBankQueryByDistrictOrder;
import com.icebreak.p2p.service.openingbank.order.OpeningBankQueryOrder;
import com.icebreak.p2p.service.openingbank.result.BankIdRestult;
import com.icebreak.p2p.service.openingbank.result.BankInfoResult;
import com.icebreak.p2p.service.openingbank.result.BankInfosResult;
import com.icebreak.p2p.ws.enums.SettleResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service("openingBankService")
public class OpeningBankServiceImpl implements OpeningBankService {
	/**
	 * 卡binDAO
	 */
	@Autowired
	private CommonCardBinDAO			commonCardBinDAO;
	/**
	 * 联行号dao
	 */
	@Autowired
	private CommonBranchInfoExtendDAO	commonBranchInfoExtendDAO;
	/**
	 * 地区信息dao
	 */
	@Autowired
	private CommonDistrictDAO			commonDistrictDAO;
	/** 普通日志 */
	protected final Logger				logger				= LoggerFactory.getLogger(getClass());
	protected static BankInfoResult		allBankInfoResult	= null;
	
	@Override
	public BankInfoResult findBankInfosByCardNo(OpeningBankQueryOrder order) {
		logger.info("开始查询开户行信息，查询条件为：cardNumber" + order.getCardNumber());
		String cardNumber = order.getCardNumber();
		BankInfoResult result = new BankInfoResult();
		try {
			CommonCardBinDO ccDO = this.getBankId(cardNumber);
			if (ccDO != null) {//查询到合适的卡bin
				String bankId = ccDO.getBankId();
				List<CommonBranchInfoExtendDO> cbieDO = commonBranchInfoExtendDAO
					.findBankInfosByBankId(bankId);
				//查询得到所有的机构信息
				List<CommonDistrictDO> cdDOs = commonDistrictDAO.getAll();
				//组建一颗树，在树的上部节点上可能缺失，
				BankInfo opBank = OpeningBankConvert.creatTree(cbieDO, cdDOs);
				
				//将卡信息插入result中
				CardInfo cardInfo = new CardInfo();
				OpeningBankConvert.cardBinDOToCardInfo(ccDO, cardInfo);
				result.setCardInfo(cardInfo);
				result.setBankInfo(opBank);
				result.setResultCode(SettleResultEnum.EXECUTE_SUCCESS);
				result.setMessage(SettleResultEnum.EXECUTE_SUCCESS.getMessage());
				result.setSuccess(true);
			} else {
				throw new SettleException(SettleResultEnum.DATABASE_DATE_NULL);
			}
		} catch (SettleException ex) {
			result.setBankInfo(null);
			result.setResultCode(ex.getCode());
			result.setMessage(ex.getCode().getMessage());
			result.setSuccess(false);
			logger.error("返回结果：" + result.toString(), ex);
		} catch (Exception ex) {
			result.setBankInfo(null);
			result.setResultCode(SettleResultEnum.UN_KNOWN_EXCEPTION);
			result.setMessage(SettleResultEnum.UN_KNOWN_EXCEPTION.getMessage());
			result.setSuccess(false);
			logger.error("返回结果：" + result.toString(), ex);
		}
		return result;
	}
	
	@Override
	public BankInfosResult findBankInfoByDistrictNo(OpeningBankQueryByDistrictOrder order) {
		logger.info("开始查询开户行信息，查询条件为：" + order.toString());
		String bankId = order.getBankId();
		String districtNo = order.getDistrictNo();
		BankInfosResult result = new BankInfosResult();
		try {
			CommonDistrictDO cdDO = commonDistrictDAO.getByDistrictNo(districtNo);//地区信息
			if (cdDO == null) {//当前地区查询不到，地区信息必须是有的，如果没有认定非法
				throw new SettleException(SettleResultEnum.DATABASE_DATE_ERROR);
			}
			//查询地区信息的父级节点信息
			CommonDistrictDO fatherCdDO = commonDistrictDAO.getByDistrictNo(cdDO.getFatherNo());
			List<CommonBranchInfoExtendDO> cbieDOs = commonBranchInfoExtendDAO
				.findBankInfosByDistrict(bankId, districtNo);//开户行列表
			if (cbieDOs == null || cbieDOs.isEmpty()) {//当前地区查询不到开户行
				throw new SettleException(SettleResultEnum.DATABASE_DATE_NULL);
			}
			List<BankInfo> bankInfos = OpeningBankConvert.voluationBankInfoList(cbieDOs, cdDO,
				fatherCdDO);
			result.setBankInfos(bankInfos);
			result.setSuccess(true);
			result.setResultCode(SettleResultEnum.EXECUTE_SUCCESS);
			result.setMessage(SettleResultEnum.EXECUTE_SUCCESS.getMessage());
		} catch (SettleException ex) {
			result.setResultCode(ex.getCode());
			result.setMessage(ex.getCode().getMessage());
			result.setSuccess(false);
			logger.error("返回结果：" + result.toString(), ex);
		} catch (Exception ex) {
			result.setResultCode(SettleResultEnum.UN_KNOWN_EXCEPTION);
			result.setMessage(SettleResultEnum.UN_KNOWN_EXCEPTION.getMessage());
			result.setSuccess(false);
			logger.error("返回结果：" + result.toString(), ex);
		}
		return result;
	}
	
	@Override
	public BankInfoResult getAllDistrict() {
		logger.info("开始查询地区信息信息：");
		if (allBankInfoResult == null) {
			synchronized (this) {
				if (allBankInfoResult == null) {
					BankInfoResult result = new BankInfoResult();
					try {
						List<CommonDistrictDO> cdDOs = commonDistrictDAO.getAll();
						BankInfo district = OpeningBankConvert.creatUnit(cdDOs,
							new HashMap<String, List<CommonBranchInfoExtendDO>>());
						result.setBankInfo(district);
						result.setProvinceInfos(district.getProvinceInfos());
						result.setSuccess(true);
						result.setResultCode(SettleResultEnum.EXECUTE_SUCCESS);
						result.setMessage(SettleResultEnum.EXECUTE_SUCCESS.getMessage());
					} catch (SettleException ex) {
						result.setResultCode(ex.getCode());
						result.setMessage(ex.getCode().getMessage());
						result.setSuccess(false);
						logger.error("返回结果：" + result.toString(), ex);
					} catch (Exception ex) {
						result.setResultCode(SettleResultEnum.UN_KNOWN_EXCEPTION);
						result.setMessage(SettleResultEnum.UN_KNOWN_EXCEPTION.getMessage());
						result.setSuccess(false);
						logger.error("返回结果：" + result.toString(), ex);
					}
					allBankInfoResult = result;
				}
			}
		}
		return allBankInfoResult;
	}
	
	@Override
	public BankIdRestult getBankIdByCardNo(OpeningBankQueryOrder order) {
		logger.info("开始查询开户行信息，查询条件为：districtId：" + order.toString());
		BankIdRestult result = new BankIdRestult();
		try {
			CommonCardBinDO ccDO = this.getBankId(order.getCardNumber());
			if (ccDO != null) {//查询到合适的卡bin
				result.setBankId(ccDO.getBankId());
			}
			result.setSuccess(true);
			result.setResultCode(SettleResultEnum.EXECUTE_SUCCESS);
			result.setMessage(SettleResultEnum.EXECUTE_SUCCESS.getMessage());
		} catch (SettleException ex) {
			result.setResultCode(ex.getCode());
			result.setMessage(ex.getCode().getMessage());
			result.setSuccess(false);
			logger.error("返回结果：" + result.toString(), ex);
		} catch (Exception ex) {
			result.setResultCode(SettleResultEnum.UN_KNOWN_EXCEPTION);
			result.setMessage(SettleResultEnum.UN_KNOWN_EXCEPTION.getMessage());
			result.setSuccess(false);
			logger.error("返回结果：" + result.toString(), ex);
		}
		return result;
	}
	
	@Override
	public BankInfoResult getOpeningBankByDistrictNameAndBankId(OpeningBankByDisNameAndBankIdOrder order) {
		logger.info("开始根据银行地区名称和银行ID查询开户行的联行号，查询条件为：order：" + order);
		BankInfoResult result = new BankInfoResult();
		try {
			BankInfo bankInfo = null;
			String dName = order.getDistrictName();
			String bankId = order.getBankId();
			//获取传入地区
			CommonDistrictDO disDO = commonDistrictDAO.getByDistrictName(dName);
			if (disDO == null) {
				throw new SettleException(SettleResultEnum.DATABASE_DATE_NULL);
			}
			//获取传入地区银行的分行DO
			List<CommonBranchInfoExtendDO> cbieDOs = commonBranchInfoExtendDAO
				.findOpeningBankByDistrict(bankId, disDO.getNbNo(), "Y");
			if (cbieDOs == null || cbieDOs.isEmpty()) { //如果为null再次尝试获取
				cbieDOs = commonBranchInfoExtendDAO
					.findBankInfosByDistrict(bankId, disDO.getNbNo());
			}
			if (cbieDOs == null || cbieDOs.isEmpty()) {
				throw new SettleException(SettleResultEnum.NO_USED_BANK_CNAPS);
			}
			bankInfo = OpeningBankConvert.voluation(disDO, cbieDOs.get(0));
			//处理省信息
			if (disDO.getFatherNo() == null || disDO.getFatherNo().equals("001")
				|| disDO.getFatherNo().equals("-1")) {//如果为，根节点、中国、直辖市、特别行政区
				bankInfo.setFatherName(bankInfo.getAreaName());
			} else {//获取省信息
				CommonDistrictDO disFatherDO = commonDistrictDAO.getByDistrictNo(disDO
					.getFatherNo());
				bankInfo.setFatherName(disFatherDO.getAreaName());
			}
			
			result.setBankInfo(bankInfo);
			result.setSuccess(true);
			result.setResultCode(SettleResultEnum.EXECUTE_SUCCESS);
			result.setMessage(SettleResultEnum.EXECUTE_SUCCESS.getMessage());
		} catch (SettleException ex) {
			result.setResultCode(ex.getCode());
			result.setMessage(ex.getCode().getMessage());
			result.setSuccess(false);
			logger.error("返回结果：" + result, ex);
		} catch (Exception ex) {
			result.setResultCode(SettleResultEnum.UN_KNOWN_EXCEPTION);
			result.setMessage(SettleResultEnum.UN_KNOWN_EXCEPTION.getMessage());
			result.setSuccess(false);
			logger.error("返回结果：" + result, ex);
		}
		return result;
	}
	
	/**
	 * 通过银行卡号得到银行DO实体
	 * @param cardNumber
	 * @return
	 */
	private CommonCardBinDO getBankId(String cardNumber) {
		List<Integer> flagLens = commonCardBinDAO.getAllFlagLen();
		CommonCardBinDO ccDO = null;
		for (Integer flagLen : flagLens) {
			if (cardNumber.length() < flagLen)
				continue;
			String thisCard = cardNumber.substring(0, flagLen);
			List<CommonCardBinDO> cbs = commonCardBinDAO.findByCardFlag(thisCard);
			if (cbs != null && cbs.size() == 1) {
				ccDO = cbs.get(0);
				//校验卡号长度是否一致
				if (cardNumber.length() == ccDO.getCardLength()) {
					break;
				} else {
					ccDO = null;
				}
			}
		}
		return ccDO;
	}
	
	@Override
	public BankInfoResult findCardByCardNo(OpeningBankQueryOrder order) {
		logger.info("通过银行卡号查询该卡信息：cardNumber" + order.getCardNumber());
		String cardNumber = order.getCardNumber();
		BankInfoResult result = new BankInfoResult();
		try {
			CommonCardBinDO ccDO = this.getBankId(cardNumber);
			if (ccDO != null) {//查询到合适的卡bin
				//将卡信息插入result中
				CardInfo cardInfo = new CardInfo();
				OpeningBankConvert.cardBinDOToCardInfo(ccDO, cardInfo);
				if (order.getCardType() != null) {
					if (order.getCardType() == cardInfo.getCardType()) {
						result.setCardInfo(cardInfo);
					}
				} else {
					result.setCardInfo(cardInfo);
				}
				if (result.getCardInfo() != null) {
					result.setResultCode(SettleResultEnum.EXECUTE_SUCCESS);
					result.setMessage(SettleResultEnum.EXECUTE_SUCCESS.getMessage());
					result.setSuccess(true);
				}
			} else {
				throw new SettleException(SettleResultEnum.DATABASE_DATE_NULL);
			}
		} catch (SettleException ex) {
			result.setBankInfo(null);
			result.setResultCode(ex.getCode());
			result.setMessage(ex.getCode().getMessage());
			result.setSuccess(false);
			logger.error("返回结果：" + result.toString());
		} catch (Exception ex) {
			result.setBankInfo(null);
			result.setResultCode(SettleResultEnum.UN_KNOWN_EXCEPTION);
			result.setMessage(SettleResultEnum.UN_KNOWN_EXCEPTION.getMessage());
			result.setSuccess(false);
			logger.error("返回结果：" + result.toString(), ex);
		}
		return result;
	}

	public BankInfoResult findBankInfoByBankInfo(OpeningBankQueryOrder order) {
		BankInfoResult result = new BankInfoResult();
		try {
			CommonCardBinDO cardBinDO = commonCardBinDAO.findByCardNo(order.getCardNumber(), order.getBankCode());
			if(null != cardBinDO) {
				CardInfo cardInfo = new CardInfo();
				cardInfo.setBankId(cardBinDO.getBankId());
				cardInfo.setBankName(cardBinDO.getBankName());
				cardInfo.setCardName(cardBinDO.getCardName());
				cardInfo.setCardType(CardTypeEnum.getByCode(cardBinDO.getCardType()));
				result.setCardInfo(cardInfo);
				result.setSuccess(true);
				result.setResultCode(SettleResultEnum.EXECUTE_SUCCESS);
				return result;
			}
			result.setSuccess(false);
			result.setMessage("卡号与银行不匹配");
		} catch (Exception ex) {
			result.setResultCode(SettleResultEnum.SYSTEM_EXCEPTION);
			result.setMessage(SettleResultEnum.SYSTEM_EXCEPTION.getMessage());
			result.setSuccess(false);
			logger.error("返回结果：" + result.toString(), ex);
		}
		return result;
	}

	@Override
    public void clearCache() {
        allBankInfoResult = null;
    }
}
