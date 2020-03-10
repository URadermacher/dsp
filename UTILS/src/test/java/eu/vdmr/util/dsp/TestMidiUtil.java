package eu.vdmr.util.dsp;

import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
class TestMidiUtil {

    @Test
    void testMiddleC() {
        double res = MidiUtil.getFreqOfMidi(69);  // 69 must be a
        assertThat(res).isCloseTo(440, Percentage.withPercentage(0.0000001));
    }

    @Test
    void testbig() {
        assertThatThrownBy(() -> MidiUtil.getFreqOfMidi(128))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("is NOT a valid Midi note number!");
    }

    @Test
    void testneg() {
        assertThatThrownBy(() -> {MidiUtil.getFreqOfMidi(-1);})
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("is NOT a valid Midi note number!");
    }

}
