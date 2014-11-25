package eu.vdrm.util.dsp;

public class MidiUtil {
    
    /**
     * calculate frequency of midi note
     * @param midinote number
     * @return frequency (in Hz) 
     */
    public static double getFreqOfMidi(int midinote) {
        if (midinote < 0 || midinote > 127){
            throw new IllegalArgumentException(""  + midinote  + " is NOT a valid Midi note number!" );
        }
        double ratio = Math.pow(2.0 , 1.0/12.0); // 12th root
        double C5 = 220.0 * Math.pow(ratio, 3); // middle C is 3 half tones above a (220 Hz)
        double C0 = C5 * Math.pow(0.5, 5); // midi 0 is 5 octaves below
        return C0 * Math.pow(ratio, midinote);
    }

}
