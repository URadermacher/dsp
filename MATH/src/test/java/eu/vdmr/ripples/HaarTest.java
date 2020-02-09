package eu.vdmr.ripples;

import eu.vdmr.math.matrix.Matrix;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class HaarTest extends RippleTest {

    private Haar haar;

    @BeforeEach
    public void setup() {
        super.setup();
        haar = new Haar();
    }

    @Test
    public void testHaar_Ex1() {
        double[] data = dataProvider.makeDoubles(56d, 40d, 8d, 24d, 48d, 48d, 40d, 16d);
        double[] meaned = haar.haarTransform(data);
        checkDoubles(meaned, 35d, -3d, 16d, 10d, 8d, -8d, 0, 12d);
    }

    @Test
    public void testHaarInPlace_Ex1() {
        double[] data = dataProvider.makeDoubles(56d, 40d, 8d, 24d, 48d, 48d, 40d, 16d);
        haar.haarInPlaceTransform(data, 3);
        checkDoubles(data, 35d, -3d, 16d, 10d, 8d, -8d, 0, 12d);
    }

    @Test
    public void testHaar_2() {
        double[] data = dataProvider.makeDoubles(10d, 20d);
        double[] meaned = haar.haarTransform(data);
        checkDoubles(meaned, 15d, -5d);
    }

    @Test
    public void testThreshold() {
        double[] testdata = dataProvider.makeDoubles(35d, -3d, 16d, 10d, 8d, -8d, 0, 12d);
        haar.applyThreshold(testdata, 4d);
        checkDoubles(testdata, 35d, 0d, 16d, 10d, 8d, -8d, 0, 12d);
        testdata = dataProvider.makeDoubles(35d, -3d, 16d, 10d, 8d, -8d, 0, 12d);
        haar.applyThreshold(testdata, 9d);
        checkDoubles(testdata, 35d, 0d, 16d, 10d, 0d, 0d, 0, 12d);
    }

    @Test
    public void testRestore() {
        double[] testdata = dataProvider.makeDoubles(35d, -3d, 16d, 10d, 8d, -8d, 0, 12d);
        double[] restored = haar.haarInverse(testdata);
        checkDoubles(restored, 56d, 40d, 8d, 24d, 48d, 48d, 40d, 16d);
    }

    @Test
    public void testWrongLength() {
        double[] testdata = dataProvider.makeDoubles(10d, 20d, 30d);
        assertThatThrownBy(() -> haar.haarTransform(testdata))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("input array length must be exp of 2");
    }

    @Test
    void testRandom() {
        double[] testdata = dataProvider.makeRandomDoubles(128, 56);
        listData(testdata);
        double[] meaned = haar.haarTransform(testdata);
        double[] restored = haar.haarInverse(meaned);
        checkDoubles(restored, testdata);
    }

    @Test
    public void testMultiHaar_1() {
        double[] data = dataProvider.makeDoubles(56d, 40d, 8d, 24d, 48d, 48d, 40d, 16d);
        double[][] meaned = haar.multiHaarTransform(data, 3);
        int i = 1;
        checkDoubles(meaned[0], 48d, 16d, 48d, 28d, 8d, -8d, 0, 12d);
        checkDoubles(meaned[1], 32d, 38d, 16d, 10d);
        checkDoubles(meaned[2], 35d, -3d);
    }

    @Test
    public void testMatrixHaar1() {
        double[] transformed = haar.matrixTransform(dataProvider.makeDoubles(1, 0, 0, 0, 0, 0, 0, 0));
        checkDoubles(transformed, 0.125d, 0.125d, 0.25d, 0, 0.5d, 0d, 0d, 0d);
    }

    @Test
    public void testMatrixInverseHaar1() {
        double[] reversed = haar.matrixReverse(dataProvider.makeDoubles(1, 0, 0, 0, 0, 0, 0, 0));
        checkDoubles(reversed, 1d, 1d, 1d, 1d, 1d, 1d, 1d, 1d);
    }

    @Test
    public void testMatrixHaarEx1() {
        double[] transformed = haar.matrixTransform(dataProvider.makeDoubles(56d, 40d, 8d, 24d, 48d, 48d, 40d, 16d));
        checkDoubles(transformed, 35d, -3d, 16d, 10, 8d, -8d, 0d, 12d);
    }

    @Test
    public void testMatrixInverseHaarEx1() {
        double[] reversed = haar.matrixReverse(dataProvider.makeDoubles(35d, -3d, 16d, 10, 8d, -8d, 0d, 12d));
        checkDoubles(reversed, 56d, 40d, 8d, 24d, 48d, 48d, 40d, 16d);
    }

    @Test
    public void testAnalysisMatrixGeneration() {
        Matrix result = haar.generateAnalysisMatrix(8);
        Matrix check = haar.get_W3_AnalysisMatrix();
        assertThat(result).isEqualTo(check);
    }

    @Test
    public void testSynthesisMatrixGeneration() {
        Matrix result = haar.generateSynthesisMatrix(8);
        Matrix check = haar.get_W3_SynthesissMatrix();
        assertThat(result).isEqualTo(check);
    }
}
