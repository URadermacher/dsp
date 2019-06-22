package eu.vdmr.miscellaneous;

import org.junit.Test;

public class AttackDecayTest {

    @Test
    public void testSimpleDecay() {
        AttackDecay ad = new AttackDecay();
        String[] parms  = new String[5];
        parms[0] = "200";    // duration
        parms[1] = "200";  // nr of points
        parms[2] = "40";  // start value
        parms[3] = "0"; // end value
        parms[4] = "C:/tmp/data.bks"; // output file
        ad.createPoints(parms);
     
    }
}
