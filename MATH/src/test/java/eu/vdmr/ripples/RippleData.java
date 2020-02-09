package eu.vdmr.ripples;

public class RippleData {

    public double[] makeDoubles(double... entries) {
        double[] retval = new double[entries.length];
        int idx = 0;
        for (double entry : entries) {
            retval[idx++] = entry;
        }
        return retval;
    }

    public double[] makeRandomDoubles(int count, int max) {
        double[] result = new double[count];
        for (int i = 0; i < count; i++) {
            result[i] = Math.random() * max;
        }
        return result;
    }

    public double[] makeCounters(int cnt) {
        double[] result = new double[cnt];
        for (int i = 0; i < cnt; i++) {
            result[i] = i;
        }
        return result;
    }

}
