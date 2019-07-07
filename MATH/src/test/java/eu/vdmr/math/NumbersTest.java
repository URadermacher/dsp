package eu.vdmr.math;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NumbersTest {

    @SuppressWarnings("ConstantConditions")
    @Test
    void testMaxNull() {
        assertThatThrownBy(() -> Numbers.getMax(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("may not be called with null!");
    }

    @Test
    void testMaxEmpty() {
        List<Integer> l = new ArrayList<>();
        assertThatThrownBy(() -> Numbers.getMax(l))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("may not be called with empty list!");
    }

    @Test
    void testMaxInteger() {
        List<Integer> l = new ArrayList<>();
        l.add(1);
        l.add(5);
        l.add(3);
        Object o = Numbers.getMax(l);
        assertThat(o).isInstanceOf(Integer.class);
        assertThat(((Integer) o).intValue()).isEqualTo(5);
    }

    @Test
    void testMaxByte() {
        List<Byte> l = new ArrayList<>();
        l.add(Byte.valueOf("1"));
        l.add(Byte.valueOf("5"));
        l.add(Byte.valueOf("3"));
        Object o = Numbers.getMax(l);
        assertThat(o).isInstanceOf(Byte.class);
        assertThat(((Byte) o).intValue()).isEqualTo(5);
    }

    @Test
    void testMaxDouble() {
        List<Double> l = new ArrayList<>();
        l.add(Double.valueOf("1.3"));
        l.add(Double.valueOf("5.88989"));
        l.add(Double.valueOf("3.7"));
        Object o = Numbers.getMax(l);
        assertThat(o).isInstanceOf(Double.class);
        assertThat(((Double) o).intValue()).isEqualTo(5.88989);
    }

    @Test
    void testMaxBigDecimal() {
        List<BigDecimal> l = new ArrayList<>();
        BigDecimal res = BigDecimal.valueOf(5.88989);
        l.add(BigDecimal.valueOf(1.3));
        l.add(res);
        l.add(BigDecimal.valueOf(3.7));
        Object o = Numbers.getMax(l);
        assertThat(o).isInstanceOf(BigDecimal.class);
        assertThat(((BigDecimal) o).intValue()).isEqualTo(res);
    }
}
