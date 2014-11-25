package eu.vdrm.util.bits;

public class HexUtil {

	
	/**
	 * int to Hex string 
	 */
	public String hexo(int i) {
		return hexo((long)i);
	}

	/**
	 * long to Hex string 
	 */
	public String hexo(long i) {
		return (Long.toHexString(i));
	}

}
