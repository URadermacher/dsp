package eu.vdrm.util.string;


public class Extractor {
    
    public static double[] extractDoubles(String in, int count, String sep){
        String[] splitted = in.split(sep);
        if (splitted.length < count){
            throw new IllegalArgumentException("not enough elems in string to return " + count + " doubles"  );
        }
        double[] res = new double[count];
        int i = 0;
        boolean endOfData = false;
        while (i < count && !endOfData){
            for (String single : splitted){
                try {
                    double d = Double.parseDouble(single);
                    res[i] = d;
                    i++;
                } catch (NumberFormatException nfe){
                    // nop, just ry next entry
                }
            }
            endOfData = true;
        }
        if (i < count){
            throw new IllegalArgumentException("not " + count + " doubles found in string, only " + i + " found"  );
        }
        return res;
        
    }   

}
