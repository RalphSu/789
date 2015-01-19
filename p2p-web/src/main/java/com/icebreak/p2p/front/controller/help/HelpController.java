package com.icebreak.p2p.front.controller.help;

import com.icebreak.p2p.dataobject.PopInfoDO;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.pop.IPopService;
import com.icebreak.p2p.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("help")
public class HelpController {

	@Autowired
	IPopService popService;
	private String VM_PATH = "/front/help/";

	@RequestMapping("aboutus")
	public String center(HttpSession session, Model model) {
		setCSS("aboutus",model);
		return VM_PATH + "aboutus.vm";
	}

	@RequestMapping("news/list")
	public String newsList(PageParam pageParam, HttpSession session, Model model) {
		setCSS("newsmenu",model);

		Map<String, Object> conditions = new HashMap<String, Object>();
		List<Integer> types = new ArrayList<Integer>();
		types.add(1);
		types.add(2);
		conditions.put("type", types);
		conditions.put("status", 2);
		pageParam.setPageSize(5);
		Page<PopInfoDO> page = popService.getPageByConditions(pageParam, conditions);
		List<PopInfoDO> newItems = page.getResult();
		model.addAttribute("newItems", newItems);
		return VM_PATH + "newsList.vm";
	}

	@RequestMapping("news/{id}")
	public String news(@PathVariable String id, HttpSession session, Model model) {
		setCSS("newsmenu",model);
		long lId = 1;
		try {
			lId = Long.parseLong(id);
		} catch (NumberFormatException e) {
		}
		PopInfoDO news = popService.getByPopId(lId);
		model.addAttribute("news",news);
		return VM_PATH + "news.vm";
	}

	@RequestMapping("joinus")
	public String Join(HttpSession session, Model model) {
		setCSS("joinus", model);
		return VM_PATH+"joinus.vm";
	}

	@RequestMapping("investment")
	public String Investment(HttpSession session,Model model)
	{
		setCSS("investment", model);
		return VM_PATH+"investment.vm";
	}

	//融资流程
	@RequestMapping("financing")
	public String Financing(HttpSession session,Model model)
	{
		setCSS("financing", model);
		return VM_PATH+"financing.vm";
	}

	@RequestMapping("guidelines")
	public String Guidelines(HttpSession session,Model model)
	{
		setCSS("guidelines", model);
		return VM_PATH+"guidelines.vm";
	}

	//联系我们
	@RequestMapping("contactus")
	public String Contactus(HttpSession session,Model model)
	{
		setCSS("contactus", model);
		return VM_PATH+"contactus.vm";
	}


	@RequestMapping("qa")
	public String qa(HttpSession session,Model model)
	{
		setCSS("qa", model);
		return VM_PATH+"qa.vm";
	}


	//安全保障
	@RequestMapping("security")
	public String security(HttpSession session,Model model)
	{
		setCSS("security", model);
		return VM_PATH+"safe.vm";
	}

	private void setCSS(String menu, Model model) {
		model.addAttribute("helpSelected", Constants.selectedCSS);
		model.addAttribute(menu, Constants.selectedCSS);
	}

}
