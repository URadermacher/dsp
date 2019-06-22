package eu.vdmr.ztt;

public class Arctan {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		double sum = 0;
		boolean even = false;
		int quot = -1;
		int MAX = 100;
		for (int i = 1; i < MAX; i++){
			quot = quot + 2;
			if (even) sum = sum - (1/quot);
			else sum = sum + (1/quot);
		}
		System.out.println("res for " + MAX + " = " + sum);

	}

}
