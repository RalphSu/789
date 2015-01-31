package com.icebreak.p2p.front.controller.account;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.base.BaseAutowiredController;
import com.icebreak.p2p.base.Image;
import com.icebreak.p2p.common.info.SessionMobileSend;
import com.icebreak.p2p.dataobject.InstitutionsInfoDO;
import com.icebreak.p2p.dataobject.PersonalInfoDO;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.dataobject.UserRelationDO;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.session.Session;
import com.icebreak.p2p.user.result.UserBaseReturnEnum;
import com.icebreak.p2p.util.MD5Util;
import com.icebreak.p2p.util.SendInformation;
import com.icebreak.p2p.ws.enums.UserTypeEnum;
import com.icebreak.util.lang.util.StringUtil;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("anon")
public class InvestorRegisterController extends BaseAutowiredController {
	private final String vm_path = "/front/anon/investorsOpen/";
	
	@RequestMapping("register")
	public String register(HttpSession session) {
		String token = UUID.randomUUID().toString();
		session.setAttribute("token", token);
		session.removeAttribute("brokerNo");
		return vm_path + "investorsOpen.vm";
	}
	
	@RequestMapping("brokerOpenInvestor")
	public String brokerOpenInvestor(String NO, HttpSession session) {
		String token = UUID.randomUUID().toString();
		session.setAttribute("token", token);
		session.setAttribute("brokerNo", NO);
		Page<UserRelationDO> page = userRelationManager.getRelationsByConditions(null, null, null,
			NO);
		List<UserRelationDO> lst = page.getResult();
		if (lst.size() <= 0) {
			session.setAttribute("referNotExist", "推荐人不存在");
		} else {
			session.removeAttribute("referNotExist");
		}
		return vm_path + "investorsOpen.vm";
	}

	@RequestMapping("verifyRegCode")
	@ResponseBody
	public String verifyRegCode(HttpSession session, String imageVerifyCode){
		if (Image.checkImgCode(session, imageVerifyCode)) {
			session.setAttribute("verifyRegCodeSuccess",true);
			return "success";
		}
		return "failed";
	}
	
	@RequestMapping("perfectInfo")
	public String perfectInfo(HttpSession session, InstitutionsInfoDO institution, String verifyCode,
								PersonalInfoDO personalInfo, UserBaseInfoDO userBaseInfo,
								String token, Model model) throws Exception {

		SessionMobileSend validCode = (SessionMobileSend)Session.getAttribute("session_mobile_send");
		if(validCode == null){
			model.addAttribute("userName", userBaseInfo.getUserName());
			model.addAttribute("reason", "短信验证码不匹配，请先获取短信验证码！");
			return vm_path + "investorsRegisterFail.vm";
		}

		if(verifyCode == null || !verifyCode.equals(validCode.getCode())){
			model.addAttribute("userName", userBaseInfo.getUserName());
			model.addAttribute("reason", "短信验证码不匹配！");
			return vm_path + "investorsRegisterFail.vm";
		}

		//用户注册时直接手机号码作为用户名
		userBaseInfo.setUserName(userBaseInfo.getMobile());
		JSONObject json = new JSONObject();
		if (UserTypeEnum.GR.code().equals(userBaseInfo.getType())) {
			logger.info("个人投资者自主注册，入参1：{}，入参2：{}", personalInfo, userBaseInfo);
			//现已取消邮件验证 代码见 PersonalInfoManagerImpl.saveAndSendEmail()
			json = registerService.personalRegister(session, personalInfo, userBaseInfo, token);
		} else if ("JG".equals(userBaseInfo.getType())) {
			logger.info("机构投资者自主注册，入参1：{}，入参2：{}", institution, userBaseInfo);
			json = registerService.institutionsRegister(session, institution, userBaseInfo, token);
		} else {
			model.addAttribute("userName", userBaseInfo.getUserName());
			model.addAttribute("reason", "账户类型不匹配");

			//删除短信验证码
			Session.removeAttribute("session_mobile_send");
			return vm_path + "investorsRegisterFail.vm";
		}

		UserBaseInfoDO user = userBaseInfoManager.queryByUserName(userBaseInfo.getUserName(),12L);
		
		if (json.getInteger("code") != 1) {
			model.addAttribute("userName", userBaseInfo.getUserName());
			model.addAttribute("reason", json.get("message").toString());
			//删除短信验证码
			Session.removeAttribute("session_mobile_send");
			return vm_path + "investorsRegisterFail.vm";
		} 
		String resendEmailUrl = "/anon/resendEmail/"
								+ MD5Util.getMD5_32(user.getUserBaseId());
		model.addAttribute("resendEmailUrl", resendEmailUrl);
		model.addAttribute("userName", user.getUserName());
		model.addAttribute("MD5UserBaseId", user.getUserBaseId());
		String email = user.getMail();
		if (StringUtil.isNotBlank(email) && email.indexOf("@") != -1) {
			String loginEmailUrl = user.getMail().substring(email.indexOf("@") + 1);
			loginEmailUrl = "http://mail." + loginEmailUrl;
			model.addAttribute("loginEmailUrl", loginEmailUrl);
		}
//		return "/front/anon/activate/regSuccess.vm";
		//取消邮件验证 直接到验证成功页面 15.01.19
		return "/front/anon/activate/regActiveSuccess.vm";
	}


	@RequestMapping("unactivate/resendEmail")
	public String resendEmail(HttpSession session, HttpServletRequest request, Model model)
			throws Exception {
		String userBaseId = request.getParameter("userBaseId");
		UserBaseInfoDO userBaseInfo = userBaseInfoManager.queryByUserBaseId(userBaseId);
		String MD5UserBaseId =  MD5Util.getMD5_32(userBaseId);
		String email = userBaseInfo.getMail();
		String activeUrl = "/anon/activate/" + MD5UserBaseId;
		//发送邮件
		if(!mailService.sendMail(SendInformation.sendMail(email, userBaseInfo.getUserName(), activeUrl, 19L), "HTML"))
		{
			throw new Exception("发送邮件失败！");
		}
		model.addAttribute("userName", userBaseInfo.getUserName());
		String emailUrl = "http://mail."
				+ email.substring(email.lastIndexOf('@') + 1, email.lastIndexOf("."))
				+ ".com";
		model.addAttribute("emailUrl", emailUrl);
		String[] strMail = email.split("@");
		model.addAttribute("email", strMail[0].substring(0, 3) + "********@" + strMail[1]);
		String resendEmailUrl = "/anon/resendEmail/"
				+ MD5Util.getMD5_32(MD5UserBaseId);
		model.addAttribute("resendEmailUrl", resendEmailUrl);
		UserBaseReturnEnum result = userBaseInfoManager.updateUserBaseInfo(userBaseInfo);
		if(result != UserBaseReturnEnum.EXECUTE_SUCCESS) {
			throw new Exception("发送邮件失败！");
		}
		return "/front/anon/activate/regSuccess.vm";
	}

	@RequestMapping("resendEmail")
	public String resendEmail(HttpSession session, UserBaseInfoDO userBaseInfo, Model model)
																									throws Exception {
		Validate.notNull(userBaseInfo, "邮箱不能为空！");
//		UserBaseInfoDO userBaseInfo = userBaseInfoManager.queryByMD5UserBaseId(MD5UserBaseId);
		//String MD5UserBaseId = userBaseInfo.getUserBaseId();
		String MD5UserBaseId = "";
		//TODO
		userBaseInfo.setUserBaseId("14152813431768431105");
		//userBaseInfo.setUserBaseId(userBaseInfo.getUserBaseId());
		String email = userBaseInfo.getMail();
		String activeUrl = "/anon/activate/" + MD5Util.getMD5_32(MD5UserBaseId);
		//发送邮件
		if(!mailService.sendMail(SendInformation.sendMail(email, userBaseInfo.getUserName(), activeUrl, 19L), "HTML"))
		{
			throw new Exception("发送邮件失败！");
		}
		model.addAttribute("userName", userBaseInfo.getUserName());
		String emailUrl = "http://mail."
							+ email.substring(email.lastIndexOf('@') + 1, email.lastIndexOf("."))
							+ ".com";
		model.addAttribute("emailUrl", emailUrl);
        String[] strMail = email.split("@");
        model.addAttribute("email", strMail[0].substring(0, 3) + "********@" + strMail[1]);
		String resendEmailUrl = "/anon/resendEmail/"
								+ MD5Util.getMD5_32(MD5UserBaseId);
		model.addAttribute("resendEmailUrl", resendEmailUrl);
		UserBaseReturnEnum result = userBaseInfoManager.updateUserBaseInfo(userBaseInfo);
		if(result != UserBaseReturnEnum.EXECUTE_SUCCESS) {
			throw new Exception("发送邮件失败！");
		}
		return "/front/anon/activate/regSuccess.vm";
	}
	
	@RequestMapping("sendEmailPage/{MD5UserBaseId}")
	public String sendEmailPage(HttpSession session, @PathVariable String MD5UserBaseId, Model model)
																										throws Exception {
			UserBaseInfoDO userBaseInfo = userBaseInfoManager.queryByMD5UserBaseId(MD5UserBaseId);
		model.addAttribute("userName", userBaseInfo.getUserName());
		String email = userBaseInfo.getMail();
		String emailUrl = "http://mail."
							+ email.substring(email.lastIndexOf('@') + 1, email.lastIndexOf("."))
							+ ".com";
		model.addAttribute("emailUrl", emailUrl);
		model.addAttribute("email", userBaseInfo.getMail());
		model.addAttribute("userBaseId",userBaseInfo.getUserBaseId());
		String resendEmailUrl = "/anon/resendEmail/"
								+ MD5Util.getMD5_32(userBaseInfo.getUserBaseId());
		model.addAttribute("resendEmailUrl", resendEmailUrl);
		return vm_path + "investorsOpenSuccess.vm";
	}
	
}
