package eu.vdmr.dspgui.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import eu.vdmr.dspgui.util.xmlentities.Entry;
import eu.vdmr.dspgui.util.xmlentities.Group;


/**
 * SAX parser for Components.xml
 * Will parse a component without special fields..
 * @author ura03640
 *
 */
public class ComponentReader extends DefaultHandler {
	private static Logger LOG = LogManager.getLogger(ComponentReader.class);
	private XMLReader xmlReader;
	private Stack<Group> stack;
	private Entry entry;
	private StringBuilder data;

	public ComponentReader(String filename) {
		LOG.debug("start reading " + filename);
		try {
			xmlReader = XMLReaderFactory.createXMLReader();
		} catch (SAXException e) {
			LOG.error("Error creating XMLReader " + e, e);
		}
		InputStream in = ClassLoader.getSystemResourceAsStream(filename);
		xmlReader.setContentHandler(this);
		stack = new Stack<Group>();
		data = new StringBuilder();
		try {
			xmlReader.parse(new InputSource(in));
		} catch (IOException e) {
			LOG.error("Error reading file " + filename + ": " + e, e);
			e.printStackTrace();
		} catch (SAXException e) {
			LOG.error("Error parsing file " + filename + ": " + e, e);
			e.printStackTrace();
		}
	}
	
	public Group getRoot(){
		Group g = (Group)stack.pop();
		return g;
	}


	@Override
	public void startDocument(){
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws org.xml.sax.SAXException {
		LOG.debug("Start element " + qName);
		if ("components".equals(qName)){
			//create a root entry
			stack.push(new Group());
		} else if ("group".equals(qName)){
			Group g = new Group();
			g.setName(attributes.getValue("name"));
			stack.push(g);
		} else if ("entry".equals(qName)){
			entry = new Entry();
		} else if ("displayname".equals(qName)){
			data = new StringBuilder();
		} else if ("class".equals(qName)){
			data = new StringBuilder();
		} else if ("impl".equals(qName)){
			data = new StringBuilder();
		} else {
			LOG.error("unknown TAG " + qName);
		}
		return;
	}

	public void endElement(String uri, String localName, String qName)
			throws org.xml.sax.SAXException {
		LOG.debug("End element " + qName);
		if ("components".equals(qName)){
			//finished
			LOG.debug("finished parsing ");
		} else if ("group".equals(qName)){
			Group g = stack.pop();
			Group parent = stack.peek();
			parent.addGroup(g);
		} else if ("entry".equals(qName)){
			Group parent = stack.peek();
			parent.addEntry(entry);
		} else if ("displayname".equals(qName)){
			entry.setName(data.toString());
		} else if ("class".equals(qName)){
			entry.setClassName(data.toString());
		} else if ("impl".equals(qName)){
			entry.setImplName(data.toString());
		}
		return;
	}

	// Method descriptor #4 ([CII)V
	// Stack: 0, Locals: 4
	public void characters(char[] ch, int start, int length) throws org.xml.sax.SAXException {
		data.append(ch, start, length);
		return;
	}

	// // Method descriptor #4 ([CII)V
	// // Stack: 0, Locals: 4
	// public void ignorableWhitespace(char[] arg0, int arg1, int arg2) throws
	// org.xml.sax.SAXException;
	// 0 return

	//
	// // Method descriptor #3 (Ljava/lang/String;Ljava/lang/String;)V
	// // Stack: 0, Locals: 3
	// public void processingInstruction(java.lang.String arg0, java.lang.String
	// arg1) throws org.xml.sax.SAXException;
	// 0 return
	//
	//
	// // Method descriptor #2 (Ljava/lang/String;)V
	// // Stack: 0, Locals: 2
	// public void skippedEntity(java.lang.String arg0) throws
	// org.xml.sax.SAXException;
	// 0 return

	// Method descriptor #42 (Lorg/xml/sax/SAXParseException;)V
	// Stack: 0, Locals: 2
	public void warning(org.xml.sax.SAXParseException arg0)
			throws org.xml.sax.SAXException {
		return;
	}

	// Method descriptor #42 (Lorg/xml/sax/SAXParseException;)V
	// Stack: 0, Locals: 2
	public void error(org.xml.sax.SAXParseException arg0)
			throws org.xml.sax.SAXException {
		return;
	}

	// Method descriptor #42 (Lorg/xml/sax/SAXParseException;)V
	// Stack: 1, Locals: 2
	public void fatalError(org.xml.sax.SAXParseException arg0)
			throws org.xml.sax.SAXException {
		return;
	}

}
