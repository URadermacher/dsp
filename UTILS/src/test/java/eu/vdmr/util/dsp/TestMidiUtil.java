package eu.vdmr.util.dsp;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
class TestMidiUtil {

    @Test
    void testMiddleC() {
        double res = MidiUtil.getFreqOfMidi(69);  // 69 must be a
        assertThat(res).isEqualTo(440);
    }

    @Test
    void testbig() {
        assertThatThrownBy(() -> {MidiUtil.getFreqOfMidi(128)})
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("is NOT a valid Midi note number!");
    }

    @Test
    void testneg() {
        assertThatThrownBy(() -> {MidiUtil.getFreqOfMidi(-1)})
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("is NOT a valid Midi note number!");
    }

}
