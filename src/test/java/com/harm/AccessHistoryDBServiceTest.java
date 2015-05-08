package com.harm;

import static com.harm.DBServiceTest.DELETE_SUCCESS_FLAG;
import static com.harm.DBServiceTest.INSERT_SUCCESS_FLAG;
import static com.harm.DBServiceTest.RESULT;
import static com.harm.DBServiceTest.SELECT_SUCCESS_FLAG;
import static com.harm.DBServiceTest.UPDATE_SUCCESS_FLAG;
import static com.harm.DBServiceTest.WHOLE_TEST_SUCCCESS;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Log4jConfigurer;

import com.harm.bean.AccessHistoryBean;
import com.harm.sgc.service.AccessHistoryDBService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"file:src/main/resources/spring/appServlet-context.xml",
		"file:src/main/resources/spring/root-context.xml"})
@Transactional
public class AccessHistoryDBServiceTest {
	
	private Logger logger = LoggerFactory.getLogger(AccessHistoryDBServiceTest.class);
	
	@Autowired
	private AccessHistoryDBService accessHistoryDBService;
	
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
	public void CardTest() {
		RESULT = 0;
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		
		AccessHistoryBean insertAccessHistoryBean = new AccessHistoryBean();
		insertAccessHistoryBean.setCardId("TEST_INSERT_CARD_ID");
		insertAccessHistoryBean.setGateId("TEST_INSERT_GATE_ID");
		insertAccessHistoryBean.setAccessDate(formatter.format(new Date()));
		if(accessHistoryDBService.insert(insertAccessHistoryBean) > 0) {
			RESULT += INSERT_SUCCESS_FLAG;
		}
		
		List<AccessHistoryBean> accessHistoryBeans = accessHistoryDBService.select(null);
		for(AccessHistoryBean accessHistoryBean : accessHistoryBeans) {
			logger.debug(accessHistoryBean.getCardId());
			logger.debug(accessHistoryBean.getGateId());
			logger.debug(accessHistoryBean.getAccessDate());
		}
		RESULT += SELECT_SUCCESS_FLAG;
		
		AccessHistoryBean updateAccessHistoryBean = new AccessHistoryBean();
		updateAccessHistoryBean.setCardId(insertAccessHistoryBean.getCardId());
		updateAccessHistoryBean.setGateId("TEST_UPDATE_GATE_ID");
		updateAccessHistoryBean.setAccessDate(formatter.format(new Date()));
		if(accessHistoryDBService.update(updateAccessHistoryBean) == -1) {
			RESULT += UPDATE_SUCCESS_FLAG;
		}
		
		AccessHistoryBean deleteAccessHistoryBean = new AccessHistoryBean();
		deleteAccessHistoryBean.setCardId(insertAccessHistoryBean.getCardId());
		deleteAccessHistoryBean.setGateId(insertAccessHistoryBean.getGateId());
		deleteAccessHistoryBean.setAccessDate(insertAccessHistoryBean.getAccessDate());
		if(accessHistoryDBService.delete(deleteAccessHistoryBean) > 0) {
			RESULT += DELETE_SUCCESS_FLAG;
		}
		
		Assert.assertThat(RESULT, org.hamcrest.CoreMatchers.is(WHOLE_TEST_SUCCCESS));
	}//END OF FUNCTION
}//END OF CLASS