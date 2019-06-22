package eu.vdmr.miscellaneous;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.Vector;
import eu.vdrm.util.dsp.MidiUtil;
import eu.vdrm.util.run.Args;

/**
 * usage: MakeScale [-i] [-m] [-v] N startval [outfile] \
 * -m = startval is midi : otherwise frequency 
 * -i = prints interval and absolute frequencies (i.e. not only frequencies)
 * N  = number of notes within an octave (12 would result in 'normal' chromatic scale 
 * startval = frequency or midi note number of starting note
 * outfile : if filled output will be written to file
 * 
 * (it is possible to use outfile and verbose. If none is used, calculation will be quite silent... 
 * @author ura03640
 * 
 */
public class MakeScale {

    private boolean isMidi = false;
    private boolean printBoth = false;
    private boolean verbose = false;
    private int noteCount;
    private double startFreq;
    private String outFileName;
    private File outFile;
    
    private double ratio;
    private double[] freqs; 
    

    public static void main(String[] args) {
        MakeScale makeScale = new MakeScale();
        makeScale.makeScale(args);
    }

    public void makeScale(String[] args) {
        if (!handelArgs(args)) {
            return;
        }
        if (outFileName != null){
            if (! tryOutput(outFileName)){
                return;
            }
        }
        if (isMidi){ // convert midi to frequency..
            startFreq = MidiUtil.getFreqOfMidi((int)startFreq);
        }
        ratio = Math.pow(2.0, 1.0 / noteCount);
        double currFreq = startFreq;
        freqs = new double[noteCount + 1]; // we'll write the first octave, too
        for (int i = 0; i <= noteCount; i++){
            freqs[i] = currFreq;
            currFreq *= ratio;
        }
       
        outputStuff(freqs, ratio);
        
    }

    /**
     * handle command line args
     * 
     * @param args
     *            the args
     * @return false if any error found
     */
    private boolean handelArgs(String[] args) {
        if (args == null){
            return false;
        }
        Args cmdarg = new Args();
        Args.ArgVal vals = cmdarg.getArgs(args, false);
        if (vals.getKetValuePairs() != null) {
            Set<String> keys = vals.getKetValuePairs().keySet();
            for (String key : keys) {
                if ("m".equalsIgnoreCase(key)) {
                    isMidi = true;
                } else if ("i".equalsIgnoreCase(key)) {
                    printBoth = true;
                } else if ("v".equalsIgnoreCase(key)) {
                    verbose = true;
                } else {
                    System.out.println("Unknown option: " + key);
                    return false;
                }
            }
        }
        Vector<String> parms = vals.getParms();
        if (parms != null) {
            if (parms.size() < 2 || parms.size() > 3) {
                System.out.println("Only 2 or 3 pars allowed: number, start and may be outfile ");
                return false;
            }
            try {
                noteCount = Integer.parseInt(parms.get(0));
            } catch (NumberFormatException nfe) {
                System.out.println("Not a valid number for note count: " + parms.get(0));
                return false;
            }
            try {
                startFreq = Double.parseDouble(parms.get(1));
                if (isMidi) {
                    if (startFreq < 0.0 || startFreq > 127.0) {
                        System.out.println("Valid midi notes are 0 to 127: " + startFreq + "  is not valid");
                        return false;
                    }
                } else {
                    if (startFreq < 0.0 ) {
                        System.out.println("starting frequency must be > 0 ");
                        return false;
                    }
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Not a valid number for start-frequency: " + parms.get(0));
                return false;
            }
            if (parms.size() > 2){
                outFileName = parms.get(2);
            }
        }
        return true;
    }
    
    private boolean tryOutput(String fileName){
        File file = new File(fileName);
        if (! file.canWrite()){
            System.out.println("Cannot  write file: " + fileName);
            return false;
        }
        return true;
    }
    
    
    private void outputStuff(double[] freqs, double ratio ) {
        
        FileWriter wr = null;
        try {
            
            if (outFile != null){
                wr = new FileWriter(outFile);
            }
            for (int i = 0; i < freqs.length; i++){
                String out = null;
                if (printBoth){
                    out = i + " " + Math.pow(ratio, i) + " - " + freqs[i];
                } else {
                    out = i + " " + freqs[i];
                }
            
                if (verbose){
                    System.out.println(out);
                }
                if (outFile != null){
                    wr.write(out);
                }
            }
        } catch (IOException ioe){
            System.out.println(" Cannot write to file " + outFileName);
            ioe.printStackTrace(System.out);
            return; // exit..
        } finally{
            if (wr != null){
                try {
                   wr.close(); 
                } catch (IOException ioec){
                    System.out.println(" Cannot close file " + outFileName);
                    ioec.printStackTrace(System.out);
                    return;
                }
            }
        }
    }

    public double getRatio() {
        return ratio;
    }

    public double[] getFreqs() {
        return freqs;
    }
}
