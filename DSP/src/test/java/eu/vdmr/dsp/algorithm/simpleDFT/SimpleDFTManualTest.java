package eu.vdmr.dsp.algorithm.simpleDFT;

import eu.vdmr.math.matrix.ComplexMatrix;
import eu.vdmr.util.gnuplot.GnuPlot;
import eu.vdmr.util.gnuplot.GnuPlottingStyles;
import org.apache.commons.math3.complex.Complex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SimpleDFTManualTest {

    private SimpleDFT dft;
    private static Logger LOG = LogManager.getLogger(SimpleDFTManualTest.class);


    @BeforeEach
    public void setup() {
        dft = new SimpleDFT();
    }


    @Test
    public void testConstant() {
        int count = 256;
        Complex[] signal = SimpleDFTTestData.makeConstantRealSignal(count);
        Complex[] spectrum = dft.doDFTTrigonometric(signal);
        double[] realSignal = getRealArray(signal);
        double[] imgSignal = getImaginaryArray(signal);
        double[] realSpeectrum = getRealArray(spectrum);
        double[] imgSpectrum = getImaginaryArray(spectrum);
        GnuPlot.GnuPlotDataBuilder buikder = GnuPlot.GnuPlotDataBuilder.eenGnuPlotDataBuilder()
                .withPlottingStyles(GnuPlottingStyles.Impulses, GnuPlottingStyles.Points);

    }

    @Test
    public void testMatrix_8() {
        Complex[] signal = SimpleDFTTestData.makeConstantRealSignal(8);
        dft.doDFTWithMatrix(signal);
    }

    private double[] getRealArray(Complex[] signal) {
        double[] result = new double[signal.length];
        for (int i = 0; i < signal.length; i++) {
            result[i] = signal[i].getReal();
        }
        return result;
    }


    private double[] getImaginaryArray(Complex[] signal) {
        double[] result = new double[signal.length];
        for (int i = 0; i < signal.length; i++) {
            result[i] = signal[i].getImaginary();
        }
        return result;
    }
}
