package eu.vdmr.ripples;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class Ex1Test extends RippleTest{

    private  Ex1 ex1;

    @BeforeEach
    public void setup() {
        super.setup();
        ex1 = new Ex1();
    }

    @Test
    public void testEx1_1() {
        double[] data = dataProvider.makeDoubles(56d, 40d, 8d, 24d, 48d, 48d, 40d, 16d);
        double[] meaned  = ex1.calculateMean(data);
        checkDoubles(meaned, 35d, -3d, 16d, 10d, 8d, -8d, 0, 12d);
    }

    @Test
    public void testThreshold() {
        double[] testdata = dataProvider.makeDoubles(35d, -3d, 16d, 10d, 8d, -8d, 0, 12d);
        ex1.applyThreshold(testdata, 4d);
        checkDoubles(testdata, 35d, 0d, 16d, 10d, 8d, -8d, 0, 12d);
        testdata = dataProvider.makeDoubles(35d, -3d, 16d, 10d, 8d, -8d, 0, 12d);
        ex1.applyThreshold(testdata, 9d);
        checkDoubles(testdata, 35d, 0d, 16d, 10d, 0d, 0d, 0, 12d);
    }

    @Test
    public void testRestore() {
        double[] testdata = dataProvider.makeDoubles(35d, -3d, 16d, 10d, 8d, -8d, 0, 12d);
        double[] restored = ex1.restore(testdata);
        checkDoubles(restored, 56d, 40d, 8d, 24d, 48d, 48d, 40d, 16d);
    }

    @Test
    public void testWrongLength() {
        double[] testdata = dataProvider.makeDoubles(10d,20d,30d);
        assertThatThrownBy(() -> ex1.calculateMean(testdata))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("input array length must be exp of 2");
    }

    @Test
    void testRandom() {
        double[] testdata = dataProvider.makeRandomDoubles(128, 56);
        for (int i = 0; i < testdata.length; i++) {
            System.out.println(testdata[i]);
        }
        double[] meaned = ex1.calculateMean(testdata);
        double[] restored = ex1.restore(meaned);
        checkDoubles(restored, testdata);
     }
}
