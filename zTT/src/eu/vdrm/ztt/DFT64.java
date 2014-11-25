package eu.vdrm.ztt;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.commons.math3.complex.Complex;

/**
 * Calculate DTF in space 64.
 * set source to define intput function
 * @author ura03640
 *
 */
public class DFT64 {
	
	int x = 3;
	int y = 10;
	int z = 13;
	
	int source = 5;
	/**
	 * 1 = delta
	 * 2 = step
	 * 3 = XYcos with s[n] = x + cos( ((2*pi/y)) * n)  
	 * 4 = XYcosPhase with s[n] = x + cos( (((2*pi/y)) * n) + pi/z)  
	 * 5 = partstep(z)  i.e. x[n] = 1 for 0 <=x < z and else 0
	 */

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DFT64 dft = new DFT64();
		try {
			double[] in = dft.getIn(args);
			Complex[] out = dft.doit(in);
			dft.write(out);
			System.out.println("Done...");
		} catch (Exception e){
			System.err.println("Oeps, exeception " + e);
			e.printStackTrace(System.err);
		}
	}
	
	private double[] getIn(String[] args){
		if (source == 1){
			return DFTUtils.getDelta(64);
		} else if (source == 2){
			return DFTUtils.getStep(64);
		} else if (source == 3){
			return DFTUtils.getXYCos(64, x, y);
		} else if (source == 4){
			return DFTUtils.getXYCosPhase(64, x, y, z);
		} else if (source == 5){
			return DFTUtils.getPartStep(64, z);
		} else {
			throw new IllegalArgumentException("source " + source + " unknown");
		}
	}
	
	private Complex[] doit(double[] in){
		Complex[] res = new Complex[in.length];
		for (int k_idx = 0; k_idx < 64; k_idx ++){
			Complex kVal = new Complex(0.0,0.0);
			for (int n_idx = 0; n_idx < 64; n_idx++){
				double real = in[n_idx] * Math.cos((Math.PI * n_idx * k_idx) / 32 );
				double imag = in[n_idx] * Math.sin((Math.PI * n_idx * k_idx) / 32 );
				Complex val = new Complex(real,imag);
				kVal = kVal.add(val);
			}
			res[k_idx] = kVal;
		}
		return res;
	}
	
	private void write(Complex[] out) throws IOException{
		StringBuilder real = new StringBuilder();
		StringBuilder imag = new StringBuilder();
		for (int n = 0; n < 64; n++){
			real.append(out[n].getReal() + "\n");
			imag.append(out[n].getImaginary() + "\n");
		}
		String realString = real.toString();
		String imagString = imag.toString();
		BufferedWriter realWriter = new BufferedWriter(new FileWriter("D:/tmp/cos.txt"));
		realWriter.write(realString);
		realWriter.flush();
		realWriter.close();
		BufferedWriter imagWriter = new BufferedWriter(new FileWriter("D:/tmp/sin.txt"));
		imagWriter.write(imagString);
		imagWriter.flush();
		imagWriter.close();
	}

}
