package eu.vdmr.ztt;

public class MovingAverage {

	private  int count;
	private double[]  prevs;
	private int idx;
	private double avg = 0;
	
	public MovingAverage(int count) {
		this.count = count;
		prevs = new double[count];
		for (int i = 0; i < count; i++){
			prevs[i] = 0.0;
		}
		idx = 0;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MovingAverage mv  = new MovingAverage(10);
		double signal = 1.0;
		for (int i = 0; i < 20;i ++){
			double res = mv.calculate(signal);
			signal = 0;
			System.out.println("" + res);
		}
	}
	
	private double calculate(double signal){
		prevs[idx] = signal;
		idx = (idx + 1) % count;
		for (int i = 0; i < count ; i++){
			avg += prevs[i];
		}
		return avg / count;
	}

}
