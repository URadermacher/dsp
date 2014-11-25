package eu.vdrm.dsp.component;

public abstract class AbstractAudio extends DSPComponentImpl{
	// Class data
	protected int samplingRate;
	protected boolean byPass;
	
	/**
	 * AbstractAudio class constructor
	 *
	 * NOTE: name and type are for informational purposes only and
	 * serve to identify a specific device.
	 *
	 * @param String name is the name given to this device
	 * @param int type is one of the device types listed above
	 */
	public AbstractAudio(String name, DeviceType type) {

		super(name, type);

		// Do various initialization
		previous = null;
		nexts = null;
		
		samplingRate = 44100; // default
		byPass = false;
	}


	/**
	 * Convert AbstractAudio parameters to a string for display
	 *
	 * @return String containing description of this device
	 */
	public String toDisplayString() {
		String retString = "<AbstractAudio: " + name;
		retString += " Type: " + type.toString();
		retString += " Rate: " + samplingRate;
		retString += " Bypass: " + byPass + ">\n";

		return retString;
	}
	/**
	 * Static method for displaying a type string given the device type
	 *
	 * @param int type is the type of this AbstractAudio device
	 */
	public static String typeString(DeviceType type) {

		switch (type) {

			case NOTYPE:
				return "No Type";

			case SOURCE:
				return "Source";

			case PROCESSOR:
				return "Processor";

			case MONITOR:
				return "Monitor";

			case SINK:
				return "Sink";
		}
		return "Unknown type";
	}

	/**
	 * Function to determine if one AbstractAudio device is the
	 * same as another. Equality is assumed if name and type match. This
	 * method is used in the LinkedListVector class to find specific
	 * devices on the list.
	 *
	 * @return boolean true if the name and type match, false otherwise.
	 */
	public boolean equals(AbstractAudio a) {

		return (name.equals(a.getName()) && (type == a.type)) ;
	}



	/**
	 * Return the current by pass state of this device.
	 *
	 * @return boolean true if this stage of processing is bypassed and
	 * false otherwise.
	 */
	public boolean getByPass() {

		return byPass;
	}
	
	/**
	 * Used to set the bypass state of this device
	 *
	 * @param boolean byPass if true stage will be bypassed.
	 */
	public void setByPass(boolean byPass) {

		this.byPass = byPass;
	}
	
	public int getSamplingRate() {
		return samplingRate;			// Return sampling rate
	}

	protected void setSamplingRate(int s) {
		samplingRate = s;
	}
	
	
	


	/**
	 * Given four integer values, generate a long containing the chunk
	 * name tag.
	 */
	public static long chunkName(int a, int b, int c, int d) {

		long l =	(((long) a & 255) << 24) +
					(((long) b & 255) << 16) +
					(((long) c & 255) <<  8) +
					 ((long) d & 255);
		return l;
	}

	// Write big endian integer to buffer
	public static void writeIntMsb(byte [] buffer, int offset, int value, int size) {

		byte b1 = (byte)((value >> 24) & 255);
		byte b2 = (byte)((value >> 16) & 255);
		byte b3 = (byte)((value >>  8) & 255);
		byte b4 = (byte)((value      ) & 255);

		if (size == 2) {
			buffer[offset++] = b3;
			buffer[offset++] = b4;
		}	else	{
			buffer[offset++] = b1;
			buffer[offset++] = b2;
			buffer[offset++] = b3;
			buffer[offset++] = b4;
		}
	}



}
