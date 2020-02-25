package eu.vdmr.math;

public class Complex {

    private final double real;
    private final double imag;

    public Complex(double real, double imag) {
        this.real = real;
        this.imag = imag;
    }

    public double getReal() {
        return real;
    }

    public double getImag() {
        return imag;
    }

    // polar
    public double getMagnitude() {
        return Math.sqrt(real * real + imag * imag);
    }

    public boolean equals(Object other) {
        if (! (other instanceof Complex)) {
            return false;
        }
        return real == ((Complex) other).real && imag == ((Complex) other).imag;
    }

    public Complex add(Complex summand) {
        return new Complex(real + summand.real, imag + summand.imag);
    }

    public Complex multiply(Complex factor) {
        return new Complex(real * factor.real - imag * factor.imag, real * factor.imag + imag * factor.real);
    }

    public Complex divide(Complex divisor) {
        return null;
    }
}
