package com.icebreak.p2p.rs.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.rs.service.base.AppService;

public class RemoteServiceHandler extends  ControllerBase{
	public  Map<String, Object> handleRequest(Map<String, Object> params, HttpServletRequest request){
		AppService service = serviceFacade.getYrdService(params.get("service").toString());
		JSONObject json = service.execute(params,request);
		return json;
	}

}
