package eu.vdrm.dspgui.util.project.component.analyzer;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import eu.vdrm.dsp.component.analyzer.counter.ZeroCrossingCounter;
import eu.vdrm.dspgui.component.DSPGuiComponent;
import eu.vdrm.dspgui.component.DSPGuiComponentImpl;
import eu.vdrm.dspgui.util.project.ComponentFiler;
import eu.vdrm.dspgui.util.project.ComponentFilerImpl;
import eu.vdrm.dspgui.util.project.XMLLabel;
import eu.vdrm.util.xml.XMLFileWriter;

public class ZeroCrossingCounterFiler  extends ComponentFilerImpl  implements ComponentFiler{
	private static Logger LOG = Logger.getLogger(ZeroCrossingCounterFiler.class);
	
	
	@Override
	public void writeSpecific(int indent, XMLFileWriter fw, DSPGuiComponent guiComp) throws IOException {
		ZeroCrossingCounter comp = (ZeroCrossingCounter)guiComp.getImpl();
		fw.makeLabel(indent, XMLLabel.COUNT_PER, ""+comp.getInterval() );
	}
	



	
	public void endElement(String uri, String localName, String qName,DSPGuiComponentImpl guiImpl) throws SAXException {                                                                
		if (XMLLabel.COMPONENTCLASS.equals(qName)){
			ZeroCrossingCounter comp = new ZeroCrossingCounter();
			guiImpl.setImpl(comp);
		} else if (XMLLabel.COMPONENTID.equals(qName)){
			guiImpl.getImpl().setId(sb.toString());
		} else if (XMLLabel.COMPONENTNAME.equals(qName)){
			guiImpl.getImpl().setName(sb.toString());
		} else if (XMLLabel.COMPONENT.equals(qName)){
			// NOP
		} else if (XMLLabel.COUNT_PER.equals(qName)){
			((ZeroCrossingCounter)guiImpl.getImpl()).setInterval(Integer.parseInt(sb.toString()));
			guiImpl.setInitialized(true);
		} else {
			LOG.error("Unknown tag " + qName);
		}
	}
	


}
