package com.icebreak.p2p.rs.service.base;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.rs.base.enums.APIServiceEnum;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface AppService {
	
	
	/**
	 * 客户端调用执行核心方法
	 * @param request
	 * @return
	 */
	public JSONObject execute(Map<String,Object> params,HttpServletRequest request);
	/**
	 * 获取调用接口
	 * @return
	 */
	public APIServiceEnum getServiceType();

}
