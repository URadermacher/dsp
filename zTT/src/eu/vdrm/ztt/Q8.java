package eu.vdrm.ztt;

public class Q8 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		double x = 1;
		double y = 1;
		for(int i = 0; i < 100; i++){
			System.out.println("for " + i + " = " + (2*(x+y)+ (x+y) -3)/(((x+y)*(x+y)) - (x+y)));
			y = y / 10;
		}

	}

}
