package eu.vdmr.math.matrix;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//@SuppressWarnings("unchecked")
public class Matrix {

    private static Logger LOG = LogManager.getLogger(Matrix.class);


    private final double[][] data;
    private final int rows;
    private final int cols;

    private MatrixState state;
    private boolean dataSet;

    private Matrix(double[][] data, int rows, int cols) {
        this.data = data;
        this.rows = rows;
        this.cols = cols;
        state = MatrixState.NORMAL;
        dataSet = false;
    }

    public static Matrix createMatrix(int rows, int cols) {
        return new Matrix(new double[rows][cols], rows, cols);
    }

    public void setData(double... dd) {
        if (dataSet) {
            throw new IllegalStateException("setData cannot be called twice!");
        }
        int parmidx = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = dd[parmidx++];
            }
        }
        dataSet = true;
    }

    public double[] getRow(int row) {
        if (row >= rows) {
            throw new IllegalArgumentException("there is no row " + row + ", actual rows = " + rows);
        }
        return data[row];
    }

    public double get(int row, int col) {
        return data[row][col];
    }

    public void echelon() {
        // we start with [0][0]
        logMatrix();
        int currCol = 0;
        for (int idx = 0; idx < rows - 1; idx++) {
            boolean found = false;
            while (!found && currCol < cols) {
                int pivotRow = findPivot(idx, currCol);
                if (pivotRow != -1) { // pivot row found, pivot = data[pivotRow][currCol]
                    found = true;
                    if (pivotRow != idx) {
                        switchRows(idx, pivotRow, false);
                    }
                    logMatrix();
                    nullifyFromRow(idx, currCol);
                    logMatrix();
                    currCol++;

                } else {
                    currCol++;
                }
            }
            if (!found) {
                logMatrix();
                if (LOG.isDebugEnabled()) {
                    LOG.debug("no pivot found for col " + currCol + " starting row " + idx);
                }
            }
        }
        state = MatrixState.ECHELON;
        logMatrix();
    }

    public void reducedEchelon() {
        if (!MatrixState.ECHELON.isEqualTo(state)) {
            throw new IllegalStateException("reduced Echelon form only possible for matrices in echelon form");
        }

        state = MatrixState.REDUCED_ECHELOC;
    }

    /**
     * make sure that all entries [row][col] with row > row parameter and col = parameter are zero.
     * use the [row][col] to determine actons
     *
     * @param idx      row from where below only zeros will be
     * @param pivotCol column where this happens
     */
    public void nullifyFromRow(int idx, int pivotCol) {
        double pivotValue = data[idx][pivotCol];
        for (int calcRow = idx + 1; calcRow < rows; calcRow++) {
            double toBeZeroed = data[calcRow][pivotCol];
            if (toBeZeroed == 0) { // nothing to do here..
                continue;
            }
            double scaleFactor = -1 * toBeZeroed / pivotValue;
            addRow(data[idx], data[calcRow], scaleFactor, pivotCol);
        }


    }

    /**
     * add row pivotRow scaled by addFactor to calcRow
     *
     * @param pivotRow    row to be scaled and added
     * @param calcRow     row added to
     * @param scaleFactor scale factor
     */
    public void addRow(double[] pivotRow, double[] calcRow, double scaleFactor, int colToStart) {
        // columns < colToStart are all zero when making echelon form, so we kan skip those..
        for (int i = colToStart; i < cols; i++) {
            calcRow[i] = calcRow[i] + (pivotRow[i] * scaleFactor);
            // 0.20000000000018 should be 0.2, as 0.1999999999999993
            calcRow[i] = Math.round(calcRow[i] * 100000000000000.0) / 100000000000000.0;
        }
    }

    public void switchRows(int targetRow, int pivotrow) {
        switchRows(targetRow, pivotrow, true);
    }

    private void switchRows(int targetRow, int pivotrow, boolean check) {
        if (check) {
            if (targetRow >= rows || pivotrow >= rows) {
                throw new IllegalArgumentException("there is no row " + targetRow + " or " + pivotrow + ", actual rows = " + rows);
            }
        }
        double[] tmp = new double[cols];
        System.arraycopy(data[pivotrow], 0, tmp, 0, cols);
        System.arraycopy(data[targetRow], 0, data[pivotrow], 0, cols);
        System.arraycopy(tmp, 0, data[targetRow], 0, cols);
    }

    /**
     * using partial pivoting (i.e. looking for the (absolute) largest value)
     * (should reduce roundoff errors (David C. Lay "Linear Algebra" , 2019 p. 20)
     *
     * @param startRow where to start the search
     * @param colidx   column to search
     * @return the row index of the pivot (or -1 if only zeroes were found)
     */
    public int findPivot(int startRow, int colidx) {
        double candidate = 0;
        int candidateRow = -1;
        for (int rowidx = startRow; rowidx < rows; rowidx++) {
            if (data[rowidx][colidx] != 0) {
                if (Math.abs(data[rowidx][colidx]) > candidate) {
                    candidate = data[rowidx][colidx];
                    candidateRow = rowidx;
                }
            }
        }
        return candidateRow;
    }

    /**
     * Checks that no row exists where the last column is the only non-zero column
     * (which is the same as a row in a linear system with
     * 0x1 + 0x2 + ... + 0xn = 1  which no xn's can solve..)
     *
     * @return true if no inconsistent line is found
     * @throws IllegalStateException if matrix is not in an echelon form
     */
    public boolean isConsistent() {
        if (!MatrixState.ECHELON.isEqualTo(state)) {
            throw new IllegalStateException("consistency check only with matrices in echelon form");
        }
        for (int rowidx = 0; rowidx < rows; rowidx++) {
            if (isInconsistent(data[rowidx])) {
                return false;
            }
        }
        return true;
    }

    /**
     * check for one row whether it is inconsistent
     */
    public boolean isInconsistent(double[] row) {
        if (row[cols - 1] != 0) {
            for (int colidx = 0; colidx < cols - 1; colidx++) {
                if (row[colidx] != 0) {
                    return false;
                }
            }
            return true;
        }
        // if solution column = 0 there may a solution..
        return false;
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

    public void logRow(int row) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cols; i++) {
            sb.append("\t").append("\t").append(data[row][i]);
        }
        LOG.info(sb.toString());
    }

    public boolean isInEchelonForm() {
        return MatrixState.ECHELON.isEqualTo(state);
    }

}
