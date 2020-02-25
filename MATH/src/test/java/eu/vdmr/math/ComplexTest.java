package eu.vdmr.math;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
public class ComplexTest {

    @Test
    void testCtor() {
        Complex c = new Complex(1.0d, 2.0d);
        assertThat(c.getReal()).isEqualTo(1.0);
        assertThat(c.getImag()).isEqualTo(2.0);
    }

    @Test
    void testAdd() {
        Complex c1 = new Complex(1.0d, 2.0d);
        Complex c2 = new Complex(3.0d, 4.0d);
        Complex res1 = c1.add(c2);
        assertThat(res1.getReal()).isEqualTo(4.0);
        assertThat(res1.getImag()).isEqualTo(6.0);
        Complex res2 = c2.add(c1);
        assertThat(res2.getReal()).isEqualTo(4.0);
        assertThat(res2.getImag()).isEqualTo(6.0);
    }

    @Test
    void testMultiply() {
        Complex c1 = new Complex(3.0d, 2.0d);
        Complex c2 = new Complex(1.0d, 7.0d);
        Complex res1 = c1.multiply(c2);
        assertThat(res1.getReal()).isEqualTo(-11.0d);
        assertThat(res1.getImag()).isEqualTo(23.0d);
        Complex res2 = c2.multiply(c1);
        assertThat(res2.getReal()).isEqualTo(-11.0d);
        assertThat(res2.getImag()).isEqualTo(23.0d);
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
        assertThat(c1.getMagnitude()).isEqualTo(5.0d);
    }
}
