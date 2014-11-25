package eu.vdrm.dsp.component.analyzer.transform;

import java.io.File;
import java.io.IOException;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;
import org.apache.log4j.Logger;
import eu.vdrm.dsp.component.DeviceType;
import eu.vdrm.dsp.component.analyzer.AbstractAnalyzer;
import eu.vdrm.dsp.component.data.FFTDataWriter;

public class SimpleFFT extends AbstractAnalyzer {
	private static Logger LOG = Logger.getLogger(SimpleFFT.class);
	
	/**
	 * how many samples will be used to calculate the FFT
	 */
	private int numberOfSamples;
	/**
	 * if there should be overlap, how far in the next set should it go?
	 */
	private int numberOfOverlap;
	
	private double[] dataBuffer; // = new double[1024];
	
	private int index = 0;
	
	private FastFourierTransformer  transformer;
	
	private FFTDataWriter fftWriter; 

	
	public SimpleFFT(){
		super("SimpleFFT",DeviceType.PROCESSOR);
		transformer = new  FastFourierTransformer(DftNormalization.STANDARD); 
		fftWriter = new FFTDataWriter();
	}
	
	@Override
	public int getSamples(short[] buffer, int length) {
		int ret = previous.getSamples(buffer, length);
		int count = 0;
		while (count < ret) {
			while (index < numberOfSamples && count < ret){
				dataBuffer[index++] = buffer[count++];
			}
			// evtl pad array
			if (ret < length || count >= ret){ // end of data reached
				while (index < numberOfSamples){
					dataBuffer[index++] = 0.0;
				}
			}
			Complex[] transformationResult = transformer.transform(dataBuffer, TransformType.FORWARD);
			
			writeData(transformationResult);
			index = 0; // start
		}
		
		return ret;
	}
	private void writeData(Complex[] data) {
		try {
			fftWriter.addData(data);
		} catch (IOException e) {
			LOG.error("IOexception writing data " + e,e);
		}
	}

	@Override
	public void stop() {
		try {
			fftWriter.end();
		} catch (IOException e) {
			LOG.error("IOexception closing datafile " + e,e);
		}
	}

    @Override
    public void SetFileName(String name) throws Exception {
        fftWriter.setFileName(name);
    }
    
    public String getFileName() throws IOException {
        return (fftWriter.getFile() == null?null:fftWriter.getFile().getCanonicalPath());
    }

    @Override
    public void SetFile(File file) throws Exception {
        fftWriter.setFile(file);
    }

    public int getNumberOfSamples() {
        return numberOfSamples;
    }

    public void setNumberOfSamples(int numberOfSamples) {
        this.numberOfSamples = numberOfSamples;
        dataBuffer = new double[numberOfSamples];
    }

    public int getNumberOfOverlap() {
        return numberOfOverlap;
    }

    public void setNumberOfOverlap(int numberOfOverlap) {
        this.numberOfOverlap = numberOfOverlap;
    }
}
