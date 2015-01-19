package com.icebreak.p2p.util;

public class YrdConstants {
	
	public static class CommonConfig {
		/**
		 * 实名认证通知类型
		 */
		public static String REAL_NAME_NOTIFY_TYPE = "SMS";
		public static String INVEST_NOTIFY_TYPE = "SMS";
		public static double AVAILABLE_INVEST_MULTIPLE = 1.0;
		public static double FESTIVAL = 1.0;

		/** 还款处理日期 */
		public static String REPAY_PROCESSDAYS = "1";
		
	}
	
	public static class TradeStatus {
		/**
		 * 待审核
		 */
		public final static short CHECK_PENDING = 0;
		/**
		 * 募集中-待成立
		 */
		public final static short TRADING = 1;
		/**
		 * 已成立
		 */
		public final static short REPAYING = 2;
		/**
		 * 交易完成
		 */
		public final static short REPAY_FINISH = 3;
		/**
		 * 交易失败
		 */
		public final static short FAILED = 4;
		/**
		 * 还款失败
		 */
		public final static short REPAYING_FAILD = 5;
		/**
		 * 担保公司审核中
		 */
		public final static short GUARANTEE_AUDITING = 6;
		/**
		 * 违约代偿完成
		 */
		public final static short COMPENSATORY_REPAY_FINISH = 7;
		/**
		 * 待还款
		 */
		public final static short DOREPAY = 8;
	}
	
	public static class TransferComment {
		/**
		 * 平台分润
		 */
		public static String YRD_DIVISION = AppConstantsUtil.getProductName() + "分润转帐";
		/**
		 * 平台投资
		 */
		public static String YRD_INVEST = AppConstantsUtil.getProductName() + "投资转帐";
		/**
		 * 平台还款转账
		 */
		public static String YRD_REPAY = AppConstantsUtil.getProductName() + "还款转账";
		
		public static String YRD_TRADE_CHARGE = AppConstantsUtil.getProductName() + "交易手续费";
	}
	
	public static class SesssionKey {
		/**
		 * 签名银行key
		 */
		public static String SIGN_BANK_KEY = "SIGN_BANK_KEY";
		
	}
	
	public static class ChargeDetail {
		/**
		 * 服务性收费
		 */
		public static String CHARGE_DETAIL_NOTE = AppConstantsUtil.getProductName() + "金融服务性收费";
	}
	
	/**
	 * 消息通知常量
	 * @author CHARLEY
	 * 
	 */
	public static class MessageNotifyConstants {
		
		public static String INVEST_NOTIFY_TYPE = CommonConfig.INVEST_NOTIFY_TYPE;
		public static String GREETING_CONTENT = "尊敬的" + AppConstantsUtil.getProductName() + "金融会员：";
		public static final String ISNOTIFIED_NO = "NO";
		public static final String ISNOTIFIED_YES = "YES";
		public static String NOTIFY_TYPE_EMAIL = "EMAIL";
		public static String NOTIFY_TYPE_SMS = "SMS";
		public static String LOCAL_CLIENT = "yr";
		
		public static String PAYlOAN_FAILD_CONTENT = SmsConstantsProperty
			.getContent("PAYlOAN_FAILD_CONTENT");//付款失败";
		public static String UNFULL_LOANDEMAND_OVERDUE_CONTENT = SmsConstantsProperty
			.getContent("UNFULL_LOANDEMAND_OVERDUE_CONTENT");//您有一笔借款融资日期已达到,但未满标,借款名称：var1,申请借款金额：var2元,已融资金额：var3元,系统将自动处理交易为未成立状态,请登录: hostLink 进行核实, 如有任何疑问，请联系我公司客服进行处理!";
		public static String UNFULL_INVESTEDlOAND_OVERDUE_CONTENT = SmsConstantsProperty
			.getContent("UNFULL_INVESTEDlOAND_OVERDUE_CONTENT");//您在"+ AppConstantsUtil.getProductName()+ "金融平台的一笔投资,交易名称：var1,投资金额：var2元,融资期限已达到,但该笔融资未满标,系统将自动处理交易为未成立状态,该笔借款投资冻结金额将自动返回原有帐户，请登录: hostLink 进行核实, 如有任何疑问，请联系我公司客服进行处理!";
		public static String AUTO_CHANGE_EXPIRELOANTODOREPAY_CONTENT = SmsConstantsProperty
			.getContent("AUTO_CHANGE_EXPIRELOANTODOREPAY_CONTENT");//您有一笔借款已到期，借款名称：var1,实际借款金额：var2元,系统已自动为您转入待还款中，请及时处理还款，请登录: hostLink 进行核实, 如有任何疑问，请联系我公司客服进行处理！";
		public static String AUTO_PAYLOAN_CONTENT = SmsConstantsProperty
			.getContent("AUTO_PAYLOAN_CONTENT");//您有一笔借款自动还款失败,借款名称：var1,实际借款金额：var2元,原因：var3,请登录: hostLink 进行核实,如有任何疑问，请联系我公司客服进行处理！ ";
		public static String AUTO_PAYLOAN_PLAN_CONTENT = SmsConstantsProperty
			.getContent("AUTO_PAYLOAN_PLAN_CONTENT");
		public static String LOAN_DEMAND_INVESTED_CONTENT = SmsConstantsProperty
			.getContent("LOAN_DEMAND_INVESTED_CONTENT");//您在"+ AppConstantsUtil.getProductName()+ "金融平台的一笔借款已满额,该笔借款名称：var1,申请融资金额：var2元,已获融资：var3元,请登录: hostLink 进行核实,如有任何疑问,请联系我公司客服进行处理！";
		public static String REPAY_NOTIFY_LOANER_CONTENT = SmsConstantsProperty
			.getContent("REPAY_NOTIFY_LOANER_CONTENT");//您在"+ AppConstantsUtil.getProductName()+ "金融平台的一笔借款还款处理成功,借款名称：var1,实际借款金额：var2元,还款金额：var3元,还款时间：var4,请登录: hostLink 进行核实,如有任何疑问,请联系我公司客服进行处理！";
		public static String REPAY_NOTIFY_INVESTOR_CONTENT = SmsConstantsProperty
			.getContent("REPAY_NOTIFY_INVESTOR_CONTENT");//您在"+ AppConstantsUtil.getProductName()+ "金融平台的一笔投资已收到还款,交易名称：var1,投资金额：var2元, 获得收益：var4元,还款时间:var3,具体到账时间以实际到账时间为准,请登录: hostLink 进行核实,如有任何疑问,请联系我公司客服进行处理！";
		public static String REPAY_NOTIFY_LOANER_CONTENT_4PLAN = SmsConstantsProperty
			.getContent("REPAY_NOTIFY_LOANER_CONTENT_4PLAN");//您在"+ AppConstantsUtil.getProductName()+ "金融平台的一笔借款第var5期还款处理成功,借款名称：var1,实际借款金额：var2元,本期还款金额：var3元,还款时间：var4,请登录: hostLink 进行核实,如有任何疑问,请联系我公司客服进行处理！";
		public static String REPAY_NOTIFY_INVESTOR_CONTENT_4PLAN = SmsConstantsProperty
			.getContent("REPAY_NOTIFY_INVESTOR_CONTENT_4PLAN");//您在"+ AppConstantsUtil.getProductName()+ "金融平台的一笔投资已收到第var5期还款,交易名称：var1, 本期还款：var4元,还款时间:var3,具体到账时间以实际到账时间为准,请登录: hostLink 进行核实,如有任何疑问,请联系我公司客服进行处理！";	
		public static String LOAN_SUCCESS_LOANER_CONTENT = SmsConstantsProperty
			.getContent("LOAN_SUCCESS_LOANER_CONTENT");//您在"+ AppConstantsUtil.getProductName()+ "金融平台的一笔借款担保公司审核通过现已放款,交易名称：var1,实际借款金额：var2元,实际到账金额：var3元,放款时间:var4,具体到账时间以实际到账时间为准,请登录:hostLink进行核实,如有任何疑问,请联系我公司客服进行处理！";
		public static String REAL_NAME_AUTH_NOTIFY = SmsConstantsProperty
			.getContent("REAL_NAME_AUTH_NOTIFY");//请查看您在"+ AppConstantsUtil.getProductName()+ "金融平台提交的实名认证结果,账户名：var1, 认证状态：var2, 请登录: hostLink 进行核实,如有任何疑问,请联系我公司客服进行处理！";
		public static String DEMAND_CONFIRMED_NOTIFY = SmsConstantsProperty
			.getContent("DEMAND_CONFIRMED_NOTIFY");//借款人已确认一笔新的资金需求，名称：var1,融资金额：var2元，确认金额：var3元，请做好相关准备便于资金流转!";
		public static String REPAY_ADVANCED_NOTIFY = SmsConstantsProperty
			.getContent("REPAY_ADVANCED_NOTIFY");//一笔新的交易即将进入还款期，名称：var1, 借款金额：var2元，还款时间：var3，请做好相关准备便于资金流转!";
		public static String PDF_OK_TO_INVESTOR = SmsConstantsProperty
			.getContent("PDF_OK_TO_INVESTOR");//您在"+ AppConstantsUtil.getProductName()+ "金融的一笔投资, 交易名称：var1,担保公司已上传合同与担保函，请登录: hostLink 进行核实,如有任何疑问,请联系我公司客服进行处理！";
		public static String RECEIPT_OK_TO_INVESTOR = SmsConstantsProperty
			.getContent("RECEIPT_OK_TO_INVESTOR");//您在"+ AppConstantsUtil.getProductName()+ "金融的一笔投资已经成立, 借款名称：var1,担保公司已上传投资凭证,请登录: hostLink 进行核实,如有任何疑问,请联系我公司客服进行处理！";	
		public static String SUCCESS_INVEST_NOTIFY = SmsConstantsProperty
			.getContent("SUCCESS_INVEST_NOTIFY");//您在"+ AppConstantsUtil.getProductName()+ "金融的一笔投资操作已成功，借款名称：var1，投资金额：var2元，如有任何疑问,请联系我公司客服进行处理！";
	}
	
	/**
	 * 合同状态
	 * @author CHARLEY
	 * 
	 */
	
	public static class GuaranteeTradeStatus {
		/**
		 * 待审核
		 */
		public final static String GUARANTEE_CREATE = "合约创建";
		/**
		 * 募集中
		 */
		public final static String GUARANTEE_SIGN = "合约签发";
		/**
		 * 还款中
		 */
		public final static String GUARANTEE_IMPLEMENT = "合约履行中";
		/**
		 * 交易完成
		 */
		public final static String GUARANTEE_FINISH = "合约完毕";
		/**
		 * 交易失败
		 */
		public final static String GUARANTEE_FAILD = "合约失效";
		/**
		 * 合约违约
		 */
		public final static String GUARANTEE_BROKEN = "合约违约";
		/**
		 * 担保机构审核中
		 */
		public final static String GUARANTEE_AUDITING = "担保机构审核中";
		/**
		 * 违约代偿完成
		 */
		public final static String COMPENSATORY_REPAY_FINISH = "违约代偿完成";
	}
	
	/**
	 * 时间相关
	 * @author CHARLEY
	 * 
	 */
	public static class TimeRelativeConstants {
		public final static int DAYSOFAYEAR = 360;
	}
	
	/**
	 * 充值相关
	 * @author CHARLEY
	 * 
	 */
	public static class CoreEngineConstants {
		public final static String BUSINESSNO = "710";
		public final static String RECHARGEFROM = "EASY_LOAN";
	}
	
	public static class RoleId {
		/**
		 * 操作员
		 */
		public static final String OPERATOR = "14";
		/**
		 * 投资人
		 */
		public static String INVESTOR = "12";
		/**
		 * 借款人
		 */
		public static String LOANER = "13";
		/**
		 * 经纪人
		 */
		public static String BROKER = "11";
		/**
		 * 运营机构
		 */
		public static String MARKETING = "10";
		/**
		 * 保荐机构
		 */
		public static String SPONSOR = "9";
		/**
		 * 担保机构
		 */
		public static String GUARANTEE = "8";
		/**
		 * 平台金融
		 */
		public static String YRD = "7";
	}
	
	/**
	 * 用户相关
	 * @author CHARLEY
	 * 
	 */
	public static class UserInfoConstants {


	}
	
	public static class TradeViewCanstants {
		public static String TRADE_DEFAULT = "状态暂无";
		public static String TRADE_TRADING = "待成立";
		public static String TRADE_FAILD = "未成立";
		public static String TRADE_REPAYING = "已成立";
		public static String TRADE_FINISH = "已还款";
		public static String TRADE_REPAY_FAILD = "到期未还款";
		public static String TRADE_PAID = "已收款";
		public static String TRADE_NOT_PAID = "待收款";
		public static String GUARANTEE_AUDITING = "担保公司审核中";
		public static String DOREPAY = "待还款";
		public static String COMPENSATORY_REPAY_FINISH = "违约代偿完成";
	}
	
	public static class RepayConfigureCanstants {
		static String processDays = CommonConfig.REPAY_PROCESSDAYS;
		/**
		 * 还款处理日期
		 */
		public static int PROCESSIONREPAYDAYS = Integer.parseInt(processDays == null ? "1"
			: processDays);
	}
	
	public static class GuaranteeAuthFilterCanstants {
		public static String FILTEREDGUARANTEES = "";
		public static String GUARANTEE_EDU = "重庆市担保有限责任公司";
	}
	
	public static class BankCodes {
		/**
		 * ABC,BOC,COMM,CCB,CEB,CIB,CMBC,CITIC,CQRCB,ICBC,PSBC,SPDB,UNION,CQCB,
		 * GDB,SDB,HXB,CQTGB,PINGANBANK,BANKSH
		 */
		public static String sychBankCodes = "ABC,BOC,COMM,CCB,CEB,CIB,CMBC,CQRCB,ICBC,PSBC,SPDB,UNION,CQCB,GDB,SDB,HXB,CQTGB,PINGANBANK,BANKSH";
		/**
		 * CMB
		 */
		public static String asychBankCodes = "CMB,CITIC";
		/**
		 * 绑卡验证过滤CITIC
		 */
		public static String filteredBankCodes = "CITIC";
	}
	
}
