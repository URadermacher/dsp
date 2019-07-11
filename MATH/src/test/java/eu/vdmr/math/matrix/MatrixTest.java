package eu.vdmr.math.matrix;

import eu.vdmr.math.tesrData.TestData;
import org.junit.jupiter.api.Test;

import static eu.vdmr.math.matrix.Matrix.createMatrix;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowable;

class MatrixTest {

    private double[] orgEx0 = new double[]{1, -2, 1, 0};
    private double[] orgEx1 = new double[]{0, 2, -8, 8};
    private double[] orgEx2 = new double[]{-4, 5, 9, -9};


    @SuppressWarnings("NewObjectEquality")
    @Test
    void test2MatricesAreDifferentObjects() {
        Matrix matrix1 = createMatrix(3, 4);
        Matrix matrix2 = createMatrix(3, 4);
        assertThat(matrix1 != matrix2).isTrue();
    }

    @Test
    void testSetGetData() {
        Matrix matrix = createMatrix(3, 4);
        matrix.setData(1, 2, 3, 4, 11, 12, 13, 14, 21, 22, 23, 24);
        double res = matrix.get(1, 2);
        assertThat(res).isEqualTo(13);
        assertThat(matrix.isInEchelonForm()).isFalse();
    }

    @Test
    void testSetDataTwice() {
        Matrix matrix = createMatrix(2, 2);
        matrix.setData(1, 2, 3, 4);
        assertThatThrownBy(() -> matrix.setData(1, 2, 3, 4))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("setData cannot be called twice!");
    }

    @Test
    void testSwitchRowOutofBound1() {
        Matrix matrix = createMatrix(3, 4);
        Throwable thrown = catchThrowable(() -> matrix.switchRows(3, 1));
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("there is no row 3 or 1, actual rows = 3");
    }

    @Test
    void testSwitchRowOutofBound2() {
        Matrix matrix = createMatrix(3, 4);
        Throwable thrown = catchThrowable(() -> matrix.switchRows(1, 3));
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("there is no row 1 or 3, actual rows = 3");
    }

    @Test
    void testGetRow() {
        Matrix matrix = TestData.getEx1();
        assertThat(matrix.getRow(0)).as("getRow 0").isEqualTo(orgEx0);
        assertThat(matrix.getRow(1)).as("getRow 1").isEqualTo(orgEx1);
        assertThat(matrix.getRow(2)).as("getRow 2").isEqualTo(orgEx2);
        assertThatThrownBy(() -> matrix.getRow(3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("there is no row 3, actual rows = 3");
    }

    @Test
    void testPivot() {
        Matrix matrix = TestData.getEx1();
        assertThat(matrix.findPivot(0, 0)).as("row 0").isEqualTo(2);
        assertThat(matrix.findPivot(1, 1)).as("row 1").isEqualTo(2);
        assertThat(matrix.findPivot(2, 2)).as("row 2").isEqualTo(2);
    }


    @Test
    void testSwitchRows() {
        Matrix matrix = TestData.getEx1();
        matrix.logMatrix();
        matrix.switchRows(1, 2);
        assertThat(matrix.getRow(0)).as("getRow 0").isEqualTo(orgEx0);
        assertThat(matrix.getRow(1)).as("getRow 1").isEqualTo(orgEx2);  // <-- switched!
        assertThat(matrix.getRow(2)).as("getRow 2").isEqualTo(orgEx1);
        matrix.logMatrix();
    }

    @Test
    void testAddScaledRow() {
        Matrix matrix = createMatrix(2, 8);
        double[] pivotrow = new double[]{1, 2, 3, 4, 5, 6, 7, 8};
        double[] calcRow = new double[]{1, 2, 3, 4, 5, 6, 7, 8};
        matrix.addRow(pivotrow, calcRow, -2, 0);
        double[] expect = new double[]{-1, -2, -3, -4, -5, -6, -7, -8};
        assertThat(calcRow).isEqualTo(expect);
    }

    @Test
    void testNullifyFromRow() {
        Matrix matrix = TestData.getEx1();
        matrix.nullifyFromRow(0, 0);
        double[] exp1 = new double[]{0, 2, -8, 8}; // was already zero..
        double[] exp2 = new double[]{0, -3, 13, -9};
        assertThat(matrix.getRow(1)).as("row 1").isEqualTo(exp1);
        assertThat(matrix.getRow(2)).as("row 2").isEqualTo(exp2);
    }

    @Test
    void testEchelonForm1() {
        Matrix matrix = TestData.getEx1();
        matrix.echelon();
        double[] exp0 = new double[]{-4, 5, 9, -9};
        double[] exp1 = new double[]{0, 2, -8, 8};
        double[] exp2 = new double[]{0, 0, 0.25, 0.75};
        assertThat(matrix.getRow(0)).as("row 0").isEqualTo(exp0);
        assertThat(matrix.getRow(1)).as("row 1").isEqualTo(exp1);
        assertThat(matrix.getRow(2)).as("row 2").isEqualTo(exp2);
    }

    @Test
    void testEchelonForm2() {
        Matrix matrix = TestData.getEx2();
        matrix.echelon();
        double[] exp0 = new double[]{5.0, -8.0, 7.0, 1.0};
        double[] exp1 = new double[]{0.0, 1.0, -4.0, 8.0};
        double[] exp2 = new double[]{0.0, 0.0, 0.0, -1.0};
        assertThat(matrix.getRow(0)).as("row 0").isEqualTo(exp0);
        assertThat(matrix.getRow(1)).as("row 1").isEqualTo(exp1);
        assertThat(matrix.getRow(2)).as("row 2").isEqualTo(exp2);
    }

    @Test
    void testEchelonForm3() {
        Matrix matrix = TestData.getEx3();
        matrix.echelon();
        double[] exp0 = new double[]{1, 2, 3, 4, 5, 6};
        double[] exp1 = new double[]{0, 0, 1, 2, 3, 4};
        double[] exp2 = new double[]{0, 0, 0, 0, 5, 6};
        double[] exp3 = new double[]{0, 0, 0, 0, 0, 0.2

        };
        assertThat(matrix.getRow(0)).as("row 0").isEqualTo(exp0);
        assertThat(matrix.getRow(1)).as("row 1").isEqualTo(exp1);
        assertThat(matrix.getRow(2)).as("row 2").isEqualTo(exp2);
        assertThat(matrix.getRow(3)).as("row 3").isEqualTo(exp3);
    }

    @Test
    void testEchelonForm4() {
        Matrix matrix = TestData.getEx4();
        matrix.echelon();
        double[] exp0 = new double[]{1, 2, 3, 4};
        double[] exp1 = new double[]{0, 0, 0, 0};
        double[] exp2 = new double[]{0, 0, 0, 0};
        double[] exp3 = new double[]{0, 0, 0, 0};
        assertThat(matrix.getRow(0)).as("row 0").isEqualTo(exp0);
        assertThat(matrix.getRow(1)).as("row 1").isEqualTo(exp1);
        assertThat(matrix.getRow(2)).as("row 2").isEqualTo(exp2);
        assertThat(matrix.getRow(3)).as("row 3").isEqualTo(exp3);
    }

    @Test
    void testConsistencyOnlyWithEchelon() {
        Matrix matrix = TestData.getEx1();
        assertThatThrownBy(matrix::isConsistent)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("consistency check only with matrices in echelon form");
    }

    @Test
    void testConsistecyTrue() {
        Matrix matrix = TestData.getEx1();
        matrix.echelon();
        assertThat(matrix.isConsistent()).isTrue();
    }

    @Test
    void testConsistecyFalse() {
        Matrix matrix = TestData.getEx2();
        matrix.echelon();
        assertThat(matrix.isConsistent()).isFalse();
    }

    @Test
    void testConsistecyRow() {
        Matrix matrix = TestData.getEx2();
        assertThat(matrix.isInconsistent(new double[]{1.0, 2.0, 3.0, 0.0})).isFalse();
    }

    @Test
    void testReducedEchelonOnlyIfEchelon() {
        Matrix matrix = TestData.getEx1();
        assertThatThrownBy(matrix::reducedEchelon)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("reduced Echelon form only possible for matrices in echelon form");
    }
}
