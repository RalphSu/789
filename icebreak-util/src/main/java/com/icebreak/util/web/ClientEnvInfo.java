package com.icebreak.util.web;

import com.google.common.base.Preconditions;
import com.icebreak.util.lang.ip.IPUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

public class ClientEnvInfo implements Serializable {
	
	public static final String CLIENT_INFO_KEY = "client_env_info";
	
	public static final String DEFAULT_CLIENT_INFO_KEY = "client_env_info_default";
	
	public static final String CLIENT_MAC_KEY = "client_mac";
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 请求客户端ip
	 */
	private String ip;
	/**
	 * 请求客户端mac地址
	 */
	private String mac;
	
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getMac() {
		return mac;
	}
	
	public void setMac(String mac) {
		this.mac = mac;
	}
	
	/**
	 * 从请求中获取ClientEvnInfo，首先通过ClientEvnInfo.CLIENT_INFO_KEY，
	 * 获取失败后在从ClientEvnInfo.DEFAULT_CLIENT_INFO_KEY中获取,
	 * 不管session是否存在，都会返回ClientEvnInfo
	 * 
	 * @param httpServletRequest
	 * @return
	 */
	@Nonnull
	public static ClientEnvInfo getClientEnvInfo(@Nonnull HttpServletRequest httpServletRequest) {
		
		return getClientEnvInfo(httpServletRequest, true);
	}
	
	/**
	 * 
	 * 从请求中获取ClientEvnInfo，首先通过ClientEvnInfo.CLIENT_INFO_KEY，
	 * 获取失败后在从ClientEvnInfo.DEFAULT_CLIENT_INFO_KEY中获取,
	 * @param httpServletRequest
	 * @param createNew session为空时是否创建对象
	 * @return
	 */
	@Nullable
	public static ClientEnvInfo getClientEnvInfo(@Nonnull HttpServletRequest httpServletRequest,
													boolean createNew) {
		Preconditions.checkNotNull(httpServletRequest);
		HttpSession session = httpServletRequest.getSession(false);
		ClientEnvInfo clientEvnInfo = null;
		//session不为空才处理，不新建session
		if (session != null) {
			clientEvnInfo = (ClientEnvInfo) session.getAttribute(ClientEnvInfo.CLIENT_INFO_KEY);
			//获取clientInfo，如果不为空，直接返回
			if (clientEvnInfo == null) {
				String mac = httpServletRequest.getParameter(ClientEnvInfo.CLIENT_MAC_KEY);
				//获取mac值，如果mac为空，判断是否写入默认clientInfo
				if (mac == null) {
					clientEvnInfo = (ClientEnvInfo) session
						.getAttribute(ClientEnvInfo.DEFAULT_CLIENT_INFO_KEY);
					//如果默认clientinfo为空，创建ClientEvnInfo对象，写入默认clientInfo
					if (clientEvnInfo == null) {
						clientEvnInfo = new ClientEnvInfo();
						clientEvnInfo.setIp(IPUtil.getIpAddr(httpServletRequest));
						session.setAttribute(ClientEnvInfo.DEFAULT_CLIENT_INFO_KEY, clientEvnInfo);
					}
				} else {
					//如果maca不为空，写入clientInfo
					clientEvnInfo = new ClientEnvInfo();
					clientEvnInfo.setIp(IPUtil.getIpAddr(httpServletRequest));
					clientEvnInfo.setMac(mac);
					session.setAttribute(ClientEnvInfo.CLIENT_INFO_KEY, clientEvnInfo);
				}
			}
		} else {
			if (createNew) {
				clientEvnInfo = new ClientEnvInfo();
				clientEvnInfo.setIp(IPUtil.getIpAddr(httpServletRequest));
			}
		}
		return clientEvnInfo;
	}
	
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("ClientEnvInfo{");
		sb.append("ip='").append(ip).append('\'');
		sb.append(", mac='").append(mac).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
