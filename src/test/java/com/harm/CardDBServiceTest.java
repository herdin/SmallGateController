package com.harm;


import static com.harm.DBServiceTest.DELETE_SUCCESS_FLAG;
import static com.harm.DBServiceTest.INSERT_SUCCESS_FLAG;
import static com.harm.DBServiceTest.SELECT_SUCCESS_FLAG;
import static com.harm.DBServiceTest.UPDATE_SUCCESS_FLAG;
import static com.harm.DBServiceTest.WHOLE_TEST_SUCCCESS;

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

import com.harm.bean.CardBean;
import com.harm.sgc.service.CardDBService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"file:src/main/resources/spring/appServlet-context.xml",
		"file:src/main/resources/spring/root-context.xml"})
@Transactional
public class CardDBServiceTest {
	
	private Logger logger = LoggerFactory.getLogger(CardDBServiceTest.class);
	
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
	public void CardTest() {
		int RESULT = 0;
		
		CardBean insertCardBean = new CardBean();
		insertCardBean.setCardId("TEST_INSERT_CARD_ID");
		insertCardBean.setCardDesc("TEST_INSERT_CARD_DESC");
		if(cardDBService.insert(insertCardBean) > 0) {
			RESULT += INSERT_SUCCESS_FLAG;
		}
		
		List<CardBean> cardBeans = cardDBService.select(null);
		for(CardBean cardbean : cardBeans) {
			logger.debug(cardbean.getCardId());
		}
		RESULT += SELECT_SUCCESS_FLAG;
		
		CardBean updateCardBean = new CardBean();
		updateCardBean.setCardId(insertCardBean.getCardId());
		updateCardBean.setCardDesc("TEST_UPDATE_CARD_DESC");
		if(cardDBService.update(updateCardBean) > 0) {
			RESULT += UPDATE_SUCCESS_FLAG;
		}
		
		CardBean deleteCardBean = new CardBean();
		deleteCardBean.setCardId(insertCardBean.getCardId());
		if(cardDBService.delete(deleteCardBean) > 0) {
			RESULT += DELETE_SUCCESS_FLAG;
		}
		
		Assert.assertThat(RESULT, org.hamcrest.CoreMatchers.is(WHOLE_TEST_SUCCCESS));
	}//END OF FUNCTION
}//END OF CLASS