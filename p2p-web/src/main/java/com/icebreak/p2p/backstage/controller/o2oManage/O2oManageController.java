package com.icebreak.p2p.backstage.controller.o2oManage;


import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icebreak.p2p.dal.dataobject.O2oJoinApplicationDO;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.service.JoinApplication.JoinApplicationService;
import com.icebreak.p2p.service.JoinApplication.queryOrder.JoinApplicationOrder;
import com.icebreak.util.lang.util.StringUtil;

@Controller
@RequestMapping("backstage")
public class O2oManageController {
	@Autowired
	JoinApplicationService joinApplicationService;
	
	@RequestMapping("getJoinApplicationInfo")
	public String getJoinApplicationInfo(Model model,JoinApplicationOrder queryConditions,PageParam pageParam){ 
		Page<O2oJoinApplicationDO> Infos=joinApplicationService.findByCondition(queryConditions, pageParam);
		model.addAttribute("appliInfos", Infos.getResult());
		return "/backstage/o2oApplicationManager/applicationInfo.vm";
		
	}
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("updateStatues")
	public Object updateStatues(Model model,JoinApplicationOrder order,int ids,String statu){
		JSONObject json = new JSONObject();
		order.setId(ids);
		if(StringUtil.isNotEmpty(statu)){
			order.setStatus(statu);
		}
		int result=joinApplicationService.updateById(order);
		if(result>0){
			model.addAttribute("result", true);
			json.put("code",1);
			json.put("message","申请提交成功");
			json.put("result", true);
		}else{
			model.addAttribute("result", false);
			json.put("code",1);
			json.put("message","申请提交失败");
			json.put("result", false);
		}
		return json;
	}

}
