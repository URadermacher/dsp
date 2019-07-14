package eu.vdmr.math.tesrData;

import eu.vdmr.math.matrix.Matrix;

import static eu.vdmr.math.matrix.Matrix.createMatrix;

public class TestData {

    public static Matrix getEmpty() {
        return createMatrix(3, 4);
    }

    /**
     * <pre>
     *
     *     1  -2   1   0
     *     0   2  -8   8
     *    -4   5   9  -9
     *
     * </pre>
     *
     * @return this matrix
     */
    public static Matrix getEx1() {
        Matrix matrix = createMatrix(3, 4);
        matrix.setData(1, -2, 1, 0, 0, 2, -8, 8, -4, 5, 9, -9);
        return matrix;
    }

    /**
     * <pre>
     *
     *     0    1  -4   8
     *     2   -3   2   1
     *     5   -8   7   1
     *
     * </pre>
     * @return this matrix
     */
    public static Matrix getEx2() {
        Matrix matrix = createMatrix(3, 4);
        matrix.setData(0, 1, -4, 8, 2, -3, 2, 1, 5, -8, 7, 1);
        return matrix;
    }

    /**
     *
     * <pre>
     *     1   2   3   4   5   6
     *     0   0   1   2   3   4
     *     0   0   0   0   4   5
     *     0   0   0   0   5   6
     * </pre>
     * @return this matrix
     */
    public static Matrix getEx3() {
        Matrix matrix = createMatrix(4, 6);
        matrix.setData(1, 2, 3, 4, 5, 6, 0, 0, 1, 2, 3, 4, 0, 0, 0, 0, 4, 5, 0, 0, 0, 0, 5, 6);
        return matrix;
    }

    /**
     * <pre>
     *
     *    1  2  3  4
     *    0  0  0  0
     *    0  0  0  0
     *    0  0  0  0
     *
     * </pre>
     * @return this matrix
     */
    public static Matrix getEx4() {
        Matrix matrix = createMatrix(4, 4);
        matrix.setData(1, 2, 3, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        return matrix;
    }
}
