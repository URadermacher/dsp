package eu.vdmr.dsp.component.analyzer;

import java.io.File;

import eu.vdmr.dsp.component.AbstractAudio;
import eu.vdmr.dsp.component.DeviceType;

public abstract class AbstractAnalyzer extends AbstractAudio {

	
	public AbstractAnalyzer(String name, DeviceType type) {
		super(name, type);
	}
	
	public abstract void SetFileName(String name)throws Exception;
	
	public abstract void SetFile(File file)throws Exception;
}
