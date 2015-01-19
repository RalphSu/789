package com.icebreak.p2p.front.controller.charge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icebreak.p2p.base.UserAccountInfoBaseController;
import com.icebreak.p2p.charge.ChargeService;
import com.icebreak.p2p.dataobject.ChargeTemplate;

@Controller
@RequestMapping("backstage/chargetemplate")
public class ChargeTemplateController extends UserAccountInfoBaseController {
	
	@Autowired
	private ChargeService	chargeService;
	
	/**
	 * 根据条件查询收费模版
	 * @param page
	 * @param size
	 * @param name
	 * @param type
	 * @param role
	 * @param model
	 * @return
	 */
	@RequestMapping("conditions/{page}/{size}")
	public String getChargeTemplates(@PathVariable int page, @PathVariable int size, String name,
										String type, String[] roles, Model model) {
		model.addAttribute("page",
			chargeService.getChargeTemplates(name, type, (page - 1) * size, size, roles));
		return "backstage/chargetemplate/chargetemplate_list.vm";
	}
	
	/**
	 * 查看收费模版
	 * @param id 模版ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("lookup/{id}")
	public Object lookup(@PathVariable long id) {
		return chargeService.lookup(id);
	}
	
	/**
	 * 根据收费方式获取收费项目
	 * @param method
	 * @return
	 */
	@ResponseBody
	@RequestMapping("projects/{method}")
	public Object getProjects(@PathVariable String method) {
		return chargeService.getChargeProjectsByMethod(method);
	}
	
	/**
	 * 修改
	 * @return
	 */
	@ResponseBody
	@RequestMapping("modify")
	public Object modify(ChargeTemplate template, long[] projectIds, long[] ruleStarts,
							long[] ruleEnds, double[] ruleValues, String[] ruleMethods) {
		try {
			chargeService.modify(template, projectIds, ruleStarts, ruleEnds, ruleValues,
				ruleMethods);
			return true;
		} catch (Exception e) {
			logger.error("Exception异常{}", e.getMessage(), e);
			return false;
		}
	}
	
	/**
	 * 新增
	 * @return
	 */
	@ResponseBody
	@RequestMapping("add")
	public Object addNew(ChargeTemplate template, long[] projectIds, long[] ruleStarts,
							long[] ruleEnds, double[] ruleValues, String[] ruleMethods) {
		try {
			chargeService.addChargeTemplate(template, projectIds, ruleStarts, ruleEnds, ruleValues,
				ruleMethods);
			return true;
		} catch (Exception e) {
			logger.error("Exception异常{}", e.getMessage(), e);
			return false;
		}
	}
}
