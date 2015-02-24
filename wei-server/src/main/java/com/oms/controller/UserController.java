package com.oms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oms.interceptor.OAuthRequired;

@Controller
@RequestMapping("werchat")
public class UserController {
	/**
	 * 加载个人信息，此处添加了@OAuthRequired注解
	 * @param model
	 * @return
	 */
	@RequestMapping(value={"/userInfo.do"})
	@OAuthRequired
	public String load(HttpServletRequest request,Model model){
		HttpSession session = request.getSession();
		String openId = session.getAttribute(session.getId()).toString();
		System.out.println(openId);
		return "user";
	}
}
