package com.icebreak.p2p.rs.base.enums;

import java.util.ArrayList;
import java.util.List;

public enum APIServiceEnum {
	/**注册*/
	userRegister("register", "注册"),
	/**登录*/
	userLogin("doLogin", "登录"),
	/**找回密码*/
	forgotPasswordService("forgotPasswordService", "找回密码"),
	
	modifyPasswordService("modifyPasswordService", "修改密码"),
	
	personalInfoService("personalInfoService", "个人信息"),
	
	updateRealNameInfoService("updateRealNameInfoService", "更新实名信息"),
	
	sendRealNameService("sendRealNameService", "发送实名认证"),
	
	appUserActive("appUserActive", "app用户激活"),
	
	indexTradeDetail("indexTradeDetail", "项目详情查询"),
	
	indexTrade("indexTrade", "项目列表查询"),
	
	inverstRecordService("inverstRecordService", "投资记录"),
	
	appInvestDetail("appInvestDetail", "投资详情"),
	
	appInvest("appInvest", "投资"),
	
	appInvestorRecordService("appInvestorRecordService", "客户记录"),
	
	appInvestorInfoService("appInvestorInfoService", "客户详情"),
	
	appBrokerageRecordService("appBrokerageRecordService", "佣金记录"),
	
	appBrokerageDetail("appBrokerageDetail", "佣金详情"),
	
	queryAcountInfoService("queryAcountInfo", "查询用户资金帐户信息"),
	
	withdrawaslService("withdrawaslService", "划出(提现)服务"),
	
	withdrawasRecordService("withdrawasRecordService", "划出(提现)记录"),
	
	getBanksInfoService("getBanksInfoService", "银行信息查询"),
	
	deductService("deductService", "代扣服务"),
	
	deductRecordService("deductRecordService", "代扣记录"),
	
	addBankCardService("addBankCardService", "绑定银行卡"),
	
	validationBankCard("validationBankCard", "验证银行卡信息"),
	
	appMobileService("appMobileService", "手机信息服务"),
	
	appValidationUserNameService("appValidationUserNameService", "校验用户名"),
	
	appGetAllDistrictService("appGetAllDistrictService", "获取省市"),
	
	appFileUploadService("appFileUploadService", "手机文件上传服务"),
	
	appSetUserPwd("appSetUserPwd", "设置用户登录密码和支付密码"),
	
	appBindingMobileService("appBindingMobileService", "修改绑定手机"),
	
	appGetUserRoleService("appGetUserRoleService", "获取用户角色"),
	
	queryUserInfoService("queryUserInfoService", "按条件查询用户信息"),
	
	appGetUserMemberNoService("appGetUserMemberNoService", "获取用户编号");
	
	/** 枚举值 */
	private final String	code;
	
	/** 枚举描述 */
	private final String	message;
	
	/**
	 * 构造一个<code>YrdServiceEnum</code>枚举对象
	 *
	 * @param code
	 * @param message
	 */
	private APIServiceEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
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
	 * @return OpenApiServiceEnum
	 */
	public static APIServiceEnum getByCode(String code) {
		for (APIServiceEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<OpenApiServiceEnum>
	 */
	public List<APIServiceEnum> getAllEnum() {
		List<APIServiceEnum> list = new ArrayList<APIServiceEnum>();
		for (APIServiceEnum _enum : values()) {
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
		for (APIServiceEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
	
	public static void main(String[] args) {
		System.out.println(APIServiceEnum.getByCode("register"));
	}
	
}
