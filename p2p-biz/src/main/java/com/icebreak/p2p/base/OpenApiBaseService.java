package com.icebreak.p2p.base;

import org.springframework.beans.factory.annotation.Autowired;

import com.icebreak.p2p.common.services.BankBaseService;
import com.icebreak.p2p.common.services.SysFunctionConfigService;
import com.icebreak.p2p.integration.openapi.CustomerService;
import com.icebreak.p2p.integration.openapi.DeductDepositService;
import com.icebreak.p2p.integration.openapi.DepositQueryService;
import com.icebreak.p2p.integration.openapi.FundsTransferService;
import com.icebreak.p2p.integration.openapi.SMSService;
import com.icebreak.p2p.integration.openapi.WithdrawQueryService;
import com.icebreak.p2p.service.openingbank.api.OpeningBankService;
import com.yiji.openapi.sdk.APITool;
import com.yiji.openapi.sdk.service.common.OpenApiGatewayService;
import com.yiji.openapi.sdk.service.yzz.YzzGatewayService;

public class OpenApiBaseService extends OpenApiBaseContextServiceBase {
	
	@Autowired
	protected YzzGatewayService yzzGatewayService;
	@Autowired
	protected OpenApiGatewayService openApiGatewayService;
	@Autowired
	protected APITool apiTool;
	
	
	@Autowired
	protected BankBaseService			bankBaseService;
	
	@Autowired
	protected SMSService				smsService;
	
	@Autowired
	protected OpeningBankService		openingBankService;
	
	@Autowired
	protected CustomerService			customerService;
	
	@Autowired
	protected DeductDepositService		deductDepositService;
	@Autowired
	protected FundsTransferService		fundsTransferService;
	
	@Autowired
	protected DepositQueryService		depositQueryService;
	
	@Autowired
	protected WithdrawQueryService		withdrawQueryService;
	
	@Autowired
	protected SysFunctionConfigService	sysFunctionConfigService;
	
}
