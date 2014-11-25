package eu.vdrm.dsp.component.analyzer;

import java.io.File;

import eu.vdrm.dsp.component.AbstractAudio;
import eu.vdrm.dsp.component.DeviceType;

public abstract class AbstractAnalyzer extends AbstractAudio {

	
	public AbstractAnalyzer(String name, DeviceType type) {
		super(name, type);
	}
	
	public abstract void SetFileName(String name)throws Exception;
	
	public abstract void SetFile(File file)throws Exception;
}
