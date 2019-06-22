package eu.vdmr.dspgui.util.project.component.converter.channel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import eu.vdmr.dsp.component.converter.channel.StereoToMono;
import eu.vdmr.dspgui.component.DSPGuiComponentImpl;
import eu.vdmr.dspgui.util.project.ComponentFiler;
import eu.vdmr.dspgui.util.project.ComponentFilerImpl;
import eu.vdmr.dspgui.util.project.XMLLabel;

public class StereoToMonoFiler extends ComponentFilerImpl implements ComponentFiler {
	private static Logger LOG = LogManager.getLogger(StereoToMonoFiler.class);

	

	public void endElement(String uri, String localName, String qName, DSPGuiComponentImpl guiImpl) throws SAXException {
		// LOG.debug("endElement called, qname = " + qName);
		if (XMLLabel.COMPONENTCLASS.equals(qName)) {
			StereoToMono comp = new StereoToMono();
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
