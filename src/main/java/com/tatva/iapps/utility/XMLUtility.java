package com.tatva.iapps.utility;

import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

public class XMLUtility {

	public static Validator initializeValidator(Schema schema) throws SAXException {
		return schema.newValidator();
	}
	
	public static SchemaFactory initializeSchemaFactory() {
		return SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	}
	
	public static Schema initializeSchema(String xsdPath) throws SAXException {
		URL url = XMLUtility.class.getClassLoader().getResource(xsdPath);
         return initializeSchemaFactory().newSchema(url);
	}
}
