package eu.vdmr.dspgui.util.project.component.util;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import eu.vdmr.dsp.component.util.DummySink;
import eu.vdmr.dspgui.component.DSPGuiComponent;
import eu.vdmr.dspgui.component.DSPGuiComponentImpl;
import eu.vdmr.dspgui.util.project.ComponentFiler;
import eu.vdmr.dspgui.util.project.ComponentFilerImpl;
import eu.vdmr.dspgui.util.project.XMLLabel;
import eu.vdmr.util.xml.XMLFileWriter;

public class DummySinkFiler  extends ComponentFilerImpl  implements ComponentFiler{
	private static Logger LOG = LogManager.getLogger(DummySinkFiler.class);
	
	
	@Override
	public void writeSpecific(int indent, XMLFileWriter fw, DSPGuiComponent guiComp) throws IOException {
		DummySink comp = (DummySink)guiComp.getImpl();
		fw.makeLabel(indent, XMLLabel.CONTINUOUS, comp.isContinuous()?"true":"false" );
	}
	



	
	public void endElement(String uri, String localName, String qName,DSPGuiComponentImpl guiImpl) throws SAXException {                                                                
		if (XMLLabel.COMPONENTCLASS.equals(qName)){
			DummySink comp = new DummySink();
			guiImpl.setImpl(comp);
		} else if (XMLLabel.COMPONENTID.equals(qName)){
			guiImpl.getImpl().setId(sb.toString());
		} else if (XMLLabel.COMPONENTNAME.equals(qName)){
			guiImpl.getImpl().setName(sb.toString());
		} else if (XMLLabel.COMPONENT.equals(qName)){
			// NOP
		} else if (XMLLabel.CONTINUOUS.equals(qName)){
			((DummySink)guiImpl.getImpl()).setContinuous(Boolean.parseBoolean(sb.toString()));
			guiImpl.setInitialized(true);
		} else {
			LOG.error("Unknown tag " + qName);
		}
	}
	


}
