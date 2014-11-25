package eu.vdrm.dspgui.util.project;
import java.io.IOException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import eu.vdrm.dsp.component.DSPComponentImpl;
import eu.vdrm.dspgui.component.DSPGuiComponent;
import eu.vdrm.util.xml.XMLFileWriter;

public abstract class ComponentFilerImpl implements ComponentFiler {
	//private static Logger LOG = Logger.getLogger(ComponentFilerImpl.class);

	protected StringBuilder sb = new StringBuilder();

	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		sb = new StringBuilder();
	}


	public void characters(char[] ch, int start, int length) throws SAXException {
		sb.append(ch, start, length);
	}
	
	public void write(int indent, XMLFileWriter fw,	DSPGuiComponent guiComponent) throws IOException {
		DSPComponentImpl comp = (DSPComponentImpl)guiComponent.getImpl();
		fw.startLabel(indent, XMLLabel.COMPONENT);
		indent++;
		fw.makeLabel(indent, XMLLabel.COMPONENTCLASS, comp.getClass().getName());
		fw.makeLabel(indent, XMLLabel.COMPONENTID,  comp.getId());
		fw.makeLabel(indent, XMLLabel.COMPONENTNAME, comp.getName());
		writeSpecific(indent, fw, guiComponent);
		indent--;
		fw.endLabel(indent, XMLLabel.COMPONENT);
	}
	
	public void writeSpecific(int indent, XMLFileWriter fw, DSPGuiComponent guiComp) throws IOException {
		//override iff anything special must be written for this component
	}


	
}
