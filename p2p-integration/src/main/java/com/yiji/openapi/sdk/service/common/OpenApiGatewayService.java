package com.yiji.openapi.sdk.service.common;

import com.yiji.openapi.sdk.message.common.*;
import com.yiji.openapi.sdk.message.common.funds.*;
import com.yiji.openapi.sdk.message.common.manage.*;
import com.yiji.openapi.sdk.message.common.trade.*;
import com.yiji.openapi.sdk.notify.NotifyHandler;

import java.util.List;
import java.util.Map;

public interface OpenApiGatewayService {
	
	UserRegisterResponse userRegister(UserRegisterRequest request);
	
	YzzUserAccountQueryResponse userAccountQuery(YzzUserAccountQueryRequest request);
	
	ApplyMobileBindingCustomerResponse applyMobileBindingCustomer(	ApplyMobileBindingCustomerRequest request);
	

	BankNoQueryResponse bankNoQuery(BankNoQueryRequest request);
	
	/**
	 * 查询实名认证信息
	 * @param request
	 * @return
	 */
	RealNameCertQueryResponse realNameCertQuery(RealNameCertQueryRequest request);
	
	/**
	 * 实名认证信息保存
	 * @param request
	 * @return
	 */
	RealNameCertSaveResponse realNameCertSave(RealNameCertSaveRequest request);
	


	/**
	 * 会员修改绑定手机号
	 * @param request
	 * @return
	 */
	UpdateMobileBindingCustomerResponse updateMobileBindingCustomer(UpdateMobileBindingCustomerRequest request);
	
	/**
	 * 添加银行卡绑定信息
	 * @param request
	 * @return
	 */
	BankCodeBindingAddInfoResponse bankCodeBindingAddInfo(BankCodeBindingAddInfoRequest request);
	
	/**
	 * 设置银行卡为默认绑定
	 * @param request
	 * @return
	 */
	BankCodeBindingSetDefaultResponse bankCodeBindingSetDefault(BankCodeBindingSetDefaultRequest request);
	

	// ------------------------------- 资金类 ------------------------------------

	/** 充值跳转请求签名 */
	Map<String, String> signDepositRequest(DepositRequest request);
	
	/** 解析充值异步通知 */
	DepositNotify notifyDeposit(Map<String, String> data);
	
	DepositNotify notifyDeposit(Map<String, String> data, NotifyHandler handler);
	
	// ------------------------------- 交易类 ------------------------------------
	/** 批量转账-创建交易 */
	TradeCreatePoolReverseResponse tradeCreatePoolReverse(TradeCreatePoolReverseRequest request);
	
	/** 批量转账-转账交易 */
	TradePayPoolReverseResponse tradePayPoolReverse(TradePayPoolReverseRequest request);
	
	/** 批量转账-完成交易 */
	TradeFinishPoolReverseResponse tradeFinishPoolReverse(TradeFinishPoolReverseRequest request);
	
	/**
	 * 批量转账整合接口
	 * 
	 * 整合批量转账交易的创建，转账和完成三个接口
	 * 
	 * @return
	 */
	TradePayPoolReverseResponse batchTransfer(List<SubOrder> subOrders, String tradeMemo);
	
	/** 批量转账-结果异步通知 */
	TradePayPoolReverseNotify notifyTradePayPoolReverse(Map<String, String> data);
	
	TradePayPoolReverseNotify notifyTradePayPoolReverse(Map<String, String> data,
														NotifyHandler handler);
	

	/**
	 * 普通交易创立 创建普通交易
	 * @param request
	 * @return
	 */
	TradeCreateResponse tradeCreate(TradeCreateRequest request);
	
	/**
	 * 根据交易号查询交易信息 根据交易号查询单笔交易信息
	 * @param request
	 * @return
	 */
	TradeQueryResponse tradeQuery(TradeQueryRequest request);
	

	/**
	 * 解除银行卡绑定 将用户已经绑定的银行卡取消绑定
	 * @param request
	 * @return
	 */
	BankCodeBindingRemoveResponse bankCodeBindingRemove(BankCodeBindingRemoveRequest request);
	
	/**
	 * 代扣充值服务 使用银行代扣业务充值
	 * @param request
	 * @return
	 */
	DeductDepositApplyResponse deductDepositApply(DeductDepositApplyRequest request);
	
	/**
	 * 快速实名认证 三要素快速实名认证：真实姓名，身份证号码，有效期
	 * @param request
	 * @return
	 */
	RealNameSimpleCertifyResponse realNameSimpleCertify(RealNameSimpleCertifyRequest request);
	
	/**
	 * 交易转账 交易转账
	 * @param request
	 * @return
	 */
	TradeTransferResponse tradeTransfer(TradeTransferRequest request);
	
	/**
	 * 验证银行卡信息 将用户注册的银行卡信息送至银行验证
	 * @param request
	 * @return
	 */
	VerifyFacadeResponse verifyFacade(VerifyFacadeRequest request);
	

	/**
	 * 查询卡bin信息 查询银行卡基本信息
	 * @param request
	 * @return
	 */
	YzzBankCardBinResponse yzzBankCardBin(YzzBankCardBinRequest request);

	/**
     * 检查用户名是否存在
     * 检查用户名是否在易极付注册过
     * @param request
     * @return
     */
	CheckUserNameExistResponse checkUserNameExist(CheckUserNameExistRequest request);

	TradeQueryOrderNoResponse tradeQueryOrderNo(TradeQueryOrderNoRequest request);

	Object notifySignManyBank(Map<String, String> data, NotifyHandler handler);

	Object notifyRealNameCert(Map<String, String> data, NotifyHandler handler);

	QueryBindBankCardResponse queryBindBankCard (QueryBindBankCardRequest request);


}
