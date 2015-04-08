package com.harm.util;

import com.harm.schema.message.Message;

public class Constants {
	// fyi : project > properties > deployment assembly
	public static final String MESSAGE_SCHEMA_PATH = Message.class.getResource("/schema").getPath() + "message.xsd";
	
	public enum MESSAGE_ID {
		REG_CARD	("regCard"),
		REQ_ACCS	("reqAssc"),
		
		RES_PASS	("regPass"),
		RES_FAIL	("regFail"),
		;
		
		private String value;
		private MESSAGE_ID(String value) {
			this.value = value;
		}
		public String value() {
			return this.value;
		}
	}//END OF ENUM
	
	public enum ERROR_MESSAGE {
		INVALID_MESSAGE_ID	("invalid message id")
		;
		private String value;
		private ERROR_MESSAGE(String value) {
			this.value = value;
		}
		public String value() {
			return this.value;
		}
	}
	
}//END OF CLASS