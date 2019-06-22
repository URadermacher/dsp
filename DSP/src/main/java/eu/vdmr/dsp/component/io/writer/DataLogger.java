package eu.vdmr.dsp.component.io.writer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.vdmr.dsp.util.audio.AbstractAudioSink;
import eu.vdmr.dsp.util.data.ExecutionResult;

/**
 * a writer which just writes incoming data to the log
 * @author ura03640
 *
 */
public class DataLogger extends AbstractAudioSink {
	private static Logger LOG = LogManager.getLogger(DataLogger.class);
	private short[] buffer = new short[DEFAULTBUFFERSIZE];
	private boolean continuous = false;
	// if true, this component can just be used to pull the data chain.
	private boolean silent = false;

	

	public DataLogger(){
		super("DataDumper");
	}


	public ExecutionResult call() throws Exception {
		int len = previous.getSamples(buffer, DEFAULTBUFFERSIZE);
		if (continuous){
			while (len != -1){
				logBuffer(len);
				len = previous.getSamples(buffer, DEFAULTBUFFERSIZE);
			}
		} else { // just once
			logBuffer(len);
		}
		
		return new ExecutionResult(true);
	}
	
	private void logBuffer(int len){
		if (silent){ // we are used used to pull the stuff
			return;
		}
		for (int i = 0; i < len; i++){
			//byte[] res = BBConverter.shortToByte(buffer);
			LOG.debug(buffer[i]);
		}
	}

	public boolean isSilent() {
		return silent;
	}

	public void setSilent(boolean silent) {
		this.silent = silent;
	}

	public boolean isContinuous() {
		return continuous;
	}

	public void setContinuous(boolean continuous) {
		this.continuous = continuous;
	}

	

	

}
