package eu.vdmr.util.gnuplot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class GnuPlotter {
    private Path dataPath;
    private Path cmdPath;
    private GnuPlotData data;

    public GnuPlotter(GnuPlotData data) {
        String dataFile = System.getProperty("java.io.tmpdir") + File.separator + "Ex1.txt";
        dataPath = Paths.get(dataFile);
        System.out.println("data: " + dataPath.toAbsolutePath());
        String cmdFile = System.getProperty("java.io.tmpdir") + File.separator + "execGnu.cmd";
        cmdPath = Paths.get(cmdFile);
        System.out.println("cmd: " + cmdPath.toAbsolutePath());
    }

    private boolean writeCommand() {
        try (BufferedWriter writer = Files.newBufferedWriter(cmdPath,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {
            writer.write("plot \"ex1.txt\" with lines \n" +
                    "pause -1"
            );
            writer.write("plot \"ex2.txt\" with lines \n" +
                    "pause -1"
            );
            writer.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean writeData(double[] data1, double[] data2) {
        try (BufferedWriter writer = Files.newBufferedWriter(dataPath,
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

    private void doPlot() {

    }

    public void execute() {
        writeCommand();
        writeData();
        doPlot();
    }

}
