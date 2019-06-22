package eu.vdmr.ztt;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class PiE {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("->" + Math.PI/8);
		StringBuilder sb = new StringBuilder();
		int k = 16;
		for (int n = 0; n < 64; n++){
			//double idx = i;
			//double ex = (idx/8.0)*Math.PI * (-1.0);
			//double res = Math.exp(ex);
			double angle = ((2 * Math.PI) / 64.0) * n * k;
			double sin = Math.sin(angle);
			double cos = Math.cos(angle);		
			System.out.println(n + " - " + angle + " : "+sin + "; "+cos);
			sb.append(sin + "\n");
		}
		String res = sb.toString();
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("D:/tmp/sin.txt"));
	        out.write(res);
	        out.flush();
	        out.close();
			System.out.println(res);
		} catch (Exception e){
			System.out.println("oeps" + e);
		}

	}

}
