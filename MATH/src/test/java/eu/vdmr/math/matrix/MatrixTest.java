package eu.vdmr.math.matrix;

import org.junit.jupiter.api.Test;

import static eu.vdmr.math.matrix.Matrix.createMatrix;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class MatrixTest {


    @SuppressWarnings("NewObjectEquality")
    @Test
    void test2MatricesAreDifferentObjects() {
        Matrix matrix1 = createMatrix( 3, 4);
        Matrix matrix2 = createMatrix( 3, 4);
        assertThat(matrix1 != matrix2).isTrue();
    }

    @Test
    void testSetGetData() {
        Matrix matrixInt = createMatrix(3, 4);
        matrixInt.setData(1, 2, 3, 4, 11, 12, 13, 14, 21, 22, 23, 24);
        double res =  matrixInt.get(1, 2);
        assertThat(res).isEqualTo(13);
    }

    @Test
    void testSwitchRowOutofBound1() {
        Matrix matrixInt = createMatrix(3, 4);
        Throwable thrown = catchThrowable(() -> matrixInt.switchRow(3, 1));
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("there is no row 3 or 1, actual rows = 3");
    }

    @Test
    void testSwitchRowOutofBound2() {
        Matrix matrixInt = createMatrix(3, 4);
        Throwable thrown = catchThrowable(() -> matrixInt.switchRow(1, 3));
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("there is no row 1 or 3, actual rows = 3");
    }

    @Test
    void testEchelonForm() {
        Matrix matrixInt = createMatrix(3, 4);
        matrixInt.setData(1, -2, 1, 0, 0, 2, -8, 8, -4, 5, 9, -9);
        matrixInt.echelon();
    }

}
