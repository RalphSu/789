package com.icebreak.p2p.rs.service.base.impl;

import com.icebreak.p2p.rs.base.ServiceBase;
import com.icebreak.p2p.rs.base.enums.YrdServiceExceptionEnum;
import com.icebreak.p2p.rs.base.exception.YrdException;
import com.icebreak.p2p.rs.service.base.AppService;
import com.icebreak.p2p.rs.service.base.ServiceFacade;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

public class ServiceFacadeImpl extends ServiceBase implements ServiceFacade,
														InitializingBean, DisposableBean,
														ApplicationContextAware {
	private final Map<String, AppService>	serviceMap	= new HashMap<String, AppService>();
	
	private ApplicationContext				applicationContext;
	
	@Override
	public AppService getYrdService(String code) {

		AppService service = serviceMap.get(code);
		if (service == null) {
			logger.error("不支持的服务", code);
			throw new YrdException(YrdServiceExceptionEnum.NON_EXISTS_SERVICE);
		}
		return service;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

		this.applicationContext = applicationContext;
	}
	
	@Override
	public void destroy() throws Exception {
		serviceMap.clear();
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		loadProcessor();
	}
	
	/**
	 * 加载业务处理器
	 */
	public void loadProcessor() {
		logger.info("eloaservice业务处理器序列开始加载......");
		Map<?, ?> taskBeansMap = applicationContext.getBeansOfType(AppService.class);
		if (taskBeansMap.isEmpty()) {
			logger.error("service业务处理器序列加载失败......");
			return;
		}
		// 循环加入业务执行器
		for (Object object : taskBeansMap.values()) {
			AppService service = (AppService) object;
			serviceMap.put(service.getServiceType().getCode(), service);
			logger.info("装载Service业务组件:{}", service.getServiceType());
		}
	}
	
}
