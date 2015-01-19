package com.icebreak.p2p.base;

import com.icebreak.p2p.common.services.BankBaseService;
import com.icebreak.p2p.division.DivisionService;
import com.icebreak.p2p.integration.openapi.SignService;
import com.icebreak.p2p.trade.TradeService;
import com.icebreak.p2p.user.UserBaseInfoManager;
import com.icebreak.p2p.user.UserRelationManager;
import org.springframework.beans.factory.annotation.Autowired;


public class BaseAutowiredService extends BaseBizService {


    @Autowired
    protected DivisionService divisionService;

    @Autowired
    protected BankBaseService bankBaseService;

    @Autowired
    protected UserRelationManager userRelationManager;

    @Autowired
    protected UserBaseInfoManager userBaseInfoManager;

    @Autowired
    protected TradeService tradeService;

    @Autowired
    protected SignService signService;

}
