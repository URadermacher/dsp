package eu.vdmr.dspgui.util.project.component.io.reader;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import eu.vdmr.dspgui.component.DSPGuiComponent;
import eu.vdmr.dspgui.component.DSPGuiComponentImpl;
import eu.vdmr.dspgui.util.project.ComponentFiler;
import eu.vdmr.dspgui.util.project.ComponentFilerImpl;
import eu.vdmr.dspgui.util.project.XMLLabel;
import eu.vdmr.util.clazz.ObjectInitialiser;
import eu.vdmr.util.xml.XMLFileWriter;

public class WAVReaderFiler extends ComponentFilerImpl implements ComponentFiler {
	private static Logger LOG = LogManager.getLogger(WAVReaderFiler.class);

	

		
		
		public void endElement(String uri, String localName, String qName, DSPGuiComponentImpl guiImpl) throws SAXException {                                                                
			if (XMLLabel.COMPONENTCLASS.equals(qName)){
//				WAVReader comp = new WAVReader();
//				guiImpl.setImpl(comp);
//			} else if (XMLLabel.COMPONENTID.equals(qName)){
//				guiImpl.getImpl().setId(sb.toString());
//			} else if (XMLLabel.COMPONENTNAME.equals(qName)){
//				guiImpl.getImpl().setName(sb.toString());
//			} else if (XMLLabel.COMPONENT.equals(qName)){
//				// NOP
//			} else if (XMLLabel.FILE.equals(qName)){
//				((WAVReader)guiImpl.getImpl()).setFileName(sb.toString());
//				guiImpl.setInitialized(true);
//			} else if (XMLLabel.READCOMPLETEIF.equals(qName)){
//				if ("NULL".equals(sb.toString())){
//					return;
//				}
				Object o = ObjectInitialiser.getObject(sb.toString());
//				((WAVReader)guiImpl.getImpl()).setReadCompleteIF((ReadCompleteIF)o);
			} else {
				LOG.error("Unknown tag " + qName);
			}
		}

}
