package eu.vdmr.ripples;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

//import com.panayotis.gnuplot.JavaPlot;


public class HaarManualTest extends RippleTest{

    private Path path ;
    private Haar haar;

    @BeforeEach
    public void setup() {
        super.setup();
        haar = new Haar();
        String tmpFile = System.getProperty("java.io.tmpdir") + File.separator + "Ex1.txt";
        path = Paths.get(tmpFile);
        System.out.println(path.toAbsolutePath());
    }

    @Test
    // plot the original example 1 data
    public void test1() {
        double[] data = dataProvider.makeDoubles(56d, 40d, 8d, 24d, 48d, 48d, 40d, 16d);
        double[] index = dataProvider.makeCounters(8);
        if (writeData(index, data)) {
            System.out.println("go to gnuplt to plot");
        }
    }

    @Test
    // plot the transformed original example 1
    public void test2() {
        double[] data = dataProvider.makeDoubles(56d, 40d, 8d, 24d, 48d, 48d, 40d, 16d);
        double[] index = dataProvider.makeCounters(8);
        double[] meaned  = haar.haarTransform(data);
        if (writeData(index, meaned)) {
            System.out.println("go to gnuplt to plot");
        }
    }

    @Test
    // plot inverse for single 1's in transformed data
    public void test3() {
        double[] data = new double[8];
        data[0] = 1d;
        double[] index = dataProvider.makeCounters(8);
        double[] meaned  = haar.haarInverse(data);
        if (writeData(index, meaned)) {
            System.out.println("go to gnuplt to plot");
        }
    }

    @Test
    // plot transform for single 1's in signal data
    public void test4()  {
        double[] data = new double[8];
        data[7] = 1d;
        double[] index = dataProvider.makeCounters(8);
        double[] meaned  = haar.haarTransform(data);
        if (writeData(index, meaned)) {
            System.out.println("go to gnuplt to plot");
        }
    }


    private boolean writeData(double[] data1, double[] data2) {
        try (BufferedWriter writer = Files.newBufferedWriter(path,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {
            for (int i = 0; i < data1.length; i++) {
                writer.write("" + data1[i] + "\t" + "" + data2[i] + "\n");
            }
            writer.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
