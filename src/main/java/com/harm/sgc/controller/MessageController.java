package com.harm.sgc.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.harm.bean.CardBean;
import com.harm.schema.message.Message;
import com.harm.sgc.service.CardDBService;
import com.harm.util.Constants;
import com.harm.util.Constants.MESSAGE_ID;
import com.harm.util.XmlConverter;

@RequestMapping(value = "/msg")
@Controller
public class MessageController {

	private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
	
	@Autowired
	private CardDBService cardDBService;
	
	@RequestMapping(value = "/xml", method = RequestMethod.POST)
	public void xmlProcessor(HttpServletRequest req, HttpServletResponse res) {
		try {
			String xmlString = this.recvXmlStringFromReq(req);
			logger.debug("received xml : " + xmlString);
			
			Message message = (Message) XmlConverter.convertXmlToJaxb(Message.class, xmlString, Constants.MESSAGE_SCHEMA_PATH);
			logger.debug(message.toString());
			
			MESSAGE_ID recvMessageId = null;
			for(MESSAGE_ID messageId : MESSAGE_ID.values()) {
				if(messageId.value().equals(message.getMessageId())) {
					recvMessageId = messageId;
				}
			}
			
			int result = -1;
			CardBean cardBean = null;
			
			switch (recvMessageId) {
			case REG_CARD :
				cardBean = new CardBean();
				cardBean.setCardId(message.getCardId());
				result = cardDBService.insert(cardBean);
				break;
			default :
				break;
			}
			
			logger.debug("dao result : " + result);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}//END OF xmlProcessor()

	private String recvXmlStringFromReq(HttpServletRequest req) {
		BufferedInputStream reqIn = null;
		ByteArrayOutputStream byteOut = null;
		byte[] bytes = null;
		
		try {
			reqIn = new BufferedInputStream(req.getInputStream());
			byteOut = new ByteArrayOutputStream();
			
			//	read received xml (style 1.)
			int data = -1;
			while(	(data=reqIn.read()) >= 0	) {
				byteOut.write(data);
			}
			
			bytes = byteOut.toByteArray();
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				byteOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return new String(bytes, StandardCharsets.UTF_8);
	}//END OF recvXmlStringFromReq()

	private void sendXmlStringToRes(HttpServletResponse res) {
		BufferedOutputStream resOut = null;
		try {
			resOut = new BufferedOutputStream(res.getOutputStream());
			String param = "<xml><messageId>regOk</messageId><cardId>098098</cardId></xml>";
			resOut.write(param.getBytes(StandardCharsets.UTF_8));
			resOut.flush();
			resOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				resOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}//END OF CLASS