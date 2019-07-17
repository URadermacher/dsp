package eu.vdmr.math.vector;

import eu.vdmr.math.DoubleHelper;
import eu.vdmr.math.matrix.Matrix;

public class Vector {
    private double[] data;
    private int dim;

    private Vector(int dimension) {
        dim = dimension;
        data = new double[dimension];
    }

    public static Vector createVector(int dimension) {
        return new Vector(dimension);
    }

    public static Vector createVector(int dimension, double ... values) {
        if (values.length != dimension) {
            throw new IllegalArgumentException("Cannot accept " + values.length + " data for vector of size " + dimension);
        }
        Vector result = new Vector(dimension);
        for (int i = 0; i < dimension; i++) {
            result.data[i] = values[i];
        }

        return result;
    }

    public static Vector createVector(Vector template) {
        Vector v = createVector(template.dim);
        for (int i = 0; i < template.dim; i++) {
            v.data[i] = template.data[i];
        }
        return v;
    }

    public static Vector createVector(double[] values) {
        Vector v = createVector(values.length);
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
    public double[] getData() {
        return data;
    }

    @Override
    public  boolean equals(Object obj){
        if (! (obj instanceof  Vector)) {
            return false;
        }
        Vector other = (Vector)obj;
        if (this.dim != other.dim) {
            return false;
        }
        for (int i = 0; i < dim; i ++) {
            if (this.data[i] != other.data[i]) {
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
    public Vector add(Vector other) {
        if (this.dim != other.dim) {
            throw new IllegalArgumentException("Cannot add Vectors of different dimension "+this.dim+" v " + other.dim);
        }
        Vector res = Vector.createVector(this);
        for (int i = 0; i < dim; i++) {
            res.data[i] = this.data[i] + other.data[i];
        }
        return res;
    }

    /**
     * Scalar multiplication
     * @param scalar the scalar
     * @return a new Vector with the multiplied values
     */
    public Vector multiply(double scalar){
        Vector res = Vector.createVector(this);
        for (int i = 0; i < dim; i++) {
            DoubleHelper.adjust(res.data[i] = this.data[i] * scalar);
        }
        return res;
    }

    /**
     * check whether this is a linear combination of a1 and a2
     * we create the equation:
     * x1*a1 + x2*a2 = this
     * We make a Matrix with this and solve the matrix..
     *
     * @return true if this can be written as a linear combination of the vectors
     */
    public boolean isLinearCombinationOf(Vector a1, Vector a2) {
        Matrix matrix = Matrix.createMatrix(this.dim, 3);
        matrix.setCol(0, a1.getData());
        matrix.setCol(1, a2.getData());
        matrix.setCol(2, getData());
        matrix.echelon();
        return matrix.isConsistent().isEmpty();
    }

    public boolean isInSpan(Vector ... span) {
        Matrix matrix = Matrix.createMatrix(this.dim, (span.length+1));
        for (int i = 0; i < span.length; i++) {
            matrix.setCol(i, span[i].getData());
        }
        matrix.setCol(span.length, getData());
        matrix.echelon();
        return matrix.isConsistent().isEmpty();
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
