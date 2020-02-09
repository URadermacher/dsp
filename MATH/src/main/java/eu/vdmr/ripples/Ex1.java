package eu.vdmr.ripples;

import eu.vdmr.math.Numbers;


public class Ex1 extends DWT {

    public double[] calculateMean(double[] in) {
        return calculateMean(in, false);
    }

    public double[] calculateMean(double[] in, boolean padding) {
        if (Numbers.expOf2(in.length) == -1) {
            if (!padding) {
                throw new IllegalArgumentException("input array length must be exp of 2");
            }
            in = padd(in);
        }
        int div = in.length / 2;

        while (div >= 1) {
            in = calculateRow(in, div);
            div /= 2;
        }
        return in;
    }



    private double[] calculateRow(double[] in, int div) {
        double[] out = new double[in.length];
        // copy data wich will not be touched..
        for (int idx = div; idx < out.length; idx++) {
            out[idx] = in[idx];
        }
        // calculate
        for (int idx = 0; idx < div; idx++) {
            out[idx] = (in[idx * 2] + in[idx * 2 + 1]) / 2;
            out[div + idx] = in[idx * 2] - out[idx];
        }
        return out;
    }

    public double[] restore(double[] in) {
        int stepsToDo = in.length / 2;
        for (int rowidx = 1; rowidx <= stepsToDo; rowidx*=2) {
            calculateRestore(in, rowidx);
        }
        return in;
    }

    private void calculateRestore(double[] in, int row) {
        // will be overwritten, thus keep copy
        double[] org = new double[in.length];
        System.arraycopy(in, 0, org, 0, in.length);
        for (int idx = 0; idx < row ; idx++) {
            double diff = org[idx+row];
            in[idx*2] = org[idx] + diff;
            in[idx*2 + 1] = org[idx] - diff;
        }
    }

}