package eu.vdmr.math;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.apache.commons.math3.complex.Complex;
public class ComplexTest {

    @Test
    void testCtor() {
        Complex c = new Complex(1.0d, 2.0d);
        assertThat(c.getReal()).isEqualTo(1.0);
        assertThat(c.getImaginary()).isEqualTo(2.0);
    }

    @Test
    void testAdd() {
        Complex c1 = new Complex(1.0d, 2.0d);
        Complex c2 = new Complex(3.0d, 4.0d);
        Complex res1 = c1.add(c2);
        assertThat(res1.getReal()).isEqualTo(4.0);
        assertThat(res1.getImaginary()).isEqualTo(6.0);
        Complex res2 = c2.add(c1);
        assertThat(res2.getReal()).isEqualTo(4.0);
        assertThat(res2.getImaginary()).isEqualTo(6.0);
    }

    @Test
    void testMultiply() {
        Complex c1 = new Complex(3.0d, 2.0d);
        Complex c2 = new Complex(1.0d, 7.0d);
        Complex res1 = c1.multiply(c2);
        assertThat(res1.getReal()).isEqualTo(-11.0d);
        assertThat(res1.getImaginary()).isEqualTo(23.0d);
        Complex res2 = c2.multiply(c1);
        assertThat(res2.getReal()).isEqualTo(-11.0d);
        assertThat(res2.getImaginary()).isEqualTo(23.0d);
    }

    @Test
        public void testEquals() {
        Complex c1 = new Complex(1.0d, 2.0d);
        Complex c2 = new Complex(1.0d, 2.0d);
        assertThat(c1).isNotSameAs(c2);
        assertThat(c1.equals(c2)).isTrue();
        //noinspection EqualsBetweenInconvertibleTypes
        assertThat(c1.equals("Piet")).isFalse();
    }

    @Test
    void testMagnitude() {
        Complex c1 = new Complex(3.0d, 4.0d);
        assertThat(c1.abs()).isEqualTo(5.0d);
    }

    @Test
    void testAngle_0(){
        Complex c1 = new Complex(1.0d, 0.0d);
        assertThat(c1.getArgument()).isEqualTo(0.0d);

    }

    @Test
    void testAngle_45(){
        Complex c1 = new Complex(4.0d, 4.0d);
        assertThat(Math.toDegrees(c1.getArgument())).isCloseTo(45.0d, Percentage.withPercentage(0.0001));

    }
}
