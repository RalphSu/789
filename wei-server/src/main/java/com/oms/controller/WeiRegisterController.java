package com.oms.controller;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.daointerface.UserBaseInfoDAO;
import com.icebreak.p2p.dataobject.PersonalInfoDO;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.front.controller.account.InvestorRegisterController;
import com.icebreak.p2p.session.Session;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.util.StringUtil;
import com.icebreak.p2p.ws.enums.UserTypeEnum;

@Controller
@RequestMapping("weichat")
public class WeiRegisterController extends InvestorRegisterController {
	private final String wc_path = "/weichat/";
	
	@Autowired
	private UserBaseInfoDAO userBaseInfoDAO;
	
	@RequestMapping("register.do")
	public String register(HttpSession session,HttpServletRequest request, Model model){
		String message = request.getParameter("message");
		model.addAttribute("message", message);
		String token = UUID.randomUUID().toString();
		session.setAttribute("token", token);
		return wc_path + "register.vm";
	}
	
	@RequestMapping("doRegister.do")
	public String doRegister(HttpSession session, PersonalInfoDO personalInfo, UserBaseInfoDO userBaseInfo,
			String token, Model model) throws Exception{
		if(StringUtil.isBlank(userBaseInfo.getMobile())){
			return null;
		}
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("userName", userBaseInfo.getUserName());
		if(userBaseInfoDAO.queryByUserName(condition).size() > 0){
			//手机号码已注册
			return "redirect:register.do?message=" + URLEncoder.encode("手机号码已注册","UTF-8");
		}
		//用户注册时直接手机号码作为用户名
		userBaseInfo.setUserName(userBaseInfo.getMobile());
		JSONObject json = new JSONObject();
		logger.info("个人投资者自主注册，入参1：{}，入参2：{}", personalInfo, userBaseInfo);
		json = registerService.personalRegister(session, personalInfo, userBaseInfo, token);
		if (json.getInteger("code") != 1) {
			return "redirect:register.do?message=" + URLEncoder.encode(json.get("message").toString(),"UTF-8"); 
		} 
		return "redirect:registerTwo.do?userName=" + userBaseInfo.getUserName();
  	}
	
	/**
	 * 开通支付账户
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("registerTwo.do")
	public String registerTow(HttpServletRequest request, Model model){
		String userName = request.getParameter("userName");
		if(StringUtil.isBlank(userName)){
			if(null != SessionLocalManager.getSessionLocal())
				userName = SessionLocalManager.getSessionLocal().getUserName();
		}
		model.addAttribute("userName", request.getParameter("userName"));
		return wc_path + "registerTwo.vm"; 
	}
}
