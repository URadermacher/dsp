package eu.vdrm.miscellaneous;

import java.util.Map;
import java.util.TreeMap;

public class AttackDecay {
    private static final double verySmall = 1.0e-4; // ~ -80dB
    /**
     * Create a Attack decay envelope
     * @param args
     *      1 = total duration (double)
     *      2 = number of point (int)
     *      3 = start value
     *      4 = end value
     *      5 = total filename...
     * if  start value > end value
     *      => decay
     * else
     *      => attack
     * endif
     *     
     */
    public static void main(String[] args) {
        AttackDecay attDec = new AttackDecay();
        attDec.createPoints(args);
    }
    
    public void createPoints(String[] args){
        int idx = 1;
        double duration = 0.0D;
        int pointCount = 0;
        double step = 0.0D;
        double startVal = 0.0D;
        double endVal = 0.0D;
        double thisStep = 0.0D;
        double valRange = 0.0D;
        double start = 0.0D;
        double end = 0.0D;
        double offset = 0.0D;
        double factor = 0.0D;
        
        try {
            duration = Double.parseDouble(args[0]);
            idx++;
            pointCount = Integer.parseInt(args[1]);
            idx++;
            step = duration/pointCount;
            startVal = Double.parseDouble(args[2]);
            idx++;
            endVal = Double.parseDouble(args[3]);
            valRange = endVal - startVal;
            idx++;
            if (startVal > endVal) {  // decay
                start = 1.0D;
                end = verySmall;
                valRange = -valRange;
                offset = endVal;
            } else {  // attack
                start = verySmall;
                end = 1.0D;
                offset = startVal;  
                // valRange stays
            }
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException("invalid parameter " + idx);
        }
        
        // now doit:
        thisStep = 0.0D;
        // make normalized curve, scale output to input, range..
        factor = Math.pow(end/start, 1.0d/pointCount);
        Map<Double,Double> data = new TreeMap<Double,Double>();
        
        for (int i = 0; i < pointCount; i++){
            data.put(thisStep, offset + (start * valRange));
            start *= factor;
            thisStep += step; 
        }
        // final value:
        data.put(thisStep, offset + (start * valRange));
        BreakPoints bps = new BreakPoints();
        bps.set(data);
        bps.writeToFile(args[4]);
        
    }


}
