package com.harm.sgc.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.harm.bean.AccessHistoryBean;
import com.harm.bean.CardBean;
import com.harm.schema.message.Message;
import com.harm.sgc.service.AccessHistoryDBService;
import com.harm.sgc.service.CardDBService;
import com.harm.sgc.service.GateDBService;
import com.harm.util.Constants;
import com.harm.util.Constants.DEBUG_MESSAGE;
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
	
	@Autowired
	private AccessHistoryDBService accessHistoryDBService;
	
	@RequestMapping(value = "/xml", method = RequestMethod.POST)
	public void xmlProcessor(HttpServletRequest req, HttpServletResponse res) {
		try {
			
			Message recvMessage = this.recvMessageObjectFromReq(req);
			MESSAGE_ID recvMessageId = distinguishingMessageId(recvMessage);
			
			Message sendMessage = new Message();
			
			switch (recvMessageId) {
				case REG_CARD :
					CardBean cardBean = new CardBean();
					cardBean.setCardId(recvMessage.getCardId());
					
					if(cardDBService.insert(cardBean) == 1) {
						sendMessage.setMessageId(MESSAGE_ID.RES_PASS.value());
						sendMessage.setCardId(recvMessage.getCardId());
						sendMessage.setGateId(recvMessage.getGateId());
						logger.debug(DEBUG_MESSAGE.REG_CARD_PASS.value());
					} else {
						logger.debug(DEBUG_MESSAGE.REG_CARD_FAIL.value());
					}
					
					this.sendMessageObjectToRes(res, sendMessage);
					break;
				case REQ_ACCS :
					boolean isAccessable = false;
					AccessHistoryBean accessHistoryBean = new AccessHistoryBean();
					accessHistoryBean.setCardId(recvMessage.getCardId());
					accessHistoryBean.setGateId(recvMessage.getGateId());
					
					if(gateDBService.isAccessable(accessHistoryBean)) {
						isAccessable = true;
						sendMessage.setMessageId(MESSAGE_ID.RES_PASS.value());
						logger.debug(DEBUG_MESSAGE.REQ_ACCS_PASS.value());
					} else {
						sendMessage.setMessageId(MESSAGE_ID.RES_FAIL.value());
						logger.debug(DEBUG_MESSAGE.REQ_ACCS_FAIL.value());
					}
					sendMessage.setCardId(recvMessage.getCardId());
					sendMessage.setGateId(recvMessage.getGateId());
					
					this.sendMessageObjectToRes(res, sendMessage);
					
					if(isAccessable) {
						accessHistoryBean.setAccessDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
					
						if(accessHistoryDBService.insert(accessHistoryBean) == 1) {
							logger.debug(DEBUG_MESSAGE.INSERT_ACCESS_HISTORY_PASS.value());
						} else {
							logger.debug(DEBUG_MESSAGE.INSERT_ACCESS_HISTORY_FAIL.value());
						}
					}
					
					break;
				default :
					logger.error(ERROR_MESSAGE.INVALID_MESSAGE_ID.value());
					break;
			}//END OF SWITCH : RECEIVED MESSAGE ID
			
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

	private MESSAGE_ID distinguishingMessageId(Message RecvMessage) {
		MESSAGE_ID recvMessageId = null;
		for(MESSAGE_ID messageId : MESSAGE_ID.values()) {
			if(messageId.value().equals(RecvMessage.getMessageId())) {
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