package eu.vdrm.miscellaneous;

import org.junit.Assert;
import org.junit.Test;

public class MakeScaleTest {

    @Test
    public void testMakeScaleNull(){
        MakeScale ms = new MakeScale();
        ms.makeScale(null);
        // nothing done, but no crash..
        Assert.assertNull(ms.getFreqs());
    }
    
    @Test
    public void testMakeScaleWrongParm(){
        MakeScale ms = new MakeScale();
        String[] parms = new String[1];
        parms[0] = "-r";
        ms.makeScale(parms);
        // nothing done, but no crash..
        Assert.assertNull(ms.getFreqs());
    }
    
    @Test
    public void testMakeScaleCToCChromatic(){
        MakeScale ms = new MakeScale();
        String[] parms = new String[5];
        parms[0] = "-m";
        parms[1] = "12";
        parms[2] = "69";
        parms[3] = "-v";
        parms[4] = "-i";
        ms.makeScale(parms);
        Assert.assertNotNull(ms.getFreqs());
        Assert.assertEquals(13,ms.getFreqs().length); // 12 notes plus octave..
        Assert.assertEquals(ms.getFreqs()[0] *2, ms.getFreqs()[12], 1); // octave is twice
        
    }
}
