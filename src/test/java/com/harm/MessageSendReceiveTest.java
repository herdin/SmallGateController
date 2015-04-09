package com.harm;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.harm.schema.message.Message;
import com.harm.util.Constants.MESSAGE_ID;
import com.harm.util.XmlConverter;


public class MessageSendReceiveTest {

	public static void main(String[] args) throws IOException {
		
		String serverMessageUrl = "http://localhost:8080/sgc/app/msg/xml/";
		String schemaFullPath = "C:\\spring-tool-suite\\sts-bundle\\workspace\\SmallGateController\\src\\main\\resources\\schema\\message.xsd";
		
		boolean JAXB_CONVERT_TEST		= false;
		boolean XML_SEND_RECEIVE_TEST	= true;

		if(JAXB_CONVERT_TEST) {
			String xmlString = "<message><messageId>123</messageId><gateId>456</gateId><cardId>789</cardId></message>";
//			String schemaFullPath = System.class.getResource("/com/spring/schema/").getPath() + "message.xsd";
			Message message = (Message)XmlConverter.convertXmlToJaxb(Message.class, xmlString, schemaFullPath);
			System.out.println(message.getMessageId());
			System.out.println(message.getGateId());
			System.out.println(message.getCardId());
			
			String xmlStringOut = XmlConverter.convertJaxbToXml(Message.class, message, schemaFullPath);
			System.out.println(xmlStringOut);
		}
		
		if(XML_SEND_RECEIVE_TEST) {
		
			String sendXmlString = null;
			@SuppressWarnings("unused")
			String recvXmlString = null;
			Message message = new Message();
//			message.setMessageId(MESSAGE_ID.REG_CARD.value());
			message.setMessageId(MESSAGE_ID.REQ_ACCS.value());
			message.setGateId("GT002");
			message.setCardId("0101");
			sendXmlString = XmlConverter.convertJaxbToXml(Message.class, message, schemaFullPath);
			byte[] bytes = sendXmlString.getBytes(StandardCharsets.UTF_8);
			
			System.out.println("bytes start.");
			for(int i=0; i<bytes.length; i++) {
				System.out.print(String.format("%02x", bytes[i]));
				System.out.print(" ");
			}
			System.out.println("bytes end.");
			
			System.out.println("string > byte > string start.");
			String str = new String(bytes, StandardCharsets.UTF_8);
			System.out.println(str);
			System.out.println("string > byte > string end.");
		
			MessageSendReceiveTest sj = new MessageSendReceiveTest();
			recvXmlString = sj.sendRecvXmlStringToServer(serverMessageUrl, sendXmlString);
		}

	}//END OF MAIN

	
	public String sendRecvXmlStringToServer(String serverUrl, String sendXmlString) throws IOException {
		System.out.println("ENTER : " + this.getMethodName());
		URL url = new URL(serverUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setRequestMethod("POST");
		OutputStream out = con.getOutputStream();
		out.write(sendXmlString.getBytes("UTF-8"));
		out.flush();
		out.close();
		System.out.print("response code : ");
		System.out.println(con.getResponseCode());
		
		BufferedInputStream in = new BufferedInputStream(con.getInputStream());
		ByteArrayOutputStream out2 = new ByteArrayOutputStream();
		int data = -1;
		while( (data=in.read()) >= 0 ) {
			out2.write(data);
		}
		byte[] recvBytes = out2.toByteArray();
		String recvXmlString = new String(recvBytes, StandardCharsets.UTF_8);
		System.out.println("response xml : " + recvXmlString);
		
		
		System.out.println("LEAVE : " + this.getMethodName());
		return recvXmlString;
	}//END OF sendXmlStringToServer()
	
	public String getMethodName() {
		return Thread.currentThread().getStackTrace()[2].getMethodName();
	}
}//END OF CLASS