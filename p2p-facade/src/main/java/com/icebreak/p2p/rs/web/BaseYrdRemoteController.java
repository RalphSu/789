package com.icebreak.p2p.rs.web;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.rs.util.security.YrdCoderUtil;
import com.icebreak.p2p.session.common.Constant;
import com.icebreak.util.lang.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("base")
public class BaseYrdRemoteController {
	@Autowired
	RemoteServiceHandler	remoteServiceHandler;
	
	@ResponseBody
	@RequestMapping("gateWay.htm")
	public Object baseRemoteService(HttpServletRequest request) throws Exception {
		JSONObject json = new JSONObject();
		String appKey = request.getParameter("appKey");
		String encriptData = request.getParameter("encriptData");
		String securityKey = request.getParameter("securityKey");
		String secKey = null;
		Map<String, Object> reqParams = null;
		if (StringUtil.isNotEmpty(appKey)) {
			Constant.setSessionId(appKey);
		}
		
		try {
			secKey = YrdCoderUtil.getRSADecriptedKey(securityKey);
		} catch (Exception e) {
			json.put("status", "FAILD");
			json.put("message", "解密AES密钥失败！");
			return json;
		}
		try {
			reqParams = YrdCoderUtil.getAESDecriptedParams(encriptData, secKey);
		} catch (Exception e) {
			json.put("status", "FAILD");
			json.put("message", "解析请求数据失败！");
			return json;
		}
		Map<String, Object> resParams = null;
		if (reqParams != null) {
			resParams = remoteServiceHandler.handleRequest(reqParams, request);
			resParams.put("status", "SUCCESS");
		}
		
		json.put("result", resParams);
		
		return json;
	}
}
