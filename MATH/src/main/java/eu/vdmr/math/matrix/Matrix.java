package eu.vdmr.math.matrix;

import java.lang.reflect.Array;

//@SuppressWarnings("unchecked")
public class Matrix {

    private double[][] data;
    private Class myType;
    private int rows;
    private int cols;

    private Matrix(double[][] data, int rows, int cols) {
        this.data = data;
        this.rows = rows;
        this.cols = cols;
    }

    public static Matrix createMatrix(int rows, int cols) {
        return new Matrix(new double[rows][cols], rows, cols);
    }

    public void setData(double... dd) {
        int parmidx = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = dd[parmidx++];
            }
        }
    }


    public double get(int row, int col) {
        return data[row][col];
    }

    public void switchRow(int row1, int row2) {
        if (row1 >= rows || row2 >= rows) {
            throw new IllegalArgumentException("there is no row " + row1 + " or " + row2 + ", actual rows = " + rows);
        }
    }

    public void echelon() {
        for (int colidx = 0; colidx < cols - 1; colidx++) {
            int pivotrow = findPivotRowForCol(colidx);

        }
    }

    public int findPivotRowForCol(int colidx) {
        int candidate = -1;
        for (int rowidx = 0; rowidx < rows; rowidx++) {
            if (data[rowidx][colidx] != 0) {
                if (data[rowidx][colidx] == 1) {
                    // best pivot is a 1
                    return rowidx;
                }  else {
                    candidate = rowidx;
                }
            }
        }
        return candidate;
    }

}
