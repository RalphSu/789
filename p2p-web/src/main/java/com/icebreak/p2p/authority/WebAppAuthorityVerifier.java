package com.icebreak.p2p.authority;

import javax.servlet.http.HttpServletRequest;

public interface WebAppAuthorityVerifier {
	/**
	 * 根据request验证权限
	 * @param request
	 * @return
	 */
	public boolean checkPermission(HttpServletRequest request);
	/**
	 * 获取回调url
	 * @param request
	 * @return
	 */
	public String getBackOperate(HttpServletRequest request);
	/**
	 * 默认条件
	 * @return
	 */
	public String getDefault();

}
