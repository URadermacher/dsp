package eu.vdmr.math.vector;

import eu.vdmr.math.Numbers;
import org.apache.commons.math3.complex.Complex;

public class ComplexVector {
    private Complex[] data;
    private int dim;

    private ComplexVector(int dimension) {
        dim = dimension;
        data = new Complex[dimension];
    }

    public static ComplexVector createVector(int dimension) {
        return new ComplexVector(dimension);
    }

    public static ComplexVector createVector(int dimension, Complex ... values) {
        if (values.length != dimension) {
            throw new IllegalArgumentException("Cannot accept " + values.length + " data for vector of size " + dimension);
        }
        ComplexVector result = new ComplexVector(dimension);
        for (int i = 0; i < dimension; i++) {
            result.data[i] = values[i];
        }

        return result;
    }

    public static ComplexVector createVector(ComplexVector template) {
        ComplexVector v = createVector(template.dim);
        for (int i = 0; i < template.dim; i++) {
            v.data[i] = template.data[i];
        }
        return v;
    }

    public static ComplexVector createVector(Complex[] values) {
        ComplexVector v = createVector(values.length);
        System.arraycopy(values, 0, v.data, 0, values.length);
        return v;
    }

    public int getDimension() {
        return dim;
    }

    /**
     * get the data array. It is not the intention that these data are changed, but it's your responsibility
     *
     * @return the vector's data
     */
    public Complex[] getData() {
        return data;
    }

    @Override
    public  boolean equals(Object obj){
        if (! (obj instanceof ComplexVector)) {
            return false;
        }
        ComplexVector other = (ComplexVector)obj;
        if (this.dim != other.dim) {
            return false;
        }
        for (int i = 0; i < dim; i ++) {
            if (! this.data[i].equals(other.data[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param other the Vector to be added
     * @return a *new* Vector as the sum of this + other
     */
    public ComplexVector add(ComplexVector other) {
        if (this.dim != other.dim) {
            throw new IllegalArgumentException("Cannot add Vectors of different dimension "+this.dim+" v " + other.dim);
        }
        ComplexVector res = ComplexVector.createVector(this);
        for (int i = 0; i < dim; i++) {
            res.data[i] = this.data[i].add(other.data[i]);
        }
        return res;
    }

    /**
     * Dot Product
     * @param scalar the scalar
     * @return a new Vector with the multiplied values
     */
    public ComplexVector dotProduct(Complex scalar){
        ComplexVector res = ComplexVector.createVector(this);
        for (int i = 0; i < dim; i++) {
            // TODO afronden!
            res.data[i] = this.data[i].multiply(scalar);
        }
        return res;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int idx = 0; idx < dim; idx ++) {
            sb.append(data[idx]);
            if (idx < dim-1) {
                sb.append(", ");
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
