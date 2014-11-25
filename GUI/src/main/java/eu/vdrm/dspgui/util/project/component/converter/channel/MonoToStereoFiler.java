package eu.vdrm.dspgui.util.project.component.converter.channel;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import eu.vdrm.dsp.component.converter.channel.MonoToStereo;
import eu.vdrm.dspgui.component.DSPGuiComponentImpl;
import eu.vdrm.dspgui.util.project.ComponentFiler;
import eu.vdrm.dspgui.util.project.ComponentFilerImpl;
import eu.vdrm.dspgui.util.project.XMLLabel;

public class MonoToStereoFiler extends ComponentFilerImpl implements ComponentFiler {
	private static Logger LOG = Logger.getLogger(MonoToStereoFiler.class);




	public void endElement(String uri, String localName, String qName, DSPGuiComponentImpl guiImpl) throws SAXException {
		// LOG.debug("endElement called, qname = " + qName);
		if (XMLLabel.COMPONENTCLASS.equals(qName)) {
			MonoToStereo comp = new MonoToStereo();
			guiImpl.setImpl(comp);
		} else if (XMLLabel.COMPONENTID.equals(qName)) {
			guiImpl.getImpl().setId(sb.toString());
		} else if (XMLLabel.COMPONENTNAME.equals(qName)) {
			guiImpl.getImpl().setName(sb.toString());
		} else if (XMLLabel.COMPONENT.equals(qName)) {
			// NOP: out component is finished..
		} else {
			LOG.error("Unknown tag " + qName);
		}
	}



}
