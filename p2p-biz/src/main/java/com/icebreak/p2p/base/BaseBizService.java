package com.icebreak.p2p.base;

import org.springframework.beans.factory.annotation.Autowired;

import com.icebreak.p2p.authority.AuthorityService;
import com.icebreak.p2p.user.InstitutionsInfoManager;
import com.icebreak.p2p.user.PersonalInfoManager;

public class BaseBizService extends BaseAutowiredToolsService {
	@Autowired
	protected PersonalInfoManager		personalInfoManager;
	@Autowired
	protected AuthorityService			authorityService;
	
	@Autowired
	protected InstitutionsInfoManager	institutionsInfoManager;
	
}
