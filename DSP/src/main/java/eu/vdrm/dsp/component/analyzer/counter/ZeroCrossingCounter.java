package eu.vdrm.dsp.component.analyzer.counter;

import java.io.File;
import java.io.IOException;
import org.apache.log4j.Logger;
import eu.vdrm.dsp.component.DeviceType;
import eu.vdrm.dsp.component.analyzer.AbstractAnalyzer;
import eu.vdrm.dsp.component.data.ZeroCountDataWriter;

/**
 * counts the number of zero crossings in a wave form. parameters to be set are
 * sampling rate (parent) and interval, i.e. the number of milliseconds in which
 * the zero crossings should be counted if a zero crossing happens exactly
 * between the borders of 2 adjacent intervals, the count is added to the second
 * interval needs a DataWriter it can push data to.
 * 
 * @author ura03640
 * 
 */
public class ZeroCrossingCounter extends AbstractAnalyzer {
	private static Logger LOG = Logger.getLogger(ZeroCrossingCounter.class);

	/**
	 * last sample from previous buffer, starts as null
	 */
	private Short lastSample = null;
	/**
	 * interval to write count. in milliseconds. If MONO, and samplingRate =
	 * 44100/second and interval is 20 then the count is done for 882 samples.
	 */
	private int interval = 0;

	/**
	 * we are initialized iff interval is set (and sample rate) and we've got a
	 * DataWriter. We then can calculate the samples/count number
	 */
	private boolean initialized = false;

	/**
	 * how many samples will contribute to one count
	 */
	private int samplesPerCount;

	/**
	 * count crossings (kind of accumulator) must live over 2 calls..
	 */
	private int cnt = 0;

	/**
	 * count the samplesPerCount
	 */
	private int sampleCount = 0;
	
	/**
	 * array to write to ZeroCountDataWriter..
	 */
	Integer[] data = new Integer[1];
	
	/**
	 * our data output
	 */
	ZeroCountDataWriter zcWriter; 


	public ZeroCrossingCounter() {
		super("ZeroCrossingCounter", DeviceType.PROCESSOR);
		zcWriter = new ZeroCountDataWriter();

	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public int getInterval() {
		return interval;
	}

	@Override
	public int getSamples(short[] buffer, int length) {
		if (!initialized) {
			initialize();
		}
		int ret = previous.getSamples(buffer, length);
		if (ret > 0) { // we got data from previous
			if (lastSample != null) {
				if ((lastSample.shortValue() ^ buffer[0]) < 0) {
					cnt++;
				}
			}
			for (int i = 1; i < ret; i++) {
				sampleCount++;
				if (sampleCount > samplesPerCount) {
					writeData(cnt); // write data for this interval
					cnt = 0; // reset accumulator for next interval
					sampleCount = 0;
				}
				if ((buffer[i] ^ buffer[i - 1]) < 0) {
					cnt++;
				}
			}
			// TODO how to find out that we have arrived at the end of data, and
			// have to write the (last part of the ) last count
		}
		return ret;
	}

	private void initialize() {
		if (interval == -1) {
			throw new IllegalStateException("interval not set. cannot run!");
		}
		samplesPerCount = (samplingRate * interval) / 1000; // TODO what to do,
															// iff this does not
															// naturally end up
															// as integer??
		try {
			zcWriter.writeHeader(new Object[0]);
		} catch (IOException ioe) {
			LOG.error("IOexception writing data " + ioe, ioe);
			throw new RuntimeException("IOexception writing data " + ioe);
		} 
		initialized = true;
	}

	private void writeData(int count) {
		data[0] = Integer.valueOf(count);
		try {
			zcWriter.addData(data);
		} catch (IOException e) {
			LOG.error("IOexception writing data " + e,e);
		}
	}

	@Override
	public void stop() throws Exception {
		zcWriter.end();
	}

	@Override
	public void SetFileName(String name)throws Exception {
		zcWriter.setFileName(name);
		
	}

	@Override
	public void SetFile(File file) throws Exception {
		zcWriter.setFile(file);
		
	}

}
