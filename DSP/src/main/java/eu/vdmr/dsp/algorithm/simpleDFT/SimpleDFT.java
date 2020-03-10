package eu.vdmr.dsp.algorithm.simpleDFT;

import eu.vdmr.math.Numbers;
import eu.vdmr.math.matrix.ComplexMatrix;
import org.apache.commons.math3.complex.Complex;

import java.util.Map;
import java.util.TreeMap;

public class SimpleDFT {


    private static double TRESHOLD = 0.0000000001d;

    private static enum Quadrant{
        Q_I,  // + +
        Q_2,  // - +
        Q_3,  // - -
        Q_4   // + -
    }

    private int trigCount = 0;

    /**
     * If not optimized a Matrix of 1024 will require 2056192 evaluations of sin(), cos(), for 8192 it's 134184962
     */
    private boolean optimizeTrig = false;

    private Map<Double, Double> cossin = new TreeMap<>();

    public int getTrigCount() {
        return trigCount;
    }
    public void setOptimizeTrig(boolean optimize) {
        optimizeTrig = optimize;
    }

    public Complex[] doDFTTrigonometric(Complex[] signal) {
        Complex[] result = new Complex[signal.length];
        for (int i = 0; i < signal.length; i++) {
            result[i] = calculateXk(signal, i);
        }
        return result;
    }



    private Complex calculateXk(Complex[] signal, int k) {
//        System.out.println("k = " + k);
        double len = signal.length * 1.0d;
        double real = 0.0d;
        double imaginary = 0.0d;
        for (int i = 0; i < signal.length; i++) {
            double realAdd = roundIt(signal[i].getReal() * Math.cos((2 * Math.PI * k * i) / len));
            double imaginaryAdd = roundIt(signal[i].getImaginary() * Math.sin((2 * Math.PI * k * i) / len));
//            System.out.println("-> " + realAdd + " , " + imaginaryAdd);
            real += realAdd;
            imaginary -= imaginaryAdd;
//            System.out.println(": " + real + " , " + imaginary);
        }
        // adding 1.0000000000000000 to  0.7071067811865475 ( ~ cos (1/4 pi)) results in  0.7071067811865476
        // then the total result becomes like -3.3306690738754696E-16 + i * 2.220446049250313E-16
        // so we need another rounding...
        return new Complex(roundIt(real), roundIt(imaginary));
    }



    public Complex[] doDFTWithMatrix(Complex[] signal) {
        int dimension = signal.length;
        ComplexMatrix bases = getBaseMatrix(dimension);
        return null;
    }

    public ComplexMatrix getBaseMatrix(int dimension) {
        ComplexMatrix bases = ComplexMatrix.createMatrix(dimension, dimension);
        double baseAngleDegree = Numbers.adjustDouble(360d / dimension);
        int i = 0;
        for (int row = 0; row < dimension; row++) {
            for (int column = 0; column < dimension; column++) {
                Complex entry = calculateValue(baseAngleDegree * column * row);
                bases.set(row, column, entry);
            }
        }
        return  bases;
    }

    public Complex calculateValue(double angleDegree) {
        double myAngle = angleDegree % 360d;
        if (myAngle == 0) {
            return Complex.ONE;
        }
        if (myAngle == 90 || myAngle == -270) {
            return new Complex(0,1);
        }
        if (myAngle == 180 || myAngle == -180) {
            return new Complex(-1,0);
        }
        if (myAngle == -90 || myAngle == 270) {
            return new Complex(0,-1);
        }
        double cosi = 0;
        double sini = 0;
        if (optimizeTrig) {
            Quadrant quadrant = determineQuadrant(myAngle);
            double baseDegree = getBaseDegree(angleDegree, quadrant);
            Double kept = cossin.get(baseDegree);
            if (kept == null) {


            }
        } else {
            cosi = Numbers.adjustDouble(Math.cos(Math.toRadians(angleDegree)));
            sini = Numbers.adjustDouble(Math.sin(Math.toRadians(angleDegree)));
        }
        trigCount +=2;
        return new Complex(cosi, sini);
    }

    /**
     * For cases the sin) and cos() are the same for several degrees (just the sign changes)
     * e.g. sin() : : cos() of degrees related to 30:
     * <ol>
     *     <li>0 deg = 0 : +1</li>
     *     <li>30 deg = +1/2 : +sqrt(3)/2</li>
     *     <li>60deg = +sqrt(3)/2 : +1/2</li>
     *     <li>90deg = +1 : 0</li>
     *     <li>120deg = +sqrt(3)/2 : -1/2</li>
     *     <li>150 deg = +1/2 : -sqrt(3)/2</li>
     *     <li>90deg = 0 : -1</li>
     *     <li>210 deg = -1/2 : -sqrt(3)/2</li>
     *     <li>240deg = -sqrt(3)/2 : -1/2</li>
     *     <li>90deg = -1 : 0</li>
     *     <li>300deg = -sqrt(3)/2 : +1/2</li>
     *     <li>330 deg = -1/2 : +sqrt(3)/2</li>
     * </ol>
     * using this could be 1/8 of calculations needed
     *
     * @param angleDegree degree where we search the base degree for (e.g. 330 -> 30)
     * @param quadrant of the angle
     * @return the base degree (e.g. 30)
     */
    private double getBaseDegree(double angleDegree, Quadrant quadrant) {
        return 0d;
    }

    private Quadrant determineQuadrant(double myAngle) {
        double check = Math.abs(myAngle);
        if (myAngle < 0 )  // LATER OMFDRAAIEN
        if (myAngle > 0 && myAngle < 90) {
            return Quadrant.Q_I;
        }
        if (myAngle > 0 && myAngle < 90) {
            return Quadrant.Q_I;
        }
        return Quadrant.Q_I;
    }

    private double roundIt(double in) {
        if (Math.abs(in) < TRESHOLD) {
            return 0.0d;
        }
        return in;
    }
}
