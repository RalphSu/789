package com.icebreak.p2p.util;

public class AppConstantsUtil {
	private static SystemConfig staticConfig;
	
	public synchronized static void init(SystemConfig config) {
		if (staticConfig == null) {
			staticConfig = config;
		}
	}
	
	public synchronized static void clear() {
		staticConfig = null;
	}
	
	public static String getYrdPrefixion() {
		return staticConfig.getYrdPrefixion();
	}
	
	public static String getPlatformName() {
		return staticConfig.getPlatformName();
	}
	
	public static String getProductName() {
		return staticConfig.getProductName();
	}
	
	public static String getOutBizNumber() {
		return staticConfig.getOutBizNumber();
	}
	/**
	 * ICP备案号
	 * @return
	 */
	public static String getProductICP() {
		return staticConfig.getProductICP();
	}
	/**
	 * 通用参数
	 * @return
	 */
	public static String getAllCommon(){
		return staticConfig.getAllCommon();
	}
	/**
	 * QQ
	 * @return
	 */
	public static String getProductQQ(){
		return staticConfig.getProductQQ();
	}
	/**
	 * 默认经纪人编号
	 * @return
	 */
	public static String getDeafaultBrokerNO() {
		
		return staticConfig.getDeafaultBrokerNO();
	}
	
	/**
	 * 接收融资请求邮箱地址
	 * @return
	 */
	public static String getLoanRequestMail() {
		
		return staticConfig.getLoanRequestMail();
	}
	/**
	 * 上传文件服器路径
	 * @return
	 */
	public static String getYrdUploadFolder() {
		return staticConfig.getYrdUploadFolder();
	}
	
	/**
	 * fop 字体库配置文件（userconfig.xml）路径
	 * @return
	 */
	public static String getYrdFopFontFolder() {
		return staticConfig.getYrdFopFontFolder();
	}
	
	/**
	 * 图片服务器url
	 * @return
	 */
	public static String getImageServerUrl() {
		return staticConfig.getImageServerUrl();
	}
	
	/**
	 * 的客服电话座机
	 * @return
	 */
	public static String getCustomerServicePhone() {
		return staticConfig.getCustomerServicePhone();
	}
	
	/**
	 * 的客服手机
	 * @return
	 */
	public static String getCustomerServiceMobile() {
		return staticConfig.getCustomerServiceMobile();
	}
	
	/**
	 * 客服邮箱
	 * @return
	 */
	public static String getCustomerServiceEmail() {
		return staticConfig.getCustomerServiceEmail();
	}
	
	/**
	 * 有转账权限的特殊用户
	 * @return
	 */
	public static String getYrdTransferLimit() {
		return staticConfig.getYrdTransferLimit();
	}
	
	/**
	 * host配置
	 * @return
	 */
	public static String getHostUrl() {
		return staticConfig.getYrdTransferLimit();
	}
	
	/**
	 * 中间结算账户
	 * @return
	 */
	public static String getExchangeAccount() {
		return staticConfig.getExchangeAccount();
	}
	
	/**
	 * 分润账户
	 * @return
	 */
	public static String getProfitSharingAccount() {
		return staticConfig.getProfitSharingAccount();
	}
	
	/**
	 * 收费产品编码
	 * @return
	 */
	public static String getTradeBizProductCode() {
		return staticConfig.getTradeBizProductCode();
	}
	
	/**
	 * 获取当前应用的url
	 * @return
	 */
	public static String getHostHttpUrl() {
		return staticConfig.getHostHttpUrl();
	}
	
	/**
	 * 默认经济人账户用户名
	 * @return
	 */
	public static String getDefaultBrokerUserName() {
		return staticConfig.getDefaultBrokerUserName();
	}
	
	/**
	 * 提现收费code
	 * @return
	 */
	public static String getWithdrawChargeCode() {
		return staticConfig.getWithdrawChargeCode();
	}
	
	/**
	 * 网银收费code
	 * @return
	 */
	public static String getBankB2CChargeCode() {
		return staticConfig.getBankB2CChargeCode();
	}
	
	/**
	 * 公司地址
	 * @return
	 */
	public static String getPlatformAddress() {
		return staticConfig.getPlatformAddress();
	}
	
	public static String getYrdCustomerServiceEmail() {
		return staticConfig.getCustomerServiceEmail();
	}
	
	/**
	 * 邮件发送服务器配置
	 * @return
	 */
	public static String getMailServer() {
		return staticConfig.mailServer;
	}
	
	/**
	 * 邮件发送服务器配置
	 * @return
	 */
	public static String getMailServerport() {
		return staticConfig.mailServerport;
	}
	
	/**
	 * 邮件发送服务器配置
	 * @return
	 */
	public static String getMailUserName() {
		return staticConfig.mailUsername;
	}
	
	/**
	 * 邮件发送服务器配置
	 * @return
	 */
	public static String getMailPassword() {
		return staticConfig.mailPassword;
	}
	
	/**
	 * 邮件发送服务器配置
	 * @return
	 */
	public static String getMailNickName() {
		return staticConfig.mailNickName;
	}
	
	/**
	 * 邮件发送服务器配置
	 * @return
	 */
	public static String getMailSenderName() {
		return staticConfig.mailSenderName;
	}
	/**
	 * 当前p2p行业特性
	 * @return
	 */
	public static String getCurrentIndustry() {
		return staticConfig.currentIndustry;
	}


    public static boolean getBrokerIsNumber() {

        return StringUtil.equals("Y", staticConfig.brokerIsNumbered);
    }


    public static String getTaskTimerIp(){
        return staticConfig.getTaskTimerIp();
    }

	/***
	 * 项目默认缩略图
	 * @return
	 */
	public static String getProjectDefaultThumbnailUrl(){
		return staticConfig.getProjectDefaultThumbnailUrl();
	}


}
