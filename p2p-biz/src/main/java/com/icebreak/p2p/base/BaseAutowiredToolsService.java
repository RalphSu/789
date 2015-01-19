package com.icebreak.p2p.base;

import org.springframework.beans.factory.annotation.Autowired;

import com.icebreak.p2p.common.services.MessageService;
import com.icebreak.p2p.mail.IMailSendService;

public class BaseAutowiredToolsService extends BaseAutowiredDAOService {
	@Autowired
	protected IMailSendService	mailService;
	@Autowired
	protected MessageService messageService;
	
}
