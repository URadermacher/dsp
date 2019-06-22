package eu.vdmr.dsp.util.audio;

import java.util.concurrent.Callable;

import eu.vdmr.dsp.component.AbstractAudio;
import eu.vdmr.dsp.component.DeviceType;
import eu.vdmr.dsp.util.data.ExecutionResult;


/**
 * An AbstractAudio that acts as a sink, i.e. a last component in a chain: this component will pull
 * data from it's 'previous' component, and is a threads start point. It will never be itself a
 * previous to any other component.
 * @author ura03640
 *
 */
public abstract class AbstractAudioSink extends AbstractAudio implements Callable<ExecutionResult> {
	
	protected boolean stopped = false;

	public AbstractAudioSink(String name){
		super(name,DeviceType.SINK);
	}
	
	@Override
	/**
	 * As SINK this will never be called
	 */
	public int getSamples(short[] buffer, int length) {
		throw new IllegalStateException("Method getSamples of Sink " + name + " was called! ");
	}
	
	public void stop() throws Exception{
		stopped = true;
		if (previous != null) {
			previous.stop();
		}
	}

}
