package eu.vdmr.miscellaneous;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import eu.vdmr.miscellaneous.data.BreakPoint;
import eu.vdmr.util.string.Extractor;

public class BreakPoints {


    private List<String> errs;
    private List<BreakPoint> points;
    
    public BreakPoints(){
        errs = new ArrayList<String>();
        points = new ArrayList<BreakPoint>();
    }
    
    public long getCount() {
        return points.size();
    }
    
    public List<BreakPoint> getPoints() {
        return points;
    }

    
    public boolean loadFromPhysicalFile(String filename) {
        boolean returnval = true;
        double lastTime = Double.MIN_VALUE;
        BufferedReader in = null;
        try {
            File file = new File(filename);
            in = new BufferedReader(new FileReader(file));
            String str;
            long count = 1;
            while ((str = in.readLine()) != null) {
                String trimmed = str.trim();
                if ("".equals(trimmed)) {
                    continue; // skip empty lines
                }
                if (trimmed.startsWith("#")) {
                    continue; // allow comments
                }
                trimmed = trimmed.replaceAll("\t", " "); //we support tabs as delimiter
                double[] thedoubles =  Extractor.extractDoubles(trimmed, 2, " " );
                double time = thedoubles[0];
                if (time < lastTime){
                    errs.add("breackpoint goes back in time in line " + count + "; skipped");
                    returnval = false;
                } else {
                    double value = thedoubles[1];
                    BreakPoint bp = new BreakPoint(time, value);
                    points.add(bp);
                }               
                count++;
                lastTime = time;
            }
        } catch (IOException e) {
            System.out.println("IOException " + e);
            e.printStackTrace(System.out);
            errs.add(e.getMessage());
            returnval = false;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ioe){
                    // noppes..
                }
            }
        }
        if (points.size() == 0 ){
            errs.add("No breakpoints read from file");
            returnval = false;
        }
        return returnval;
    }
    
    public boolean writeToFile(String fileName){
        if (points == null){
            return false;
        }
        PrintWriter writer  = null;
        try {
            writer = new PrintWriter(fileName);
            writer.write("# filename: " + fileName +"\n");
            for (BreakPoint point : points){
                writer.write("" + point.getTime() + " " + point.getValue()+ "\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFound ("+fileName+")" + e);
            e.printStackTrace(System.out);
            errs.add(e.getMessage());
            return false;
        } finally {
            if (writer != null){
                writer.close();
            }
        }
        return true;
    }
    
    
    /**
     * Not really clear what "add" can mean for a sorted list.
     * if atendOnly = true, value will just be added at the end
     * (but stuff must be kept sorted!)
     * if atEndOnly is false , the new stuff must be merged with old stuff and then :
     *   what do we do with duplicate time-point: replace, skip, exception??
     * @param data
     * @param atEndOnly
     * @return
     */
    public boolean add(Map<Double,Double> data, boolean atEndOnly){
        throw new UnsupportedOperationException("add noyt yet implemented");
    }
    /**
     * creates a list of Breakpoint from a <Double,Double> Map.
     * Will remove any existing data.
     * Will fail (= return false) if no data given.
     * to add to an existing set use add(Map<Double,Double>)
     * @param data the data
     * @return false if no data given (null or empty map) 
     */
    public boolean reset(Map<Double,Double> data){
        points = new ArrayList<BreakPoint>();
        return set(data);    }
    
    /**
     * creates a list of Breakpoint from a <Double,Double> Map.
     * Will fail (= return false) if no data given or if data already set.
     * To replace an existing set use reset(Map<Double,Double>), to add to an existing set use add(Map<Double,Double>)
     * @param data the data
     * @return false if no data given (null or empty map) or if data already exist
     */
    public boolean set(Map<Double,Double> data){
        if (data == null || data.size() == 0){
            errs.add("no data given");
            return false;
        }
        if (points.size() != 0){
            errs.add("data already exist");
            return false;
        }
        Set<Entry<Double,Double>> dataset = data.entrySet();
        for (Entry<Double,Double> entry : dataset){
            BreakPoint bp = new BreakPoint(entry.getKey().doubleValue(), entry.getValue().doubleValue());
            points.add(bp);
        }
        
        return true;
    }
    
    /**
     * let the max value in the point be = limit and adjust other values accordingly
     * @param limit
     */
    public void normalize(Double limit){
        double max = getMaxValue();
        double factor = limit/max;
        scale(factor);
        
    }
    
    public void scale(double factor){
        for (BreakPoint bp : points) {
            bp.setValue(bp.getValue() * factor);
        }
    }
    
    
    /**
     * shift all values a specified amount up (positive) or down (negative) 
     */
    public void shift(Double amount){
        for (BreakPoint bp : points) {
            bp.setValue(bp.getValue() + amount);
        }
    }
    
    public double getMaxValue(){
        if (points.size() == 0){
            throw new IllegalStateException("no data");
        }
        double max = Double.MIN_VALUE;
        for (BreakPoint bp : points) {
            if (bp.getValue() > max){
                max = bp.getValue();
            }
        }
        return max;
    }
    
    public List<String> getErrors(){
        return errs;
    }
   
}
