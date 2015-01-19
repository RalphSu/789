package com.icebreak.p2p.base;

import org.springframework.beans.factory.annotation.Autowired;

import com.icebreak.p2p.common.services.SysParameterService;
import com.icebreak.p2p.integration.openapi.context.OpenApiContext;
import com.icebreak.p2p.util.ApplicationConstant;
import com.icebreak.p2p.util.BusinessNumberUtil;
import com.yiji.openapi.sdk.Constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenApiBaseContextServiceBase {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	protected SysParameterService sysParameterService;

	protected OpenApiContext getOpenApiContext() {
		OpenApiContext openApiContext = new OpenApiContext();
		openApiContext.setPartnerId(Constants.PARTNER_ID);
		openApiContext.setSecurityCheckKey(Constants.SECRETKEY);
		openApiContext.setOpenApiUrl(Constants.OPENAPI_GATEWAY);

		openApiContext
				.setNotifyUrl(sysParameterService
						.getSysParameterValue(ApplicationConstant.SYS_PARAM_RETURN_URL_KEY));
		openApiContext.setOpenApiBizNo(BusinessNumberUtil.gainOutBizNoNumber());
		return openApiContext;
	}
}
