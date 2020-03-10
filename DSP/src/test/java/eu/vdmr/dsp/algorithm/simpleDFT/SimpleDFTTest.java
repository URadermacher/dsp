package eu.vdmr.dsp.algorithm.simpleDFT;

import eu.vdmr.math.matrix.ComplexMatrix;
import org.apache.commons.math3.complex.Complex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleDFTTest {
    private SimpleDFT dft;
    private static Complex complexZero = new Complex(0.0d, 0.0d);
    private static Complex ci = new Complex(0, 1);
    private static Complex cmi = new Complex(0, -1);
    private static Complex cm1 = new Complex(-1, 0);


    @BeforeEach
    public void setup() {
        dft = new SimpleDFT();
    }

    @Test
    public void testConstant() {
        int count = 256;
        Complex[] signal = SimpleDFTTestData.makeConstantRealSignal(count);
        Complex[] spectrum = dft.doDFTTrigonometric(signal);
        assertThat(spectrum).hasSize(count);
        assertThat(spectrum[0]).isEqualTo(new Complex(count, 0.0));
        for (int i = 1; i < count; i++) {
            assertThat(spectrum[i]).isEqualTo(complexZero);
        }
    }

    @Test
    public void testAlternate() {
        int count = 8;
        Complex[] signal = SimpleDFTTestData.makeAlternateRealSignal(count);
        Complex[] spectrum = dft.doDFTTrigonometric(signal);
        assertThat(spectrum).hasSize(count);
        for (int i = 0; i < count; i++) {
            if (i == 0 || i == count / 2) {
                assertThat(spectrum[i]).isEqualTo(new Complex(count / 2.0d, 0.0d));
            } else {
                assertThat(spectrum[i]).isEqualTo(complexZero);
            }
        }
    }

    @Test
    public void testCalculateSimpleValue() {
        assertThat(dft.calculateValue(0)).isEqualTo(Complex.ONE);
        assertThat(dft.calculateValue(90)).isEqualTo(ci);
        assertThat(dft.calculateValue(-90)).isEqualTo(cmi);
        assertThat(dft.calculateValue(180)).isEqualTo(cm1);
        assertThat(dft.calculateValue(-180)).isEqualTo(cm1);
        assertThat(dft.calculateValue(270)).isEqualTo(cmi);
        assertThat(dft.calculateValue(-270)).isEqualTo(ci);
        assertThat(dft.calculateValue(-540)).isEqualTo(cm1);
        assertThat(dft.calculateValue(-810)).isEqualTo(cmi);
    }

    @Test
    public void testCalculateValues() {
        assertThat(dft.calculateValue(-45)).isEqualTo(new Complex(+0.7071068, -0.7071068));
        assertThat(dft.calculateValue(-135)).isEqualTo(new Complex(-0.7071068, -0.7071068));
        assertThat(dft.calculateValue(-315)).isEqualTo(new Complex(+0.7071068, +0.7071068));
        assertThat(dft.calculateValue(-225)).isEqualTo(new Complex(-0.7071068, +0.7071068));
        assertThat(dft.calculateValue(-1350)).isEqualTo(ci);
        assertThat(dft.calculateValue(-1575)).isEqualTo(new Complex(-0.7071068, -0.7071068));
    }

    @Test
    public void testGetBaseMatrix() {
        int dimension = 8;
        Complex pp45 = new Complex(0.7071068, 0.7071068);
        Complex pm45 = new Complex(0.7071068, -0.7071068);
        Complex mp45 = new Complex(-0.7071068, 0.7071068);
        Complex mm45 = new Complex(-0.7071068, -0.7071068);
        ComplexMatrix result = dft.getBaseMatrix(dimension);
        //result.logMatrix();
        for (int i = 0; i < dimension; i++) {
            assertThat(result.get(0, i)).isEqualTo(Complex.ONE);
        }
        for (int i = 0; i < dimension; i++) {
            assertThat(result.get(i, 0)).isEqualTo(Complex.ONE);
        }
        if (dimension == 8) {
            assertThat(result.get(1, 1)).isEqualTo(pp45);
            assertThat(result.get(3, 3)).isEqualTo(pp45);
            assertThat(result.get(5, 5)).isEqualTo(pp45);
            assertThat(result.get(7, 7)).isEqualTo(pp45);

            assertThat(result.get(1, 7)).isEqualTo(pm45);
            assertThat(result.get(7, 1)).isEqualTo(pm45);
            assertThat(result.get(5, 3)).isEqualTo(pm45);
            assertThat(result.get(3, 5)).isEqualTo(pm45);

            assertThat(result.get(1, 3)).isEqualTo(mp45);
            assertThat(result.get(3, 1)).isEqualTo(mp45);
            assertThat(result.get(7, 5)).isEqualTo(mp45);
            assertThat(result.get(5, 7)).isEqualTo(mp45);

            assertThat(result.get(1, 5)).isEqualTo(mm45);
            assertThat(result.get(5, 1)).isEqualTo(mm45);
            assertThat(result.get(7, 3)).isEqualTo(mm45);
            assertThat(result.get(3, 7)).isEqualTo(mm45);

        }
        System.out.println("trigcount = " + dft.getTrigCount());
    }

}
