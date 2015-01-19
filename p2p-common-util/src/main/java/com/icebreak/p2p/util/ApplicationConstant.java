package com.icebreak.p2p.util;

import java.io.File;

public class ApplicationConstant {
	public static String FILE_PATH_ROOT = "upload" + File.separator + "file" + File.separator; //文件相对web目录路径	
	public static String HTTP_PATH_ROOT = "upload/file/"; //http访问目录路径
	
	public final static int STORE_FILE_TYPE_ABSOLUTE_PATH = 1; //文件存储 方式  绝对路径
	public final static int STORE_FILE_TYPE_RELATIVE_PATH = 0; //文件存储 方式  相对路径 相对web目录
	public static int CURRENT_STORE_FILE_TYPE = 0;
	public static String HTTP_DOMAIN_URL = "http://121.41.46.230"; //http访问域名 绝对路径方式使用
	
	public final static int UPLOAD_FILE_MAX_SIZE = 2 * 1024 * 1024; //文件上传最大尺寸
	
	public static String ENVIRONMENT_TYPE_TEST = "TEST";
	public static String ENVIRONMENT_TYPE_DEV = "DEV";
	public static String ENVIRONMENT_TYPE_ONLINE_TEST = "ONLINE_TEST";
	public static String ENVIRONMENT_TYPE_PRODUCTION = "PRODUCTION";
	public final static String SYS_SERVICE_PHONE_KEY = "SYS_SERVICE_PHONE_KEY";
	
	/** 托管机构商户id */
	public final static String SYS_PARAM_YJF_BUSINESS_USER_ID = "SYS_PARAM_YJF_BUSINESS_USER_ID";
	/** 安全验证key */
	public final static String SYS_PARAM_SECURITY_CHECK_KEY = "SYS_PARAM_SECURITY_CHECK_KEY";
	/** openApi访问地址 */
	public final static String SYS_PARAM_OPEN_API_URL_KEY = "SYS_PARAM_OPEN_API_URL_KEY";
	/** 回推地址访问地址 */
	public final static String SYS_PARAM_RETURN_URL_KEY = "RETURN_URL_KEY";
	
	/** 图片服务器地址Key */
	public final static String SYS_PARAM_IMAGE_URL_KEY = "IMAGE_URL_KEY";
	/** 产品key */
	public final static String SYS_PARAM_PRODUCT_KEY = "SYS_PARAM_PRODUCT_KEY";
	/** 平台名称 */
	public final static String SYS_PARAM_PLATFORM_NAME = "SYS_PARAM_PLATFORM_NAME";
	/** 产品名称 */
	public final static String SYS_PARAM_PRODUCT_NAME = "SYS_PARAM_PRODUCT_NAME";
	/** 外部订单号的（中间代码，不同产品不一样） */
	public final static String SYS_PARAM_OUT_BIZ_NUMBER = "SYS_PARAM_OUT_BIZ_NUMBER";
	public static final String TRANSFER_FREEZE_TYPE = "YZZ_GUARANTEE_FREEZE";
	/** 用户前缀 */
	public final static String SYS_YRD_PREFIXION = "SYS_YRD_PREFIXION";
	
	/** host配置 */
	public static final String SYS_PARAM_HOST = "SYS_PARAM_HOST";
	/** host通知类型 EMAIL|SMS */
	public static final String SYS_PARAM_NOTIFY_TYPE = "SYS_PARAM_NOTIFY_TYPE";
	
	/** 文件上传目录 */
	public static final String SYS_PARAM_YRD_UPLOAD_FOLDER = "SYS_PARAM_YRD_UPLOAD_FOLDER";
	
	public static final String SYS_PARAM_YRD_TRANSFER_LIMIT = "SYS_PARAM_YRD_TRANSFER_LIMIT";
	/** 平台分润账户 */
	public static final String SYS_PARAM_PROFIT_SHARING_ACCOUNT = "SYS_PARAM_PROFIT_SHARING_ACCOUNT";
	
	/** 收银台的url */
	public static final String SYS_PARAM_CASHIER_HOST = "SYS_PARAM_CASHIER_HOST";
	
	/** 平台在托管机构平台中的用户ID:用于存储中转资金 */
	public static final String SYS_PARAM_EXCHANGE_ACCOUNT = "SYS_PARAM_EXCHANGE_ACCOUNT";
	/** 收费编码 */
	public static final String SYS_PARAM_TRADE_BIZPRODUCTCODE = "SYS_PARAM_TRADE_BIZPRODUCTCODE";
	/** 默认经济人编码 */
	public static final String SYS_PARAM_YRD_PBROKER_USER_NAME = "SYS_PARAM_YRD_PBROKER_USER_NAME";
	/** fop 字体库 */
	public static final String SYS_PARAM_YRD_FOP_FONT = "SYS_PARAM_YRD_FOP_FONT";
	
	/** 提现收费code */
	public static final String SYS_PARAM_WITHDRAW_CHARGE_CODE = "SYS_PARAM_WITHDRAW_CHARGE_CODE";
	/** 网银充值code */
	public static final String SYS_PARAM_BANKB2C_CHARGE_CODE = "SYS_PARAM_BANKB2C_CHARGE_CODE";
	/** 平台地址 */
	public static final String SYS_PARAM_PLATFORM_ADDRESS = "SYS_PARAM_PLATFORM_ADDRESS";
	
	public static final String SYS_PARAM_YRD_CS_EMAIL = "SYS_PARAM_YRD_CS_EMAIL";
	
	public static final String SYS_PARAM_YRD_CS_MOBILE = "SYS_PARAM_YRD_CS_MOBILE";
	
	public final static String JJRAGENTPREFIX = "K"; //经纪人下的投资人前缀??????
	
	public final static long EMAIL_TEMPLATE_ID = 19L;
	
	public final static double AVAILABLE_INVEST_MULTIPLE = 1;
	
	/** 邮件发送服务器 */
	public static final String SYS_PARAM_MAIL_SERVER = "SYS_PARAM_MAIL_SERVER";
	/** 邮件发送服务端口 */
	public static final String SYS_PARAM_MAIL_SERVERPORT = "SYS_PARAM_MAIL_SERVERPORT";
	/** 发件人地址 */
	public static final String SYS_PARAM_MAIL_USERNAME = "SYS_PARAM_MAIL_USERNAME";
	/** 发件人邮箱密码 */
	public static final String SYS_PARAM_MAIL_PASSWORD = "SYS_PARAM_MAIL_PASSWORD";
	/** 发件人别名 */
	public static final String SYS_PARAM_MAIL_NICKNAME = "SYS_PARAM_MAIL_NICKNAME";
	/** 发件人地址 */
	public static final String SYS_PARAM_MAIL_SENDERNAME = "SYS_PARAM_MAIL_SENDERNAME";

    public static final String SYS_PARAM_BROKER_ISNUMBERED = "SYS_PARAM_BROKER_ISNUMBERED";

    public static final String SYS_PARAM_TASK_TIMER_RUNIP = "SYS_PARAM_TASK_TIMER_RUNIP";

	
	public static EnvironmentConfig ENV_CONFIG = new EnvironmentConfig();
	
	public int getCurrentStoreFileType() {
		return CURRENT_STORE_FILE_TYPE;
	}
	
	/** 经纪人 */
	public final static long ROLE_ID_BROKER = 11L;
	/** 投资人 */
	public final static long ROLE_ID_INVESTOR = 12L;
	/**  ICP号*/
	public static final String SYS_PARAM_PRODUCT_ICP = "SYS_PARAM_PRODUCT_ICP";
	/**  QQ号*/
	public static final String SYS_PARAM_PRODUCT_QQ = "SYS_PARAM_PRODUCT_QQ";
	public static final String SYS_PARAM_MAIL_LOAN_REQUEST = "SYS_PARAM_MAIL_LOAN_REQUEST";
	/**  通用参数*/
	public static final String SYS_PARAM_ALL_COMMON = "SYS_PARAM_ALL_COMMON";
	/**默认经纪人编号*/
	public static final String SYS_PARAM_DEFAULT_BROKER_NUMBER = "SYS_PARAM_DEFAULT_BROKER_NUMBER";

	/** 项目默认缩略图路径配置 **/
	public static final String SYS_PARAM_PROJECT_DEFAULT_THUMBNAIL_URL = "SYS_PARAM_PROJECT_DEFAULT_THUMBNAIL_URL";
}
