package eu.vdrm.ztt;

public class Leaky_decay {

	static double y = 0.0;
	static double lambda = 0.9;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		double prm = 1;
		for (int i = 0; i < 20; i++){
			double k = leaky(prm);
			prm = 0;
			System.out.println(""+k);
		}
	}
	
	private static  double leaky(double x){
		double a = lambda * y;
		double b = 1 - lambda;
		double c = b * x;
		double d = a + c;
		y = lambda * y + (1 - lambda) * x;
		return y;
	}

}
