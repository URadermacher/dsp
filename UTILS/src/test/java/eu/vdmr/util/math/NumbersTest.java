package eu.vdmr.util.math;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NumbersTest {

    @Test
    void testMaxNull() {
        assertThatThrownBy(() -> {Numbers.getMax(null);})
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("may not be called with null!");
    }

    @Test
    void testMaxEmpty() {
        List<Integer> l = new ArrayList<>();
        assertThatThrownBy(() -> {Numbers.getMax(l);})
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
        List<Byte> l = new ArrayList<Byte>();
        l.add(Byte.valueOf("1"));
        l.add(Byte.valueOf("5"));
        l.add(Byte.valueOf("3"));
        Object o = Numbers.getMax(l);
        Assert.assertTrue(o instanceof Byte);
        Assert.assertEquals(5, ((Byte) o).intValue());
    }

    @Test
    void testMaxDouble() {
        List<Double> l = new ArrayList<Double>();
        l.add(Double.valueOf("1.3"));
        l.add(Double.valueOf("5.88989"));
        l.add(Double.valueOf("3.7"));
        Object o = Numbers.getMax(l);
        Assert.assertTrue(o instanceof Double);
        Assert.assertEquals(5.88989, ((Double) o).doubleValue(), 0.0);
    }

    @Test
    void testMaxBigDecimal() {
        List<BigDecimal> l = new ArrayList<BigDecimal>();
        BigDecimal res = BigDecimal.valueOf(5.88989);
        l.add(BigDecimal.valueOf(1.3));
        l.add(res);
        l.add(BigDecimal.valueOf(3.7));
        Object o = Numbers.getMax(l);
        Assert.assertTrue(o instanceof BigDecimal);
        Assert.assertTrue(res.equals(o));
    }
}
