package com.icebreak.p2p.base;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LogInterceptor implements HandlerInterceptor {
	
	private org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String url = request.getRequestURL().toString();
		if(url.contains("/resources/") || url.contains("/sendSmsCode")){
			return true;
		}
		StringBuilder stringBuilder = new StringBuilder("http请求URL：" + url + "，传入参数：{");
		Enumeration<String> names = request.getParameterNames();
		int count = 0;
		while(names.hasMoreElements()){
			String name = names.nextElement();
			String[] values = request.getParameterValues(name);
			if(count > 0){
				stringBuilder.append(",");
			}
			stringBuilder.append(name + "=[");
			for (int i = 0; i < values.length; i++) {
				if(i > 0){
					stringBuilder.append(",");
				}
				stringBuilder.append(values[i]);
			}
			stringBuilder.append("]");
			count++;
		}
		stringBuilder.append("}");
		logger.info(stringBuilder.toString());
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
