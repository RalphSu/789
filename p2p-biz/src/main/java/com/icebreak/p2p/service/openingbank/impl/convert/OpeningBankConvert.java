package com.icebreak.p2p.service.openingbank.impl.convert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.icebreak.p2p.dal.dataobject.CommonBranchInfoExtendDO;
import com.icebreak.p2p.dal.dataobject.CommonCardBinDO;
import com.icebreak.p2p.dal.dataobject.CommonDistrictDO;
import com.icebreak.p2p.service.openingbank.enums.CardTypeEnum;
import com.icebreak.p2p.service.openingbank.info.BankInfo;
import com.icebreak.p2p.service.openingbank.info.CardInfo;
import com.icebreak.p2p.service.openingbank.info.CityInfo;
import com.icebreak.p2p.service.openingbank.info.ProvinceInfo;



public class OpeningBankConvert {
	
	/**
	 * 将机构列表 和开户行列表，组合起来形成一个树 其根节点为一个虚拟的节点，在使用时可以忽略
	 * 
	 * @param cbieDO
	 *            开户行列表
	 * @param cdDOs
	 *            机构列表
	 */
	public static BankInfo creatTree(List<CommonBranchInfoExtendDO> cbieDOs,
										List<CommonDistrictDO> cdDOs) {
		// 将cbieDO转成MAP，KEY为其地区编号
		HashMap<String, List<CommonBranchInfoExtendDO>> branchMap = new HashMap<String, List<CommonBranchInfoExtendDO>>();
		
		for (CommonBranchInfoExtendDO cbieDO : cbieDOs) {
			List<CommonBranchInfoExtendDO> test = branchMap.get(cbieDO.getBranchDistrictNo());
			if (test == null) {
				List<CommonBranchInfoExtendDO> node = new ArrayList<CommonBranchInfoExtendDO>();
				node.add(cbieDO);
				branchMap.put(cbieDO.getBranchDistrictNo(), node);
			} else {
				test.add(cbieDO);
			}
		}
		BankInfo root = creatUnit(cdDOs, branchMap);
		return root;
	}
	
	/**
	 * 创建机构树
	 * 
	 * @param cdDOs
	 * @return
	 */
	public static BankInfo creatUnit(List<CommonDistrictDO> cdDOs,
										HashMap<String, List<CommonBranchInfoExtendDO>> branchMap) {
		HashMap<String, List<CommonDistrictDO>> unitTreeMap = new HashMap<String, List<CommonDistrictDO>>();
		List<ProvinceInfo> provinceInfos = new ArrayList<ProvinceInfo>(50);
		Map<String, ProvinceInfo> cityMap = new HashMap<String, ProvinceInfo>();
		// 获取到关于父节点索引的MAP
		for (CommonDistrictDO cdDO : cdDOs) {
			List<CommonDistrictDO> test = unitTreeMap.get(cdDO.getFatherNo());
			if (test == null) {
				List<CommonDistrictDO> node = new ArrayList<CommonDistrictDO>();
				node.add(cdDO);
				unitTreeMap.put(cdDO.getFatherNo(), node);
			} else {
				test.add(cdDO);
			}
			//中国为父节点
			if ("001".equals(cdDO.getFatherNo())) {
				ProvinceInfo pInfo = new ProvinceInfo();
				pInfo.setCityList(new ArrayList<CityInfo>());
				pInfo.setProvinceName(cdDO.getAreaName());
				provinceInfos.add(pInfo);
				cityMap.put(cdDO.getNbNo(), pInfo);
			} else {
				ProvinceInfo pInfo = cityMap.get(cdDO.getFatherNo());
				if (pInfo != null) {
					CityInfo cityInfo = new CityInfo();
					cityInfo.setCityName(cdDO.getAreaName());
					cityInfo.setBranchDistrictNo(cdDO.getNbNo());
					pInfo.getCityList().add(cityInfo);
				}
			}
		}
		
		BankInfo root = new BankInfo();
		root.setBranchDistrictNo("-1");
		root.setAreaName("所有");
		root.setProvinceInfos(provinceInfos);
		// 生成整个机构的树形结构
		creatDistrictTree(unitTreeMap, root, branchMap);
		return root;
	}
	
	/**
	 * 递归调用，将当前fatherNode的子节点初始化好
	 * 
	 * @param cbieDO
	 * @param unitTreeMap
	 * @param thisNo
	 * @param fatherNode
	 */
	private static void creatDistrictTree(	HashMap<String, List<CommonDistrictDO>> unitTreeMap,
											BankInfo fatherNode,
											HashMap<String, List<CommonBranchInfoExtendDO>> branchMap) {
		
		List<BankInfo> childs = new ArrayList<BankInfo>();
		List<CommonDistrictDO> listDos = unitTreeMap.get(fatherNode.getBranchDistrictNo());
		if (listDos != null && !listDos.isEmpty()) {// 不能为null,并且也不能为空数组集合
			for (CommonDistrictDO lDo : listDos) {
				BankInfo testBankInfo = null;
				List<CommonBranchInfoExtendDO> testOpenBanks = branchMap.get(lDo.getNbNo());
				if (testOpenBanks != null && testOpenBanks.size() == 1) {// 如果自由一个开户行信息不管是否为叶子节点，直接赋值
					testBankInfo = voluation(lDo, testOpenBanks.get(0));
				} else if (testOpenBanks != null && testOpenBanks.size() > 1) {// 如果有多个开户行信息
					List<CommonDistrictDO> testUnit = unitTreeMap.get(lDo.getNbNo());
					BankInfo cityBankInfo = null;
					if (testUnit == null || testUnit.isEmpty()) {// 叶子地区节点
						cityBankInfo = voluation(lDo, null);
					} else {// 非叶子地区节点，虚拟一个省直属开户行这个节点
						cityBankInfo = new BankInfo();
						cityBankInfo.setAreaName(lDo.getAreaName() + "-直属");
						cityBankInfo.setBranchDistrictNo(lDo.getNbNo());
						cityBankInfo.setExistNode(false);
					}
					List<BankInfo> banks = new ArrayList<BankInfo>();
					// 遍历当前地区节点相同的开户行列表，并将其转换为BankInfo，并且终止递归
					for (CommonBranchInfoExtendDO testOpenBank : testOpenBanks) {
						BankInfo testBankInfo2 = voluation(lDo, testOpenBank);
						banks.add(testBankInfo2);
					}
					cityBankInfo.setChilds(banks);
					childs.add(cityBankInfo);
				} else {
					testBankInfo = voluation(lDo, null);
				}
				if (testBankInfo != null) {
					creatDistrictTree(unitTreeMap, testBankInfo, branchMap);
					
					childs.add(testBankInfo);
				}
			}
			fatherNode.setChilds(childs);
		}
	}
	
	/**
	 * 为BankInfo 赋值，里面的集合在外面赋值
	 * 
	 * @param bankInfo
	 *            要赋值的对象
	 * @param districtDO
	 *            地区信息
	 */
	public static BankInfo voluation(CommonDistrictDO districtDO,
										CommonBranchInfoExtendDO openBankDO) {
		BankInfo bankInfo = new BankInfo();
		bankInfo.setBranchDistrictNo(districtDO.getNbNo());
		bankInfo.setBranchDistrictName(districtDO.getAreaName());
		bankInfo.setFatherNo(districtDO.getFatherNo());
		bankInfo.setAreaName(districtDO.getAreaName());
		if (openBankDO != null) {
			bankInfo.setBankId(openBankDO.getBankId());
			bankInfo.setBankLasalle(openBankDO.getBankLasalle());
			bankInfo.setBranchName(openBankDO.getBranchName());
			bankInfo.setExistNode(true);
		} else {
			bankInfo.setExistNode(false);
		}
		return bankInfo;
	}
	
	/**
	 * 将开户行DO集合和地区DO转换为BankInfo集合
	 * 
	 * @param cbieDOs
	 * @param cdDO
	 * @return
	 */
	public static List<BankInfo> voluationBankInfoList(List<CommonBranchInfoExtendDO> cbieDOs,
														CommonDistrictDO cdDO,
														CommonDistrictDO fatherCdDO) {
		List<BankInfo> bankInfos = new ArrayList<BankInfo>();
		for (CommonBranchInfoExtendDO cbieDO : cbieDOs) {
			BankInfo bankInfo = voluation(cdDO, cbieDO);
			bankInfo.setFatherName(fatherCdDO.getAreaName());
			bankInfos.add(bankInfo);
		}
		return bankInfos;
	}
	
	/**
	 * 将卡bin的DO对象转为INFO对象
	 * 
	 * @param ccDO
	 * @param cardInfo
	 */
	public static void cardBinDOToCardInfo(CommonCardBinDO ccDO, CardInfo cardInfo) {
		
		BeanUtils.copyProperties(ccDO, cardInfo, new String[] { "cardType" });
		cardInfo.setCardType(CardTypeEnum.getByCode(ccDO.getCardType()));
	}
	
}
