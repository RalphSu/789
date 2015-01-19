package com.icebreak.p2p.rs.web;

import org.springframework.beans.factory.annotation.Autowired;

import com.icebreak.p2p.rs.service.base.ServiceFacade;

public class ControllerBase {
	@Autowired
	protected ServiceFacade serviceFacade;

}
