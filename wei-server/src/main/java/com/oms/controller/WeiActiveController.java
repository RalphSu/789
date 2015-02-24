package com.oms.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.icebreak.p2p.front.controller.account.ActivateController;

@Controller
@RequestMapping("weichat")
public class WeiActiveController extends ActivateController {
	private String wc_path = "/weichat/";
	@RequestMapping("doPayAccount.do")
	public String doPayAccount(HttpServletRequest request, Model model) throws Exception{
		String result = "";
		try{
			result = super.regYJFAccount(request, model);
		}catch (Exception e){
			result = "failed";
		}
		if(result.endsWith("activateSuccess.vm")){
			//开通成功
			model.addAttribute("message", "您已成功注册账户!");
			model.addAttribute("code", "1");
			result = wc_path + "registerComplete.vm";
		}else{
			model.addAttribute("message", "开通支付账户失败!");
			model.addAttribute("code", "0");
			model.addAttribute("userName",request.getParameter("userName"));
			result = wc_path + "registerComplete.vm";
		}
		return result;
	}
}
