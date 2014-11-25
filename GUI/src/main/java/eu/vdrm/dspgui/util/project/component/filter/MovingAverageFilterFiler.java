package eu.vdrm.dspgui.util.project.component.filter;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import eu.vdrm.dsp.component.filter.average.MovingAverageFilter;
import eu.vdrm.dspgui.component.DSPGuiComponent;
import eu.vdrm.dspgui.component.DSPGuiComponentImpl;
import eu.vdrm.dspgui.util.project.ComponentFiler;
import eu.vdrm.dspgui.util.project.ComponentFilerImpl;
import eu.vdrm.dspgui.util.project.XMLLabel;
import eu.vdrm.util.xml.XMLFileWriter;

public class MovingAverageFilterFiler extends ComponentFilerImpl  implements ComponentFiler{
	private static Logger LOG = Logger.getLogger(MovingAverageFilterFiler.class);

	
	public void writeSpecific(int indent, XMLFileWriter fw, DSPGuiComponent guiComp) throws IOException {
		MovingAverageFilter comp = (MovingAverageFilter)guiComp.getImpl();
		fw.makeLabel(indent, XMLLabel.POINTS, ""+comp.getPoints() );
	}
	


	
	public void endElement(String uri, String localName, String qName, DSPGuiComponentImpl guiImpl) throws SAXException {                                                                
		if (XMLLabel.COMPONENTCLASS.equals(qName)){
			MovingAverageFilter comp = new MovingAverageFilter();
			guiImpl.setImpl(comp);
		} else if (XMLLabel.COMPONENTID.equals(qName)){
			guiImpl.getImpl().setId(sb.toString());
		} else if (XMLLabel.COMPONENTNAME.equals(qName)){
			guiImpl.getImpl().setName(sb.toString());
		} else if (XMLLabel.COMPONENT.equals(qName)){
			// NOP
		} else if (XMLLabel.POINTS.equals(qName)){
			((MovingAverageFilter)guiImpl.getImpl()).setPoints(Integer.parseInt(sb.toString()));
			guiImpl.setInitialized(true);
		} else {
			LOG.error("Unknown tag " + qName);
		}
	}

}
