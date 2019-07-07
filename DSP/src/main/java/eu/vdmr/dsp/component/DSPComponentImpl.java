package eu.vdmr.dsp.component;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class DSPComponentImpl implements DSPComponent {


    public DSPComponentImpl() {
        this.id = UUID.randomUUID().toString();
    }

    public DSPComponentImpl(String name, DeviceType type) {
        this();
        this.name = name;
        this.type = type;
    }

    /**
     * number of samples per second for CD quality.
     * multiply by 2 for stereo
     * Also multiply if element is bigger than one buffer entry (i.e. putting int into ByteBuffer)
     * ==> this value (the optimal buffer size!)  should be 3040 for audio play-back on my 'wolfgang 2'
     * ==> optimal sample rate is then 44100
     */
    public static int DEFAULTBUFFERSIZE = 44100;

    protected DSPComponent previous;
    protected List<DSPComponent> nexts;
    protected String name;
    protected DeviceType type;
    protected String id;

    /**
     * Override this method if reset functionality is required
     * for your AbstractAudio device derivative. If not overridden
     * nothing will happen in this device when a reset operation
     * occurs.
     */
    public void reset() {
    }

    /**
     * Propagate reset call to all processing stages
     */
    public void propagateReset() {

        if (previous != null)
            previous.propagateReset();

        // Call reset on this stage of processing
        reset();
    }

    /**
     * Called to perform a reset operation on all participating device
     * stages.
     */
    public void doReset() {

        // Propagate reset towards sink then towards source
        if (nexts != null) {
            for (DSPComponent next : nexts) {
                next.doReset();
            }
        } else {
            propagateReset();
        }
    }

    public void setPrevious(DSPComponent previous) {
        this.previous = previous;
    }

    public void addNext(DSPComponent next) {
        if (this.nexts == null) {
            nexts = new ArrayList<>();
        }
        nexts.add(next);
    }

    /**
     * This method must be implemented by all devices that extend
     * the AbstractAudio class. This is the method by which audio
     * samples are moved between device stages. Call the getSamples()
     * method on the device previous to this device in the signal path
     * causes samples to be pull from it.
     *
     * @param buffer is a buffer that this stage of processing
     *               should fill with data for subsequent return to calling code.
     * @param length is the number of samples that are requested
     * @return int indicating the number of samples available or -1 if
     * the end of input or file has been reached.
     */
    public abstract int getSamples(short[] buffer, int length);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DeviceType getType() {
        return type;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * override this iff you need to do something when run stops (like flush and close files ...)
     * in this case DO NOT FORGET to call stop() on your previous..
     */
    public void stop() throws Exception {
        if (previous != null) {
            previous.stop();
        }
    }
}
