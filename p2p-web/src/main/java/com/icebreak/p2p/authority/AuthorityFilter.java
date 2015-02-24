package com.icebreak.p2p.authority;

import com.icebreak.p2p.session.SessionLocalManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthorityFilter implements Filter {
	protected static final String		DEDUCT_OPERATE			= "/deduct/*";
	private static final String[]		urls					= { "/uploadfile/", "/styles/",
			"/images/", "/resources/", "/bank/", "/anon/", "/resources/", "/help", "/upload/",
			"/staticFiles/", "/backstage/userManage/common/", "/backstage/checkBorrower",
			"/backstage/getRealName", "/backstage/checkguaranteeLicenceNo",
			"/backstage/guaranteeAuthCheck", "/backstage/queryRuleInfo", "/backstage/updatePdfUrl",
											"/backstage/backstageIdex", "/admincenter/",
											"/backstage/nopermission", "/loan/", "/about/",
								 			"/JoinApplication/", "/remote/trade/receive/",
											"/notify", "/index/" , "/goto.htm","/gotoInvest.htm","/help/","/coreJoin","/weichat"};
	/**
	 * 正则${:}
	 */
	protected static Pattern			pattern					= Pattern
																	.compile("\\$\\{[a-zA-Z0-9.]{1,}:[a-zA-Z0-9.]{1,}\\}");
	protected final Logger				logger					= LoggerFactory
																	.getLogger(getClass());
	/**
	 * 权限验证器
	 */
	protected WebAppAuthorityVerifier	webAppAuthorityVerifier	= null;
	
	@Override
	public void destroy() {
		
	}
	
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain)
																						throws IOException,
																						ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		String uri = request.getServletPath();
		for (String s : urls) {
			if (uri.startsWith(s)) {
				chain.doFilter(request, response);
				return;
			}
		}
		if (webAppAuthorityVerifier.checkPermission(request)) {
			Pattern p = Pattern.compile(DEDUCT_OPERATE.replace("*", ".*").replace("?", "\\?"));
			Matcher matcher = p.matcher(uri);
			if (matcher.matches()) {
				if (SessionLocalManager.getSessionLocal() != null) {
					chain.doFilter(request, response);
				} else {
					String backOperate = webAppAuthorityVerifier.getBackOperate(request);
					if (backOperate == null || backOperate.length() < 1) {
						backOperate = webAppAuthorityVerifier.getDefault();
					}
					response.sendRedirect(backOperate);
				}
			} else {
				chain.doFilter(request, response);
			}
		} else {
			String backOperate = webAppAuthorityVerifier.getBackOperate(request);
			if (backOperate == null || backOperate.length() < 1) {
				backOperate = webAppAuthorityVerifier.getDefault();
			}
			response.sendRedirect(backOperate);
		}
	}
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		String className = config.getInitParameter("webAppAuthorityVerifier");
		try {
			if (className != null && className.length() > 0) {
				webAppAuthorityVerifier = (WebAppAuthorityVerifier) (Class.forName(className, true,
					Thread.currentThread().getContextClassLoader()).newInstance());
				return;
			}
		} catch (Exception e) {
			logger.error("className={} create error", className, e);
		}
		webAppAuthorityVerifier = new DefaultWebAppAuthorityVerifier();
	}
}
