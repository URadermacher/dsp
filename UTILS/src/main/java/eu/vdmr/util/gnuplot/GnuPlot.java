package eu.vdmr.util.gnuplot;


import java.util.ArrayList;
import java.util.List;

public class GnuPlot {

    private GnuPlot() {
    }

    public String getplatCommand() {
        return null;
    }

    public static class GnuPlotDataBuilder {

        private List<GnuPlottingStyles> styles;

        public static GnuPlotDataBuilder eenGnuPlotDataBuilder() {
            return new GnuPlotDataBuilder();
        }

        public GnuPlotDataBuilder withPlottingStyles(GnuPlottingStyles... plottingStyles) {
            styles = new ArrayList<>();
            for (GnuPlottingStyles style : plottingStyles) {
                styles.add(style);
            }
            return this;
        }

        public GnuPlotDataBuilder withData() {


            return this;

        }

        public GnuPlot build() {
            GnuPlot data = new GnuPlot();
            return data;
        }
    }



}
