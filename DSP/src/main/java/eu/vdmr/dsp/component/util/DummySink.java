package eu.vdmr.dsp.component.util;

import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.vdmr.dsp.component.io.player.WAVPlayer;
import eu.vdmr.dsp.util.audio.AbstractAudioSink;
import eu.vdmr.dsp.util.data.ExecutionResult;


/**
 * Dummy Sink class. A Dummy Sink  will just pull data from it's previous.
 * If set to continuous it will do so until stopped, otherwise it will just pull one buffer and then stop. 
 * @author ura03640
 *
 */
public class DummySink extends AbstractAudioSink implements Callable<ExecutionResult>{
	private static Logger LOG = LogManager.getLogger(DummySink.class);
	private short[] buffer = new short[DEFAULTBUFFERSIZE];
	private boolean continuous;
	
	public DummySink(){
		super("DummySink");
	}

	//@Override
	public ExecutionResult call() throws Exception {
		stopped = false;
		if (! continuous) {
			stopped = true;
		}
		int len = previous.getSamples(buffer, DEFAULTBUFFERSIZE);
		LOG.debug("before loop, read " + len);
		while (len != -1 && ! stopped){
			len = previous.getSamples(buffer, DEFAULTBUFFERSIZE);
			LOG.debug("in loop, read " + len);
		}
			
		return new ExecutionResult(true);
	}
	
	public void setContinuous(boolean cont) {
		this.continuous = cont;
	}

	
	public boolean isContinuous() {
		return continuous;
	}

}
