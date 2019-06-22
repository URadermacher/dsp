package eu.vdmr.dspgui.util.project.component.io.writer;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import eu.vdmr.dsp.component.io.writer.DataLogger;
import eu.vdmr.dspgui.component.DSPGuiComponent;
import eu.vdmr.dspgui.component.DSPGuiComponentImpl;
import eu.vdmr.dspgui.util.project.ComponentFiler;
import eu.vdmr.dspgui.util.project.ComponentFilerImpl;
import eu.vdmr.dspgui.util.project.XMLLabel;
import eu.vdmr.util.xml.XMLFileWriter;

public class DataLoggerFiler   extends ComponentFilerImpl implements ComponentFiler{
	private static Logger LOG = LogManager.getLogger(DataLoggerFiler.class);


	@Override
	public void writeSpecific(int indent, XMLFileWriter fw, DSPGuiComponent guiComp) throws IOException {
		DataLogger comp = (DataLogger)guiComp.getImpl();
		fw.makeLabel(indent, XMLLabel.SILENT, comp.isSilent()?"true":"false" );
		fw.makeLabel(indent, XMLLabel.CONTINUOUS, comp.isContinuous()?"true":"false" );
	}
	




	public void endElement(String uri, String localName, String qName, DSPGuiComponentImpl guiImpl) throws SAXException {                                                                
		if (XMLLabel.COMPONENTCLASS.equals(qName)){
			DataLogger comp = new DataLogger();
			guiImpl.setImpl(comp);
		} else if (XMLLabel.COMPONENTID.equals(qName)){
			guiImpl.getImpl().setId(sb.toString());
		} else if (XMLLabel.COMPONENTNAME.equals(qName)){
			guiImpl.getImpl().setName(sb.toString());
		} else if (XMLLabel.COMPONENT.equals(qName)){
			// NOP
		} else if (XMLLabel.SILENT.equals(qName)){
			((DataLogger)guiImpl.getImpl()).setSilent(Boolean.parseBoolean(sb.toString()));
		} else if (XMLLabel.CONTINUOUS.equals(qName)){
			((DataLogger)guiImpl.getImpl()).setContinuous(Boolean.parseBoolean(sb.toString()));
		} else {
			LOG.error("Unknown tag " + qName);
		}
	}
	

}
