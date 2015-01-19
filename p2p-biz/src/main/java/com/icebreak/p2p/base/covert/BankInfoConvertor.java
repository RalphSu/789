package com.icebreak.p2p.base.covert;

import java.util.ArrayList;
import java.util.List;

import com.icebreak.p2p.dal.dataobject.BankBaseInfoDO;
import com.icebreak.p2p.ws.base.info.BankBasicInfo;
import com.icebreak.util.lang.util.ListUtil;

public class BankInfoConvertor {
	public static void BankInfoDOConvertInfo(BankBaseInfoDO infoDO, BankBasicInfo basicInfo) {
		basicInfo.setBankCode(infoDO.getBankCode());
		basicInfo.setBankName(infoDO.getBankName());
		basicInfo.setLogoUrl(infoDO.getLogoUrl());
	}
	
	public static List<BankBasicInfo> BankInfoDOConvertInfoList(List<BankBaseInfoDO> infoDOs) {
		List<BankBasicInfo> basicInfos = new ArrayList<BankBasicInfo>();
		if (ListUtil.isNotEmpty(infoDOs)) {
			for (BankBaseInfoDO infoDO : infoDOs) {
				BankBasicInfo basicInfo = new BankBasicInfo();
				BankInfoDOConvertInfo(infoDO, basicInfo);
				basicInfos.add(basicInfo);
			}
		}
		return basicInfos;
	}
}
