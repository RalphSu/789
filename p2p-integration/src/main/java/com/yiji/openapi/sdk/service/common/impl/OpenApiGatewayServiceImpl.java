package com.yiji.openapi.sdk.service.common.impl;

import com.yiji.openapi.sdk.exception.OpenApiClientException;
import com.yiji.openapi.sdk.exception.OpenApiServiceException;
import com.yiji.openapi.sdk.executer.OpenApiServiceExecuter;
import com.yiji.openapi.sdk.message.common.*;
import com.yiji.openapi.sdk.message.common.funds.*;
import com.yiji.openapi.sdk.message.common.manage.*;
import com.yiji.openapi.sdk.message.common.trade.*;
import com.yiji.openapi.sdk.message.yzz.RealNameCertNotify;
import com.yiji.openapi.sdk.message.yzz.SignManyBankNotify;
import com.yiji.openapi.sdk.notify.NotifyHandler;
import com.yiji.openapi.sdk.service.AbstractGatewayService;
import com.yiji.openapi.sdk.service.common.OpenApiGatewayService;

import java.util.List;
import java.util.Map;


public class OpenApiGatewayServiceImpl extends AbstractGatewayService implements
																		OpenApiGatewayService {
	
	public OpenApiGatewayServiceImpl() {
		super();
	}
	
	public OpenApiGatewayServiceImpl(OpenApiServiceExecuter executer) {
		setExecuter(executer);
	}
	
	@Override
	public UserRegisterResponse userRegister(UserRegisterRequest request) {
		return (UserRegisterResponse) assertExcecuteSuccess(execute(request));
	}
	
	@Override
	public YzzUserAccountQueryResponse userAccountQuery(YzzUserAccountQueryRequest request) {
		return (YzzUserAccountQueryResponse) assertExcecuteSuccess(execute(request));
	}
	
	@Override
	public ApplyMobileBindingCustomerResponse applyMobileBindingCustomer(	ApplyMobileBindingCustomerRequest request) {
		return (ApplyMobileBindingCustomerResponse) assertExcecuteSuccess(execute(request));
	}
	
	@Override
	public BankNoQueryResponse bankNoQuery(BankNoQueryRequest request) {
		return (BankNoQueryResponse) assertExcecuteSuccess(execute(request));
	}
	
	
	@Override
	public Map<String, String> signDepositRequest(DepositRequest request) {
		return signRequest(request);
	}
	
	@Override
	public DepositNotify notifyDeposit(Map<String, String> data) {
		return notifyDeposit(data, null);
	}
	
	@Override
	public DepositNotify notifyDeposit(Map<String, String> data, NotifyHandler handler) {
		// 收到即成功，失败的情况不会通知
		DepositNotify notify = (DepositNotify) executor.parseNotify("deposit", data);
		if (handler != null) {
			handler.handleNotify(notify);
		}
		return notify;
	}
	
	@Override
	public TradeCreatePoolReverseResponse tradeCreatePoolReverse(	TradeCreatePoolReverseRequest request) {
		TradeCreatePoolReverseResponse response = (TradeCreatePoolReverseResponse) assertExcecuteSuccess(execute(request));
		response.setOrderNo(request.getOrderNo());
		return response;
	}
	
	@Override
	public TradePayPoolReverseResponse tradePayPoolReverse(TradePayPoolReverseRequest request) {
		TradePayPoolReverseResponse response = (TradePayPoolReverseResponse) assertExcecuteSuccess(execute(request));
		response.setOrderNo(request.getOrderNo());
		return response;
	}
	
	@Override
	public TradeFinishPoolReverseResponse tradeFinishPoolReverse(	TradeFinishPoolReverseRequest request) {
		TradeFinishPoolReverseResponse response = (TradeFinishPoolReverseResponse) assertExcecuteSuccess(execute(request));
		response.setOrderNo(request.getOrderNo());
		return response;
	}
	
	@Override
	public TradePayPoolReverseResponse batchTransfer(List<SubOrder> subOrders, String tradeMemo) {
		if (subOrders == null || subOrders.isEmpty()) {
			throw new OpenApiClientException("批量支付子订单不能为空");
		}
		String mainAmount = "1";
		String payerUserId = subOrders.iterator().next().getPayerUserId();
		TradePayPoolReverseResponse response = null;
		try {
			TradeCreatePoolReverseResponse responseCreate = tradeCreatePoolReverse(new TradeCreatePoolReverseRequest(
				payerUserId, mainAmount));
			TradePayPoolReverseRequest request = new TradePayPoolReverseRequest();
			request.setTradeNo(responseCreate.getTradeNo());
			request.setTradeMemo(tradeMemo);
			request.setSubOrders(subOrders);
			response = tradePayPoolReverse(request);
			tradeFinishPoolReverse(new TradeFinishPoolReverseRequest(responseCreate.getTradeNo()));
		} catch (OpenApiServiceException ose) {
			throw ose;
		} catch (Exception e) {
			throw new OpenApiClientException("批量支付交易请求失败:" + e.getMessage());
		}
		return response;
	}
	
	@Override
	public TradePayPoolReverseNotify notifyTradePayPoolReverse(Map<String, String> data) {
		return notifyTradePayPoolReverse(data, null);
	}
	
	@Override
	public TradePayPoolReverseNotify notifyTradePayPoolReverse(Map<String, String> data,
																NotifyHandler handler) {
		TradePayPoolReverseNotify notify = (TradePayPoolReverseNotify) executor.parseNotify(
			"tradePayPoolReverse", data);
		if (handler != null) {
			handler.handleNotify(notify);
		}
		return notify;
	}
	
	@Override
	public RealNameCertQueryResponse realNameCertQuery(RealNameCertQueryRequest request) {
		request.setOrderNo(generateOrderNo());
		return (RealNameCertQueryResponse) execute(request);
	}
	
	/**
	 * @param request
	 * @return
	 * @see com.yiji.openapi.sdk.service.common.OpenApiGatewayService#realNameCertSave(com.yiji.openapi.sdk.message.common.manage.RealNameCertSaveRequest)
	 */
	@Override
	public RealNameCertSaveResponse realNameCertSave(RealNameCertSaveRequest request) {
		request.setOrderNo(generateOrderNo());
		return (RealNameCertSaveResponse) assertExcecuteSuccess(execute(request));
	}
	

	/**
	 * @param request
	 * @return
	 * @see com.yiji.openapi.sdk.service.common.OpenApiGatewayService#updateMobileBindingCustomer(com.yiji.openapi.sdk.message.common.manage.UpdateMobileBindingCustomerRequest)
	 */
	@Override
	public UpdateMobileBindingCustomerResponse updateMobileBindingCustomer(	UpdateMobileBindingCustomerRequest request) {
		return (UpdateMobileBindingCustomerResponse) assertExcecuteSuccess(execute(request));
	}
	
	@Override
	public BankCodeBindingAddInfoResponse bankCodeBindingAddInfo(	BankCodeBindingAddInfoRequest request) {
		return (BankCodeBindingAddInfoResponse) execute(request);
	}
	
	/**
	 * @param request
	 * @return
	 * @see com.yiji.openapi.sdk.service.common.OpenApiGatewayService#bankCodeBindingSetDefault(com.yiji.openapi.sdk.message.common.manage.BankCodeBindingSetDefaultRequest)
	 */
	@Override
	public BankCodeBindingSetDefaultResponse bankCodeBindingSetDefault(	BankCodeBindingSetDefaultRequest request) {
		return (BankCodeBindingSetDefaultResponse) execute(request);
	}
	
	/**
	 * @param request
	 * @return
	 * @see com.yiji.openapi.sdk.service.common.OpenApiGatewayService#tradeCreate(com.yiji.openapi.sdk.message.common.trade.TradeCreateRequest)
	 */
	@Override
	public TradeCreateResponse tradeCreate(TradeCreateRequest request) {
		return (TradeCreateResponse) assertExcecuteSuccess(execute(request));
	}
	
	/**
	 * @param request
	 * @return
	 * @see com.yiji.openapi.sdk.service.common.OpenApiGatewayService#tradeQuery(com.yiji.openapi.sdk.message.common.trade.TradeQueryRequest)
	 */
	@Override
	public TradeQueryResponse tradeQuery(TradeQueryRequest request) {
		return (TradeQueryResponse) execute(request);
	}
	
	/**
	 * @param request
	 * @return
	 * @see com.yiji.openapi.sdk.service.common.OpenApiGatewayService#bankCodeBindingRemove(com.yiji.openapi.sdk.message.common.manage.BankCodeBindingRemoveRequest)
	 */
	@Override
	public BankCodeBindingRemoveResponse bankCodeBindingRemove(BankCodeBindingRemoveRequest request) {
		return (BankCodeBindingRemoveResponse) assertExcecuteSuccess(execute(request));
	}
	
	@Override
	public DeductDepositApplyResponse deductDepositApply(DeductDepositApplyRequest request) {
		return (DeductDepositApplyResponse) assertExcecuteSuccess(execute(request));
	}
	
	@Override
	public RealNameSimpleCertifyResponse realNameSimpleCertify(RealNameSimpleCertifyRequest request) {
		return (RealNameSimpleCertifyResponse) assertExcecuteSuccess(execute(request));
	}
	
	@Override
	public TradeTransferResponse tradeTransfer(TradeTransferRequest request) {
		return (TradeTransferResponse) assertExcecuteSuccess(execute(request));
	}

	
	@Override
	public VerifyFacadeResponse verifyFacade(VerifyFacadeRequest request) {
		return (VerifyFacadeResponse) assertExcecuteSuccess(execute(request));
	}

	@Override
	public YzzBankCardBinResponse yzzBankCardBin(YzzBankCardBinRequest request) {
		return (YzzBankCardBinResponse) assertExcecuteSuccess(execute(request));
	}
	
	@Override
	public CheckUserNameExistResponse checkUserNameExist(CheckUserNameExistRequest request) {
		return (CheckUserNameExistResponse) assertSuccess(execute(request));
	}
	
	@Override
	public TradeQueryOrderNoResponse tradeQueryOrderNo(TradeQueryOrderNoRequest request) {
		return (TradeQueryOrderNoResponse) assertExcecuteSuccess(execute(request));
	
    }

	@Override
	public Object notifySignManyBank(Map<String, String> data, NotifyHandler handler) {
		// 收到即成功，失败的情况不会通知
		SignManyBankNotify notify = (SignManyBankNotify) executor.parseNotify("signManyBank", data);
		if (handler != null) {
			handler.handleNotify(notify);
		}
		return null;
	}

	@Override
	public Object notifyRealNameCert(Map<String, String> data, NotifyHandler handler) {
		RealNameCertNotify notify = (RealNameCertNotify) executor.parseNotify("realNameCert", data);
		if (handler != null) {
			handler.handleNotify(notify);
		}
		return null;
	}

	@Override
	public QueryBindBankCardResponse queryBindBankCard(QueryBindBankCardRequest request) {
		return (QueryBindBankCardResponse) assertExcecuteSuccess(execute(request));
	}
}
