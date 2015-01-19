package com.icebreak.p2p.common.services.impl;

import com.icebreak.p2p.common.services.SysParameterService;
import com.icebreak.p2p.dal.daointerface.SysParamDAO;
import com.icebreak.p2p.dal.dataobject.SysParamDO;
import com.icebreak.p2p.localService.SysClearCacheServiceClient;
import com.icebreak.p2p.util.AppConstantsUtil;
import com.icebreak.p2p.util.ApplicationConstant;
import com.icebreak.p2p.util.MD5Util;
import com.icebreak.p2p.util.SystemConfig;
import com.icebreak.p2p.ws.base.PageComponent;
import com.icebreak.p2p.ws.order.SysParamOrder;
import com.icebreak.p2p.ws.result.P2PBaseResult;
import com.icebreak.p2p.ws.service.ResultEnum;
import com.icebreak.p2p.ws.service.query.order.SysParamQueryOrder;
import com.icebreak.p2p.ws.service.query.result.QueryBaseBatchResult;
import com.icebreak.util.lang.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("sysParameterService")
public class SysParameterServiceImpl implements SysParameterService, InitializingBean {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	SysParamDAO sysParamDAO;
	@Autowired
	SysClearCacheServiceClient sysClearCacheServiceClient;
	
	private static Map<String, String> paramMap = null;
	
	@Override
	public String getSysParameterValue(String paramName) {
		if (paramMap == null) {
			initParamMap();
		}
		return paramMap.get(paramName);
	}
	
	private SysParamDO getDbValue(String paramName) {
		SysParamDO sysParamDO = sysParamDAO.findById(paramName);
		return sysParamDO;
	}
	
	/**
	 * 
	 */
	private synchronized void initParamMap() {
		paramMap = new HashMap<String, String>();
		synchronized (paramMap) {
			List<SysParamDO> paramDOs = sysParamDAO.findAll();
			if (paramDOs != null) {
				for (SysParamDO item : paramDOs) {
					paramMap.put(item.getParamName(), item.getParamValue());
				}
			}
		}
	}
	
	@Override
	public List<SysParamDO> getSysParameterValueList(String paramName) {
		return sysParamDAO.findByLike(paramName + "%");
	}
	
	@Override
	public void clearCache() {
		synchronized (SysParameterServiceImpl.class) {
			if (paramMap != null) {
				paramMap.clear();
				paramMap = null;
				AppConstantsUtil.clear();
				try {
					afterPropertiesSet();
				} catch (Exception e) {
					logger.error("afterPropertiesSet is error", e);
				}
			}
		}
	}
	
	@Override
	public SysParamDO getSysParameterValueDO(String paramName) {
		return getDbValue(paramName);
	}
	
	@Override
	public P2PBaseResult updateSysParameterValueDO(SysParamOrder sysParamOrder) {
		P2PBaseResult result = new P2PBaseResult();
		logger.info("sysParamOrder，sysParamOrder={}", sysParamOrder);
		try {
			
			sysParamOrder.check();
			SysParamDO sysParamDO = new SysParamDO();
			staticCopy(sysParamOrder, sysParamDO);
			sysParamDAO.update(sysParamDO);
			sysClearCacheServiceClient.clearCache();
			result.setSuccess(true);
			
		} catch (IllegalArgumentException e) {
			result.setSuccess(false);
			result.setResultEnum(ResultEnum.INCOMPLETE_REQ_PARAM);
			result.setMessage(ResultEnum.INCOMPLETE_REQ_PARAM.getMessage() + "["
								+ e.getMessage() + "]");
		} catch (DataAccessException e) {
			logger.error("数据库异常:e={}", e.getMessage(), e);
			result.setSuccess(false);
			result.setResultEnum(ResultEnum.DATABASE_EXCEPTION);
			result.setMessage(ResultEnum.DATABASE_EXCEPTION.getMessage());
		} catch (Exception e) {
			logger.error("未知异常:e={}", e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
	@Override
	public P2PBaseResult insertSysParameterValueDO(SysParamOrder sysParamOrder) {
		P2PBaseResult result = new P2PBaseResult();
		logger.info("sysParamOrder，sysParamOrder={}", sysParamOrder);
		try {
			sysParamOrder.check();
			SysParamDO sysParamDO = new SysParamDO();
			staticCopy(sysParamOrder, sysParamDO);
			sysParamDAO.insert(sysParamDO);
			sysClearCacheServiceClient.clearCache();
			result.setSuccess(true);
			
		} catch (IllegalArgumentException e) {
			result.setSuccess(false);
			result.setResultEnum(ResultEnum.INCOMPLETE_REQ_PARAM);
			result.setMessage(ResultEnum.INCOMPLETE_REQ_PARAM.getMessage() + "["
								+ e.getMessage() + "]");
		} catch (DataAccessException e) {
			logger.error("数据库异常:e={}", e.getMessage(), e);
			result.setSuccess(false);
			result.setResultEnum(ResultEnum.DATABASE_EXCEPTION);
			result.setMessage(ResultEnum.DATABASE_EXCEPTION.getMessage());
		} catch (Exception e) {
			logger.error("未知异常:e={}", e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
	@Override
	public void deleteSysParameterValue(String paramName) {
		sysParamDAO.deleteByParamName(paramName);
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		try {
			SystemConfig config = new SystemConfig();
			String code = this.getSysParameterValue(ApplicationConstant.SYS_PARAM_PRODUCT_KEY);
			String platformName = this
				.getSysParameterValue(ApplicationConstant.SYS_PARAM_PLATFORM_NAME);
			String productName = this
				.getSysParameterValue(ApplicationConstant.SYS_PARAM_PRODUCT_NAME);
			if (StringUtil.equals(code, makeProductKey(platformName, productName))) {
				logger.info("init sucess");
			} else {
				logger.info("error sucess");
			}
			config.setPlatformName(platformName);
			config.setProductName(productName);
			initConfigValue(config);
			AppConstantsUtil.init(config);
		} catch (Exception e) {
			logger.info("init complete Exception", e);
		}
		
	}
	
	/**
	 * @param config
	 */
	protected void initConfigValue(SystemConfig config) {
		config.setOutBizNumber(this
			.getSysParameterValue(ApplicationConstant.SYS_PARAM_OUT_BIZ_NUMBER));
		config.setYrdPrefixion(this.getSysParameterValue(ApplicationConstant.SYS_YRD_PREFIXION));
		config.setYrdUploadFolder(this
			.getSysParameterValue(ApplicationConstant.SYS_PARAM_YRD_UPLOAD_FOLDER));
		config.setImageServerUrl(this
			.getSysParameterValue(ApplicationConstant.SYS_PARAM_IMAGE_URL_KEY));
		config.setYrdTransferLimit(this
			.getSysParameterValue(ApplicationConstant.SYS_PARAM_YRD_TRANSFER_LIMIT));
		config.setCustomerServicePhone(this
			.getSysParameterValue(ApplicationConstant.SYS_SERVICE_PHONE_KEY));
		config.setExchangeAccount(this
			.getSysParameterValue(ApplicationConstant.SYS_PARAM_EXCHANGE_ACCOUNT));
		config.setProfitSharingAccount(this
			.getSysParameterValue(ApplicationConstant.SYS_PARAM_PROFIT_SHARING_ACCOUNT));
		config.setTradeBizProductCode(this
			.getSysParameterValue(ApplicationConstant.SYS_PARAM_TRADE_BIZPRODUCTCODE));
		config.setHostUrl(this.getSysParameterValue(ApplicationConstant.SYS_PARAM_HOST));
		config.setHostHttpUrl(this
			.getSysParameterValue(ApplicationConstant.SYS_PARAM_RETURN_URL_KEY));
		
		config.setDefaultBrokerUserName(this
			.getSysParameterValue(ApplicationConstant.SYS_PARAM_YRD_PBROKER_USER_NAME));
		
		config.setProductICP(this
				.getSysParameterValue(ApplicationConstant.SYS_PARAM_PRODUCT_ICP));
		config.setProductQQ(this
				.getSysParameterValue(ApplicationConstant.SYS_PARAM_PRODUCT_QQ));
		config.setAllCommon(this
				.getSysParameterValue(ApplicationConstant.SYS_PARAM_ALL_COMMON));
		
		config.setLoanRequestMail(this
				.getSysParameterValue(ApplicationConstant.SYS_PARAM_MAIL_LOAN_REQUEST));
		
		config.setDeafaultBrokerNO(this
				.getSysParameterValue(ApplicationConstant.SYS_PARAM_DEFAULT_BROKER_NUMBER));
		
		//		config.setQftBrokerUserName(this
		//			.getSysParameterValue(ApplicationConstant.SYS_PARAM_QFT_PBROKER_USER_NAME));
		
		config.setYrdFopFontFolder(this
			.getSysParameterValue(ApplicationConstant.SYS_PARAM_YRD_FOP_FONT));
		config.setWithdrawChargeCode(this
			.getSysParameterValue(ApplicationConstant.SYS_PARAM_WITHDRAW_CHARGE_CODE));
		config.setBankB2CChargeCode(this
			.getSysParameterValue(ApplicationConstant.SYS_PARAM_BANKB2C_CHARGE_CODE));
		config.setPlatformAddress(this
			.getSysParameterValue(ApplicationConstant.SYS_PARAM_PLATFORM_ADDRESS));

		
		config.setCustomerServiceEmail(this
				.getSysParameterValue(ApplicationConstant.SYS_PARAM_YRD_CS_EMAIL));
		config.setCustomerServiceMobile(this
				.getSysParameterValue(ApplicationConstant.SYS_PARAM_YRD_CS_MOBILE));
		
		
		config.setMailServer(this.getSysParameterValue(ApplicationConstant.SYS_PARAM_MAIL_SERVER));
		config.setMailServerport(this
			.getSysParameterValue(ApplicationConstant.SYS_PARAM_MAIL_SERVERPORT));
		config.setMailUsername(this
			.getSysParameterValue(ApplicationConstant.SYS_PARAM_MAIL_USERNAME));
		config.setMailPassword(this
			.getSysParameterValue(ApplicationConstant.SYS_PARAM_MAIL_PASSWORD));
		config.setMailNickName(this
			.getSysParameterValue(ApplicationConstant.SYS_PARAM_MAIL_NICKNAME));
		config.setMailSenderName(this
			.getSysParameterValue(ApplicationConstant.SYS_PARAM_MAIL_SENDERNAME));
        config.setBrokerIsNumbered(this.getSysParameterValue(ApplicationConstant.SYS_PARAM_BROKER_ISNUMBERED));

        config.setTaskTimerIp(this.getSysParameterValue(ApplicationConstant.SYS_PARAM_TASK_TIMER_RUNIP));

		//项目默认缩略图
		config.setProjectDefaultThumbnailUrl(this.
				getSysParameterValue(ApplicationConstant.SYS_PARAM_PROJECT_DEFAULT_THUMBNAIL_URL));
	}
	
	@Override
	public QueryBaseBatchResult<SysParamDO> querySysPram(SysParamQueryOrder sysParamQueryOrder) {
		QueryBaseBatchResult<SysParamDO> batchResult = new QueryBaseBatchResult<SysParamDO>();
		try {
			sysParamQueryOrder.check();
			List<SysParamDO> pageList = new ArrayList<SysParamDO>(
				(int) sysParamQueryOrder.getPageSize());
			long totalCount = sysParamDAO.paramInfoQueryCount(sysParamQueryOrder.getParamName());
			PageComponent component = new PageComponent(sysParamQueryOrder, totalCount);
			List<SysParamDO> recordList = sysParamDAO.paramInfoQueryList(
				sysParamQueryOrder.getParamName(), sysParamQueryOrder.getLimitStart(),
				sysParamQueryOrder.getPageSize());
			pageList.addAll(recordList);
			batchResult.initPageParam(component);
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
		} catch (IllegalArgumentException e) {
			batchResult.setSuccess(false);
			batchResult.setResultEnum(ResultEnum.INCOMPLETE_REQ_PARAM);
			batchResult.setMessage(e.getMessage());
		} catch (Exception e) {
			batchResult.setSuccess(false);
			batchResult.setResultEnum(ResultEnum.DATABASE_EXCEPTION);
			logger.error(e.getLocalizedMessage(), e);
		}
		
		return batchResult;
	}
	
	private static String makeProductKey(String platName, String productName) {
		String sKey = "bo15300";
		return (MD5Util.getMD5_32(platName + "_" + productName + "_" + sKey));
	}
	
	private static void staticCopy(SysParamOrder sysParamOrder, SysParamDO sysParamDO) {
		sysParamDO.setParamValue(sysParamOrder.getParamValue());
		sysParamDO.setParamName(sysParamOrder.getParamName());
		sysParamDO.setRawUpdateTime(sysParamOrder.getRawUpdateTime());
		sysParamDO.setRawAddTime(sysParamOrder.getRawAddTime());
		sysParamDO.setExtendAttributeOne(sysParamOrder.getExtendAttributeOne());
		sysParamDO.setExtendAttributeTwo(sysParamOrder.getExtendAttributeOne());
		
	}
	
	public static void main(String[] args) {
		System.out.println(makeProductKey("软件科技有限公司", "财神在线"));
	}
}
