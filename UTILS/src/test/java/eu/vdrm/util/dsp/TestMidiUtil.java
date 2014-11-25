package eu.vdrm.util.dsp;

import org.junit.Assert;
import org.junit.Test;

public class TestMidiUtil {
    
    @Test
    public void testMiddleC() {
        double res = MidiUtil.getFreqOfMidi(69);  // 69 must be a
        Assert.assertEquals(440, res,1);
    }

    @Test (expected=IllegalArgumentException.class)
    public void testbig() {
        MidiUtil.getFreqOfMidi(128);
    }

    @Test (expected=IllegalArgumentException.class)
    public void testneg() {
        MidiUtil.getFreqOfMidi(-1);
    }

}
