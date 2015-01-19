package com.icebreak.p2p.bank;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.base.BaseAutowiredController;
import com.icebreak.p2p.service.openingbank.info.BankInfo;
import com.icebreak.p2p.service.openingbank.info.ProvinceInfo;
import com.icebreak.p2p.service.openingbank.order.OpeningBankQueryByDistrictOrder;
import com.icebreak.p2p.service.openingbank.order.OpeningBankQueryOrder;
import com.icebreak.p2p.service.openingbank.result.BankInfoResult;
import com.icebreak.p2p.ws.base.info.BankBasicInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("bank")
public class BankController extends BaseAutowiredController {
	/** 查询所有银行渠道信息 */
	@ResponseBody
	@RequestMapping("getAllBank")
	public Object getAllBank() {
		JSONObject jsonobj = new JSONObject();
		List<BankBasicInfo> bankBasicInfos = this.bankDataService.getBankBasicInfos();
		if (bankBasicInfos.size() <= 0) {
			jsonobj.put("code", 0);
			jsonobj.put("data", bankBasicInfos);
		} else {
			jsonobj.put("code", 1);
			jsonobj.put("data", bankBasicInfos);
		}
		return jsonobj;
	}
	
	/** 查询所有省、市 */
	@ResponseBody
	@RequestMapping("getAllDistrict")
	public Object getAllDistrict() {
		logger.debug("【查询所有省、市】");
		JSONObject jsonobj = new JSONObject();
		List<ProvinceInfo> provinceList = openingBankService.getAllDistrict()
			.getProvinceInfos();
		
		if (provinceList.size() <= 0) {
			jsonobj.put("code", 0);
			jsonobj.put("data", provinceList);
		} else {
			jsonobj.put("code", 1);
			jsonobj.put("data", provinceList);
		}
		return jsonobj;
	}
	
	/** 根据地区号和银行编号查询支行信息 */
	@ResponseBody
	@RequestMapping("getBankBranch")
	public Object getBankBranch(String bankId, String districtNo, ModelMap modelMap) {
		JSONObject jsonobj = new JSONObject();
		OpeningBankQueryByDistrictOrder order = new OpeningBankQueryByDistrictOrder();
		order.setBankId(bankId);
		order.setDistrictNo(districtNo);
		List<BankInfo> branchList = openingBankService.findBankInfoByDistrictNo(order
			).getBankInfos();
		if (branchList == null || branchList.size() <= 0) {
			jsonobj.put("code", 0);
			jsonobj.put("data", branchList);
		} else {
			jsonobj.put("code", 0);
			jsonobj.put("data", branchList);
		}
		return jsonobj;
	}

	/**
	 * 卡柄校验
	 * @param modelMap
	 * @param cardNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping("verifyBankCode")
	public Object verifyBankCode(ModelMap modelMap, String cardNo, String bankCode) {
		OpeningBankQueryOrder order = new OpeningBankQueryOrder();
		order.setCardNumber(cardNo);
		order.setBankCode(bankCode);
		BankInfoResult result = openingBankService.findBankInfoByBankInfo(order);
		Map<String, String> map = new HashMap<String, String>();
		map.put("code", "1");  //前端验证框架1 == success
		if(!result.isSuccess()) {
			map.put("code", "2");
			map.put("message", result.getMessage());
		}
		return map;
	}
}
