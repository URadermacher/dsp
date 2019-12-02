package eu.vdmr.math.matrix;

import eu.vdmr.math.testData.MatrixTestData;
import eu.vdmr.math.vector.Vector;
import org.junit.jupiter.api.Test;

import java.util.List;

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
        assertThat(matrix.get(1, 2)).isEqualTo(13);
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
    void testMultiplyRow1() {
        Matrix matrix = createMatrix(2, 8);
        double[] testRow = new double[]{0, 0, 4, 15, 1, 16, 40000000, -2.5};
        matrix.multiplyRow(testRow, 2, 0.25);
        assertThat(testRow).isEqualTo(new double[]{0, 0, 1, 3.75, 0.25, 4, 10000000, -0.625});
    }

    @Test
    void testGetRow() {
        Matrix matrix = MatrixTestData.getEx1();
        assertThat(matrix.getRow(0)).as("getRow 0").isEqualTo(orgEx0);
        assertThat(matrix.getRow(1)).as("getRow 1").isEqualTo(orgEx1);
        assertThat(matrix.getRow(2)).as("getRow 2").isEqualTo(orgEx2);
        assertThatThrownBy(() -> matrix.getRow(3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("there is no row 3, actual rows = 3");
    }

    @Test
    void testPivot() {
        Matrix matrix = MatrixTestData.getEx1();
        assertThat(matrix.findPivot(0, 0)).as("row 0").isEqualTo(2);
        assertThat(matrix.findPivot(1, 1)).as("row 1").isEqualTo(2);
        assertThat(matrix.findPivot(2, 2)).as("row 2").isEqualTo(2);
    }


    @Test
    void testSwitchRows() {
        Matrix matrix = MatrixTestData.getEx1();
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
        Matrix matrix = MatrixTestData.getEx1();
        matrix.nullifyFromRow(0, 0);
        double[] exp1 = new double[]{0, 2, -8, 8}; // was already zero..
        double[] exp2 = new double[]{0, -3, 13, -9};
        assertThat(matrix.getRow(1)).as("row 1").isEqualTo(exp1);
        assertThat(matrix.getRow(2)).as("row 2").isEqualTo(exp2);
    }

    @Test
    void testGetFirstNonZero() {
        Matrix matrix = MatrixTestData.getEmpty();
        assertThat(matrix.getFirstNonZero(new double[]{0, 0, 2, 2})).isEqualTo(2);
    }

    @Test
    void testGetFirstNonZeroAllZero() {
        Matrix matrix = MatrixTestData.getEmpty();
        assertThat(matrix.getFirstNonZero(new double[]{0, 0, 0, 0,})).isEqualTo(-1);
    }

    @Test
    void testEchelonForm1() {
        Matrix matrix = MatrixTestData.getEx1();
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
        Matrix matrix = MatrixTestData.getEx2();
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
        Matrix matrix = MatrixTestData.getEx3();
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
        Matrix matrix = MatrixTestData.getEx4();
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
        Matrix matrix = MatrixTestData.getEx1();
        assertThatThrownBy(matrix::isConsistent)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("consistency check only with matrices in echelon form");
    }

    @Test
    void testConsistecyTrue() {
        Matrix matrix = MatrixTestData.getEx1();
        matrix.echelon();
        assertThat(matrix.isConsistent()).isEmpty();
    }

    @Test
    void testConsistecyFalse() {
        Matrix matrix = MatrixTestData.getEx2();
        matrix.echelon();
        assertThat(matrix.isConsistent()).isNotEmpty();
    }

    @Test
    void testConsistecyRow() {
        Matrix matrix = MatrixTestData.getEx2();
        assertThat(matrix.isInconsistent(new double[]{1.0, 2.0, 3.0, 0.0})).isFalse();
    }

    @Test
    void testReducedEchelonOnlyIfEchelon() {
        Matrix matrix = MatrixTestData.getEx1();
        assertThatThrownBy(matrix::reducedEchelon)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("reduced Echelon form only possible for matrices in echelon form");
    }

    @Test
    void testReducedEchelon1() {
        Matrix matrix = MatrixTestData.getEx1();
        matrix.echelon();
        matrix.reducedEchelon();
        assertThat(matrix.getRow(0)).isEqualTo(new double[]{1.0, 0.0, 0.0, 29.0});
        assertThat(matrix.getRow(1)).isEqualTo(new double[]{0.0, 1.0, 0.0, 16.0});
        assertThat(matrix.getRow(2)).isEqualTo(new double[]{0.0, 0.0, 1.0, 3.0});
    }

    @Test
    void testReducedEchelon2() {
        Matrix matrix = MatrixTestData.getEx2();
        matrix.echelon();
        matrix.reducedEchelon();
        assertThat(matrix.getRow(0)).isEqualTo(new double[]{1.0, 0.0, -5.0, 0.0});
        assertThat(matrix.getRow(1)).isEqualTo(new double[]{0.0, 1.0, -4.0, 0.0});
        assertThat(matrix.getRow(2)).isEqualTo(new double[]{0.0, 0.0, 0.0, 1.0});
    }


    @Test
    void testNullifyToTopError() {
        Matrix matrix = MatrixTestData.getEx1();
        assertThatThrownBy(() -> matrix.nullifyToTop(2, 1))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("pivot cell must be 1.0, but data[2][1] is 5.0");
    }

    @Test
    void testNullifyToTop() {
        Matrix matrix = MatrixTestData.getEx1();
        matrix.set(2, 1, 1.0);
        matrix.nullifyToTop(2, 1);
        assertThat(matrix.getRow(0)).isEqualTo(new double[]{1, 0, 19, -18});
        assertThat(matrix.getRow(1)).isEqualTo(new double[]{0, 0, -26, 26});
    }

    @Test
    void testWriteAsLEq() {
        Matrix matrix = MatrixTestData.getEx1();
        String res = matrix.writeAsLinearEquation();
        String[] lines = res.split("\n");
        assertThat(lines).hasSize(4);
        assertThat(lines[0]).isEqualTo("");
        assertThat(lines[1]).isEqualTo("x1\t-2.0x2\tx3\t = 0.0");
        assertThat(lines[2]).isEqualTo("\t\t2.0x2\t-8.0x3\t = 8.0");
        assertThat(lines[3]).isEqualTo("-4.0x1\t5.0x2\t9.0x3\t = -9.0");
    }

    @Test
    void testSolve1() {
        Matrix matrix = MatrixTestData.getEx1();
        LinearEquationSolution solution = matrix.solve();
        assertThat(solution).isNotNull();
        List<LinearEquationSolution.FixedSolution> solutions = solution.getSolutions();
        assertThat(solutions).hasSize(3);
        assertThat(solution.getFreeVariables()).isEmpty();
        assertThat(solutions.get(0).getVarNr()).isEqualTo(1);
        assertThat(solutions.get(0).getSolution()).isEqualTo(29.0);
        assertThat(solutions.get(1).getVarNr()).isEqualTo(2);
        assertThat(solutions.get(1).getSolution()).isEqualTo(16.0);
        assertThat(solutions.get(2).getVarNr()).isEqualTo(3);
        assertThat(solutions.get(2).getSolution()).isEqualTo(3.0);
        matrix.writeAsLinearEquation();
    }

    @Test
    void testSolve2() {
        Matrix matrix = MatrixTestData.getEx2();
        LinearEquationSolution solution = matrix.solve();
        assertThat(solution).isNotNull();
        assertThat(solution.isSolved()).isFalse();
        assertThat(solution.isInconsistent()).isTrue();
        assertThat(solution.getInconsistenRowsIndices()).hasSize(1);
        assertThat(solution.getInconsistenRowsIndices().get(0)).isEqualTo(2);
    }

    @Test
    void testSolve3() {
        Matrix matrix = MatrixTestData.getEx3();
        LinearEquationSolution solution = matrix.solve();
        assertThat(solution).isNotNull();
        assertThat(solution.isSolved()).isFalse();
        assertThat(solution.isInconsistent()).isTrue();
        assertThat(solution.getInconsistenRowsIndices()).hasSize(1);
        assertThat(solution.getInconsistenRowsIndices().get(0)).isEqualTo(3);
    }


    @Test
    void testSolve4() {
        Matrix matrix = MatrixTestData.getEx4();
        LinearEquationSolution solution = matrix.solve();
        assertThat(solution).isNotNull();
        assertThat(solution.isSolved()).isTrue();
        assertThat(solution.isInconsistent()).isFalse();
        assertThat((solution.getFreeVariables())).hasSize(2);
        List<LinearEquationSolution.FixedSolution> solutions = solution.getSolutions();
        assertThat(solutions).hasSize(1);
        assertThat(solutions.get(0).getFreeVariables()).hasSize(2);
        assertThat(solutions.get(0).getFreeVariables().get(0)).isEqualTo(2);
        assertThat(solutions.get(0).getFreeVariables().get(1)).isEqualTo(3);
        assertThat(solutions.get(0).getFreeVariableValues()).hasSize(2);
        assertThat(solutions.get(0).getFreeVariableValues().get(0)).isEqualTo(2.0);
        assertThat(solutions.get(0).getFreeVariableValues().get(1)).isEqualTo(3.0);
        assertThat(solutions.get(0).getVarNr()).isEqualTo(1);
        assertThat(solutions.get(0).getSolution()).isEqualTo(4.0);
        matrix.writeAsLinearEquation();
    }

    @Test
    void testSolve5() {
        Matrix matrix = MatrixTestData.getEx5();
        LinearEquationSolution solution = matrix.solve();
        assertThat(solution).isNotNull();
        assertThat(solution.isInconsistent()).isTrue();
    }

    @Test
    void testSetCol() {
        Matrix matrix = MatrixTestData.getEx1();
        matrix.setCol(1, new double[] {22.0, 23.0, 24.0});
        double[] exp0 = new double[]{1.0, 22.0, 1.0, 0};
        double[] exp1 = new double[]{0, 23.0, -8.0, 8.0};
        double[] exp2 = new double[]{-4.0, 24.0, 9.0, -9.0};
        assertThat(matrix.getRow(0)).as("row 0").isEqualTo(exp0);
        assertThat(matrix.getRow(1)).as("row 1").isEqualTo(exp1);
        assertThat(matrix.getRow(2)).as("row 2").isEqualTo(exp2);

    }

    @Test
    void testVectorProduct() {
        Matrix matrix = MatrixTestData.getEx1();
        Vector multiplicand = Vector.createVector(4, 2.0, 3.0, 4.0, 5.0);
        Vector result = matrix.vectorProduct(multiplicand);
        Vector expected = Vector.createVector(3, 0.0, 14.0, -2.0 );
        assertThat(result).isEqualTo(expected);

    }


    @Test
    void testVectorProductError() {
        Matrix matrix = MatrixTestData.getEx1();
        Vector multiplicand = Vector.createVector(2, 2.0, 3.0);
        assertThatThrownBy(() -> matrix.vectorProduct(multiplicand))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Vector must have same dimansion as matric colums!");
    }

    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Test
    void tesEqualsOtherClass() {
        Matrix matrix = MatrixTestData.getEx1();
        assertThat(matrix.equals("Piet")).isFalse();
    }

    @Test
    void testEqualsOK() {
        Matrix matrix1 = MatrixTestData.getEx1();
        Matrix matrix2 = MatrixTestData.getEx1();
        assertThat(matrix1).isNotSameAs(matrix2);
        assertThat(matrix1.equals(matrix2)).isTrue();
    }

    @Test
    void testEqualsDiffSizes() {
        Matrix matrix1 = MatrixTestData.getEx2();
        Matrix matrix2 = MatrixTestData.getEx3();
        assertThat(matrix1.equals(matrix2)).isFalse();
    }

    @Test
    void testEqualsNot() {
        Matrix matrix1 = MatrixTestData.getEx1();
        Matrix matrix2 = MatrixTestData.getEx2();
        assertThat(matrix1.equals(matrix2)).isFalse();
    }


}
