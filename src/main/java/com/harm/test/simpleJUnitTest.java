package com.harm.test;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Log4jConfigurer;

import com.harm.bean.CardBean;
import com.harm.sgc.service.CardDBService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"file:src/main/resources/spring/appServlet-context.xml",
		"file:src/main/resources/spring/root-context.xml"})
@Transactional
public class simpleJUnitTest {

	private Logger logger = LoggerFactory.getLogger(simpleJUnitTest.class);
	
//	@Autowired
//	private ApplicationContext applicationContext;
	
	@Autowired
	private CardDBService cardDBService;
	
	@Before
	public void setUp() throws Exception {
		Log4jConfigurer.initLogging("file:src/main/resources/log4j.xml");
		System.out.println("LOGGER INIT DEBUG : " + logger.isDebugEnabled());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	@Rollback(true)
	public void test() {
		List<CardBean> cardBeans = cardDBService.select(null);
		for(CardBean cardbean : cardBeans) {
			logger.debug(cardbean.getCardId());
		}
	}

}
