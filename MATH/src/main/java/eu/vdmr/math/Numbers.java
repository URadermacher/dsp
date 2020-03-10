package eu.vdmr.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class Numbers {
    
//    public static Double getMax(List<Double> data){
//        check(data);
//        Double max = Double.MIN_VALUE;
//        for (Double curr : data){
//            if (max < curr){
//                max = curr;
//            }
//        }
//        return max;
//    }
//
    
    public static <T extends Number>  T getMax(List<T> data){
        if (data == null){
            throw new IllegalArgumentException("may not be called with null!");
        }
        if (data.size() == 0){
            throw new IllegalArgumentException("may not be called with empty list!");
        }
        T max = data.get(0);
        for (int i = 0 ; i < data.size(); i++){
            if (isbigger(data.get(i),max)){
                max = data.get(i);
            }
        }
        return max;
    }
    
    
    private static boolean isbigger(Number a , Number b){
        // sorry no compare in Number...
        // normal numbers:
        if (a instanceof Byte ||a instanceof Integer || a instanceof Short || a instanceof Long){
            return ((Long)a.longValue() > ((Long)b.longValue()));
        }
        // with decimal
        if (a instanceof Float || a instanceof Double){
            return ((Double)a.doubleValue() > ((Double)b.doubleValue()));
        }
        // big
        if (a instanceof BigDecimal){
            return ((BigDecimal)a).compareTo((BigDecimal)b) > 0;
        }
        // big
        if (a instanceof BigInteger){
            return ((BigInteger)a).compareTo((BigInteger)b) > 0;
        }
        throw new IllegalArgumentException("Unknown Number type " + a.getClass().getCanonicalName());
        
    }

    public static int expOf2(int in) {
        if (in < 1) {
            return -1;
        }
        int test = 1;
        int exp = 0;
        while (test <= in) {
            if (test == in) {
                return exp;
            }
            if (test > Integer.MAX_VALUE/2) {
                return -1;
            }
            test = test * 2;
            exp++;
        }
        return -1;
    }

    private static final double doubleAdjustment = 10000000.0;
    public static double adjustDouble(double number) {
        // 0.20000000000018 should be 0.2, as 0.1999999999999993
//        // but 1.0 E^7 should *not* become 92233.72036854776
//        if (number % 1 ==0 ) {
//            return number;
//        }
//        double nn = number * adjustment;
//        double xx = Math.round(nn);
//        double yy = xx / adjustment;
        return Math.round(number * doubleAdjustment) / doubleAdjustment;
    }

}
