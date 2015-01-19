package com.icebreak.util.web;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class DuplicateSubmitInterceptor extends HandlerInterceptorAdapter {
	
	/**
	 * token key
	 */
	public static String TOKEN_KEY = "_synchronizerToken";
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
								Object handler) throws Exception {
		HttpSession session = request.getSession();
		if (session != null) {
			String token = (String) session.getAttribute(TOKEN_KEY);
			if (token == null) {
				session.setAttribute(TOKEN_KEY, nextToken());
				return true;
			} else {
				String tokenIn = request.getParameter(TOKEN_KEY);
				if (tokenIn == null) {
					return true;
				}
				if (tokenIn.equals(token)) {
					session.setAttribute(TOKEN_KEY, nextToken());
					return true;
				} else {
					DuplicateSubmitException dse = new DuplicateSubmitException("重复提交请求");
					dse.setReferer(request.getHeader("Referer"));
					dse.setRequestUri(request.getRequestURI());
					throw dse;
				}
			}
		} else {
			return true;
		}
		
	}
	
	private String nextToken() {
		long seed = System.currentTimeMillis();
		Random r = new Random();
		r.setSeed(seed);
		return Long.toString(seed) + Long.toString(r.nextLong());
	}
}
