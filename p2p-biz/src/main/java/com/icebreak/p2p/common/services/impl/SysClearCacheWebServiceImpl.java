package com.icebreak.p2p.common.services.impl;

import com.icebreak.p2p.base.data.BankDataService;
import com.icebreak.p2p.common.services.SysParameterService;
import com.icebreak.p2p.division.DivisionService;
import com.icebreak.p2p.mail.MailSenderInfo;
import com.icebreak.p2p.service.openingbank.api.OpeningBankService;
import com.icebreak.p2p.task.AbstractBaseJob;
import com.icebreak.p2p.task.AbstractTask;
import com.icebreak.p2p.ws.service.SysClearCacheWebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysClearCacheWebService")
public class SysClearCacheWebServiceImpl implements SysClearCacheWebService {
    @Autowired
    SysParameterService sysParameterService;
    @Autowired
    BankDataService bankDataService;

    @Autowired
    private DivisionService divisionService;

    @Autowired
    OpeningBankService openingBankService;

    @Override
    public void clearCache() {
        sysParameterService.clearCache();
        bankDataService.clearCache();
        divisionService.clearCache();
        MailSenderInfo.clearMailSession();
        openingBankService.clearCache();
        AbstractBaseJob.clearCache();
     }
}
