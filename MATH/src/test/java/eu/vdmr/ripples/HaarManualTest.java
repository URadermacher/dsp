package eu.vdmr.ripples;

import eu.vdmr.util.gnuplot.GnuPlot;
import eu.vdmr.util.gnuplot.GnuPlotter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HaarManualTest extends RippleTest {

    private GnuPlotter plotter;
    private Haar haar;

    @BeforeEach
    public void setup() {
        super.setup();
        haar = new Haar();
       //plotter = new GnuPlotter(new GnuPlot(1, new int[] {2}));
    }

    @Test
    // plot the original example 1 data
    public void test1() {
        double[] data = dataProvider.makeDoubles(56d, 40d, 8d, 24d, 48d, 48d, 40d, 16d);
        double[] index = dataProvider.makeCounters(8);
        if (plotter.writeData(index, data)) {
            System.out.println("go to gnuplt to plot");
        }
    }

    @Test
    // plot the transformed original example 1
    public void test2() {
        double[] data = dataProvider.makeDoubles(56d, 40d, 8d, 24d, 48d, 48d, 40d, 16d);
        double[] index = dataProvider.makeCounters(8);
        double[] meaned = haar.haarTransform(data);
        if (plotter.writeData(index, meaned)) {
            System.out.println("go to gnuplt to plot");
        }
    }

    @Test
    // plot inverse for single 1's in transformed data
    public void test3() {
        double[] data = new double[8];
        data[0] = 1d;
        double[] index = dataProvider.makeCounters(8);
        double[] meaned = haar.haarInverse(data);
        if (plotter.writeData(index, meaned)) {
            System.out.println("go to gnuplt to plot");
        }
    }

    @Test
    // plot transform for single 1's in signal data
    public void test4() {
        double[] data = new double[8];
        data[7] = 1d;
        double[] index = dataProvider.makeCounters(8);
        double[] meaned = haar.haarTransform(data);
        if (plotter.writeData(index, meaned)) {
            System.out.println("go to gnuplt to plot");
        }
    }
}
