package eu.vdmr.dsp.algorithm.simpleDFT;

import org.apache.commons.math3.complex.Complex;

public class SimpleDFTTestData {


    public static Complex[] makeAlternateRealSignal(int count) {
        Complex[] result = new Complex[count];
        for (int i = 0; i < count-1; i += 2) {
            result[i] = new Complex(1.0d, 0.0d);
            result[i+1] = new Complex(0.0d, 0.0d);
        }
        return result;
    }

    public static Complex[] makeConstantRealSignal(int count) {
        Complex[] result = new Complex[count];
        for (int i = 0; i < count; i++) {
            result[i] = new Complex(1.0d, 0.0d);
        }
        return result;
    }
}
