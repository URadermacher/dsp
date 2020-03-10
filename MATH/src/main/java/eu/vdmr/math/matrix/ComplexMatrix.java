package eu.vdmr.math.matrix;

import eu.vdmr.math.vector.ComplexVector;
import org.apache.commons.math3.complex.Complex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//@SuppressWarnings("unchecked")
public class ComplexMatrix {

    private static Logger LOG = LogManager.getLogger(ComplexMatrix.class);

    private final Complex[][] data;
    private final int rows;
    private final int cols;


    private ComplexMatrix(Complex[][] data, int rows, int cols) {
        this.data = data;
        this.rows = rows;
        this.cols = cols;
    }

    public static ComplexMatrix createMatrix(int rows, int cols) {
        int a = 9;
        Complex[][] data =   new Complex[rows][cols];
        return new ComplexMatrix(data, rows, cols);
    }

    public void setData(Complex... dd) {
        int parmidx = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = dd[parmidx++];
            }
        }
    }

    public void setCol(int colIdx, Complex[] values) {
        for (int i = 0; i < rows; i++) {
            data[i][colIdx] = values[i];
        }
    }

    public void set(int row, int col, Complex value) {
        data[row][col] = value;
    }

    public Complex[] getRow(int row) {
        if (row >= rows) {
            throw new IllegalArgumentException("there is no row " + row + ", actual rows = " + rows);
        }
        return data[row];
    }

    public Complex get(int row, int col) {
        return data[row][col];
    }


    public void logMatrix() {
        if (LOG.isInfoEnabled()) {
            LOG.info("-------------------------------------------");
            for (int i = 0; i < rows; i++) {
                logRow(i);
            }
            LOG.info("-------------------------------------------\n");
        }
    }

    private void logRow(int row) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cols; i++) {
            sb.append("\t").append("\t").append(data[row][i]);
        }
        LOG.info(sb.toString());
    }


    public ComplexVector vectorProduct(ComplexVector x) {
        if (cols != x.getDimension()) {
            throw new IllegalArgumentException("Vector must have same dimension as matrix colums!");
        }
        ComplexVector sum = ComplexVector.createVector(rows);
        Complex[] xArr = x.getData();
        for (int colidx = 0; colidx < cols; colidx++) {
            sum = sum.add(getColVector(colidx).dotProduct(xArr[colidx]));
        }
        return sum;
    }

    private ComplexVector getColVector(int colIdx) {
        return ComplexVector.createVector(getCol(colIdx));
    }

    public Complex[] getCol(int colIdx) {
        Complex[] res = new Complex[rows];
        for (int i = 0; i < rows; i++) {
            res[i] = data[i][colIdx];
        }
        return res;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (!(otherObject instanceof ComplexMatrix)) {
            return false;
        }
        ComplexMatrix other = (ComplexMatrix) otherObject;
        // no IndexOutOfBound, please..
        if (cols != other.cols || rows != other.rows) {
            return false;
        }
        for (int rowidx = 0; rowidx < rows; rowidx++) {
            for (int colidx = 0; colidx < cols; colidx++) {
                if (!data[rowidx][colidx].equals(other.data[rowidx][colidx])) {
                    return false;
                }
            }
        }
        return true;
    }
}
