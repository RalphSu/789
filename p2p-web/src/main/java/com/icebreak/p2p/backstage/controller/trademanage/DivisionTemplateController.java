package com.icebreak.p2p.backstage.controller.trademanage;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.base.BaseAutowiredController;
import com.icebreak.p2p.dataobject.DivisionRule;
import com.icebreak.p2p.dataobject.DivisionTemplate;
import com.icebreak.p2p.division.DivisionService;
import com.icebreak.p2p.util.LoanUtil;
import com.icebreak.util.lang.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("backstage/divisiontemplate")
public class DivisionTemplateController extends BaseAutowiredController {
	
	@Autowired
	private DivisionService	divisionService;
	
	/**
	 * 根据条件查询分润模板列表
	 * @param page 页码
	 * @param size 页大小
	 * @param name 模版查询条件：模版名称
	 * @param status 模版查询条件：模版状态
	 * @param model 视图参数
	 * @return
	 */
	@RequestMapping("conditions/{page}/{size}")
	public String getByConditions(@PathVariable int page, @PathVariable int size, String name,
									String status, Model model) {
		model.addAttribute("page",
			divisionService.getDivisionTemplatesByCondition(name, status, (page - 1) * size, size));
		return "backstage/divisiontemplate/divisiontemplate_list.vm";
	}
	
	/**
	 * 查看分润模版详情
	 * @param id 分润模版ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("lookup/{id}")
	public Object lookup(@PathVariable long id) {
		DivisionTemplate d = divisionService.getByTemplateId(id);
		List<DivisionRule> list = d.getRules();
		for (DivisionRule l : list) {
			String rule = LoanUtil.getRate_1(String.valueOf(l.getRule()));
			l.setRule(Double.parseDouble(rule));
		}
		d.setRules(list);
		return d;
	}
	
	/**
	 * 修改分润模版
	 * @param template
	 * @return
	 */
	@ResponseBody
	@RequestMapping("modify")
	public Object modify(DivisionTemplate template, int[] roleIds, double[] percentages) {
		try {
			divisionService.modifyDivisionTemplate(template, roleIds, percentages);
			return true;
		} catch (Exception e) {
			logger.error("modify", e);
			return false;
		}
	}
	
	/**
	 * 添加分润模版
	 * @param template
	 * @return
	 */
	@ResponseBody
	@RequestMapping("add")
	public Object add(DivisionTemplate template, int[] roleIds, double[] percentages) {
		try {
			divisionService.addDivisionTemplate(template, roleIds, percentages);
			return true;
		} catch (Exception e) {
			logger.error("add", e);
			return false;
		}
	}


    @ResponseBody
    @RequestMapping("changeStatus")
    public Object changeStatus(long templateId, String status, Model model) {
        JSONObject jsonObject = new JSONObject();
        try {
            if (templateId > 0 && StringUtil.isNotEmpty(status)) {
                boolean isUse = divisionService.isUseDivisionTemplate(templateId);
                if(isUse){
                    jsonObject.put("code", 0);
                    jsonObject.put("message", "分润模板已经使用不能修改状态！");
                    return jsonObject;
                }
                divisionService.changeDivisionTemplateStatus(templateId, status);
                jsonObject.put("code", 1);
                jsonObject.put("message", "修改成功！");
            }
        } catch (Exception e) {
            logger.error("修改分润模板状态出错",e.getMessage(), e);
            jsonObject.put("code", 0);
            jsonObject.put("message", "修改失败！");
        }
        return jsonObject;
    }

}
