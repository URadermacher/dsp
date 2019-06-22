package eu.vdmr.ztt;

public class DFTUtils {

	
	public static double[] getDelta(int size) {
		double[] res = new double[size];
		res[0] = 1.0;
		for(int i = 1; i < size; i++){
			res[i] = 0.0;
		}
		return res;
	}
	
	
	public static double[] getStep(int size) {
		double[] res = new double[size];
		for(int i = 0; i < size; i++){
			res[i] = 1.0;
		}
		return res;
	}
	
	public static double[] getXYCos(int size, int x, int y){
		double[] res = new double[size];
		for(int i = 0; i < size; i++){
			res[i] = x *  Math.cos((2*Math.PI*i)/y);
		}
		return res;
	}
	public static double[] getXYCosPhase(int size, int x, int y, int z){
		double[] res = new double[size];
		for(int i = 0; i < size; i++){
			res[i] = x *  Math.cos(((2*Math.PI*i)/y) + (z/Math.PI));
		}
		return res;
	}
	
	public static double[] getPartStep(int size, int len){
		double[] res = new double[size];
		for(int i = 0; i < size; i++){
			if (i < len){
				res[i] = 1.0;
			} else {
				res[i] = 0.0;
			}
		}
		return res;
	}
}
