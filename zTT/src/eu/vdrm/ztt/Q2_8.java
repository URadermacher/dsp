package eu.vdrm.ztt;

public class Q2_8 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Q2_8  q28 = new Q2_8();
		q28.doit();
	}

	private void doit(){
		double[] entries = new double[20];
		for (int i = 0; i < 20; i++){
			int sign = ((i % 2 == 0)? 1:-1);
			entries[i] = (sign * prd(i)) / fact(i);
		}
		double sum = 0.0D;
		for (int i =0; i < entries.length; i++ ){
			sum = sum + entries[i];
		}
		System.out.println(sum);
	}
	
	private double prd(int max){
		if (max == 0){
			return 1.0D;
		}
		double x = Math.log(4.0D);
		double res = 1;
		for (int i = 0; i < max; i++){
			res = res * x;
		}
		return res;
	}
	
	private int fact(int i){
		if (i <= 1){
			return 1;
		} else {
			return i * fact(i-1);
		}
	}
}
