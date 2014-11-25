package eu.vdrm.dspgui.util.project.component.filter;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import eu.vdrm.dsp.component.filter.Decrackler;
import eu.vdrm.dspgui.component.DSPGuiComponentImpl;
import eu.vdrm.dspgui.util.project.ComponentFiler;
import eu.vdrm.dspgui.util.project.ComponentFilerImpl;
import eu.vdrm.dspgui.util.project.XMLLabel;


public class DecracklerFiler extends ComponentFilerImpl  implements ComponentFiler{
	private static Logger LOG = Logger.getLogger(DecracklerFiler.class);
	



	
	public void endElement(String uri, String localName, String qName,DSPGuiComponentImpl guiImpl) throws SAXException {                                                                
		if (XMLLabel.COMPONENTCLASS.equals(qName)){
			Decrackler comp = new Decrackler();
			guiImpl.setImpl(comp);
		} else if (XMLLabel.COMPONENTID.equals(qName)){
			guiImpl.getImpl().setId(sb.toString());
		} else if (XMLLabel.COMPONENTNAME.equals(qName)){
			guiImpl.getImpl().setName(sb.toString());
		} else if (XMLLabel.COMPONENT.equals(qName)){
			// NOP
		} else {
			LOG.error("Unknown tag " + qName);
		}
	}
	


}
