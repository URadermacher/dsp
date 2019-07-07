package eu.vdmr.math;

public class Fibonacci {
	
	private long[] next = new long[2];
	
	public Fibonacci() {
		next[0] = 1L;
		next[1] = 1L;
	}
	
	public long next() {
		long result = next[0];
		next[0] = next[1];
		next[1] = result + next[0];
		return result;
	}

}
