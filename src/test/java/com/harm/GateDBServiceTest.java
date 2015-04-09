package com.harm;


import static com.harm.test.service.DBServiceTest.DELETE_SUCCESS_FLAG;
import static com.harm.test.service.DBServiceTest.INSERT_SUCCESS_FLAG;
import static com.harm.test.service.DBServiceTest.RESULT;
import static com.harm.test.service.DBServiceTest.SELECT_SUCCESS_FLAG;
import static com.harm.test.service.DBServiceTest.UPDATE_SUCCESS_FLAG;
import static com.harm.test.service.DBServiceTest.WHOLE_TEST_SUCCCESS;

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

import com.harm.bean.GateBean;
import com.harm.sgc.service.GateDBService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"file:src/main/resources/spring/appServlet-context.xml",
		"file:src/main/resources/spring/root-context.xml"})
@Transactional
public class GateDBServiceTest {
	
	private Logger logger = LoggerFactory.getLogger(GateDBServiceTest.class);
	
	@Autowired
	private GateDBService gateDBService;
	
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
	public void GateTest() {
		RESULT = 0;
		
		GateBean insertGateBean = new GateBean();
		insertGateBean.setGateId("TEST_INSERT_GATE_ID");
		insertGateBean.setGateDesc("TEST_INSERT_GATE_DESC");
		if(gateDBService.insert(insertGateBean) > 0) {
			RESULT += INSERT_SUCCESS_FLAG;
		}
		
		List<GateBean> gateBeans = gateDBService.select(null);
		for(GateBean gatebean : gateBeans) {
			logger.debug(gatebean.getGateId());
		}
		RESULT += SELECT_SUCCESS_FLAG;
		
		GateBean updateGateBean = new GateBean();
		updateGateBean.setGateId(insertGateBean.getGateId());
		updateGateBean.setGateDesc("TEST_UPDATE_GATE_DESC");
		if(gateDBService.update(updateGateBean) > 0) {
			RESULT += UPDATE_SUCCESS_FLAG;
		}
		
		GateBean deleteGateBean = new GateBean();
		deleteGateBean.setGateId(insertGateBean.getGateId());
		if(gateDBService.delete(deleteGateBean) > 0) {
			RESULT += DELETE_SUCCESS_FLAG;
		}
		
		Assert.assertThat(RESULT, org.hamcrest.CoreMatchers.is(WHOLE_TEST_SUCCCESS));
	}//END OF FUNCTION

}//END OF CLASS
