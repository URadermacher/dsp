package eu.vdrm.dsp.component.converter.channel;

import org.apache.log4j.Logger;

import eu.vdrm.dsp.component.AbstractAudio;
import eu.vdrm.dsp.component.DeviceType;

public class StereoToMono extends AbstractAudio{
	private static Logger LOG = Logger.getLogger(StereoToMono.class);
	private short[] samples = new short[DEFAULTBUFFERSIZE];
	/** Number of samples read the last time getSamples was called on previous */
	private int got;
	
	public StereoToMono(){
		super("StereoToMono",DeviceType.PROCESSOR);
	}
	
	/**
	 * Provide samples to subsequent stages of processing
	 * fill a buffer of length len
	 * we convert stereo to mono, thus we add 2 samples of input to one output sample
	 * @param buffer the buffer
	 * @param len the length
	 * @return the number of samples written into the buffer. 
	 */
	public int getSamples(short [] buffer, int length) {
		LOG.debug("my get called");
		int posin = 0;  // position in the input buffer
		int posout = 0; // position in the output buffer
		int count1 = 0;
		got = previous.getSamples(samples, length);
		LOG.debug("previous1 returned " + got);
		if (got == -1){ // EOF
			return -1;
		}
		
		if (LOG.isDebugEnabled()){LOG.debug("count = " + count1 + " lenght = "  + length);}
		while (count1++ < got){
			//if (LOG.isDebugEnabled()){LOG.debug("posin = " + posin);}
			buffer[posout] = (short)(samples[posin*2]/2 + samples[posin*2 + 1]/2);
			posin++;
			posout++;
		}
		int count2 = 0;
		if (count1 == length){ // we got a full buffer last time, read next
			posin = 0;  // position in the input buffer
			got = previous.getSamples(samples, length);
			LOG.debug("previous2 returned " + got);
			if (got == -1){ // EOF
				return count1;
			}
			
			if (LOG.isDebugEnabled()){LOG.debug("count = " + count1 + " lenght = "  + length);}
			while (count2++ < got){
				//if (LOG.isDebugEnabled()){LOG.debug("posin = " + posin);}
				buffer[posout] = (short)(samples[posin*2]/2 + samples[posin*2 + 1]/2);
				posin++;
				posout++;
			}
		}
		return count1 + count2; 
	}
}
