package com.harm.sgc;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;

import com.harm.schema.message.Message;
import com.harm.util.XmlConverter;


public class SimpleJava {

	public static void main(String[] args) throws IOException {
		
		boolean XML_SEND_RECEIVE_TEST	= false;
		boolean JAXB_CONVERT_TEST		= true;
		
		if(XML_SEND_RECEIVE_TEST) {
		
			String param = "<xml><messageId>regCard</messageId><cardId>098098</cardId></xml>";
			byte[] bytes = param.getBytes(StandardCharsets.UTF_8);
			System.out.println("bytes start.");
			for(int i=0; i<bytes.length; i++) {
				System.out.print(String.format("%02x", bytes[i]));
				System.out.print(" ");
			}
			System.out.println("bytes end.");
			
			System.out.println("string start.");
			String str = new String(bytes, StandardCharsets.UTF_8);
			System.out.println(str);
			System.out.println("string end.");
		
			SimpleJava sj = new SimpleJava();
			sj.sendXmlStringToServer(param);
		}
		
		if(JAXB_CONVERT_TEST) {
			String xmlString = "<message><messageId>123</messageId><deviceId>456</deviceId><cardId>789</cardId></message>";
			String schemaFullPath = "C:\\spring-tool-suite\\sts-bundle\\workspace\\SpringMVCProject02\\src\\main\\resources\\schema\\message.xsd";
//			String schemaFullPath = System.class.getResource("/com/spring/schema/").getPath() + "message.xsd";
			Message message = (Message)XmlConverter.convertXmlToJaxb(Message.class, xmlString, schemaFullPath);
			System.out.println(message.getMessageId());
			System.out.println(message.getDeviceId());
			System.out.println(message.getCardId());
			
			String xmlStringOut = XmlConverter.convertJaxbToXml(Message.class, message, schemaFullPath);
			System.out.println(xmlStringOut);
		}
	}//END OF MAIN

	
	public void sendXmlStringToServer(String xmlString) throws IOException {
		System.out.println("ENTER : " + this.getMethodName());
		URL url = new URL("http://localhost:8080/sample/xml/d");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setRequestMethod("POST");
		OutputStream out = con.getOutputStream();
		out.write(xmlString.getBytes("UTF-8"));
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
		String recvString = new String(recvBytes, StandardCharsets.UTF_8);
		System.out.println("response xml : " + recvString);
		
		
		System.out.println("LEAVE : " + this.getMethodName());
	}//END OF sendXmlStringToServer()
	
	public String getMethodName() {
		return Thread.currentThread().getStackTrace()[2].getMethodName();
	}
}//END OF CLASS