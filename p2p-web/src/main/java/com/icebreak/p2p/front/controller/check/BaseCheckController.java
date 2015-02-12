package com.icebreak.p2p.front.controller.check;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.base.BaseAutowiredController;
import com.icebreak.p2p.base.Image;
import com.icebreak.p2p.common.result.SmsCodeResult;
import com.icebreak.p2p.dataobject.Role;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.page.Pagination;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.user.result.UserBaseReturnEnum;
import com.icebreak.p2p.util.AppConstantsUtil;
import com.icebreak.p2p.util.CommonUtil;
import com.icebreak.p2p.ws.enums.SmsBizType;
import com.icebreak.p2p.ws.enums.SysUserRoleEnum;
import com.icebreak.p2p.ws.result.P2PBaseResult;
import com.icebreak.util.lang.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("anon")
public class BaseCheckController extends BaseAutowiredController {
	
	@RequestMapping("getImgCode")
	public void getImgCode(HttpServletResponse response, HttpSession session) throws IOException {
		BufferedImage bufferedImage = Image.creatImage(session);
		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(bufferedImage, "jpg", out);
		out.flush();
		out.close();
	}
	
	@ResponseBody
	@RequestMapping("checkImgCode")
	public Object checkImgCode(HttpSession session, String imgCode) throws IOException {
		logger.info("验证图片验证码是否正确，入参：{}", imgCode);
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtil.isBlank(imgCode)) {
			map.put("code", 0);
			map.put("message", "验证码不能为空");
		}
		String oldImgCode = (String) session.getAttribute("imgCode");
		if (StringUtil.isBlank(oldImgCode)) {
			map.put("code", 0);
			map.put("message", "旧验证码不能为空");
		}
		if (imgCode.equalsIgnoreCase(oldImgCode)) {
			map.put("code", 1);
			map.put("message", "验证码正确");
		} else {
			map.put("code", 0);
			map.put("message", "验证码错误");
		}
		logger.info("验证易图片验证码是否正确，结果：{}", map);
		return map;
	}
	
	@ResponseBody
	@RequestMapping("checkUserMobile")
	public Object checkUserMobile(String userName, String mobile) throws Exception {
		logger.info("验证" + AppConstantsUtil.getProductName() + "金融用户不存在，入参：{}", userName);
		JSONObject jsonobj = new JSONObject();
		UserBaseInfoDO baseUser = userBaseInfoManager.queryByUserName(userName, 0);
		//验证用户不存在
		if (baseUser != null) {
			if (mobile.equals(baseUser.getMobile())) {
				jsonobj.put("code", 1);
				jsonobj.put("message", "用户手机验证成功");
			} else {
				jsonobj.put("code", 0);
				jsonobj.put("message", "用户手机验证失败");
			}
		} else {
			jsonobj.put("code", 0);
			jsonobj.put("message", "用户手机验证失败");
		}
		logger.info("验证" + AppConstantsUtil.getProductName() + "金融用户手机，入参：{}", userName);
		return jsonobj;
	}
	
	@ResponseBody
	@RequestMapping("checkUserName")
	public Object checkUserName(String userName) throws Exception {
		logger.info("验证" + AppConstantsUtil.getProductName() + "金融用户不存在，入参：{}", userName);
		Map<String, Object> map = new HashMap<String, Object>();
		UserBaseReturnEnum returnEnum = UserBaseReturnEnum.USERNAME_ERROR;
		returnEnum = userBaseInfoManager.validationUserName(userName);
		//验证用户不存在
		if (returnEnum == UserBaseReturnEnum.USERNAME_SUCCESS) {
			map.put("code", 1);
			map.put("message", "用户名可以用");
		} else {
			map.put("code", 0);
			map.put("message", "用户名已存在");
		}
		logger.info("验证" + AppConstantsUtil.getProductName() + "金融用户不存在，结果：{}", map);
		return map;
	}
	
	@ResponseBody
	@RequestMapping("checkEmail")
	public Object checkEmail(String email) throws Exception {
		logger.info("验证邮箱格式是否正确，入参：{}", email);
		Map<String, Object> map = new HashMap<String, Object>();
		boolean bool = CommonUtil.checkEmail(email);
		if (bool) {
			map.put("code", 1);
			map.put("message", "邮箱格式正确");
		} else {
			map.put("code", 0);
			map.put("message", "邮箱格式错误");
		}
		logger.info("验证邮箱格式是否正确，结果：{}", map);
		return map;
	}
	
	@ResponseBody
	@RequestMapping("checkCertNo")
	public Object checkCertNo(String certNo) throws Exception {
		logger.info("验证身份证号码格式是否正确，入参：{}", certNo);
		Map<String, Object> map = new HashMap<String, Object>();
		boolean bool = CommonUtil.checkIdentifyCardNum(certNo);
		if (bool) {
			map.put("code", 1);
			map.put("message", "身份证号码格式正确");
		} else {
			map.put("code", 0);
			map.put("message", "身份证号码格式错误");
		}
		logger.info("验证身份证号码格式是否正确，结果：{}", map);
		return map;
	}
	
	@ResponseBody
	@RequestMapping("checkCertNoByType")
	public Object checkCertNoByType(String certNo) throws Exception {
		logger.info("验证身份证号码格式是否正确，入参：{}", certNo);
		String checkType = null;
		Map<String, Object> map = new HashMap<String, Object>();
		boolean bool = CommonUtil.checkIdentifyCardNum(certNo);
		if (bool) {
			long userId = SessionLocalManager.getSessionLocal().getUserId();
			Pagination<Role> rolePage = authorityService.getRolesByUserId(userId, 0, 10);
			if (rolePage.getResult() != null && rolePage.getResult().size() > 0) {
				for (Role role : rolePage.getResult()) {
					if (SysUserRoleEnum.INVESTOR.getRoleCode().equals(role.getCode())) {
						checkType = role.getCode();
						break;
					} else if (SysUserRoleEnum.BROKER.getRoleCode().equals(role.getCode())) {
						checkType = role.getCode();
						break;
					} else if (SysUserRoleEnum.LOANER.getRoleCode().equals(role.getCode())) {
						checkType = role.getCode();
						break;
					}
				}
			}
			if (StringUtil.isNotEmpty(checkType)) {
				long count = personalInfoManager.countCertNoByRole(certNo, checkType);
				if (count > 0) {
					map.put("code", 0);
					map.put("message", "此身份证已被注册");
				} else {
					map.put("code", 1);
					map.put("message", "身份证号码格式正确");
				}
			} else {
				map.put("code", 1);
				map.put("message", "身份证号码格式正确");
			}
		} else {
			map.put("code", 0);
			map.put("message", "身份证号码格式错误");
		}
		logger.info("验证身份证号码格式是否正确，结果：{}", map);
		return map;
	}
	
	@ResponseBody
	@RequestMapping("checkEmailOrMobile")
	public Object checkEmail(String email, String mobile, String checkType) throws Exception {
		logger.info("验证邮箱格式是否正确，入参：{}", email);
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtil.isNotEmpty(email)) {
			boolean bool = CommonUtil.checkEmail(email);
			if (bool) {
				//				map.put("email", email);
				//				map.put("roleCode", checkType);
				//				long count = userBaseInfoManager.countMailOrMobileByRole(map);
				//				if(count > 0){
				//					map.put("code", 0);
				//					map.put("message", "此邮箱已被注册");
				//				}else{
				map.put("code", 1);
				map.put("message", "邮箱格式正确");
				//				}
			} else {
				map.put("code", 0);
				map.put("message", "邮箱格式错误");
			}
		}
		if (StringUtil.isNotEmpty(mobile)) {
			boolean bool = CommonUtil.checkMobile(mobile);
			if (bool) {
				map.put("email", email);
				map.put("roleCode", checkType);
				long count = userBaseInfoManager.countMailOrMobileByRole(map);
				if (count > 0) {
					map.put("code", 0);
					map.put("message", "此手机号已被注册");
				} else {
					map.put("code", 1);
					map.put("message", "手机号码格式正确");
				}
				map.put("code", 1);
				map.put("message", "手机号码格式正确");
			} else {
				map.put("code", 0);
				map.put("message", "手机号码格式错误");
			}
		}
		logger.info("验证邮箱或手机格式是否正确，结果：{}", map);
		return map;
	}
	
	@ResponseBody
	@RequestMapping("checkMobile")
	public Object checkMobile(String mobile) throws Exception {
		logger.info("验证手机号码格式是否正确，入参：{}", mobile);
		Map<String, Object> map = new HashMap<String, Object>();
		boolean bool = CommonUtil.checkMobile(mobile);
		if (bool) {
			map.put("code", 1);
			map.put("message", "手机号码格式正确");
		} else {
			map.put("code", 0);
			map.put("message", "手机号码格式错误");
		}
		logger.info("验证手机号码格式是否正确，结果：{}", map);
		return map;
	}
	
	@ResponseBody
	@RequestMapping("sendSmsCode")
	public Object sendSmsCode(HttpSession session,String mobile, String business) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if (SessionLocalManager.getSessionLocal() != null) {
			UserBaseInfoDO baseUser = userBaseInfoManager.queryByUserBaseId(SessionLocalManager
				.getSessionLocal().getUserBaseId());
			mobile = baseUser.getMobile();
		}

		if(mobile == null || "".equals(mobile.trim())){
			map.put("code", 0);
			map.put("message", "手机号码错误！");
			return map;
		}

		Pattern p = Pattern.compile("^[1][3,4,5,8,7][0-9]{9}$"); // 验证手机号
		Matcher m =p.matcher(mobile);
		if(!m.matches()){
			map.put("code", 0);
			map.put("message", "手机号码错误！");
			return map;
		}


		if(business == null || "".equals(business.trim())){
			map.put("code", 0);
			map.put("message", "业务规则错误！");
			return map;
		}

		//当获取注册验证码的时候，必须先验证图片验证码
		if(business.equals(SmsBizType.REGISTER.code())){
			Object isVaildRegImgCode = session.getAttribute("verifyRegCodeSuccess");
			if(isVaildRegImgCode == null || (Boolean)isVaildRegImgCode != true){
				map.put("code", 0);
				map.put("message", "请先获取图片验证码");
				return map;
			}else{
				session.removeAttribute("verifyRegCodeSuccess");
			}
		}
		logger.info("发生手机验证码，入参[{}],[{}]", mobile, business);
		P2PBaseResult baseResult = smsManagerService.sendSmsCode(mobile,
			SmsBizType.getByCode(business));
		if (baseResult.isSuccess()) {
			map.put("code", 1);
			map.put("message", "发生手机验证码成功");
		} else {
			map.put("code", 0);
			map.put("message", baseResult.getMessage());
		}
		logger.info("发生手机验证码，结果：{}", map);
		return map;
	}
	
	@ResponseBody
	@RequestMapping("sendSmsPasswordCode")
	public Object sendSmsPasswordCode(String mobile, String business, String userName)
																						throws Exception {
		logger.info("发生手机验证码，入参[{}],[{}]", mobile, business);
		JSONObject jsonobj = new JSONObject();
		UserBaseInfoDO baseUser = userBaseInfoManager.queryByUserName(userName, 0);
		if (baseUser == null) {
			jsonobj.put("code", 0);
			jsonobj.put("message", "用户名错误");
			return jsonobj;
		}
		if (!mobile.equals(baseUser.getMobile())) {
			jsonobj.put("code", 0);
			jsonobj.put("message", "手机号不匹配");
			return jsonobj;
		}
		P2PBaseResult baseResult = smsManagerService.sendSmsCode(mobile,
			SmsBizType.getByCode(business));
		if (baseResult.isSuccess()) {
			jsonobj.put("code", 1);
			jsonobj.put("message", "发生手机验证码成功");
		} else {
			jsonobj.put("code", 0);
			jsonobj.put("message", baseResult.getMessage());
		}
		logger.info("发生手机验证码，结果：{}", jsonobj);
		return jsonobj;
	}
	
	@ResponseBody
	@RequestMapping("checkSmsCode")
	public Object checkSmsCode(String mobile, String business, String code) throws Exception {
		logger.info("验证手机验证码，入参[{}],[{}]", mobile, business);
		Map<String, Object> map = new HashMap<String, Object>();
		SmsCodeResult smsCodeResult = this.smsManagerService.verifySmsCode(mobile,
			SmsBizType.getByCode(business), code, false);
		if (smsCodeResult.isSuccess()) {
			map.put("code", 1);
			map.put("message", "验证手机验证码成功");
		} else {
			map.put("code", 0);
			map.put("message", smsCodeResult.getMessage());
		}
		logger.info("验证手机验证码，结果：{}", map);
		return map;
	}
	
	@ResponseBody
	@RequestMapping("checkIdentityName")
	public Object checkIdentityName(String identityName, String type) throws Exception {
		logger.info("验证机构简码，入参[{}],[{}]", identityName, type);
		Map<String, Object> map = new HashMap<String, Object>();
		long userId = userBaseInfoManager.queryUserId(identityName, null, null, type);
		if (userId > 0) {
			map.put("code", 0);
			map.put("message", "机构简码已存在");
		} else {
			map.put("code", 1);
			map.put("message", "机构简码不存在");
		}
		logger.info("验证机构简码，结果：{}", map);
		return map;
	}
	
	@ResponseBody
	@RequestMapping("checkUserNameAndType")
	public Object checkUserNameAndType(String userName, String type) throws Exception {
		logger.info("根据用户名和用户类型验证用户，入参[{}],[{}]", userName, type);
		Map<String, Object> map = new HashMap<String, Object>();
		long userId = userBaseInfoManager.queryUserId(null, userName, null, type);
		if (userId > 0) {
			map.put("code", 1);
			map.put("message", "验证成功");
		} else {
			map.put("code", 0);
			map.put("message", "用户名和用户类型不匹配");
		}
		logger.info("根据用户名和用户类型验证用户，结果：{}", map);
		return map;
	}
	
	@ResponseBody
	@RequestMapping("checkUserNameAndRealName")
	public Object checkUserNameAndRealName(String userName, String realName) throws Exception {
		logger.info("根据用户名和真实姓名验证用户，入参[{}],[{}]", userName, realName);
		Map<String, Object> map = new HashMap<String, Object>();
		long userId = userBaseInfoManager.queryUserId(null, userName, realName, null);
		if (userId > 0) {
			map.put("code", 1);
			map.put("message", "验证成功");
		} else {
			map.put("code", 0);
			map.put("message", "用户名和真实姓名不匹配");
		}
		logger.info("根据用户名和真实姓名验证用户，结果：{}", map);
		return map;
	}
	
	@ResponseBody
	@RequestMapping("checkIdentityNameUserNameAndType")
	public Object checkIdentityNameUserNameAndType(String identityName, String userName, String type)
																										throws Exception {
		logger.info("根据用户名,用户类型,验机构号段，入参[{" + identityName + "}],[{" + identityName + "}],[{"
					+ type + "}]");
		Map<String, Object> map = new HashMap<String, Object>();
		long userId = userBaseInfoManager.queryUserId(identityName, userName, null, type);
		if (userId > 0) {
			map.put("code", 1);
			map.put("message", "用户名与机构号段匹配成功");
		} else {
			map.put("code", 0);
			map.put("message", "用户名与机构号段匹配失败");
		}
		logger.info("根据用户名,用户类型,验机构号段，结果：{}", map);
		return map;
	}
	
	@RequestMapping("toGetKey")
	public void toGetKey(HttpServletRequest request, HttpServletResponse response)
																					throws IOException {
		
		logger.info("获取安全控件密钥，开始");
		logger.info("获取安全控件密钥，结束");
	}

	@ResponseBody
	@RequestMapping(value = "checkGuaranteeLicenseNo")
	public Object checkGuaranteeLicenseNo(String guaranteeLicenseNo) {
		logger.info("验证借款人，入参[{}]", guaranteeLicenseNo);
		JSONObject jsonobj = new JSONObject();
		try {
			long counts = loanDemandManager.checkNO(guaranteeLicenseNo, "");
			if (counts > 0) {
				jsonobj.put("code", 0);
				jsonobj.put("message", "编号已存在,请重新输入! ");
			} else {
				jsonobj.put("code", 1);
				jsonobj.put("message", "暂无此编号");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jsonobj.put("code", 2);
			jsonobj.put("message", "查询错误 ");
		}
		return jsonobj;
	}
	
	public String getKey(HttpServletRequest request) throws NoSuchAlgorithmException,
													UnsupportedEncodingException {
		HttpSession session = request.getSession(true);
		String aesKey = getRandomString(16);
		session.setAttribute(session.getId(), aesKey);
		return aesKey;
	}
	
	// 产生一个随机数 方法
	public String getRandomString(int length) {
		String str = "abcdefghigklmnopkrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()";
		Random random = new Random();
		StringBuffer sf = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(72);// 0~71
			sf.append(str.charAt(number));
			
		}
		return sf.toString();
	}


	
}
