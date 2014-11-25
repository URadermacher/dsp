package eu.vdrm.dsp.component.generator;



import org.apache.log4j.Logger;

import eu.vdrm.dsp.component.AbstractAudio;
import eu.vdrm.dsp.component.DeviceType;

public class SimpleSineGenerator extends AbstractAudio{
	private static Logger LOG = Logger.getLogger(SimpleSineGenerator.class);
	private static short[] samples = null;
	
	private int[] pos;
	private int[] frequencies;
	private short freqCnt;
	private static double SHORTSCALE = 32767.0;
	
	public SimpleSineGenerator(){
		super("SimpleSine",DeviceType.SOURCE);
		init();
	}
	
	
	private synchronized void init(){
		if (samples != null){
			return; // already initialized..
		}
		LOG.debug("Initializing. Sampling rate = " + samplingRate);
		// build one cycle of a sine for the whole array (i.e. 1 Hz)
		// sin() returns a number between -1 and 1, thus scale to amplitude
		samples = new short[samplingRate];
		double scale = 2.0 * Math.PI / samplingRate;
		for (int sample = 0; sample < samplingRate; sample++){
			double sin = Math.sin(sample * scale);
			short samp = (short)(SHORTSCALE * sin);
			samples[sample] = samp;
			//LOG.debug(samp);
		}
	}
	
	
	public void setFrequencies(int[] f){
		frequencies= f;
		pos = new int[f.length];
		for (int i = 0; i < f.length; i++){
			pos[i] = 0;
		}
		freqCnt = new Integer(f.length).shortValue();
	}
	
	public int[] getFrequencies(){
		return frequencies;
	}
	
	/**
	 * Provide samples to subsequent stages of processing
	 * fill a buffer of length len
	 * @param buffer the buffer
	 * @param len the length
	 * @return the number of samples written into the buffer. For this component it's always the required len.
	 * (e.g. FileReaders could hit the EOF...) 
	 */
	public int getSamples(short [] buffer, int length) {
		LOG.debug("get called, nr of freqs = " + frequencies.length);

		if (frequencies.length == 1){
			return simpleSamples(buffer, length);
		}
		int sample = 0;
		int count = length;
		
		while (count-- !=  0){
			for (int i = 0; i < frequencies.length; i++){
				buffer[sample] = new Integer(samples[pos[i]]/freqCnt).shortValue();
			}
			sample++;
			// advance pos depending on desired frequency
			for (int i = 0; i < frequencies.length; i++){
				pos[i] = pos[i] + frequencies[i];
				// check end of samples:
				if (pos[i] >= samplingRate){
					pos[i] = pos[i] - samplingRate;
				}
			}
		}
		return length; // we always fill all the required samples!
	}

	public int simpleSamples(short [] buffer, int length) {
		int sample = 0;
		int count = length;
		
		while (count-- !=  0){
			buffer[sample++] = samples[pos[0]];
			
			// advance pos depending on desired frequency
			pos[0] += frequencies[0];
			// check end of samples:
			if (pos[0] >= samplingRate){
				pos[0] = pos[0] - samplingRate;
			}
		}
		return length; // we always fill all the required samples!
	}


}
