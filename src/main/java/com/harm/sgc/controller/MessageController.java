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
import com.harm.sgc.service.GateDBService;
import com.harm.util.Constants;
import com.harm.util.Constants.ERROR_MESSAGE;
import com.harm.util.Constants.MESSAGE_ID;
import com.harm.util.XmlConverter;

@RequestMapping(value = "/msg")
@Controller
public class MessageController {

	private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
	
	@Autowired
	private CardDBService cardDBService;
	
	@Autowired
	private GateDBService gateDBService;
	
	@RequestMapping(value = "/xml", method = RequestMethod.POST)
	public void xmlProcessor(HttpServletRequest req, HttpServletResponse res) {
		try {
			Message sendMessage = new Message();
			Message recvMessage = this.recvMessageObjectFromReq(req);
			MESSAGE_ID recvMessageId = distinguishingMessageId(recvMessage);
			
			int result = -1;
			CardBean cardBean = null;
			
			switch (recvMessageId) {
				case REG_CARD :
					cardBean = new CardBean();
					cardBean.setCardId(recvMessage.getCardId());
					result = cardDBService.insert(cardBean);
					if(result == 1) {
						sendMessage.setMessageId(MESSAGE_ID.REG_PASS.value());
						sendMessage.setCardId(recvMessage.getCardId());
						sendMessage.setGateId(recvMessage.getGateId());
					}
					break;
				case REQ_ACCS :
					
					break;
				default :
					logger.info(ERROR_MESSAGE.INVALID_MESSAGE_ID.value());
					break;
			}
			
			logger.debug("dao result : " + result);
			if(sendMessage.getMessageId() != null) {
				this.sendMessageObjectToRes(res, sendMessage);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}//END OF xmlProcessor()

	private Message recvMessageObjectFromReq(HttpServletRequest req) {
		BufferedInputStream reqIn = null;
		ByteArrayOutputStream byteOut = null;
		byte[] bytes = null;
		Message message = null;
		String xmlString = null;
		
		try {
			reqIn = new BufferedInputStream(req.getInputStream());
			byteOut = new ByteArrayOutputStream();
			
			//	read received xml (style 1.)
			int data = -1;
			while(	(data=reqIn.read()) >= 0	) {
				byteOut.write(data);
			}
			
			bytes = byteOut.toByteArray();
			
			xmlString = new String(bytes, StandardCharsets.UTF_8);
			logger.debug("received xml : " + xmlString);
			
			message = (Message) XmlConverter.convertXmlToJaxb(Message.class, xmlString, Constants.MESSAGE_SCHEMA_PATH);
			
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				byteOut.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		
		return message;
	}//END OF FUNCTION

	private MESSAGE_ID distinguishingMessageId(Message message) {
		MESSAGE_ID recvMessageId = null;
		for(MESSAGE_ID messageId : MESSAGE_ID.values()) {
			if(messageId.value().equals(message.getMessageId())) {
				recvMessageId = messageId;
			}
		}
		return recvMessageId;
	}//END OF FUNCTION
	
	private void sendMessageObjectToRes(HttpServletResponse res, Message message) {
		BufferedOutputStream resOut = null;
		try {
			resOut = new BufferedOutputStream(res.getOutputStream());
			String xmlString = XmlConverter.convertJaxbToXml(Message.class, message, Constants.MESSAGE_SCHEMA_PATH);
			resOut.write(xmlString.getBytes(StandardCharsets.UTF_8));
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
	}//END OF FUNCTION
	
}//END OF CLASS