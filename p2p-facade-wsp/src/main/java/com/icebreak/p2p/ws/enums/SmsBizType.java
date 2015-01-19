package com.icebreak.p2p.ws.enums;

import java.util.ArrayList;
import java.util.List;

public enum SmsBizType {
	
	REGISTER("register", "注册", 10),
	
	ACTIVATE("activate", "激活", 10),
	
	ForgetLoginPassWord("ForgetLoginPassWord", "找回登录密码", 10),
	
	ForgetPayPassWord("ForgetPayPassWord", "找回支付密码", 5),
	
	PAYPASS("payPass", "修改支付密码", 5),
	
	ADDCELLPHONE("addcellphone", "申请绑定手机号码", 10),
	
	CELLPHONE("cellphone", "修改绑定手机号码", 10),
	
	OLDCELLPHONE("oldCellphone", "修改手机号码，旧手机", 10),
	
	NEWCELLPHONE("newCellphone", "修改手机号码，新手机", 10),
	
	PERSONAL("personal", "修改个人信息", 10),
	
	ADDOPERATOR("addOperator", "添加成员", 50),
	
	EDITOPERATOR("editOperator", "修改成员", 50),
	
	AUTHORIZED("authorized", "权限分配", 50),
	
	OPERATORFIRSTTIME("operatorFirstTime", "操作员首次登录", 10),
	
	WITHDRAW("withdraw", "提现", 10),
	
	TRANSFER("transfer", "转账", 20),
	
	INVEST("invest", "投资", 20),
	
	TRANSFERTOCARD("transferToCard", "转账到卡", 20),
	
	BATCHTRANSFERTOCARD("batchTransferToCard", "批量转账到卡", 10),
	
	WITHDRAWVERIFY("withdrawVerify", "提现审核", 10),
	
	INNERVERIFYTRANSFER("innerVerifyTransfer", "站内转账审核", 10),
	
	CARDVERIFYTRANSFER("cardVerifyTransfer", "转账到卡审核", 10),
	
	BATCHVERIFYTRANSFER("batchVerifyTransfer", "批量审核", 10),
	
	DEDUCT("deduct", "申请代扣", 10),
	
	SIGN("sign", "签约", 10),
	
	SALARY("salary", "发工资", 10),
	
	DEPOSIT("deposit", "充值", 20),
	
	INVESTMENT("investment", "投资", 20),
	
	REPAYMENT("repayment", "还款", 20);
	
	private final String	code;
	private final String	message;
	private final int		daySendCount;
	
	private SmsBizType(String code, String message, int daySendCount) {
		this.code = code;
		this.message = message;
		this.daySendCount = daySendCount;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getMessage() {
		return message;
	}
	
	public int getDaySendCount() {
		return daySendCount;
	}
	
	/**
	 * @return Returns the code.
	 */
	public String code() {
		return code;
	}
	
	/**
	 * @return Returns the message.
	 */
	public String message() {
		return message;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return LogResultEnum
	 */
	public static SmsBizType getByCode(String code) {
		for (SmsBizType _enum : values()) {
			if (_enum.getCode().equalsIgnoreCase(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<LogResultEnum>
	 */
	public List<SmsBizType> getAllEnum() {
		List<SmsBizType> list = new ArrayList<SmsBizType>();
		for (SmsBizType _enum : values()) {
			list.add(_enum);
		}
		return list;
	}
	
	/**
	 * 获取全部枚举值
	 * 
	 * @return List<String>
	 */
	public List<String> getAllEnumCode() {
		List<String> list = new ArrayList<String>();
		for (SmsBizType _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
	
}
