package eu.vdmr.dsp.component.filter.average;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.vdmr.dsp.component.DSPComponentImpl;

public class MovingAverageFilter extends DSPComponentImpl {
	private static Logger LOG = LogManager.getLogger(MovingAverageFilter.class);
	private short[] previousA = new short[DEFAULTBUFFERSIZE];
	private short[] previousB = new short[DEFAULTBUFFERSIZE];
	private int currentBuffer = 1;
	private int currentPos = 0;
	private boolean firstcall = true;
	private int firstavg;
	private int points; // number of samples to take average from

	@Override
	public int getSamples(short[] buffer, int length) {
		int read =0;
		if (firstcall) {
			firstcall = false;
			read = previous.getSamples(previousA, DEFAULTBUFFERSIZE);
			if (read == DEFAULTBUFFERSIZE){
				read = previous.getSamples(previousB,DEFAULTBUFFERSIZE);
			}
			// calculate first average
			firstavg = 0;
			for (int i = 0; i < points; i++){
				firstavg += previousA[i];
			}
			firstavg = firstavg/points;
			buffer[0] = (short)firstavg;
			currentPos = 1;
		}
		while (currentPos < length){
			//buffer[currentPos] = buffer[currentPos-1] + 
		}
		return 0;
	}

	
	public int getPoints(){
		return points;
	}
	
	public void setPoints(int arg){
		points = arg;
	}
}
