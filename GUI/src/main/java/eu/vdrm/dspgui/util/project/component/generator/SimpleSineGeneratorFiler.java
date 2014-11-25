package eu.vdrm.dspgui.util.project.component.generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import eu.vdrm.dsp.component.generator.SimpleSineGenerator;
import eu.vdrm.dspgui.component.DSPGuiComponent;
import eu.vdrm.dspgui.component.DSPGuiComponentImpl;
import eu.vdrm.dspgui.util.project.ComponentFiler;
import eu.vdrm.dspgui.util.project.ComponentFilerImpl;
import eu.vdrm.dspgui.util.project.XMLLabel;
import eu.vdrm.util.xml.XMLFileWriter;

public class SimpleSineGeneratorFiler extends ComponentFilerImpl  implements ComponentFiler{
	private static Logger LOG = Logger.getLogger(SimpleSineGeneratorFiler.class);

	private List<Integer> freqs;
	
	@Override
	public void writeSpecific(int indent, XMLFileWriter fw, DSPGuiComponent guiComponent) throws IOException {
		SimpleSineGenerator comp = (SimpleSineGenerator)guiComponent.getImpl();
		fw.startLabel(indent, XMLLabel.FREQUENCIES);
		int[] cf = comp.getFrequencies();
		indent++;
		for (int i = 0; i < cf.length; i++){
			fw.makeLabel(indent, XMLLabel.FREQUENCY,""+cf[i]);
		}
		indent--;
		fw.endLabel(indent, XMLLabel.FREQUENCIES);
	}


	
	public void endElement(String uri, String localName, String qName, DSPGuiComponentImpl guiImpl) throws SAXException {                                                                
		if (XMLLabel.COMPONENTCLASS.equals(qName)){
			SimpleSineGenerator comp = new SimpleSineGenerator();
			guiImpl.setImpl(comp);
		} else if (XMLLabel.COMPONENTID.equals(qName)){
			guiImpl.getImpl().setId(sb.toString());
		} else if (XMLLabel.COMPONENTNAME.equals(qName)){
			guiImpl.getImpl().setName(sb.toString());
		} else if (XMLLabel.COMPONENT.equals(qName)){
			// NOP
		} else if (XMLLabel.FREQUENCIES.equals(qName)){
			int[] intfr = new int[freqs.size()];
			for (int i = 0; i < freqs.size(); i++){
				intfr[i] = freqs.get(i);
			}
			((SimpleSineGenerator)guiImpl.getImpl()).setFrequencies(intfr);
			guiImpl.setInitialized(true);
			freqs = null;
		} else if (XMLLabel.FREQUENCY.equals(qName)){
			if (freqs == null){
				freqs = new ArrayList<Integer>();
			}
			freqs.add(Integer.parseInt(sb.toString()));
		} else {
			LOG.error("Unknown tag " + qName);
		}
	}

}
