package eu.vdmr.math;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FibonacciTest {
	
	private long[] exp = new long[] {1L,1L,2L,3L,5L,8L,13L,21L};



	@Test
	public void testFibonacci() {
		Fibonacci tt = new Fibonacci();
		long result = 0L;
		for (int i = 0; i < exp.length; i++) {
			result = tt.next();
			assertThat(result).isEqualTo(exp[i]);
		}
	}
}
