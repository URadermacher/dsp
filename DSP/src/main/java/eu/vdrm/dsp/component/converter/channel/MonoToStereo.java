package eu.vdrm.dsp.component.converter.channel;

import org.apache.log4j.Logger;

import eu.vdrm.dsp.component.AbstractAudio;
import eu.vdrm.dsp.component.DeviceType;


public class MonoToStereo extends AbstractAudio{
	private static Logger LOG = Logger.getLogger(MonoToStereo.class);
	private short[] samples = new short[DEFAULTBUFFERSIZE];
	/** as out output is double as big as the input we toggle this var to know whether we write the first or second part of out samples to next*/
	boolean firstcall = true;
	/** Number of samples read the last time getSamples was called on previous */
	private int got;
	
	public MonoToStereo(){
		super("MonoToStereo",DeviceType.PROCESSOR);
	}
	
	/**
	 * Provide samples to subsequent stages of processing
	 * fill a buffer of length len
	 * we convert mono to stereo, thus write each incoming sample twice in the output
	 * @param buffer the buffer
	 * @param len the length
	 * @return the number of samples written into the buffer. 
	 */
	public int getSamples(short [] buffer, int length) {
		int posin = 0;  // position in the input buffer
		int posout = 0; // position in the output buffer
		int count = 0;
		if (firstcall) {
			firstcall = false;
			got = previous.getSamples(samples, length);
			LOG.debug("previous returned " + got);
			if (got == -1){ // EOF
				return -1;
			}
			posin = 0;
		} else { // during second call return second half of input buffer
			firstcall = true;
			posin = length/2;
		}
		
		if (LOG.isDebugEnabled()){LOG.debug("count = " + count + " lenght = "  + length);}
		while (count++ < length/2){
			//if (LOG.isDebugEnabled()){LOG.debug("posin = " + posin);}
			buffer[posout*2] = samples[posin];
			buffer[posout*2 + 1] = samples[posin];
			posin++;
			posout++;
		}
		return length; 
	}
}
