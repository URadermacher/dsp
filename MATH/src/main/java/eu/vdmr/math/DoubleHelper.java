package eu.vdmr.math;

public class DoubleHelper {

    private static final double adjustment = 10000000.0;


    public static double adjust(double number) {
        // 0.20000000000018 should be 0.2, as 0.1999999999999993
//        // but 1.0 E^7 should *not* become 92233.72036854776
//        if (number % 1 ==0 ) {
//            return number;
//        }
//        double nn = number * adjustment;
//        double xx = Math.round(nn);
//        double yy = xx / adjustment;
        return Math.round(number * adjustment) / adjustment;
    }

}
