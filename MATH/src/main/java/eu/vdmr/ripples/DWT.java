package eu.vdmr.ripples;

public abstract class DWT {

    protected double[] padd(double[] in) {
        return new double[2];
    }

    public void applyThreshold(double[] in, double threshold) {
        for (int idx = 0; idx < in.length; idx++) {
            if (Math.abs(in[idx]) < threshold) {
                in[idx] = 0;
            }
        }
    }

}
