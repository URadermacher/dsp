package eu.vdmr.math.matrix;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

//@SuppressWarnings("unchecked")
public class Matrix {

    private static Logger LOG = LogManager.getLogger(Matrix.class);
    private static final double adjustment = 10000000.0;

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

    public void set(int row, int col, double value) {
        data[row][col] = value;
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
        logMatrix();
        // set all pivot cells to 1
        for (int rowidx = 0; rowidx< rows; rowidx++) {
            boolean rowHandled = false;
            for (int colidx = 0; colidx < cols && !rowHandled; colidx++) {
                if (data[rowidx][colidx] != 0) {
                    // pivot of row found
                    multiplyRow(getRow(rowidx), colidx, 1/data[rowidx][colidx]);
                    rowHandled = true;
                    logMatrix();
                }
            }
        }
        // now create zeroes above all pivots, starting with the last one
        for (int rowidx = rows - 1; rowidx > 0; rowidx-- ) {
            int colidx = getFirstNonZero(getRow(rowidx));
            if (colidx != -1) { // non zero column found
                nullifyToTop(rowidx, colidx);
                logMatrix();
            }
        }

        state = MatrixState.REDUCED_ECHELOC;
    }

    public int getFirstNonZero(double[] row) {
        int result = -1;
        for (int i = 0; i < cols; i++) {
            if (row[i] != 0) {
                return i;
            }
        }
        // all zeroes:
        return result;
    }

    /**
     * make sure that all entries [row][col] with row < idx parameter and col = parameter are zero.
     * use the [row][col] to determine actions
     *
     * @param idx      row from where above only zeros will be
     * @param pivotCol column where this happens. We assume that for the pivot row all values before the
     *                 pivot column are 0.0
     */

    public void nullifyToTop(int idx, int pivotCol) {
        if (data[idx][pivotCol] != 1.0) {
            throw new IllegalStateException("pivot cell must be 1.0, but data["+idx+"]["+pivotCol+"] is " + data[idx][pivotCol] );
        }
        for (int rowidx = idx-1; rowidx >= 0; rowidx--) {
            if (data[rowidx][pivotCol] != 0.0) {
                addRow(getRow(idx), getRow(rowidx), data[rowidx][pivotCol] * -1.0 , pivotCol);
            }
        }
    }

    public void multiplyRow(double[] row, int fromCol, double multiplier) {
        for (int colidx = fromCol; colidx < cols; colidx++) {
            row[colidx] = adjust(row[colidx] * multiplier);
        }
    }

    /**
     * make sure that all entries [row][col] with row > idx parameter and col = parameter are zero.
     * use the [row][col] to determine actions
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
            calcRow[i] = adjust(calcRow[i] + (pivotRow[i] * scaleFactor));
        }
    }

    private double adjust(double number) {
        // 0.20000000000018 should be 0.2, as 0.1999999999999993
//        // but 1.0 E^7 should *not* become 92233.72036854776
//        if (number % 1 ==0 ) {
//            return number;
//        }
        double nn = number * adjustment;
        double xx = Math.round(nn);
        double yy = xx / adjustment;
        return Math.round(number * adjustment) / adjustment;
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
     * @return an empty list if no inconsistent line is found, otherwise a list of row-indices
     * @throws IllegalStateException if matrix is not in an echelon form
     */
    public List<Integer> isConsistent() {
        List<Integer> result = new ArrayList<>();
        if (!MatrixState.ECHELON.isEqualTo(state)) {
            throw new IllegalStateException("consistency check only with matrices in echelon form");
        }
        for (int rowidx = 0; rowidx < rows; rowidx++) {
            if (isInconsistent(data[rowidx])) {
                result.add(rowidx);
            }
        }
        return result;
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

    private void logRow(int row) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cols; i++) {
            sb.append("\t").append("\t").append(data[row][i]);
        }
        LOG.info(sb.toString());
    }

    public boolean isInEchelonForm() {
        return MatrixState.ECHELON.isEqualTo(state);
    }

    /**
     * assume the matrix is a representation of a linear equation: try to solve it
     * @return an Object describing the solution
     */
    public LinearEquationSolution solve() {
        LinearEquationSolution result = new LinearEquationSolution();
            echelon();
            if (!isConsistent().isEmpty()) {
                result.setInconsistent(true);
                result.setInconsistenRows(isConsistent());
                return result;
            }
            reducedEchelon();
            findBasicAndFreeVariables(result);
            result.setSolved(true);
        return result;
    }

    /**
     * basic variables are variables corresponding to pivot columns.
     * Assumes the matrix to be in reduced echelon form
     * @param result the result object to record findings in
     */
    private void findBasicAndFreeVariables(LinearEquationSolution result) {
        for (int rowidx = 0; rowidx < rows; rowidx++) {
            boolean pivotFound = false;
            LinearEquationSolution.FixedSolution solution = result.new FixedSolution();
            for (int colidx = 0; colidx < cols - 1; colidx++) {
                if (data[rowidx][colidx] != 0.0) {
                    if (!pivotFound) {
                        solution.setVarNr(colidx + 1);
                        solution.setSolution(data[rowidx][cols - 1]);
                        pivotFound = true;
                    } else {
                        solution.addFreeVariable(colidx + 1);
                        solution.addFreeVariableValue(data[rowidx][colidx]);
                    }
                }
            }
            if (solution.getVarNr()!= -1 ) {
                result.addSolution(solution);
            }
        }
    }

    public String writeAsLinearEquation() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (int rowidx = 0; rowidx < rows; rowidx++) {
            getLinearEqationRow(sb, getRow(rowidx));
            sb.append("\n");
        }
        LOG.debug(sb.toString());
        return  sb.toString();
    }

    private void getLinearEqationRow( StringBuilder sb ,double[] row) {
        for (int colidx = 0; colidx < cols-1; colidx++) {
            if (row[colidx] == 0) {
                sb.append("\t\t");
            } else {
                sb.append(row[colidx]).append("x").append(colidx+1).append("\t");
            }
        }
        sb.append(" = ").append(row[cols-1]);
    }
}
