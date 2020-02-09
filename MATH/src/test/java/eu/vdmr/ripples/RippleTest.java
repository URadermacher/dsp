package eu.vdmr.ripples;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public abstract class RippleTest {

    protected RippleData dataProvider;

    @BeforeEach
    public void setup() {
        dataProvider = new RippleData();
    }

    protected void checkDoubles(double[] actual, double ... expected) {
        int idx = 0;
        for (double exp : expected) {
            assertThat(actual[idx++]).isCloseTo(exp, Assertions.offset(0.00001));
        }
    }

    protected void listData(double[] in) {
        for (int i = 0; i < in.length; i++) {
            System.out.println(in[i]);
        }
    }
}
