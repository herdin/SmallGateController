package com.harm.util;

import com.harm.schema.message.Message;

public class Constants {
	
	public static final String MESSAGE_SCHEMA_PATH = Message.class.getResource("/schema").getPath() + "message.xsd";
	
	public enum MESSAGE_ID {
		REG_CARD	("regCard")		
		;
		
		private String value;
		
		private MESSAGE_ID(String value) {
			this.value = value;
		}
		public String value() {
			return this.value;
		}
	}
	
	
}//END OF CLASS