package com.harm.util;

import com.harm.schema.message.Message;

public class Constants {
	// fyi : project > properties > deployment assembly
	public static final String MESSAGE_SCHEMA_PATH = Message.class.getResource("/schema").getPath() + "message.xsd";
	
	public enum MESSAGE_ID {
		REG_CARD	("regCard"),
		REQ_ACCS	("reqAssc"),
		
		RES_PASS	("resPass"),
		RES_FAIL	("resFail");
		
		private String value;
		private MESSAGE_ID(String value) { this.value = value; }
		public String value() { return this.value; }
	}//END OF ENUM
	
	public enum ERROR_MESSAGE {
		INVALID_MESSAGE_ID	("invalid message id");
		private String value;
		private ERROR_MESSAGE(String value) { this.value = value; }
		public String value() { return this.value; }
	}//END OF ENUM
	
	public enum DEBUG_MESSAGE {
		REG_CARD_PASS	("register card pass"),
		REG_CARD_FAIL	("register card fail"),
		
		REQ_ACCS_PASS	("request access pass"),
		REQ_ACCS_FAIL	("request access fail"),
		
		INSERT_ACCESS_HISTORY_PASS("insert access history pass"),
		INSERT_ACCESS_HISTORY_FAIL("insert access history fail");
		private String value;
		private DEBUG_MESSAGE(String value) { this.value = value; }
		public String value() { return this.value; }
	}//END OF ENUM	
	
}//END OF CLASS