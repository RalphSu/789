package com.icebreak.util.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrossScriptingFilter implements Filter {
	protected static final Logger logger = LoggerFactory.getLogger(CrossScriptingFilter.class);
	private static String EXCLUDE_URL = "excludeUrl";
	private static String EXCLUDE_URL_PATH = "excludeUrlPath";
	private static String AUTO_CHECK = "autoCheck";
	private static String ALLOW_METHOD = "GET,HEAD,POST";
	/**
	 * 不过滤路径正则表达式
	 */
	private Pattern EXCLUDE_PATTERN = null;
	
	/**
	 * 不过滤路径
	 */
	private String[] excludePaths = null;
	
	/**
	 * 是否自动检查，如果启用，会在第一次获取值时检查所有的传入参数，建议不要开启
	 */
	private boolean autoCheck = false;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("防跨域站点攻击filter启动");
		String excludeUrl = filterConfig.getInitParameter(EXCLUDE_URL);
		if (excludeUrl != null) {
			EXCLUDE_PATTERN = Pattern.compile(excludeUrl, Pattern.CASE_INSENSITIVE);
			logger.info("防跨域站点攻击排除url正则表达式为：{}", excludeUrl);
		}
		String excludeUrlPath = filterConfig.getInitParameter(EXCLUDE_URL_PATH);
		if (StringUtils.isNotBlank(excludeUrlPath)) {
			excludePaths = excludeUrlPath.split(",");
			logger.info("防跨域站点攻击排除url：{}", excludeUrlPath);
		}
		autoCheck = Boolean.valueOf(filterConfig.getInitParameter(AUTO_CHECK));
		logger.info("是否启动自动检查功能：{},建议不开启", autoCheck);
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
																								throws IOException,
																								ServletException {
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = ((HttpServletResponse) response);
		
		//设置所有的响应Allow
		httpServletResponse.setHeader("Allow", ALLOW_METHOD);
		if (isExcluded((httpServletRequest).getRequestURI())) {
			chain.doFilter(request, response);
			return;
		}
		//检查是否为合法的method方法
		String method = httpServletRequest.getMethod();
		if (method != null && !ALLOW_METHOD.contains(method)) {
			//下面这行语句在tomcat下面完全无效果，allow被他替换了。
			//			httpServletResponse.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			PrintWriter pw = httpServletResponse.getWriter();
			httpServletResponse.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			pw.print("Method Not Allowed");
			pw.flush();
			return;
		}
		chain.doFilter(new XSSRequestWrapper(httpServletRequest, autoCheck), response);
	}
	
	/**
	 * 是否不过滤
	 * @param requestURI
	 * @return
	 */
	private boolean isExcluded(String requestURI) {
		if (EXCLUDE_PATTERN != null) {
			if (EXCLUDE_PATTERN.matcher(requestURI).matches()) {
				return true;
			}
		}
		if (excludePaths != null) {
			for (String path : excludePaths) {
				if (requestURI.startsWith(path)) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public void destroy() {
	}
	
}
