package com.icebreak.p2p.base;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.icebreak.p2p.dal.daointerface.BankBaseInfoDAO;
import com.icebreak.p2p.daointerface.*;
import com.icebreak.p2p.integration.openapi.RepayService;
import com.icebreak.p2p.trade.RechargeFlowService;

public class BaseAutowiredDAOService extends OpenApiBaseService {
	@Autowired
	protected UserBaseInfoDAO userBaseInfoDAO;
	@Autowired
	protected InstitutionsInfoDAO institutionsInfoDAO;
	@Autowired
	protected PersonalInfoDAO personalInfoDAO;
	@Autowired
	protected LoanDemandDAO loanDemandDAO;
	@Autowired
	protected UserRelationDAO userRelationDAO;

	@Autowired
	protected BankBaseInfoDAO bankBaseInfoDAO;

	@Autowired
	protected RoleDao roleDao;
	@Autowired
	protected UserDao userDao;

	@Autowired
	protected DivisionDetailDao divisionDetailDao;

	@Resource
	protected RepayService repayService;
	@Resource
	protected RechargeFlowService rechargeFlowService;

}
