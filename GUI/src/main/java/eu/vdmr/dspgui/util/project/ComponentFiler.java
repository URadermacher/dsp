package eu.vdmr.dspgui.util.project;

import java.io.IOException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import eu.vdmr.dspgui.component.DSPGuiComponent;
import eu.vdmr.dspgui.component.DSPGuiComponentImpl;
import eu.vdmr.util.xml.XMLFileWriter;

/**
 * Interface to be implemented by classes which can read and write xml representations of (GUI-)components.
 * There is an abstract class ComponentFilerImpl, which handles 
 * 		StartElement events (just create a new StringBuilder) and 
 * 		Character events (just append to StringBuilder)
 * @author ura03640
 *
 */
public interface ComponentFiler {
	
	public void write(int indent, XMLFileWriter fw, DSPGuiComponent guiComp) throws IOException;
	
	public void writeSpecific(int indent, XMLFileWriter fw, DSPGuiComponent guiComp) throws IOException;
	
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException;

	public void endElement(String uri, String localName, String qName, DSPGuiComponentImpl guiImpl) throws SAXException;

	public void characters(char[] ch, int start, int length) throws SAXException;
	

}
