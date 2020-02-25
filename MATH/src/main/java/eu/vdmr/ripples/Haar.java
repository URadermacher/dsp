package eu.vdmr.ripples;

import eu.vdmr.math.Numbers;
import eu.vdmr.math.matrix.Matrix;
import eu.vdmr.math.vector.Vector;

import java.util.Arrays;

public class Haar extends DWT {

    /**
     * transform a signal with the Haar transform steps times and return all the results
     *
     * @param in    the signal
     * @param steps number of transform steps to do
     * @return all results (i.e. one array for each step s returned)
     */
    public double[][] multiHaarTransform(double[] in, int steps) {
        double[][] result = new double[steps][in.length];
        if (Numbers.expOf2(in.length) < 0) {
            throw new IllegalArgumentException("input array length must be exp of 2");
        }
        if (Math.pow(2, steps) > in.length) {
            throw new IllegalArgumentException("signal to short for number of steps");
        }
        double[] todo = Arrays.copyOf(in, in.length);
        int row = 0;
        int stepLen = in.length;
        for (int i = 0; i < steps; i++) {
            result[row] = dwtHaar(todo);
            stepLen /= 2;
            todo = Arrays.copyOf(result[row], stepLen);
            row++;
        }
        return result;
    }

    /**
     * Haar transform in place
     * we want the result as in example so we have to rearrange:
     * after one transform we have the diffs directly side by side
     *  so [56d, 40d, 8d, 24d, 48d, 48d, 40d, 16d] becomes
     *  [48, 8, 16, -8, 48, 0, 28, 12]  but we want
     *  [48, 16, 48, 28, 8. -8, 0, 12]
     * @param in signal to be transformed
     * @param steps number of steps to do
     */
    public void haarInPlaceTransform(double[] in, int steps) {
        int length = in.length * 2;
        double org;
        double moving;
        for (int step = 0; step < steps; step++) {
            length /= 2;
            for (int idx = 0; idx < length; idx +=2) {
                org = in[idx];
                in[idx] = (in[idx] + in[idx+1])/2;
                in[idx+1] = org - in[idx];
            }
            // rearrange (i.e. move the diffs to the right)
            for (int idx = 1; idx < length/2; idx++) {
                moving = in[ idx * 2];
                // move diffs
                for (int i =  idx * 2; i > idx; i--) {
                    in[i] = in[i-1];
                }
                in[idx] = moving;
            }
        }
    }

    /**
     * transform a signal with Haar transform as many steps as possible (i.e. 2^x times. i.e. 3 steps with signal
     * length 8)
     *
     * @param in the signal
     * @return the last row..
     */
    public double[] haarTransform(double[] in) {
        if (Numbers.expOf2(in.length) == -1) {
            throw new IllegalArgumentException("input array length must be exp of 2");
        }
        double[] out = Arrays.copyOf(in, in.length);
        for (int i = in.length; i > 1; i /= 2) {
            double[] todo = Arrays.copyOf(out, i);
            double[] transformed = dwtHaar(todo);
//            for (int j = 0; j < i; j++) {
//                out[j] = transformed[j];
//            }
            System.arraycopy(transformed, 0, out, 0, i);
        }
        return out;
    }

    /**
     * single row transform.
     * Returns a new array with same length as input with the first half values the means of 2 consecutive
     * entries en the second half the difference between the second value and the mean
     * e.g.:
     * in: 10 20
     * out : 15 -5
     *
     * @param in signal to be transformed
     * @return the with one prediction/update step transformed signal.
     */
    private double[] dwtHaar(double[] in) {
        double[] out = new double[in.length];
        for (int i = 0; i < in.length / 2; i++) {
            out[i] = (in[2 * i] + in[2 * i + 1]) / 2;
            out[in.length / 2 + i] = in[2 * i] - out[i];
        }
        return out;
    }

    public double[] haarInverse(double[] in) {
        if (Numbers.expOf2(in.length) == -1) {
            throw new IllegalArgumentException("input array length must be exp of 2");
        }
        double[] out = Arrays.copyOf(in, in.length);

        for (int i = 1; i < in.length; i *= 2) {
            inverseHaar(out, Arrays.copyOfRange(out, 0, i));
        }
        return out;
    }


    /**
     * single row inverse
     * e.g.:
     * in : 15 -5
     * out: 10 20
     *
     * @param in  row to be transformed (in place)
     * @param org copy of this row (as data in the in array are overwritten ..
     */
    private void inverseHaar(double[] in, double[] org) {
        int diffIdx = org.length;
        for (int i = 0; i < org.length; i++) {
            double diff = in[diffIdx++];
            in[2 * i] = org[i] + diff;
            in[2 * i + 1] = org[i] - diff;
        }
    }

    /**
     * transform by matrix multiplication,
     * only implemented for length 8 signals
     *
     * @param in the signal
     * @return the transformed
     */
    public double[] matrixTransform(double[] in) {
        Matrix analysisMatrix = get_W3_AnalysisMatrix();
        Vector basisE0 = Vector.createVector(8, in);
        Vector result = analysisMatrix.vectorProduct(basisE0);
        return result.getData();
    }

    /**
     * reverse transform by matrix multiplication,
     * only implemented for length 8 signals
     *
     * @param in the transformed signal
     * @return the original signal
     */
    public double[] matrixReverse(double[] in) {
        Matrix syntesisMatrix = get_W3_SynthesissMatrix();
        Vector basisE0 = Vector.createVector(8, in);
        Vector result = syntesisMatrix.vectorProduct(basisE0);
        return result.getData();
    }

    /**
     * generate an analysis matrix for a signal of given length
     * (for length 8 there is a fixed (hardcoded) version
     *
     * @param len length of the signal (should be a power of 2)
     * @return the generated matrix
     */
    public Matrix generateAnalysisMatrix(int len) {
        Matrix analysisMatrix = Matrix.createMatrix(len, len);
        for (int i = 0; i < len; i++) {
            // defaults to 0d;
            double[] data = new double[len];
            data[i] = 1d;
            double[] transformed = haarTransform(data);
            analysisMatrix.setCol(i, transformed);
        }
        return analysisMatrix;
    }


    /**
     * generate an synthesis matrix for a signal of given length
     * (for length 8 there is a fixed (hardcoded) version
     *
     * @param len length of the signal (should be a power of 2)
     * @return the generated matrix
     */
    public Matrix generateSynthesisMatrix(int len) {
        Matrix synthesisMatrix = Matrix.createMatrix(len, len);
        for (int i = 0; i < len; i++) {
            // defaults to 0d;
            double[] data = new double[len];
            data[i] = 1d;
            double[] transformed = haarInverse(data);
            synthesisMatrix.setCol(i, transformed);
        }
        return synthesisMatrix;

    }
    /**
     * @return The W_3 Analysis Matrix
     */
    public Matrix get_W3_AnalysisMatrix() {
        Matrix analysisMatrix = Matrix.createMatrix(8, 8);
        analysisMatrix.setData(
                0.125d, 0.125d, 0.125d, 0.125d, 0.125d, 0.125d, 0.125d, 0.125d,
                0.125d, 0.125d, 0.125d, 0.125d, -0.125d, -0.125d, -0.125d, -0.125d,
                0.25d, 0.25d, -0.25d, -0.25d, 0d, 0d, 0d, 0d,
                0d, 0d, 0d, 0d, 0.25d, 0.25d, -0.25d, -0.25d,
                0.5d, -0.5d, 0d, 0d, 0d, 0d, 0d, 0d,
                0d, 0d, 0.5d, -0.5d, 0d, 0d, 0, 0d,
                0d, 0d, 0d, 0d, 0.5d, -0.5d, 0d, 0d,
                0d, 0d, 0d, 0d, 0d, 0d, 0.5d, -0.5d
        );
        return analysisMatrix;
    }

    /**
     * @return The W_3 Synthesis Matrix
     */
    public Matrix get_W3_SynthesissMatrix() {
        Matrix synthesisMatrix = Matrix.createMatrix(8, 8);
        synthesisMatrix.setData(
                1d, 1d, 1d, 0d, 1d, 0d, 0d, 0d,
                1d, 1d, 1d, 0d, -1d, 0d, 0d, 0d,
                1d, 1d, -1d, 0d, 0d, 1d, 0d, 0d,
                1d, 1d, -1d, 0d, 0d, -1d, 0d, 0d,
                1d, -1d, 0d, 1d, 0d, 0d, 1d, 0d,
                1d, -1d, 0d, 1d, 0d, 0d, -1d, 0d,
                1d, -1d, 0d, -1d, 0d, 0d, 0d, 1d,
                1d, -1d, 0d, -1d, 0d, 0d, 0d, -1d
        );
        return synthesisMatrix;
    }

}
