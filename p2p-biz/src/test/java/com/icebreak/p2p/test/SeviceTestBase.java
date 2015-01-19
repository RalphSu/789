package com.icebreak.p2p.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/spring/common-dal-dao.xml", "/spring/common-dal-db.xml",
						"/spring/integration-cxf.xml", "/spring/client.xml",
						"/spring/service.xml" })
public class SeviceTestBase {
	protected final Logger	logger	= LoggerFactory.getLogger(this.getClass());
}
