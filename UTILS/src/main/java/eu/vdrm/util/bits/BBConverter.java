package eu.vdrm.util.bits;

import java.nio.ByteBuffer;
//import java.nio.ShortBuffer;

import org.apache.log4j.Logger;

/**
 * bits, bytes, int etc convert to each other
 * @author ura03640
 *
 */
public class BBConverter {
	private static Logger LOG = Logger.getLogger(BBConverter.class);

	public static byte[] intToByte(int in){
		byte[] barr = new byte[4];
        for (int i = barr.length - 1; i >= 0; i--) {
            barr[i] = (byte) in;
            in >>= 8;
        }
        return barr;
	}

	// TODO what about endianess
	public static byte[] shortToByte(short in){
		byte[] barr = new byte[2];
		barr[1] = (byte)in;
		in >>= 8;
		barr[0] = (byte) in;
        return barr;
	}
	
	public static byte[] shortToByte(short[] in){
		byte[] barr = new byte[(in.length * 2)];
		int idx = 0;
		for (int i = 0; i < in.length; i++){
			short t = in[i];
			barr[idx + 1] = (byte)t;
			t >>= 8;
			barr[idx] = (byte)t;
			LOG.debug("short " + toBitString(t) + " bytes: " + barr[idx] + " - " + barr[idx+1]);
			idx += 2;
		}
		return barr;
	}
	/**
	 * uses byteorder of current system
	 * @param in
	 * @return
	 */
	public static byte[] shortToByte2(short[] in){
		ByteBuffer outBuffer = ByteBuffer.allocate(in.length * 2);
		for (int i = 0; i < in.length; i++){
			outBuffer.putShort(in[i]);
//			LOG.debug("short = " + toBitString(in[i]));
//			LOG.debug("bytes = " + toBitString(outBuffer.array()[i*2]) +" - " + toBitString(outBuffer.array()[i*2 + 1]));  
		}
		return outBuffer.array();
	}

	/**
	 * just truncate (array of short down to byte)
	 * @param in
	 * @return
	 */
	public static byte[] shortDownToByte(short[] in){
		byte[] out = new byte[in.length];
		for (int i = 0; i < in.length; i++){
			short s = Short.parseShort("" + (in[i]/256));
			out[i] = (byte)s;
			//LOG.debug("from " + in[i] +  ", adding " + out[i]);
		}
		return out;
	}
	
	/**
	 * write array of bytes as String of binary values
	 * @param in the array of bytes
	 * @return a string representing the binary values
	 * TODO what about endianess
	 */
	public static String toBitString(byte[] in){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < in.length; i++) {
			sb.append(toBitString(in[i]));
	    }
		return sb.toString();
	}
	
	/**
	 * write a bytes as String of binary values
	 * @param in the array of bytes
	 * @return a string representing the binary values
	 * TODO what about endianess
	 */
	public static String toBitString(byte in){
		StringBuilder sb = new StringBuilder();
	    int mask = 0x80;
	    while (mask > 0) {
	    	if ((mask & in) != 0) {
	    		sb.append('1');
	        } else {
	            sb.append('0');
	        }
	        mask >>= 1;
	    }
	    return sb.toString();
	}

	public static String toBitString(short in){
		StringBuilder sb = new StringBuilder();
	    int mask = 0x8000;
	    while (mask > 0) {
	    	if ((mask & in) != 0) {
	    		sb.append('1');
	        } else {
	            sb.append('0');
	        }
	        mask >>= 1;
	    }
	    return sb.toString();
	}
}
