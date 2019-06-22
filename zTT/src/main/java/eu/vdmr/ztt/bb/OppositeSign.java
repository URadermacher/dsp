package eu.vdmr.ztt.bb;

import eu.vdmr.ztt.OUT;

public class OppositeSign {
	
	public static void main(String[] args) {
		short a =  1;
		short b =  -10;
		int x = (a ^ b);
		OUT.out(Integer.toBinaryString(x));
		if ((a ^ b) < 0) {
			OUT.out("diff");
		} else {
			OUT.out("same");
		}
		for (int i = -10; i < 11; i++){
			OUT.out(Integer.toBinaryString(i)  + " - " + Integer.toBinaryString(~i));
		}
	}

}
