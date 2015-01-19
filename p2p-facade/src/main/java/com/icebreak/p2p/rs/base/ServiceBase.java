package com.icebreak.p2p.rs.base;

import org.springframework.beans.factory.annotation.Autowired;

import com.icebreak.p2p.authority.AuthorityService;
import com.icebreak.p2p.base.OpenApiBaseService;
import com.icebreak.p2p.base.data.BankDataService;
import com.icebreak.p2p.common.services.BankBaseService;
import com.icebreak.p2p.common.services.MessageService;
import com.icebreak.p2p.common.services.SmsManagerService;
import com.icebreak.p2p.daointerface.PersonalInfoDAO;
import com.icebreak.p2p.division.DivisionService;
import com.icebreak.p2p.division.DivisionTemplateYrdService;
import com.icebreak.p2p.integration.openapi.CustomerService;
import com.icebreak.p2p.integration.openapi.WithdrawQueryService;
import com.icebreak.p2p.loandemand.LoanDemandManager;
import com.icebreak.p2p.login.LoginService;
import com.icebreak.p2p.mail.IMailSendService;
import com.icebreak.p2p.trade.DeductYrdService;
import com.icebreak.p2p.trade.TradeService;
import com.icebreak.p2p.user.InstitutionsInfoManager;
import com.icebreak.p2p.user.PersonalInfoManager;
import com.icebreak.p2p.user.UserBaseInfoManager;
import com.icebreak.p2p.user.UserRelationManager;
import com.icebreak.p2p.user.UserService;
import com.yiji.openapi.sdk.service.common.OpenApiGatewayService;
import com.yiji.openapi.sdk.service.yzz.YzzGatewayService;

public class ServiceBase extends OpenApiBaseService {
	@Autowired
	protected YzzGatewayService yzzGatewayService;
	@Autowired
	protected OpenApiGatewayService openApiGatewayService;
	@Autowired
	protected PersonalInfoManager			personalInfoManager;
	@Autowired
	protected InstitutionsInfoManager		institutionsInfoManager;
	@Autowired
	protected UserBaseInfoManager			userBaseInfoManager;
	@Autowired
	protected LoanDemandManager				loanDemandManager;
	@Autowired
	protected UserRelationManager			userRelationManager;
	@Autowired
	protected PersonalInfoDAO				personalInfoDAO;
	@Autowired
	protected IMailSendService				mailService;
	@Autowired
	protected BankBaseService				bankBaseService;
	@Autowired
	protected BankDataService				bankDataService;
	@Autowired
	protected AuthorityService				authorityService;
	@Autowired
	protected DivisionService				divisionService;
	@Autowired
	protected TradeService					tradeService;
	@Autowired
	protected LoginService					loginService;
	@Autowired
	protected DivisionTemplateYrdService	divisionTemplateLoanService;
	@Autowired
	protected UserService					userService;
	
	@Autowired
	protected MessageService				yrdMessageService;
	
	@Autowired
	protected DeductYrdService				deductYrdService;
	@Autowired
	protected CustomerService				customerService;
	
	@Autowired
	protected WithdrawQueryService			withdrawQueryService;
	
	@Autowired
	protected SmsManagerService				smsManagerService;
	
}
