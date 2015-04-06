package com.harm.util;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventLocator;
import javax.xml.bind.util.ValidationEventCollector;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

public class XmlConverter {
	
	private static final Logger logger = LoggerFactory.getLogger(XmlConverter.class);
	
	public static Object convertXmlToJaxb(Class<?> clazz, String xmlString, String schemaFullPath) {
		
		Object jaxbObj = null;
		
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
			Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = schemaFactory.newSchema(new File(schemaFullPath));
			unMarshaller.setSchema(schema);
			unMarshaller.setEventHandler(new JAXBValidator());
			jaxbObj = unMarshaller.unmarshal(new StringReader(xmlString)); 
					
			if(jaxbObj == null) {
				throw new JAXBException("why null?");
			}
		} catch (JAXBException e) {
			logger.error(e.getMessage(), e);
		} catch (SAXException e) {
			logger.error(e.getMessage(), e);
		}
		
		return jaxbObj;
	}//END OF convertXmlToJaxb()
	
	public static String convertJaxbToXml(Class<?> clazz, Object jaxbObj, String schemaFullPath) {
		String xmlString = null;
		StringWriter stringWriter = new StringWriter();
		
		try {
			
			Marshaller marshaller = JAXBContext.newInstance(clazz).createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, StandardCharsets.UTF_8.toString());
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, schemaFullPath);
			marshaller.setSchema(SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(new File(schemaFullPath)));
			marshaller.setEventHandler(new JAXBValidator());
			
			stringWriter.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
			
			marshaller.marshal(jaxbObj, stringWriter);
			xmlString = stringWriter.toString();
			
		} catch (JAXBException e) {
			logger.error(e.getMessage(), e);
		} catch (SAXException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				stringWriter.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		
		return xmlString;
	}//END OF convertJaxbToXml()
	
}//END OF CLASS







/**
 * 
 * @author herdin
 *
 */
class JAXBValidator extends ValidationEventCollector {
	@Override
    public boolean handleEvent(ValidationEvent event) {
		switch (event.getSeverity()) {
			case ValidationEvent.WARNING :
				//logging point
				this.printInfo(event);
				break;
			case ValidationEvent.ERROR :
				//logging point
				this.printInfo(event);
				break;
			case ValidationEvent.FATAL_ERROR :
				//logging point
				this.printInfo(event);
				break;
			default:
				//logging point
				this.printInfo(event);
				break;
		}
		return false;
	}
	private void printInfo(ValidationEvent event) {
		ValidationEventLocator locator = event.getLocator();
		
		System.out.println(locator.getLineNumber());
		System.out.println(locator.getColumnNumber());
		System.out.println(event.getMessage());
	}
}//END OF CLASS