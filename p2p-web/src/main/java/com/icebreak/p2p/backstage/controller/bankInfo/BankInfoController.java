package com.icebreak.p2p.backstage.controller.bankInfo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.base.BaseAutowiredController;
import com.icebreak.p2p.dataobject.BankInfo;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.page.PageParamUtil;
import com.icebreak.p2p.util.AppConstantsUtil;
import com.icebreak.p2p.ws.base.info.BankBasicInfo;

@Controller
@RequestMapping("backstage")
public class BankInfoController extends BaseAutowiredController {
	/** 页面所在路径 */
	private final String	_PATH	= "/backstage/bank/";
	
	@RequestMapping(value = "getBankInfoList/add_vm")
	public String add_vm(Model model) {
		List<BankBasicInfo> bankList = this.bankDataService.getBankBasicInfos();//获取所有银行
		model.addAttribute("bankList", bankList);
		String s = "bankInfo.vm";
		return _PATH + s;
	}
	
	@RequestMapping(value = "uploadImages2Front")
	public String uploadImages2Front(Model model) {
		String uploadHost = AppConstantsUtil.getYrdUploadFolder();
		model.addAttribute("uploadHost", "");
		return "/backstage/upload/uploadFile.vm";
	}
	
	@ResponseBody
	@RequestMapping(value = "getBankInfoList/addBankInfoSubmit")
	public Object addBankInfoSubmit(BankInfo bankInfo, Model model) {
		JSONObject json = new JSONObject();
		if (bankInfo != null) {
			try {
				bankBaseService.addBank(bankInfo);
				json.put("code", 1);
				json.put("message", "保存成功");
			} catch (Exception e) {
				logger.error("保存银行信息失败", e);
				json.put("code", 0);
				json.put("message", "保存失败");
			}
			
		}
		return json;
	}
	
	@RequestMapping(value = "getBankInfoList")
	public String getBankInfoList(BankInfo bankInfo, Model model, PageParam pageParam) {
		try {
			List<BankInfo> list = new ArrayList<BankInfo>();
			long totalSize = bankBaseService.queryCountByConditions(bankInfo);
			int start = PageParamUtil.startValue((int) totalSize, pageParam.getPageSize(),
				pageParam.getPageNo());
			list = bankBaseService.queryByConditions(bankInfo, pageParam);
			Page<BankInfo> bankInfoList = new Page<BankInfo>(start, totalSize,
				pageParam.getPageSize(), list);
			model.addAttribute("page", bankInfoList);
		} catch (Exception e) {
			logger.error("查询银行异常", e);
		}
		
		return _PATH + "bankInfoList.vm";
	}
	
	@RequestMapping(value = "getBankInfoList/getBankInfoById")
	public String getBankInfoById(long bankId, Model model, String opt) {
		try {
			BankInfo info = new BankInfo();
			info.setBankId(bankId);
			List<BankBasicInfo> bankList = this.bankDataService.getBankBasicInfos();//获取所有银行
			model.addAttribute("bankList", bankList);
			List<BankInfo> list = bankBaseService.queryByConditions(info, null);
			if (list != null && list.size() > 0) {
				BankInfo bankInfo = list.get(0);
				model.addAttribute("opt", opt);
				model.addAttribute("bankInfo", bankInfo);
			} else {
				return _PATH;
			}
		} catch (Exception e) {
			logger.error("查询银行信息失败", e);
			return _PATH;
		}
		return _PATH + "updateBankInfo.vm";
	}
	
	@ResponseBody
	@RequestMapping(value = "getBankInfoList/updateBankInfoById")
	public Object updateBankInfoById(BankInfo bankInfo, Model model) {
		JSONObject json = new JSONObject();
		try {
			int size = bankBaseService.updateBank(bankInfo);
			if (size > 0) {
				json.put("code", 1);
				json.put("message", "更新银行信息成功");
			} else {
				json.put("code", 0);
				json.put("message", "更新银行信息失败");
			}
		} catch (Exception e) {
			logger.error("更新银行信息失败", e);
			json.put("code", 0);
			json.put("message", "更新银行信息失败");
		}
		return json;
		
	}
	
	@ResponseBody
	@RequestMapping(value = "getBankInfoList/deleteBankInfoById")
	public Object deleteBankInfoById(long bankId, Model model) {
		JSONObject json = new JSONObject();
		try {
			int size = bankBaseService.deleteBankInfoById(bankId);
			if (size > 0) {
				json.put("code", 1);
				json.put("message", "删除银行信息成功");
			} else {
				json.put("code", 0);
				json.put("message", "删除银行信息失败");
			}
		} catch (Exception e) {
			logger.error("删除银行信息失败", e);
			json.put("code", 0);
			json.put("message", "删除银行信息失败");
		}
		return json;
		
	}
	
}
