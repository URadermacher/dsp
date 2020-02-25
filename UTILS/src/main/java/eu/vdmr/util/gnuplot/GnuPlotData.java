package eu.vdmr.util.gnuplot;


public class GnuPlotData {
    /**
     * number of data files to write
     */
    private int dataFiles;

    /**
     * per file number of provided data arrays
     */
    private int[] dataEntries;

    /**
     * default plot styling
     */
    private static GnuPlotStyling defaultStyling;

    /**
     * per data a specific styling may be used
     */
    private GnuPlotStyling styling;

    static {
        defaultStyling = new GnuPlotStyling(GnuPlottingStyles.Lines);
    }

    public GnuPlotData(int dataFiles, int[] dataEntries) {
        this.dataFiles = dataFiles;
        this.dataEntries = dataEntries;
    }

    


}
