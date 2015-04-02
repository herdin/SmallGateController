package com.harm.sgc;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.harm.bean.CardBean;
import com.harm.sgc.HomeController;
import com.harm.sgc.TestDBService;

/**
 * Handles requests for the application home page.
 */
@RequestMapping(value = "/test")
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@Inject
	private TestDBService testDbService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) throws Exception {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );

		/*
		List<HashMap<String, String>> results = testDbService.getCardList();
	
		for(int i=0; i<results.size(); i++) {
			HashMap<String, String> result = results.get(i);
			Set<String> keySet =  result.keySet();
			@SuppressWarnings("rawtypes")
			Iterator itor = keySet.iterator();
			while(itor.hasNext()) {
				String key = (String) itor.next();
				logger.info("key : " + key);
				logger.info("val : " + result.get(key));
			}
		}
		*/
		System.out.println("select test start");
		CardBean selectParam = new CardBean();
		selectParam.setCardId("234234234");
		List<CardBean> selectResults = testDbService.getCardList2(selectParam);
		for(CardBean CBean : selectResults) {
			System.out.println(CBean.getCardId() + "/" + CBean.getCardDesc());
		}
		System.out.println("select test end");
		
		System.out.println("insert test start");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		CardBean insertParam = new CardBean();
		insertParam.setCardId(sdf.format(new Date()));
		int result = testDbService.regCard(insertParam);
		System.out.println("insert result : " + result);
		System.out.println("insert test end");
		
		System.out.println("update test start");
		selectResults = testDbService.getCardList2(insertParam);
		CardBean updateParam = selectResults.get(0);
		updateParam.setCardDesc("card desc");
		result = testDbService.modCard(updateParam);
		System.out.println("update result : " + result);
		System.out.println("update test end");
		
		System.out.println("delete test start");
		CardBean delParam = new CardBean();
		delParam.setCardId("20150330180312");
		result = testDbService.delCard(delParam);
		System.out.println("delete result : " + result);
		System.out.println("delete test end");
		
//		return "home";
		return "sampleMenu";
	}//END FO home()
	
	@RequestMapping(value = "/xml/*", method = RequestMethod.POST)
	public void xmlHome(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		//receive start.
		BufferedInputStream reqIn = new BufferedInputStream(req.getInputStream());
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		
		/*read style 1.*/
		int data = -1;
		while(	(data=reqIn.read()) >= 0	) {
			byteOut.write(data);
		}
		byte[] bytes = byteOut.toByteArray();
		String xml = new String(bytes, StandardCharsets.UTF_8);
		System.out.println("received xml : " + xml);
		//receive done.
		
		//send start.
		BufferedOutputStream resOut = new BufferedOutputStream(res.getOutputStream());
		String param = "<xml><messageId>regOk</messageId><cardId>098098</cardId></xml>";
		resOut.write(param.getBytes(StandardCharsets.UTF_8));
		resOut.flush();
		resOut.close();
		
		
		//send done.
		
	}//END OF xmlHome()
	
	@RequestMapping(value = "/xml2/*", method = RequestMethod.POST)
	public void jaxbHome(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
	}//END OF jaxbHome()
	
//	/*all get key-value mapping to param*/
//	@RequestMapping(value = "/xml/*", method = RequestMethod.GET)
//	public String xmlHome(	@RequestParam Map<String, String> param,
//							Locale locale,
//							Model model) throws Exception {
//		Date date = new Date();
//		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
//		String formattedDate = dateFormat.format(date);
//		model.addAttribute("serverTime", formattedDate );
//		
//		param.put("cardId", "123123");
//		List<CardBean> results = testDbService.getCardList2(param);
//		
//		for(int i=0; i<results.size(); i++) {
//			CardBean cardBean = results.get(i);
//			logger.info("" + i + " : " + cardBean.getCARD_ID());
//		}		
//		return "home";
//	}
	

}//END OF CLASS